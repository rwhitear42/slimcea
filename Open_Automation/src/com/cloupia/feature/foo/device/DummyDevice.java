package com.cloupia.feature.foo.device;

import java.util.ArrayList;
import java.util.List;

import com.cloupia.feature.foo.accounts.model.DummyInterface;
import com.cloupia.feature.foo.constants.FooConstants;
import com.cloupia.model.cIM.InventoryDBItemIf;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This is a dummy class used to simulate a device.  I'll be using this class to provide dummy
 * data.
 */
public class DummyDevice {
	
	private static DummyDevice instance = new DummyDevice();
	
	private DummyDevice() {
		
	}
	
	public static DummyDevice getInstance() {
		return instance;
	}
	
	public boolean connect(String ip, String login, String password) {
		return true;
	}
	
	//this is the dummy data we're returning, again this is all for testing/demonstration purposes
	private static final String DUMMY_INTERFACES = 
		"{\"data\":[" +
		"	{\"name\":\"bond0\", \"mtu\":\"1500\",\"status\":\"not active\",\"ip\":\"192.168.56.17\",\"mac\":\"00:00:00:00:00:00\",\"mask\":\"255.255.255.0\"}," +
		"	{\"name\":\"asdf0\", \"mtu\":\"150\",\"status\":\"active\",\"ip\":\"100.168.56.17\",\"mac\":\"11:00:00:11:00:00\",\"mask\":\"255.255.255.0\"}" +
		"]}";
	
	private static final String DUMMY_VLANS = 
			"{\"data\":[" +
			"	{\"name\":\"vlan1\", \"vlanID\":\"1\"}," +
			"	{\"name\":\"vlan2\", \"vlanID\":\"2\"}" +
			"]}";
	
	public String getData(String type) {
		if (type.equals(FooConstants.INTERFACES)) {
			return DUMMY_INTERFACES;
		} else if (type.equals(FooConstants.PORTS)) {
			return DUMMY_VLANS;
		} else {
			return null;
		}
	}
	
	public static void main(String[] args) {
		String jsonData = DummyDevice.getInstance().getData(FooConstants.INTERFACES);
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(jsonData);
		JsonArray array = obj.getAsJsonArray("data");
		List<InventoryDBItemIf> objs = new ArrayList<InventoryDBItemIf>();
		Gson gson = new Gson();
		//for each object in the json array, use gson to convert the json string into a pojo
		for (int i=0; i<array.size(); i++) {
			JsonElement ele = array.get(i);
			InventoryDBItemIf invDBObj = gson.fromJson(ele, DummyInterface.class);
			objs.add(invDBObj);
		}
	}

}
