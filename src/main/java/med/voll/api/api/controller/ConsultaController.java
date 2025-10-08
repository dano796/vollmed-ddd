package med.voll.api.api.controller;

import jakarta.validation.Valid;
import med.voll.api.application.dto.request.DatosReservaConsulta;
import med.voll.api.application.dto.request.DatosCancelamientoConsulta;
import med.voll.api.application.dto.response.DatosDetalleConsulta;
import med.voll.api.application.usecase.CancelarConsultaUseCase;
import med.voll.api.application.usecase.ReservarConsultaUseCase;
import med.voll.api.application.query.ConsultaQueryService;
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
    private ReservarConsultaUseCase reservarConsultaUseCase;

    @Autowired
    private CancelarConsultaUseCase cancelarConsultaUseCase;

    @Autowired
    private ConsultaQueryService consultaQueryService;

    @PostMapping
    public ResponseEntity<DatosDetalleConsulta> reservar(@RequestBody @Valid DatosReservaConsulta datos, UriComponentsBuilder builder) {
        Consulta consulta = reservarConsultaUseCase.execute(datos);
        DatosDetalleConsulta datosDetalle = new DatosDetalleConsulta(consulta);
        URI url = builder.path("/consultas/{id}").buildAndExpand(consulta.getId()).toUri();
        return ResponseEntity.created(url).body(datosDetalle);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelar(@RequestBody @Valid DatosCancelamientoConsulta datos) {
        cancelarConsultaUseCase.execute(datos);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleConsulta>> listarConsultas(@PageableDefault(size = 10) Pageable paginacion) {
        Page<Consulta> consultas = consultaQueryService.listarConsultas(paginacion);
        Page<DatosDetalleConsulta> datosConsultas = consultas.map(DatosDetalleConsulta::new);
        return ResponseEntity.ok(datosConsultas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleConsulta> obtenerConsultaPorId(@PathVariable Long id) {
        try {
            Consulta consulta = consultaQueryService.obtenerConsultaPorId(id);
            DatosDetalleConsulta datosDetalle = new DatosDetalleConsulta(consulta);
            return ResponseEntity.ok(datosDetalle);
        } catch (Exception e) {
            throw new med.voll.api.domain.shared.ResourceNotFoundException("Consulta", id);
        }
    }

}
