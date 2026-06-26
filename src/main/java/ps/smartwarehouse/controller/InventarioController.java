package ps.smartwarehouse.controller;

import ps.smartwarehouse.inventario.InventarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ps.smartwarehouse.inventario.sensor.Sensor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final InventarioService inventario;

    public InventarioController(InventarioService inventario) {
        this.inventario = inventario;
    }

    @GetMapping("/mapa")
    public List<Map<String, Object>> mapa() {
        return inventario.mapa();
    }

    @GetMapping("/auditoria")
    public List<Map<String, Object>> auditoria() {
        return inventario.auditoria();
    }

    @GetMapping("/sensores")
    public List<Map<String, Object>> sensores() {
        return inventario.sensores().stream().map(this::toDto).toList();
    }

    public record LeituraRequest(String zona, String tipo, double valor) {}

    @PostMapping("/sensores/leitura")
    public Map<String, Object> leitura(@RequestBody LeituraRequest req) {
        Sensor sensor = inventario.registrarLeitura(req.zona(), req.tipo(), req.valor());
        return toDto(sensor);
    }

    @PostMapping("/zonas/{id}/desbloquear")
    public Map<String, Object> desbloquear(@PathVariable String id) {
        inventario.desbloquearZona(id);
        Map<String, Object> r = new LinkedHashMap<>();
        r.put("zona", id);
        r.put("bloqueada", false);
        return r;
    }

    private Map<String, Object> toDto(Sensor s) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("zona", s.getZona() != null ? s.getZona().getIdentificador() : null);
        m.put("tipo", s.getTipo());
        m.put("estado", s.getEstado().name());
        m.put("leitura", s.getLeituraAtual());
        m.put("limiteMin", s.getLimiteMin());
        m.put("limiteMax", s.getLimiteMax());
        return m;
    }
}
