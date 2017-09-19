package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */

public class MonthOrderListModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"limit":20,"prePage":0,"start":0,"currentPage":1,"nextPage":2,"totalCount":"2","list":[{"orderId":241,"orderCode":"16102016183174860021","deliveryWay":"STAY","userDeliveryPrice":0,"shopId":261,"shopName":"缤纷分享","total":0.01,"status":"OK","deliveryStatus":"PRE","refundStatus":"NORMAL","cancelStatus":"NORMAL","evaluateStatus":"NOT_YET","orderType":"包月套餐","startStatus":1,"createTime":1476951511,"freeSend":400,"payWay":"ALIPAY","payNo":"2016102021001004670280282864","restJuiceCount":0,"juiceTotal":1,"orderGoods":[{"goodsId":7566,"goodsName":"果汁套餐二","goodsCount":1,"promotePrice":"","amount":1,"price":500,"pack":"","unit":"null"}],"orderPartial":[{"partialOrderId":2,"shopId":2,"shopOrderStatus":"","orderStatus":1,"sendTime":1477065601,"lng":121.490761,"lat":31.311445,"address":"上海市杨浦区三门路吉浦路(公交站)","addressDetail":"1102","remarks":"18:00","sendMorningStatus":"","sendMorningTime":"8:10","sendNoonStatus":"","sendNoonTime":"12:10","sendNightStatus":"","sendnightTime":""}]},{"orderId":240,"orderCode":"16102016134762130021","deliveryWay":"STAY","userDeliveryPrice":0,"shopId":261,"shopName":"缤纷分享","total":0.01,"status":"OK","deliveryStatus":"PRE","refundStatus":"NORMAL","cancelStatus":"NORMAL","evaluateStatus":"NOT_YET","orderType":"包月套餐","startStatus":1,"createTime":1476951227,"freeSend":400,"payWay":"ALIPAY","payNo":"2016102021001004670280221510","restJuiceCount":59,"juiceTotal":60,"orderGoods":[{"goodsId":7565,"goodsName":"果汁套餐一","goodsCount":1,"promotePrice":"","amount":60,"price":500,"pack":"","unit":"null"}],"orderPartial":[{"partialOrderId":1,"shopId":2,"shopOrderStatus":"","orderStatus":1,"sendTime":1477065601,"lng":121.488029,"lat":31.31258,"address":"上海市宝山区复旦软件园(高境)","addressDetail":"1101","remarks":"咋打的法师打发士大夫撒旦飞洒地方","sendMorningStatus":"","sendMorningTime":"8:10","sendNoonStatus":"","sendNoonTime":"12:30","sendNightStatus":"","sendnightTime":""}]}]}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * limit : 20
     * prePage : 0
     * start : 0
     * currentPage : 1
     * nextPage : 2
     * totalCount : 2
     * list : [{"orderId":241,"orderCode":"16102016183174860021","deliveryWay":"STAY","userDeliveryPrice":0,"shopId":261,"shopName":"缤纷分享","total":0.01,"status":"OK","deliveryStatus":"PRE","refundStatus":"NORMAL","cancelStatus":"NORMAL","evaluateStatus":"NOT_YET","orderType":"包月套餐","startStatus":1,"createTime":1476951511,"freeSend":400,"payWay":"ALIPAY","payNo":"2016102021001004670280282864","restJuiceCount":0,"juiceTotal":1,"orderGoods":[{"goodsId":7566,"goodsName":"果汁套餐二","goodsCount":1,"promotePrice":"","amount":1,"price":500,"pack":"","unit":"null"}],"orderPartial":[{"partialOrderId":2,"shopId":2,"shopOrderStatus":"","orderStatus":1,"sendTime":1477065601,"lng":121.490761,"lat":31.311445,"address":"上海市杨浦区三门路吉浦路(公交站)","addressDetail":"1102","remarks":"18:00","sendMorningStatus":"","sendMorningTime":"8:10","sendNoonStatus":"","sendNoonTime":"12:10","sendNightStatus":"","sendnightTime":""}]},{"orderId":240,"orderCode":"16102016134762130021","deliveryWay":"STAY","userDeliveryPrice":0,"shopId":261,"shopName":"缤纷分享","total":0.01,"status":"OK","deliveryStatus":"PRE","refundStatus":"NORMAL","cancelStatus":"NORMAL","evaluateStatus":"NOT_YET","orderType":"包月套餐","startStatus":1,"createTime":1476951227,"freeSend":400,"payWay":"ALIPAY","payNo":"2016102021001004670280221510","restJuiceCount":59,"juiceTotal":60,"orderGoods":[{"goodsId":7565,"goodsName":"果汁套餐一","goodsCount":1,"promotePrice":"","amount":60,"price":500,"pack":"","unit":"null"}],"orderPartial":[{"partialOrderId":1,"shopId":2,"shopOrderStatus":"","orderStatus":1,"sendTime":1477065601,"lng":121.488029,"lat":31.31258,"address":"上海市宝山区复旦软件园(高境)","addressDetail":"1101","remarks":"咋打的法师打发士大夫撒旦飞洒地方","sendMorningStatus":"","sendMorningTime":"8:10","sendNoonStatus":"","sendNoonTime":"12:30","sendNightStatus":"","sendnightTime":""}]}]
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
         * orderId : 241
         * orderCode : 16102016183174860021
         * deliveryWay : STAY
         * userDeliveryPrice : 0
         * shopId : 261
         * shopName : 缤纷分享
         * total : 0.01
         * status : OK
         * deliveryStatus : PRE
         * refundStatus : NORMAL
         * cancelStatus : NORMAL
         * evaluateStatus : NOT_YET
         * orderType : 包月套餐
         * startStatus : 1
         * createTime : 1476951511
         * freeSend : 400
         * payWay : ALIPAY
         * payNo : 2016102021001004670280282864
         * restJuiceCount : 0
         * juiceTotal : 1
         * orderGoods : [{"goodsId":7566,"goodsName":"果汁套餐二","goodsCount":1,"promotePrice":"","amount":1,"price":500,"pack":"","unit":"null"}]
         * orderPartial : [{"partialOrderId":2,"shopId":2,"shopOrderStatus":"","orderStatus":1,"sendTime":1477065601,"lng":121.490761,"lat":31.311445,"address":"上海市杨浦区三门路吉浦路(公交站)","addressDetail":"1102","remarks":"18:00","sendMorningStatus":"","sendMorningTime":"8:10","sendNoonStatus":"","sendNoonTime":"12:10","sendNightStatus":"","sendnightTime":""}]
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
            private int orderId;
            private String orderCode;
            private String deliveryWay;
            private String userDeliveryPrice;
            private int shopId;
            private String shopName;
            private String total;
            private String status;
            private String deliveryStatus;
            private String refundStatus;
            private String cancelStatus;
            private String evaluateStatus;
            private String orderType;
            private String startStatus;
            private String createTime;
            private String freeSend;
            private String payWay;
            private String payNo;
            private String restJuiceCount;
            private String juiceTotal;
            /**
             * goodsId : 7566
             * goodsName : 果汁套餐二
             * goodsCount : 1
             * promotePrice :
             * amount : 1
             * price : 500
             * pack :
             * unit : null
             */

            private List<OrderGoodsBean> orderGoods;
            /**
             * partialOrderId : 2
             * shopId : 2
             * shopOrderStatus :
             * orderStatus : 1
             * sendTime : 1477065601
             * lng : 121.490761
             * lat : 31.311445
             * address : 上海市杨浦区三门路吉浦路(公交站)
             * addressDetail : 1102
             * remarks : 18:00
             * sendMorningStatus :
             * sendMorningTime : 8:10
             * sendNoonStatus :
             * sendNoonTime : 12:10
             * sendNightStatus :
             * sendnightTime :
             */

            private List<OrderPartialBean> orderPartial;

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

            public String getUserDeliveryPrice() {
                return userDeliveryPrice;
            }

            public void setUserDeliveryPrice(String userDeliveryPrice) {
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

            public String getOrderType() {
                return orderType;
            }

            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }

            public String getStartStatus() {
                return startStatus;
            }

            public void setStartStatus(String startStatus) {
                this.startStatus = startStatus;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getFreeSend() {
                return freeSend;
            }

            public void setFreeSend(String freeSend) {
                this.freeSend = freeSend;
            }

            public String getPayWay() {
                return payWay;
            }

            public void setPayWay(String payWay) {
                this.payWay = payWay;
            }

            public String getPayNo() {
                return payNo;
            }

            public void setPayNo(String payNo) {
                this.payNo = payNo;
            }

            public String getRestJuiceCount() {
                return restJuiceCount;
            }

            public void setRestJuiceCount(String restJuiceCount) {
                this.restJuiceCount = restJuiceCount;
            }

            public String getJuiceTotal() {
                return juiceTotal;
            }

            public void setJuiceTotal(String juiceTotal) {
                this.juiceTotal = juiceTotal;
            }

            public List<OrderGoodsBean> getOrderGoods() {
                return orderGoods;
            }

            public void setOrderGoods(List<OrderGoodsBean> orderGoods) {
                this.orderGoods = orderGoods;
            }

            public List<OrderPartialBean> getOrderPartial() {
                return orderPartial;
            }

            public void setOrderPartial(List<OrderPartialBean> orderPartial) {
                this.orderPartial = orderPartial;
            }

            public static class OrderGoodsBean {
                private int goodsId;
                private String goodsName;
                private int goodsCount;
                private String promotePrice;
                private String amount;
                private String price;
                private String pack;
                private String unit;

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public String getGoodsName() {
                    return goodsName;
                }

                public void setGoodsName(String goodsName) {
                    this.goodsName = goodsName;
                }

                public int getGoodsCount() {
                    return goodsCount;
                }

                public void setGoodsCount(int goodsCount) {
                    this.goodsCount = goodsCount;
                }

                public String getPromotePrice() {
                    return promotePrice;
                }

                public void setPromotePrice(String promotePrice) {
                    this.promotePrice = promotePrice;
                }

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getPack() {
                    return pack;
                }

                public void setPack(String pack) {
                    this.pack = pack;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }
            }

            public static class OrderPartialBean {
                private int partialOrderId;
                private int shopId;
                private String shopOrderStatus;
                private String orderStatus;
                private String sendTime;
                private String lng;
                private String lat;
                private String address;
                private String addressDetail;
                private String remarks;
                private String sendMorningStatus;
                private String sendMorningTime;
                private String sendNoonStatus;
                private String sendNoonTime;
                private String sendNightStatus;
                private String sendnightTime;
                private String goodsName;
                private String cup;

                public String getGoodsName() {
                    return goodsName;
                }

                public void setGoodsName(String goodsName) {
                    this.goodsName = goodsName;
                }

                public String getCup() {
                    return cup;
                }

                public void setCup(String cup) {
                    this.cup = cup;
                }

                public int getPartialOrderId() {
                    return partialOrderId;
                }

                public void setPartialOrderId(int partialOrderId) {
                    this.partialOrderId = partialOrderId;
                }

                public int getShopId() {
                    return shopId;
                }

                public void setShopId(int shopId) {
                    this.shopId = shopId;
                }

                public String getShopOrderStatus() {
                    return shopOrderStatus;
                }

                public void setShopOrderStatus(String shopOrderStatus) {
                    this.shopOrderStatus = shopOrderStatus;
                }

                public String getOrderStatus() {
                    return orderStatus;
                }

                public void setOrderStatus(String orderStatus) {
                    this.orderStatus = orderStatus;
                }

                public String getSendTime() {
                    return sendTime;
                }

                public void setSendTime(String sendTime) {
                    this.sendTime = sendTime;
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

                public String getRemarks() {
                    return remarks;
                }

                public void setRemarks(String remarks) {
                    this.remarks = remarks;
                }

                public String getSendMorningStatus() {
                    return sendMorningStatus;
                }

                public void setSendMorningStatus(String sendMorningStatus) {
                    this.sendMorningStatus = sendMorningStatus;
                }

                public String getSendMorningTime() {
                    return sendMorningTime;
                }

                public void setSendMorningTime(String sendMorningTime) {
                    this.sendMorningTime = sendMorningTime;
                }

                public String getSendNoonStatus() {
                    return sendNoonStatus;
                }

                public void setSendNoonStatus(String sendNoonStatus) {
                    this.sendNoonStatus = sendNoonStatus;
                }

                public String getSendNoonTime() {
                    return sendNoonTime;
                }

                public void setSendNoonTime(String sendNoonTime) {
                    this.sendNoonTime = sendNoonTime;
                }

                public String getSendNightStatus() {
                    return sendNightStatus;
                }

                public void setSendNightStatus(String sendNightStatus) {
                    this.sendNightStatus = sendNightStatus;
                }

                public String getSendnightTime() {
                    return sendnightTime;
                }

                public void setSendnightTime(String sendnightTime) {
                    this.sendnightTime = sendnightTime;
                }
            }
        }
    }
}
