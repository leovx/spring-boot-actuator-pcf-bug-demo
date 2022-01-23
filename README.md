# spring-boot-actuator-pcf-bug-demo

When booting up the application, supply the following as environment variable, so that your local environment will be 
treated as Pivotal Cloud Foundry.

> VCAP_APPLICATION={"app":"demo"}

The extension should be a unnecessary bean to instantiate when health endpoint is not manually included.

# Trigger Conditions
0. Running on Cloud Foundry environment
1. Using Spring Boot Actuator 2.6.x (Current latest = 2.6.2)
2. In your config file, manually include some endpoints but not 'health'

> e.g. management.endpoints.web.exposure.include: routes,gateway,info

# My Current Temp Solution for Project

Intercept the startup process by supplying a bean of 'WebEndpointDiscoverer', where I inject a customized
filter to wrap up the provided 'IncludeExcludeEndpointFilter', so that we can override the 'match()' method,
in order to supply such endpoint.

# Related Issues
> https://github.com/spring-projects/spring-boot/issues/28389
>
> https://github.com/spring-projects/spring-boot/issues/25471
> 
> https://github.com/spring-projects/spring-boot/issues/28131

# Error log
> org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'healthEndpointWebMvcHandlerMapping' defined in class path resource [org/springframework/boot/actuate/autoconfigure/health/HealthEndpointWebExtensionConfiguration$MvcAdditionalHealthEndpointPathsConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.boot.actuate.endpoint.web.servlet.AdditionalHealthEndpointPathsWebMvcHandlerMapping]: Factory method 'healthEndpointWebMvcHandlerMapping' threw exception; nested exception is java.util.NoSuchElementException: No value present
at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:658) ~[spring-beans-5.3.14.jar:5.3.14]
at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:638) ~[spring-beans-5.3.14.jar:5.3.14]
at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1352) ~[spring-beans-5.3.14.jar:5.3.14]
at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1195) ~[spring-beans-5.3.14.jar:5.3.14]
at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582) ~[spring-beans-5.3.14.jar:5.3.14]
at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542) ~[spring-beans-5.3.14.jar:5.3.14]
at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335) ~[spring-beans-5.3.14.jar:5.3.14]
at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[spring-beans-5.3.14.jar:5.3.14]
at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333) ~[spring-beans-5.3.14.jar:5.3.14]
at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208) ~[spring-beans-5.3.14.jar:5.3.14]
at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953) ~[spring-beans-5.3.14.jar:5.3.14]
at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918) ~[spring-context-5.3.14.jar:5.3.14]
at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583) ~[spring-context-5.3.14.jar:5.3.14]
at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:145) ~[spring-boot-2.6.2.jar:2.6.2]
at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:730) ~[spring-boot-2.6.2.jar:2.6.2]
at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:412) ~[spring-boot-2.6.2.jar:2.6.2]
at org.springframework.boot.SpringApplication.run(SpringApplication.java:302) ~[spring-boot-2.6.2.jar:2.6.2]
at org.springframework.boot.SpringApplication.run(SpringApplication.java:1301) ~[spring-boot-2.6.2.jar:2.6.2]
at org.springframework.boot.SpringApplication.run(SpringApplication.java:1290) ~[spring-boot-2.6.2.jar:2.6.2]
at com.jinghao.demo.DemoApplication.main(DemoApplication.java:10) ~[classes/:na]
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.boot.actuate.endpoint.web.servlet.AdditionalHealthEndpointPathsWebMvcHandlerMapping]: Factory method 'healthEndpointWebMvcHandlerMapping' threw exception; nested exception is java.util.NoSuchElementException: No value present
at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:185) ~[spring-beans-5.3.14.jar:5.3.14]
at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:653) ~[spring-beans-5.3.14.jar:5.3.14]
... 19 common frames omitted
Caused by: java.util.NoSuchElementException: No value present
at java.base/java.util.Optional.get(Optional.java:143) ~[na:na]
at org.springframework.boot.actuate.autoconfigure.health.HealthEndpointWebExtensionConfiguration.getHealthEndpoint(HealthEndpointWebExtensionConfiguration.java:81) ~[spring-boot-actuator-autoconfigure-2.6.2.jar:2.6.2]
at org.springframework.boot.actuate.autoconfigure.health.HealthEndpointWebExtensionConfiguration.access$000(HealthEndpointWebExtensionConfiguration.java:69) ~[spring-boot-actuator-autoconfigure-2.6.2.jar:2.6.2]
at org.springframework.boot.actuate.autoconfigure.health.HealthEndpointWebExtensionConfiguration$MvcAdditionalHealthEndpointPathsConfiguration.healthEndpointWebMvcHandlerMapping(HealthEndpointWebExtensionConfiguration.java:90) ~[spring-boot-actuator-autoconfigure-2.6.2.jar:2.6.2]
at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:154) ~[spring-beans-5.3.14.jar:5.3.14]
... 20 common frames omitted