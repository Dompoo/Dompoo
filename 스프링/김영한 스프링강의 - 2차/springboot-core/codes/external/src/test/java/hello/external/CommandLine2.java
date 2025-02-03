package hello.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.Set;

@Slf4j
public class CommandLine2 {
	
	public static void main(String[] args) {
		ApplicationArguments arguments = new DefaultApplicationArguments(args);
		Set<String> optionNames =  arguments.getOptionNames(); // key값
		for (String optionName : optionNames) {
			log.info("{}={}", optionName, arguments.getOptionValues(optionName));
		}
	}
}
