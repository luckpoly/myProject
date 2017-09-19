package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */

public class YaodianListModel {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"limit":20,"prePage":0,"start":0,"currentPage":1,"nextPage":2,"totalCount":"1","list":[{"id":"1","name":"药店测试","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1481013601595648.jpg","address":"dddd","distance":"12968552.3328","addressDetail":"3333"}]}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * limit : 20
     * prePage : 0
     * start : 0
     * currentPage : 1
     * nextPage : 2
     * totalCount : 1
     * list : [{"id":"1","name":"药店测试","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1481013601595648.jpg","address":"dddd","distance":"12968552.3328","addressDetail":"3333"}]
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
         * id : 1
         * name : 药店测试
         * icon : http://7xn4ae.com1.z0.glb.clouddn.com/1481013601595648.jpg
         * address : dddd
         * distance : 12968552.3328
         * addressDetail : 3333
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
            private String id;
            private String name;
            private String icon;
            private String address;
            private String distance;
            private String addressDetail;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getAddressDetail() {
                return addressDetail;
            }

            public void setAddressDetail(String addressDetail) {
                this.addressDetail = addressDetail;
            }
        }
    }
}
