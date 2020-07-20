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
package io.scleropages.sentarum.item.property.model.impl;

import io.scleropages.sentarum.item.property.model.PropertyValue;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class PropertyValueModel implements PropertyValue {


    private Long id;
    private String name;
    private Integer bizType;
    private Long bizId;
    private Long metaId;
    private Object value;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBizType() {
        return bizType;
    }

    public Long getBizId() {
        return bizId;
    }

    public Long getMetaId() {
        return metaId;
    }

    public Object getValue() {
        return value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public void setMetaId(Long metaId) {
        this.metaId = metaId;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public String name() {
        return getName();
    }

    @Override
    public Integer bizType() {
        return getBizType();
    }

    @Override
    public Long bizId() {
        return getBizId();
    }

    @Override
    public Long metaId() {
        return getMetaId();
    }

    @Override
    public Object value() {
        return getValue();
    }
}
