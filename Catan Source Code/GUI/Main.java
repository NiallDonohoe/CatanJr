package GUI;
	
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
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("CatanJr");
		initRootLayout();	
		
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("PickNumberPlayers.fxml"));
        AnchorPane start = (AnchorPane) loader.load();
        rootLayout.getChildren().setAll(start);
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
            loader.setLocation(Main.class.getResource("CatanMainScene.fxml"));
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
}
