package com.platform.cloud.xpmanagement.common;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Parameter(
        description = "Player id is requested and should be integer",
        schema = @Schema(pattern = "[0-9]*", maxLength = 32)
)
public @interface PlayerId {
}
