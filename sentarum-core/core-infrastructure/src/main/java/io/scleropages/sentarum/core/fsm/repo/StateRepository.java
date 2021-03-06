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
package io.scleropages.sentarum.core.fsm.repo;

import io.scleropages.sentarum.core.fsm.entity.StateEntity;
import io.scleropages.sentarum.jooq.tables.FsmState;
import io.scleropages.sentarum.jooq.tables.records.FsmStateRecord;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.criteria.JoinType;
import java.util.Optional;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StateRepository extends GenericRepository<StateEntity, Long>, JooqRepository<FsmState, FsmStateRecord, StateEntity> {

    @Cacheable
    default StateEntity getById(Long id /*,boolean fetchInvocationConfig*/) {
        return get((root, query, builder) -> {
//            if (fetchInvocationConfig) {
            root.fetch("enteredActionConfig", JoinType.LEFT);
            root.fetch("exitActionConfig", JoinType.LEFT);
//            }
            return builder.equal(root.get("id"), id);
        }).orElseThrow(() -> new IllegalArgumentException("no state found: " + id));
    }

    @Cacheable
    default Optional<Long> getIdByValue(Integer value) {
        FsmState fsmState = dslTable();
        return dslContext().select(fsmState.ID).from(fsmState).where(fsmState.VALUE_.eq(value)).fetchOptional(fsmState.ID);
    }

    @Cacheable
    default Optional<Long> getIdByName(String name) {
        FsmState fsmState = dslTable();
        return dslContext().select(fsmState.ID).from(fsmState).where(fsmState.NAME_.eq(name)).fetchOptional(fsmState.ID);
    }

}
