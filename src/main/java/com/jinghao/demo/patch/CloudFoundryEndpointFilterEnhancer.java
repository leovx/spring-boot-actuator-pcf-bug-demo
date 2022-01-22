package com.jinghao.demo.patch;

import org.springframework.boot.actuate.autoconfigure.endpoint.expose.IncludeExcludeEndpointFilter;
import org.springframework.boot.actuate.endpoint.EndpointFilter;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;

/**
 * Used to enhance web exposure endpoint filter.
 */
public class CloudFoundryEndpointFilterEnhancer {

    private CloudFoundryEndpointFilterEnhancer() {
        // not allowed
    }

    public static EndpointFilter<ExposableWebEndpoint> enhance(EndpointFilter<ExposableWebEndpoint> filter) {
        if(filter instanceof IncludeExcludeEndpointFilter<ExposableWebEndpoint> f) {
            return new EnhancedDefaultIncludeExcludeEndpointFilter(f);
        }
        return filter;
    }
}
