package io.github.matheusvdlima.incidents.converters;

import io.github.matheusvdlima.incidents.enums.StatusEnum;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class StatusConverter {

    @Named("toStatusEnum")
    public StatusEnum toStatusEnum(String code) {
        return code != null ? StatusEnum.fromCode(code) : null;
    }
}
