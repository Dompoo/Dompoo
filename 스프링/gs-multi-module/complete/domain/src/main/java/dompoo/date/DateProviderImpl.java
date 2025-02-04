package dompoo.date;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateProviderImpl implements DateProvider {
    
    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
