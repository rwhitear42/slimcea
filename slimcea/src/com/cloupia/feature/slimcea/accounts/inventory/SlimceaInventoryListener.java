package com.cloupia.feature.slimcea.accounts.inventory;

import org.apache.log4j.Logger;

import com.cisco.cuic.api.client.JSON;
import com.cloupia.feature.slimcea.accounts.SlimceaAccount;
import com.cloupia.feature.slimcea.accounts.util.SlimceaAccountPersistenceUtil;
import com.cloupia.lib.connector.InventoryContext;
import com.cloupia.lib.connector.InventoryEventListener;
import com.cloupia.lib.connector.account.AbstractInfraAccount;
import com.cloupia.lib.connector.account.AccountTypeEntry;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalAccountManager;
import com.cloupia.lib.connector.account.PhysicalConnectivityStatus;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;

public class SlimceaInventoryListener implements InventoryEventListener {
	private static Logger logger = Logger.getLogger(SlimceaInventoryListener.class);

	@Override
	public void afterInventoryDone(String accountName, InventoryContext context)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Call in SlimceaInventoryListener afterInventoryDone");
		
		SlimceaAccountPersistenceUtil.persistCollectedInventory(accountName);
		
		AccountTypeEntry entry = PhysicalAccountManager.getInstance().getAccountTypeEntryByName(accountName);
		PhysicalConnectivityStatus connectivityStatus =null;
		if(entry != null)
		{
			connectivityStatus = entry.getTestConnectionHandler().testConnection(accountName);
		}
		
		SlimceaAccount acc = getSlimceaCredential(accountName);
		
		if(acc != null && connectivityStatus != null)
		{
			
			
			logger.info("Inventory collected successfully");
		}


	}

	@Override
	public void beforeInventoryStart(String accountName, InventoryContext arg1)
			throws Exception {
		logger.info("Call in SlimceaInventoryListener beforeInventoryStart");
		
	}
	
	private static SlimceaAccount getSlimceaCredential(String accountName) throws Exception{
		PhysicalInfraAccount acc = AccountUtil.getAccountByName(accountName);
		String json = acc.getCredential();
		AbstractInfraAccount specificAcc  =  (AbstractInfraAccount) JSON.jsonToJavaObject(json, SlimceaAccount.class);
		specificAcc.setAccount(acc);
		
		return (SlimceaAccount) specificAcc;
		
	}

}
