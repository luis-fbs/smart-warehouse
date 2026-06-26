package ps.smartwarehouse.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ps.smartwarehouse.comum.EventoLog;
import ps.smartwarehouse.inventario.InventarioService;
import ps.smartwarehouse.inventario.domain.Corredor;
import ps.smartwarehouse.inventario.domain.Galpao;
import ps.smartwarehouse.inventario.domain.Prateleira;
import ps.smartwarehouse.inventario.domain.Produto;
import ps.smartwarehouse.inventario.domain.Slot;
import ps.smartwarehouse.inventario.domain.Zona;
import ps.smartwarehouse.inventario.sensor.Fumaca;
import ps.smartwarehouse.inventario.sensor.Sensor;
import ps.smartwarehouse.inventario.sensor.Temperatura;
import ps.smartwarehouse.inventario.sensor.Umidade;
import ps.smartwarehouse.operacoes.OperacoesService;
import ps.smartwarehouse.operacoes.robo.Robo;

import java.time.LocalDate;


@Component
public class DadosIniciais implements ApplicationRunner {

    private final InventarioService inventario;
    private final OperacoesService operacoes;
    private final EventoLog log;

    public DadosIniciais(InventarioService inventario, OperacoesService operacoes, EventoLog log) {
        this.inventario = inventario;
        this.operacoes = operacoes;
        this.log = log;
    }

    @Override
    public void run(ApplicationArguments args) {
        Galpao galpao = inventario.getGalpao();

        Zona zonaA = new Zona("A", 0);
        instalarSensor(zonaA, new Temperatura(2, 8));   // câmara fria
        instalarSensor(zonaA, new Umidade(30, 70));
        Slot a0 = new Slot("A-S0", 3, 0);
        Slot a1 = new Slot("A-S1", 3, 1);
        Slot a2 = new Slot("A-S2", 3, 2);
        a0.armazenar(new Produto("Vacina-Lote1", LocalDate.now().plusDays(20), Produto.Categoria.PERECIVEL, 2, 0.9));
        zonaA.adicionarCorredor(new Corredor("A-C1")
                .adicionarPrateleira(new Prateleira("A-P1", 3).adicionarSlot(a0).adicionarSlot(a1).adicionarSlot(a2)));
        galpao.adicionarZona(zonaA);

        Zona zonaB = new Zona("B", 2);
        instalarSensor(zonaB, new Umidade(30, 70));
        instalarSensor(zonaB, new Fumaca(10));
        Slot b0 = new Slot("B-S0", 2, 0);
        Slot b1 = new Slot("B-S1", 2, 1);
        Slot b2 = new Slot("B-S2", 4, 2);
        b0.armazenar(new Produto("Parafuso-M8", LocalDate.now().plusYears(3), Produto.Categoria.COMUM, 0, 0.3));
        b2.armazenar(new Produto("Cabo-Rede", LocalDate.now().plusYears(2), Produto.Categoria.COMUM, 0, 0.2));
        zonaB.adicionarCorredor(new Corredor("B-C1")
                .adicionarPrateleira(new Prateleira("B-P1", 3).adicionarSlot(b0).adicionarSlot(b1).adicionarSlot(b2)));
        galpao.adicionarZona(zonaB);

        Zona zonaC = new Zona("C", 4);
        instalarSensor(zonaC, new Temperatura(15, 30));
        instalarSensor(zonaC, new Fumaca(10));
        Slot c0 = new Slot("C-S0", 2, 0);
        Slot c1 = new Slot("C-S1", 2, 1);
        zonaC.adicionarCorredor(new Corredor("C-C1")
                .adicionarPrateleira(new Prateleira("C-P1", 2).adicionarSlot(c0).adicionarSlot(c1)));
        galpao.adicionarZona(zonaC);

        operacoes.adicionarRobo(new Robo("R1", "A"));
        operacoes.adicionarRobo(new Robo("R2", "B"));
        operacoes.adicionarRobo(new Robo("R3", "C"));

        inventario.registrarObservadorGlobal(inventario.getSistemaAlerta());
        inventario.registrarObservadorGlobal(operacoes);

        log.registrar("Galpão inicializado: 3 zonas, "
                + inventario.sensores().size() + " sensores, 3 robôs.");
    }

    private void instalarSensor(Zona zona, Sensor sensor) {
        sensor.setZona(zona);
        zona.adicionarSensor(sensor);
    }
}
