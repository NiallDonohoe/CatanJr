package GUI;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Board.Board;
import Board.DevelopedLocation;
import Board.Location;
import CocoCards.CocoCard;
import CocoCards.CocoDeck;
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
import javafx.scene.paint.Color;
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
    private static boolean isCocoDevelopment;
    
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
    		Label L = (Label) ChooseColourScene.lookup("#playerChooseColour");
    		L.setText("Player"+(GameRunner.players.size()+1)+" choose your colour:");
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
    		Game.GameRunner.addPlayer(BluePlayer.getInstance());
    	}
    	if (colour.contentEquals("White")) { 
    		WhitePlayer.getInstance(); 
    		WhitePlayer.getInstance().developStartingPositions();
    		Game.GameRunner.addPlayer(WhitePlayer.getInstance());}
    	if (colour.contentEquals("Red")) { 
    		RedPlayer.getInstance(); 
    		RedPlayer.getInstance().developStartingPositions();
    		Game.GameRunner.addPlayer(RedPlayer.getInstance());}
    	if (colour.contentEquals("Orange")) { 
    		OrangePlayer.getInstance(); 
    		OrangePlayer.getInstance().developStartingPositions();
    		Game.GameRunner.addPlayer(OrangePlayer.getInstance());}
    	
    	this.handleChooseColour(event);	
	}
    
    
	//===========================================================
	// Add Scene to Main Scene
	//===========================================================     
    public Scene addToMainScene(String File2) throws IOException{
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("CatanMainScene.fxml"));
        Pane catanMain = (Pane) loader.load();
        
    	FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(Main.class.getResource(File2));
        Pane scene2 = (Pane) loader1.load();
        
        catanMain.getChildren().add(scene2);
        Scene catanMainScene = new Scene(catanMain);
        addMainSceneDetails(catanMainScene);
        return catanMainScene;
    }
    public void addMainSceneDetails(Scene mainScene) {
        loadMapColours(mainScene);
        setPlayerVariableLabel(mainScene);
        setMarketVariableLabels(mainScene);
        setStockpileVariableLabels(mainScene);
    }
	//===========================================================
	// Roll Die Scene and Event Handler
	//=========================================================== 
    public void loadRollDie(ActionEvent event) throws IOException{
    	try {
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        Scene updatedScene = addToMainScene("RollDie.fxml");
	        stage.setScene(updatedScene);
	        stage.show();
	        
    	} catch (IOException e) {
            e.printStackTrace();
    	}
    }
    public void rollDie(ActionEvent event) throws IOException {
    	
    	int dieResult = Dice.getInstance().roll();
    	System.out.println("Player rolled a "+dieResult);
    	if(dieResult<6) {
    		Board.getInstance().handleGeneratingIslandResources(dieResult,  GameRunner.getCurrentPlayer());
    		loadChooseActionMenu(event);
    	}
    	if(dieResult == 6) {
    		loadGhostCaptainScene(event);
    	}
    }
	//===========================================================
	// Choose Action Scene and Event Handler
	//===========================================================    
    public void loadChooseActionMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene updatedScene = addToMainScene("ChooseAction.fxml");
        stage.setScene(updatedScene);
        stage.show();
    }
  
    public void chooseAction(ActionEvent event) throws IOException{
    	String Action = ((Button) event.getSource()).getText();
    	System.out.println(Action);
    	System.out.println(GameRunner.getCurrentPlayer().getColour());
    	if(Action.contentEquals("Build")) {
    		loadMap(event);
    	}
    	else if(Action.contentEquals("Buy Coco Tile")) {
    		CocoCard card = GameRunner.getCurrentPlayer().buyCocoCard();
    		if(card instanceof CocoCards.GetShipOrLairCoco) {
    			System.out.print("Choose Free Ship or Lair");
    			isCocoDevelopment = true;
    			loadMap(event);
    		}
    		else if(card instanceof CocoCards.MoveGhostCaptainCoco) {
    			loadGhostCaptainScene(event);
    		}
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
	// Ghost Captain Scene and Event Handler
	//===========================================================
    public void loadGhostCaptainScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene updatedScene = addToMainScene("GhostCaptainIslands.fxml");
        stage.setScene(updatedScene);
        stage.show();    	
    }
    public void chooseGhostCaptainIslands(ActionEvent event) throws IOException{
    	String[] LocationSelected = ((Button) event.getSource()).getId().split(",",2);
    	System.out.println("\nGhost captain placed on: "+LocationSelected[0]+","+LocationSelected[1]);
    	GameRunner.getCurrentPlayer().moveGhostCaptain(Integer.parseInt(LocationSelected[0]), Integer.parseInt(LocationSelected[1]));
    	loadChooseActionMenu(event);
    }
    
    
    
	//===========================================================
	// Trade Scenes and Event Handlers
	//===========================================================
    public void loadOfferedResource(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene updatedScene = addToMainScene("SelectOfferedResource.fxml");
        stage.setScene(updatedScene);
        stage.show();
    }
    public void loadRequestedResource(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene updatedScene = addToMainScene("SelectRequestedResource.fxml");
        stage.setScene(updatedScene);
        stage.show();
    }
    public void loadTradeMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene updatedScene = addToMainScene("ChooseTradePartner.fxml");
        stage.setScene(updatedScene);
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

    public void chooseOfferedResource(ActionEvent event) throws IOException {
    	String resource = ((Button) event.getSource()).getText();
    	if(resource.contentEquals("Gold"))
    		offeredResource = ResourceType.gold;
    	else if(resource.contentEquals("Molasses"))
    		offeredResource = ResourceType.molasses;
    	else if(resource.contentEquals("Cutlasses"))
    		offeredResource = ResourceType.cutlass;
    	else if(resource.contentEquals("Goats"))
    		offeredResource = ResourceType.goat;
    	else if(resource.contentEquals("Wood"))
    		offeredResource = ResourceType.wood;
    	System.out.println("Player offers: "+offeredResource);
    	loadRequestedResource(event);
    }
    public void chooseRequestedResource(ActionEvent event) throws IOException {
    	String resource = ((Button) event.getSource()).getText();
    	if(resource.contentEquals("Gold"))
    		requestedResource = ResourceType.gold;
    	else if(resource.contentEquals("Molasses"))
    		requestedResource = ResourceType.molasses;
    	else if(resource.contentEquals("Cutlasses"))
    		requestedResource = ResourceType.cutlass;
    	else if(resource.contentEquals("Goats"))
    		requestedResource = ResourceType.goat;
    	else if(resource.contentEquals("Wood"))
    		requestedResource = ResourceType.wood;
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
            setPlayerVariableLabel(mapScene);
            setMarketVariableLabels(mapScene);
            setStockpileVariableLabels(mapScene);
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
  	// Select Location On Map 
  	//===========================================================
    public void chooseLocation(ActionEvent event) throws IOException{
        if(((Button) event.getSource()).getId().contains("endturn"))
        	loadChooseActionMenu(event);
        else {
	       	String[] LocationSelected = ((Button) event.getSource()).getId().split(",",2);
	    	int x = Integer.parseInt(LocationSelected[0]);
	    	int y = Integer.parseInt(LocationSelected[1]);
	    	if(isCocoDevelopment) {
	    		Board.getInstance().developLocation(x, y, GameRunner.getCurrentPlayer());
	    		isCocoDevelopment = false;
	    		Game.GameRunner.checkForAWinner();
	    		loadChooseActionMenu(event);
	    	}
	    	else {
	    		Board.getInstance().buyLairOrShip(x, y, GameRunner.getCurrentPlayer());
	    		Game.GameRunner.checkForAWinner();
	    		loadMap(event);
	    	}
        }    	
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
    
    //===========================================================
  	// Load resource variables.
  	//===========================================================
    public void setPlayerVariableLabel(Scene mapScene) {
    	String[] playerNum = {"P1","P2","P3","P4"};
    	String[] variableLabel = {"Player","gold","molasses","cutlasses","goats",
    			"wood","UsedCoco","UnusedLairs","UnusedShips"};
    	for(int p = 0; p < GameRunner.getNumPlayers(); p++) {
	    	for(int i = 0; i < variableLabel.length;i++) {
	    		Label L = (Label) mapScene.lookup("#"+variableLabel[i]+playerNum[p]);
	    		Pane P = (Pane) mapScene.lookup("#pane"+playerNum[p]);
	    		if(variableLabel[i]=="Player") {
	    			L.setText(L.getText() + "("+GameRunner.getPlayer(p).getColour()+"):");
	    			if((p+1)==GameRunner.getPlayerTurnNumber())
	    				P.getStyleClass().add("currentPlayerPane");
	    			else
	    				P.getStyleClass().add("resourceHolderPane");
	    		}
	    		if(variableLabel[i]=="gold")
	    			L.setText(L.getText() + GameRunner.getPlayer(p).getNumGold());
	    		if(variableLabel[i]=="molasses")
	    			L.setText(L.getText() + GameRunner.getPlayer(p).getNumMolasses());
	    		if(variableLabel[i]=="cutlasses")
	    			L.setText(L.getText() + GameRunner.getPlayer(p).getNumCutlasses());
	    		if(variableLabel[i]=="goats")
	    			L.setText(L.getText() + GameRunner.getPlayer(p).getNumGoats());
	    		if(variableLabel[i]=="wood")
	    			L.setText(L.getText() + GameRunner.getPlayer(p).getNumWood());
	    		if(variableLabel[i]=="UsedCoco")
	    			L.setText(L.getText() + "###");
	    		if(variableLabel[i]=="UnusedLairs")
	    			L.setText(L.getText() + GameRunner.getPlayer(p).getUnbuiltLairs());
	    		if(variableLabel[i]=="UnusedShips")
	    			L.setText(L.getText() + GameRunner.getPlayer(p).getUnbuiltShips());
	    	}
    	}
    	if(GameRunner.getNumPlayers()==3) {
    		Label L = (Label) mapScene.lookup("#PlayerP4");
	    	for(int i = 0; i < variableLabel.length;i++) {
	    		Label L1 = (Label) mapScene.lookup("#"+variableLabel[i]+"P4");
	    		L1.setText("");
	    	}
    	}
    }
    public void setMarketVariableLabels(Scene mapScene) {
    	String[] variableLabel = {"gold","molasses","cutlasses","goats","wood"};
    	for(int i = 0; i < variableLabel.length;i++) {
    		Label L = (Label) mapScene.lookup("#"+variableLabel[i]+"M");
    		if(variableLabel[i]=="gold")
    			L.setText(L.getText() + Trading.Market.getInstance().getNumGold());
    		if(variableLabel[i]=="molasses")
    			L.setText(L.getText() + Trading.Market.getInstance().getNumMolasses());
    		if(variableLabel[i]=="cutlasses")
    			L.setText(L.getText() + Trading.Market.getInstance().getNumCutlasses());
    		if(variableLabel[i]=="goats")
    			L.setText(L.getText() + Trading.Market.getInstance().getNumGoats());
    		if(variableLabel[i]=="wood")
    			L.setText(L.getText() + Trading.Market.getInstance().getNumWood());
    	}
    }
    public void setStockpileVariableLabels(Scene mapScene) {
    	String[] variableLabel = {"gold","molasses","cutlasses","goats","wood"};
    	for(int i = 0; i < variableLabel.length;i++) {
    		Label L = (Label) mapScene.lookup("#"+variableLabel[i]+"S");
    		if(variableLabel[i]=="gold")
    			L.setText(L.getText() + Trading.Stockpile.getInstance().getNumGold());
    		if(variableLabel[i]=="molasses")
    			L.setText(L.getText() + Trading.Stockpile.getInstance().getNumMolasses());
    		if(variableLabel[i]=="cutlasses")
    			L.setText(L.getText() + Trading.Stockpile.getInstance().getNumCutlasses());
    		if(variableLabel[i]=="goats")
    			L.setText(L.getText() + Trading.Stockpile.getInstance().getNumGoats());
    		if(variableLabel[i]=="wood")
    			L.setText(L.getText() + Trading.Stockpile.getInstance().getNumWood());
    	}
    }
}