package com.brandsh.tiaoshi.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sisi on 16/3/10.
 */
public class OrderListJsondata1 implements Serializable{


    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"limit":10,"prePage":0,"start":0,"currentPage":1,"nextPage":2,"totalCount":"3","list":[{"orderId":269,"orderCode":"16052711001502010063","deliveryWay":"SELF","userDeliveryPrice":0,"shopId":60,"shopName":"呵呵水果铺","total":210,"status":"OK","createTime":1464318015,"freeSend":"55","orderGoods":[{"goodsId":625,"goodsName":"榴莲","goodsCount":4,"promotePrice":60,"price":66}]},{"orderId":291,"orderCode":"16052814242796770063","deliveryWay":"STAY","userDeliveryPrice":0,"shopId":60,"shopName":"呵呵水果铺","total":190,"status":"PAYING","createTime":1464416667,"freeSend":"55","orderGoods":[{"goodsId":592,"goodsName":"香蕉123123","goodsCount":10,"promotePrice":20,"price":""}]},{"orderId":292,"orderCode":"16052814243733590063","deliveryWay":"STAY","userDeliveryPrice":0,"shopId":60,"shopName":"呵呵水果铺","total":807,"status":"PAYING","createTime":1464416677,"freeSend":"55","orderGoods":[{"goodsId":449,"goodsName":"芒果4","goodsCount":5,"promotePrice":61,"price":60},{"goodsId":448,"goodsName":"芒果2","goodsCount":7,"promotePrice":56,"price":50},{"goodsId":592,"goodsName":"香蕉123123","goodsCount":4,"promotePrice":20,"price":""},{"goodsId":426,"goodsName":"香蕉123123","goodsCount":3,"promotePrice":20,"price":""}]}]}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * limit : 10
     * prePage : 0
     * start : 0
     * currentPage : 1
     * nextPage : 2
     * totalCount : 3
     * list : [{"orderId":269,"orderCode":"16052711001502010063","deliveryWay":"SELF","userDeliveryPrice":0,"shopId":60,"shopName":"呵呵水果铺","total":210,"status":"OK","createTime":1464318015,"freeSend":"55","orderGoods":[{"goodsId":625,"goodsName":"榴莲","goodsCount":4,"promotePrice":60,"price":66}]},{"orderId":291,"orderCode":"16052814242796770063","deliveryWay":"STAY","userDeliveryPrice":0,"shopId":60,"shopName":"呵呵水果铺","total":190,"status":"PAYING","createTime":1464416667,"freeSend":"55","orderGoods":[{"goodsId":592,"goodsName":"香蕉123123","goodsCount":10,"promotePrice":20,"price":""}]},{"orderId":292,"orderCode":"16052814243733590063","deliveryWay":"STAY","userDeliveryPrice":0,"shopId":60,"shopName":"呵呵水果铺","total":807,"status":"PAYING","createTime":1464416677,"freeSend":"55","orderGoods":[{"goodsId":449,"goodsName":"芒果4","goodsCount":5,"promotePrice":61,"price":60},{"goodsId":448,"goodsName":"芒果2","goodsCount":7,"promotePrice":56,"price":50},{"goodsId":592,"goodsName":"香蕉123123","goodsCount":4,"promotePrice":20,"price":""},{"goodsId":426,"goodsName":"香蕉123123","goodsCount":3,"promotePrice":20,"price":""}]}]
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

    public static class DataBean implements Serializable{
        private int limit;
        private int prePage;
        private int start;
        private int currentPage;
        private int nextPage;
        private String totalCount;
        /**
         * orderId : 269
         * orderCode : 16052711001502010063
         * deliveryWay : SELF
         * userDeliveryPrice : 0
         * shopId : 60
         * shopName : 呵呵水果铺
         * total : 210
         * status : OK
         * createTime : 1464318015
         * freeSend : 55
         * orderGoods : [{"goodsId":625,"goodsName":"榴莲","goodsCount":4,"promotePrice":60,"price":66}]
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

        public static class ListBean implements Serializable{
            private int orderId;
            private String orderCode;
            private String deliveryWay;
            private int userDeliveryPrice;
            private int shopId;
            private String shopName;
            private String total;
            private String status;
            private int createTime;
            private String freeSend;
            private String deliveryStatus;
            private String refundStatus;
            private String cancelStatus;
            private String evaluateStatus;
            private String shareStatus;

            public String getShareStatus() {
                return shareStatus;
            }

            public void setShareStatus(String shareStatus) {
                this.shareStatus = shareStatus;
            }

            private int Time1;

            public String getDeliveryStatus() {
                return deliveryStatus;
            }

            public void setDeliveryStatus(String deliveryStatus) {
                this.deliveryStatus = deliveryStatus;
            }

            public String getRefundStatus() {
                return refundStatus;
            }

            public void setRefundStatus(String refundStatus) {
                this.refundStatus = refundStatus;
            }

            public String getCancelStatus() {
                return cancelStatus;
            }

            public void setCancelStatus(String cancelStatus) {
                this.cancelStatus = cancelStatus;
            }

            public String getEvaluateStatus() {
                return evaluateStatus;
            }

            public void setEvaluateStatus(String evaluateStatus) {
                this.evaluateStatus = evaluateStatus;
            }



            public int getTime1() {
                return Time1;
            }

            public void setTime1(int time1) {
                Time1 = time1;
            }

            /**
             * goodsId : 625
             * goodsName : 榴莲
             * goodsCount : 4
             * promotePrice : 60
             * price : 66
             */

            private List<OrderGoodsBean> orderGoods;

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public String getDeliveryWay() {
                return deliveryWay;
            }

            public void setDeliveryWay(String deliveryWay) {
                this.deliveryWay = deliveryWay;
            }

            public int getUserDeliveryPrice() {
                return userDeliveryPrice;
            }

            public void setUserDeliveryPrice(int userDeliveryPrice) {
                this.userDeliveryPrice = userDeliveryPrice;
            }

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getCreateTime() {
                return createTime;
            }

            public void setCreateTime(int createTime) {
                this.createTime = createTime;
            }

            public String getFreeSend() {
                return freeSend;
            }

            public void setFreeSend(String freeSend) {
                this.freeSend = freeSend;
            }

            public List<OrderGoodsBean> getOrderGoods() {
                return orderGoods;
            }

            public void setOrderGoods(List<OrderGoodsBean> orderGoods) {
                this.orderGoods = orderGoods;
            }

            public static class OrderGoodsBean implements Serializable{
                private String goodsId;
                private String goodsName;
                private String goodsCount;
                private String promotePrice;
                private String price;
                private String pack;
                private String unit;
                private String amount;

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public String getPack() {
                    return pack;
                }

                public void setPack(String pack) {
                    this.pack = pack;
                }

                public String getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(String goodsId) {
                    this.goodsId = goodsId;
                }

                public String getGoodsName() {
                    return goodsName;
                }

                public void setGoodsName(String goodsName) {
                    this.goodsName = goodsName;
                }

                public String getGoodsCount() {
                    return goodsCount;
                }

                public void setGoodsCount(String goodsCount) {
                    this.goodsCount = goodsCount;
                }

                public String getPromotePrice() {
                    return promotePrice;
                }

                public void setPromotePrice(String promotePrice) {
                    this.promotePrice = promotePrice;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }
            }
        }
    }
}
