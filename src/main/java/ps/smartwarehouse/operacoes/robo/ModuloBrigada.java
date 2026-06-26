package ps.smartwarehouse.operacoes.robo;

import org.springframework.stereotype.Component;
import ps.smartwarehouse.comum.EventoLog;

import java.util.List;


@Component
public class ModuloBrigada {

    private final EventoLog log;

    public ModuloBrigada(EventoLog log) {
        this.log = log;
    }

    public void acionar(String zonaId, List<Robo> robos) {
        log.registrar("BRIGADA acionada para a Zona " + zonaId + " — calculando rotas de evacuação...");
        List<Robo> naArea = robos.stream()
                .filter(r -> zonaId.equals(r.getZonaAtual()))
                .toList();
        if (naArea.isEmpty()) {
            log.registrar("BRIGADA: nenhum robô na Zona " + zonaId + ".");
            return;
        }
        for (Robo robo : naArea) {
            robo.setEstado(Robo.Estado.EVACUANDO);
            log.registrar("Robô " + robo.getId() + " EVACUANDO Zona " + zonaId
                    + " -> rota prioritária para a saída de emergência.");
        }
    }
}
