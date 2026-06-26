package ps.smartwarehouse.comum;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


@Component
public class EventoLog {

    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final int LIMITE = 200;

    private final Deque<String> eventos = new ArrayDeque<>();

    public synchronized void registrar(String mensagem) {
        String linha = "[" + LocalTime.now().format(HORA) + "] " + mensagem;
        System.out.println(linha);
        eventos.addFirst(linha);
        while (eventos.size() > LIMITE) eventos.removeLast();
    }

    public synchronized List<String> listar() {
        return new ArrayList<>(eventos);
    }
}
