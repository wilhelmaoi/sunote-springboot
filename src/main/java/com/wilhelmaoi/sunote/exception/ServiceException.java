package com.wilhelmaoi.sunote.exception;

/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2025/4/1 00:35
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}