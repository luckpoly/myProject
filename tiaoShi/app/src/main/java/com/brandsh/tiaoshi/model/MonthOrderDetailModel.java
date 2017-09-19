package com.brandsh.tiaoshi.model;

/**
 * Created by Administrator on 2016/10/20.
 */

public class MonthOrderDetailModel  {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"partialOrderId":"","shopOrderStatus":"待配送","startStatus":"1","restJuiceCount":"60","orderId":"240","sendShopId":261,"shopId":"261","contact":"啦啦啦","tel":"18300709012","lng":"121.5083389282","lat":"31.3132133484","address":"上海市杨浦区三门路(地铁站)","addressDetail":"1102","remarks":"","morningSendTime":"07:00-08:00","noonSendTime":"11:00-13:00","nightSendTime":"18:00-19:00"}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * partialOrderId :
     * shopOrderStatus : 待配送
     * startStatus : 1
     * restJuiceCount : 60
     * orderId : 240
     * sendShopId : 261
     * shopId : 261
     * contact : 啦啦啦
     * tel : 18300709012
     * lng : 121.5083389282
     * lat : 31.3132133484
     * address : 上海市杨浦区三门路(地铁站)
     * addressDetail : 1102
     * remarks :
     * morningSendTime : 07:00-08:00
     * noonSendTime : 11:00-13:00
     * nightSendTime : 18:00-19:00
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
        private String partialOrderId;
        private String shopOrderStatus;
        private String startStatus;
        private String restJuiceCount;
        private String orderId;
        private int sendShopId;
        private String shopId;
        private String contact;
        private String tel;
        private String lng;
        private String lat;
        private String address;
        private String addressDetail;
        private String remarks;
        private String morningSendTime;
        private String noonSendTime;
        private String nightSendTime;
        private String isEdit;

        public String getIsEdit() {
            return isEdit;
        }

        public void setIsEdit(String isEdit) {
            this.isEdit = isEdit;
        }

        public String getPartialOrderId() {
            return partialOrderId;
        }

        public void setPartialOrderId(String partialOrderId) {
            this.partialOrderId = partialOrderId;
        }

        public String getShopOrderStatus() {
            return shopOrderStatus;
        }

        public void setShopOrderStatus(String shopOrderStatus) {
            this.shopOrderStatus = shopOrderStatus;
        }

        public String getStartStatus() {
            return startStatus;
        }

        public void setStartStatus(String startStatus) {
            this.startStatus = startStatus;
        }

        public String getRestJuiceCount() {
            return restJuiceCount;
        }

        public void setRestJuiceCount(String restJuiceCount) {
            this.restJuiceCount = restJuiceCount;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getSendShopId() {
            return sendShopId;
        }

        public void setSendShopId(int sendShopId) {
            this.sendShopId = sendShopId;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
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

        public String getMorningSendTime() {
            return morningSendTime;
        }

        public void setMorningSendTime(String morningSendTime) {
            this.morningSendTime = morningSendTime;
        }

        public String getNoonSendTime() {
            return noonSendTime;
        }

        public void setNoonSendTime(String noonSendTime) {
            this.noonSendTime = noonSendTime;
        }

        public String getNightSendTime() {
            return nightSendTime;
        }

        public void setNightSendTime(String nightSendTime) {
            this.nightSendTime = nightSendTime;
        }
    }
}
