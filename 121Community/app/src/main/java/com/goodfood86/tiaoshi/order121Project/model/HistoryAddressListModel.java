package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class HistoryAddressListModel {


    /**
     * respCode : 0
     * respMsg : 获取地址成功！
     * data : {"start":0,"limit":50,"prePage":1,"nextPage":2,"currentPage":1,"list":[{"id":3,"uid":130,"city":"上海","area":"杨浦区","address":"五角场12","addressDetail":"五角场12","lng":232,"lat":3434,"isDefault":1,"addTime":"2016-04-06 14:13:58","updateTime":"2016-04-06 14:13:58"}],"count":1}
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    /**
     * start : 0
     * limit : 50
     * prePage : 1
     * nextPage : 2
     * currentPage : 1
     * list : [{"id":3,"uid":130,"city":"上海","area":"杨浦区","address":"五角场12","addressDetail":"五角场12","lng":232,"lat":3434,"isDefault":1,"addTime":"2016-04-06 14:13:58","updateTime":"2016-04-06 14:13:58"}]
     * count : 1
     */

    private DataEntity data;
    private List<?> debugInfo;

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setDebugInfo(List<?> debugInfo) {
        this.debugInfo = debugInfo;
    }

    public int getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public DataEntity getData() {
        return data;
    }

    public List<?> getDebugInfo() {
        return debugInfo;
    }

    public static class DataEntity {
        private int start;
        private int limit;
        private int prePage;
        private int nextPage;
        private int currentPage;
        private int count;
        /**
         * id : 3
         * uid : 130
         * city : 上海
         * area : 杨浦区
         * address : 五角场12
         * addressDetail : 五角场12
         * lng : 232
         * lat : 3434
         * isDefault : 1
         * addTime : 2016-04-06 14:13:58
         * updateTime : 2016-04-06 14:13:58
         */

        private List<ListEntity> list;

        public void setStart(int start) {
            this.start = start;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public int getStart() {
            return start;
        }

        public int getLimit() {
            return limit;
        }

        public int getPrePage() {
            return prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getCount() {
            return count;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private int id;
            private int uid;
            private String city;
            private String area;
            private String address;
            private String addressDetail;
            private String lng;
            private String lat;
            private int isDefault;
            private String addTime;
            private String updateTime;

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            private String phone;

            public void setId(int id) {
                this.id = id;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public void setAddressDetail(String addressDetail) {
                this.addressDetail = addressDetail;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public void setIsDefault(int isDefault) {
                this.isDefault = isDefault;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public int getId() {
                return id;
            }

            public int getUid() {
                return uid;
            }

            public String getCity() {
                return city;
            }

            public String getArea() {
                return area;
            }

            public String getAddress() {
                return address;
            }

            public String getAddressDetail() {
                return addressDetail;
            }

            public String getLng() {
                return lng;
            }

            public String getLat() {
                return lat;
            }

            public int getIsDefault() {
                return isDefault;
            }

            public String getAddTime() {
                return addTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }
        }
    }
}
