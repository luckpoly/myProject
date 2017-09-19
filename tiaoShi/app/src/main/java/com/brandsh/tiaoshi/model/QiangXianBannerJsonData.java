package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by sisi on 16/3/7.
 */
public class QiangXianBannerJsonData {


    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"name":"首页轮播","code":"APPIndexCarousel","icon":"","intro":"首页的轮播图的推荐位","content":"","link":"","createTime":"","docs":[{"name":"第一个轮播图","subName":"","intro":"","content":"","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1463708690409718.jpg","link":"","href":"","target":"_self","addTime":1463708695},{"name":"第二章轮播图","subName":"","intro":"","content":"","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1463708725301122.jpg","link":"","href":"","target":"_self","addTime":1463708732}]}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * name : 首页轮播
     * code : APPIndexCarousel
     * icon :
     * intro : 首页的轮播图的推荐位
     * content :
     * link :
     * createTime :
     * docs : [{"name":"第一个轮播图","subName":"","intro":"","content":"","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1463708690409718.jpg","link":"","href":"","target":"_self","addTime":1463708695},{"name":"第二章轮播图","subName":"","intro":"","content":"","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1463708725301122.jpg","link":"","href":"","target":"_self","addTime":1463708732}]
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
        private String name;
        private String code;
        private String icon;
        private String intro;
        private String content;
        private String link;
        private String createTime;
        /**
         * name : 第一个轮播图
         * subName :
         * intro :
         * content :
         * img : http://7xn4ae.com1.z0.glb.clouddn.com/1463708690409718.jpg
         * link :
         * href :
         * target : _self
         * addTime : 1463708695
         */

        private List<DocsBean> docs;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<DocsBean> getDocs() {
            return docs;
        }

        public void setDocs(List<DocsBean> docs) {
            this.docs = docs;
        }

        public static class DocsBean {
            private String name;
            private String subName;
            private String intro;
            private String content;
            private String img;
            private String link;
            private String href;
            private String target;
            private int addTime;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSubName() {
                return subName;
            }

            public void setSubName(String subName) {
                this.subName = subName;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public String getTarget() {
                return target;
            }

            public void setTarget(String target) {
                this.target = target;
            }

            public int getAddTime() {
                return addTime;
            }

            public void setAddTime(int addTime) {
                this.addTime = addTime;
            }
        }
    }
}
