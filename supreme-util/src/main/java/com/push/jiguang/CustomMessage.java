package com.push.jiguang;

/**
 * Created by Victor on 2017/5/24.
 */
public class CustomMessage {


    public CustomMessage(MsgTypeEnum typeEnum, String content){
        this.type = typeEnum.toString();
        this.msg = content;
    }

    /**
     * 消息大类型
     * 必填
     * C 客户消息、A 日程消息、P 产品消息、O 运维提醒、D 存管公告、S 系统通知
     */
    private String type;

    /**
     * 消息编码
     * 可以为空,app暂时用不到
     */
    private String code;

    /**
     * 随机数
     * 可以为空,app暂时用不到
     */
    private String nonce;

    /**
     * 消息内容
     * 必填
     */
    private String msg;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

