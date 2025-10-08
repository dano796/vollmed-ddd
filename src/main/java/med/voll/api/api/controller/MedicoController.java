package med.voll.api.api.controller;

import jakarta.validation.Valid;
import med.voll.api.application.dto.request.*;
import med.voll.api.application.dto.response.*;
import med.voll.api.application.service.GestionMedicoService;
import med.voll.api.domain.entities.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private GestionMedicoService gestionMedicoService;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = gestionMedicoService.registrarMedico(datosRegistroMedico);
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico);
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 10) Pageable paginacion) {
        Page<Medico> medicos = gestionMedicoService.listarMedicos(paginacion);
        Page<DatosListadoMedico> datosListado = medicos.map(DatosListadoMedico::new);
        return ResponseEntity.ok(datosListado);
    }

    @PutMapping
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
        Medico medico = gestionMedicoService.actualizarMedico(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedico(@PathVariable Long id) {
        gestionMedicoService.eliminarMedico(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id) {
        try {
            Medico medico = gestionMedicoService.obtenerMedico(id);
            return ResponseEntity.ok(new DatosRespuestaMedico(medico));
        } catch (Exception e) {
            // Si no se encuentra el médico, debería devolver 404, no 403
            throw new med.voll.api.domain.shared.ResourceNotFoundException("Médico", id);
        }
    }
}
