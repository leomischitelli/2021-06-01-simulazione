package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;



public class Model {
	
	private Graph<Genes, DefaultWeightedEdge> grafo;
	private GenesDao dao;
	private Map<String, Genes> idMap;
	private List<Genes> listaGeni;
	private Simulator sim;
	
	public Model() {
		this.dao = new GenesDao();
	}
	
	public void creaGrafo() {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.listaGeni = new ArrayList<>(this.dao.getAllEssentialGenes());
		this.idMap = new HashMap<>();
		for(Genes g : this.listaGeni)
			idMap.put(g.getGeneId(), g);
		Graphs.addAllVertices(this.grafo, this.listaGeni);
		List<Coppia> coppie = new ArrayList<>(this.dao.getCoppie(this.idMap));
		for(Coppia c : coppie)
			Graphs.addEdge(this.grafo, c.getG1(), c.getG2(), c.getCorr());
	}
	
	public List<Adiacenza> getGeniAdiacenti(Genes gene){
		List<Genes> vicini = new ArrayList<>(Graphs.neighborListOf(this.grafo, gene));
		List<Adiacenza> result = new ArrayList<>();
		for(Genes g : vicini) {
			DefaultWeightedEdge e = this.grafo.getEdge(gene, g);
			double peso = this.grafo.getEdgeWeight(e);
			result.add(new Adiacenza(g, peso));
		}
		
		return result;
	}
	
	public void simula(int N, Genes primo) {
		this.sim = new Simulator();
		this.sim.init(this, primo, N);
		this.sim.run();		
	}
	
	
	
	public List<Genes> getListaGeni() {
		return listaGeni;
	}

	public int getNumeroVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumeroArchi() {
		return this.grafo.edgeSet().size();
	}

	public double sommaPesi(List<Adiacenza> vicini) {
		double somma = 0.0;
		for(Adiacenza a : vicini)
			somma+=a.getCorr();
		return somma;
	}
	
	public Map<Genes, Integer> getOutput() {
		return this.sim.getOutput();
	}
	
	

	
}
