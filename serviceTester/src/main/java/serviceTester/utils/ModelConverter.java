package serviceTester.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ModelConverter {

	private static final Logger logger = LoggerFactory.getLogger(ModelConverter.class);
	
	
	public static String extractFieldsFromHashMap(LinkedHashMap<String, String> map) throws Exception{
		StringBuilder sb = new StringBuilder();
		if(map != null){
			for(Map.Entry<String, String> entry: map.entrySet()){
				if(entry.getValue() != null){
					sb.append("<");
					sb.append(entry.getKey());
					sb.append(">");
					sb.append(entry.getValue());
					sb.append("</");
					sb.append(entry.getKey());
					sb.append(">");
				}else{
					sb.append("<");
					sb.append(entry.getKey());
					sb.append("/>");
				}
			}
		}
		logger.debug("Extract payload from map {}", sb.toString());
		return sb.toString();
	}
	
		
	
}
