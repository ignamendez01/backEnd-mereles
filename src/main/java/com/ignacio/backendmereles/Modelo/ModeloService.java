package com.ignacio.backendmereles.Modelo;

import com.ignacio.backendmereles.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ModeloService {

    private final ModeloRepository modeloRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

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

    public Optional<Modelo> desactivarModelo(Long id) {
        return modeloRepository.findById(id).map(modelo -> {
            modelo.setActivo(false);
            return modeloRepository.save(modelo);
        });
    }

    private String saveImage(MultipartFile imagen) throws IOException {
        return cloudinaryService.uploadImage(imagen);
    }

    private void deleteImage(String imageUrl) {
        cloudinaryService.deleteImage(imageUrl);
    }

}
