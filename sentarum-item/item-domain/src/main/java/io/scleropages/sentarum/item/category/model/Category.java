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
package io.scleropages.sentarum.item.category.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述品类（类目）业务模型.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Category extends Serializable {


    enum Status {
        VALID(1, "可用"), INVALID(2, "不可用");

        private final int ordinal;
        private final String tag;

        Status(int ordinal, String tag) {
            this.ordinal = ordinal;
            this.tag = tag;
        }


        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        private static final Map<String, Status> nameMappings = new HashMap<>();
        private static final Map<Integer, Status> ordinalMappings = new HashMap<>();

        static {
            for (Status status : Status.values()) {
                nameMappings.put(status.name(), status);
                ordinalMappings.put(status.getOrdinal(), status);
            }
        }



        public static Status getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static Status getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 类目名称
     *
     * @return
     */
    String name();

    /**
     * 类目显示名
     *
     * @return
     */
    String tag();

    /**
     * 类目描述
     *
     * @return
     */
    String description();


    /**
     * 状态
     *
     * @return
     */
    Status status();


    /**
     * 上级类目
     *
     * @return
     */
    Category parentCategory();


    /**
     * 关联的一组子类目
     *
     * @return
     */
    List<? extends Category> childCategories();


    /**
     * 扩展属性
     *
     * @return
     */
    Map<String, Object> additionalAttributes();


    interface Create {
    }

    interface Update {
    }

}
