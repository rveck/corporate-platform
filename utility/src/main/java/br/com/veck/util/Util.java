package br.com.veck.util;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Util {
	
	public static String convertObjectToJson(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public static <T> T convertJsonToObject(String json, Class<T> classOf) {
		Gson gson = new Gson();
        return gson.fromJson(json, classOf);
	}
	
	public static <T> List<T> convertJsonToList(String json, Class<T> classOf) {
		Type listType = new TypeToken<ArrayList<T>>(){}.getType();
		List<T> list = new Gson().fromJson(json, listType);
        return list;
	}		
	
	public static String getClientIp(HttpServletRequest request) {

        String remoteAddr = null;
        
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
	
	public static boolean isSameDay(Date date1, Date date2) {
		boolean ret = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (sdf.format(date1).equals(sdf.format(date2))) {
			ret = true;
		}
		return ret;
	}

}
