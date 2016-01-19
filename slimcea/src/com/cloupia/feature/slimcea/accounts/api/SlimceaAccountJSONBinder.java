package com.cloupia.feature.slimcea.accounts.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cloupia.service.cIM.inframgr.collector.model.ItemResponse;

/**
 * This is sample implementation to provide the response data 
 * 
 * 
 * 
 *
 */
public class SlimceaAccountJSONBinder extends SlimceaJSONBinder {
	private static Logger logger = Logger.getLogger(SlimceaAccountJSONBinder.class);
	
	@Override
	public ItemResponse bind(ItemResponse bindable) {
		String jsonData = bindable.getCollectedData();
		logger.debug("RAW JSON Data" + jsonData);
		
		if( jsonData != null && !jsonData.isEmpty())
		{
			//Json data to be converted as target object 
		
		}
		
		return null;
	}

	@Override
	public List<Class> getPersistantClassList() {
		List<Class> cList = new ArrayList<Class>();
		//add the Persistant class in the CList , for reference.
		return cList;
	}


}
