package com.tcc.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestWordDTO {
    private String word;
    private String video_url;
    private String category;
    private Long request_user_id;
    private String status;
    private Long interpreter_id;
}
