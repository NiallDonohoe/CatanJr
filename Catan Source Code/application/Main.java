package application;
	
import java.io.IOException;
import Board.Board;
import Board.Location;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;


public class Main extends Application {
	
	Button button;
	private Stage primaryStage;
	@FXML 
	public AnchorPane rootLayout;
    Button[] btn = new Button[100];

    
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.declareIslands();
//		board.printEach();
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("CatanJr");
		initRootLayout();	
//		loadMap();
		
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("Start.fxml"));
        AnchorPane start = (AnchorPane) loader.load();
        rootLayout.getChildren().setAll(start);
	}
	public void changeScreenButtonPushed(ActionEvent event) throws IOException {
    	Parent ChooseColour = FXMLLoader.load(getClass().getResource("Cat.fxml"));
    	Scene StartScene =  new Scene(ChooseColour);
    	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	primaryStage.setScene(StartScene);

	}
	public void initRootLayout() {
		try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("RootLayout.fxml"));
            rootLayout = (AnchorPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public void loadMap() {
		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Cat.fxml"));
            Pane map = (Pane) loader.load();
//            for(int i=0;i<Board.availableLocations.size();i++) {
//            	btn[i] = loadLocations(Board.availableLocations.get(i));
//            	map.getChildren().add(btn[i]);
//            }
            rootLayout.getChildren().addAll(map);
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
    		
    	}
		return button;
	}
}
