/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbGeni"
    private ComboBox<Genes> cmbGeni; // Value injected by FXMLLoader

    @FXML // fx:id="btnGeniAdiacenti"
    private Button btnGeniAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtIng"
    private TextField txtIng; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.model.creaGrafo();
    	txtResult.setText("Grafo creato!\nNumero vertici: " + model.getNumeroVertici());
		txtResult.appendText("\nNumero archi: " + model.getNumeroArchi() + "\n");
		cmbGeni.getItems().addAll(this.model.getListaGeni());

    }

    @FXML
    void doGeniAdiacenti(ActionEvent event) {
    	try {
    		Genes g1 = cmbGeni.getValue();
    		List<Adiacenza> adiacenti = new ArrayList<>(this.model.getGeniAdiacenti(g1));
    		txtResult.appendText("Geni adiacenti a " + g1.toString() + ":\n");
    		for(Adiacenza a : adiacenti)
    			txtResult.appendText(a.toString() + "\n");
    	}catch (NullPointerException e) {
    		txtResult.appendText("Creare il grafo e selezionare un gene prima di procedere.\n");
    		return;
      	}

    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	try {
    		Genes primo = cmbGeni.getValue();
    		int N = Integer.parseInt(txtIng.getText());
    		this.model.simula(N, primo);
    		Map<Genes, Integer> output = new HashMap<>(this.model.getOutput());
    		txtResult.appendText("Simulazione completata, stato finale:\n");
    		for(Genes g : output.keySet()) {
    			txtResult.appendText(g.toString() + " " + output.get(g) + " ingegneri\n");
    		}
    		
    	} catch (NullPointerException e) {
    		txtResult.appendText("Creare il grafo e selezionare un gene prima di procedere.\n");
    		return;
      	} catch (NumberFormatException e) {
      		txtResult.appendText("Inserire numero intero prima di procedere.\n");
    		return;
      	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGeni != null : "fx:id=\"cmbGeni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGeniAdiacenti != null : "fx:id=\"btnGeniAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtIng != null : "fx:id=\"txtIng\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
    
}
