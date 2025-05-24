package com.lievasoft.cefac.exception.types;

import com.lievasoft.cefac.exception.Problem;
import lombok.Getter;

@Getter
public class AlreadyExistsException extends RuntimeException {

    private final Problem problem;

    public AlreadyExistsException(String message, Problem problem) {
        super(message);
        this.problem = problem;
    }
}
