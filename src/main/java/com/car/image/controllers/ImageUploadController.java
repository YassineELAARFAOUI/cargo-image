package com.car.image.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.car.image.models.Image;
import com.car.image.services.ImageService;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ImageUploadController {

    //private static final String UPLOAD_DIR = "C:\\Users\\khalid Bouhssine\\Desktop\\imgs\\";
	private static final String UPLOAD_DIR = "C:\\Users\\Yassine ELAARFAOUI\\OneDrive\\Desktop\\imgs\\";

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int RANDOM_STRING_LENGTH = 30;

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image) {
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Veuillez sélectionner une image.");
        }

        try {
            // Assurez-vous que le dossier d'upload existe
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Générer un nom aléatoire pour le fichier
            String randomFileName = generateRandomString() + getFileExtension(image.getOriginalFilename());
            String filePath = UPLOAD_DIR + randomFileName;

            // Sauvegarder l'image dans le dossier
            image.transferTo(new File(filePath));

            // Sauvegarder le nom de l'image dans la base de données
            Image savedImage = imageService.saveImage(randomFileName);

            return ResponseEntity.ok().body(new ResponseMessage("ID Image : " + savedImage.getIdImage()));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new ResponseMessage("Erreur lors de l'upload : " + e.getMessage()));
        }
    }
    
    @GetMapping("/image/{id}")
    public ResponseEntity<?> getImageNameById(@PathVariable Long id) {
        try {
            Image image = imageService.getImageById(id);
            if (image != null) {
                return ResponseEntity.ok().body(new ResponseMessage(image.getNameImage()));
            } else {
                return ResponseEntity.status(404).body("Image introuvable pour l'ID : " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la récupération de l'image : " + e.getMessage());
        }
    }


    private String generateRandomString() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(RANDOM_STRING_LENGTH);
        for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    private String getFileExtension(String originalFilename) {
        int lastDotIndex = originalFilename.lastIndexOf('.');
        if (lastDotIndex != -1 && lastDotIndex < originalFilename.length() - 1) {
            return originalFilename.substring(lastDotIndex);
        }
        return ""; // Retourne une chaîne vide si aucune extension n'est trouvée
    }

    private static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
    
    

}
