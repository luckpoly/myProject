package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class GroupsModel {

    /**
     * code : 200
     * result : [{"role":0,"group":{"id":"TBoqC9bjc","name":"聊天室","portraitUri":"","creatorId":"KDqFoujiZ","memberCount":2,"maxMemberCount":500}},{"role":0,"group":{"id":"pLto10nzU","name":"哦哦哦","portraitUri":"","creatorId":"KDqFoujiZ","memberCount":2,"maxMemberCount":500}},{"role":0,"group":{"id":"xRtE8MCsD","name":"jjjjj","portraitUri":"","creatorId":"KDqFoujiZ","memberCount":2,"maxMemberCount":500}},{"role":0,"group":{"id":"d6l3N7KX1","name":"哈哈哈哈","portraitUri":"","creatorId":"KDqFoujiZ","memberCount":1,"maxMemberCount":500}},{"role":0,"group":{"id":"QmnE9bLzi","name":"就来了","portraitUri":"http://7xogjk.com1.z0.glb.clouddn.com/Fmazxy74rOjgQVxrauPpZNIargXB","creatorId":"KDqFoujiZ","memberCount":2,"maxMemberCount":500}},{"role":0,"group":{"id":"z8CS1wRMi","name":"啦咯啦咯啦咯啦","portraitUri":"","creatorId":"KDqFoujiZ","memberCount":2,"maxMemberCount":500}}]
     */

    private int code;
    /**
     * role : 0
     * group : {"id":"TBoqC9bjc","name":"聊天室","portraitUri":"","creatorId":"KDqFoujiZ","memberCount":2,"maxMemberCount":500}
     */

    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private int role;
        /**
         * id : TBoqC9bjc
         * name : 聊天室
         * portraitUri :
         * creatorId : KDqFoujiZ
         * memberCount : 2
         * maxMemberCount : 500
         */

        private GroupBean group;

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public GroupBean getGroup() {
            return group;
        }

        public void setGroup(GroupBean group) {
            this.group = group;
        }

        public static class GroupBean {
            private String id;
            private String name;
            private String portraitUri;
            private String creatorId;
            private int memberCount;
            private int maxMemberCount;

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

            public String getPortraitUri() {
                return portraitUri;
            }

            public void setPortraitUri(String portraitUri) {
                this.portraitUri = portraitUri;
            }

            public String getCreatorId() {
                return creatorId;
            }

            public void setCreatorId(String creatorId) {
                this.creatorId = creatorId;
            }

            public int getMemberCount() {
                return memberCount;
            }

            public void setMemberCount(int memberCount) {
                this.memberCount = memberCount;
            }

            public int getMaxMemberCount() {
                return maxMemberCount;
            }

            public void setMaxMemberCount(int maxMemberCount) {
                this.maxMemberCount = maxMemberCount;
            }
        }
    }
}
