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
package io.scleropages.sentarum.item.category.entity.mapper;

import io.scleropages.sentarum.item.category.entity.MarketingCategoryEntity;
import io.scleropages.sentarum.item.category.entity.StandardCategoryLinkEntity;
import io.scleropages.sentarum.item.category.model.StandardCategoryLink;
import io.scleropages.sentarum.item.category.model.impl.MarketingCategoryModel;
import org.mapstruct.Mapper;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface MarketingCategoryEntityMapper extends AbstractCategoryEntityMapper<MarketingCategoryEntity, MarketingCategoryModel> {

    default StandardCategoryLinkEntity toStandardCategoryLinkEntity(StandardCategoryLink model) {
        return null;
    }

    default StandardCategoryLink toStandardCategoryLink(StandardCategoryLinkEntity entity) {
        if (!isEntityInitialized(entity))
            return null;
        StandardCategoryLinkEntityMapper mapper = (StandardCategoryLinkEntityMapper) ModelMapperRepository.getRequiredModelMapper(StandardCategoryLinkEntityMapper.class);
        return mapper.mapForRead(entity);
    }

    default List<StandardCategoryLink> standardCategoryLinkEntityListToStandardCategoryLinkList(List<StandardCategoryLinkEntity> list) {
        if (!isEntityInitialized(list))
            return null;
        List<StandardCategoryLink> list1 = new ArrayList<StandardCategoryLink>(list.size());
        for (StandardCategoryLinkEntity standardCategoryLinkEntity : list) {
            list1.add(toStandardCategoryLink(standardCategoryLinkEntity));
        }

        return list1;
    }
}
