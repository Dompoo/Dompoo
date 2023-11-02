package dompoo.study.observerPattern.subject;

import dompoo.study.observerPattern.observer.Observer;

public interface Subject {

    void addObserver(Observer observer);
    void deleteObserver(Observer observer);
    void notifyObserver();
}
