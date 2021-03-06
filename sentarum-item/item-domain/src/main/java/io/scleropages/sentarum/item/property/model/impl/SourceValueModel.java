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

import io.scleropages.sentarum.item.property.model.ValuesSource;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Map;

/**
 * Generic {@link io.scleropages.sentarum.item.property.model.ValuesSource.SourceValue} implementation.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class SourceValueModel implements ValuesSource.SourceValue {

    private Long id;
    private Long value;
    private String valueTag;
    private Long refId;
    private Map<String, Object> attributes;
    private Long valuesSourceId;


    public SourceValueModel() {

    }

    public SourceValueModel(Long value, String valueTag, Long valuesSourceId) {
        this.value = value;
        this.valueTag = valueTag;
        this.valuesSourceId = valuesSourceId;
    }

    public SourceValueModel(Long value, String valueTag, Long valuesSourceId, Long refId) {
        this.value = value;
        this.valueTag = valueTag;
        this.valuesSourceId = valuesSourceId;
        this.refId = refId;
    }


    public SourceValueModel(Long id, Long value, String valueTag, Long valuesSourceId) {
        this.id = id;
        this.value = value;
        this.valueTag = valueTag;
        this.valuesSourceId = valuesSourceId;
    }

    public SourceValueModel(Long id, Long value, String valueTag, Long valuesSourceId, Long refId) {
        this.id = id;
        this.value = value;
        this.valueTag = valueTag;
        this.valuesSourceId = valuesSourceId;
        this.refId = refId;
    }


    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    public Long getId() {
        return id;
    }

    @NotNull(groups = Create.class)
    public Long getValue() {
        return value;
    }

    @NotEmpty(groups = Create.class)
    public String getValueTag() {
        return valueTag;
    }

    public Long getRefId() {
        return refId;
    }

    @Null(groups = Query.class)
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @NotNull(groups = {Create.class, Query.class})
    public Long getValuesSourceId() {
        return valuesSourceId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public void setValueTag(String valueTag) {
        this.valueTag = valueTag;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void setValuesSourceId(Long valuesSourceId) {
        this.valuesSourceId = valuesSourceId;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Long value() {
        return getValue();
    }

    @Override
    public String valueTag() {
        return getValueTag();
    }

    @Override
    public Long refId() {
        return getRefId();
    }

    @Override
    public Long valuesSourceId() {
        return getValuesSourceId();
    }

    @Override
    public Map<String, Object> additionalAttributes() {
        return getAttributes();
    }

    public interface Create {
    }

    public interface Update {
    }

    public interface Query {
    }
}
