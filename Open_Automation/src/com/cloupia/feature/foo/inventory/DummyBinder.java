package com.cloupia.feature.foo.inventory;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cloupia.model.cIM.InventoryDBItemIf;
import com.cloupia.service.cIM.inframgr.collector.controller.ItemDataObjectBinderIf;
import com.cloupia.service.cIM.inframgr.collector.model.ItemResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DummyBinder implements ItemDataObjectBinderIf {
	
	private static Logger logger = Logger.getLogger(DummyBinder.class);

	@Override
	public ItemResponse bind(ItemResponse response) {
		//Recall in DummyConnector, i setCollectedData with the json string i got from my dummy device
		String jsonData = response.getCollectedData();
		logger.info("ppppp - json data = " + jsonData);
		//now all i'm doing is using gson to marshall the json string into a java pojo
		//first step, the json string comes as an object with a field called data, the data field is
		//an array storing all the objects we're interested in
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(jsonData);
		//so parse through the json object and get the json array so we can perform the marshalling
		JsonArray array = obj.getAsJsonArray("data");
		Class clazz = response.getItem().getBoundToClass();
		List<InventoryDBItemIf> objs = new ArrayList<InventoryDBItemIf>();
		Gson gson = new Gson();
		//for each object in the json array, use gson to convert the json string into a pojo
		for (int i=0; i<array.size(); i++) {
			JsonElement ele = array.get(i);
			//please take note that i'm expecting items of type InventoryDBItemIf, which i made
			//sure to implement in our inventory models
			InventoryDBItemIf invDBObj = (InventoryDBItemIf) gson.fromJson(ele, clazz);
			//in this step i'm making sure that each object sets the account name, this way i know
			//the current pojo being made is to be associated with this account name.
			String accountName = response.getNodeId().getConnectorId();
			invDBObj.setAccountName(accountName);
			//add the pojo into a list
			objs.add(invDBObj);
		}
		//place all the newly binded pojos into the response and return, the next step is persistence, at this
		//in this example, the pojos are now db ready.  the jdo persistence listener i return in the 
		//DummyInventoryCollector will take care of persistence for me.  if your persistence logic is more
		//complicated you will need to prepare these objects for persistence yourself.
		response.setBoundObjects(objs);
		return response;
	}

}
