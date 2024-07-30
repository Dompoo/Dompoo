package dompoo.cafekiosk.spring.domain.stock;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class StockRepositoryTest {
    
    @Autowired
    private StockRepository stockRepository;
    
    @Test
    @DisplayName("상품 번호 리스트로 재고를 조회한다.")
    void findAllByProductNumberIn() {
        //given
        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 5);
        Stock stock3 = Stock.create("003", 4);
        
        stockRepository.saveAll(List.of(stock1, stock2, stock3));
        
        //when
        List<Stock> result = stockRepository.findAllByProductNumberIn(List.of("001", "002"));
        
        //then
        assertThat(result).hasSize(2)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                Tuple.tuple("001", 1),
                Tuple.tuple("002", 5)
            );
    }
}