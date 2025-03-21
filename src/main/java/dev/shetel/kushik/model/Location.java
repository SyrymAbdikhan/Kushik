package dev.shetel.kushik.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 2)
    private String countryCode;
}
