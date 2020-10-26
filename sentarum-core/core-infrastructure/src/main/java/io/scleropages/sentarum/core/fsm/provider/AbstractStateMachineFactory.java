/**
 * Copyright 2001-2005 The Apache Software Foundation.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.scleropages.sentarum.core.fsm.provider;

import io.scleropages.sentarum.core.fsm.Action;
import io.scleropages.sentarum.core.fsm.StateMachine;
import io.scleropages.sentarum.core.fsm.TransitionEvaluator;
import io.scleropages.sentarum.core.fsm.entity.EventDefinitionEntity;
import io.scleropages.sentarum.core.fsm.entity.EventEntity;
import io.scleropages.sentarum.core.fsm.entity.HistoricTransitionExecutionEntity;
import io.scleropages.sentarum.core.fsm.entity.StateMachineDefinitionEntity;
import io.scleropages.sentarum.core.fsm.entity.StateMachineExecutionEntity;
import io.scleropages.sentarum.core.fsm.entity.StateTransitionEntity;
import io.scleropages.sentarum.core.fsm.entity.mapper.EventEntityMapper;
import io.scleropages.sentarum.core.fsm.entity.mapper.StateMachineDefinitionEntityMapper;
import io.scleropages.sentarum.core.fsm.entity.mapper.StateMachineExecutionEntityMapper;
import io.scleropages.sentarum.core.fsm.entity.mapper.StateTransitionEntityMapper;
import io.scleropages.sentarum.core.fsm.model.Event;
import io.scleropages.sentarum.core.fsm.model.EventDefinition;
import io.scleropages.sentarum.core.fsm.model.InvocationConfig;
import io.scleropages.sentarum.core.fsm.model.State;
import io.scleropages.sentarum.core.fsm.model.StateMachineDefinition;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecution;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecution.ExecutionState;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecutionContext;
import io.scleropages.sentarum.core.fsm.model.StateTransition;
import io.scleropages.sentarum.core.fsm.model.impl.EventModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateMachineExecutionContextModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateMachineExecutionModel;
import io.scleropages.sentarum.core.fsm.repo.EventDefinitionRepository;
import io.scleropages.sentarum.core.fsm.repo.EventRepository;
import io.scleropages.sentarum.core.fsm.repo.HistoricTransitionExecutionRepository;
import io.scleropages.sentarum.core.fsm.repo.StateMachineDefinitionRepository;
import io.scleropages.sentarum.core.fsm.repo.StateMachineExecutionRepository;
import io.scleropages.sentarum.core.fsm.repo.StateRepository;
import io.scleropages.sentarum.core.fsm.repo.StateTransitionRepository;
import org.apache.commons.collections.MapUtils;
import org.scleropages.crud.ModelMapperRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * abstract implementation for state machine factory.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AbstractStateMachineFactory implements StateMachineFactory {


    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private StateMachineDefinitionRepository definitionRepository;
    private StateTransitionRepository transitionRepository;
    private EventDefinitionRepository eventDefinitionRepository;
    private EventRepository eventRepository;
    private StateMachineExecutionRepository stateMachineExecutionRepository;
    private StateRepository stateRepository;
    private HistoricTransitionExecutionRepository historicTransitionExecutionRepository;

    private InvocationContainer invocationContainer;

    private StateMachineExecutionEntityMapper stateMachineExecutionEntityMapper;
    private EventEntityMapper eventEntityMapper;


    @Override
    @Transactional
    public StateMachine createStateMachine(Long definitionId, Integer bizType, Long bizId, Map<String, Object> contextAttributes) {

        Assert.notNull(definitionId, "definitionId is required.");
        Assert.notNull(bizType, "bizType is required.");
        Assert.notNull(bizId, "bizId is required.");

        //read state machine definition and transitions for state machine building.
        StateMachineDefinitionEntity stateMachineDefinitionEntity = definitionRepository.getById(definitionId).orElseThrow(() -> new IllegalArgumentException("no state machine definition id found: " + definitionId));
        List<StateTransitionEntity> stateTransitionEntities = transitionRepository.findAllByStateMachineDefinition_Id(definitionId, definitionRepository, stateRepository, eventDefinitionRepository);
        ProviderStateMachine providerStateMachine = createStateMachineInternal(map(stateMachineDefinitionEntity), map(stateTransitionEntities));

        //create state machine ready for servicing.
        StateMachine delegatingStateMachine = new DelegatingStateMachine(providerStateMachine, stateMachineDefinitionEntity, bizType, bizId);

        if (stateMachineDefinitionEntity.getAutoStartup()) {
            delegatingStateMachine.start(contextAttributes);
        }
        return delegatingStateMachine;
    }


    private void sendEventInternal(DelegatingStateMachine stateMachine, Event event, Map<String, Object> contextAttributes) {
        final ExecutionState executionState = stateMachine.stateMachineExecution.executionState();
        if (!executionState.acceptingEvents()) {
            throw new IllegalStateException("not allowed accepting events. execution state is: " + executionState);
        }
        final StateMachineExecutionEntity executionEntity = stateMachine.stateMachineExecutionEntity;
        final ProviderStateMachine providerStateMachine = stateMachine.providerStateMachine;
        final StateMachineExecutionModel execution = stateMachine.stateMachineExecution;

        State stateFrom = providerStateMachine.currentState();
        ObservableStateMachineExecutionContext executionContext = (ObservableStateMachineExecutionContext) execution.getExecutionContext();
        executionContext.addAttributes(contextAttributes, true);
        boolean accepted = providerStateMachine.sendEvent(event, execution.getExecutionContext());
        EventEntity eventEntity = createEventEntity(event, accepted);
        //if events was accepted.write back execution context and create transition execution...
        if (accepted) {
            saveContextPayload(execution.id(), executionContext);
            createHistoricTransitionExecution(stateMachine, stateFrom, providerStateMachine.currentState(), eventEntity);
        } else {
            logger.warn("current execution [{}] with state [{}] reject event: {}", executionEntity.getId(), executionEntity.getCurrentState().getName(), event.name());
        }
    }


    private final void createHistoricTransitionExecution(DelegatingStateMachine stateMachine, State
            stateFrom, State stateTo, EventEntity event) {
        HistoricTransitionExecutionEntity entity = new HistoricTransitionExecutionEntity();
        entity.setStateMachineDefinition(stateMachine.stateMachineDefinitionEntity);
        entity.setStateMachineExecution(stateMachine.stateMachineExecutionEntity);
        entity.setFrom(stateRepository.getById(stateFrom.id()));
        entity.setTo(stateRepository.getById(stateTo.id()));
        entity.setEvent(event);
        entity.setTime(new Date());
        historicTransitionExecutionRepository.save(entity);
    }

    private final EventEntity createEventEntity(Event event, boolean accepted) {
        Assert.notNull(event, "event must not be null.");
        EventDefinition eventDefinition = event.eventDefinition();
        Assert.notNull(eventDefinition, "event definition must not be null.");
        Long eventDefinitionId = eventDefinition.id();
        Assert.notNull(eventDefinitionId, "event definition id must not be null.");
        EventDefinitionEntity eventDefinitionEntity = eventDefinitionRepository.getById(eventDefinitionId).orElseThrow(() -> new IllegalArgumentException("no event definition found: " + eventDefinitionId));
        EventEntity eventEntity = eventEntityMapper.mapForSave((EventModel) event);
        eventEntity.setEventDefinition(eventDefinitionEntity);
        eventEntity.populateEventDefinitionInformation();
        eventEntity.setAccepted(accepted);
        eventRepository.save(eventEntity);
        if (logger.isDebugEnabled())
            logger.debug("saved state machine event: {}. accepting result: {}.", eventEntity.getName(), eventEntity.getAccepted());
        return eventEntity;
    }


    protected StateMachineExecutionEntity createStateMachineExecution(Integer bizType, Long
            bizId, StateMachineDefinitionEntity stateMachineDefinitionEntity, Map<String, Object> contextAttributes) {
        StateMachineExecutionEntity executionEntity = new StateMachineExecutionEntity();
        executionEntity.setBizType(bizType);
        executionEntity.setBizId(bizId);
        executionEntity.setStateMachineDefinition(stateMachineDefinitionEntity);
        executionEntity.setCurrentState(stateMachineDefinitionEntity.getInitialState());
        executionEntity.setExecutionContext(new StateMachineExecutionContextModel(contextAttributes).getContextPayload());
        executionEntity.setExecutionState(ExecutionState.RUNNING.getOrdinal());
        stateMachineExecutionRepository.save(executionEntity);
        if (logger.isDebugEnabled())
            logger.debug("successfully create state machine execution: {} with state: {}.", executionEntity.getId(), executionEntity.getCurrentState().getName());
        return executionEntity;
    }


    private final class DelegatingStateMachine implements StateMachine {

        private final ProviderStateMachine providerStateMachine;
        private final StateMachineDefinitionEntity stateMachineDefinitionEntity;
        private final Integer bizType;
        private final Long bizId;
        private StateMachineExecutionEntity stateMachineExecutionEntity;
        private StateMachineExecutionModel stateMachineExecution;
        private AtomicBoolean started = new AtomicBoolean(false);


        public DelegatingStateMachine(ProviderStateMachine providerStateMachine, StateMachineDefinitionEntity stateMachineDefinitionEntity, Integer bizType, Long bizId) {
            this.providerStateMachine = providerStateMachine;
            this.stateMachineDefinitionEntity = stateMachineDefinitionEntity;
            this.bizType = bizType;
            this.bizId = bizId;
        }

        @Override
        public Long id() {
            assertStarted();
            return stateMachineExecution.id();
        }

        @Override
        public void sendEvent(Event event, Map<String, Object> contextAttributes) {
            assertStarted();
            sendEventInternal(this, event, contextAttributes);
        }

        @Override
        public void start(Map<String, Object> contextAttributes) {
            if (started.compareAndSet(false, true)) {
                this.stateMachineExecutionEntity = createStateMachineExecution(bizType, bizId, stateMachineDefinitionEntity, contextAttributes);
                this.stateMachineExecution = stateMachineExecutionEntityMapper.mapForRead(stateMachineExecutionEntity);
                observeStateMachineExecution(stateMachineExecution);
                providerStateMachine.start(stateMachineExecution.getExecutionContext());
            }
        }

        @Override
        public void terminate(String note) {
            if (!stateMachineExecution.executionState().acceptingTerminate()) {
                throw new IllegalStateException("not allowed terminate a [" + stateMachineExecution.executionState() + "] state machine.");
            }
            updateStateMachineExecutionState(stateMachineExecutionEntity, ExecutionState.TERMINATE, note);
        }

        @Override
        public void suspend(String note) {
            if (!stateMachineExecution.executionState().acceptingSuspend()) {
                throw new IllegalStateException("not allowed suspend a [" + stateMachineExecution.executionState() + "] state machine.");
            }
            updateStateMachineExecutionState(stateMachineExecutionEntity, ExecutionState.SUSPEND, note);
        }

        @Override
        public void resume(String note) {
            if (!stateMachineExecution.executionState().acceptingResume()) {
                throw new IllegalStateException("not allowed resume a [" + stateMachineExecution.executionState() + "] state machine.");
            }
            updateStateMachineExecutionState(stateMachineExecutionEntity, ExecutionState.RUNNING, note);
        }

        @Override
        public StateMachineExecution stateMachineExecution() {
            assertStarted();
            return stateMachineExecution;
        }

        @Override
        public boolean isStarted() {
            return started.get();
        }

        private void assertStarted() {
            if (!isStarted())
                throw new IllegalStateException("state machine has not bean started. call start first.");
        }

    }

    /**
     * set observable context to given execution.
     *
     * @param execution
     */
    private void observeStateMachineExecution(StateMachineExecutionModel execution) {
        execution.setExecutionContext(new ObservableStateMachineExecutionContext(execution));
    }


    private final void updateStateMachineExecutionState(StateMachineExecutionEntity executionEntity, ExecutionState executionState, String note) {
        executionEntity.setExecutionState(executionState.getOrdinal());
        stateMachineExecutionRepository.save(executionEntity);
    }


    /**
     * observable execution context holds the change status and how to save this context.
     */
    private final class ObservableStateMachineExecutionContext extends StateMachineExecutionContextModel implements StateMachineExecutionContext {

        private final StateMachineExecution execution;
        private final StateMachineExecutionContext nativeContext;
        private volatile boolean changeFlag = false;

        public ObservableStateMachineExecutionContext(StateMachineExecution execution) {
            this.execution = execution;
            this.nativeContext = execution.executionContext();
        }

        @Override
        public void setAttribute(String name, Object attribute) {
            nativeContext.setAttribute(name, attribute);
            changeFlag = true;
        }

        @Override
        public void removeAttribute(String name) {
            nativeContext.removeAttribute(name);
            changeFlag = true;
        }

        @Override
        public Object getAttribute(String name) {
            return nativeContext.getAttribute(name);
        }

        @Override
        public Map<String, Object> getAttributes() {
            return nativeContext.getAttributes();
        }

        @Override
        public boolean hasAttribute(String name) {
            return nativeContext.hasAttribute(name);
        }

        @Override
        public void addAttributes(Map<String, Object> contextAttributes, boolean force) {
            nativeContext.addAttributes(contextAttributes, force);
            if (MapUtils.isNotEmpty(contextAttributes))
                changeFlag = true;
        }

        private boolean changed() {
            return changeFlag;
        }

        private void resetChange() {
            changeFlag = false;
        }

        @Override
        public void save() {
            saveContextPayload(execution.id(), this);
        }
    }

    private void saveContextPayload(Long executionId, ObservableStateMachineExecutionContext stateMachineExecutionContext) {
        if (!stateMachineExecutionContext.changed()) {
            if (logger.isDebugEnabled()) {
                logger.debug("no context attributes changes found from state machine execution: ", stateMachineExecutionContext.execution.id());
            }
            return;
        }
        stateMachineExecutionRepository.saveContextPayload(executionId, stateMachineExecutionContext.getContextPayload());
        stateMachineExecutionContext.resetChange();
    }

    /**
     * implements this method for provider.
     *
     * @param stateMachineDefinition definition of state machine for creating.
     * @param stateTransitions       state transitions of state machine for creating.
     * @return
     */
    protected abstract ProviderStateMachine createStateMachineInternal(StateMachineDefinition
                                                                               stateMachineDefinition, List<StateTransition> stateTransitions);

    /**
     * map given entities to model.
     *
     * @param stateMachineDefinitionEntity
     * @return
     */
    protected StateMachineDefinition map(StateMachineDefinitionEntity stateMachineDefinitionEntity) {
        return (StateMachineDefinition) ModelMapperRepository.getRequiredModelMapper(StateMachineDefinitionEntityMapper.class).mapForRead(stateMachineDefinitionEntity);
    }

    /**
     * map given entities to model.
     *
     * @param stateTransitionEntities
     * @return
     */
    protected List<StateTransition> map(List<StateTransitionEntity> stateTransitionEntities) {
        return (List<StateTransition>) ModelMapperRepository.getRequiredModelMapper(StateTransitionEntityMapper.class).mapForReads(stateTransitionEntities);
    }

    /**
     * return {@link Action} by invocation config from {@link InvocationContainer}
     *
     * @param invocationConfig
     * @return
     */
    protected Action getAction(InvocationConfig invocationConfig) {
        return invocationContainer.getAction(invocationConfig);
    }

    /**
     * return {@link TransitionEvaluator} by invocation config from {@link InvocationContainer}.
     *
     * @param invocationConfig
     * @return
     */
    protected TransitionEvaluator getTransitionEvaluator(InvocationConfig invocationConfig) {
        return invocationContainer.getTransitionEvaluator(invocationConfig);
    }

    @Override
    public StateMachine getStateMachine(Long machineId) {
        return null;
    }

    @Autowired
    public void setDefinitionRepository(StateMachineDefinitionRepository definitionRepository) {
        this.definitionRepository = definitionRepository;
    }

    @Autowired
    public void setTransitionRepository(StateTransitionRepository transitionRepository) {
        this.transitionRepository = transitionRepository;
    }

    @Autowired
    public void setEventDefinitionRepository(EventDefinitionRepository eventDefinitionRepository) {
        this.eventDefinitionRepository = eventDefinitionRepository;
    }

    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired
    public void setInvocationContainer(InvocationContainer invocationContainer) {
        this.invocationContainer = invocationContainer;
    }

    @Autowired
    public void setStateMachineExecutionRepository(StateMachineExecutionRepository stateMachineExecutionRepository) {
        this.stateMachineExecutionRepository = stateMachineExecutionRepository;
    }

    @Autowired
    public void setStateMachineExecutionEntityMapper(StateMachineExecutionEntityMapper
                                                             stateMachineExecutionEntityMapper) {
        this.stateMachineExecutionEntityMapper = stateMachineExecutionEntityMapper;
    }

    @Autowired
    public void setEventEntityMapper(EventEntityMapper eventEntityMapper) {
        this.eventEntityMapper = eventEntityMapper;
    }

    @Autowired
    public void setStateRepository(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Autowired
    public void setHistoricTransitionExecutionRepository(HistoricTransitionExecutionRepository
                                                                 historicTransitionExecutionRepository) {
        this.historicTransitionExecutionRepository = historicTransitionExecutionRepository;
    }

    private class StateMachineExecutionEntityContext {
        private final StateMachineDefinitionEntity stateMachineDefinitionEntity;
        private final StateMachineExecutionEntity stateMachineExecutionEntity;

        public StateMachineExecutionEntityContext(StateMachineDefinitionEntity stateMachineDefinitionEntity, StateMachineExecutionEntity stateMachineExecutionEntity) {
            this.stateMachineDefinitionEntity = stateMachineDefinitionEntity;
            this.stateMachineExecutionEntity = stateMachineExecutionEntity;
        }
    }
}
