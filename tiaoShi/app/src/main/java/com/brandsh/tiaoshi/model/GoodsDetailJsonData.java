package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by apple on 16/2/22.
 */
public class GoodsDetailJsonData {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : [{"goodsId":"903","goodsName":"我问问","price":"66","promotePrice":"45","salesNum":"1","totalSoldOut":"50","salesUnit":"箱","description":"男的女的你猜呢","tags":[""],"star":"5","icon":"","isPromote":"1","goodsRotateImgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952204775121.jpg","goodsDetialImgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952204775121.jpg","minBuy":"1","maxBuy":"0","thumImg":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952196148646.jpg","inventory":"-1","weight":"0","weightUnit":"kg","activityName":"","salesStatus":"OPEN","pack":"","categoryRootCode":"Fruit","typeId":"1","typeIds":"","typeName":"","typeNames":"","categoryId":"135,138","categoryIds":"","categoryName":"","categoryNames":"","customId":"0","customIds":"","customName":"","customNames":""}]
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    private String debugInfo;
    /**
     * goodsId : 903
     * goodsName : 我问问
     * price : 66
     * promotePrice : 45
     * salesNum : 1
     * totalSoldOut : 50
     * salesUnit : 箱
     * description : 男的女的你猜呢
     * tags : [""]
     * star : 5
     * icon :
     * isPromote : 1
     * goodsRotateImgs : http://7xn4ae.com1.z0.glb.clouddn.com/1464952204775121.jpg
     * goodsDetialImgs : http://7xn4ae.com1.z0.glb.clouddn.com/1464952204775121.jpg
     * minBuy : 1
     * maxBuy : 0
     * thumImg : http://7xn4ae.com1.z0.glb.clouddn.com/1464952196148646.jpg
     * inventory : -1
     * weight : 0
     * weightUnit : kg
     * activityName :
     * salesStatus : OPEN
     * pack :
     * categoryRootCode : Fruit
     * typeId : 1
     * typeIds :
     * typeName :
     * typeNames :
     * categoryId : 135,138
     * categoryIds :
     * categoryName :
     * categoryNames :
     * customId : 0
     * customIds :
     * customName :
     * customNames :
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

    public static class DataBean {
        private String goodsId;
        private String shopId;
        private String goodsName;
        private String price;
        private String promotePrice;
        private String salesNum;
        private String totalSoldOut;
        private String salesUnit;
        private String description;
        private String star;
        private String icon;
        private String isPromote;
        private String goodsRotateImgs;
        private String goodsDetialImgs;
        private String minBuy;
        private String maxBuy;
        private String thumImg;
        private String inventory;
        private String weight;
        private String weightUnit;
        private String activityName;
        private String salesStatus;
        private String pack;
        private String categoryRootCode;
        private String typeId;
        private String typeIds;
        private String typeName;
        private String typeNames;
        private String categoryId;
        private String categoryIds;
        private String categoryName;
        private String categoryNames;
        private String customId;
        private String customIds;
        private String customName;
        private String customNames;
        private List<String> tags;

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPromotePrice() {
            return promotePrice;
        }

        public void setPromotePrice(String promotePrice) {
            this.promotePrice = promotePrice;
        }

        public String getSalesNum() {
            return salesNum;
        }

        public void setSalesNum(String salesNum) {
            this.salesNum = salesNum;
        }

        public String getTotalSoldOut() {
            return totalSoldOut;
        }

        public void setTotalSoldOut(String totalSoldOut) {
            this.totalSoldOut = totalSoldOut;
        }

        public String getSalesUnit() {
            return salesUnit;
        }

        public void setSalesUnit(String salesUnit) {
            this.salesUnit = salesUnit;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIsPromote() {
            return isPromote;
        }

        public void setIsPromote(String isPromote) {
            this.isPromote = isPromote;
        }

        public String getGoodsRotateImgs() {
            return goodsRotateImgs;
        }

        public void setGoodsRotateImgs(String goodsRotateImgs) {
            this.goodsRotateImgs = goodsRotateImgs;
        }

        public String getGoodsDetialImgs() {
            return goodsDetialImgs;
        }

        public void setGoodsDetialImgs(String goodsDetialImgs) {
            this.goodsDetialImgs = goodsDetialImgs;
        }

        public String getMinBuy() {
            return minBuy;
        }

        public void setMinBuy(String minBuy) {
            this.minBuy = minBuy;
        }

        public String getMaxBuy() {
            return maxBuy;
        }

        public void setMaxBuy(String maxBuy) {
            this.maxBuy = maxBuy;
        }

        public String getThumImg() {
            return thumImg;
        }

        public void setThumImg(String thumImg) {
            this.thumImg = thumImg;
        }

        public String getInventory() {
            return inventory;
        }

        public void setInventory(String inventory) {
            this.inventory = inventory;
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

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getSalesStatus() {
            return salesStatus;
        }

        public void setSalesStatus(String salesStatus) {
            this.salesStatus = salesStatus;
        }

        public String getPack() {
            return pack;
        }

        public void setPack(String pack) {
            this.pack = pack;
        }

        public String getCategoryRootCode() {
            return categoryRootCode;
        }

        public void setCategoryRootCode(String categoryRootCode) {
            this.categoryRootCode = categoryRootCode;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getTypeIds() {
            return typeIds;
        }

        public void setTypeIds(String typeIds) {
            this.typeIds = typeIds;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeNames() {
            return typeNames;
        }

        public void setTypeNames(String typeNames) {
            this.typeNames = typeNames;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryIds() {
            return categoryIds;
        }

        public void setCategoryIds(String categoryIds) {
            this.categoryIds = categoryIds;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryNames() {
            return categoryNames;
        }

        public void setCategoryNames(String categoryNames) {
            this.categoryNames = categoryNames;
        }

        public String getCustomId() {
            return customId;
        }

        public void setCustomId(String customId) {
            this.customId = customId;
        }

        public String getCustomIds() {
            return customIds;
        }

        public void setCustomIds(String customIds) {
            this.customIds = customIds;
        }

        public String getCustomName() {
            return customName;
        }

        public void setCustomName(String customName) {
            this.customName = customName;
        }

        public String getCustomNames() {
            return customNames;
        }

        public void setCustomNames(String customNames) {
            this.customNames = customNames;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
