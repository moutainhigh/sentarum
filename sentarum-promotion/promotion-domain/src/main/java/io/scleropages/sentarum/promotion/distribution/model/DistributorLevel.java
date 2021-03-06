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
package io.scleropages.sentarum.promotion.distribution.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 分销人等级，分销人在不同分销体系可处于不同的分销等级
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface DistributorLevel {


    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * level of distributor
     *
     * @return
     */
    DistributionLevel level();


    /**
     * associated distributor hierarchy
     *
     * @return
     */
    DistributionHierarchy distributionHierarchy();


    /**
     * parent distributor of this.
     *
     * @return
     */
    Distributor parent();



    /**
     * total amount of this distributor in current level
     *
     * @return
     */
    BigDecimal totalAmount();


    /**
     * add given amount to {@link #totalAmount()}
     */
    void addTotalAmount(BigDecimal amount);


    /**
     * 最近一次消费时间
     *
     * @return
     */
    Date recencyTime();

    /**
     * 最近一次消费金额
     *
     * @return
     */
    BigDecimal recencyAmount();
}
