package hello.external;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class CommandLine {
	
	public static void main(String[] args) {
		for (String arg : args) {
			log.info("{}", arg);
		}
	}
}
