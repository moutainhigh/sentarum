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

import io.scleropages.sentarum.item.property.model.input.BasicInput;
import io.scleropages.sentarum.item.property.model.input.ComplexInput;
import org.apache.commons.collections.ComparatorUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 属性组定义，描述属性组或属性组元数据
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface GroupedPropertyMetadata extends Serializable {

    /**
     * 标识
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
     * 属性标题
     *
     * @return
     */
    String tag();

    /**
     * 属性描述
     *
     * @return
     */
    String description();


    /**
     * 输入
     * @return
     */
    default Input input() {
        ComplexInput complexInput = new ComplexInput();
        orderedPropertiesMetadata().forEach(orderedPropertyMetadata -> {
            complexInput.addBasicInput((BasicInput) orderedPropertyMetadata.input());
        });
        return complexInput;
    }


    /**
     * 返回已排序的属性列表
     *
     * @return
     */
    List<OrderedPropertyMetadata> orderedPropertiesMetadata();


    /**
     * 含顺序的 {@link PropertyMetadata}
     */
    class OrderedPropertyMetadata implements PropertyMetadata, Comparable<OrderedPropertyMetadata> {

        private final float order;

        private final PropertyMetadata propertyMetadata;

        public OrderedPropertyMetadata(float order, PropertyMetadata propertyMetadata) {
            this.order = order;
            this.propertyMetadata = propertyMetadata;
        }

        public float getOrder() {
            return order;
        }


        public PropertyMetadata getPropertyMetadata() {
            return propertyMetadata;
        }

        @Override
        public int compareTo(OrderedPropertyMetadata o) {
            return ComparatorUtils.naturalComparator().compare(getOrder(), o.getOrder());
        }

        @Override
        public Long id() {
            return propertyMetadata.id();
        }

        @Override
        public String name() {
            return propertyMetadata.name();
        }

        @Override
        public String tag() {
            return propertyMetadata.tag();
        }

        @Override
        public String description() {
            return propertyMetadata.description();
        }

        @Override
        public Boolean keyed() {
            return propertyMetadata.keyed();
        }

        @Override
        public Integer bizType() {
            return propertyMetadata.bizType();
        }

        @Override
        public PropertyValueType valueType() {
            return propertyMetadata.valueType();
        }

        @Override
        public PropertyStructureType structureType() {
            return propertyMetadata.structureType();
        }

        @Override
        public Input input() {
            return propertyMetadata.input();
        }

        @Override
        public ValuesSource valuesSource() {
            return propertyMetadata.valuesSource();
        }

        @Override
        public List<Constraint> constraints() {
            return propertyMetadata.constraints();
        }

        @Override
        public Long refId() {
            return propertyMetadata.refId();
        }
    }
}
