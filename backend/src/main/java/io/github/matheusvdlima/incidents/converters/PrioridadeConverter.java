package io.github.matheusvdlima.incidents.converters;

import io.github.matheusvdlima.incidents.enums.PrioridadeEnum;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class PrioridadeConverter {

    @Named("toPrioridadeEnum")
    public PrioridadeEnum toPrioridadeEnum(String code) {
        return code != null ? PrioridadeEnum.fromCode(code) : null;
    }
}
