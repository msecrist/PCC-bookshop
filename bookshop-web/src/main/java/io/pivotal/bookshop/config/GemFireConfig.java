package io.pivotal.bookshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.EnableCachingDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;

@Configuration
@EnableCachingDefinedRegions
@EnableEntityDefinedRegions(basePackages = "io.pivotal.bookshop.domain")
public class GemFireConfig {
}
