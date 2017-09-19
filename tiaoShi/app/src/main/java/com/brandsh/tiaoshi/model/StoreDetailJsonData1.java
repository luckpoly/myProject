package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by apple on 16/2/21.
 */
public class StoreDetailJsonData1 {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"name":"张华水果店","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1471867227155.jpg","minBuy":"10","address":"上海市宝山区复旦软件园(高境)","addressDetail":"1001","lng":"121.488028","lat":"31.31258","serviceTel":"18300709011","freeSend":"33","star":"0","orderTotal":"","maxSend":"3","salesStatus":"OPEN","openTime":"08:00","closeTime":"22:00","distance":"15","description":"张华水果店","shopPreferential":[{"subtract":"10","full":"100"}],"followStatus":"no","deliveryPriceConf":{"weight":"3","weightMin":"1000","weightSpacing":"1","weightPrice":"0","weightMax":"100000","distance":"1","distanceMin":"3","distanceSpacing":"1","distancePrice":"1","distanceMax":"30"}}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * name : 张华水果店
     * icon : http://7xn4ae.com1.z0.glb.clouddn.com/1471867227155.jpg
     * minBuy : 10
     * address : 上海市宝山区复旦软件园(高境)
     * addressDetail : 1001
     * lng : 121.488028
     * lat : 31.31258
     * serviceTel : 18300709011
     * freeSend : 33
     * star : 0
     * orderTotal :
     * maxSend : 3
     * salesStatus : OPEN
     * openTime : 08:00
     * closeTime : 22:00
     * distance : 15
     * description : 张华水果店
     * shopPreferential : [{"subtract":"10","full":"100"}]
     * followStatus : no
     * deliveryPriceConf : {"weight":"3","weightMin":"1000","weightSpacing":"1","weightPrice":"0","weightMax":"100000","distance":"1","distanceMin":"3","distanceSpacing":"1","distancePrice":"1","distanceMax":"30"}
     */

    private DataBean data;
    private String debugInfo;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public static class DataBean {
        private String name;
        private String icon;
        private String minBuy;
        private String address;
        private String addressDetail;
        private String lng;
        private String lat;
        private String serviceTel;
        private String freeSend;
        private String star;
        private String orderTotal;
        private String maxSend;
        private String salesStatus;
        private String openTime;
        private String closeTime;
        private String distance;
        private String description;
        private String followStatus;
        /**
         * weight : 3
         * weightMin : 1000
         * weightSpacing : 1
         * weightPrice : 0
         * weightMax : 100000
         * distance : 1
         * distanceMin : 3
         * distanceSpacing : 1
         * distancePrice : 1
         * distanceMax : 30
         */

        private DeliveryPriceConfBean deliveryPriceConf;
        /**
         * subtract : 10
         * full : 100
         */

        private List<ShopPreferentialBean> shopPreferential;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getMinBuy() {
            return minBuy;
        }

        public void setMinBuy(String minBuy) {
            this.minBuy = minBuy;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getServiceTel() {
            return serviceTel;
        }

        public void setServiceTel(String serviceTel) {
            this.serviceTel = serviceTel;
        }

        public String getFreeSend() {
            return freeSend;
        }

        public void setFreeSend(String freeSend) {
            this.freeSend = freeSend;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getOrderTotal() {
            return orderTotal;
        }

        public void setOrderTotal(String orderTotal) {
            this.orderTotal = orderTotal;
        }

        public String getMaxSend() {
            return maxSend;
        }

        public void setMaxSend(String maxSend) {
            this.maxSend = maxSend;
        }

        public String getSalesStatus() {
            return salesStatus;
        }

        public void setSalesStatus(String salesStatus) {
            this.salesStatus = salesStatus;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getCloseTime() {
            return closeTime;
        }

        public void setCloseTime(String closeTime) {
            this.closeTime = closeTime;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFollowStatus() {
            return followStatus;
        }

        public void setFollowStatus(String followStatus) {
            this.followStatus = followStatus;
        }

        public DeliveryPriceConfBean getDeliveryPriceConf() {
            return deliveryPriceConf;
        }

        public void setDeliveryPriceConf(DeliveryPriceConfBean deliveryPriceConf) {
            this.deliveryPriceConf = deliveryPriceConf;
        }

        public List<ShopPreferentialBean> getShopPreferential() {
            return shopPreferential;
        }

        public void setShopPreferential(List<ShopPreferentialBean> shopPreferential) {
            this.shopPreferential = shopPreferential;
        }

        public static class DeliveryPriceConfBean {
            private String weight;
            private String weightMin;
            private String weightSpacing;
            private String weightPrice;
            private String weightMax;
            private String distance;
            private String distanceMin;
            private String distanceSpacing;
            private String distancePrice;
            private String distanceMax;

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getWeightMin() {
                return weightMin;
            }

            public void setWeightMin(String weightMin) {
                this.weightMin = weightMin;
            }

            public String getWeightSpacing() {
                return weightSpacing;
            }

            public void setWeightSpacing(String weightSpacing) {
                this.weightSpacing = weightSpacing;
            }

            public String getWeightPrice() {
                return weightPrice;
            }

            public void setWeightPrice(String weightPrice) {
                this.weightPrice = weightPrice;
            }

            public String getWeightMax() {
                return weightMax;
            }

            public void setWeightMax(String weightMax) {
                this.weightMax = weightMax;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getDistanceMin() {
                return distanceMin;
            }

            public void setDistanceMin(String distanceMin) {
                this.distanceMin = distanceMin;
            }

            public String getDistanceSpacing() {
                return distanceSpacing;
            }

            public void setDistanceSpacing(String distanceSpacing) {
                this.distanceSpacing = distanceSpacing;
            }

            public String getDistancePrice() {
                return distancePrice;
            }

            public void setDistancePrice(String distancePrice) {
                this.distancePrice = distancePrice;
            }

            public String getDistanceMax() {
                return distanceMax;
            }

            public void setDistanceMax(String distanceMax) {
                this.distanceMax = distanceMax;
            }
        }

        public static class ShopPreferentialBean {
            private String subtract;
            private String full;

            public String getSubtract() {
                return subtract;
            }

            public void setSubtract(String subtract) {
                this.subtract = subtract;
            }

            public String getFull() {
                return full;
            }

            public void setFull(String full) {
                this.full = full;
            }
        }
    }
}
