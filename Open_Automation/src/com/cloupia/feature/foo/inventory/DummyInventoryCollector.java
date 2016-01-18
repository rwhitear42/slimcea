package com.cloupia.feature.foo.inventory;

import com.cloupia.feature.foo.constants.FooConstants;
import com.cloupia.model.cIM.FormLOVPair;
import com.cloupia.service.cIM.inframgr.collector.controller.InventoryCollector;
import com.cloupia.service.cIM.inframgr.collector.controller.ItemDataObjectBinderIf;
import com.cloupia.service.cIM.inframgr.collector.controller.ItemParserIf;
import com.cloupia.service.cIM.inframgr.collector.controller.JDOPersistenceListener;
import com.cloupia.service.cIM.inframgr.collector.controller.NodeConnectorIf;
import com.cloupia.service.cIM.inframgr.collector.controller.PersistenceListener;
import com.cloupia.service.cIM.inframgr.collector.model.NodeID;

/**
 * This class is where you handle most of the configuration details for your inventory collection task.
 * You specify which class to use for connection, parsing, binding, persisting.
 * 
 */
public class DummyInventoryCollector extends InventoryCollector {
	
	//you don't really need to cache instances of all these classes, but it can't hurt to for performance
	//purposes, it's best you don't create new instances on every collection cycle ...
	private NodeID node;
	private DummyConnector connector;
	private DummyBinder binder;
	//this is the default persistence handler, it assumes you have JDO annotated POJOs and will try to
	//persist them accordingly.  if you want to handle persistence yourself, you should develop your own
	//implementation.
	private JDOPersistenceListener listener;

	public DummyInventoryCollector(NodeID nodeId) {
		super(nodeId);
		this.node = nodeId;
		this.connector = new DummyConnector();
		this.binder = new DummyBinder();
		this.listener = new JDOPersistenceListener();
	}

	@Override
	public NodeConnectorIf getConnector() {
		return connector;
	}

	@Override
	public PersistenceListener getItemListener() {
		return listener;
	}

	@Override
	public ItemParserIf getItemParser() {
		//I'm not performing any parsing in this example, so I'll return null here
		//any step you do not want to handle, you can just return null.
		return null;
	}

	@Override
	public ItemDataObjectBinderIf getObjectBinder() {
		return binder;
	}

	@Override
	public String getTaskName() {
		//it's a good idea when implementing this method to include node.getConnectorId() which
		//is basically the account name, this way you can easily identify which collection task
		//is being executed in the logs.
		return FooConstants.DUMMY_INVENTORY_COLLECTOR_NAME + "_" + node.getConnectorId();
	}

	@Override
	public long getFrequenceInMinutes() {
		//pretty self explanatory, this is how often this collector is triggered in minutes
		return 15;
	}
	
	@Override
	public FormLOVPair[] getFrequencyHoursLov() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public FormLOVPair[] getFrequencyMinsLov() {
		// TODO Auto-generated method stub
		return null;
	}

}
