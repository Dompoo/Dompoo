package hello.external;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Properties;

@Slf4j
public class JavaProperties {
	
	public static void main(String[] args) {
		Properties properties = System.getProperties();
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			log.info("{} = {}", entry.getKey(), entry.getValue());
		}
	}
}
