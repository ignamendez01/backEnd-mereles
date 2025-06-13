package com.ignacio.backendmereles.Modelo;

import com.ignacio.backendmereles.config.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public ModeloService(ModeloRepository modeloRepository) {
        this.modeloRepository = modeloRepository;
    }

    private void notificarCambioModelos() {
        messagingTemplate.convertAndSend("/topic/modelos", "actualizar");
    }

    public Modelo crearModelo(String descripcion, Double peso, MultipartFile imagen) throws IOException {
        String imagePath = saveImage(imagen);
        Modelo modelo = new Modelo();
        modelo.setDescripcion(descripcion);
        modelo.setPeso(peso);
        modelo.setImagen(imagePath);
        modelo.setActivo(true);

        Modelo guardado = modeloRepository.save(modelo);
        notificarCambioModelos();
        return guardado;
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

                try {
                    String nuevaImagenPath = saveImage(imagen);
                    modelo.setImagen(nuevaImagenPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            Modelo actualizado = modeloRepository.save(modelo);
            notificarCambioModelos();
            return actualizado;
        });
    }

    public Optional<Modelo> desactivarModelo(Long id) {
        return modeloRepository.findById(id).map(modelo -> {
            modelo.setActivo(false);
            Modelo desactivado = modeloRepository.save(modelo);
            notificarCambioModelos();
            return desactivado;
        });
    }

    private String saveImage(MultipartFile imagen) throws IOException {
        return cloudinaryService.uploadImage(imagen);
    }

    private void deleteImage(String imageUrl) {
        cloudinaryService.deleteImage(imageUrl);
    }

}
