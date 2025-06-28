package com.test.customer.management.infrastructure.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.customer.management.infrastructure.exception.custom.*;
import com.test.customer.management.infrastructure.exception.resolver.*;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.*;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Configuration
@Order(-2)
@RequiredArgsConstructor
@Generated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResolverHandler implements ErrorWebExceptionHandler {

    @Value("${info.project.version}")
    private String version;
    private final ObjectMapper mapper;
    private final UnexpectedErrorResolver unexpectedErrorResolver = new UnexpectedErrorResolver();
    private final NotFoundErrorCustomerResolver notFoundErrorCustomerResolver = new NotFoundErrorCustomerResolver();
    private final Map<Class<? extends Throwable>, ErrorResolver<?>> resolvers;

    @PostConstruct
    private void initializeResolvers() {
        resolvers.put(AccessDeniedException.class, new AccessDeniedExceptionResolver());
        resolvers.put(CodeException.class, new CodeExceptionResolver());
        resolvers.put(ConstraintViolationException.class, new ConstraintViolationExceptionResolver());
        resolvers.put(FailureException.class, new FailureExceptionResolver());
        resolvers.put(IllegalArgumentException.class, new IllegalArgumentExceptionResolver());
        resolvers.put(MissingRequestValueException.class, new MissingRequestValueExceptionResolver());
        resolvers.put(ResponseStatusException.class, new ResponseStatusExceptionResolver());
        resolvers.put(ServerWebInputException.class, new ServerWebInputExceptionResolver());
        resolvers.put(UnsupportedMediaTypeStatusException.class, new UnsupportedMediaTypeStatusExceptionResolver());
        resolvers.put(WebClientRequestException.class, new WebClientRequestExceptionResolver());
        resolvers.put(WebClientResponseException.class, new WebClientResponseExceptionResolver());
        resolvers.put(WebExchangeBindException.class, new WebExchangeBindExceptionResolver());
        resolvers.put(WebClientResponseException.NotFound.class, notFoundErrorCustomerResolver);

        resolvers.put(DeleteException.class, new FailureExceptionResolver());
        resolvers.put(GetRegisterException.class, new FailureExceptionResolver());
        resolvers.put(GetRegistersException.class, new FailureExceptionResolver());
        resolvers.put(ParsingJsonException.class, new FailureExceptionResolver());
        resolvers.put(RegisterNotFoundException.class, new FailureExceptionResolver());
        resolvers.put(SaveException.class, new FailureExceptionResolver());
        resolvers.put(UpdateException.class, new FailureExceptionResolver());


    }

    @SafeVarargs
    @NonNull
    @SuppressWarnings({"unchecked", "rawtypes"})
    private ErrorResolver<?> getFallbackErrorResolver(
            @NonNull final Throwable throwable,
            @NonNull final Class<? extends Throwable>... classes
    ) {

        return Stream.of(classes)
                .filter(theClass -> theClass.isInstance(throwable))
                .findFirst()
                .map(resolvers::get)
                .orElse((ErrorResolver) unexpectedErrorResolver);
    }

    @NonNull
    @Override
    public Mono<Void> handle(@NonNull final ServerWebExchange serverWebExchange,
                             @NonNull final Throwable throwable) {
        return Mono.just(serverWebExchange.getResponse())
                .doOnNext(response -> response.getHeaders().setContentType(MediaType.APPLICATION_JSON))
                .map(response -> Tuples.of(
                        response,
                        resolvers.getOrDefault(throwable.getClass(), getFallbackErrorResolver(throwable, CodeException.class))))
                .flatMap(responseAndResolverTuple -> responseAndResolverTuple.getT1().writeWith(
                        Mono.fromCallable(() -> mapper
                                .writeValueAsBytes(responseAndResolverTuple.getT2().apply(serverWebExchange, throwable, version)))
                                .doOnNext(error -> log.error("Error response: {}", new String(error, StandardCharsets.UTF_8)))
                                .map(responseAndResolverTuple.getT1().bufferFactory()::wrap)));
    }


}