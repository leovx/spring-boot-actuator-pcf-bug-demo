package com.jinghao.demo.autoconfigure;

import com.jinghao.demo.patch.CloudFoundryEndpointFilterEnhancer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.endpoint.EndpointFilter;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvokerAdvisor;
import org.springframework.boot.actuate.endpoint.invoke.ParameterValueMapper;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.PathMapper;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpointDiscoverer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.stream.Collectors;

/**
 * Temporary solution, disable this auto-configuration to view the error triggered by bug
 */
@Configuration
@ConditionalOnClass(WebEndpointDiscoverer.class)
@ConditionalOnCloudPlatform(CloudPlatform.CLOUD_FOUNDRY)
public class SpringActuatorCloudFoundryPatchAutoConfiguration {

    private final ApplicationContext applicationContext;

    public SpringActuatorCloudFoundryPatchAutoConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Primary
    @Bean
    public WebEndpointDiscoverer webEndpointDiscoverer(ParameterValueMapper parameterValueMapper,
                                                       EndpointMediaTypes endpointMediaTypes,
                                                       ObjectProvider<PathMapper> endpointPathMappers,
                                                       ObjectProvider<OperationInvokerAdvisor> invokerAdvisors,
                                                       ObjectProvider<EndpointFilter<ExposableWebEndpoint>> filters) {
        return new WebEndpointDiscoverer(
                this.applicationContext,
                parameterValueMapper,
                endpointMediaTypes,
                endpointPathMappers.orderedStream().toList(),
                invokerAdvisors.orderedStream().toList(),
                filters.orderedStream()
                        .map(CloudFoundryEndpointFilterEnhancer::enhance) // do enhancement here
                        .collect(Collectors.toList())
        );
    }

}
