package com.lievasoft.cefac.exception.types;

import com.lievasoft.cefac.exception.Problem;
import lombok.Getter;

@Getter
public class OperationNotAllowedException extends RuntimeException {

    private final Problem problem;

    public OperationNotAllowedException() {
        super("This operation is prohibited.");
        problem = Problem.OPERATION_NOT_ALLOWED;
    }
}
