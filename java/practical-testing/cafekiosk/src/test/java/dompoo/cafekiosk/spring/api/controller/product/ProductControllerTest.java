package dompoo.cafekiosk.spring.api.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import dompoo.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import dompoo.cafekiosk.spring.api.service.product.ProductService;
import dompoo.cafekiosk.spring.api.service.product.response.ProductResponse;
import dompoo.cafekiosk.spring.domain.product.ProductSellingStatus;
import dompoo.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ProductService productService;
	
	@Test
	@DisplayName("신규 상품을 등록한다.")
	void createProduct() throws Exception {
		//given
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
				.andDo(print());
	}
	
	@Test
	@DisplayName("상품 이름은 필수이다.")
	void createProductWithoutName() throws Exception {
		//given
		ProductCreateRequest request = ProductCreateRequest.builder()
//				.name("아메리카노")
				.sellingStatus(ProductSellingStatus.SELLING)
				.type(ProductType.HANDMADE)
				.price(4000)
				.build();
		
		//expected
		mockMvc.perform(post("/api/v1/products/new")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value(400))
				.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
				.andExpect(jsonPath("$.data").isEmpty())
				.andDo(print());
	}
	
	@Test
	@DisplayName("상품 판매 상태 필수이다.")
	void createProductWithoutSellingStatus() throws Exception {
		//given
		ProductCreateRequest request = ProductCreateRequest.builder()
				.name("아메리카노")
//				.sellingStatus(ProductSellingStatus.SELLING)
				.type(ProductType.HANDMADE)
				.price(4000)
				.build();
		
		//expected
		mockMvc.perform(post("/api/v1/products/new")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value(400))
				.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.message").value("상품 판매 상태는 필수입니다."))
				.andExpect(jsonPath("$.data").isEmpty())
				.andDo(print());
	}
	
	@Test
	@DisplayName("상품 타입은 필수이다.")
	void createProductWithoutProductType() throws Exception {
		//given
		ProductCreateRequest request = ProductCreateRequest.builder()
				.name("아메리카노")
				.sellingStatus(ProductSellingStatus.SELLING)
//				.type(ProductType.HANDMADE)
				.price(4000)
				.build();
		
		//expected
		mockMvc.perform(post("/api/v1/products/new")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value(400))
				.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
				.andExpect(jsonPath("$.data").isEmpty())
				.andDo(print());
	}
	
	@Test
	@DisplayName("가격은 0보다 커야합니다.")
	void createProductWithoutPrice() throws Exception {
		//given
		ProductCreateRequest request = ProductCreateRequest.builder()
				.name("아메리카노")
				.sellingStatus(ProductSellingStatus.SELLING)
				.type(ProductType.HANDMADE)
				.price(0)
				.build();
		
		//expected
		mockMvc.perform(post("/api/v1/products/new")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value(400))
				.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.message").value("상품 가격은 0 이상이어야 합니다."))
				.andExpect(jsonPath("$.data").isEmpty())
				.andDo(print());
	}
	
	@Test
	@DisplayName("판매상품을 조회한다.")
	void getSellingProducts() throws Exception {
		//given
		List<ProductResponse> productList = List.of();
		when(productService.getSellingProducts()).thenReturn(productList);
		
	    //expected
		mockMvc.perform(get("/api/v1/products/selling"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value(200))
				.andExpect(jsonPath("$.status").value("OK"))
				.andExpect(jsonPath("$.message").value("OK"))
				.andExpect(jsonPath("$.data").isArray())
				.andDo(print());
	    
	}
	
}