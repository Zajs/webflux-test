package com.github.zajs.webflux;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 */
public class TestHandler implements HandlerFunction {
    private static final String CONTENT_PATH = "classpath:/content/some.json";

    private final byte[] jsonContent;


    public TestHandler(ResourceLoader resourceLoader) {
        Resource resource = resourceLoader.getResource(CONTENT_PATH);
        if (!resource.exists()) {
            throw new IllegalArgumentException("Can't find json");
        }
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            StreamUtils.copy(resource.getInputStream(), stream);
            jsonContent = stream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Mono handle(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(jsonContent));
    }
}
