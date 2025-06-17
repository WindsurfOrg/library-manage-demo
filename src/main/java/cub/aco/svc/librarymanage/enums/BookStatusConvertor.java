package cub.aco.svc.librarymanage.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookStatusConvertor implements Converter<String, BookStatus> {

    @Override
    public BookStatus convert(String code) {
        return BookStatus.of(code);
    }
}