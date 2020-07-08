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
package io.scleropages.sentarum.item.model.property.constraint;

import io.scleropages.sentarum.item.model.property.Constraint;
import io.scleropages.sentarum.item.model.property.PropertyMetadata;
import io.scleropages.sentarum.item.model.property.input.MultiInput;
import io.scleropages.sentarum.item.model.property.input.SingleInput;

import java.util.regex.Pattern;

/**
 * 正则表达式约束
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class Regex extends Constraint {

    private String regex;

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    protected boolean validate(PropertyMetadata propertyMetadata, SingleInput input) {
        Pattern compile = Pattern.compile(regex);

        return false;
    }

    @Override
    protected boolean validate(PropertyMetadata propertyMetadata,MultiInput input) {
        return false;
    }
}
