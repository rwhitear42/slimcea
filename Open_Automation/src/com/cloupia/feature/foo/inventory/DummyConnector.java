package com.cloupia.feature.foo.inventory;

import com.cloupia.feature.foo.device.DummyDevice;
import com.cloupia.model.cIM.InfraAccount;
import com.cloupia.service.cIM.inframgr.collector.controller.NodeConnectorIf;
import com.cloupia.service.cIM.inframgr.collector.model.ConfigItemIf;
import com.cloupia.service.cIM.inframgr.collector.model.ConfigResponse;
import com.cloupia.service.cIM.inframgr.collector.model.ConnectionProperties;
import com.cloupia.service.cIM.inframgr.collector.model.ConnectionStatus;
import com.cloupia.service.cIM.inframgr.collector.model.ItemIf;
import com.cloupia.service.cIM.inframgr.collector.model.ItemResponse;
import com.cloupia.service.cIM.inframgr.collector.model.NodeID;
import com.cloupia.service.cIM.inframgr.collector.view2.ConnectorCredential;

public class DummyConnector implements NodeConnectorIf {
	
	private DummyDevice device = DummyDevice.getInstance();

	//This is where you will perform all your connection logic.  For example, establishing a SSH session, retrieving
	//a cookie or token, etc.
	@Override
	public ConnectionStatus connect(ConnectorCredential credential, ConnectionProperties props) throws Exception {
		//In DummyCollectorFactory, we created a DummyInventoryCollector with a GenericNodeID.  The
		//ConnectorCredential will be using this GenericNodeID to retrieve the device details that you are
		//connecting to.  This is why it's a good idea to use GenericNodeID instead of implementing your own.
		InfraAccount account = credential.toInfraAccount();
		String serverIP = account.getServer(); //deviceCreds.getDeviceIp();
		String login = account.getUserID(); //deviceCreds.getLogin();
		String password = account.getPassword();
		
		//your connection logic will probably be more complicated
		boolean result = device.connect(serverIP, login, password);
		
		//now you need to create an instance of connection status and specify whether the connection succeeded or failed
		ConnectionStatus status = new ConnectionStatus();
    	status.setConnected(result);
    	return status;
	}

	@Override
	public void disconnect() throws Exception {
		//perform any session or connection clean ups here
	}

	@Override
	public ConfigResponse executeItems(NodeID nodeId, ConfigItemIf item) throws Exception {
		//NOTE: ignore this method for now, you really don't need this for inventory collection purposes
		return null;
	}

	//This is where you would perform your inventory collection logic, in this method here, you should determine
	//what data to retrieve and how to retrieve that data and prepare it to be returned for the next step, either
	//parsing or binding or persisting.
	@Override
	public ItemResponse getItem(NodeID nodeId, ItemIf item) throws Exception {
		//In DummyCollectorFactory, recall we populated our DummyInventoryCollector with a couple DummyInventoryItems
		//this is what item is, so you can cast item to the your implementation of ItemIf
		DummyInventoryItem invItem = (DummyInventoryItem) item;
		String dataToCollect = invItem.getName();
		//Note: in my inventory item getName() returns me the data i am planning on collecting
		String data = device.getData(dataToCollect);
		//so data in my example here is just a json string, the next step is to create an instance of item response
		//populate it with the data and other details so you can prepare to parse/bind/persist it.  in my case, the json
		//data is well structured, i don't need to perform any additional parsing, i'm just going to take the json
		//data and move to binding, so after this you'll want to take a look at DummyBinder.
		ItemResponse itemResponse = new ItemResponse();
		//make sure you pass along the itemif, you will need it to figure out what pojo to bind the collected data into!
		itemResponse.setItem(item);
		//this is how you pass along the output from the device to the next step
		itemResponse.setCollectedData(data);
		//make sure to pass along the node id, you'll need it in case you want to know the account name you're
		//currently collecting on and any device details
		itemResponse.setNodeId(nodeId);
		return itemResponse;
	}

}
