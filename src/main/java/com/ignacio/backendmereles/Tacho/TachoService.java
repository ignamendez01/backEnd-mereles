package com.ignacio.backendmereles.Tacho;

import com.ignacio.backendmereles.Modelo.Modelo;
import com.ignacio.backendmereles.Modelo.ModeloRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TachoService {

    private final TachoRepository tachoRepository;

    public TachoService(TachoRepository tachoRepository) {
        this.tachoRepository = tachoRepository;
    }

    public Tacho crearTacho(String descripcion, Double peso, MultipartFile imagen) throws IOException {
        String imagePath = saveImage(imagen);
        Tacho tacho = new Tacho();
        tacho.setDescripcion(descripcion);
        tacho.setPeso(peso);
        tacho.setImagen(imagePath);

        tacho.setActivo(true);

        return tachoRepository.save(tacho);
    }

    public List<Tacho> obtenerTodosLosTachos() {
        return tachoRepository.findAll();
    }

    public List<Tacho> obtenerTachosActivos() {
        return tachoRepository.findByActivoTrueOrderByIdAsc();
    }

    public Optional<Tacho> actualizarTacho(Long id, String descripcion, Double peso, MultipartFile imagen) throws IOException {
        return tachoRepository.findById(id).map(tacho -> {
            tacho.setDescripcion(descripcion);
            tacho.setPeso(peso);

            if (imagen != null && !imagen.isEmpty()) {
                if (tacho.getImagen() != null) {
                    deleteImage(tacho.getImagen());
                }

                String nuevaImagenPath;
                try {
                    nuevaImagenPath = saveImage(imagen);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                tacho.setImagen(nuevaImagenPath);
            }

            return tachoRepository.save(tacho);
        });
    }

    public Optional<Tacho> desactivarTacho(Long id) {
        return tachoRepository.findById(id).map(tacho -> {
            tacho.setActivo(false);
            return tachoRepository.save(tacho);
        });
    }

    private String saveImage(MultipartFile imagen) throws IOException {
        String folderPath = "/app/imagenes/";
        String fileName = UUID.randomUUID() + "-" + imagen.getOriginalFilename();
        Path path = Paths.get(folderPath + fileName);

        Files.createDirectories(path.getParent());
        Files.write(path, imagen.getBytes());

        String baseUrl = System.getenv("BASE_URL");
        if (baseUrl == null) {
            baseUrl = "http://localhost:8080";
        }

        return baseUrl + "/imagenes/" + fileName;
    }

    private void deleteImage(String imagePath) {
        Path path = Paths.get("/app/imagenes/" + imagePath);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
