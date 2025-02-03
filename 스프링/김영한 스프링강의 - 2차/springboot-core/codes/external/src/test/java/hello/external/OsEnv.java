package hello.external;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class OsEnv {
	
	public static void main(String[] args) {
		Map<String, String> getenv = System.getenv();
		for (Map.Entry<String, String> entry : getenv.entrySet()) {
			log.info("{} = {}", entry.getKey(), entry.getValue());
		}
		
	}
}
