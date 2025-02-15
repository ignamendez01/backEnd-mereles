package com.ignacio.backendmereles.Modelo;

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
public class ModeloService {

    private final ModeloRepository modeloRepository;

    public ModeloService(ModeloRepository modeloRepository) {
        this.modeloRepository = modeloRepository;
    }

    public Modelo crearModelo(String descripcion, Double peso, MultipartFile imagen) throws IOException {
        String imagePath = saveImage(imagen);
        Modelo modelo = new Modelo();
        modelo.setDescripcion(descripcion);
        modelo.setPeso(peso);
        modelo.setImagen(imagePath);
        modelo.setActivo(true);

        return modeloRepository.save(modelo);
    }

    public List<Modelo> obtenerTodosLosModelos() {
        return modeloRepository.findAll();
    }

    public List<Modelo> obtenerModelosActivos() {
        return modeloRepository.findByActivoTrueOrderByIdAsc();
    }

    public Optional<Modelo> actualizarModelo(Long id, String descripcion, Double peso, MultipartFile imagen) throws IOException {
        return modeloRepository.findById(id).map(modelo -> {
            modelo.setDescripcion(descripcion);
            modelo.setPeso(peso);

            if (imagen != null && !imagen.isEmpty()) {
                if (modelo.getImagen() != null) {
                    deleteImage(modelo.getImagen());
                }

                String nuevaImagenPath;
                try {
                    nuevaImagenPath = saveImage(imagen);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                modelo.setImagen(nuevaImagenPath);
            }

            return modeloRepository.save(modelo);
        });
    }

    private void deleteImage(String imagePath) {
        Path path = Paths.get("/app/imagenes/" + imagePath);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Optional<Modelo> desactivarModelo(Long id) {
        return modeloRepository.findById(id).map(modelo -> {
            modelo.setActivo(false);
            return modeloRepository.save(modelo);
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

}
