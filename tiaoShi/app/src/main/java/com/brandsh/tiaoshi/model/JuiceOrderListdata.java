package com.brandsh.tiaoshi.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public class JuiceOrderListdata implements Serializable{

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : [{"orderId":1028,"orderCode":"16082314315410700059","shareUsername":"沫沫","sharePhone":"18300709012","shareIntro":"快乐每一天！","getPhone":"","getAddress":"","getAddressDetail":"","getStatus":"GET","orderGoods":[{"goodsId":985,"goodsName":"菠萝汁","goodsCount":1,"promotePrice":"","price":1,"pack":"亲情杯","unit":"杯"}]}]
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    private String debugInfo;
    /**
     * orderId : 1028
     * orderCode : 16082314315410700059
     * shareUsername : 沫沫
     * sharePhone : 18300709012
     * shareIntro : 快乐每一天！
     * getPhone :
     * getAddress :
     * getAddressDetail :
     * getStatus : GET
     * orderGoods : [{"goodsId":985,"goodsName":"菠萝汁","goodsCount":1,"promotePrice":"","price":1,"pack":"亲情杯","unit":"杯"}]
     */

    private List<DataBean> data;

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

    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private int orderId;
        private String orderCode;
        private String shareUsername;
        private String sharePhone;
        private String shareIntro;
        private String getPhone;
        private String getAddress;
        private String getAddressDetail;
        private String getStatus;
        /**
         * goodsId : 985
         * goodsName : 菠萝汁
         * goodsCount : 1
         * promotePrice :
         * price : 1
         * pack : 亲情杯
         * unit : 杯
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

        public String getShareUsername() {
            return shareUsername;
        }

        public void setShareUsername(String shareUsername) {
            this.shareUsername = shareUsername;
        }

        public String getSharePhone() {
            return sharePhone;
        }

        public void setSharePhone(String sharePhone) {
            this.sharePhone = sharePhone;
        }

        public String getShareIntro() {
            return shareIntro;
        }

        public void setShareIntro(String shareIntro) {
            this.shareIntro = shareIntro;
        }

        public String getGetPhone() {
            return getPhone;
        }

        public void setGetPhone(String getPhone) {
            this.getPhone = getPhone;
        }

        public String getGetAddress() {
            return getAddress;
        }

        public void setGetAddress(String getAddress) {
            this.getAddress = getAddress;
        }

        public String getGetAddressDetail() {
            return getAddressDetail;
        }

        public void setGetAddressDetail(String getAddressDetail) {
            this.getAddressDetail = getAddressDetail;
        }

        public String getGetStatus() {
            return getStatus;
        }

        public void setGetStatus(String getStatus) {
            this.getStatus = getStatus;
        }

        public List<OrderGoodsBean> getOrderGoods() {
            return orderGoods;
        }

        public void setOrderGoods(List<OrderGoodsBean> orderGoods) {
            this.orderGoods = orderGoods;
        }

        public static class OrderGoodsBean implements Serializable{
            private int goodsId;
            private String goodsName;
            private String goodsCount;
            private String promotePrice;
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
    }
}
