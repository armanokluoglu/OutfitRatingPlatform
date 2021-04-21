package model.domain;

public interface Subject {

	public void register(Observer obj);

	public void unregister(Observer obj);

	public void notifyObservers();
}