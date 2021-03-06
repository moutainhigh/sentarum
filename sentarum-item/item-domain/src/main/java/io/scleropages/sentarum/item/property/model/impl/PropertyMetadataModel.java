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

import io.scleropages.sentarum.item.property.model.Constraint;
import io.scleropages.sentarum.item.property.model.Input;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.ValuesSource;
import org.springframework.core.OrderComparator;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * Generic {@link PropertyMetadata} implementation.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class PropertyMetadataModel implements PropertyMetadata {

    private Long id;
    private String name;
    private String tag;
    private String description;
    private Boolean keyed;
    private Integer bizType;
    private PropertyValueType valueType;
    private PropertyStructureType structureType;
    private Input input;
    private ValuesSource valuesSource;
    private List<Constraint> constraints;
    private Long refId;

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    public Long getId() {
        return id;
    }

    @NotBlank(groups = Create.class)
    public String getName() {
        return name;
    }

    @NotBlank(groups = Create.class)
    public String getTag() {
        return tag;
    }

    @NotBlank(groups = Create.class)
    public String getDescription() {
        return description;
    }

    @NotNull(groups = Create.class)
    public Boolean getKeyed() {
        return keyed;
    }

    @NotNull(groups = Create.class)
    public Integer getBizType() {
        return bizType;
    }

    @NotNull(groups = Create.class)
    public PropertyValueType getValueType() {
        return valueType;
    }

    @NotNull(groups = Create.class)
    public PropertyStructureType getStructureType() {
        return structureType;
    }

    @NotNull(groups = Create.class)
    public Input getInput() {
        return input;
    }

    @Null
    public ValuesSource getValuesSource() {
        return valuesSource;
    }

    @Null
    public List<Constraint> getConstraints() {
        if (null != constraints && constraints.size() > 0)
            OrderComparator.sort(constraints);
        return constraints;
    }

    public Long getRefId() {
        return refId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKeyed(Boolean keyed) {
        this.keyed = keyed;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public void setValueType(PropertyValueType valueType) {
        this.valueType = valueType;
    }

    public void setStructureType(PropertyStructureType structureType) {
        this.structureType = structureType;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public void setValuesSource(ValuesSource valuesSource) {
        this.valuesSource = valuesSource;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }


    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String tag() {
        return tag;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public Boolean keyed() {
        return keyed;
    }

    @Override
    public Integer bizType() {
        return bizType;
    }


    @Override
    public PropertyValueType valueType() {
        return valueType;
    }

    @Override
    public PropertyStructureType structureType() {
        return structureType;
    }

    @Override
    public Input input() {
        return input;
    }

    @Override
    public ValuesSource valuesSource() {
        return valuesSource;
    }

    @Override
    public List<Constraint> constraints() {
        return constraints;
    }

    @Override
    public Long refId() {
        return refId;
    }


    public void assertModelValid(){
        if (Input.InputType.isCheckInput(getInput())) {
            Assert.isTrue(PropertyValueType.isCheckValueType(getValueType()),"invalid property value type. incompatible input type");
        }
    }


    public interface Create {
    }

    public interface Update {
    }
}
