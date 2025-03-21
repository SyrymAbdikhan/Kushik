package dev.shetel.kushik.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isPrimary = false;
}
