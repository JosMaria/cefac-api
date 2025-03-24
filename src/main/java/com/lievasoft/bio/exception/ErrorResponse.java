package com.lievasoft.bio.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        String path,
        String reason,
        LocalDateTime timestamp
) {
}
