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
package io.scleropages.sentarum.item.model.impl;

import io.scleropages.sentarum.item.model.CombineSkuEntry;
import io.scleropages.sentarum.item.model.Sku;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class CombineSkuEntryModel implements CombineSkuEntry {

    private Long id;
    private Sku sku;
    private Float order;
    private Integer num;
    private BigDecimal salesPrice;
    private Map<String, Object> additionalAttributes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public Float getOrder() {
        return order;
    }

    public void setOrder(Float order) {
        this.order = order;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Map<String, Object> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Sku sku() {
        return getSku();
    }

    @Override
    public Float order() {
        return getOrder();
    }

    @Override
    public Integer num() {
        return getNum();
    }

    @Override
    public BigDecimal salesPrice() {
        return getSalesPrice();
    }

    @Override
    public Map<String, Object> additionalAttributes() {
        return getAdditionalAttributes();
    }
}
