package com.he.exam.webapp.client;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * 自定义FreeMarkerView，用来定义项目的全局路径
 * @author daizy
 *
 */
public class MyFreeMarkerViewClient extends FreeMarkerView {
	
	private static final String CONTEXT_PATH = "base"; 
	
	@Override
	protected void exposeHelpers(Map<String, Object> model,HttpServletRequest request) throws Exception {
		model.put(CONTEXT_PATH, request.getContextPath());
		super.exposeHelpers(model, request);
	}

}