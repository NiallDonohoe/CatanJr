package GUI;


import java.io.IOException;

import Board.Board;
import Board.DevelopedLocation;
import CocoCards.CocoCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	private static Stage primaryStage;
	@FXML 
	AnchorPane rootLayout;
    Button[] btn = new Button[100];
    @FXML
    private Label label;
    private static ResourceType offeredResource;
    private static ResourceType requestedResource;
    private static boolean isCocoDevelopment;
    
	//===========================================================
	// Setters
	//===========================================================
    public static void setPrimaryStage(Stage stage) {
    	primaryStage = stage;
    }
	//===========================================================
	// GameWinner Scene and Event Handler
	//===========================================================
    public void gameWinnerScene() throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("CatanMainScene.fxml"));
        Pane catanMain = (Pane) loader.load();
        
    	FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(Main.class.getResource("GameWinner.fxml"));
        Pane scene2 = (Pane) loader1.load();
        
        Label L = (Label) scene2.lookup("#gameWinnerLabel");
        L.setText(GameRunner.getGameWinner()+L.getText());
        scene2.getChildren().add(L);
        
        
        catanMain.getChildren().add(scene2);
        Scene catanMainScene = new Scene(catanMain);
        addMainSceneDetails(catanMainScene);
        primaryStage.setScene(catanMainScene);
        primaryStage.show();
    }
    public void endGame(ActionEvent event) throws IOException{
    	primaryStage.close();
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
    		primaryStage.setScene(ChooseColourScene);
    		primaryStage.show();
    	}
    	else
    		loadRollDie();    		
    }
    
	//===========================================================
	// Player Colour Selection
	//===========================================================    
    public void chooseColour(ActionEvent event) throws IOException {
    	String colour = ((Button) event.getSource()).getText();
    	System.out.println("Picked "+colour);
    	
    	if (colour.contentEquals("Blue")) { 
    		BluePlayer.getInstance();
    		System.out.println("Declared player");
    		BluePlayer.getInstance().developStartingPositions();
    		System.out.println("Developed player starting positions.");
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
    public void addToMainScene(String File2) throws IOException{
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("CatanMainScene.fxml"));
        Pane catanMain = (Pane) loader.load();
        
    	FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(Main.class.getResource(File2));
        Pane scene2 = (Pane) loader1.load();
        
        catanMain.getChildren().add(scene2);
        Scene catanMainScene = new Scene(catanMain);
        addMainSceneDetails(catanMainScene);
        primaryStage.setScene(catanMainScene);
        primaryStage.show();
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
    public void loadRollDie() throws IOException{
    	try {
	        addToMainScene("RollDie.fxml");	        
    	} catch (IOException e) {
            e.printStackTrace();
    	}
    }
    public void rollDie(ActionEvent event) throws IOException {
    	
    	int dieResult = Dice.getInstance().roll();
    	System.out.println("Player rolled a "+dieResult);
    	if(dieResult<6) {
    		Board.getInstance().handleGeneratingIslandResources(dieResult);
    		loadChooseActionMenu();
    	}
    	if(dieResult == 6) {
    		loadGhostCaptainScene();
    	}
    }
	//===========================================================
	// Choose Action Scene and Event Handler
	//===========================================================    
    public void loadChooseActionMenu() throws IOException {
        addToMainScene("ChooseAction.fxml");
    }
  
    public void chooseAction(ActionEvent event) throws IOException{
    	String Action = ((Button) event.getSource()).getText();
    	System.out.println(Action);
    	System.out.println(GameRunner.getCurrentPlayer().getColour());
    	if(Action.contentEquals("Build")) {
    		loadMap();
    	}
    	else if(Action.contentEquals("Buy Coco Tile")) {
    		CocoCard card = GameRunner.getCurrentPlayer().buyCocoCard();
    		if(card instanceof CocoCards.GetShipOrLairCoco) {
    			System.out.print("Choose Free Ship or Lair");
    			isCocoDevelopment = true;
    			loadMap();
    		}
    		else if(card instanceof CocoCards.MoveGhostCaptainCoco) {
    			loadGhostCaptainScene();
    		}
    	}
    	else if(Action.contentEquals("Trade")) {
    		loadOfferedResource();
    	}
    	else if(Action.contentEquals("End Turn")) {
    		GameRunner.nextPlayer();
    		System.out.println(GameRunner.getCurrentPlayer().getColour()+" players turn.");
    		loadRollDie();
    	}
    }
	//===========================================================
	// Ghost Captain Scene and Event Handler
	//===========================================================
    public void loadGhostCaptainScene() throws IOException {
    	System.out.println("Choose Island to place ghost captain.");
        addToMainScene("GhostCaptainIslands.fxml");  	
    }
    public void chooseGhostCaptainIslands(ActionEvent event) throws IOException{
    	String[] LocationSelected = ((Button) event.getSource()).getId().split(",",2);
    	System.out.println("\nGhost captain placed on: "+LocationSelected[0]+","+LocationSelected[1]);
    	GameRunner.getCurrentPlayer().moveGhostCaptain(Integer.parseInt(LocationSelected[0]), Integer.parseInt(LocationSelected[1]));
    	loadChooseActionMenu();
    }
     
	//===========================================================
	// Trade Scenes and Event Handlers
	//===========================================================
    public void loadOfferedResource() throws IOException {
        addToMainScene("SelectOfferedResource.fxml");

    }
    public void loadRequestedResource() throws IOException {
        addToMainScene("SelectRequestedResource.fxml");
    }
    public void loadTradeMenu() throws IOException {
        addToMainScene("ChooseTradePartner.fxml");
    }
    public void chooseTradePartner(ActionEvent event) throws IOException {
    	String tradingPartner = ((Button) event.getSource()).getText();
    	System.out.println("Player offers: "+offeredResource);

    	if(tradingPartner.contentEquals("Stockpile")) {
    		System.out.println(GameRunner.getCurrentPlayer().getColour());
    		GameRunner.getCurrentPlayer().trade(Trading.Stockpile.getInstance(),offeredResource, requestedResource);
    	}
    	else
    		GameRunner.getCurrentPlayer().trade(Trading.Market.getInstance(),offeredResource, requestedResource);
    	loadChooseActionMenu();
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
    	loadRequestedResource();
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

    	loadTradeMenu();
    }
    
	//===========================================================
	// Main Scene with map
	//===========================================================     
    public void loadMap() {
		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("CatanMainScene.fxml"));
            Pane map = (Pane) loader.load();
            Scene mapScene = new Scene(map);
            addMainSceneDetails(mapScene);
            primaryStage.setScene(mapScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    public void loadMapColours(Scene mapScene) {
		for(DevelopedLocation D : Board.getInstance().getDevelopedLocations()) {
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
        	loadChooseActionMenu();
        else {
	       	String[] LocationSelected = ((Button) event.getSource()).getId().split(",",2);
	    	int x = Integer.parseInt(LocationSelected[0]);
	    	int y = Integer.parseInt(LocationSelected[1]);
	    	if(isCocoDevelopment) {
	    		Board.getInstance().developLocation(x, y, GameRunner.getCurrentPlayer());
	    		isCocoDevelopment = false;
	    		if(Game.GameRunner.checkForAWinner())
	    			gameWinnerScene();
	    		else
	    			loadChooseActionMenu();
	    	}
	    	else {
	    		Board.getInstance().buyLairOrShip(x, y, GameRunner.getCurrentPlayer());
	    		if(Game.GameRunner.checkForAWinner())
	    			gameWinnerScene();
	    		else
	    			loadMap();
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
	    			L.setText(L.getText() + GameRunner.getPlayer(p).getNumUsedCoco());
	    		if(variableLabel[i]=="UnusedLairs")
	    			L.setText(L.getText() + GameRunner.getPlayer(p).getUnbuiltLairs());
	    		if(variableLabel[i]=="UnusedShips")
	    			L.setText(L.getText() + GameRunner.getPlayer(p).getUnbuiltShips());
	    	}
    	}
    	if(GameRunner.getNumPlayers()==3) {
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