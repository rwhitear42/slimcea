package com.cloupia.feature.slimcea.constants;


public class SlimceaConstants {

	public static final String DEVICE_LIST_PROVIDER = "slimcea_DeviceListProvider";
	public static final String NEXUS_DEVICE_TABLE = "slimcea_DeviceTable";
	
	public static final String TEMP_EMAIL_ADDRESSES = "slimcea_email_address_list";
    public static final String NEXUS_DEVICE_LIST = "slimcea_nexus_device_list";
   
    public static final String EMAIL_TASK_OUTPUT_NAME = "Datacenter Email Addresses";
    public static final String EMAIL_TASK_OUTPUT_TYPE = "e-mail-as-string";
    
	public static final String NEXUS_DEVICES_LOV_PROVIDER = "NetworkDeviceList";

	public static final String slimcea_HELLO_WORLD_NAME = "slimcea_name_from_other_tasks";
	
	public static final String slimcea_MULTI_SELECT_TABULARVALUE_NAME = "SampleTabulerMultiInput";
	
	//this is the unique integer i'm giving for my dummy collector, it's a good idea
	//to use some large number past 1000 so you avoid any potential collisions
	public static final int DUMMY_ACCOUNT_TYPE = 6000;
	
	//some dummy strings used to represent inventory items
	public static final String INTERFACES = "interfaces";
	public static final String PORTS = "ports";
	
	public static final String DUMMY_INVENTORY_COLLECTOR_NAME = "Dummy_Inventory_Collector";
	
	public static final String DUMMY_VLAN_RESOURCE_TYPE = "slimcea.vlan.per.group.usage";
	public static final String DUMMY_VLAN_RESOURCE_DESC = "Max Dummy VLANs per group";
	
	public static final int DUMMY_MENU_1 = 11001;
	
	public static final String DUMMY_CONTEXT_ONE = "slimcea.dummy.context.one";
	public static final String DUMMY_CONTEXT_ONE_LABEL = "Dummy Context One";
	
	public static final String INFRA_ACCOUNT_LABEL = "slimcea Account";
	public static final String INFRA_ACCOUNT_TYPE = "slimcea Account";
	
	public static final String slimcea_ACCOUNT_DRILLDOWN_NAME = "slimcea.account.sample.child.drilldown.report";
	public static final String slimcea_ACCOUNT_DRILLDOWN_LABEL = "slimcea Account Drilldown Sample";

}