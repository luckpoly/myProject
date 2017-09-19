package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by Administrator on 2016/6/3.
 */
public class CouponModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : [{"id":4,"couponCode":"pZe9jwg8AP6Jj-U5JZhD_n55vbrQ","couponUseCode":"060670865021","shopId":0,"couponType":"CASH","logoUrl":"https://mmbiz.qlogo.cn/mmbiz_png/Gic01JZqVDOd5zIEnHiaIewJM1tywuUwZoC3p3bTTmc7aYJy7gSA0S1Ficum6qE8a0C98RnTibyUts5wMZ6wbcAKxg/0?wx_fmt=png","brandName":"挑食网","title":"微信优惠券测试","subTitle":"test1","color":"Color050","notice":"通知","description":"描述","canGiveFriend":1,"useDataBegin":1478670865,"useDataEnd":1478757265,"useTimeLimitType":"0","useTimeLimitBegin":null,"useTimeLimitEnd":null,"cousumeTime":null,"leastCost":"1000","reduceCost":"100","discount":"","gift":"","defaultDetail":"","useStatus":"OK"},{"id":3,"couponCode":"pZe9jwg8AP6Jj-U5JZhD_n55vbrQ","couponUseCode":"946670862021","shopId":0,"couponType":"CASH","logoUrl":"https://mmbiz.qlogo.cn/mmbiz_png/Gic01JZqVDOd5zIEnHiaIewJM1tywuUwZoC3p3bTTmc7aYJy7gSA0S1Ficum6qE8a0C98RnTibyUts5wMZ6wbcAKxg/0?wx_fmt=png","brandName":"挑食网","title":"微信优惠券测试","subTitle":"test1","color":"Color050","notice":"通知","description":"描述","canGiveFriend":1,"useDataBegin":1478843662,"useDataEnd":1478757262,"useTimeLimitType":"0","useTimeLimitBegin":null,"useTimeLimitEnd":null,"cousumeTime":null,"leastCost":"1000","reduceCost":"100","discount":"","gift":"","defaultDetail":"","useStatus":"OK"},{"id":5,"couponCode":"pZe9jwg8AP6Jj-U5JZhD_n55vbrQ","couponUseCode":"684670867021","shopId":0,"couponType":"CASH","logoUrl":"https://mmbiz.qlogo.cn/mmbiz_png/Gic01JZqVDOd5zIEnHiaIewJM1tywuUwZoC3p3bTTmc7aYJy7gSA0S1Ficum6qE8a0C98RnTibyUts5wMZ6wbcAKxg/0?wx_fmt=png","brandName":"挑食网","title":"微信优惠券测试","subTitle":"test1","color":"Color050","notice":"通知","description":"描述","canGiveFriend":1,"useDataBegin":1478843667,"useDataEnd":1478757267,"useTimeLimitType":"0","useTimeLimitBegin":null,"useTimeLimitEnd":null,"cousumeTime":null,"leastCost":"1000","reduceCost":"100","discount":"","gift":"","defaultDetail":"","useStatus":"OK"},{"id":6,"couponCode":"pZe9jwg8AP6Jj-U5JZhD_n55vbrQ","couponUseCode":"947670869021","shopId":0,"couponType":"CASH","logoUrl":"https://mmbiz.qlogo.cn/mmbiz_png/Gic01JZqVDOd5zIEnHiaIewJM1tywuUwZoC3p3bTTmc7aYJy7gSA0S1Ficum6qE8a0C98RnTibyUts5wMZ6wbcAKxg/0?wx_fmt=png","brandName":"挑食网","title":"微信优惠券测试","subTitle":"test1","color":"Color050","notice":"通知","description":"描述","canGiveFriend":1,"useDataBegin":1478843669,"useDataEnd":1478757269,"useTimeLimitType":"0","useTimeLimitBegin":null,"useTimeLimitEnd":null,"cousumeTime":null,"leastCost":"1000","reduceCost":"100","discount":"","gift":"","defaultDetail":"","useStatus":"OK"}]
     */

    private String respCode;
    private String respMsg;
    /**
     * id : 4
     * couponCode : pZe9jwg8AP6Jj-U5JZhD_n55vbrQ
     * couponUseCode : 060670865021
     * shopId : 0
     * couponType : CASH
     * logoUrl : https://mmbiz.qlogo.cn/mmbiz_png/Gic01JZqVDOd5zIEnHiaIewJM1tywuUwZoC3p3bTTmc7aYJy7gSA0S1Ficum6qE8a0C98RnTibyUts5wMZ6wbcAKxg/0?wx_fmt=png
     * brandName : 挑食网
     * title : 微信优惠券测试
     * subTitle : test1
     * color : Color050
     * notice : 通知
     * description : 描述
     * canGiveFriend : 1
     * useDataBegin : 1478670865
     * useDataEnd : 1478757265
     * useTimeLimitType : 0
     * useTimeLimitBegin : null
     * useTimeLimitEnd : null
     * cousumeTime : null
     * leastCost : 1000
     * reduceCost : 100
     * discount :
     * gift :
     * defaultDetail :
     * useStatus : OK
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String couponCode;
        private String couponUseCode;
        private int shopId;
        private String couponType;
        private String logoUrl;
        private String brandName;
        private String title;
        private String subTitle;
        private String color;
        private String notice;
        private String description;
        private String canGiveFriend;
        private String useDataBegin;
        private String useDataEnd;
        private String useTimeLimitType;
        private String leastCost;
        private String reduceCost;
        private String discount;
        private String gift;
        private String defaultDetail;
        private String useStatus;
        private String useTimeLimitBegin;
        private String useTimeLimitEnd;
        private String cousumeTime;

        public String getUseTimeLimitBegin() {
            return useTimeLimitBegin;
        }

        public void setUseTimeLimitBegin(String useTimeLimitBegin) {
            this.useTimeLimitBegin = useTimeLimitBegin;
        }

        public String getUseTimeLimitEnd() {
            return useTimeLimitEnd;
        }

        public void setUseTimeLimitEnd(String useTimeLimitEnd) {
            this.useTimeLimitEnd = useTimeLimitEnd;
        }

        public String getCousumeTime() {
            return cousumeTime;
        }

        public void setCousumeTime(String cousumeTime) {
            this.cousumeTime = cousumeTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getCouponUseCode() {
            return couponUseCode;
        }

        public void setCouponUseCode(String couponUseCode) {
            this.couponUseCode = couponUseCode;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getCouponType() {
            return couponType;
        }

        public void setCouponType(String couponType) {
            this.couponType = couponType;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCanGiveFriend() {
            return canGiveFriend;
        }

        public void setCanGiveFriend(String canGiveFriend) {
            this.canGiveFriend = canGiveFriend;
        }

        public String getUseDataBegin() {
            return useDataBegin;
        }

        public void setUseDataBegin(String useDataBegin) {
            this.useDataBegin = useDataBegin;
        }

        public String getUseDataEnd() {
            return useDataEnd;
        }

        public void setUseDataEnd(String useDataEnd) {
            this.useDataEnd = useDataEnd;
        }

        public String getUseTimeLimitType() {
            return useTimeLimitType;
        }

        public void setUseTimeLimitType(String useTimeLimitType) {
            this.useTimeLimitType = useTimeLimitType;
        }

        public String getLeastCost() {
            return leastCost;
        }

        public void setLeastCost(String leastCost) {
            this.leastCost = leastCost;
        }

        public String getReduceCost() {
            return reduceCost;
        }

        public void setReduceCost(String reduceCost) {
            this.reduceCost = reduceCost;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getGift() {
            return gift;
        }

        public void setGift(String gift) {
            this.gift = gift;
        }

        public String getDefaultDetail() {
            return defaultDetail;
        }

        public void setDefaultDetail(String defaultDetail) {
            this.defaultDetail = defaultDetail;
        }

        public String getUseStatus() {
            return useStatus;
        }

        public void setUseStatus(String useStatus) {
            this.useStatus = useStatus;
        }
    }
}
