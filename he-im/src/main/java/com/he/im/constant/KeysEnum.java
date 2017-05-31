package com.he.im.constant;

/**
 * 缓存key
 */
public enum KeysEnum {

    /**
     * 权限菜单
     */
    BACKEND_MENU_FUNCTION("menuFunction", RedisTime.HALF_AN_HOUR),

    /**
     * 超级用户权限
     */
    BACKEND_SUPER_FUNCTION("backFunction_TO_SUPER", RedisTime.HALF_AN_HOUR),

    /**
     * 普通用户权限
     */
    BACKEND_NORMAL_FUNCTION("backFunction_TO_NOMAL", RedisTime.HALF_AN_HOUR),
    ;


    private String key;
    private String value;
    
    private KeysEnum(String key) {
        this.key = key;
    }

    KeysEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    KeysEnum(String key, long value) {
        this.key = key;
        this.value = String.valueOf(value);
    }

    public int getIntValue() {
        return Integer.parseInt(value);
    }

    public double getDoubleValue() {
        return Double.parseDouble(value);
    }

    public long getLongValue() {
        return Long.parseLong(value);
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return getKey();
    }
}
