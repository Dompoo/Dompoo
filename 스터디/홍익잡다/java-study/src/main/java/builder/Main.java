package builder;

public class Main {
	public static void main(String[] args) {
		DefaultCat cat = new DefaultCat();
		cat.name = "고냥이";
		cat.city = "서울";
		cat.meow = "왜옹";
		
		SetterCat cat2 = new SetterCat();
		cat2.setName("고냥이");
		cat2.setCity("서울");
		cat2.setMeow("왜옹");
		
		ConstructorCat cat3 = new ConstructorCat("고냥이", "서울", "왜옹");
		
		MyBuilderCat cat4 = MyBuilderCat.builder()
				.setName("고냥이")
				.setCity("서울")
				.setMeow("왜옹")
				.build();
		
		BuilderCat cat5 = BuilderCat.builder()
				.name("고냥이")
				.city("서울")
				.meow("왜옹")
				.build();
	}
}