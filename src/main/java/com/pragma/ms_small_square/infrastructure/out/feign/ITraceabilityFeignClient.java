package com.pragma.ms_small_square.infrastructure.out.feign;

import com.pragma.ms_small_square.infrastructure.configuration.FeignClientConfig;
import com.pragma.ms_small_square.infrastructure.out.feign.dto.ResponseTraceability;
import com.pragma.ms_small_square.infrastructure.out.feign.dto.TraceabilityRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "traceability-service",
        url = "${url-base.ms-traceability}",
        configuration = FeignClientConfig.class)
public interface ITraceabilityFeignClient {

    @PostMapping("/save")
    ResponseTraceability saveTraceability(TraceabilityRequest traceabilityRequest);
}
