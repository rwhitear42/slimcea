package com.cloupia.feature.foo.accounts.status.compute;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.cisco.cuic.api.client.JSON;
import com.cloupia.feature.foo.accounts.FooAccount;
import com.cloupia.fw.objstore.ObjStoreHelper;
import com.cloupia.lib.cIaaS.netapp.model.StorageAccountStatus;
import com.cloupia.lib.connector.account.AbstractInfraAccount;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalConnectivityStatus;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;

public class ComputeAccountStatusSummary {

	public void  accountSummary(String accountName)throws Exception {
		FooAccount acc = getFooCredential(accountName);
		PhysicalInfraAccount infraAccount = AccountUtil.getAccountByName(accountName);
		PhysicalConnectivityStatus status = new PhysicalConnectivityStatus(infraAccount);
		
		StorageAccountStatus accStatus = new StorageAccountStatus();
		accStatus.setAccountName(infraAccount.getAccountName());
		accStatus.setDcName(acc.getPod());
		//VERSION
		accStatus.setReachable(true);
		accStatus.setLicense("");
		accStatus.setLastMessage("Connection is verified");
		accStatus.setLastUpdated(System.currentTimeMillis());
		accStatus.setModel("");
		accStatus.setServerAddress(acc.getServerAddress());
		accStatus.setVersion("");
		
	}
	
	private static FooAccount getFooCredential(String accountName) throws Exception{
		PhysicalInfraAccount infraAccount = AccountUtil.getAccountByName(accountName);
		String json = infraAccount.getCredential();
		AbstractInfraAccount specificAcc  =  (AbstractInfraAccount) JSON.jsonToJavaObject(json, FooAccount.class);
		specificAcc.setAccount(infraAccount);
		
		return (FooAccount) specificAcc;
		
	}
	
	public static void persistStorageAccountStatus(StorageAccountStatus ac) throws Exception
    {
        PersistenceManager pm = ObjStoreHelper.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        try
        {
            tx.begin();

            String query = "accountName == '" + ac.getAccountName() + "'";

            Query q = pm.newQuery(StorageAccountStatus.class, query);
            q.deletePersistentAll();

            pm.makePersistent(ac);
            tx.commit();
        } finally
        {
            try
            {
                if (tx.isActive())
                {
                    tx.rollback();
                }
            }
            finally { pm.close(); }
        }
    }
}
