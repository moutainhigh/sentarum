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
package io.scleropages.sentarum.core.entity.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Embeddable
public class Amount {

    private BigDecimal amount;
    private String currencyCode;

    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @Transient
    //当前无需支持货币化表示
    //@Column(name = "amount_currency")
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
