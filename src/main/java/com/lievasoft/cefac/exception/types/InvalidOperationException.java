package com.lievasoft.cefac.exception.types;

import com.lievasoft.cefac.exception.Problem;
import lombok.Getter;

@Getter
public class InvalidOperationException extends RuntimeException {

    private final Problem problem;

    public InvalidOperationException() {
        super("This operation is prohibited.");
        problem = Problem.INVALID_OPERATION;
    }
}
