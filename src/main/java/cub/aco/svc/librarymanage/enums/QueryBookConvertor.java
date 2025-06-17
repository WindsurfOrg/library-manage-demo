package cub.aco.svc.librarymanage.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class QueryBookConvertor implements Converter<String, QueryType> {

    @Override
    public QueryType convert(String code) {
        return QueryType.of(code);
    }
}