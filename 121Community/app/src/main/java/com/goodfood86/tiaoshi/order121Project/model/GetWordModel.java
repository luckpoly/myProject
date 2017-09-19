package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class GetWordModel {


    /**
     * respCode : 0
     * respMsg : 获取文档成功！
     * data : {"id":3,"code":"UserOrderIndex","name":"首页轮换","intro":"表示用户下单首页轮换的几张图片","context":"无","sort":100,"time_create":"","time_update":"","status_display":1,"words":[{"id":1,"id_words_type":3,"img":"http://7xq12g.com1.z0.glb.clouddn.com/0067c291b5366ef74c976b45bfc79a2d.jpg","href":"http://www.86goodfood.com/","target":"_self","intro":"","content":"","sort":1,"time_create":"","time_update":"","status_display":1},{"id":2,"id_words_type":3,"img":"http://7xq12g.com1.z0.glb.clouddn.com/015cd797a060d6676f4718d8db4a2210.jpg","href":"http://www.86goodfood.com/","target":"_self","intro":"","content":"","sort":2,"time_create":"","time_update":"","status_display":1},{"id":3,"id_words_type":3,"img":"http://7xq12g.com1.z0.glb.clouddn.com/0dcc1695a2e608863c68b241ec20d6a0.jpg","href":"http://www.86goodfood.com/","target":"_self","intro":"","content":"","sort":3,"time_create":"","time_update":"","status_display":1}]}
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    /**
     * id : 3
     * code : UserOrderIndex
     * name : 首页轮换
     * intro : 表示用户下单首页轮换的几张图片
     * context : 无
     * sort : 100
     * time_create :
     * time_update :
     * status_display : 1
     * words : [{"id":1,"id_words_type":3,"img":"http://7xq12g.com1.z0.glb.clouddn.com/0067c291b5366ef74c976b45bfc79a2d.jpg","href":"http://www.86goodfood.com/","target":"_self","intro":"","content":"","sort":1,"time_create":"","time_update":"","status_display":1},{"id":2,"id_words_type":3,"img":"http://7xq12g.com1.z0.glb.clouddn.com/015cd797a060d6676f4718d8db4a2210.jpg","href":"http://www.86goodfood.com/","target":"_self","intro":"","content":"","sort":2,"time_create":"","time_update":"","status_display":1},{"id":3,"id_words_type":3,"img":"http://7xq12g.com1.z0.glb.clouddn.com/0dcc1695a2e608863c68b241ec20d6a0.jpg","href":"http://www.86goodfood.com/","target":"_self","intro":"","content":"","sort":3,"time_create":"","time_update":"","status_display":1}]
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
        private int id;
        private String code;
        private String name;
        private String intro;
        private String context;
        private int sort;
        private String time_create;
        private String time_update;
        private int status_display;
        /**
         * id : 1
         * id_words_type : 3
         * img : http://7xq12g.com1.z0.glb.clouddn.com/0067c291b5366ef74c976b45bfc79a2d.jpg
         * href : http://www.86goodfood.com/
         * target : _self
         * intro :
         * content :
         * sort : 1
         * time_create :
         * time_update :
         * status_display : 1
         */

        private List<WordsEntity> words;

        public void setId(int id) {
            this.id = id;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public void setTime_create(String time_create) {
            this.time_create = time_create;
        }

        public void setTime_update(String time_update) {
            this.time_update = time_update;
        }

        public void setStatus_display(int status_display) {
            this.status_display = status_display;
        }

        public void setWords(List<WordsEntity> words) {
            this.words = words;
        }

        public int getId() {
            return id;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getIntro() {
            return intro;
        }

        public String getContext() {
            return context;
        }

        public int getSort() {
            return sort;
        }

        public String getTime_create() {
            return time_create;
        }

        public String getTime_update() {
            return time_update;
        }

        public int getStatus_display() {
            return status_display;
        }

        public List<WordsEntity> getWords() {
            return words;
        }

        public static class WordsEntity {
            private int id;
            private int id_words_type;
            private String img;
            private String href;
            private String target;
            private String intro;
            private String content;
            private int sort;
            private String time_create;
            private String time_update;
            private int status_display;

            public void setId(int id) {
                this.id = id;
            }

            public void setId_words_type(int id_words_type) {
                this.id_words_type = id_words_type;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public void setTarget(String target) {
                this.target = target;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public void setTime_create(String time_create) {
                this.time_create = time_create;
            }

            public void setTime_update(String time_update) {
                this.time_update = time_update;
            }

            public void setStatus_display(int status_display) {
                this.status_display = status_display;
            }

            public int getId() {
                return id;
            }

            public int getId_words_type() {
                return id_words_type;
            }

            public String getImg() {
                return img;
            }

            public String getHref() {
                return href;
            }

            public String getTarget() {
                return target;
            }

            public String getIntro() {
                return intro;
            }

            public String getContent() {
                return content;
            }

            public int getSort() {
                return sort;
            }

            public String getTime_create() {
                return time_create;
            }

            public String getTime_update() {
                return time_update;
            }

            public int getStatus_display() {
                return status_display;
            }
        }
    }
}
