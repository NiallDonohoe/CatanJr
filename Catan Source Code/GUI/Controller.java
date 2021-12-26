package GUI;


import java.io.IOException;
import Board.Board;
import Board.DevelopedLocation;
import Board.Island;
import CocoCards.CocoCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import Game.Dice;
import Game.GameRunner;
import Trading.BluePlayer;
import Trading.OrangePlayer;
import Trading.Player;
import Trading.Player.colour;
import Trading.RedPlayer;
import Trading.WhitePlayer;
import Trading.ResourceHolder.ResourceType;

public class Controller {

	private static Stage primaryStage;
    private static ResourceType offeredResource;
    private static ResourceType requestedResource;
    private static boolean isCocoDevelopment;
    
	//===========================================================
	// Setters
	//===========================================================
    protected static void setPrimaryStage(Stage stage) {
    	primaryStage = stage;
    }
	//===========================================================
	// GameWinner Scene and Event Handler
	//===========================================================
    private void gameWinnerScene() throws IOException {
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
    @FXML
    private void endGame(ActionEvent event) throws IOException{
    	primaryStage.close();
    }
        
	//===========================================================
	// Player Number Selection
	//===========================================================
    @FXML
    private void playerSelection(ActionEvent event) throws IOException {
    	int numPlayers = (Integer.parseInt(((Button) event.getSource()).getText()));
    	System.out.println("Picked "+numPlayers+ " Players");
    	Game.GameRunner.setNumPlayers(numPlayers);
		this.handleChooseColour(event);
    }
    
	//===========================================================
	// Handle Colour Selection
	//===========================================================
    @FXML
    private void handleChooseColour(ActionEvent event) throws IOException {
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
    @FXML
    private void chooseColour(ActionEvent event) throws IOException {
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
    private void addToMainScene(String File2) throws IOException{
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
    private void addMainSceneDetails(Scene mainScene) {
    	setGhostCaptainIndicator(mainScene);
        loadMapColours(mainScene);
        setPlayerVariableLabel(mainScene);
        setMarketVariableLabels(mainScene);
        setStockpileVariableLabels(mainScene);
    }
	//===========================================================
	// Roll Die Scene and Event Handler
	//=========================================================== 
    private void loadRollDie() throws IOException{
    	try {
	        addToMainScene("RollDie.fxml");	        
    	} catch (IOException e) {
            e.printStackTrace();
    	}
    }
    @FXML
    private void rollDie(ActionEvent event) throws IOException {
    	
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
    private void loadChooseActionMenu() throws IOException {
        addToMainScene("ChooseAction.fxml");
    }
    @FXML
    private void chooseAction(ActionEvent event) throws IOException{
    	String Action = ((Button) event.getSource()).getText();
    	System.out.println(Action);
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
    		else
    			loadChooseActionMenu();
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
    private void loadGhostCaptainScene() throws IOException {
    	System.out.println("Choose Island to place ghost captain.");
        addToMainScene("GhostCaptainIslands.fxml");  	
    }
    @FXML
    private void chooseGhostCaptainIslands(ActionEvent event) throws IOException{
    	String[] LocationSelected = ((Button) event.getSource()).getId().split(",",2);
    	System.out.println("\nGhost captain placed on: "+LocationSelected[0]+","+LocationSelected[1]);
    	GameRunner.getCurrentPlayer().moveGhostCaptain(Integer.parseInt(LocationSelected[0]), Integer.parseInt(LocationSelected[1]));
    	loadChooseActionMenu();
    }
     
	//===========================================================
	// Trade Scenes and Event Handlers
	//===========================================================
    private void loadOfferedResource() throws IOException {
        addToMainScene("SelectOfferedResource.fxml");

    }
    private void loadRequestedResource() throws IOException {
        addToMainScene("SelectRequestedResource.fxml");
    }
    private void loadTradeMenu() throws IOException {
        addToMainScene("ChooseTradePartner.fxml");
    }
    @FXML
    private void chooseTradePartner(ActionEvent event) throws IOException {
    	String tradingPartner = ((Button) event.getSource()).getText();
    	System.out.println("Player offers: "+offeredResource);

    	if(tradingPartner.contentEquals("Stockpile")) {
    		GameRunner.getCurrentPlayer().trade(Trading.Stockpile.getInstance(),offeredResource, requestedResource);
    	}
    	else
    		GameRunner.getCurrentPlayer().trade(Trading.Market.getInstance(),offeredResource, requestedResource);
    	loadChooseActionMenu();
    }
    @FXML
    private void chooseOfferedResource(ActionEvent event) throws IOException {
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
    @FXML
    private void chooseRequestedResource(ActionEvent event) throws IOException {
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
	// Load Map Scene
	//===========================================================     
    private void loadMap() {
		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("CatanMainScene.fxml"));
            Pane map = (Pane) loader.load();
            if(!isCocoDevelopment) {
	            Label label = new Label("Lair costs 1 molasses, 1 cutlass, 1 goat, 1 wood");
	            Label label1 = new Label("Ship costs 1 goat, 1 wood");
	            label.setFont(new Font("Papyrus",15));label1.setFont(new Font("Papyrus",15));
	            label.setLayoutX(550);label.setLayoutY(50);
	            label1.setLayoutX(550);label1.setLayoutY(70);
	            map.getChildren().add(label);map.getChildren().add(label1);
            }
            else{
            	Label label = new Label("Build a ship or lair for free!");
            	label.setFont(new Font("Papyrus",15));
            	label.setLayoutX(550);label.setLayoutY(50);
            	map.getChildren().add(label);
            }
            Scene mapScene = new Scene(map);
            addMainSceneDetails(mapScene);
            primaryStage.setScene(mapScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    private void loadMapColours(Scene mapScene) {
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
 			// To deal with case where spooky island becomes undeveloped.
 			if(Board.getInstance().getSpookyIsland().getX()==D.getX() &&
 			   Board.getInstance().getSpookyIsland().getY()==D.getY()) {
 				
 	 			if(Board.getInstance().getSpookyIsland().getCocoCardLair()==null) {
 	 				bt.getStyleClass().add("default");
 	 				
 	 			}
 			}
    	}
    }
    
    //===========================================================
  	// Select Location On Map 
  	//===========================================================
    @FXML
    protected void chooseLocation(ActionEvent event) throws IOException{
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
    //===========================================================
  	// Load resource variables.
  	//===========================================================
    private void setPlayerVariableLabel(Scene mapScene) {
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
    private void setMarketVariableLabels(Scene mapScene) {
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
    private void setStockpileVariableLabels(Scene mapScene) {
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
    //===========================================================
  	// Set ghost captain indicator for islands.
  	//===========================================================
    protected void setGhostCaptainIndicator(Scene mapScene) {
    	for(Island island : Board.getInstance().getIslands()) {
    		if(island.getHasGhostCaptain() && island.getGhostCaptainLastMovedColour()!=null) {
    			colour colour = island.getGhostCaptainLastMovedColour();
    			int x = island.getX();
    			int y = island.getY();
    			changeGhostCaptainIndicatorColour(mapScene,colour);
    			changeGhostCaptainPosition(mapScene,x,y);
    		}
    	}
    }
    private void changeGhostCaptainIndicatorColour(Scene mapScene, colour colour) {
    	Label L = (Label) mapScene.lookup("#ghostCaptain");
    	switch (colour) {
	    	case Red:
	    		L.getStyleClass().add("red");
	    		break;
	    	case Orange:
	    		L.getStyleClass().add("orange");
	    		break;
	    	case Blue:
	    		L.getStyleClass().add("blue");
	    		break;
	    	case White:
	    		L.getStyleClass().add("white");
	    		break;
	    	default:
	    		L.getStyleClass().add("default");
    	}
    }
    private void changeGhostCaptainPosition(Scene mapScene, int x, int y) {
    	Label L = (Label) mapScene.lookup("#ghostCaptain");
    	if(y==2)
    		L.setLayoutY(218);
    	else if(y==4)
    		L.setLayoutY(133);
    	else if(y==6)
    		L.setLayoutY(50);
    	if(x==3)
    		L.setLayoutX(28);
    	else if(x==5)
    		L.setLayoutX(73);
    	else if(x==7)
    		L.setLayoutX(119);
    	else if(x==9)
    		L.setLayoutX(168);
    	else if(x==11)
    		L.setLayoutX(213);
    	else if(x==13)
    		L.setLayoutX(259);
    	else if(x==15)
    		L.setLayoutX(305);
    	else if(x==17)
    		L.setLayoutX(351);
    	else if(x==19)
    		L.setLayoutX(398);
    }
}