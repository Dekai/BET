package com.dk.alirr.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ResourceNotFoundException extends AbstractThrowableProblem {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String entityName, Long id) {
        super(ErrorConstants.OBJECT_NOT_FOUND, String.format("Resource[%s] with ID=%d not exists",entityName, id), Status.NOT_FOUND);
    }
}
