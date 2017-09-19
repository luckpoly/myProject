package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class GroupUserQueryModel {

    /**
     * code : 200
     * users : [{"id":"18300703012"},{"id":"18321082889"}]
     */

    private int code;
    /**
     * id : 18300703012
     */

    private List<UsersBean> users;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
