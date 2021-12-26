package UnitTests;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import CocoCards.CocoCard;
import CocoCards.CocoDeck;
import CocoCards.GetGoatAndCutlassCoco;
import CocoCards.GetMolassesAndWoodCoco;
import CocoCards.GetShipOrLairCoco;
import CocoCards.MoveGhostCaptainCoco;

public class CocoCardDeckTests {
	
	@BeforeAll
	static void BeforeAll() {
		System.out.println("Running Coco Card Test Cases...");	
	}

	@BeforeEach
	public void setUp() throws Exception{
		CocoDeck.getInstance();
	}
	
	@After
	public void tearDown() throws Exception{
		CocoDeck.getInstance().destroyMe();
	}

	@Test
	public void weHave20CocoCardsInTotal() {
		assertEquals("The number of coco cards should be:", 20, CocoDeck.getInstance().getCocoCardDeck().size());
	}
	
	@Test
	public void weHaveTheCorrectAmountOfEachCard() {
		int numGoatAndCutlassCoco = 0;
		int numMolassesAndWoodCoco = 0;
		int numGetShipOrLairCoco = 0;
		int numMoveGhostCaptainCoco = 0;
		
		for(CocoCard card: CocoDeck.getInstance().getCocoCardDeck()) {
			if(card instanceof GetGoatAndCutlassCoco) {
				numGoatAndCutlassCoco++;
			}
			else if(card instanceof GetMolassesAndWoodCoco) {
				numMolassesAndWoodCoco++;
			}
			else if(card instanceof GetShipOrLairCoco) {
				numGetShipOrLairCoco++;
			}
			else if(card instanceof MoveGhostCaptainCoco) {
				numMoveGhostCaptainCoco++;
			}
		}
		assertEquals("The number of Goat/Cutlasss coco cards should be:", 3, numGoatAndCutlassCoco);
		assertEquals("The number of Molasses/Wood coco cards should be:", 3, numMolassesAndWoodCoco);
		assertEquals("The number of Ship/Lair coco cards should be:", 3, numGetShipOrLairCoco);
		assertEquals("The number of Move Ghost Captain coco cards should be:", 11, numMoveGhostCaptainCoco);
	}
}
