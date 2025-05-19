package com.wilhelmaoi.sunote.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * OSS配置属性类
 */
@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class OssProperties {
    private String accessKey;
    private String secretKey;
    private String endpoint;
    private String bucket;
    private String region;
    private String urlPrefix;
} 