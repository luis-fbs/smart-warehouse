package ps.smartwarehouse.inventario.sensor;


public interface Subject {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}
