package com.jinghao.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

@Slf4j
public class LogListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {
    public static final int ORDER = Ordered.HIGHEST_PRECEDENCE + 100;

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        boolean detected = CloudPlatform.CLOUD_FOUNDRY.isDetected(event.getEnvironment());
        log.info("### PCF Platform detected? [" + detected + "]");
    }

    @Override
    public int getOrder() {
        return ORDER;
    }

}
