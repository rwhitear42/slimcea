package com.cloupia.feature.foo.accounts.inventory;

import java.util.Map;

import org.apache.log4j.Logger;

import com.cisco.cuic.api.client.JSON;
import com.cloupia.feature.foo.accounts.FooAccount;
import com.cloupia.feature.foo.accounts.api.FooAccountJSONBinder;
import com.cloupia.feature.foo.accounts.api.FooJSONBinder;
import com.cloupia.lib.connector.AbstractInventoryItemHandler;
import com.cloupia.lib.connector.InventoryContext;
import com.cloupia.lib.connector.account.AbstractInfraAccount;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;
import com.cloupia.service.cIM.inframgr.collector.controller.PersistenceListener;
import com.cloupia.service.cIM.inframgr.collector.model.ItemResponse;

/**
 * This class is used to provide the  implementation for Foo Account Inventory Item handlers.
 * 	While writing any new Item handler for any Foo Account inventory task, that should extend this AbstractInventoryItemHandler Class. 
 * This provides the flexible way to override the url, binder and listener objects.
 * 
 *  This class provides the common logic to collect the inventory data and persist into the respective persistable POJO objects and cleanup 
 *  the data while deleting the Foo account.
 */
public class FooInventoryItemHandler extends AbstractInventoryItemHandler {

	private static Logger logger = Logger.getLogger(FooInventoryItemHandler.class);
	
	/**
	 * This method used for cleanup Item Inventory.
	 * method of InventoryItemHandlerIf interface
	 * @param accountName
	 * @return void
	 * @exception Exception
	 */
	@Override
	public void cleanup(String accountName) throws Exception {
		// TODO Auto-generated method stub

	}
	/** 
	 * This method used for do Inventory of Account
	 * @Override method of IInventoryItemHandlerIf interface
	 * @param accountName ,InventoryContext
	 * @return void
	 * @exception Exception
	 */
	@Override
	public void doInventory(String accountName, InventoryContext inventoryCtxt)
			throws Exception {
		doInventory(accountName);

	}
	/**
	 * This method used for do Inventory of Account
	 * @Override method of IInventoryItemHandlerIf interface
	 * @param accountName ,Object
	 * @return void
	 * @exception Exception
	 */
	@Override
	public void doInventory(String accountName, Object obj) throws Exception {
		// TODO Auto-generated method stub

	}
	/** private Method used for doing Inventory of Account
	 * @param accountName
	 * @return void
	 * @exception Exception
	 */
	private void doInventory(String accountName) throws Exception {
		
		FooAccount acc = getFooCredential(accountName);
		
		
		//FooAccountAPI api = FooAccountAPI.getFooAccountAPI(acc);
		
		/**
		 * To provide the real implemntation , depends on the respond data binder for the requested item.
		 * To ensure the data collecting for the inventory via HTTP / SSH connection. 
		 * Response is converted as JSON Data, Json Data is binded with the 
		 */
		
		//String jsonData = api.getInventoryData(getUrl());
		String jsonData = null;
		ItemResponse bindableResponse = new ItemResponse();
		bindableResponse.setContext(getContext(accountName));
		bindableResponse.setCollectedData(jsonData);
		ItemResponse bindedResponse = null;
		logger.info("Before Callng bind");
		
		FooJSONBinder binder = getBinder();
		if(binder != null)
		{
			bindedResponse = binder.bind(bindableResponse);
			
		}
		
		logger.info("after Calling bind");
		
		
		 PersistenceListener listener = getListener();
		if(listener != null)
		{
			logger.info("Calling for Persistence");
			listener.persistItem(bindedResponse);
		}
		else
		{
			logger.info("Persistence is null");
		}
		
		
		
		
		
		
	}

	/** Method used for get Url
	 * @param No
	 * @return String
	 * @exception No
	 */
	public String getUrl() {
		// TODO Auto-generated method stub
		return "platform/1/protocols/smb/shares";
	}
	/** Method used to get object of  FooAccountJSONBinder
	 * 	Binder will bind the respective object as JSON. 
	 * @param No
	 * @return FooAccountJSONBinder
	 * @exception No
	 */
	public FooAccountJSONBinder getBinder() {
		// TODO Auto-generated method stub
		return new FooAccountJSONBinder();
	}
	/** Private Method used to get Map of Context
	 * @param accountName
	 * @return Map<String, Object>
	 * @exception No
	 */
	private Map<String, Object> getContext(String accountName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** Private Method used to get Object of PersistenceListener
	 * @param No
	 * @return PersistenceListener
	 * @exception No
	 */
	private PersistenceListener getListener() {
		// TODO Auto-generated method stub
		return new FooCollectorInventroyPersistenceListener();
	}
	/** Private Method used to get Credential of account Name
	 * @param accountName
	 * @return FooAccount
	 * @exception Exception
	 */
	private static FooAccount getFooCredential(String accountName) throws Exception{
		PhysicalInfraAccount acc = AccountUtil.getAccountByName(accountName);
		String json = acc.getCredential();
		AbstractInfraAccount specificAcc  =  (AbstractInfraAccount) JSON.jsonToJavaObject(json, FooAccount.class);
		specificAcc.setAccount(acc);
		
		return (FooAccount) specificAcc;
		
	}

}
