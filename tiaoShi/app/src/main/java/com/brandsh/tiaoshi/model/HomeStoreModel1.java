package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by XianXianGe on 2016/1/7.
 */
public class HomeStoreModel1 {


    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"limit":15,"prePage":0,"start":0,"currentPage":1,"nextPage":2,"totalCount":"1","list":[{"shopId":"60","name":"平价水果店","icon":"","top":"0","goodsCount":"1","minBuy":"0","maxSend":"10","freeSend":"400","salesStatus":"1","distance":"23.8431","star":"0","totalMonthSales":"0","isSend":"true","shopPreferential":[{"subtract":"10","full":"90"}]},{"shopId":"62","name":"挑食网","icon":"","top":"0","goodsCount":"0","minBuy":"0","maxSend":"10","freeSend":"400","salesStatus":"1","distance":"23.8431","star":"0","totalMonthSales":"0","isSend":"true","shopPreferential":[]}]}
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
     * totalCount : 1
     * list : [{"shopId":"60","name":"平价水果店","icon":"","top":"0","goodsCount":"1","minBuy":"0","maxSend":"10","freeSend":"400","salesStatus":"1","distance":"23.8431","star":"0","totalMonthSales":"0","isSend":"true","shopPreferential":[{"subtract":"10","full":"90"}]},{"shopId":"62","name":"挑食网","icon":"","top":"0","goodsCount":"0","minBuy":"0","maxSend":"10","freeSend":"400","salesStatus":"1","distance":"23.8431","star":"0","totalMonthSales":"0","isSend":"true","shopPreferential":[]}]
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
         * name : 平价水果店
         * icon :
         * top : 0
         * goodsCount : 1
         * minBuy : 0
         * maxSend : 10
         * freeSend : 400
         * salesStatus : 1
         * distance : 23.8431
         * star : 0
         * totalMonthSales : 0
         * isSend : true
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
            private String top;
            private String goodsCount;
            private String minBuy;
            private String maxSend;
            private String freeSend;
            private String salesStatus;
            private String distance;
            private String star;
            private String totalMonthSales;
            private String isSend;
            private String balancePay;
            private int goods_sc_count;

            public String getBalancePay() {
                return balancePay;
            }

            public void setBalancePay(String balancePay) {
                this.balancePay = balancePay;
            }

            public int getGoods_sc_count() {
                return goods_sc_count;
            }

            public void setGoods_sc_count(int goods_sc_count) {
                this.goods_sc_count = goods_sc_count;
            }

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

            public String getTop() {
                return top;
            }

            public void setTop(String top) {
                this.top = top;
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

            public String getIsSend() {
                return isSend;
            }

            public void setIsSend(String isSend) {
                this.isSend = isSend;
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
