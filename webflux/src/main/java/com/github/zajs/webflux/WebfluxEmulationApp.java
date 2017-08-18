package com.github.zajs.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 *
 */
@EnableWebFlux
@SpringBootApplication
public class WebfluxEmulationApp implements WebFluxConfigurer {

    private final Environment environment;

    @Autowired
    public WebfluxEmulationApp(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public TestHandler testHandler(ResourceLoader resourceLoader) {
        return new TestHandler(resourceLoader);
    }

    @Bean
    public RouterFunction monoRouterFunction(TestHandler testHandler) {
        return route(RequestPredicates.all(), testHandler);
    }

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        boolean fastMode = environment.getProperty("turnOnFastMode", Boolean.class, false);
        if (fastMode) {
            configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder());
            configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(WebfluxEmulationApp.class, args);
    }
}
