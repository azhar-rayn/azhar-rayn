package pk.org.rayn.zindagi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);
	private static ObjectMapper om = new ObjectMapper();

	public static <T> T getValue(String json, TypeReference<T> t) {
		try {
			return om.readValue(json, t);
		} catch (JsonProcessingException e) {
			LOG.warn("error deserializing {}", json, e);
			return null;
		}
	}

}
