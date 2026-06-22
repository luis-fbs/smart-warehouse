package ps.smartwarehouse.domain.alocador;

import ps.smartwarehouse.domain.Produto;

import java.util.Collection;


public class ControleAmbiental implements AlocadorStrategy{
    @Override
    public void alocar(Collection<Produto> produtos){
        System.out.println("Alocando por controle ambiental.");
    }
}
