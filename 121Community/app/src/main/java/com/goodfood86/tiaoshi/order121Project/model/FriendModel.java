package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class FriendModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"limit":20,"prePage":0,"start":0,"currentPage":1,"nextPage":2,"totalCount":"5","list":[{"id":"9","userId":"20","username":"13601800009","nickname":"","title":"无油红烧排骨","subTitle":"","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1481688741211594.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741774118.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741800297.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741726081.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741366197.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741922856.jpg","content":"<p>\r\n\t说到红烧排骨，有人会说，我不会炒糖色，要么排骨不上色，要么就是排骨会苦的不能吃。确实，炒糖色是个技术活，火候掌握不好的人很多。那么我今天就分享给大家一道不用炒糖色的红烧排骨，它不用放一滴油，不用炒糖色，也不用过多的调味品，吃起来清香不油腻，这样更健康。\r\n<\/p>\r\n<strong>用料<\/strong><br />\r\n排骨 1000g, 红烧酱油，姜，八角，冰糖，精盐 少许。<br />\r\n<br />\r\n<strong>做法<\/strong><br />\r\n1，姜切成片或是拍破，用来去腥增鲜；八角用来增香；冰糖和红烧酱油用来给排骨提色。<br />\r\n2，先把排骨剁成小块儿清洗干净，然后入冷水锅小火煮开，把血沫煮出来，捞出排骨用热水冲洗干净，放进苏泊尔电压力锅内胆。<br />\r\n3，把第一步中的调味料及适量盐放进锅中。<br />\r\n4，添加适量开水，记得是开水哦！不要用凉水，会影响排骨的口感和味道。<br />\r\n5，20分钟后，程序结束，排骨已软烂鲜香，香气扑鼻。<br />\r\n<div>\r\n\t<br />\r\n<\/div>","createTime":"1481688746"},{"id":"8","userId":"1014","username":"13641649286","nickname":"9286","title":"地三鲜","subTitle":"","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1481688646684577.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688646969293.jpg","content":"<br />\r\n什么菜做法简单，美味又营养？当然地三鲜！<br />\r\n<br />\r\n<strong>主料<\/strong>：土豆1个，茄子2个，青椒1个，<br />\r\n<strong>调料<\/strong>：烹调油，酱油，糖，盐，葱花，蒜蓉，生粉，高汤各适量<br />\r\n<br />\r\n<strong>做法<\/strong><br />\r\n1、茄子和土豆去皮，切成滚刀块;；青椒手掰成小块；<br />\r\n2、锅中倒入多一些的油，七成热时，先将土豆块放入，炸成金黄色，略显透明时捞出备用；<br />\r\n3、再将茄子倒入油锅，炸至金黄色，加入青椒块即一起捞起；<br />\r\n4、以少量热油爆香葱花及蒜茸，加高汤、生抽、糖、盐、茄子、土豆和青椒块略烧；<br />\r\n5、加入水生粉大火收汁即可。<br />","createTime":"1481688650"}]}
     */

    private String respCode;
    private String respMsg;
    /**
     * limit : 20
     * prePage : 0
     * start : 0
     * currentPage : 1
     * nextPage : 2
     * totalCount : 5
     * list : [{"id":"9","userId":"20","username":"13601800009","nickname":"","title":"无油红烧排骨","subTitle":"","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1481688741211594.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741774118.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741800297.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741726081.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741366197.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741922856.jpg","content":"<p>\r\n\t说到红烧排骨，有人会说，我不会炒糖色，要么排骨不上色，要么就是排骨会苦的不能吃。确实，炒糖色是个技术活，火候掌握不好的人很多。那么我今天就分享给大家一道不用炒糖色的红烧排骨，它不用放一滴油，不用炒糖色，也不用过多的调味品，吃起来清香不油腻，这样更健康。\r\n<\/p>\r\n<strong>用料<\/strong><br />\r\n排骨 1000g, 红烧酱油，姜，八角，冰糖，精盐 少许。<br />\r\n<br />\r\n<strong>做法<\/strong><br />\r\n1，姜切成片或是拍破，用来去腥增鲜；八角用来增香；冰糖和红烧酱油用来给排骨提色。<br />\r\n2，先把排骨剁成小块儿清洗干净，然后入冷水锅小火煮开，把血沫煮出来，捞出排骨用热水冲洗干净，放进苏泊尔电压力锅内胆。<br />\r\n3，把第一步中的调味料及适量盐放进锅中。<br />\r\n4，添加适量开水，记得是开水哦！不要用凉水，会影响排骨的口感和味道。<br />\r\n5，20分钟后，程序结束，排骨已软烂鲜香，香气扑鼻。<br />\r\n<div>\r\n\t<br />\r\n<\/div>","createTime":"1481688746"},{"id":"8","userId":"1014","username":"13641649286","nickname":"9286","title":"地三鲜","subTitle":"","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1481688646684577.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688646969293.jpg","content":"<br />\r\n什么菜做法简单，美味又营养？当然地三鲜！<br />\r\n<br />\r\n<strong>主料<\/strong>：土豆1个，茄子2个，青椒1个，<br />\r\n<strong>调料<\/strong>：烹调油，酱油，糖，盐，葱花，蒜蓉，生粉，高汤各适量<br />\r\n<br />\r\n<strong>做法<\/strong><br />\r\n1、茄子和土豆去皮，切成滚刀块;；青椒手掰成小块；<br />\r\n2、锅中倒入多一些的油，七成热时，先将土豆块放入，炸成金黄色，略显透明时捞出备用；<br />\r\n3、再将茄子倒入油锅，炸至金黄色，加入青椒块即一起捞起；<br />\r\n4、以少量热油爆香葱花及蒜茸，加高汤、生抽、糖、盐、茄子、土豆和青椒块略烧；<br />\r\n5、加入水生粉大火收汁即可。<br />","createTime":"1481688650"}]
     */

    private DataBean data;

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

    public static class DataBean {
        private int limit;
        private int prePage;
        private int start;
        private int currentPage;
        private int nextPage;
        private String totalCount;
        /**
         * id : 9
         * userId : 20
         * username : 13601800009
         * nickname :
         * title : 无油红烧排骨
         * subTitle :
         * imgs : http://7xn4ae.com1.z0.glb.clouddn.com/1481688741211594.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741774118.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741800297.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741726081.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741366197.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481688741922856.jpg
         * content : <p>
         说到红烧排骨，有人会说，我不会炒糖色，要么排骨不上色，要么就是排骨会苦的不能吃。确实，炒糖色是个技术活，火候掌握不好的人很多。那么我今天就分享给大家一道不用炒糖色的红烧排骨，它不用放一滴油，不用炒糖色，也不用过多的调味品，吃起来清香不油腻，这样更健康。
         </p>
         <strong>用料</strong><br />
         排骨 1000g, 红烧酱油，姜，八角，冰糖，精盐 少许。<br />
         <br />
         <strong>做法</strong><br />
         1，姜切成片或是拍破，用来去腥增鲜；八角用来增香；冰糖和红烧酱油用来给排骨提色。<br />
         2，先把排骨剁成小块儿清洗干净，然后入冷水锅小火煮开，把血沫煮出来，捞出排骨用热水冲洗干净，放进苏泊尔电压力锅内胆。<br />
         3，把第一步中的调味料及适量盐放进锅中。<br />
         4，添加适量开水，记得是开水哦！不要用凉水，会影响排骨的口感和味道。<br />
         5，20分钟后，程序结束，排骨已软烂鲜香，香气扑鼻。<br />
         <div>
         <br />
         </div>
         * createTime : 1481688746
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
            private String userId;
            private String username;
            private String nickname;
            private String title;
            private String subTitle;
            private String imgs;
            private String content;
            private String createTime;
            private String icon;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
