package Trading;

import Trading.ResourceHolder.ResourceType;

public abstract class TradingResourceHolder extends ResourceHolder {
	//===========================================================
	// Constructor 
	//===========================================================
	protected TradingResourceHolder(int numCards) {
		super(numCards);
	}
  	//===========================================================
  	// Other Methods
  	//===========================================================
	public boolean tradePossible(ResourceHolder RH, ResourceType offeredRes, int numOfferedRes, ResourceType requestedRes, int numRequestedRes) {
		if(this.resourcesAvailable(offeredRes,numOfferedRes)&&RH.resourcesAvailable(requestedRes,numRequestedRes))
			return true;
		
		else if(!this.resourcesAvailable(offeredRes,numOfferedRes)) {
			System.out.println("\nPlayer does not have the offered resources for trade.");
			return false;
		}

		else {
			System.out.println(this.instanceType(RH)+" does not have have the requested resources for trade.");
			return false;
		}
	}
	
	public void handleTrade(ResourceHolder RH,ResourceType offeredResource, int amountOffered, ResourceType requestedResource, int amountRequired) {
		RH.moveResource(offeredResource,amountRequired,this);
		this.moveResource(requestedResource,amountRequired,RH);
	}
	
	public void trade(ResourceHolder RH, ResourceType offeredRes, ResourceType requestedRes) {
		int amountRequired=0;
		if(RH instanceof Market) {
			amountRequired = 1;
		}
		else if(RH instanceof Stockpile) {
			amountRequired = 2;
		}

		if(this.tradePossible(RH, offeredRes, amountRequired, requestedRes, 1)) {
			this.handleTrade(RH, offeredRes, amountRequired, requestedRes, 1);
		}
		
		if (RH instanceof Market) {
			((Market) RH).checkRefreshMarket();
		}
	}
}
