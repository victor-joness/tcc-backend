package com.tcc.api.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "request_words")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    private String videoUrl;

    private String category;

    private Long requestUserId;

    private String status;

    private Long interpreterId;
}
