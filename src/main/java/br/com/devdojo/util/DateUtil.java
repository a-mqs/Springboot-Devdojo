package br.com.devdojo.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ? Mesmo estando no pacote util, essa classe
 * ? não é exatamente útil. Ela apenas é uma
 * ? exemplificação do funcionamento das
 * ? anotações @Component e @Autowired.
 */

@Component
public class DateUtil {
    public String formatLocalDateTimeToDatabaseStyle(LocalDateTime localDateTime){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }
}
