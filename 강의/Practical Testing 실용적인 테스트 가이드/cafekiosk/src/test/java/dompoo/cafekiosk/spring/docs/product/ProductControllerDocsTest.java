package dompoo.cafekiosk.spring.docs.product;

import dompoo.cafekiosk.spring.api.controller.product.ProductController;
import dompoo.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import dompoo.cafekiosk.spring.api.service.product.ProductService;
import dompoo.cafekiosk.spring.api.service.product.dto.request.ProductCreateServiceRequest;
import dompoo.cafekiosk.spring.api.service.product.dto.response.ProductResponse;
import dompoo.cafekiosk.spring.docs.RestDocsSupport;
import dompoo.cafekiosk.spring.domain.product.ProductSellingStatus;
import dompoo.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerDocsTest extends RestDocsSupport {
	
	private final ProductService productService = mock(ProductService.class);
	
	@Override
	protected Object initController() {
		return new ProductController(productService);
	}
	
	@Test
	@DisplayName("신규 상품 등록 API")
	void createProduct() throws Exception {
		when(productService.createProduct(any(ProductCreateServiceRequest.class)))
				.thenReturn(ProductResponse.builder()
						.id(1L)
						.productNumber("001")
						.name("아메리카노")
						.price(4000)
						.sellingStatus(ProductSellingStatus.SELLING)
						.type(ProductType.HANDMADE)
						.build());
		
		ProductCreateRequest request = ProductCreateRequest.builder()
				.name("아메리카노")
				.sellingStatus(ProductSellingStatus.SELLING)
				.type(ProductType.HANDMADE)
				.price(4000)
				.build();
		
		//expected
		mockMvc.perform(post("/api/v1/products/new")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andDo(document("product-create",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						requestFields(
								createStringField("type", "상품 종류"),
								createStringField("sellingStatus", "상품 판매 상태").optional(),
								createStringField("name", "상품 이름"),
								createNumberField("price", "상품가격")
						),
						responseFields(
								createNumberField("code", "코드"),
								createStringField("status", "Http Status"),
								createStringField("message", "상세 메시지"),
								createObjectField("data", "응답 데이터"),
								createNumberField("data.id", "상품 아이디"),
								createStringField("data.productNumber", "상품 번호"),
								createStringField("data.name", "상품 이름"),
								createNumberField("data.price", "상품 가격"),
								createStringField("data.type", "상품 타입"),
								createStringField("data.sellingStatus", "상품 판매 상태")
						)
				))
				.andDo(print());
	}
	
	private static FieldDescriptor createStringField(String name, String desc) {
		return fieldWithPath(name)
				.type(STRING)
				.description(desc);
	}
	
	private static FieldDescriptor createObjectField(String name, String desc) {
		return fieldWithPath(name)
				.type(OBJECT)
				.description(desc);
	}
	
	private static FieldDescriptor createNumberField(String name, String desc) {
		return fieldWithPath(name)
				.type(NUMBER)
				.description(desc);
	}
	
	
}
