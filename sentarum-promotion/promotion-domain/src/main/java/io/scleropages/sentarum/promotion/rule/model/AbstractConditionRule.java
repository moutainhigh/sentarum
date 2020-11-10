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
package io.scleropages.sentarum.promotion.rule.model;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class AbstractConditionRule extends AbstractRule implements ConditionRule {


    private ConditionConjunction conditionConjunction;
    private List<ConditionRule> conditions;

    public ConditionConjunction getConditionConjunction() {
        return conditionConjunction;
    }

    public List<ConditionRule> getConditions() {
        if (null == conditions)
            conditions = Lists.newArrayList();
        return conditions;
    }

    public void setConditionConjunction(ConditionConjunction conditionConjunction) {
        this.conditionConjunction = conditionConjunction;
    }

    public void setConditions(List<ConditionRule> conditions) {
        this.conditions = conditions;
    }

    @Override
    public ConditionConjunction conditionConjunction() {
        return getConditionConjunction();
    }

    @Override
    public void addCondition(ConditionRule conditionRule) {
        conditions().add(conditionRule);
    }

    @Override
    public List<ConditionRule> conditions() {
        return getConditions();
    }
}
