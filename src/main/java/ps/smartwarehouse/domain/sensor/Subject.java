package ps.smartwarehouse.domain.sensor;

public interface Subject {
    void registerObserver(Observer observer);
    void removerObserver(Observer observer);
    void notifyObserver();
}