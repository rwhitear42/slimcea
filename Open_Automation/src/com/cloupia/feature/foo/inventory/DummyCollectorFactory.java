package com.cloupia.feature.foo.inventory;

import com.cloupia.feature.foo.accounts.model.DummyInterface;
import com.cloupia.feature.foo.accounts.model.DummyVLAN;
import com.cloupia.feature.foo.constants.FooConstants;
import com.cloupia.model.cIM.InfraAccount;
import com.cloupia.model.cIM.InfraAccountTypes;
import com.cloupia.model.cIM.stackView.StackViewItemProviderIf;
import com.cloupia.service.cIM.inframgr.GenericInfraAccountConnectionTestHandler;
import com.cloupia.service.cIM.inframgr.InfraAccountConnectionTestHandlerIf;
import com.cloupia.service.cIM.inframgr.collector.controller.CollectorFactory;
import com.cloupia.service.cIM.inframgr.collector.controller.InventoryCollector;
import com.cloupia.service.cIM.inframgr.collector.impl.GenericNodeID;
import com.cloupia.service.cIM.inframgr.reports.contextresolve.ConvergedStackComponentBuilderIf;

/**
 * This is a dummy implementation of a collector to demonstrate how to develop your own.
 *
 */
public class DummyCollectorFactory extends CollectorFactory {

	public DummyCollectorFactory(int accountType) {
		super(accountType);
	}

	@Override
	public InventoryCollector createCollector(String accountName) throws Exception {
		//creating an instance of generic node id, your inventory collector will need a node id, it's easiest
		//to use this abstract implementation.
		//the node id's purpose is given an account name, retrieve all the credentials for that account.
		//this will work assuming you are using GenericInfraAccountReport to handle all account management!
		//NOTE: as i specified this collector is in network category, i am making sure the generic node id is
		//of network type too!
		GenericNodeID nodeID = new GenericNodeID(accountName, InfraAccountTypes.CAT_STORAGE);
		DummyInventoryCollector collector = new DummyInventoryCollector(nodeID);
		//next is to populate your collector with all the inventory items you are interested in collecting
		DummyInventoryItem interfaces = new DummyInventoryItem(FooConstants.INTERFACES, DummyInterface.class);
		DummyInventoryItem ports = new DummyInventoryItem(FooConstants.PORTS, DummyVLAN.class);
		//so as you can see here the inventory items consist of a string that i will use so i can determine
		//how to retrieve data for that type, and the pojo the data will be marshalled into
		collector.addItem(interfaces);
		collector.addItem(ports);
		//you need to add these items into your collector like so, this will make sure when collection is
		//kicked off, your code will iterate through these items so you can retrieve, parse, bind and persist
		//them.
		return collector;
	}

	//You need to specify the category to which this device collector belongs to.
	//In this example, my dummy collector will be a network type device.
	@Override
	public int getAccountCategory() {
		return CollectorFactory.STORAGE_CATEGORY;
	}

	@Override
	public ConvergedStackComponentBuilderIf getStackComponentBuilder() {
		return new DummyConvergedStackBuilder();
	}

	@Override
	public StackViewItemProviderIf getStackViewProvider() {
		return new DummyStackViewProvider();
	}

	@Override
	public InfraAccountConnectionTestHandlerIf getTestConnectionHandler() {
		return new GenericInfraAccountConnectionTestHandler() {
			
			@Override
			public boolean testConnectionTo(InfraAccount arg0, StringBuffer arg1) {
				return true;
			}
		};
	}

}
