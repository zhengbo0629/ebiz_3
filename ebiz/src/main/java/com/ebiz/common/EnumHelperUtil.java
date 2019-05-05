package com.ebiz.common;


public class EnumHelperUtil {
    /**
     * indexOf,传入的参数ordinal指的是需要的枚举值在设定的枚举类中的顺序，以0开始
     *
     * @param clazz   enum
     * @param ordinal 顺序
     * @return Enum T
     */
    public static <T extends Enum<T>> T indexOf(Class<T> clazz, int ordinal) {
        return clazz.getEnumConstants()[ordinal];
    }

    /**
     * nameOf,传入的参数name指的是枚举值的名称，一般是大写加下划线的
     *
     * @param clazz enum
     * @param name  enum
     * @return Enum T
     */
    public static <T extends Enum<T>> T nameOf(Class<T> clazz, String name) {

        return Enum.valueOf(clazz, name);
    }

}