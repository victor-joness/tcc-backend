package com.tcc.api.controllers.responses;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseCreated extends Response{
    Object data = null;

    public ResponseCreated(String message, Object data) {
        super(message);
        this.data = data;
    }
}
