package builder;


import lombok.Builder;

@Builder
public class BuilderCat {
	private final String name;
	private final String city;
	private final String meow;
}
