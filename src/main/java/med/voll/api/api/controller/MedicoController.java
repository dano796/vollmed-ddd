package med.voll.api.api.controller;

import jakarta.validation.Valid;
import med.voll.api.application.dto.request.*;
import med.voll.api.application.dto.response.*;
import med.voll.api.application.usecase.ActualizarMedicoUseCase;
import med.voll.api.application.usecase.DesactivarMedicoUseCase;
import med.voll.api.application.usecase.RegistrarMedicoUseCase;
import med.voll.api.application.query.MedicoQueryService;
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
    private RegistrarMedicoUseCase registrarMedicoUseCase;

    @Autowired
    private ActualizarMedicoUseCase actualizarMedicoUseCase;

    @Autowired
    private DesactivarMedicoUseCase desactivarMedicoUseCase;

    @Autowired
    private MedicoQueryService medicoQueryService;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = registrarMedicoUseCase.execute(datosRegistroMedico);
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico);
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 10) Pageable paginacion) {
        Page<Medico> medicos = medicoQueryService.listarMedicos(paginacion);
        Page<DatosListadoMedico> datosListado = medicos.map(DatosListadoMedico::new);
        return ResponseEntity.ok(datosListado);
    }

    @PutMapping
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
        Medico medico = actualizarMedicoUseCase.execute(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedico(@PathVariable Long id) {
        desactivarMedicoUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id) {
        try {
            Medico medico = medicoQueryService.obtenerMedico(id);
            return ResponseEntity.ok(new DatosRespuestaMedico(medico));
        } catch (Exception e) {
            // Si no se encuentra el médico, debería devolver 404, no 403
            throw new med.voll.api.domain.shared.ResourceNotFoundException("Médico", id);
        }
    }
}
