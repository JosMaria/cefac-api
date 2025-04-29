package com.lievasoft.cefac.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        String path,
        String reason,
        LocalDateTime timestamp,
        Problem problem
) {
}
