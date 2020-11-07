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
package io.scleropages.sentarum.promotion.rule.promotion;

import io.scleropages.sentarum.promotion.rule.PromotionEvaluator;
import io.scleropages.sentarum.promotion.rule.context.OrderPromotionContext;
import io.scleropages.sentarum.promotion.rule.model.Rule;

/**
 * 订单级促销计算(整单优惠)已与特定商业主体关联.
 * 其处于整个促销计算第二级别.但实际执行顺序应对订单级促销结果进行兜底.即计算完商品级促销规则后合并计算order级别优惠.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface OrderEvaluator<R extends Rule> extends PromotionEvaluator<R, OrderPromotionContext> {

}