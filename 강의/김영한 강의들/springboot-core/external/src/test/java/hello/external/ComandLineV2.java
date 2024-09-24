package hello.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.List;

@Slf4j
public class ComandLineV2 {

    public static void main(String[] args) {
        for (String arg : args) {
            log.info("[arg] {}", arg);
        }

        ApplicationArguments appArgs = new DefaultApplicationArguments(args);
        log.info("SourceArgs = {}", List.of(appArgs.getSourceArgs()));
        log.info("NonOptionArgs = {}", appArgs.getNonOptionArgs());
        log.info("OptionNames = {}", appArgs.getOptionNames());

        for (String optionName : appArgs.getOptionNames()) {
            log.info("option arg {}={}", optionName, appArgs.getOptionValues(optionName));
        }

    }
}
