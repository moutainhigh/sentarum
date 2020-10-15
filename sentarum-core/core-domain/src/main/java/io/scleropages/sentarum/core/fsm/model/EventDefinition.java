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
package io.scleropages.sentarum.core.fsm.model;

/**
 * represent a event definition of fsm (Finite-state machine).
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface EventDefinition {

    /**
     * id of this event definition.
     *
     * @return
     */
    Long id();


    /**
     * name of this event definition.
     *
     * @return
     */
    String name();

    /**
     * tag of this event definition.
     *
     * @return
     */
    String tag();

    /**
     * description of this event definition.
     *
     * @return
     */
    String desc();


    /**
     * direction of this event definition.
     *
     * @return
     */
    Direction direction();


    /**
     * 事件方向
     * <pre>
     * 入射事件代表外部系统传入的事件，其会改变节点流向.
     * 出射事件往往是一个外部通知,外部订阅事件处理特定逻辑.
     * </pre>
     */
    enum Direction {

        /**
         * 入射事件
         */
        INCOMING,
        /**
         * 出射事件
         */
        OUTGOING
    }
}