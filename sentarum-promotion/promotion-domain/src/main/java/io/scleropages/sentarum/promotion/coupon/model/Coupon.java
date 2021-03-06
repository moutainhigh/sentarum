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
package io.scleropages.sentarum.promotion.coupon.model;

import io.scleropages.sentarum.promotion.activity.model.Activity;

import java.util.Date;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Coupon {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * id of {@link CouponFactory}
     *
     * @return
     */
    CouponFactory couponFactory();

    /**
     * 持有人id
     *
     * @return
     */
    Long holderId();

    /**
     * 优惠券状态
     *
     * @return
     */
    Status status();


    /**
     * 券关联的活动.
     *
     * @return
     */
    Activity activity();


    /**
     * 创建时间
     *
     * @return
     */
    Date createTime();


    /**
     * 券状态
     */
    enum Status {

    }
}
