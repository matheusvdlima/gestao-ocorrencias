package io.github.matheusvdlima.incidents;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class IncidentsOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        Components components = new Components();

        // Security scheme: JWT Bearer
        components.addSecuritySchemes("bearerAuth",
                new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        // Garante que ApiError e dependências apareçam no components/schemas
        var resolved = ModelConverters.getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(io.github.matheusvdlima.incidents.exceptions.ApiError.class));
        components.addSchemas("ApiError", resolved.schema);
        if (resolved.referencedSchemas != null) {
            resolved.referencedSchemas.forEach(components::addSchemas);
        }

        return new OpenAPI()
                .info(new Info()
                        .title("Gestão de Ocorrências - API")
                        .description("Aplicação para gerenciamento de incidentes")
                        .version("v1"))
                .components(components)
                // Global: tudo pede bearer token (remova em métodos públicos com @Operation(security = {}))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    @Bean
    public OpenApiCustomizer globalErrorResponsesCustomizer() {
        return openApi -> {
            Content errorJson = new Content()
                    .addMediaType("application/json",
                            new MediaType().schema(new Schema<>().$ref("#/components/schemas/ApiError")));

            Map<String, ApiResponse> commons = new LinkedHashMap<>();
            commons.put("400", new ApiResponse().description("Bad Request").content(errorJson));
            commons.put("401", new ApiResponse().description("Unauthorized").content(errorJson));
            commons.put("403", new ApiResponse().description("Forbidden").content(errorJson));
            commons.put("404", new ApiResponse().description("Not Found").content(errorJson));
            commons.put("409", new ApiResponse().description("Conflict").content(errorJson));
            commons.put("422", new ApiResponse().description("Unprocessable Entity").content(errorJson));
            commons.put("500", new ApiResponse().description("Internal Server Error").content(errorJson));

            openApi.getPaths().values().forEach(pathItem ->
                    pathItem.readOperations().forEach(op ->
                            commons.forEach(op.getResponses()::addApiResponse)));
        };
    }
}
