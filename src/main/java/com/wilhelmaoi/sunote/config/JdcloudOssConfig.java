package com.wilhelmaoi.sunote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import java.net.URI;
import java.time.Duration;

/**
 * 京东云OSS配置类 (基于AWS S3 SDK 2.x)
 */
@Configuration
public class JdcloudOssConfig {

    @Autowired
    private OssProperties ossProperties;

    /**
     * 创建S3客户端Bean
     */
    @Bean
    public S3Client s3Client() {
        // 配置凭证
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                ossProperties.getAccessKey(), 
                ossProperties.getSecretKey());
        
        // HTTP客户端配置
        ApacheHttpClient.Builder httpClientBuilder = ApacheHttpClient.builder()
                .connectionTimeout(Duration.ofSeconds(10))
                .socketTimeout(Duration.ofSeconds(10));
        
        // S3配置
        S3Configuration s3Configuration = S3Configuration.builder()
                .pathStyleAccessEnabled(true) // 使用路径样式访问
                .chunkedEncodingEnabled(false) // 禁用分块编码，兼容京东云
                .build();
        
        // 创建客户端
        S3Client s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .httpClient(httpClientBuilder.build())
                .region(Region.of(ossProperties.getRegion()))
                .endpointOverride(URI.create("https://" + ossProperties.getEndpoint()))
                .serviceConfiguration(s3Configuration)
                .build();
        
        return s3Client;
    }
} 