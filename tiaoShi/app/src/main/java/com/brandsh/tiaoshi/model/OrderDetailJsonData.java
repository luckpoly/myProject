package com.brandsh.tiaoshi.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sisi on 16/3/21.
 */
public class OrderDetailJsonData implements Serializable{


    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"userName":"18300709012","shopName":"呵呵水果铺","orderCode":"16053115271178890063","total":567,"status":"PAYING","contact":"王吴昊","tel":"17702182420","address":"1111","addressDetail":"上海市宝山区复旦软件园(高境)","userDeliveryPrice":"0","platformDiscountPrice":"0","deliveryWay":"STAY","expressCode":"","remarks":"","createTime":1464679631,"payTime":false,"arriveTime":false,"paymentTime":"","transportTime":"00分","orderGoods":[{"goodsId":630,"goodsCount":3,"goodsName":"冰糖橙","promotePrice":18,"price":20,"intro":""},{"goodsId":631,"goodsCount":2,"goodsName":"榛子","promotePrice":48,"price":50,"intro":""},{"goodsId":629,"goodsCount":3,"goodsName":" 美国进口哈密瓜","promotePrice":28,"price":30,"intro":""},{"goodsId":632,"goodsCount":3,"goodsName":"美国牛油果","promotePrice":45,"price":50,"intro":"谁谁谁水水水水"},{"goodsId":633,"goodsCount":3,"goodsName":"菠萝蜜","promotePrice":66,"price":70,"intro":"好吃好吃跟改革"}]}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * userName : 18300709012
     * shopName : 呵呵水果铺
     * orderCode : 16053115271178890063
     * total : 567
     * status : PAYING
     * contact : 王吴昊
     * tel : 17702182420
     * address : 1111
     * addressDetail : 上海市宝山区复旦软件园(高境)
     * userDeliveryPrice : 0
     * platformDiscountPrice : 0
     * deliveryWay : STAY
     * expressCode :
     * remarks :
     * createTime : 1464679631
     * payTime : false
     * arriveTime : false
     * paymentTime :
     * transportTime : 00分
     * orderGoods : [{"goodsId":630,"goodsCount":3,"goodsName":"冰糖橙","promotePrice":18,"price":20,"intro":""},{"goodsId":631,"goodsCount":2,"goodsName":"榛子","promotePrice":48,"price":50,"intro":""},{"goodsId":629,"goodsCount":3,"goodsName":" 美国进口哈密瓜","promotePrice":28,"price":30,"intro":""},{"goodsId":632,"goodsCount":3,"goodsName":"美国牛油果","promotePrice":45,"price":50,"intro":"谁谁谁水水水水"},{"goodsId":633,"goodsCount":3,"goodsName":"菠萝蜜","promotePrice":66,"price":70,"intro":"好吃好吃跟改革"}]
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
        private String userName;
        private String shopName;
        private String orderCode;
        private String orderId;
        private String total;
        private String status;
        private String contact;
        private String deliveryStatus;
        private String refundStatus;
        private String cancelStatus;
        private String evaluateStatus;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
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

        private String tel;
        private String address;
        private String addressDetail;
        private String userDeliveryPrice;
        private String platformDiscountPrice;
        private String deliveryWay;
        private String expressCode;
        private String remarks;
        private String serviceTel;
        private int createTime;
        private boolean payTime;
        private boolean arriveTime;
        private String paymentTime;
        private String transportTime;


        /**
         * goodsId : 630
         * goodsCount : 3

         * goodsName : 冰糖橙
         * promotePrice : 18
         * price : 20
         * intro :
         */
        public String getServiceTel() {
            return serviceTel;
        }

        public void setServiceTel(String serviceTel) {
            this.serviceTel = serviceTel;
        }

        private List<OrderGoodsBean> orderGoods;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
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

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
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

        public String getUserDeliveryPrice() {
            return userDeliveryPrice;
        }

        public void setUserDeliveryPrice(String userDeliveryPrice) {
            this.userDeliveryPrice = userDeliveryPrice;
        }

        public String getPlatformDiscountPrice() {
            return platformDiscountPrice;
        }

        public void setPlatformDiscountPrice(String platformDiscountPrice) {
            this.platformDiscountPrice = platformDiscountPrice;
        }

        public String getDeliveryWay() {
            return deliveryWay;
        }

        public void setDeliveryWay(String deliveryWay) {
            this.deliveryWay = deliveryWay;
        }

        public String getExpressCode() {
            return expressCode;
        }

        public void setExpressCode(String expressCode) {
            this.expressCode = expressCode;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public boolean isPayTime() {
            return payTime;
        }

        public void setPayTime(boolean payTime) {
            this.payTime = payTime;
        }

        public boolean isArriveTime() {
            return arriveTime;
        }

        public void setArriveTime(boolean arriveTime) {
            this.arriveTime = arriveTime;
        }

        public String getPaymentTime() {
            return paymentTime;
        }

        public void setPaymentTime(String paymentTime) {
            this.paymentTime = paymentTime;
        }

        public String getTransportTime() {
            return transportTime;
        }

        public void setTransportTime(String transportTime) {
            this.transportTime = transportTime;
        }

        public List<OrderGoodsBean> getOrderGoods() {
            return orderGoods;
        }

        public void setOrderGoods(List<OrderGoodsBean> orderGoods) {
            this.orderGoods = orderGoods;
        }

        public static class OrderGoodsBean {
            private int goodsId;
            private int goodsCount;
            private String goodsName;
            private String promotePrice;
            private String price;
            private String intro;
            private String pack;
            private String amount;
            private String unit;

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

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getGoodsCount() {
                return goodsCount;
            }

            public void setGoodsCount(int goodsCount) {
                this.goodsCount = goodsCount;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
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

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }
        }
    }
}
