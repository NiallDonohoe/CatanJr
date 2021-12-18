package GUI;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Board.Board;
import Board.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import Game.GameRunner;
import Trading.BluePlayer;
import Trading.OrangePlayer;
import Trading.RedPlayer;
import Trading.WhitePlayer;

public class Controller {

	Button button;
	private Stage primaryStage;
	@FXML AnchorPane rootLayout;
    Button[] btn = new Button[100];
    @FXML
    private Label label;
    
    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
    }
    
	//===========================================================
	// Player Number Selection
	//===========================================================

    public void playerSelection(ActionEvent event) throws IOException {
    	int numPlayers = (Integer.parseInt(((Button) event.getSource()).getText()));
    	System.out.println("Picked "+numPlayers+ " Players");
    	Game.GameRunner.setNumPlayers(numPlayers);
		this.handleChooseColour(event);
    }
    
	//===========================================================
	// Handle Colour Selection
	//===========================================================
    public void handleChooseColour(ActionEvent event) throws IOException {
    	if(Game.GameRunner.players.size()< Game.GameRunner.getNumPlayers()) {
    		Parent ChooseColour = FXMLLoader.load(getClass().getResource("ChooseColour.fxml"));
    		Scene ChooseColourScene = new Scene(ChooseColour);
    		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		stage.setScene(ChooseColourScene);
    		stage.show();
    	}
    	else
    		loadMap(event);
    		
    }
    
	//===========================================================
	// Player Colour Selection
	//===========================================================    
    public void chooseColour(ActionEvent event) throws IOException {
    	String colour = ((Button) event.getSource()).getText();
    	System.out.println("Picked "+colour);
    	
    	if (colour.contentEquals("Blue")) { BluePlayer.getInstance(); Game.GameRunner.players.add(BluePlayer.getInstance());}
    	if (colour.contentEquals("White")) { WhitePlayer.getInstance(); Game.GameRunner.players.add(WhitePlayer.getInstance());}
    	if (colour.contentEquals("Red")) { RedPlayer.getInstance(); Game.GameRunner.players.add(RedPlayer.getInstance());}
    	if (colour.contentEquals("Orange")) { OrangePlayer.getInstance(); Game.GameRunner.players.add(OrangePlayer.getInstance());}
    	
    	this.handleChooseColour(event);	
	}
    
	//===========================================================
	// Main Scene with map
	//===========================================================     
    public void loadMap(ActionEvent event) {
		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("CatanMainScene.fxml"));
            Pane map = (Pane) loader.load();
            Scene mapScene = new Scene(map);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(mapScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    //===========================================================
  	// Select Location On Map.
  	//===========================================================
    public void chooseLocation(ActionEvent event) throws IOException{
    	String LocationSelected = ((Button) event.getSource()).getId();
    	System.out.println("Location Pressed: "+LocationSelected);
    }
}