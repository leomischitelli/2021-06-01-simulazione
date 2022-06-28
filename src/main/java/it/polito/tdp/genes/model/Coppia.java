package it.polito.tdp.genes.model;

public class Coppia {
	
	private Genes g1;
	private Genes g2;
	private double corr;
	public Coppia(Genes g1, Genes g2, double corr) {
		super();
		this.g1 = g1;
		this.g2 = g2;
		this.corr = corr;
	}
	public Genes getG1() {
		return g1;
	}
	public Genes getG2() {
		return g2;
	}
	public double getCorr() {
		return corr;
	}
	

}
