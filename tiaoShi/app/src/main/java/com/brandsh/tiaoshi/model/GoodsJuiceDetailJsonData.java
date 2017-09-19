package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by apple on 16/2/22.
 */
public class GoodsJuiceDetailJsonData {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"shopId":"83","goodsId":"982","goodsName":"果汁分享啦","goodsTitle":"果汁分享副标题测试","promotePrice":"0","isPromote":"0","price":"1","thumImg":"http://7xn4ae.com1.z0.glb.clouddn.com/1471316386374412.jpg","icon":"","totalSoldOut":"","totalShare":"16","salesNum":"1","salesUnit":"杯","weight":"1","weightUnit":"KG","tags":[""],"inventory":"-1","pack":"情侣杯"}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * shopId : 83
     * goodsId : 982
     * goodsName : 果汁分享啦
     * goodsTitle : 果汁分享副标题测试
     * promotePrice : 0
     * isPromote : 0
     * price : 1
     * thumImg : http://7xn4ae.com1.z0.glb.clouddn.com/1471316386374412.jpg
     * icon :
     * totalSoldOut :
     * totalShare : 16
     * salesNum : 1
     * salesUnit : 杯
     * weight : 1
     * weightUnit : KG
     * tags : [""]
     * inventory : -1
     * pack : 情侣杯
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
        private String shopId;
        private String goodsId;
        private String goodsName;
        private String goodsTitle;
        private String promotePrice;
        private String isPromote;
        private String price;
        private String thumImg;
        private String detailImgs;


        private String icon;
        private String totalSoldOut;
        private String totalShare;
        private String salesNum;
        private String salesUnit;
        private String weight;
        private String weightUnit;
        private String inventory;
        private String pack;
        private String description;
        private List<String> tags;
        public String getDescription() {
            return description;
        }
        public String getDetailImgs() {
            return detailImgs;
        }

        public void setDetailImgs(String detailImgs) {
            this.detailImgs = detailImgs;
        }


        public void setDescription(String description) {
            this.description = description;
        }



        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
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

        public String getGoodsTitle() {
            return goodsTitle;
        }

        public void setGoodsTitle(String goodsTitle) {
            this.goodsTitle = goodsTitle;
        }

        public String getPromotePrice() {
            return promotePrice;
        }

        public void setPromotePrice(String promotePrice) {
            this.promotePrice = promotePrice;
        }

        public String getIsPromote() {
            return isPromote;
        }

        public void setIsPromote(String isPromote) {
            this.isPromote = isPromote;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getThumImg() {
            return thumImg;
        }

        public void setThumImg(String thumImg) {
            this.thumImg = thumImg;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTotalSoldOut() {
            return totalSoldOut;
        }

        public void setTotalSoldOut(String totalSoldOut) {
            this.totalSoldOut = totalSoldOut;
        }

        public String getTotalShare() {
            return totalShare;
        }

        public void setTotalShare(String totalShare) {
            this.totalShare = totalShare;
        }

        public String getSalesNum() {
            return salesNum;
        }

        public void setSalesNum(String salesNum) {
            this.salesNum = salesNum;
        }

        public String getSalesUnit() {
            return salesUnit;
        }

        public void setSalesUnit(String salesUnit) {
            this.salesUnit = salesUnit;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getWeightUnit() {
            return weightUnit;
        }

        public void setWeightUnit(String weightUnit) {
            this.weightUnit = weightUnit;
        }

        public String getInventory() {
            return inventory;
        }

        public void setInventory(String inventory) {
            this.inventory = inventory;
        }

        public String getPack() {
            return pack;
        }

        public void setPack(String pack) {
            this.pack = pack;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
