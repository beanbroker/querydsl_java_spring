package com.beanbroker.sample.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Component
public class JsonHelper {
	private final Logger logger = LoggerFactory.getLogger(JsonHelper.class);
	
	private ObjectMapper defaultObjectMapper;
	private ObjectMapper ymdhmsFormatObjectMapper;
	private ObjectMapper ymdFormatObjectMapper;
	
	public JsonHelper() {
		this.defaultObjectMapper = new ObjectMapper();
		
		this.ymdhmsFormatObjectMapper = new ObjectMapper();
		this.ymdhmsFormatObjectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));

		this.ymdFormatObjectMapper = new ObjectMapper();
		this.ymdFormatObjectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
	}
	
	public String getJsonString(Object jsonObject) {
		String jsonString = null;
		try {
			return defaultObjectMapper.writeValueAsString(jsonObject);
		} catch (JsonProcessingException e) {
			logger.error("Convert OBJECT to String fail.", e);
		}
		
		return jsonString;
	}
	
	public String getJsonStringYMDHMSDateType(Object jsonObject) {
		String jsonString = null;
		try {
			return ymdFormatObjectMapper.writeValueAsString(jsonObject);
		} catch (JsonProcessingException e) {
			logger.error("Convert OBJECT to String fail.", e);
		}
		
		return jsonString;
	}
	
	public String getJsonStringYMDDateType(Object jsonObject) {
		String jsonString = null;
		try {
			return ymdFormatObjectMapper.writeValueAsString(jsonObject);
		} catch (JsonProcessingException e) {
			logger.error("Convert OBJECT to String fail.", e);
		}
		
		return jsonString;
	}
	

	@SuppressWarnings("unchecked")
	public <T> T getJson(String jsonString, Class<?> clazz) throws JsonParseException, JsonMappingException, IOException {
		return (T) ymdhmsFormatObjectMapper.readValue(jsonString, clazz);
	}

}
