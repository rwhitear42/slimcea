package com.cloupia.feature.foo.inventory;

import com.cloupia.service.cIM.inframgr.collector.controller.ItemDataObjectBinderIf;
import com.cloupia.service.cIM.inframgr.collector.controller.ItemParserIf;
import com.cloupia.service.cIM.inframgr.collector.controller.MappedItemListener;
import com.cloupia.service.cIM.inframgr.collector.model.ItemDataFormat;
import com.cloupia.service.cIM.inframgr.collector.model.ItemIf;

/**
 * This is a generic implementation of ItemIf, the purpose of this class is to describe the type
 * of inventory items you're interested in collecting on.  For my purposes, I am using a unique string to
 * represent the type of data I will be collecting.  The model class is the class to be used for binding
 * purposes.  When I say binding, I mean when the data is available, it will be marshalled into this
 * model class to be persisted in the DB.
 *
 */
public class DummyInventoryItem implements ItemIf {
	
	private String type;
	private Class model;
	
	public DummyInventoryItem(String type, Class model) {
		this.type = type;
		this.model = model;
	}

	@Override
	public ItemDataObjectBinderIf getBinder() {
		// TODO Auto-generated method stub
		return null;
	}

	//this is an important method to note, you will most likely be using the value of method to properly
	//marshall the data you receive into this POJO you are returning.
	@Override
	public Class getBoundToClass() {
		return model;
	}

	@Override
	public ItemDataFormat getCollectedDataFormat() {
		//can be null, used mostly for logging purposes
		return null;
	}

	@Override
	public MappedItemListener getItemListener() {
		// TODO Auto-generated method stub
		return null;
	}

	//it's a good idea to implement these two methods, they are used for logging purposes
	@Override
	public String getLabel() {
		return type;
	}

	@Override
	public String getName() {
		return type;
	}

	@Override
	public ItemDataFormat getParsedDataFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemParserIf getParser() {
		// TODO Auto-generated method stub
		return null;
	}

}
