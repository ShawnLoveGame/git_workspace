package com.he.im.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaginationUtil {

	
	
	/**
	 * Description	：将list 按照  固定的大小 切分    进行分页
	 * @param list
	 * @param pageSize
	 * @return
	 *
	 */
	public static  <T> List<List<T>> split(List<T> list, int pageSize) {
        List<List<T>> result = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(list)) {
            result = new ArrayList<List<T>>(list.size() / pageSize + 1);  
            for (int i = 0, next, max = list.size(); i < max; i = next) {  
                next = i + pageSize;  
                if (next > max){next = max;}
                result.add(list.subList(i, next));  
            }  
        }
        return result;  
    }
}
