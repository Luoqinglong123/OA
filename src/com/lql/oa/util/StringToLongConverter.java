package com.lql.oa.util;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class StringToLongConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map content, String[] values, Class toClass) {
		String string = values[0];
		String a=string.charAt(1)+"";
		Long parseInt = (long) Integer.parseInt(a);
		return parseInt;
	}

	@Override
	public String convertToString(Map arg0, Object arg1) {
		return null;
	}

}
