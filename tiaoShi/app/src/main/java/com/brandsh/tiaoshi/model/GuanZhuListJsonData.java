package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by apple on 16/2/22.
 */
public class GuanZhuListJsonData {


    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"limit":15,"prePage":0,"start":0,"currentPage":1,"nextPage":2,"totalCount":"2","list":[{"shopId":"60","name":"呵呵水果铺","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1464164537717.jpg","goodsCount":"11","minBuy":"0","maxSend":"6","freeSend":"55","address":"上海市宝山区复旦软件园(高境)","addressDetail":"1b04","salesStatus":"1","distance":"23.24","star":"0","totalMonthSales":"0","shopPreferential":[{"subtract":"10","full":"90"}]},{"shopId":"61","name":"小宝家","icon":"","goodsCount":"0","minBuy":"0","maxSend":"10","freeSend":"400","address":"长寿路常德路(公交站) 叶家宅路500号","addressDetail":"长寿路常德路(公交站) 叶家宅路500号","salesStatus":"1","distance":"9885.0864","star":"0","totalMonthSales":"0","shopPreferential":[]}]}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * limit : 15
     * prePage : 0
     * start : 0
     * currentPage : 1
     * nextPage : 2
     * totalCount : 2
     * list : [{"shopId":"60","name":"呵呵水果铺","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1464164537717.jpg","goodsCount":"11","minBuy":"0","maxSend":"6","freeSend":"55","address":"上海市宝山区复旦软件园(高境)","addressDetail":"1b04","salesStatus":"1","distance":"23.24","star":"0","totalMonthSales":"0","shopPreferential":[{"subtract":"10","full":"90"}]},{"shopId":"61","name":"小宝家","icon":"","goodsCount":"0","minBuy":"0","maxSend":"10","freeSend":"400","address":"长寿路常德路(公交站) 叶家宅路500号","addressDetail":"长寿路常德路(公交站) 叶家宅路500号","salesStatus":"1","distance":"9885.0864","star":"0","totalMonthSales":"0","shopPreferential":[]}]
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
        private int limit;
        private int prePage;
        private int start;
        private int currentPage;
        private int nextPage;
        private String totalCount;
        /**
         * shopId : 60
         * name : 呵呵水果铺
         * icon : http://7xn4ae.com1.z0.glb.clouddn.com/1464164537717.jpg
         * goodsCount : 11
         * minBuy : 0
         * maxSend : 6
         * freeSend : 55
         * address : 上海市宝山区复旦软件园(高境)
         * addressDetail : 1b04
         * salesStatus : 1
         * distance : 23.24
         * star : 0
         * totalMonthSales : 0
         * shopPreferential : [{"subtract":"10","full":"90"}]
         */

        private List<ListBean> list;

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String shopId;
            private String name;
            private String icon;
            private String goodsCount;
            private String minBuy;
            private String maxSend;
            private String freeSend;
            private String address;
            private String addressDetail;
            private String salesStatus;
            private String distance;
            private String star;
            private String totalMonthSales;
            /**
             * subtract : 10
             * full : 90
             */

            private List<ShopPreferentialBean> shopPreferential;

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

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

            public String getGoodsCount() {
                return goodsCount;
            }

            public void setGoodsCount(String goodsCount) {
                this.goodsCount = goodsCount;
            }

            public String getMinBuy() {
                return minBuy;
            }

            public void setMinBuy(String minBuy) {
                this.minBuy = minBuy;
            }

            public String getMaxSend() {
                return maxSend;
            }

            public void setMaxSend(String maxSend) {
                this.maxSend = maxSend;
            }

            public String getFreeSend() {
                return freeSend;
            }

            public void setFreeSend(String freeSend) {
                this.freeSend = freeSend;
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

            public String getSalesStatus() {
                return salesStatus;
            }

            public void setSalesStatus(String salesStatus) {
                this.salesStatus = salesStatus;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getStar() {
                return star;
            }

            public void setStar(String star) {
                this.star = star;
            }

            public String getTotalMonthSales() {
                return totalMonthSales;
            }

            public void setTotalMonthSales(String totalMonthSales) {
                this.totalMonthSales = totalMonthSales;
            }

            public List<ShopPreferentialBean> getShopPreferential() {
                return shopPreferential;
            }

            public void setShopPreferential(List<ShopPreferentialBean> shopPreferential) {
                this.shopPreferential = shopPreferential;
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
}
