package com.ignacio.backendmereles.Tacho;

import com.ignacio.backendmereles.config.CloudinaryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TachoService {

    private final TachoRepository tachoRepository;

    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public TachoService(TachoRepository tachoRepository) {
        this.tachoRepository = tachoRepository;
    }

    private void notificarCambioTachos() {
        messagingTemplate.convertAndSend("/topic/tachos", "actualizar");
    }

    public Tacho crearTacho(String descripcion, Double peso, MultipartFile imagen) throws IOException {
        String imagePath = saveImage(imagen);
        Tacho tacho = new Tacho();
        tacho.setDescripcion(descripcion);
        tacho.setPeso(peso);
        tacho.setImagen(imagePath);
        tacho.setActivo(true);

        Tacho guardado = tachoRepository.save(tacho);
        notificarCambioTachos();
        return guardado;
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

            Tacho actualizado = tachoRepository.save(tacho);
            notificarCambioTachos();
            return actualizado;
        });
    }

    public Optional<Tacho> desactivarTacho(Long id) {
        return tachoRepository.findById(id).map(tacho -> {
            tacho.setActivo(false);
            Tacho desactivado = tachoRepository.save(tacho);
            notificarCambioTachos();
            return desactivado;
        });
    }

    private String saveImage(MultipartFile imagen) throws IOException {
        return cloudinaryService.uploadImage(imagen);
    }

    private void deleteImage(String imageUrl) {
        cloudinaryService.deleteImage(imageUrl);
    }

    public Tacho obtenerTacho(Long id) {
        return tachoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tacho con ID " + id + " no encontrado"));
    }
}
