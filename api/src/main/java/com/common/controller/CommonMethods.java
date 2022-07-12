package com.common.controller;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class CommonMethods {

	/**
	 * 条件拼接
	 * 
	 * @param condition
	 * @return conditionString
	 */
	public static String spliceCon(Map<String, Object> condition) {
		String conditionString = "";
		if (condition != null) {
			Iterator<String> iterator = condition.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object value = condition.get(key);
				if (value instanceof Integer) {
					conditionString = conditionString + key + " = " + value + " ";
				} else if (key.equals("create_time")) {
					conditionString = conditionString + key + " " + value;
				} else if (value instanceof String) {
					if (Pattern.matches("^\\(((\\d+,)*\\d+)+\\)$", (String) value)) {
						conditionString = conditionString + key + " in " + value + " ";
					} else {
						conditionString = conditionString + key + " like '%" + value + "%'";
					}
				}
				conditionString = conditionString + "and ";
			}
		}
		return conditionString;
	}
}
