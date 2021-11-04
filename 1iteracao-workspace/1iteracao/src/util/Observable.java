package util;

public interface Observable<T> {
	public void add(Observer<T> observer);
	public void remove(Observer<T> observer);
	public void notifyObserver();
}
