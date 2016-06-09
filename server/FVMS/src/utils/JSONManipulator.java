package utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONManipulator {
	public static ObjectMapper mapper = new ObjectMapper();
	public static <T> List<T> deserializeList(String jsonList) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		List<T> list = null;
		try {
			list = mapper.readValue(jsonList, new TypeReference<List<T>>() {
			});
		} catch (IOException e) {
			Logger.logERROR(e);
		}
		return list;
	}

	public static HashMap<String, Object> getMap(String message) {
		
		HashMap<String, Object> map = null;
		try {
			map = mapper.readValue(message,
					new TypeReference<HashMap<String, Object>>() {
					});
		} catch (IOException e) {
			Logger.logERROR(e);
		}
		return map;
	}

}
