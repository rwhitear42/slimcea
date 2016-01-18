package com.cloupia.feature.foo.accounts.handler;

import org.apache.log4j.Logger;

import com.cisco.cuic.api.client.JSON;
import com.cloupia.feature.foo.accounts.FooAccount;
import com.cloupia.feature.foo.accounts.api.FooAccountAPI;
import com.cloupia.feature.foo.accounts.status.storage.StorageAccountStatusSummary;
import com.cloupia.lib.connector.account.AbstractInfraAccount;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalConnectivityStatus;
import com.cloupia.lib.connector.account.PhysicalConnectivityTestHandler;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;
import com.cloupia.model.cIM.InfraAccountTypes;

/**
 * This class is used to test the connection of the real device reachable from the UCSD.
 * 
 * 
 *
 */
public class FooTestConnectionHandler extends PhysicalConnectivityTestHandler{
	static Logger logger = Logger.getLogger(FooTestConnectionHandler.class);
	
	@Override
	public PhysicalConnectivityStatus testConnection(String accountName)
			throws Exception {
		
		FooAccount acc = getFooCredential(accountName);
		PhysicalInfraAccount infraAccount = AccountUtil.getAccountByName(accountName);
		PhysicalConnectivityStatus status = new PhysicalConnectivityStatus(infraAccount);
		status.setConnectionOK(false);
		if(infraAccount != null)
		{
			if(infraAccount.getAccountType() != null)
			{
				logger.info(infraAccount.getAccountType());
				
				
				
				
				//Instead of Foo Account , real account type needs to specified.
				if(infraAccount.getAccountType().equals("Foo Account"))
				{
					logger.info("Inside if condition");
					/**
					 * The below snippet will be used for the real device connection.
					 * 
					 */
					/*try
					{
						FooAccountAPI.getFooAccountAPI(acc);	
						logger.info("*******Setting test Connection as true*****");
					}
					catch(Exception e)
					{
						status.setConnectionOK(false);
						status.setErrorMsg("Failed to establish connection with the Device.");
						logger.debug("Test Connection is failed");
						return status;
					}*/
					
					status.setConnectionOK(true);
					StorageAccountStatusSummary.accountSummary(accountName);
					logger.debug("Connection is verified");
					
				}
			}
			
			
		}
		logger.info("Returning status "+status.isConnectionOK());
		return status;
	}
	
	private static FooAccount getFooCredential(String accountName) throws Exception{
		PhysicalInfraAccount acc = AccountUtil.getAccountByName(accountName);
		String json = acc.getCredential();
		AbstractInfraAccount specificAcc  =  (AbstractInfraAccount) JSON.jsonToJavaObject(json, FooAccount.class);
		specificAcc.setAccount(acc);
		
		return (FooAccount) specificAcc;
		
	}
}
