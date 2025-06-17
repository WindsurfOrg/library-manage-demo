package cub.aco.svc.librarymanage.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LanguageConvertor implements Converter<String, Language> {

    @Override
    public Language convert(String code) {
        return Language.of(code);
    }
}