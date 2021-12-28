package GUI;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
import Game.Game;
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
    private static String printInfo;
    ByteArrayOutputStream baos;
    PrintStream old;
    
	//===========================================================
	// Setters
	//===========================================================
    /**
     * setPrimary sets the stage for the controller class.
     * @param stage the stage that is being set as the primary stage.
     */
    protected static void setPrimaryStage(Stage stage) {
    	primaryStage = stage;
    }
	//===========================================================
	// GameWinner Scene and Event Handler
	//===========================================================
    /**
     * gameWinnerScene loads the game winner scene when a player wins.
     * @throws IOException
     */
    private void gameWinnerScene() throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("CatanMainScene.fxml"));
        Pane catanMain = (Pane) loader.load();
        
    	FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(Main.class.getResource("GameWinner.fxml"));
        Pane scene2 = (Pane) loader1.load();
        
        Label L = (Label) scene2.lookup("#gameWinnerLabel");
        L.setText(Game.getGameWinner()+L.getText());
        scene2.getChildren().add(L);
        
        catanMain.getChildren().add(scene2);
        Scene catanMainScene = new Scene(catanMain);
        addMainSceneDetails(catanMainScene);
        primaryStage.setScene(catanMainScene);
        primaryStage.show();
    }
    /**
     * endGame closes stage when a player presses endgame button on GameWinner scene.
     * @param event tied to the pressing of End Game button.
     * @throws IOException
     */
    @FXML
    private void endGame(ActionEvent event) throws IOException{
    	primaryStage.close();
    }  
	//===========================================================
	// Player Number Selection
	//===========================================================
    /**
     * playerSelection is the event handler for player selection scene.
     * @param event tied to 2 buttons with the number of players that are to play the game.
     * @throws IOException
     */
    @FXML
    private void playerSelection(ActionEvent event) throws IOException {
    	int numPlayers = (Integer.parseInt(((Button) event.getSource()).getText()));
    	System.out.println("Picked "+numPlayers+ " Players");
    	Game.setNumPlayers(numPlayers);
		this.handleChooseColour();
    }
    
	//===========================================================
	// Player Colour Selection
	//===========================================================  
    /**
     * chooseColour is the event handler for the choose colour scene.
     * @param event tied to 4 different buttons with the different player colour options.
     * @throws IOException
     */
    @FXML
    private void chooseColour(ActionEvent event) throws IOException {
    	String colour = ((Button) event.getSource()).getText();
    	System.out.println("Picked "+colour);
    	
    	if (colour.contentEquals("Blue")) { 
    		BluePlayer.getInstance();
    		BluePlayer.getInstance().developStartingPositions();
    		Game.addPlayer(BluePlayer.getInstance());
    	}
    	if (colour.contentEquals("White")) { 
    		WhitePlayer.getInstance(); 
    		WhitePlayer.getInstance().developStartingPositions();
    		Game.addPlayer(WhitePlayer.getInstance());}
    	if (colour.contentEquals("Red")) { 
    		RedPlayer.getInstance(); 
    		RedPlayer.getInstance().developStartingPositions();
    		Game.addPlayer(RedPlayer.getInstance());}
    	if (colour.contentEquals("Orange")) { 
    		OrangePlayer.getInstance(); 
    		OrangePlayer.getInstance().developStartingPositions();
    		Game.addPlayer(OrangePlayer.getInstance());}
    	
    	this.handleChooseColour();	
	}
    /**
     * handleChooseColour loads the Choose Colour scene until all players have a colour and then 
     * loads roll die scene.
     * @throws IOException
     */
    @FXML
    private void handleChooseColour() throws IOException {
    	if(Game.players.size()< Game.getNumPlayers()) {
    		Parent ChooseColour = FXMLLoader.load(getClass().getResource("ChooseColour.fxml"));
    		Scene ChooseColourScene = new Scene(ChooseColour);
    		Label L = (Label) ChooseColourScene.lookup("#playerChooseColour");
    		L.setText("Player"+(Game.players.size()+1)+" choose your colour:");
    		primaryStage.setScene(ChooseColourScene);
    		primaryStage.show();
    	}
    	else
    		loadRollDie();    		
    }
	//===========================================================
	// Add Scene to Main Scene
	//===========================================================
    /**
     * addToMain scene is used to overlay other scenes over the CatanMainScene.
     * @param File2 the fxml file containing the scene to be overlayed.
     * @throws IOException
     */
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
    /**
     * addMainSceneDetails calls the methods which are used to update details in the main scene.
     * @param mainScene the scene which the details are being added to.
     */
    private void addMainSceneDetails(Scene mainScene) {
    	setGhostCaptainIndicator(mainScene);
        loadMapColours(mainScene);
        setPlayerVariableLabel(mainScene);
        setMarketVariableLabels(mainScene);
        setStockpileVariableLabels(mainScene);
        addInformationPrint(mainScene);
        printInfo = null;
    }
	//===========================================================
	// Roll Die Scene and Event Handler
	//===========================================================
    /**
     * loadRollDie adds the RollDie scene to the main scene.
     * @throws IOException
     */
    private void loadRollDie() throws IOException{
    	addToMainScene("RollDie.fxml");	        
    }
    /**
     * rollDie is the event handler for RollDie scene, calls roll method on dice after
     * die in scene is pressed and then loads the next scene based on the result
     * @param event tied to the die in the RollDie scene.
     * @throws IOException
     */
    @FXML
    private void rollDie(ActionEvent event) throws IOException {
    	
    	int dieResult = Dice.getInstance().roll();
    	this.beginCaptureOutputStream();
    	
    	if(dieResult<6) {
    		Board.getInstance().handleGeneratingIslandResources(dieResult);
    		this.endCaptureOutputStream();
    		loadChooseActionMenu();
    	}
    	if(dieResult == 6) {
    		this.endCaptureOutputStream();
    		loadGhostCaptainScene();
    	}
    }
	//===========================================================
	// Choose Action Scene and Event Handler
	//===========================================================
    /**
     * loadChooseActionMenu adds the ChooseAction scene to the main scene.
     * @throws IOException
     */
    private void loadChooseActionMenu() throws IOException {
        addToMainScene("ChooseAction.fxml");
    }
    /**
     * chooseAction is the event handler for the choose action scene which controls
     * which scene is loaded next based on the option picked.
     * @param event tied to the pressing of the different action options.
     * @throws IOException
     */
    @FXML
    private void chooseAction(ActionEvent event) throws IOException{
    	String Action = ((Button) event.getSource()).getText();
    	if(Action.contentEquals("Build")) {
    		loadMap();
    	}
    	else if(Action.contentEquals("Buy Coco Tile")) {
    		this.beginCaptureOutputStream();
    		CocoCard card = Game.getCurrentPlayer().buyCocoCard();
    		this.endCaptureOutputStream();
    		if(card instanceof CocoCards.GetShipOrLairCoco) {
//    			System.out.print("Choose Free Ship or Lair");
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
    		Game.nextPlayer();
    		loadRollDie();
    	}
    }
	//===========================================================
	// Ghost Captain Scene and Event Handler
	//===========================================================
    /**
     * loadGhostCaptainScene adds the GhostCaptain scene to the main scene.
     * @throws IOException
     */
    private void loadGhostCaptainScene() throws IOException {
    	System.out.println("Choose Island to place ghost captain.");
        addToMainScene("GhostCaptainIslands.fxml");  	
    }
    /**
     * chooseGhostCaptainIslands is the event handler for the GhostCaptainIslands scene.
     * Which calls moveGhostCaptain to the selected location for the player whose turn it is.
     * @param event the picking of one of the islands.
     * @throws IOException
     */
    @FXML
    private void chooseGhostCaptainIslands(ActionEvent event) throws IOException{
    	String[] LocationSelected = ((Button) event.getSource()).getId().split(",",2);
    	printInfo = "\nGhost captain placed on: "+LocationSelected[0]+","+LocationSelected[1];
    	System.out.println(printInfo);
    	Game.getCurrentPlayer().moveGhostCaptain(Integer.parseInt(LocationSelected[0]), Integer.parseInt(LocationSelected[1]));
    	loadChooseActionMenu();
    }
	//===========================================================
	// Trade Scenes and Event Handlers
	//===========================================================
    /**
     * loadOfferedResource adds SelectOfferedResource scene to main screen.
     * @throws IOException
     */
    private void loadOfferedResource() throws IOException {
        addToMainScene("SelectOfferedResource.fxml");

    }
    /**
     * loadRequestedResource adds SelectRequestedResource scene to main screen.
     * @throws IOException
     */
    private void loadRequestedResource() throws IOException {
        addToMainScene("SelectRequestedResource.fxml");
    }
    /**
     * loadTradeMenu adds the ChooseTradePartner scene to the main screen.
     * @throws IOException
     */
    private void loadTradeMenu() throws IOException {
        addToMainScene("ChooseTradePartner.fxml");
    }
    /**
     * chooseTradePartner is the event hander for ChooseTradePartner scene.
     * @param event button press selecting Market or Stockpile
     */
    @FXML
    private void chooseTradePartner(ActionEvent event) throws IOException {
    	String tradingPartner = ((Button) event.getSource()).getText();
    	this.beginCaptureOutputStream();
    	if(tradingPartner.contentEquals("Stockpile")) {
    		Game.getCurrentPlayer().trade(Trading.Stockpile.getInstance(),offeredResource, requestedResource);
    	}
    	else
    		Game.getCurrentPlayer().trade(Trading.Market.getInstance(),offeredResource, requestedResource);
    	this.endCaptureOutputStream();
    	loadChooseActionMenu();
    }
    /**
     * chooseOfferedResource is the event handler for the SelectOfferedResourceScene
     * @param event button press selecting which of the 5 resources the player would like to offer.
     * @throws IOException
     */
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
    /**
     * chooseRequestedResource is the event handler for the SelectRequestedResourceScreen
     * @param event button press selecting which of the 5 resources the player requests.
     * @throws IOException
     */
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
    /**
     * loadMap loads the MainSceneContaining the map on its own.
     */
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
    /**
     * loadMapColours changes the colours of the buttons on the map based on the status of their
     * corresponding locations.
     * @param mapScene the scene that these changes are being added to.
     */
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
    /**
     * chooseLocation is the event handler for locations selection for building on the CatanMainScene.
     * @param event button press of a ship or lair location  on map or end building button.
     * @throws IOException
     */
    @FXML
    protected void chooseLocation(ActionEvent event) throws IOException{
        if(((Button) event.getSource()).getId().contains("endturn"))
        	loadChooseActionMenu();
        else {
	       	String[] LocationSelected = ((Button) event.getSource()).getId().split(",",2);
	    	int x = Integer.parseInt(LocationSelected[0]);
	    	int y = Integer.parseInt(LocationSelected[1]);
	    	this.beginCaptureOutputStream();
	    	if(isCocoDevelopment) {
	    		if(Board.getInstance().developLocation(x, y, Game.getCurrentPlayer())) {
		    		Board.getInstance().developLocation(x, y, Game.getCurrentPlayer());
		    		isCocoDevelopment = false;
		    		this.endCaptureOutputStream();
		    		if(Game.checkForAWinner())
		    			gameWinnerScene();
		    		else
		    			loadChooseActionMenu();
	    		}
	    		else
	    			loadMap();
	    	}
	    	else {
	    		Board.getInstance().buyLairOrShip(x, y, Game.getCurrentPlayer());
	    		this.endCaptureOutputStream();
	    		if(Game.checkForAWinner())
	    			gameWinnerScene();
	    		else
	    			loadMap();
	    	}
        }    	
    }
    //===========================================================
  	// Load resource variables.
  	//===========================================================
    /**
     * setPlayerVariables updates the player variables in the CatanMainScene. And sets style to
     * player variable pane for the player whose turn it currently is.
     * @param mapScene the scene these details are being added to.
     */
    private void setPlayerVariableLabel(Scene mapScene) {
    	String[] playerNum = {"P1","P2","P3","P4"};
    	String[] variableLabel = {"Player","gold","molasses","cutlasses","goats",
    			"wood","UsedCoco","UnusedLairs","UnusedShips"};
    	for(int p = 0; p < Game.getNumPlayers(); p++) {
	    	for(int i = 0; i < variableLabel.length;i++) {
	    		Label L = (Label) mapScene.lookup("#"+variableLabel[i]+playerNum[p]);
	    		Pane P = (Pane) mapScene.lookup("#pane"+playerNum[p]);
	    		if(variableLabel[i]=="Player") {
	    			L.setText(L.getText() + "("+Game.getPlayer(p).getColour()+"):");
	    			if((p+1)==Game.getPlayerTurnNumber())
	    				P.getStyleClass().add("currentPlayerPane");
	    			else
	    				P.getStyleClass().add("resourceHolderPane");
	    		}
	    		if(variableLabel[i]=="gold")
	    			L.setText(L.getText() + Game.getPlayer(p).getNumGold());
	    		if(variableLabel[i]=="molasses")
	    			L.setText(L.getText() + Game.getPlayer(p).getNumMolasses());
	    		if(variableLabel[i]=="cutlasses")
	    			L.setText(L.getText() + Game.getPlayer(p).getNumCutlasses());
	    		if(variableLabel[i]=="goats")
	    			L.setText(L.getText() + Game.getPlayer(p).getNumGoats());
	    		if(variableLabel[i]=="wood")
	    			L.setText(L.getText() + Game.getPlayer(p).getNumWood());
	    		if(variableLabel[i]=="UsedCoco")
	    			L.setText(L.getText() + Game.getPlayer(p).getNumUsedCoco());
	    		if(variableLabel[i]=="UnusedLairs")
	    			L.setText(L.getText() + Game.getPlayer(p).getUnbuiltLairs());
	    		if(variableLabel[i]=="UnusedShips")
	    			L.setText(L.getText() + Game.getPlayer(p).getUnbuiltShips());
	    	}
    	}
    	if(Game.getNumPlayers()==3) {
	    	for(int i = 0; i < variableLabel.length;i++) {
	    		Label L1 = (Label) mapScene.lookup("#"+variableLabel[i]+"P4");
	    		L1.setText("");
	    	}
    	}
    }
    /**
     * setMarketVariableLabels updates the market variables in the CatanMainScene.
     * @param mapScene the scene these details are being added to.
     */
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
    /**
     * setStockpileVariableLabels updates the stockpile variables in the CatanMainScene.
     * @param mapScene the scene these details are being added to.
     */
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
    /**
     * setGhostCaptainIndicator is used to change the ghost captain indicator in the MainScene.
     * @param mapScene
     */
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
    /**
     * changeGhostCaptainIndicatorColour sets the indicator to the colour of the player 
     * to last move the ghost captain.
     * @param mapScene the scene the indicator is being modified on.
     * @param colour the colour of the player to last move the GhostCaptain.
     */
    private void changeGhostCaptainIndicatorColour(Scene mainScene, colour colour) {
    	Label L = (Label) mainScene.lookup("#ghostCaptain");
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
    /**
     * changeGhostCaptainPosition changes the position of the Ghost Captain indicator in
     * the main scene based on the island it is on.
     * @param mapScene
     * @param x
     * @param y
     */
    private void changeGhostCaptainPosition(Scene mainScene, int x, int y) {
    	Label L = (Label) mainScene.lookup("#ghostCaptain");
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
    //===========================================================
  	// Print Console Output in GUI methods.
  	//===========================================================
    /**
     * beginCaptureOutputStream is used to store the output stream in string form
     * for printing on the GUI.
     */
    private void beginCaptureOutputStream(){
    	// Create a stream to hold the output
    	this.baos = new ByteArrayOutputStream();
    	PrintStream ps = new PrintStream(baos);
    	this.old = System.out;
    	// Tell Java to use your special stream
    	System.setOut(ps);
    }
    /**
     * endCaptureOutputStream is used to end the capturing of the output stream
     * and set printInfo to the information captured.
     */
    private void endCaptureOutputStream() {
    	System.out.flush();
    	System.setOut(old);
    	// Show what happened
    	System.out.println(this.baos.toString());
    	printInfo = this.baos.toString();
    }
    // Add information print to main scene.
    /**
     * addInformationPrint used to add the captured output stream to scene.
     * @param mainScene
     */
    private void addInformationPrint(Scene mainScene) {
    	Label L = (Label) mainScene.lookup("#printInfo");
    	L.setText(printInfo);
    }
}