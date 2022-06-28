package it.polito.tdp.genes.model;

public class Adiacenza implements Comparable<Adiacenza>{

	private Genes gene;
	private double corr;
	public Adiacenza(Genes gene, double corr) {
		super();
		this.gene = gene;
		this.corr = corr;
	}
	public Genes getGene() {
		return gene;
	}
	public double getCorr() {
		return corr;
	}
	@Override
	public int compareTo(Adiacenza o) {
		// TODO Auto-generated method stub
		return Double.compare(o.getCorr(), this.corr);
	}
	@Override
	public String toString() {
		return gene.toString() + " " + corr;
	}
	
	
	
}
