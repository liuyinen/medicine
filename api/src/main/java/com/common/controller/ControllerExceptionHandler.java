package com.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.common.exception.MyException;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(MyException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handelMyException(MyException e) {
		Map<String, Object> map = new HashMap<>();
		map.put("id",e.getId());
		map.put("message",e.getMessage());
		map.put("code",e.getCode());
		return map;
	}
}
