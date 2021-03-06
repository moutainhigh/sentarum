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
package io.scleropages.sentarum.promotion.rule.context;

import io.scleropages.sentarum.core.model.primitive.Amount;

/**
 * 商品级促销上下文
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface GoodsPromotionContext extends PromotionContext {


    /**
     * 商家唯一标识（商业综合体).
     *
     * @return
     */
    Long sellerUnionId();

    /**
     * 商家唯一标识(单店).
     *
     * @return
     */
    Long sellerId();


    /**
     * 商品唯一标识
     *
     * @return
     */
    Long goodsId();

    /**
     * 外部商品唯一标识
     *
     * @return
     */
    String outerGoodsId();

    /**
     * 规格唯一标识
     *
     * @return
     */
    Long specsId();

    /**
     * 外部规格唯一标识
     *
     * @return
     */
    String outerSpecsId();

    /**
     * 购买数量
     *
     * @return
     */
    Integer num();

    /**
     * 原价
     *
     * @return
     */
    Amount originalPrice();
}
