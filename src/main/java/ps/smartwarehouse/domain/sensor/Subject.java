package ps.smartwarehouse.domain.sensor;

public interface Subject {
    public void registerObserver();
    public void removerObserver();
    public void notifyObserver();
}