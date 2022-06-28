package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import it.polito.tdp.genes.model.Event.EventType;

public class Simulator {
	
	private Queue<Event> queue;
	
	private Model model;
	private int N; //numero ingegneri
	private List<Genes> stato; //indice = ing
	private static int maxMesi = 36; //simulazione di 3 anni
	
	private Map<Genes, Integer> output;
	
	public Simulator() {
		super();
	}
	
	public void init(Model model, Genes primo, int N) {
		this.queue = new PriorityQueue<>();
		this.model = model;
		this.stato = new ArrayList<>();
		this.N = N;
		
		for(int i=0; i<N; i++) {
			stato.add(primo); //ogni ingegnere studia primo
		}
		
		for(int i=1; i<=36; i++) {
			this.queue.add(new Event(i, EventType.NUOVO_MESE, 0, null)); //ultimi due valori irrilevanti
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(it.polito.tdp.genes.model.Event e) {
		int mese = e.getMese();
		EventType type = e.getType();
		
		switch (type) {
		case NUOVO_MESE:
			//ogni ingegnere sceglie se restare o andare via
			for(int i=0; i<this.N; i++) {
				if(Math.random() < 0.70) {
					//avviene spostamento
					this.queue.add(new Event(mese, EventType.SPOSTAMENTO, i, stato.get(i)));
				}
			}
			break;

		case SPOSTAMENTO:
			int ingegnere = e.getIngegnere();
			Genes attuale = e.getGeneAttuale();
			List<Adiacenza> vicini = new ArrayList<>(this.model.getGeniAdiacenti(attuale));
			double sommaPesi = this.model.sommaPesi(vicini); //totale
			
			double prob = Math.random();
			double sommaPrecedente = 0.0;
			
			for(Adiacenza a : vicini) {
				double probAttuale = a.getCorr() / sommaPesi; //probattuale
				if(sommaPrecedente < prob && prob < probAttuale) {
					//aggiorno lo stato
					stato.remove(ingegnere);
					stato.add(ingegnere, a.getGene());
				}
				else {
					sommaPrecedente+=probAttuale;
				}
			}
			
			break;
		}
		
	}

	public Map<Genes, Integer> getOutput() {
		this.output = new HashMap<>();
		for(Genes g : this.stato) {
			if(output.containsKey(g)) {
				output.put(g, (output.get(g)+1) );
			} else {
				output.put(g, 1);
			}
		}
		
		return output;
	}
	
	

}
