package com.tcc.api.responses;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseOk extends Response{
    Object data = null;

    public ResponseOk(String message, Object data) {
        super(message);
        this.data = data;
    }
}
