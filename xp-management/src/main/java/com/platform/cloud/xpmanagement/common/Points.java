package com.platform.cloud.xpmanagement.common;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Parameter(
        description = "Player id is requested and should be integer",
        schema = @Schema(pattern = "^(\\+|-)?\\d+$")
)
public @interface Points {
}
