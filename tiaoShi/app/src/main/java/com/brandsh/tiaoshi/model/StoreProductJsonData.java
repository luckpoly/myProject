package com.brandsh.tiaoshi.model;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by apple on 16/2/21.
 */
public class StoreProductJsonData {


    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"limit":15,"prePage":0,"start":0,"currentPage":1,"nextPage":2,"totalCount":"1","salesStatus":"1","list":[{"shopId":"60","name":"呵呵水果铺","goodsId":"243","goodsName":"榴莲","promotePrice":"60","ptice":"0","thumImg":"","icon":"","totalSoldOut":"0","salesUnit":"个","star":"0","tags":[""],"weight":"1","weightUnit":"kg","inventory":"-1","salesNum":"1","auditStatus":"1","activityName":""}]}
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
     * salesStatus : 1
     * list : [{"shopId":"60","name":"呵呵水果铺","goodsId":"243","goodsName":"榴莲","promotePrice":"60","ptice":"0","thumImg":"","icon":"","totalSoldOut":"0","salesUnit":"个","star":"0","tags":[""],"weight":"1","weightUnit":"kg","inventory":"-1","salesNum":"1","auditStatus":"1","activityName":""}]
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
         * goodsId : 243
         * goodsName : 榴莲
         * promotePrice : 60
         * ptice : 0
         * thumImg :
         * icon :
         * totalSoldOut : 0
         * salesUnit : 个
         * star : 0
         * tags : [""]
         * weight : 1
         * weightUnit : kg
         * inventory : -1
         * salesNum : 1
         * auditStatus : 1
         * activityName :
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
            private String shopName;
            private String goodsId;
            private String goodsName;
            private String goodsTitle;
            private String promotePrice;
            private String price;
            private String thumImg;
            private String icon;
            private String totalSoldOut;
            private String salesUnit;
            private String star;
            private String weight;
            private String weightUnit;
            private String inventory;
            private String salesNum;
            private String auditStatus;
            private String activityName;
            private List<String> tags;
            private int goods_sc_count;
            private String shopSalesStatus;
            private String customCategoryId;
            private String customCategoryName;
            private String freeSend;

            public String getFreeSend() {
                return freeSend;
            }

            public void setFreeSend(String freeSend) {
                this.freeSend = freeSend;
            }

            public String getCustomCategoryId() {
                if (TextUtils.isEmpty(customCategoryId)){
                    return "-1";
                }
                return customCategoryId;
            }
            public void setCustomCategoryId(String customCategoryId) {
                this.customCategoryId = customCategoryId;
            }
            public String getCustomCategoryName() {
                if (customCategoryName.equals("-")){
                    return "默认分类";
                }
                return customCategoryName;
            }

            public void setCustomCategoryName(String customCategoryName) {
                this.customCategoryName = customCategoryName;
            }

            public String getGoodsTitle() {
                return goodsTitle;
            }

            public void setGoodsTitle(String goodsTitle) {
                this.goodsTitle = goodsTitle;
            }
            public String getPack() {
                return pack;
            }

            public void setPack(String pack) {
                this.pack = pack;
            }

            private String pack;

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getShopSalesStatus() {
                return shopSalesStatus;
            }

            public void setShopSalesStatus(String shopSalesStatus) {
                this.shopSalesStatus = shopSalesStatus;
            }

            public int getGoods_sc_count() {
                return goods_sc_count;
            }

            public void setGoods_sc_count(int goods_sc_count) {
                this.goods_sc_count = goods_sc_count;
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

            public String getSalesUnit() {
                return salesUnit;
            }

            public void setSalesUnit(String salesUnit) {
                this.salesUnit = salesUnit;
            }

            public String getStar() {
                return star;
            }

            public void setStar(String star) {
                this.star = star;
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

            public String getSalesNum() {
                return salesNum;
            }

            public void setSalesNum(String salesNum) {
                this.salesNum = salesNum;
            }

            public String getAuditStatus() {
                return auditStatus;
            }

            public void setAuditStatus(String auditStatus) {
                this.auditStatus = auditStatus;
            }

            public String getActivityName() {
                return activityName;
            }

            public void setActivityName(String activityName) {
                this.activityName = activityName;
            }

            public List<String> getTags() {
                return tags;
            }

            public void setTags(List<String> tags) {
                this.tags = tags;
            }
        }
    }
}
