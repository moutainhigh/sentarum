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
package io.scleropages.sentarum.promotion.activity.model.impl;

import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSpecs;
import io.scleropages.sentarum.promotion.goods.model.impl.AbstractGoodsSpecs;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ActivityGoodsSpecsModel extends AbstractGoodsSpecs implements ActivityGoodsSpecs {

    private Integer totalNum;
    private Integer userNum;
    private Activity activity;

    @NotNull(groups = AbstractGoodsSpecs.Create.class)
    public Integer getTotalNum() {
        return totalNum;
    }

    @NotNull(groups = AbstractGoodsSpecs.Create.class)
    public Integer getUserNum() {
        return userNum;
    }

    @Null
    public Activity getActivity() {
        return activity;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Integer totalNum() {
        return getTotalNum();
    }

    @Override
    public Integer userNum() {
        return getUserNum();
    }

    @Override
    public Activity activity() {
        return getActivity();
    }
}
