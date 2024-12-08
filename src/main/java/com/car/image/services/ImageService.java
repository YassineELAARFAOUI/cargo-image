package com.car.image.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.image.models.Image;
import com.car.image.repository.ImageRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image saveImage(String nameImage) {
        Image image = new Image();
        image.setNameImage(nameImage);
        image.setDateCreation(LocalDate.now());
        return imageRepository.save(image);
    }

    public Image getImageById(Long idImage) {
        // Retrieve the image by ID, or return null if not found
        Optional<Image> imageOptional = imageRepository.findById(idImage);
        return imageOptional.orElse(null);
    }
}
