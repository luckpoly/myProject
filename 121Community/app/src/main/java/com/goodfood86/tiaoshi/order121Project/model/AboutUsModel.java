package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by tiashiwang on 2016/4/13.
 */
public class AboutUsModel {


    /**
     * respCode : 0
     * respMsg : 获取文档成功！
     * data : {"id":1,"code":"UserWordAboutus","name":"关于我们","img":"","href":"#","target":"_blank","link":"","intro":"用户版的关于我们","content":"121下单致力于为用户提供全程可监控的专人直送,力争在2h送达到用户手中。&lt;br /&gt;\r\n公司隶属于上海烯配信息科技有限公司。","sort":100,"create_time":1462953293,"update_time":1462953771,"status_display":1,"words":[]}
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    /**
     * id : 1
     * code : UserWordAboutus
     * name : 关于我们
     * img :
     * href : #
     * target : _blank
     * link :
     * intro : 用户版的关于我们
     * content : 121下单致力于为用户提供全程可监控的专人直送,力争在2h送达到用户手中。&lt;br /&gt;
     公司隶属于上海烯配信息科技有限公司。
     * sort : 100
     * create_time : 1462953293
     * update_time : 1462953771
     * status_display : 1
     * words : []
     */

    private DataBean data;
    private List<?> debugInfo;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
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

    public List<?> getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(List<?> debugInfo) {
        this.debugInfo = debugInfo;
    }

    public static class DataBean {
        private int id;
        private String code;
        private String name;
        private String img;
        private String href;
        private String target;
        private String link;
        private String intro;
        private String content;


        private String sort;
        private String create_time;
        private String update_time;
        private String status_display;
        private List<?> words;
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }


        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getStatus_display() {
            return status_display;
        }

        public void setStatus_display(String status_display) {
            this.status_display = status_display;
        }

        public List<?> getWords() {
            return words;
        }

        public void setWords(List<?> words) {
            this.words = words;
        }
    }
}
