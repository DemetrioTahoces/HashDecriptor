package com.demetrio.hashdecriptor.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hash {

    @Id
    @Column(unique = true, nullable = false)
    @JsonIgnore
    private String id;

    private String algorithm;

    private String text;

    private String hash;

}
