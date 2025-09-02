package io.github.matheusvdlima.incidents;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.*;

import java.util.Map;

@Configuration
public class IncidentsOpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestão de Ocorrências - API")
                        .description("Aplicação para gerenciamento de incidentes")
                        .version("v1"));
    }

    @Bean
    public OpenApiCustomizer globalErrorResponsesCustomizer() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(op -> {
                    // Declara o schema ApiError para os responses
                    Schema<?> apiErrorSchema = new Schema<>().$ref("#/components/schemas/ApiError");
                    Components components = openApi.getComponents();
                    if (components.getSchemas() == null || !components.getSchemas().containsKey("ApiError")) {
                        components.addSchemas("ApiError", new ObjectSchema()
                                .addProperty("timestamp", new StringSchema().format("date-time"))
                                .addProperty("status", new IntegerSchema())
                                .addProperty("error", new StringSchema())
                                .addProperty("code", new StringSchema())
                                .addProperty("message", new StringSchema())
                                .addProperty("path", new StringSchema())
                                .addProperty("correlationId", new StringSchema())
                                .addProperty("details", new ArraySchema().items(
                                        new ObjectSchema()
                                                .addProperty("field", new StringSchema())
                                                .addProperty("message", new StringSchema())
                                )));
                    }

                    Map<String, ApiResponse> commons = Map.of(
                            "400", new ApiResponse().description("Bad Request").content(
                                    new Content().addMediaType("application/json",
                                            new MediaType().schema(apiErrorSchema))),
                            "401", new ApiResponse().description("Unauthorized").content(
                                    new Content().addMediaType("application/json",
                                            new MediaType().schema(apiErrorSchema))),
                            "403", new ApiResponse().description("Forbidden").content(
                                    new Content().addMediaType("application/json",
                                            new MediaType().schema(apiErrorSchema))),
                            "404", new ApiResponse().description("Not Found").content(
                                    new Content().addMediaType("application/json",
                                            new MediaType().schema(apiErrorSchema))),
                            "409", new ApiResponse().description("Conflict").content(
                                    new Content().addMediaType("application/json",
                                            new MediaType().schema(apiErrorSchema))),
                            "422", new ApiResponse().description("Unprocessable Entity").content(
                                    new Content().addMediaType("application/json",
                                            new MediaType().schema(apiErrorSchema))),
                            "500", new ApiResponse().description("Internal Server Error").content(
                                    new Content().addMediaType("application/json",
                                            new MediaType().schema(apiErrorSchema)))
                    );

                    commons.forEach(op.getResponses()::addApiResponse);
                })
        );
    }
}
