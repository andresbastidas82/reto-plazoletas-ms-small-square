package com.pragma.ms_small_square.infrastructure.out.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate template) {
        // Obtenemos los atributos de la petición actual
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            // Obtenemos la petición HTTP entrante
            HttpServletRequest request = attributes.getRequest();

            // Extraemos el header "Authorization" de la petición entrante
            String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

            // Si el header existe, lo añadimos a la plantilla de la petición de Feign
            if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
                template.header(AUTHORIZATION_HEADER, authorizationHeader);
            }
        }
    }
}
