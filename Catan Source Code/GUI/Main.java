package GUI;
	
import java.io.IOException;
import Game.GameRunner;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

/**
 * Class main used to launch CatanJr GUI and set initial stage and layout.
 * @author Niall Donohoe & Shea O'Sullivan
 */
public class Main extends Application {
	private Stage primaryStage;
	@FXML 
	public AnchorPane rootLayout;

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
