package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by sisi on 16/3/22.
 */
public class QiangXianJsonData {


    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"limit":15,"prePage":0,"start":0,"currentPage":1,"nextPage":2,"totalCount":"1","list":[{"goodsId":"893","goodsName":"下午茶数据","price":"100","unit":"斤","weightUnit":"KG","thumImg":"http://7xn4ae.com1.z0.glb.clouddn.com/1464957249854979.jpg","salesNum":"1","shopId":"93","shopName":"大宝宝","distance":"1246644.9082","tags":[""],"freeSend":"200"}]}
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
     * list : [{"goodsId":"893","goodsName":"下午茶数据","price":"100","unit":"斤","weightUnit":"KG","thumImg":"http://7xn4ae.com1.z0.glb.clouddn.com/1464957249854979.jpg","salesNum":"1","shopId":"93","shopName":"大宝宝","distance":"1246644.9082","tags":[""],"freeSend":"200"}]
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
         * goodsId : 893
         * goodsName : 下午茶数据
         * price : 100
         * unit : 斤
         * weightUnit : KG
         * thumImg : http://7xn4ae.com1.z0.glb.clouddn.com/1464957249854979.jpg
         * salesNum : 1
         * shopId : 93
         * shopName : 大宝宝
         * distance : 1246644.9082
         * tags : [""]
         * freeSend : 200
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
            private String goodsId;
            private String goodsName;
            private String price;
            private String unit;
            private String weightUnit;
            private String thumImg;
            private String salesNum;
            private String shopId;
            private String shopName;
            private String distance;
            private String freeSend;
            private List<String> tags;

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

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getWeightUnit() {
                return weightUnit;
            }

            public void setWeightUnit(String weightUnit) {
                this.weightUnit = weightUnit;
            }

            public String getThumImg() {
                return thumImg;
            }

            public void setThumImg(String thumImg) {
                this.thumImg = thumImg;
            }

            public String getSalesNum() {
                return salesNum;
            }

            public void setSalesNum(String salesNum) {
                this.salesNum = salesNum;
            }

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getFreeSend() {
                return freeSend;
            }

            public void setFreeSend(String freeSend) {
                this.freeSend = freeSend;
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
