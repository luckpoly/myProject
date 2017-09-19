package com.goodfood86.tiaoshi.order121Project.utils;

/**
 * 正则表达式助手
 * Created by libokang on 15/10/9.
 */
public class REAide {

    /**
     * 验证手机号码
     *
     * @param phoneNum
     * @return
     */
    public final static boolean checkPhoneNumValide(String phoneNum) {
        return phoneNum
                .matches("^(1)\\d{10}$");
    }

}
