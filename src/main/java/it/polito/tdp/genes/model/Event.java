package it.polito.tdp.genes.model;

public class Event implements Comparable<Event> {
	
	public enum EventType{
		NUOVO_MESE,
		SPOSTAMENTO
	}
	
	private int mese;
	private EventType type;
	private int ingegnere;
	private Genes geneAttuale;
	
	

	public Event(int mese, EventType type, int ingegnere, Genes geneAttuale) {
		super();
		this.mese = mese;
		this.type = type;
		this.ingegnere = ingegnere;
		this.geneAttuale = geneAttuale;
	}



	public int getMese() {
		return mese;
	}



	public EventType getType() {
		return type;
	}



	public int getIngegnere() {
		return ingegnere;
	}



	public Genes getGeneAttuale() {
		return geneAttuale;
	}



	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.mese - o.getMese();
	}

}
