package com.spring.boot.lms.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;

    private String resourceFieldName;

    private long resourceFieldValue;

    public ResourceNotFoundException(String resourceName, String resourceFieldName, long resourceFieldValue) {
        super(String.format("%s is not found %s: %d", resourceName, resourceFieldName, resourceFieldValue));
    }
}
