package br.edu.ifpb.simpleevents.desingPattern.Observer;

import java.util.ArrayList;

public abstract class Subject {
	private ArrayList<Observer> observers;
	
	public Subject() {
		super();
		this.observers = new ArrayList<Observer>();
	}
	
	public void attach(Observer observer){
		observers.add(observer);		
	}
	
	public void deattach(Observer observer){
		observers.remove(observer);		
	}

	public final void notifyAllObservers() {
		for (int i=0; i<observers.size(); i++){
			Observer ob = (Observer)observers.get(i);
			ob.update();
		} 	
	}


}
