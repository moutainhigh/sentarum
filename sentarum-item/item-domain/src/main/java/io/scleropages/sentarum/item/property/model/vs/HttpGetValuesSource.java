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
package io.scleropages.sentarum.item.property.model.vs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * 通过HTTP GET 请求获取服务提供者数据集，本地不进行缓存.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class HttpGetValuesSource extends AbstractValuesSource {

    public HttpGetValuesSource() {
    }

    public HttpGetValuesSource(Long id) {
        super(id);
    }

    public HttpGetValuesSource(String name, String tag, String desc) {
        super(name, tag, desc);
    }

    private String url;

    private Map<String, Object> httpHeaders;


    @Override
    public ValuesSourceType valuesSourceType() {
        return ValuesSourceType.HTTP_GET_VALUES_SOURCE;
    }

    @Override
    public Page<? extends SourceValue> readValues(SourceValue search, Pageable pageable) {
        return null;
    }

    /**
     * return http get url.
     *
     * @return
     */
    @NotEmpty(groups = Create.class)
    public String getUrl() {
        return url;
    }

    /**
     * set http get url.
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * return http headers
     *
     * @return
     */
    public Map<String, Object> getHttpHeaders() {
        return httpHeaders;
    }

    /**
     * set http headers.
     *
     * @param httpHeaders
     */
    public void setHttpHeaders(Map<String, Object> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }
}
