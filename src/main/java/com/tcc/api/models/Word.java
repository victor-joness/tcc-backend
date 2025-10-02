package com.tcc.api.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "words")
@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;
    private String description;
    private String video;
    private String status;
    private String modulo;

    @Column(name = "request_word_id")
    private Long request_word_id;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "word", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Variation> variations;
}
