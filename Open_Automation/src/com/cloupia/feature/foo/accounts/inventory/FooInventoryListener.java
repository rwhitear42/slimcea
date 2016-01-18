package com.cloupia.feature.foo.accounts.inventory;

import org.apache.log4j.Logger;

import com.cisco.cuic.api.client.JSON;
import com.cloupia.feature.foo.accounts.FooAccount;
import com.cloupia.feature.foo.accounts.util.FooAccountPersistenceUtil;
import com.cloupia.lib.connector.InventoryContext;
import com.cloupia.lib.connector.InventoryEventListener;
import com.cloupia.lib.connector.account.AbstractInfraAccount;
import com.cloupia.lib.connector.account.AccountTypeEntry;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalAccountManager;
import com.cloupia.lib.connector.account.PhysicalConnectivityStatus;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;

public class FooInventoryListener implements InventoryEventListener {
	private static Logger logger = Logger.getLogger(FooInventoryListener.class);

	@Override
	public void afterInventoryDone(String accountName, InventoryContext context)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Call in FooInventoryListener afterInventoryDone");
		
		FooAccountPersistenceUtil.persistCollectedInventory(accountName);
		
		AccountTypeEntry entry = PhysicalAccountManager.getInstance().getAccountTypeEntryByName(accountName);
		PhysicalConnectivityStatus connectivityStatus =null;
		if(entry != null)
		{
			connectivityStatus = entry.getTestConnectionHandler().testConnection(accountName);
		}
		
		FooAccount acc = getFooCredential(accountName);
		
		if(acc != null && connectivityStatus != null)
		{
			
			
			logger.info("Inventory collected successfully");
		}


	}

	@Override
	public void beforeInventoryStart(String accountName, InventoryContext arg1)
			throws Exception {
		logger.info("Call in FooInventoryListener beforeInventoryStart");
		
	}
	
	private static FooAccount getFooCredential(String accountName) throws Exception{
		PhysicalInfraAccount acc = AccountUtil.getAccountByName(accountName);
		String json = acc.getCredential();
		AbstractInfraAccount specificAcc  =  (AbstractInfraAccount) JSON.jsonToJavaObject(json, FooAccount.class);
		specificAcc.setAccount(acc);
		
		return (FooAccount) specificAcc;
		
	}

}
