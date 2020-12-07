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
package io.scleropages.sentarum.promotion.rule.context;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.rule.model.condition.ChannelConditionRule.ChannelType;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * builder of promotion context(s). make a easy way to create promotion context.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public final class PromotionContextBuilder {

    private ChannelType channelType;
    private Integer channelId;
    private Long buyerId;
    private List<Activity> activities = Lists.newArrayList();
    private Map<String, OrderPromotionContextBuilder> orderPromotionContextBuilders = Maps.newHashMap();

    private PromotionContextBuilder() {
    }

    /**
     * create new builder instance based cart.
     *
     * @return
     */
    public static PromotionContextBuilder newBuilder() {
        return new PromotionContextBuilder();
    }


    /**
     * with channelType to this builder.
     *
     * @param channelType
     * @return
     */
    public PromotionContextBuilder withChannelType(ChannelType channelType) {
        this.channelType = channelType;
        return this;
    }

    /**
     * with channelId to this builder.
     *
     * @param channelId
     * @return
     */
    public PromotionContextBuilder withChannelId(Integer channelId) {
        this.channelId = channelId;
        return this;
    }

    /**
     * add activity to this builder.
     *
     * @param activity
     * @return
     */
    public PromotionContextBuilder withActivity(Activity activity) {
        this.activities.add(activity);
        return this;
    }

    /**
     * with buyerId to this builder.
     *
     * @param buyerId
     * @return
     */
    public PromotionContextBuilder withBuyerId(Long buyerId) {
        this.buyerId = buyerId;
        return this;
    }

    /**
     * add if absent builder of {@link OrderPromotionContext} to this builder.
     *
     * @return associated {@link OrderPromotionContextBuilder}
     */
    public OrderPromotionContextBuilder orderPromotionContextBuilder(Long sellerUnionId, Long sellerId) {
        String key = computeOrderPromotionContextKey(sellerUnionId, sellerId);
        return orderPromotionContextBuilders.computeIfAbsent(key, s -> {
            OrderPromotionContextBuilder orderPromotionContextBuilder = new OrderPromotionContextBuilder(this);
            orderPromotionContextBuilder.withSellerUnionId(sellerUnionId);
            orderPromotionContextBuilder.withSellerId(sellerId);
            return orderPromotionContextBuilder;
        });
    }

    /**
     * add builder of {@link OrderPromotionContext} to this builder.
     *
     * @return
     */
    public OrderPromotionContextBuilder orderPromotionContextBuilder() {
        OrderPromotionContextBuilder orderPromotionContextBuilder = new OrderPromotionContextBuilder(this);
        this.orderPromotionContextBuilders.put(String.valueOf(orderPromotionContextBuilders.size()), orderPromotionContextBuilder);
        return orderPromotionContextBuilder;
    }

    private String computeOrderPromotionContextKey(Long sellerUnionId, Long sellerId) {
        return sellerUnionId + "_" + sellerId;
    }

    /**
     * build {@link CartPromotionContext} from this builder.
     *
     * @return
     */
    public CartPromotionContext build() {
        Assert.notNull(channelType, "channelType must not be null.");
        Assert.notNull(channelId, "channelId must not be null.");
        Assert.notNull(buyerId, "buyerId must not be null.");
        Assert.notEmpty(activities, "no activities specified.");

        List<OrderPromotionContext> orderPromotionContexts = Lists.newArrayList();
        CartPromotionContextImpl cartPromotionContext = new CartPromotionContextImpl(channelType, channelId, buyerId, orderPromotionContexts);
        cartPromotionContext.setActivities(activities);
        orderPromotionContextBuilders.values().forEach(orderPromotionContextBuilder -> {
            List<GoodsPromotionContext> goodsPromotionContexts = Lists.newArrayList();
            OrderPromotionContext orderPromotionContext = orderPromotionContextBuilder.build(cartPromotionContext, goodsPromotionContexts);
            orderPromotionContexts.add(orderPromotionContext);
            orderPromotionContextBuilder.goodsPromotionContextBuilders.forEach(goodsPromotionContextBuilder -> {
                goodsPromotionContextBuilder.build(cartPromotionContext, orderPromotionContext);
            });
        });
        return cartPromotionContext;
    }


    /**
     * inner builder of {@link OrderPromotionContext}
     */
    public final static class OrderPromotionContextBuilder {

        private Long sellerUnionId;
        private Long sellerId;
        private List<Activity> activities = Lists.newArrayList();

        private List<GoodsPromotionContextBuilder> goodsPromotionContextBuilders = Lists.newArrayList();

        private final PromotionContextBuilder promotionContextBuilder;


        private OrderPromotionContextBuilder(PromotionContextBuilder promotionContextBuilder) {
            this.promotionContextBuilder = promotionContextBuilder;
        }

        /**
         * with sellerUnionId to this builder.
         *
         * @param sellerUnionId
         * @return
         */
        public OrderPromotionContextBuilder withSellerUnionId(Long sellerUnionId) {
            this.sellerUnionId = sellerUnionId;
            return this;
        }

        /**
         * with sellerId to this builder.
         *
         * @param sellerId
         * @return
         */
        public OrderPromotionContextBuilder withSellerId(Long sellerId) {
            this.sellerId = sellerId;
            return this;
        }

        /**
         * add activity to this builder.
         *
         * @param activity
         * @return
         */
        public OrderPromotionContextBuilder withActivity(Activity activity) {
            this.activities.add(activity);
            return this;
        }

        /**
         * add builder of {@link GoodsPromotionContext} to this builder.
         *
         * @return
         */
        public GoodsPromotionContextBuilder goodsPromotionContextBuilder() {
            GoodsPromotionContextBuilder goodsPromotionContextBuilder = new GoodsPromotionContextBuilder(this);
            this.goodsPromotionContextBuilders.add(goodsPromotionContextBuilder);
            return goodsPromotionContextBuilder;
        }

        /**
         * internal build implements.
         *
         * @param cartPromotionContext
         * @param goodsPromotionContexts
         * @return
         */
        private OrderPromotionContext build(CartPromotionContext cartPromotionContext, List<GoodsPromotionContext> goodsPromotionContexts) {
            done();
            OrderPromotionContextImpl orderPromotionContext = new OrderPromotionContextImpl(cartPromotionContext, sellerUnionId, sellerId, goodsPromotionContexts);
            orderPromotionContext.setActivities(activities);
            return orderPromotionContext;
        }

        /**
         * done for this builder. and return parent builder.
         *
         * @return
         */
        public PromotionContextBuilder done() {
            Assert.notNull(sellerUnionId, "sellerUnionId must not be null.");
            Assert.notNull(sellerId, "sellerId must not be null.");
            Assert.notEmpty(activities, "no activities specified.");
            return promotionContextBuilder;
        }

    }

    /**
     * inner builder of {@link GoodsPromotionContext}
     */
    public static final class GoodsPromotionContextBuilder {

        private Long goodsId;
        private String outerGoodsId;
        private Long specsId;
        private String outerSpecsId;
        private Integer num;
        private List<Activity> activities = Lists.newArrayList();

        private final OrderPromotionContextBuilder orderPromotionContextBuilder;

        private GoodsPromotionContextBuilder(OrderPromotionContextBuilder orderPromotionContextBuilder) {
            this.orderPromotionContextBuilder = orderPromotionContextBuilder;
        }

        /**
         * with goodsId to this builder.
         *
         * @param goodsId
         * @return
         */
        public GoodsPromotionContextBuilder withGoodsId(Long goodsId) {
            this.goodsId = goodsId;
            return this;
        }

        /**
         * with outerGoodsId to this builder.
         *
         * @param outerGoodsId
         * @return
         */
        public GoodsPromotionContextBuilder withOuterGoodsId(String outerGoodsId) {
            this.outerGoodsId = outerGoodsId;
            return this;
        }

        /**
         * with specsId to this builder.
         *
         * @param specsId
         * @return
         */
        public GoodsPromotionContextBuilder withSpecsId(Long specsId) {
            this.specsId = specsId;
            return this;
        }

        /**
         * with outerSpecsId to this builder.
         *
         * @param outerSpecsId
         * @return
         */
        public GoodsPromotionContextBuilder withOuterSpecsId(String outerSpecsId) {
            this.outerSpecsId = outerSpecsId;
            return this;
        }

        /**
         * with num to this builder.
         *
         * @param num
         * @return
         */
        public GoodsPromotionContextBuilder withNum(Integer num) {
            this.num = num;
            return this;
        }

        /**
         * add activity to this builder.
         *
         * @param activity
         * @return
         */
        public GoodsPromotionContextBuilder withActivity(Activity activity) {
            this.activities.add(activity);
            return this;
        }

        /**
         * internal build implements.
         *
         * @param cartPromotionContext
         * @param orderPromotionContext
         * @return
         */
        private GoodsPromotionContext build(CartPromotionContext cartPromotionContext, OrderPromotionContext orderPromotionContext) {
            done();
            GoodsPromotionContextImpl goodsPromotionContext = new GoodsPromotionContextImpl(cartPromotionContext, orderPromotionContext, goodsId, outerGoodsId, specsId, outerSpecsId, num);
            goodsPromotionContext.setActivities(activities);
            return goodsPromotionContext;
        }

        /**
         * done for this builder. and return parent builder.
         *
         * @return
         */
        public OrderPromotionContextBuilder done() {
            Assert.notNull(goodsId, "goodsId must not be null.");
            Assert.notNull(num, "num must not be null.");
            Assert.notNull(specsId, "specsId must not be null.");
            Assert.notNull(activities, "no activities specified.");
            orderPromotionContextBuilder.goodsPromotionContextBuilders.forEach(goodsPromotionContextBuilder -> {
                if (!Objects.equals(this, goodsPromotionContextBuilder) && Objects.equals(goodsPromotionContextBuilder.specsId, specsId)) {
                    throw new IllegalArgumentException("specsId: " + specsId + "already exists");
                }
            });
            return orderPromotionContextBuilder;
        }

    }

}