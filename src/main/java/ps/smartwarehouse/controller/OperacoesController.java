package ps.smartwarehouse.controller;

import ps.smartwarehouse.operacoes.OperacoesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ps.smartwarehouse.inventario.domain.Produto;
import ps.smartwarehouse.operacoes.robo.Robo;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/operacoes")
public class OperacoesController {

    private final OperacoesService operacoes;

    public OperacoesController(OperacoesService operacoes) {
        this.operacoes = operacoes;
    }

    @GetMapping("/estrategias")
    public List<String> estrategias() {
        return operacoes.estrategiasDisponiveis();
    }

    @GetMapping("/robos")
    public List<Map<String, Object>> robos() {
        return operacoes.getRobos().stream().map(r -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", r.getId());
            m.put("zona", r.getZonaAtual());
            m.put("estado", r.getEstado().name());
            return m;
        }).toList();
    }

    public record ProdutoRequest(String nome, String categoria, Integer risco,
                                 Double indiceSaida, Integer validadeDias) {}

    public record LoteRequest(String estrategia, List<ProdutoRequest> produtos) {}

    @PostMapping("/recebimento")
    public List<Map<String, Object>> receber(@RequestBody LoteRequest req) {
        List<Produto> lote = req.produtos().stream().map(this::toProduto).toList();
        return operacoes.receberLote(lote, req.estrategia());
    }

    private Produto toProduto(ProdutoRequest p) {
        Produto.Categoria categoria = Produto.Categoria.valueOf(p.categoria().toUpperCase());
        int validadeDias = p.validadeDias() != null ? p.validadeDias() : 365;
        int risco = p.risco() != null ? p.risco() : 0;
        double indice = p.indiceSaida() != null ? p.indiceSaida() : 0.5;
        return new Produto(p.nome(), LocalDate.now().plusDays(validadeDias), categoria, risco, indice);
    }
}
