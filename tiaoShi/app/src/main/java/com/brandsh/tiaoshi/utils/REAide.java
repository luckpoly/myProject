package com.brandsh.tiaoshi.utils;

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
//        return phoneNum
//                .matches("^((13[0-9])|(15[^4,\\D])|(18[^4])|(147))\\d{8}$");
        return phoneNum
                .matches("^(1)\\d{10}$");
    }

}
