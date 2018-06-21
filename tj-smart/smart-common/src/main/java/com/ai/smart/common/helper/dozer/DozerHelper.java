package com.ai.smart.common.helper.dozer;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author daizy
 * @version 1.0
 */
public class DozerHelper {


    private static DozerBeanMapper dozer = new DozerBeanMapper();

    private DozerHelper() {

    }

    /**
     * 基于Dozer转换对象的类型.
     */
    public static <T> T transfer(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        return dozer.map(source, destinationClass);
    }

    /**
     * 基于Dozer转换Collection中对象的类型.
     */
    public static <T> List<T> transferList(Collection<?> source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        List<T> destinationList = Lists.newArrayList();
        for (Object tmp : source) {
            T destinationObject = null;
            if (tmp != null) {
                destinationObject = dozer.map(tmp, destinationClass);
            }
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    /**
     * 基于Dozer将对象A的值拷贝到对象B中.
     */
    public static void copy(Object source, Object destination) {
        if (source == null) {
            return;
        }
        dozer.map(source, destination);
    }


}
