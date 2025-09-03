package io.github.matheusvdlima.incidents;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.responses.*;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.Components;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IncidentsOpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        Components components = new Components()
                .addSchemas("ApiError", buildApiErrorSchema())
                .addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                                .description("Use um token JWT no formato: **Bearer &lt;token&gt;**"));

        return new OpenAPI()
                .info(new Info()
                        .title("Gestão de Ocorrências - API")
                        .description("Aplicação para gerenciamento de incidentes")
                        .version("v1"))
                .components(components)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    private Schema<?> buildApiErrorSchema() {
        ObjectSchema root = new ObjectSchema();
        root.addProperty("timestamp", new StringSchema().format("date-time"));
        root.addProperty("status", new IntegerSchema().format("int32"));
        root.addProperty("error", new StringSchema());
        root.addProperty("code", new StringSchema());
        root.addProperty("message", new StringSchema());
        root.addProperty("path", new StringSchema());

        ObjectSchema fieldErr = new ObjectSchema();
        fieldErr.addProperty("field", new StringSchema());
        fieldErr.addProperty("message", new StringSchema());

        ArraySchema fieldErrors = new ArraySchema().items(fieldErr);
        root.addProperty("fieldErrors", fieldErrors);
        root.description("Formato padrão de erro da API");
        return root;
    }

    @Bean
    public OpenApiCustomizer globalErrorResponsesCustomizer() {
        return openApi -> {
            Components comps = openApi.getComponents();
            if (comps == null) {
                comps = new Components();
                openApi.setComponents(comps);
            }
            if (comps.getSchemas() == null || !comps.getSchemas().containsKey("ApiError")) {
                comps.addSchemas("ApiError", buildApiErrorSchema());
            }

            Content apiErrorContent = new Content().addMediaType(
                    "application/json",
                    new MediaType().schema(new Schema<>().$ref("#/components/schemas/ApiError"))
            );

            comps.addResponses("BadRequest", new ApiResponse().description("Bad Request").content(apiErrorContent));
            comps.addResponses("Unauthorized", new ApiResponse().description("Unauthorized").content(apiErrorContent));
            comps.addResponses("Forbidden", new ApiResponse().description("Forbidden").content(apiErrorContent));
            comps.addResponses("NotFound", new ApiResponse().description("Not Found").content(apiErrorContent));
            comps.addResponses("Conflict", new ApiResponse().description("Conflict").content(apiErrorContent));
            comps.addResponses("UnprocessableEntity", new ApiResponse().description("Unprocessable Entity").content(apiErrorContent));
            comps.addResponses("InternalServerError", new ApiResponse().description("Internal Server Error").content(apiErrorContent));

            if (openApi.getPaths() != null) {
                openApi.getPaths().values().forEach(pathItem ->
                        pathItem.readOperations().forEach(op -> {
                            ApiResponses r = op.getResponses();
                            if (r == null) {
                                r = new ApiResponses();
                                op.setResponses(r);
                            }
                            r.addApiResponse("400", new ApiResponse().$ref("#/components/responses/BadRequest"));
                            r.addApiResponse("401", new ApiResponse().$ref("#/components/responses/Unauthorized"));
                            r.addApiResponse("403", new ApiResponse().$ref("#/components/responses/Forbidden"));
                            r.addApiResponse("404", new ApiResponse().$ref("#/components/responses/NotFound"));
                            r.addApiResponse("409", new ApiResponse().$ref("#/components/responses/Conflict"));
                            r.addApiResponse("422", new ApiResponse().$ref("#/components/responses/UnprocessableEntity"));
                            r.addApiResponse("500", new ApiResponse().$ref("#/components/responses/InternalServerError"));
                        })
                );
            }
        };
    }
}
