package com.richgo.util;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.richgo.annotation.SensitiveParam;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Date: 2017/8/4
 * Time: 16:58
 * User: yangkai
 * EMail: yangkai01@chtwm.com
 * 敏感信息屏蔽工具
 */
public final class SensitiveParamUtils {

    private final static Logger logger = LoggerFactory.getLogger(SensitiveParamUtils.class);

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     *
     * @param fullName
     * @return
     */
    public static String chineseName(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        String name = StringUtils.left(fullName, 1);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     *
     * @param familyName
     * @param givenName
     * @return
     */
    public static String chineseName(String familyName, String givenName) {
        if (StringUtils.isBlank(familyName) || StringUtils.isBlank(givenName)) {
            return "";
        }
        return chineseName(familyName + givenName);
    }

    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：*************5762>
     *
     * @param id
     * @return
     */
    public static String idCardNum(String id) {
        if (StringUtils.isBlank(id)) {
            return "";
        }
        String num = StringUtils.right(id, 4);
        return StringUtils.leftPad(num, StringUtils.length(id), "*");
    }

    /**
     * [固定电话] 后四位，其他隐藏<例子：****1234>
     *
     * @param num
     * @return
     */
    public static String fixedPhone(String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*");
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     *
     * @param num
     * @return
     */
    public static String mobilePhone(String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.left(num, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*"), "***"));
    }

    /**
     * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****>
     *
     * @param address
     * @param sensitiveSize
     *            敏感信息长度
     * @return
     */
    public static String address(String address, int sensitiveSize) {
        if (StringUtils.isBlank(address)) {
            return "";
        }
        int length = StringUtils.length(address);
        return StringUtils.rightPad(StringUtils.left(address, length - sensitiveSize), length, "*");
    }

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     *
     * @param email
     * @return
     */
    public static String email(String email) {
        if (StringUtils.isBlank(email)) {
            return "";
        }
        int index = StringUtils.indexOf(email, "@");
        if (index <= 1)
            return email;
        else
            return StringUtils.rightPad(StringUtils.left(email, 1), index, "*").concat(StringUtils.mid(email, index, StringUtils.length(email)));
    }

    /**
     * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600**********1234>
     *
     * @param cardNum
     * @return
     */
    public static String bankCard(String cardNum) {
        if (StringUtils.isBlank(cardNum)) {
            return "";
        }
        return StringUtils.left(cardNum, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(cardNum, 4), StringUtils.length(cardNum), "*"), "******"));
    }

    /**
     * [公司开户银行联号] 公司开户银行联行号,显示前两位，其他用星号隐藏，每位1个星号<例子:12********>
     *
     * @param code
     * @return
     */
    public static String cnapsCode(String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        return StringUtils.rightPad(StringUtils.left(code, 2), StringUtils.length(code), "*");
    }

    /**
     * [密码] 全部用星号代替<例子:********>
     *
     * @param password
     * @return
     */
    public static String password(String password) {
        if (StringUtils.isBlank(password)) {
            return "";
        }
        return StringUtils.rightPad(StringUtils.left(password, 0), StringUtils.length(password), "*");
    }

    /**
     * 获取脱敏json串 <注意：递归引用会导致java.lang.StackOverflowError>
     *
     * @param javaBean
     * @return
     */
    public static String getJson(Object javaBean) {
        String json = null;
        if (null != javaBean) {
            Class<? extends Object> raw = javaBean.getClass();
            try {
                if (raw.isInterface())
                    return json;
                Gson g = new Gson();
                Object clone = g.fromJson(g.toJson(javaBean, javaBean.getClass()), javaBean.getClass());
                Set<Integer> referenceCounter = new HashSet<Integer>();
                SensitiveParamUtils.replace(SensitiveParamUtils.findAllField(raw), clone, referenceCounter);
                json = JSON.toJSONString(clone, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty);
                referenceCounter.clear();
                referenceCounter = null;
            } catch (Throwable e) {
                logger.error("SensitiveParamUtils.getJson() ERROR", e);
            }
        }
        return json;
    }

    private static Field[] findAllField(Class<?> clazz) {
        Field[] fileds = clazz.getDeclaredFields();
        while (null != clazz.getSuperclass() && !Object.class.equals(clazz.getSuperclass())) {
            fileds = (Field[]) ArrayUtils.addAll(fileds, clazz.getSuperclass().getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        return fileds;
    }
    private static void replace(Field[] fields, Object javaBean, Set<Integer> referenceCounter) throws IllegalArgumentException, IllegalAccessException {
        if (null != fields && fields.length > 0) {
            for (Field field : fields) {
                field.setAccessible(true);
                if (null != field && null != javaBean) {
                    Object value = field.get(javaBean);
                    if (null != value) {
                        Class<?> type = value.getClass();
                        // 1.处理子属性，包括集合中的
                        if (type.isArray()) {
                            int len = Array.getLength(value);
                            for (int i = 0; i < len; i++) {
                                Object arrayObject = Array.get(value, i);
                                SensitiveParamUtils.replace(SensitiveParamUtils.findAllField(arrayObject.getClass()), arrayObject, referenceCounter);
                            }
                        } else if (value instanceof Collection<?>) {
                            Collection<?> c = (Collection<?>) value;
                            Iterator<?> it = c.iterator();
                            while (it.hasNext()) {
                                Object collectionObj = it.next();
                                SensitiveParamUtils.replace(SensitiveParamUtils.findAllField(collectionObj.getClass()), collectionObj, referenceCounter);
                            }
                        } else if (value instanceof Map<?, ?>) {
                            Map<?, ?> m = (Map<?, ?>) value;
                            Set<?> set = m.entrySet();
                            for (Object o : set) {
                                Entry<?, ?> entry = (Entry<?, ?>) o;
                                Object mapVal = entry.getValue();
                                SensitiveParamUtils.replace(SensitiveParamUtils.findAllField(mapVal.getClass()), mapVal, referenceCounter);
                            }
                        } else if (!type.isPrimitive()
                                && !StringUtils.startsWith(type.getPackage().getName(), "javax.")
                                && !StringUtils.startsWith(type.getPackage().getName(), "java.")
                                && !StringUtils.startsWith(field.getType().getName(), "javax.")
                                && !StringUtils.startsWith(field.getName(), "java.")
                                && referenceCounter.add(value.hashCode())) {
                            SensitiveParamUtils.replace(SensitiveParamUtils.findAllField(type), value, referenceCounter);
                        }
                    }
                    // 2. 处理自身的属性
                    SensitiveParam annotation = field.getAnnotation(SensitiveParam.class);
                    if (field.getType().equals(String.class) && null != annotation) {
                        String valueStr = (String) value;
                        if (StringUtils.isNotBlank(valueStr)) {
                            switch (annotation.type()) {
                                case CHINESE_NAME: {
                                    field.set(javaBean, SensitiveParamUtils.chineseName(valueStr));
                                    break;
                                }
                                case ID_CARD: {
                                    field.set(javaBean, SensitiveParamUtils.idCardNum(valueStr));
                                    break;
                                }
                                case FIXED_PHONE: {
                                    field.set(javaBean, SensitiveParamUtils.fixedPhone(valueStr));
                                    break;
                                }
                                case MOBILE_PHONE: {
                                    field.set(javaBean, SensitiveParamUtils.mobilePhone(valueStr));
                                    break;
                                }
                                case ADDRESS: {
                                    field.set(javaBean, SensitiveParamUtils.address(valueStr, 4));
                                    break;
                                }
                                case EMAIL: {
                                    field.set(javaBean, SensitiveParamUtils.email(valueStr));
                                    break;
                                }
                                case BANK_CARD: {
                                    field.set(javaBean, SensitiveParamUtils.bankCard(valueStr));
                                    break;
                                }
                                case CNAPS_CODE: {
                                    field.set(javaBean, SensitiveParamUtils.cnapsCode(valueStr));
                                    break;
                                }
                                case PASSWORD: {
                                    field.set(javaBean, SensitiveParamUtils.password(valueStr));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    public static Method [] findAllMethod(Class<?> clazz){
        Method [] methods= clazz.getMethods();
        return methods;
    }

    //----------------------------------------------------------------------------------------------
    public static enum SensitiveType {
        /**
         * 中文名
         */
        CHINESE_NAME {
            @Override
            public String unSensitive(String param) {
                return SensitiveParamUtils.chineseName(param);
            }
        },

        /**
         * 身份证号
         */
        ID_CARD {
            @Override
            public String unSensitive(String param) {
                return SensitiveParamUtils.idCardNum(param);
            }
        },
        /**
         * 座机号
         */
        FIXED_PHONE {
            @Override
            public String unSensitive(String param) {
                return SensitiveParamUtils.fixedPhone(param);
            }
        },
        /**
         * 手机号
         */
        MOBILE_PHONE {
            @Override
            public String unSensitive(String param) {
                return SensitiveParamUtils.mobilePhone(param);
            }
        },
        /**
         * 地址
         */
        ADDRESS {
            @Override
            public String unSensitive(String param) {
                return SensitiveParamUtils.address(param, 4);
            }
        },
        /**
         * 电子邮件
         */
        EMAIL {
            @Override
            public String unSensitive(String param) {
                return SensitiveParamUtils.email(param);
            }
        },
        /**
         * 银行卡
         */
        BANK_CARD {
            @Override
            public String unSensitive(String param) {
                return SensitiveParamUtils.bankCard(param);
            }
        },
        /**
         * 公司开户银行联号
         */
        CNAPS_CODE {
            @Override
            public String unSensitive(String param) {
                return SensitiveParamUtils.cnapsCode(param);
            }
        },
        /**
         * 公司开户银行联号
         */
        PASSWORD {
            @Override
            public String unSensitive(String param) {
                return SensitiveParamUtils.password(param);
            }
        };

        public String unSensitive(String param) {
            return null;
        }
    }
}