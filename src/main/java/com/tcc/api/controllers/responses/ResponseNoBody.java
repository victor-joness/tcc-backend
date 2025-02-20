package com.tcc.api.controllers.responses;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseNoBody extends Response{

    public ResponseNoBody(String message) {
        super(message);
    }
}
