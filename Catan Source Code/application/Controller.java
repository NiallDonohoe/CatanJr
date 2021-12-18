package application;


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

public class Controller implements Initializable {

	Button button;
	private Stage primaryStage;
	@FXML AnchorPane rootLayout;
    Button[] btn = new Button[100];
    @FXML
    private Label label;
    // Player Colour
    public ComboBox playerColoursBox;
    ObservableList<String> playerColoursList = FXCollections
    		.observableArrayList("Red","Blue");
    
    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
        playerColoursBox.setItems(playerColoursList);
    }
    @FXML
    public void playerSelection3(ActionEvent event) throws IOException {
		System.out.println("Picked 3 Players");
		Parent ChooseColour = FXMLLoader.load(getClass().getResource("ChooseColour.fxml"));
		Scene ChooseColourScene = new Scene(ChooseColour);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(ChooseColourScene);
		stage.show();
	}
    public void playerSelection4(ActionEvent event) throws IOException {
    	System.out.println("Picked 4 Players");
		Parent ChooseColour = FXMLLoader.load(getClass().getResource("ChooseColour.fxml"));
		Scene ChooseColourScene = new Scene(ChooseColour);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(ChooseColourScene);
		stage.show();
    }
    
    public void ChooseColourRed(ActionEvent event) throws IOException {
    	System.out.println("Picked Red");
//		Parent map = FXMLLoader.load(getClass().getResource("Cat.fxml"));
//		Scene mapScene = new Scene(map);
//		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//		stage.setScene(mapScene);
//		stage.show();
    	loadMap(event);
	}
    public void loadMap(ActionEvent event) {
		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Cat.fxml"));
            Pane map = (Pane) loader.load();
//            for(int i=0;i<Board.availableLocations.size();i++) {
//            	btn[i] = loadLocations(Board.availableLocations.get(i));
//            	map.getChildren().add(btn[i]);
//            }
//            rootLayout.getChildren().addAll(map);
            Scene mapScene = new Scene(map);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(mapScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public Button loadLocations(Location l) {
	    button = new Button();
//        	Location l = Board.availableLocations.get(i);
    	int x = l.getX();
    	int y = l.getY();
    	
    	if(l.getLairOrShip()==Location.lairOrShip.lair) {
        	button.setStyle("-fx-background-radius: 10em; " +
        	         "-fx-min-width: 10px; " +
        	         "-fx-min-height: 10px; " +
        	         "-fx-max-width: 10px; " +
        	         "-fx-max-height: 10px;"
        	      );
        	button.setLayoutX(x*20);
        	button.setLayoutY(y*20);
    	}
    	else {
    		if(y%2==0) {
    			button.setStyle("-fx-background-radius: 10em; " +
            	         "-fx-min-width: 2px; " +
            	         "-fx-min-height: 10px; " +
            	         "-fx-max-width: 2px; " +
            	         "-fx-max-height: 10px;"
            	      );
    			button.setLayoutX(x*20+4);
            	button.setLayoutY(y*20);
    		}
    		else {
        		button.setStyle("-fx-background-radius: 10em; " +
            	         "-fx-min-width: 10px; " +
            	         "-fx-min-height: 2px; " +
            	         "-fx-max-width: 10px; " +
            	         "-fx-max-height: 2px;"
            	      );
        		button.setLayoutX(x*20);
            	button.setLayoutY(y*20+4);
    		}
    		button.getOnMouseClicked();
    	}
    	button.setId(String.valueOf(x)+","+String.valueOf(y));
    	System.out.println(button.getId());
		return button;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}