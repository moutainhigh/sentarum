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
package io.scleropages.sentarum.promotion.rule.entity.calculator.mapper;

import io.scleropages.sentarum.promotion.rule.entity.mapper.AbstractRuleEntityMapper;
import io.scleropages.sentarum.promotion.rule.entity.calculator.OverflowDiscountRuleEntity;
import io.scleropages.sentarum.promotion.rule.model.calculator.OverflowDiscountRule;
import org.mapstruct.Mapper;
import org.scleropages.crud.ModelMapper;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface OverflowDiscountRuleEntityMapper extends AbstractRuleEntityMapper<OverflowDiscountRuleEntity, OverflowDiscountRule> {


    default Integer toOrdinal(OverflowDiscountRule.OverflowDiscountType overflowDiscountType) {
        return overflowDiscountType.getOrdinal();
    }

    default OverflowDiscountRule.OverflowDiscountType toStatus(Integer ordinal) {
        return OverflowDiscountRule.OverflowDiscountType.getByOrdinal(ordinal);
    }

}
