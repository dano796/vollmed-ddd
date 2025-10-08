package med.voll.api.api.controller;

import jakarta.validation.Valid;
import med.voll.api.application.dto.request.*;
import med.voll.api.application.dto.response.*;
import med.voll.api.application.service.GestionPacienteService;
import med.voll.api.domain.entities.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private GestionPacienteService gestionPacienteService;

    @PostMapping
    public ResponseEntity<DatosListaPaciente> registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datos, UriComponentsBuilder uriBuilder) {
        Paciente paciente = gestionPacienteService.registrarPaciente(datos);
        DatosListaPaciente datosRespuesta = new DatosListaPaciente(paciente);
        URI url = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaPaciente>> listarPacientes(@PageableDefault(size = 10) Pageable paginacion) {
        Page<Paciente> pacientes = gestionPacienteService.listarPacientes(paginacion);
        Page<DatosListaPaciente> datosLista = pacientes.map(DatosListaPaciente::new);
        return ResponseEntity.ok(datosLista);
    }

    @PutMapping
    public ResponseEntity<DatosListaPaciente> actualizarPaciente(@RequestBody @Valid DatosActualizacionPaciente datos) {
        Paciente paciente = gestionPacienteService.actualizarPaciente(datos);
        return ResponseEntity.ok(new DatosListaPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        gestionPacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListaPaciente> obtenerPaciente(@PathVariable Long id) {
        Paciente paciente = gestionPacienteService.obtenerPaciente(id);
        return ResponseEntity.ok(new DatosListaPaciente(paciente));
    }
}
