package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by sisi on 16/3/8.
 */
public class SearchJsonData {

    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"limit":15,"prePage":0,"start":0,"currentPage":1,"nextPage":2,"totalCount":"2","list":[{"shopId":"60","freeSend":"55","name":"呵呵水果铺","distance":"22.0935","goodsId":"592","goodsName":"香蕉123123","promotePrice":"20","isPromote":"0","price":"0","thumImg":"","icon":"","totalSoldOut":"0","salesUnit":"斤","tags":["http://7xn4ae.com1.z0.glb.clouddn.com/taocan%E5%A5%97%E9%A4%90.png"],"weight":"1","weightUnit":"kg","inventory":"-1","salesNum":"1","auditStatus":"1"},{"shopId":"60","freeSend":"55","name":"呵呵水果铺","distance":"22.0935","goodsId":"448","goodsName":"芒果2","promotePrice":"56","isPromote":"0","price":"50","thumImg":"","icon":"","totalSoldOut":"0","salesUnit":"箱","tags":[""],"weight":"1","weightUnit":"kg","inventory":"-1","salesNum":"64","auditStatus":"1"}]}
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
     * totalCount : 2
     * list : [{"shopId":"60","freeSend":"55","name":"呵呵水果铺","distance":"22.0935","goodsId":"592","goodsName":"香蕉123123","promotePrice":"20","isPromote":"0","price":"0","thumImg":"","icon":"","totalSoldOut":"0","salesUnit":"斤","tags":["http://7xn4ae.com1.z0.glb.clouddn.com/taocan%E5%A5%97%E9%A4%90.png"],"weight":"1","weightUnit":"kg","inventory":"-1","salesNum":"1","auditStatus":"1"},{"shopId":"60","freeSend":"55","name":"呵呵水果铺","distance":"22.0935","goodsId":"448","goodsName":"芒果2","promotePrice":"56","isPromote":"0","price":"50","thumImg":"","icon":"","totalSoldOut":"0","salesUnit":"箱","tags":[""],"weight":"1","weightUnit":"kg","inventory":"-1","salesNum":"64","auditStatus":"1"}]
     */

    private DataBean data;
    private String debugInfo;

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
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
         * freeSend : 55
         * name : 呵呵水果铺
         * distance : 22.0935
         * goodsId : 592
         * goodsName : 香蕉123123
         * promotePrice : 20
         * isPromote : 0
         * price : 0
         * thumImg :
         * icon :
         * totalSoldOut : 0
         * salesUnit : 斤
         * tags : ["http://7xn4ae.com1.z0.glb.clouddn.com/taocan%E5%A5%97%E9%A4%90.png"]
         * weight : 1
         * weightUnit : kg
         * inventory : -1
         * salesNum : 1
         * auditStatus : 1
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
            private String freeSend;
            private String name;
            private String distance;
            private String goodsId;
            private String goodsName;
            private String promotePrice;
            private String isPromote;
            private String price;
            private String thumImg;
            private String icon;
            private String totalSoldOut;
            private String salesUnit;
            private String weight;
            private String weightUnit;
            private String inventory;
            private String salesNum;
            private String auditStatus;
            private List<String> tags;

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public String getFreeSend() {
                return freeSend;
            }

            public void setFreeSend(String freeSend) {
                this.freeSend = freeSend;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
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

            public List<String> getTags() {
                return tags;
            }

            public void setTags(List<String> tags) {
                this.tags = tags;
            }
        }
    }
}
