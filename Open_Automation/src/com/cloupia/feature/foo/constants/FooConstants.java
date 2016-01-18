package com.cloupia.feature.foo.constants;


public class FooConstants {

	public static final String DEVICE_LIST_PROVIDER = "foo_DeviceListProvider";
	public static final String NEXUS_DEVICE_TABLE = "foo_DeviceTable";
	
	public static final String TEMP_EMAIL_ADDRESSES = "foo_email_address_list";
    public static final String NEXUS_DEVICE_LIST = "foo_nexus_device_list";
   
    public static final String EMAIL_TASK_OUTPUT_NAME = "Datacenter Email Addresses";
    public static final String EMAIL_TASK_OUTPUT_TYPE = "e-mail-as-string";
    
	public static final String NEXUS_DEVICES_LOV_PROVIDER = "NetworkDeviceList";

	public static final String FOO_HELLO_WORLD_NAME = "foo_name_from_other_tasks";
	
	public static final String FOO_MULTI_SELECT_TABULARVALUE_NAME = "SampleTabulerMultiInput";
	
	//this is the unique integer i'm giving for my dummy collector, it's a good idea
	//to use some large number past 1000 so you avoid any potential collisions
	public static final int DUMMY_ACCOUNT_TYPE = 6000;
	
	//some dummy strings used to represent inventory items
	public static final String INTERFACES = "interfaces";
	public static final String PORTS = "ports";
	
	public static final String DUMMY_INVENTORY_COLLECTOR_NAME = "Dummy_Inventory_Collector";
	
	public static final String DUMMY_VLAN_RESOURCE_TYPE = "foo.vlan.per.group.usage";
	public static final String DUMMY_VLAN_RESOURCE_DESC = "Max Dummy VLANs per group";
	
	public static final int DUMMY_MENU_1 = 11001;
	
	public static final String DUMMY_CONTEXT_ONE = "foo.dummy.context.one";
	public static final String DUMMY_CONTEXT_ONE_LABEL = "Dummy Context One";
	
	public static final String INFRA_ACCOUNT_LABEL = "Foo Account";
	public static final String INFRA_ACCOUNT_TYPE = "Foo Account";
	
	public static final String FOO_ACCOUNT_DRILLDOWN_NAME = "foo.account.sample.child.drilldown.report";
	public static final String FOO_ACCOUNT_DRILLDOWN_LABEL = "Foo Account Drilldown Sample";

}
