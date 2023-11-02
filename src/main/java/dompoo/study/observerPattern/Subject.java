package dompoo.study.observerPattern;

public interface Subject {

    void addObserver(Observer observer);
    void deleteObserver(Observer observer);
    void notifyObserver();
}
