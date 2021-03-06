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
package io.scleropages.sentarum.item.property.model;


import java.io.Serializable;

/**
 * 属性值
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface PropertyValue extends Serializable {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 属性名称
     *
     * @return
     */
    String name();

    /**
     * 业务类型
     *
     * @return
     */
    Integer bizType();


    /**
     * 业务标识
     *
     * @return
     */
    Long bizId();


    /**
     * 属性元数据
     *
     * @return
     */
    Long propertyMetaId();

    /**
     * 属性值
     *
     * @return
     */
    Object value();


    /**
     * 修改属性值
     *
     * @param value
     */
    void changeValue(Object value);
}
