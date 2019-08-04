package com.beanbroker.sample.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class JsonUtil {

	private ObjectMapper defaultObjectMapper;

	public JsonUtil() {
		this.defaultObjectMapper = new ObjectMapper();
	}
	
	public String getJsonString(Object jsonObject) {
		String jsonString = null;
		try {
			return defaultObjectMapper.writeValueAsString(jsonObject);
		} catch (JsonProcessingException e) {
			log.error("Convert OBJECT to String fail.", e);
		}
		
		return jsonString;
	}
	
}
