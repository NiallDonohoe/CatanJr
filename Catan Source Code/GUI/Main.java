package GUI;
	
import java.io.IOException;
import Game.GameRunner;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;


public class Main extends Application {
	
	Button button;
	private Stage primaryStage;
	@FXML 
	public AnchorPane rootLayout;
    Button[] btn = new Button[100];

    
	public static void main(String[] args) {
		GameRunner.startGame();
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
            Controller.setPrimaryStage(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
