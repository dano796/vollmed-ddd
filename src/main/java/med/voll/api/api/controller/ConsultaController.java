package med.voll.api.api.controller;

import jakarta.validation.Valid;
import med.voll.api.application.dto.request.DatosReservaConsulta;
import med.voll.api.application.dto.request.DatosCancelamientoConsulta;
import med.voll.api.application.dto.response.DatosDetalleConsulta;
import med.voll.api.application.service.GestionConsultaService;
import med.voll.api.domain.aggregates.Consulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private GestionConsultaService gestionConsultaService;

    @PostMapping
    public ResponseEntity<DatosDetalleConsulta> reservar(@RequestBody @Valid DatosReservaConsulta datos, UriComponentsBuilder builder) {
        Consulta consulta = gestionConsultaService.reservarConsulta(datos);
        DatosDetalleConsulta datosDetalle = new DatosDetalleConsulta(consulta);
        URI url = builder.path("/consultas/{id}").buildAndExpand(consulta.getId()).toUri();
        return ResponseEntity.created(url).body(datosDetalle);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelar(@RequestBody @Valid DatosCancelamientoConsulta datos) {
        gestionConsultaService.cancelarConsulta(datos);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleConsulta>> listarConsultas(@PageableDefault(size = 10) Pageable paginacion) {
        Page<Consulta> consultas = gestionConsultaService.listarConsultas(paginacion);
        Page<DatosDetalleConsulta> datosConsultas = consultas.map(DatosDetalleConsulta::new);
        return ResponseEntity.ok(datosConsultas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleConsulta> obtenerConsultaPorId(@PathVariable Long id) {
        try {
            Consulta consulta = gestionConsultaService.obtenerConsultaPorId(id);
            DatosDetalleConsulta datosDetalle = new DatosDetalleConsulta(consulta);
            return ResponseEntity.ok(datosDetalle);
        } catch (med.voll.api.domain.shared.ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
