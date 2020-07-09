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
package io.scleropages.sentarum.item.model.property;


import com.google.common.collect.Lists;
import io.scleropages.sentarum.item.model.property.constraint.ConstraintDepends;
import io.scleropages.sentarum.item.model.property.constraint.Max;
import io.scleropages.sentarum.item.model.property.constraint.MaxLength;
import io.scleropages.sentarum.item.model.property.constraint.Min;
import io.scleropages.sentarum.item.model.property.constraint.MinLength;
import io.scleropages.sentarum.item.model.property.constraint.NotNull;
import io.scleropages.sentarum.item.model.property.impl.PropertyMetadataBean;
import io.scleropages.sentarum.item.model.property.input.InputText;
import junit.framework.TestCase;
import org.junit.Test;
import org.scleropages.core.mapper.JsonMapper2;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */

public class PropertyMetadataTests extends TestCase {


    @Test
    public void testFlatPropertyMetadata() {

        //create metadata
        PropertyMetadataBean address = new PropertyMetadataBean();
        address.setId(1l);
        address.setName("address");
        address.setTag("地址");
        address.setDescription("详细地址信息");
        address.setStructureType(PropertyMetadata.PropertyStructureType.FLAT_PROPERTY);
        address.setValueType(PropertyValueType.TEXT);
        address.setConstraints(Lists.newArrayList(new MinLength(11), new MaxLength(32), new NotNull()));
        address.setInput(new InputText());
        System.out.println(JsonMapper2.toJson(address));

        assertSame(NotNull.class, PropertyInputValidators.validate(address).getClass());

        InputText input = (InputText) address.getInput();
        input.setValue("印度尼西亚-加里曼丹");

        assertSame(MinLength.class, PropertyInputValidators.validate(address).getClass());

        input.setValue("印度尼西亚-加里曼丹-卡普瓦斯河上游-圣塔伦大湖-龙哥龙鱼繁殖场");

        assertNull(PropertyInputValidators.validate(address));

        input.setValue("印度尼西亚-加里曼丹-卡普瓦斯河上游-圣塔伦大湖-龙哥龙鱼繁殖场1");

        assertNotNull(PropertyInputValidators.validate(address));


        PropertyMetadataBean price = new PropertyMetadataBean();
        price.setId(2l);
        price.setName("price");
        price.setTag("价格");
        price.setDescription("商品价格");
        price.setStructureType(PropertyMetadata.PropertyStructureType.FLAT_PROPERTY);
        price.setValueType(PropertyValueType.DECIMAL);

        NotNull notNull = new NotNull();//设置依赖规则，address如果不为空，则price也不为空.
        ConstraintDepends constraintDepends = new ConstraintDepends();
        notNull.setConstraintDepends(constraintDepends);
        constraintDepends.setConjunction(ConstraintDepends.Conjunction.AND);
        constraintDepends.addConstraintDepend(new ConstraintDepends.ConstraintDepend(address.getName(), ConstraintDepends.Operator.NOT_NULL));


        price.setConstraints(Lists.newArrayList(notNull, new Min(0L), new Max(9999L)));
        price.setInput(new InputText());
        System.out.println(JsonMapper2.toJson(price));

    }


    public void testHierarchyPropertyMetadata() {

        PropertyMetadataBean brandName = new PropertyMetadataBean();
        brandName.setId(1L);
        brandName.setName("brandName");
        brandName.setTag("品牌名称");
        brandName.setDescription("品牌名称作为品类的一个关键属性");
        brandName.setStructureType(PropertyMetadata.PropertyStructureType.HIERARCHY_NODE_PROPERTY);
        brandName.setValueType(PropertyValueType.PROPERTY_REF);

        PropertyMetadataBean seriesName = new PropertyMetadataBean();
        seriesName.setId(2L);
        seriesName.setName("seriesName");
        seriesName.setTag("系列名称");
        seriesName.setDescription("品牌系列作为品类的一个关键属性，属于品牌，二级属性");
        seriesName.setStructureType(PropertyMetadata.PropertyStructureType.HIERARCHY_NODE_PROPERTY);
        brandName.setValueType(PropertyValueType.PROPERTY_REF);


        PropertyMetadataBean modelName = new PropertyMetadataBean();
        modelName.setName("modelName");
        brandName.setTag("型号名称");
        brandName.setStructureType(PropertyMetadata.PropertyStructureType.HIERARCHY_LEAF_PROPERTY);

    }

}
