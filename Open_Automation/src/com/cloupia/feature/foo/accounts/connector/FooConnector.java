package com.cloupia.feature.foo.accounts.connector;

import org.apache.log4j.Logger;

import com.cisco.cuic.api.client.JSON;
import com.cloupia.feature.foo.accounts.FooAccount;
import com.cloupia.lib.connector.account.AbstractInfraAccount;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;
import com.cloupia.lib.util.ssh.SSHClient;
import com.cloupia.lib.util.ssh.SSHCommandOutput;
import com.cloupia.service.cIM.inframgr.collector.model.ConfigItemIf;

public class FooConnector {

	
	static Logger logger = Logger.getLogger(FooConnector.class);
	public String collectData(String accountName,String command) throws Exception{
		
		FooAccount acc = getFooCredential(accountName);
		SSHClient client = connection(acc.getServerAddress(), Integer.parseInt(acc.getPort()), acc.getLogin(), acc.getPassword());
		client.connect();
		String data = null;
		if(client.isConnected())
		{
			try 
			{
				

				SSHCommandOutput output = client.executeCommand(command);
				if (output.getExitCode() > 0 && output.getStdErr() != null
						&& !output.getStdErr().trim().isEmpty()) {
					logger.error(output.getStdErr());
					throw new Exception("ERROR while executing command: " + command	+ " on account: " + accountName + " ERROR: "+ output.getStdErr());
				}
				logger.debug("Command Output :" +output.getStdOut());
				data= output.getStdOut();
			}finally 
			{
				if (client != null)
					client.disconnect();
			}
		}
		return data;
	}
	
	
	public SSHClient connection(String host,int port,String userName,String passWord){
		SSHClient client = new SSHClient(host, port, userName, passWord);
		return client;
	}


	public boolean execute(String accountName,
			ConfigItemIf item) throws Exception {
		
		String data= collectData(accountName, item.getCommand());
		System.out.println(data);
		String [] errormessages=item.getErrorMessages();
		for(String d : errormessages){
			if(data.toLowerCase().contains(d.toLowerCase())){
				throw new Exception("Error occurred:" +d);
				
			}
		}
		return true;
		
	}
	
	private static FooAccount getFooCredential(String accountName) throws Exception{
		PhysicalInfraAccount acc = AccountUtil.getAccountByName(accountName);
		String json = acc.getCredential();
		AbstractInfraAccount specificAcc  =  (AbstractInfraAccount) JSON.jsonToJavaObject(json, FooAccount.class);
		specificAcc.setAccount(acc);
		
		return (FooAccount) specificAcc;
		
	}
}
