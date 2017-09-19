package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/17.
 */

public class YuLeListModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"limit":20,"prePage":0,"start":0,"currentPage":1,"nextPage":2,"totalCount":"2","list":[{"id":"2","title":"上海歌城（淞沪路）","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1481535309629727.jpg","distance":"2907.3827","content":"<p>\r\n\t<img src=\"http://7xn4ae.com1.z0.glb.clouddn.com/contentimg/image/20161215/20161215173922_26535.jpg\" alt=\"\" /> \r\n<\/p>\r\n<p>\r\n\t<br />\r\n<\/p>\r\n<p>\r\n\t<strong>人均<\/strong>：¥66<br />\r\n<strong>地址<\/strong>：淞沪路151号裙房三楼01室<br />\r\n<strong>电话<\/strong>：02151520000<br />\r\n<strong>营业时间<\/strong>：09:00-营业结束\r\n<\/p>\r\n<p>\r\n\t<br />\r\n<\/p>\r\n<p>\r\n\t<img src=\"http://7xn4ae.com1.z0.glb.clouddn.com/contentimg/image/20161215/20161215174124_38446.jpg\" alt=\"\" />\r\n<\/p>","address":"上海市杨浦区淞沪路151号","addressDetail":"裙房三楼01室","tel":"021-51520000","perCost":"80"},{"id":"1","title":"台北纯K（五角场店）","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1481535861156383.jpg","distance":"3209.3603","content":"<p>\r\n\t<img src=\"http://7xn4ae.com1.z0.glb.clouddn.com/contentimg/image/20161215/20161215173228_58151.jpg\" alt=\"\" />\r\n<\/p>\r\n<p>\r\n\t<br />\r\n<\/p>\r\n<p>\r\n\t<strong>人均消费：<\/strong>100元\r\n<\/p>\r\n<p>\r\n\t<strong>电话：<\/strong>021-31155888\r\n<\/p>\r\n<p>\r\n\t<strong>地址：<\/strong>上海市杨浦区翔殷路1088号\r\n<\/p>\r\n<p>\r\n\t<br />\r\n<\/p>\r\n<p>\r\n\t<img src=\"http://7xn4ae.com1.z0.glb.clouddn.com/contentimg/image/20161215/20161215173525_89635.jpg\" alt=\"\" />\r\n<\/p>","address":"上海市杨浦区翔殷路1088号","addressDetail":"1088号凯迪金","tel":"021-31155888","perCost":"100"}]}
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
     * totalCount : 2
     * list : [{"id":"2","title":"上海歌城（淞沪路）","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1481535309629727.jpg","distance":"2907.3827","content":"<p>\r\n\t<img src=\"http://7xn4ae.com1.z0.glb.clouddn.com/contentimg/image/20161215/20161215173922_26535.jpg\" alt=\"\" /> \r\n<\/p>\r\n<p>\r\n\t<br />\r\n<\/p>\r\n<p>\r\n\t<strong>人均<\/strong>：¥66<br />\r\n<strong>地址<\/strong>：淞沪路151号裙房三楼01室<br />\r\n<strong>电话<\/strong>：02151520000<br />\r\n<strong>营业时间<\/strong>：09:00-营业结束\r\n<\/p>\r\n<p>\r\n\t<br />\r\n<\/p>\r\n<p>\r\n\t<img src=\"http://7xn4ae.com1.z0.glb.clouddn.com/contentimg/image/20161215/20161215174124_38446.jpg\" alt=\"\" />\r\n<\/p>","address":"上海市杨浦区淞沪路151号","addressDetail":"裙房三楼01室","tel":"021-51520000","perCost":"80"},{"id":"1","title":"台北纯K（五角场店）","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1481535861156383.jpg","distance":"3209.3603","content":"<p>\r\n\t<img src=\"http://7xn4ae.com1.z0.glb.clouddn.com/contentimg/image/20161215/20161215173228_58151.jpg\" alt=\"\" />\r\n<\/p>\r\n<p>\r\n\t<br />\r\n<\/p>\r\n<p>\r\n\t<strong>人均消费：<\/strong>100元\r\n<\/p>\r\n<p>\r\n\t<strong>电话：<\/strong>021-31155888\r\n<\/p>\r\n<p>\r\n\t<strong>地址：<\/strong>上海市杨浦区翔殷路1088号\r\n<\/p>\r\n<p>\r\n\t<br />\r\n<\/p>\r\n<p>\r\n\t<img src=\"http://7xn4ae.com1.z0.glb.clouddn.com/contentimg/image/20161215/20161215173525_89635.jpg\" alt=\"\" />\r\n<\/p>","address":"上海市杨浦区翔殷路1088号","addressDetail":"1088号凯迪金","tel":"021-31155888","perCost":"100"}]
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
         * id : 2
         * title : 上海歌城（淞沪路）
         * icon : http://7xn4ae.com1.z0.glb.clouddn.com/1481535309629727.jpg
         * distance : 2907.3827
         * content : <p>
         <img src="http://7xn4ae.com1.z0.glb.clouddn.com/contentimg/image/20161215/20161215173922_26535.jpg" alt="" />
         </p>
         <p>
         <br />
         </p>
         <p>
         <strong>人均</strong>：¥66<br />
         <strong>地址</strong>：淞沪路151号裙房三楼01室<br />
         <strong>电话</strong>：02151520000<br />
         <strong>营业时间</strong>：09:00-营业结束
         </p>
         <p>
         <br />
         </p>
         <p>
         <img src="http://7xn4ae.com1.z0.glb.clouddn.com/contentimg/image/20161215/20161215174124_38446.jpg" alt="" />
         </p>
         * address : 上海市杨浦区淞沪路151号
         * addressDetail : 裙房三楼01室
         * tel : 021-51520000
         * perCost : 80
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
            private String title;
            private String icon;
            private String distance;
            private String content;
            private String address;
            private String addressDetail;
            private String tel;
            private String perCost;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAddressDetail() {
                return addressDetail;
            }

            public void setAddressDetail(String addressDetail) {
                this.addressDetail = addressDetail;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getPerCost() {
                return perCost;
            }

            public void setPerCost(String perCost) {
                this.perCost = perCost;
            }
        }
    }
}
