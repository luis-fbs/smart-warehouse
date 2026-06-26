package ps.smartwarehouse.controller;

import ps.smartwarehouse.comum.EventoLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoLog log;

    public EventoController(EventoLog log) {
        this.log = log;
    }

    @GetMapping
    public List<String> eventos() {
        return log.listar();
    }
}
