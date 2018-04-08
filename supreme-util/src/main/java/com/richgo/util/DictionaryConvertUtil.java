package com.richgo.util;

/**
 * @author Gengkg
 * @create 2017-05-26
 *  多金字典表key由String转换为Integer
 */
public class DictionaryConvertUtil {

    /**
     * 转换多金性别
     * @param type
     * @return
     */
    public static Integer getSexStatus(String type){
        Integer i = null;
        switch (type){
            case "0":  i = 0 ;//女
                break;
            case "1":  i = 1 ;//男
                break;
            default: i = null;
                break;
        }
        return i ;
    }

    /**
     * 转换多金客户类型
     * @param type
     * @return
     */
    public static Integer getCustomerType(String type){
        Integer i = null;
        switch (type){
            case "0":  i = 0 ;//机构
                break;
            case "1":  i = 1 ;//客户
                break;
            default: i = null;
                break;
        }
        return i ;
    }

    /**
     * 转换多金银行卡类型
     * @param type
     * @return
     */
    public static Integer getCardStatus(String type){
        Integer i = null;
        switch (type){
            case "1":  i = 1 ;//正常
                break;
            case "2":  i = 2 ;//注销
                break;
            default: i = null;
                break;
        }
        return i ;
    }

    /**
     * 转换多金客户是否专属
     * @param type
     * @return
     */
    public static Integer getExclusiveCustomer(String type){
        Integer i = null;
        switch (type){
            case "0":  i = 1 ;//非专属
                break;
            case "1":  i = 2 ;//专属
                break;
            default: i = null;
                break;
        }
        return i ;
    }

    /**
     * 转换多金银行卡类型
     * @param type
     *  1:银杏会员
     *  2:白银会员
     *  3:黄金会员
     *  4:白金会员
     *  5:黑金会员
     *  6:钻石会员
     *  @return
     */
    public static Integer getViplevel(String type){
        Integer i = null;
        switch (type){
            case "1":  i = 1 ;
                break;
            case "2":  i = 2 ;
                break;
            case "3":  i = 3 ;
                break;
            case "4":  i = 4 ;
                break;
            case "5":  i = 5 ;
                break;
            case "6":  i = 6 ;
                break;
            case "7":  i = 7;
                break;
            default: i = null;
                break;
        }
        return i ;
    }

    /**
     * 转换多证件类型
     * 由String转为Integer
     * @param type
     *  @return
     */
    public static Integer getCustTypeIsIndividual(String type){
        Integer i = null;
        switch (type){
            case "0":  i = 0 ;
                break;
            case "1":  i = 1 ;
                break;
            case "2":  i = 2 ;
                break;
            case "3":  i = 3 ;
                break;
            case "4":  i = 4 ;
                break;
            case "5":  i = 5 ;
                break;
            case "6":  i = 6 ;
                break;
            case "7":  i = 7;
                break;
            case "8":  i = 8;
                break;
            case "9":  i = 9;
                break;
            case "A":  i = 10;
                break;
            case "B":  i = 11;
                break;
            case "C":  i = 12;
                break;
            case "D":  i = 13;
                break;
            case "E":  i = 14;
                break;
            case "I":  i = 15;
                break;
            case "K":  i = 16;
                break;
            default: i = null;
                break;
        }
        return i ;
    }


    /**
     * 转换多证件类型
     * 由Integer转为String
     * @param type
     *  @return
     */
    public static String getCustTypeIsIndividualToString(Integer type){
        String i = null;
        switch (type){
            case 0:  i = "0" ;
                break;
            case 1:  i = "1" ;
                break;
            case 2:  i = "2" ;
                break;
            case 3:  i = "3" ;
                break;
            case 4:  i = "4" ;
                break;
            case 5:  i = "5" ;
                break;
            case 6:  i = "6" ;
                break;
            case 7:  i = "7";
                break;
            case 8:  i = "8";
                break;
            case 9:  i = "9";
                break;
            case 10:  i = "A";
                break;
            case 11:  i = "B";
                break;
            case 12:  i = "C";
                break;
            case 13:  i = "D";
                break;
            case 14:  i = "E";
                break;
            case 15:  i = "I";
                break;
            case 16:  i = "K";
                break;
            default: i = null;
                break;
        }
        return i ;
    }
}
