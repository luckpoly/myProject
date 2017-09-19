package com.brandsh.tiaoshi.utils;

/**
 * 订单状态工具类
 * Created by Administrator on 2016/7/26.
 */
public class OrderStatusUtil {
    /**
     * 订单状态返回类
     * status-订单状态
     * deliveryStatus-配送状态
     * refundStatus-退款状态
     * cancelStatus-取消状态/异常状态
     * eveluateStatus-评价状态
     * 返回 待支付、待配送、配送中、待评价、已完成、订单异常、退款\售后、订单已取消、配送完成
     */
    public static String getOrderStatus(String status,String deliveryStatus,String refundStatus,String cancelStatus,String evaluateStatus ){
        String str="";
        if (status.equals("PAYING")){
            return "待支付";
        }else if (status.equals("PAY")){
            //取消状态
            if (cancelStatus.equals("NORMAL")){
                if (deliveryStatus.equals("PRE")){
                    if (refundStatus.equals("FAILURE")||refundStatus.equals("FOR_REFUND")||refundStatus.equals("REFUNDING")){
                        return "退款中";
                    }else if (refundStatus.equals("OK")){
                        return "退款完成";
                    }else {
                        return "待配送";
                    }
                }else if (deliveryStatus.equals("SENDING")){
                    return "配送中";
                }else if (deliveryStatus.equals("OK")){
                    if (refundStatus.equals("FAILURE")||refundStatus.equals("FOR_REFUND")||refundStatus.equals("REFUNDING")){
                        return "退款中";
                    }else if (refundStatus.equals("OK")){
                        return "退款完成";
                    }else {
                        return "配送完成";
                    }
                }else {
                    return "订单异常";
                }
            }else if (cancelStatus.equals("CANCEL")){
                if (deliveryStatus.equals("OK")&&evaluateStatus.equals("DONE")){
                    return "退款售后";
                }else {
                   if (refundStatus.equals("FAILURE")||refundStatus.equals("FOR_REFUND")||refundStatus.equals("REFUNDING")){
                       return "退款中";
                   }else if (refundStatus.equals("OK")){
                       return "退款完成";
                   }else {
                       return "订单已取消";
                   }
                }
            }else {
                return "订单异常";
            }

        }else if (status.equals("OK")){
            if (evaluateStatus.equals("NOT_YET")){
                return "待评价";
            }else if (evaluateStatus.equals("DONE")){
                return "已完成";
            }
        }else {
            return "订单异常";
        }




        return str;
    }
}
