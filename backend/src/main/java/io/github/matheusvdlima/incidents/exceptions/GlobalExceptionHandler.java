package io.github.matheusvdlima.incidents.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private String cid(HttpServletRequest req) {
        Object v = req.getAttribute("X-Correlation-Id");
        return v == null ? null : v.toString();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<ApiErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> ApiErrorDetail.builder().field(f.getField()).message(f.getDefaultMessage()).build())
                .collect(Collectors.toList());

        ApiError body = ApiError.of(422, "Unprocessable Entity", "ERR_VALIDATION",
                "Dados inválidos.", req.getRequestURI(), cid(req), details);

        return ResponseEntity.unprocessableEntity().body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
        List<ApiErrorDetail> details = ex.getConstraintViolations().stream()
                .map(v -> ApiErrorDetail.builder()
                        .field(v.getPropertyPath().toString())
                        .message(v.getMessage())
                        .build())
                .collect(Collectors.toList());

        ApiError body = ApiError.of(422, "Unprocessable Entity", "ERR_VALIDATION",
                "Dados inválidos.", req.getRequestURI(), cid(req), details);

        return ResponseEntity.unprocessableEntity().body(body);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMediaTypeNotSupportedException.class
    })
    public ResponseEntity<ApiError> handleBadRequest(Exception ex, HttpServletRequest req) {
        ApiError body = ApiError.of(400, "Bad Request", "ERR_BAD_REQUEST",
                Optional.ofNullable(ex.getMessage()).orElse("Requisição inválida."),
                req.getRequestURI(), cid(req), null);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<ApiError> handleNotFound(RuntimeException ex, HttpServletRequest req) {
        ApiError body = ApiError.of(404, "Not Found", "ERR_NOT_FOUND",
                Optional.ofNullable(ex.getMessage()).orElse("Recurso não encontrado."),
                req.getRequestURI(), cid(req), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleConflict(DataIntegrityViolationException ex, HttpServletRequest req) {
        ApiError body = ApiError.of(409, "Conflict", "ERR_CONFLICT",
                "Violação de integridade de dados.",
                req.getRequestURI(), cid(req), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        ApiError body = ApiError.of(405, "Method Not Allowed", "ERR_METHOD_NOT_ALLOWED",
                "Método HTTP não suportado para este recurso.",
                req.getRequestURI(), cid(req), null);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
        ApiError body = ApiError.of(500, "Internal Server Error", "ERR_INTERNAL",
                "Erro inesperado. Se o problema persistir, contate o suporte.",
                req.getRequestURI(), cid(req), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
