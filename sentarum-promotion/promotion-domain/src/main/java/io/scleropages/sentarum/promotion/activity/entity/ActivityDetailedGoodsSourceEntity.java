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
package io.scleropages.sentarum.promotion.activity.entity;

import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityDetailedGoodsSourceModel;
import io.scleropages.sentarum.promotion.goods.entity.DetailedGoodsSourceEntity;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource.DETAILED_GOODS_SOURCE_TYPE;
import static io.scleropages.sentarum.promotion.goods.entity.AbstractGoodsSourceEntity.COLUMN_BIZ_ID;
import static io.scleropages.sentarum.promotion.goods.entity.AbstractGoodsSourceEntity.COLUMN_BIZ_TYPE;

/**
 * referenced from: {@link ActivityDetailedGoodsSourceModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "prom_act_detailed_goods_source", uniqueConstraints = @UniqueConstraint(columnNames = {COLUMN_BIZ_TYPE, COLUMN_BIZ_ID}))
@SequenceGenerator(name = "prom_act_detailed_goods_source_id", sequenceName = "seq_prom_act_detailed_goods_source", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class ActivityDetailedGoodsSourceEntity extends DetailedGoodsSourceEntity {

    @Override
    @Column(name = COLUMN_BIZ_TYPE, nullable = false, length = 1)
    public Integer getBizType() {
        return ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY;
    }

    @Override
    @Column(name = COLUMN_GOODS_SOURCE_TYPE, nullable = false)
    public Integer getGoodsSourceType() {
        return DETAILED_GOODS_SOURCE_TYPE;
    }
}
