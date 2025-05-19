package com.wilhelmaoi.sunote.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wilhelmaoi.sunote.config.OssProperties;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于AWS S3 SDK 2.x的京东云OSS存储服务
 */
@Slf4j
@Service
public class OssService {

    @Autowired
    private OssProperties ossProperties;

    @Autowired
    private S3Client s3Client;

    /**
     * 上传文件到OSS
     * 
     * @param file         文件
     * @param objectKey    对象键（存储路径）
     * @return 文件访问URL
     */
    public String uploadFile(MultipartFile file, String objectKey) {
        try {
            // 准备请求元数据
            Map<String, String> metadata = new HashMap<>();
            
            // 设置Content-Type
            String contentType = file.getContentType();
            if (contentType != null) {
                metadata.put("Content-Type", contentType);
            }
            
            // 构建上传请求
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(ossProperties.getBucket())
                    .key(objectKey)
                    .metadata(metadata)
                    .build();
            
            // 执行上传
            s3Client.putObject(putObjectRequest, 
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            
            log.info("上传文件成功, objectKey: {}", objectKey);
            
            // 返回文件URL
            return generateFileUrl(objectKey);
        } catch (IOException e) {
            log.error("上传文件到OSS失败: {}", e.getMessage(), e);
            throw new RuntimeException("上传文件失败: " + e.getMessage());
        }
    }

    /**
     * 生成文件访问URL
     * 
     * @param objectKey 对象键
     * @return 访问URL
     */
    private String generateFileUrl(String objectKey) {
        // 使用配置的URL前缀生成文件访问地址
        return ossProperties.getUrlPrefix() + objectKey;
    }
} 