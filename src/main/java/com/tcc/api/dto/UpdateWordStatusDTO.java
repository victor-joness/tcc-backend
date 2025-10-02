package com.tcc.api.dto;

public class UpdateWordStatusDTO {
    private String status;
    private Long interpreterId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getInterpreterId() {
        return interpreterId;
    }

    public void setInterpreterId(Long interpreterId) {
        this.interpreterId = interpreterId;
    }
}