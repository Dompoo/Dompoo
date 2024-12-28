package builder;

public class MyBuilderCat {
	private final String name;
	private final String city;
	private final String meow;
	
	private MyBuilderCat(String name, String city, String meow) {
		this.name = name;
		this.city = city;
		this.meow = meow;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private String name;
		private String city;
		private String meow;
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder setCity(String city) {
			this.city = city;
			return this;
		}
		
		public Builder setMeow(String meow) {
			this.meow = meow;
			return this;
		}
		
		public MyBuilderCat build() {
			return new MyBuilderCat(name, city, meow);
		}
	}
}
