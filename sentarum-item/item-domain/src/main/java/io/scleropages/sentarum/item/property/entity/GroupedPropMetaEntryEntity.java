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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "prop_grouped_prop_meta_entry", uniqueConstraints = @UniqueConstraint(columnNames = {"grouped_prop_meta_id", "property_meta_id"}))
@SequenceGenerator(name = "prop_grouped_prop_meta_entry_id", sequenceName = "seq_prop_grouped_prop_meta_entry", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class GroupedPropMetaEntryEntity extends IdEntity {

    private float order;
    private PropertyMetadataEntity propertyMetadata;
    private GroupedPropMetaEntity groupedPropMeta;

    @Column(name = "order_", nullable = false)
    public float getOrder() {
        return order;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_meta_id", nullable = false)
    public PropertyMetadataEntity getPropertyMetadata() {
        return propertyMetadata;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grouped_prop_meta_id", nullable = false)
    public GroupedPropMetaEntity getGroupedPropMeta() {
        return groupedPropMeta;
    }

    public void setOrder(float order) {
        this.order = order;
    }

    public void setPropertyMetadata(PropertyMetadataEntity propertyMetadata) {
        this.propertyMetadata = propertyMetadata;
    }

    public void setGroupedPropMeta(GroupedPropMetaEntity groupedPropMeta) {
        this.groupedPropMeta = groupedPropMeta;
    }
}
