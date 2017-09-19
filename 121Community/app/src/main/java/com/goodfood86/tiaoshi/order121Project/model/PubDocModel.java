package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class PubDocModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"id":"1","code":"BannerIndex","name":"首页banner","link":"","icon":"","intro":"","content":"","nodes":[{"id":"1","name":"首页banner第一张","href":"#","target":"","link":"","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470193368329562.jpg","intro":"","content":""},{"id":"2","name":"首页banner第二张","href":"#","target":"","link":"","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470193426135362.jpg","intro":"","content":""}]}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * id : 1
     * code : BannerIndex
     * name : 首页banner
     * link :
     * icon :
     * intro :
     * content :
     * nodes : [{"id":"1","name":"首页banner第一张","href":"#","target":"","link":"","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470193368329562.jpg","intro":"","content":""},{"id":"2","name":"首页banner第二张","href":"#","target":"","link":"","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470193426135362.jpg","intro":"","content":""}]
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
        private String id;
        private String code;
        private String name;
        private String link;
        private String icon;
        private String intro;
        private String content;
        private String subName;
        private String imgs;

        public String getSubName() {
            return subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        /**
         * id : 1
         * name : 首页banner第一张
         * href : #
         * target :
         * link :
         * img : http://7xn4ae.com1.z0.glb.clouddn.com/1470193368329562.jpg
         * intro :
         * content :
         */

        private List<NodesBean> nodes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
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

        public List<NodesBean> getNodes() {
            return nodes;
        }

        public void setNodes(List<NodesBean> nodes) {
            this.nodes = nodes;
        }

        public static class NodesBean {
            private String id;
            private String name;
            private String subName;
            private String href;
            private String target;
            private String link;
            private String imgs;
            private String intro;
            private String content;
            private String icon;

            public String getImgs() {
                return imgs;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getSubName() {
                return subName;
            }

            public void setSubName(String subName) {
                this.subName = subName;
            }


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

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getImg() {
                return imgs;
            }

            public void setImg(String img) {
                this.imgs = img;
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
        }
    }
}
