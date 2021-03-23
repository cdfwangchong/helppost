package com.cdfg.helppost.pojo.until;

/**
 * 常量
 */
public final class Constant {
    public static int errCode = 1001;
    public static String errMsg = "存储过程返回值异常";

    public static int sucCode = 1002;
    public static String sucMsg = "成功";

    public static int errCode_2 = 1003;
    public static String errMsg_2 = "员工不存在或者密码错误";

    public static int errCode_3 = 1004;
    public static String errMsg_3 = "表数据查询返回值异常";

    public static int errCode_4 = 1005;
    public static String errMsg_4 = "获取到的Token值为空";

    public static int errCode_5 = 1006;
    public static String errMsg_5 = "获取到的对象值为空";

    public static int errCode_6 = 1007;
    public static String errMsg_6 = "数据写入异常";

    public static int errCode_7 = 1008;
    public static String errMsg_7 = "收件人必须是顾客本人";

    public static int errCode_8 = 1009;
    public static String errMsg_8 = "收件地址不能是岛内";

    public static int errCode_9 = 1010;
    public static String errMsg_9 = "List中的数据没有正确写入顾客地址列表";

    public static int errCode14 = 2009;
    public static String errMsg14 = "存在未完结的邮寄申请，不能修改地址";

    public static int errCode_15 = 1010;
    public static String errMsg_15 = "该顾客邮寄商品确认失败";

    public static int errCode_16 = 1011;
    public static String errMsg_16 = "该顾客在用户表中不存在";

    public static int errCode_17 = 1012;
    public static String errMsg_17 = "当日邮寄申请票数已满";

    public static int errCode_18 = 1013;
    public static String errMsg_18 = "改用户在地址管理表中无记录";

    public static int errCode_19 = 3006;
    public static String errMsg_19 = "该邮寄申请单含状态为已提货的提货单，不能取消";

    public static int errCode_20 = 1006;
    public static String errMsg_20 = "获取到的SEQNO值为空";

    public static int errCode_21 = 1007;
    public static String errMsg_21 = "获取到的SEQNO值异常";

    public static int errCode_22 = 1008;
    public static String errMsg_22 = "解绑信息写入异常";
    //设置加密秘钥
    public static final String key = "cdfgsanyamark@6868";

}
