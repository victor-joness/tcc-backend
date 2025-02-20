package com.tcc.api.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Response {
    final String message;
}
