package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by tiashiwang on 2016/5/27.
 */
public class DiscountListModel {


    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"limit":10,"prePage":0,"start":0,"currentPage":1,"nextPage":2,"totalCount":"2","list":[{"discountID":1,"orderID":"","code":"0","discountImg":"","discount":20,"condition":200,"startTime":0,"expireTime":0},{"discountID":2,"orderID":"","code":"","discountImg":"","discount":20,"condition":300,"startTime":0,"expireTime":0}]}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * limit : 10
     * prePage : 0
     * start : 0
     * currentPage : 1
     * nextPage : 2
     * totalCount : 2
     * list : [{"discountID":1,"orderID":"","code":"0","discountImg":"","discount":20,"condition":200,"startTime":0,"expireTime":0},{"discountID":2,"orderID":"","code":"","discountImg":"","discount":20,"condition":300,"startTime":0,"expireTime":0}]
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
         * discountID : 1
         * orderID :
         * code : 0
         * discountImg :
         * discount : 20
         * condition : 200
         * startTime : 0
         * expireTime : 0
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
            private int discountID;
            private String orderID;
            private String code;
            private String discountImg;
            private int discount;
            private int condition;
            private int startTime;
            private int expireTime;

            public int getDiscountID() {
                return discountID;
            }

            public void setDiscountID(int discountID) {
                this.discountID = discountID;
            }

            public String getOrderID() {
                return orderID;
            }

            public void setOrderID(String orderID) {
                this.orderID = orderID;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDiscountImg() {
                return discountImg;
            }

            public void setDiscountImg(String discountImg) {
                this.discountImg = discountImg;
            }

            public int getDiscount() {
                return discount;
            }

            public void setDiscount(int discount) {
                this.discount = discount;
            }

            public int getCondition() {
                return condition;
            }

            public void setCondition(int condition) {
                this.condition = condition;
            }

            public int getStartTime() {
                return startTime;
            }

            public void setStartTime(int startTime) {
                this.startTime = startTime;
            }

            public int getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(int expireTime) {
                this.expireTime = expireTime;
            }
        }
    }
}
