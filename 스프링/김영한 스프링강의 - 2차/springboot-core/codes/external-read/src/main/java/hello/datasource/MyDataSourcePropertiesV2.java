package hello.datasource;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.time.DurationMax;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("my.datasource")
@Validated
public class MyDataSourcePropertiesV2 {
	
	@NotEmpty
	private final String url;
	@NotEmpty
	private final String username;
	@NotEmpty
	private final String password;
	@NotNull
	private final Etc etc;
	
	@Getter
	@RequiredArgsConstructor
	public static class Etc {
		@Range(min = 1, max = 999)
		private final int maxConnection;
		@DurationMin(seconds = 1)
		@DurationMax(seconds = 60)
		private final Duration timeout;
		@NotNull
		private final List<String> options;
	}
}
