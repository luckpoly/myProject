package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by sisi on 16/3/9.
 */
public class AddressListJsonData {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"limit":10,"prePage":0,"start":0,"currentPage":1,"nextPage":2,"totalCount":"6","list":[{"addressId":"73","userId":"59","contact":"您","tel":"18300709012","lng":"121.421768","lat":"31.204714","address":"上海市长宁区新华路定西路(公交站)","address1":"111","addTime":"1465730548"},{"addressId":"72","userId":"59","contact":"赵","tel":"18300709012","lng":"121.50834","lat":"31.313214","address":"1102","address1":"上海市杨浦区三门路(地铁站)","addTime":"1465727776"},{"addressId":"64","userId":"59","contact":"马云","tel":"18300709011","lng":"121.487032","lat":"31.31154","address":"1101","address1":"上海市宝山区三门路","addTime":"1465279300"},{"addressId":"60","userId":"59","contact":"qqq","tel":"18300709011","lng":"121.488547","lat":"31.224398","address":"11","address1":"上海市黄浦区河南南路口","addTime":"1465199341"},{"addressId":"55","userId":"59","contact":"桂乐","tel":"13601800022","lng":"121.416587","lat":"31.218442","address":"11","address1":"上海市长宁区中山公园地铁站2号口","addTime":"1464963481"},{"addressId":"43","userId":"59","contact":"赵增聚","tel":"18300709012","lng":"121.485573","lat":"31.315287","address":"666","address1":"上海市宝山区逸仙路安汾路(公交站)","addTime":"1462241808"}]}
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
     * totalCount : 6
     * list : [{"addressId":"73","userId":"59","contact":"您","tel":"18300709012","lng":"121.421768","lat":"31.204714","address":"上海市长宁区新华路定西路(公交站)","address1":"111","addTime":"1465730548"},{"addressId":"72","userId":"59","contact":"赵","tel":"18300709012","lng":"121.50834","lat":"31.313214","address":"1102","address1":"上海市杨浦区三门路(地铁站)","addTime":"1465727776"},{"addressId":"64","userId":"59","contact":"马云","tel":"18300709011","lng":"121.487032","lat":"31.31154","address":"1101","address1":"上海市宝山区三门路","addTime":"1465279300"},{"addressId":"60","userId":"59","contact":"qqq","tel":"18300709011","lng":"121.488547","lat":"31.224398","address":"11","address1":"上海市黄浦区河南南路口","addTime":"1465199341"},{"addressId":"55","userId":"59","contact":"桂乐","tel":"13601800022","lng":"121.416587","lat":"31.218442","address":"11","address1":"上海市长宁区中山公园地铁站2号口","addTime":"1464963481"},{"addressId":"43","userId":"59","contact":"赵增聚","tel":"18300709012","lng":"121.485573","lat":"31.315287","address":"666","address1":"上海市宝山区逸仙路安汾路(公交站)","addTime":"1462241808"}]
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
         * addressId : 73
         * userId : 59
         * contact : 您
         * tel : 18300709012
         * lng : 121.421768
         * lat : 31.204714
         * address : 上海市长宁区新华路定西路(公交站)
         * address1 : 111
         * addTime : 1465730548
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
            private String addressId;
            private String userId;
            private String contact;
            private String tel;
            private String lng;
            private String lat;
            private String address;
            private String addressDetail;
            private String tag;
            private String sex;
            private String num;
            private String isDefault;

            public String getIsDefault() {
                return isDefault;
            }

            public void setIsDefault(String isDefault) {
                this.isDefault = isDefault;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getAddressDetail() {
                return addressDetail;
            }

            public void setAddressDetail(String addressDetail) {
                this.addressDetail = addressDetail;
            }

            private String addTime;

            public String getAddressId() {
                return addressId;
            }

            public void setAddressId(String addressId) {
                this.addressId = addressId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
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


            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }
        }
    }
}
