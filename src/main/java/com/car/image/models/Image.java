package com.car.image.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "image")
public class Image {
	// cargo-image version2
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImage;
    
    @Column(name = "nameImage", nullable = false, length = 35)
    private String nameImage;
    
    @Column(name = "dateCreation", nullable = false)
    private LocalDate dateCreation;
}
