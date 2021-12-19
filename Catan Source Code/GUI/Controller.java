package GUI;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Board.Board;
import Board.DevelopedLocation;
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
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Game.Dice;
import Game.GameRunner;
import Trading.BluePlayer;
import Trading.OrangePlayer;
import Trading.Player;
import Trading.RedPlayer;
import Trading.WhitePlayer;
import Trading.ResourceHolder.ResourceType;

public class Controller {

	Button button;
	private Stage primaryStage;
	@FXML AnchorPane rootLayout;
    Button[] btn = new Button[100];
    @FXML
    private Label label;
    private static ResourceType offeredResource;
    private static ResourceType requestedResource;
    
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
    		loadRollDie(event);
//    		loadMap(event);
    		
    }
    
	//===========================================================
	// Player Colour Selection
	//===========================================================    
    public void chooseColour(ActionEvent event) throws IOException {
    	String colour = ((Button) event.getSource()).getText();
    	System.out.println("Picked "+colour);
    	
    	if (colour.contentEquals("Blue")) { 
    		BluePlayer.getInstance();
    		BluePlayer.getInstance().developStartingPositions();
    		Game.GameRunner.players.add(BluePlayer.getInstance());
    	}
    	if (colour.contentEquals("White")) { 
    		WhitePlayer.getInstance(); 
    		WhitePlayer.getInstance().developStartingPositions();
    		Game.GameRunner.players.add(WhitePlayer.getInstance());}
    	if (colour.contentEquals("Red")) { 
    		RedPlayer.getInstance(); 
    		RedPlayer.getInstance().developStartingPositions();
    		Game.GameRunner.players.add(RedPlayer.getInstance());}
    	if (colour.contentEquals("Orange")) { 
    		OrangePlayer.getInstance(); 
    		OrangePlayer.getInstance().developStartingPositions();
    		Game.GameRunner.players.add(OrangePlayer.getInstance());}
    	
    	this.handleChooseColour(event);	
	}
	//===========================================================
	// Roll Die Scene
	//=========================================================== 
    public void loadRollDie(ActionEvent event) throws IOException{
    	try {
//	    	loadMap(event);
	    	FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("RollDie.fxml"));
	        Pane rollDie = (Pane) loader.load();
//	        Scene rollDieScene = new Scene(rollDie);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        Scene rollDieScene = new Scene(rollDie);
	        stage.setScene(rollDieScene);
	        stage.show();
	        
    	} catch (IOException e) {
            e.printStackTrace();
    	}
    }
    public void rollDie(ActionEvent event) throws IOException {
    	int die = Dice.getInstance().roll();
    	System.out.println("Player rolled a "+die);
    	
    	
    	loadChooseActionMenu(event);
    }
    public void loadChooseActionMenu(ActionEvent event) throws IOException {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("ChooseAction.fxml"));
        Pane chooseAction = (Pane) loader.load();
        Scene chooseActionScene = new Scene(chooseAction);
        stage.setScene(chooseActionScene);
        stage.show();
    }
	//===========================================================
	// Choose Action
	//===========================================================   
    public void chooseAction(ActionEvent event) throws IOException{
    	String Action = ((Button) event.getSource()).getText();
    	System.out.println(Action);
    	System.out.println(GameRunner.getCurrentPlayer().getColour());
    	if(Action.contentEquals("Build")) {
    		loadMap(event);
    	}
    	else if(Action.contentEquals("Buy Coco Tile")) {
    		
    	}
    	else if(Action.contentEquals("Trade")) {
    		loadOfferedResource(event);
    	}
    	else if(Action.contentEquals("End Turn")) {
    		GameRunner.nextPlayer();
    		System.out.println(GameRunner.getCurrentPlayer().getColour()+" players turn.");
    		loadRollDie(event);
    	}
    }
	//===========================================================
	// Trade GUI Options
	//=========================================================== 
    public void loadTradeMenu(ActionEvent event) throws IOException {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("ChooseTradePartner.fxml"));
        Pane chooseAction = (Pane) loader.load();
        Scene chooseActionScene = new Scene(chooseAction);
        stage.setScene(chooseActionScene);
        stage.show();
    }
    public void chooseTradePartner(ActionEvent event) throws IOException {
    	String tradingPartner = ((Button) event.getSource()).getText();
    	System.out.println("Player offers: "+offeredResource);

    	System.out.println(this.offeredResource+""+this.requestedResource);
    	if(tradingPartner.contentEquals("Stockpile")) {
    		System.out.println(GameRunner.getCurrentPlayer().getColour());
    		GameRunner.getCurrentPlayer().trade(Trading.Stockpile.getInstance(),offeredResource, requestedResource);
    	}
    	else
    		GameRunner.getCurrentPlayer().trade(Trading.Market.getInstance(),offeredResource, requestedResource);
    	loadChooseActionMenu(event);
    	
    }
    public void loadOfferedResource(ActionEvent event) throws IOException {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("SelectOfferedResource.fxml"));
        Pane chooseResource = (Pane) loader.load();
        Scene chooseResourceScene = new Scene(chooseResource);
        stage.setScene(chooseResourceScene);
        stage.show();
    }
    public void loadRequestedResource(ActionEvent event) throws IOException {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("SelectRequestedResource.fxml"));
        Pane chooseResource = (Pane) loader.load();
        Scene chooseResourceScene = new Scene(chooseResource);
        stage.setScene(chooseResourceScene);
        stage.show();
    }
    public void chooseOfferedResource(ActionEvent event) throws IOException {
    	String resource = ((Button) event.getSource()).getText();
    	if(resource.contentEquals("Gold")) {
    		offeredResource = ResourceType.gold;
    	}
    	else if(resource.contentEquals("Molasses")) {
    		offeredResource = ResourceType.molasses;
    	}
    	else if(resource.contentEquals("Cutlasses")) {
    		offeredResource = ResourceType.cutlass;
    	}
    	else if(resource.contentEquals("Goats")) {
    		offeredResource = ResourceType.goat;
    	}
    	else if(resource.contentEquals("Wood")) {
    		offeredResource = ResourceType.wood;
    	}
    	System.out.println("Player offers: "+offeredResource);
    	loadRequestedResource(event);
    }
    public void chooseRequestedResource(ActionEvent event) throws IOException {
    	String resource = ((Button) event.getSource()).getText();
    	if(resource.contentEquals("Gold")) {
    		requestedResource = ResourceType.gold;
    	}
    	else if(resource.contentEquals("Molasses")) {
    		requestedResource = ResourceType.molasses;
    	}
    	else if(resource.contentEquals("Cutlasses")) {
    		requestedResource = ResourceType.cutlass;
    	}
    	else if(resource.contentEquals("Goats")) {
    		requestedResource = ResourceType.goat;
    	}
    	else if(resource.contentEquals("Wood")) {
    		requestedResource = ResourceType.wood;
    	}
    	System.out.println("Player requests: "+requestedResource);

    	loadTradeMenu(event);
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
            loadMapColours(mapScene);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(mapScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    public void loadMapColours(Scene mapScene) {
		for(DevelopedLocation D : Board.developedLocations) {
    		String ID = "#"+String.valueOf(D.getX())+","+String.valueOf(D.getY())+"";
    		Player p = D.getPlayer();
        	Button bt = (Button) mapScene.lookup(ID);
    		if(p instanceof Trading.BluePlayer)
    			bt.getStyleClass().add("blue");
    		if(p instanceof Trading.RedPlayer)
    			bt.getStyleClass().add("red");
   			if(p instanceof Trading.WhitePlayer)
    			bt.getStyleClass().add("white");
 			if(p instanceof Trading.OrangePlayer)
    			bt.getStyleClass().add("orange");
    	}
    }
    
    //===========================================================
  	// Select Location On Map.
  	//===========================================================
    public void chooseLocation(ActionEvent event) throws IOException{
        if(((Button) event.getSource()).getId().contains("endturn"))
        	loadChooseActionMenu(event);
        else {
	       	String[] LocationSelected = ((Button) event.getSource()).getId().split(",",2);
	    	int x = Integer.parseInt(LocationSelected[0]);
	    	int y = Integer.parseInt(LocationSelected[1]);
	    	Board.getInstance().buyLairOrShip(x, y, GameRunner.getCurrentPlayer());
	    	loadMap(event);
        }
    	
//    	loadMap(event);
    }
    public void changeButtonColour(Button bt) {
    	if(GameRunner.getCurrentPlayer() instanceof Trading.BluePlayer)
			bt.getStyleClass().add("blue");
		if(GameRunner.getCurrentPlayer() instanceof Trading.RedPlayer)
			bt.getStyleClass().add("red");
		if(GameRunner.getCurrentPlayer() instanceof Trading.WhitePlayer)
			bt.getStyleClass().add("white");
		if(GameRunner.getCurrentPlayer() instanceof Trading.OrangePlayer)
			bt.getStyleClass().add("orange");
    }

}