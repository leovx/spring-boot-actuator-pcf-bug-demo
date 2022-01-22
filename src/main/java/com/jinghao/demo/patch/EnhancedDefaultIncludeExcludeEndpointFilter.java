package com.jinghao.demo.patch;

import lombok.Getter;
import org.springframework.boot.actuate.autoconfigure.endpoint.expose.EndpointExposure;
import org.springframework.boot.actuate.autoconfigure.endpoint.expose.IncludeExcludeEndpointFilter;
import org.springframework.boot.actuate.endpoint.EndpointFilter;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Enhanced to filter values either (default) OR (included but not excluded)
 */
public class EnhancedDefaultIncludeExcludeEndpointFilter implements EndpointFilter<ExposableWebEndpoint> {

    @Getter
    private final IncludeExcludeEndpointFilter<ExposableWebEndpoint> includeExcludeEndpointFilter;

    public EnhancedDefaultIncludeExcludeEndpointFilter(IncludeExcludeEndpointFilter<ExposableWebEndpoint> f) {
        this.includeExcludeEndpointFilter = f;
    }

    public final boolean match(EndpointId endpointId) {
        return Arrays.stream(EndpointExposure.WEB.getDefaultIncludes())
                .collect(Collectors.toSet())
                .contains(endpointId.toLowerCaseString()) || this.includeExcludeEndpointFilter.match(endpointId);
    }

    @Override
    public boolean match(ExposableWebEndpoint endpoint) {
        return this.match(endpoint.getEndpointId());
    }
}
