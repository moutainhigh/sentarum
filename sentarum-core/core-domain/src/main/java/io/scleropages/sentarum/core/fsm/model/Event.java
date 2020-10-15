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

import java.util.Date;
import java.util.Map;

/**
 * event of fsm (Finite-state machine)
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Event {

    /**
     * id of event.
     *
     * @return
     */
    Long id();


    /**
     * associated definition of this event.
     *
     * @return
     */
    EventDefinition eventDefinition();


    /**
     * name of event.
     *
     * @return
     */
    String name();

    /**
     * tag of event.
     *
     * @return
     */
    String tag();

    /**
     * description of event.
     *
     * @return
     */
    String desc();

    /**
     * the time of this event was fired.
     *
     * @return
     */
    Date firedTime();


    /**
     * headers of this event.
     *
     * @return
     */
    Map<String, Object> headers();


    /**
     * business payload(order,payment...) of this event.
     *
     * @return
     */
    Object body();
}