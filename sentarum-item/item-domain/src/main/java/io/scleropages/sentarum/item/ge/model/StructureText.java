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
package io.scleropages.sentarum.item.ge.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 结构化文本
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class StructureText implements Serializable {

    private Long id;

    private String text;

    private MediaType mediaType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public enum MediaType {

        JSON(1, "JSON"), XML(2, "XML"), CSV(3, "CSV, comma delimited text");


        private final int ordinal;
        private final String tag;

        MediaType(int ordinal, String tag) {
            this.ordinal = ordinal;
            this.tag = tag;
        }


        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        private static final Map<String, MediaType> nameMappings = new HashMap<>();
        private static final Map<Integer, MediaType> ordinalMappings = new HashMap<>();

        static {
            for (MediaType mediaType : MediaType.values()) {
                nameMappings.put(mediaType.name(), mediaType);
                ordinalMappings.put(mediaType.getOrdinal(), mediaType);
            }
        }


        public static MediaType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static MediaType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }

    }
}
