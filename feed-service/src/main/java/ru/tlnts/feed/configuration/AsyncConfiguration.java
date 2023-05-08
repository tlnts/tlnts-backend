package ru.tlnts.feed.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author vasev-dm
 */
@Configuration
@EnableAsync(proxyTargetClass = true)
public class AsyncConfiguration {
}
