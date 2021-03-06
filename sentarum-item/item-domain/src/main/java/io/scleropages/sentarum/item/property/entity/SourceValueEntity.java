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
package io.scleropages.sentarum.item.property.entity;

import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 参考模型： {@link io.scleropages.sentarum.item.property.model.impl.SourceValueModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "pt_source_value", uniqueConstraints = @UniqueConstraint(columnNames = {"values_source_id", "value_"}), indexes = @Index(columnList = "ref_source_value_id"))
@SequenceGenerator(name = "pt_source_value_id", sequenceName = "seq_pt_source_value", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class SourceValueEntity extends IdEntity {

    private Long value;
    private String valueTag;
    private String attributes;

    private Long refId;
    private ValuesSourceEntity valuesSource;


    @Column(name = "value_", nullable = false)
    public Long getValue() {
        return value;
    }

    @Column(name = "value_tag", nullable = false)
    public String getValueTag() {
        return valueTag;
    }

    @Column(name = "attrs_payload")
    public String getAttributes() {
        return attributes;
    }

    @Column(name = "ref_source_value_id")
    public Long getRefId() {
        return refId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "values_source_id", nullable = false)
    public ValuesSourceEntity getValuesSource() {
        return valuesSource;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public void setValueTag(String valueTag) {
        this.valueTag = valueTag;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public void setValuesSource(ValuesSourceEntity valuesSource) {
        this.valuesSource = valuesSource;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }
}
