package Trading;

/**
 * Class for TradingResourceHolder in a game of CatanJr. A ResourceHolder who can initiate trades.
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */
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
	/**
	 * tradePossible determines if both parties involved in trade have the required resources to complete trade.
	 * @param RH The resource holder the TradingResourceHolder/Player is trading with.
	 * @param offeredRes The resource type that is being offered to the ResourceHolder
	 * @param numOfferedRes The number of resources that are being offered to the ResourceHolder
	 * @param requestedRes The resource type that is being requested to the ResourceHolder.
	 * @param numRequestedRes The number of resources that are being offered to the ResourceHolder.
	 * @return boolean Indication of whether both parties have the required resources.
	 */
	private boolean tradePossible(ResourceHolder RH, ResourceType offeredRes, int numOfferedRes, ResourceType requestedRes, int numRequestedRes) {
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
	/**
	 * handleTrade uses moveResource to take the requested resource from the ResourceHolder to the player
	 * and gives the offered resource from the player to the ResourceHolder.
	 * @param RH
	 * @param offeredRes The resource type that is being offered to the ResourceHolder
	 * @param numOfferedRes The number of resources that are being offered to the ResourceHolder
	 * @param requestedRes The resource type that is being requested to the ResourceHolder.
	 * @param numRequestedRes The number of resources that are being offered to the ResourceHolder.
	 */
	private void handleTrade(ResourceHolder RH,ResourceType offeredRes, int numOfferedRes, ResourceType requestedRes, int numRequestedRes) {
		RH.moveResource(offeredRes,numOfferedRes,this);
		this.moveResource(requestedRes,numRequestedRes,RH);
	}
	/**
	 * trade determines which type of ResourceHolder the Player is trading with and sets the amount of 
	 * offeredResources and requested resources accordingly.
	 * @param RH ResourceHolder which will be a Market or Stockpile
	 * @param offeredRes The resource being offered to the ResourceHolder.
	 * @param requestedRes The resource being requested from the ResourceHolder.
	 */
	public void trade(ResourceHolder RH, ResourceType offeredRes, ResourceType requestedRes) {
		int numOfferedRes=0;
		if(RH instanceof Market)
			numOfferedRes = 1;
		else if(RH instanceof Stockpile)
			numOfferedRes = 2;
		
		if(this.tradePossible(RH, offeredRes, numOfferedRes, requestedRes, 1))
			this.handleTrade(RH, offeredRes, numOfferedRes, requestedRes, 1);
		
		if (RH instanceof Market)
			((Market) RH).checkRefreshMarket();
	}
}
