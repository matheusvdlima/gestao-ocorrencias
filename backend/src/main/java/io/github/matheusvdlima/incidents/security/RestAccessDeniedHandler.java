package io.github.matheusvdlima.incidents.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.matheusvdlima.incidents.exceptions.ApiError;
import jakarta.servlet.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper;

    public RestAccessDeniedHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
            throws IOException {
        String path = request.getRequestURI();
        String cid = (String) request.getAttribute("X-Correlation-Id");

        ApiError body = ApiError.of(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden", "ERR_FORBIDDEN",
                "Você não tem permissão para acessar este recurso.",
                path, cid, null
        );

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(response.getOutputStream(), body);
    }
}
