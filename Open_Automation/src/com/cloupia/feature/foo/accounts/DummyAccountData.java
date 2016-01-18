package com.cloupia.feature.foo.accounts;


import java.util.ArrayList;
import java.util.List;

import com.cloupia.model.cIM.InventoryDBItemIf;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This is a dummy class used to simulate an account.  I'll be using this class to provide dummy
 * data.
 */
public class DummyAccountData{
	
	private static DummyAccountData instance = new DummyAccountData();
	
	private DummyAccountData() {
		
	}
	
	public static DummyAccountData getInstance() {
		return instance;
	}
	
	public boolean connect(String ip, String login, String password) {
		return true;
	}
	
	//this is the dummy data we're returning, again this is all for testing/demonstration purposes
	private static final String DUMMY_ACCOUNT_DETAILS = 
		"{\"data\":[" +
		"	{\"accountName\":\"account-1\",\"status\":\"not active\",\"ip\":\"192.168.56.17\"," +
		"	{\"accountName\":\"account-2\",\"status\":\"active\",\"ip\":\"100.168.56.17\"}" +
		"]}";
	
	public String getData() {
		return DUMMY_ACCOUNT_DETAILS;
	}
	
	public static void main(String[] args) {
		String jsonData = DummyAccountData.getInstance().getData();
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(jsonData);
		JsonArray array = obj.getAsJsonArray("data");
		List<InventoryDBItemIf> objs = new ArrayList<InventoryDBItemIf>();
		Gson gson = new Gson();
		//for each object in the json array, use gson to convert the json string into a pojo
		/*for (int i=0; i<array.size(); i++) {
			JsonElement ele = array.get(i);
			InventoryDBItemIf invDBObj = gson.fromJson(ele, DummyAccount.class);
			objs.add(invDBObj);
		}*/
	}

}

