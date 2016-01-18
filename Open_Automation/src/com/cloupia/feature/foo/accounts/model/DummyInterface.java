package com.cloupia.feature.foo.accounts.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.lib.easyui.annotations.ReportField;
import com.cloupia.model.cIM.InventoryDBItemIf;
import com.cloupia.service.cIM.inframgr.reports.simplified.ReportableIf;

/**
 * There are several things to note about this POJO, it's doing several things so I will detail it here.
 * 1. There are JDO annotations here, so this POJO can be persisted into the DB.
 * 2. It implements InventoryDBItemIf, which is the interface you should implement if you want to persist an
 * inventory item into DB.  This is to ensure you have an account name field in the POJO.
 * 3. It implements ReportableIf, this means this POJO is also used as a data source for a report, so you
 * can create a new report simply using this POJO.
 * 4. It is also used for binding, if you'll recall in DummyCollectorFactory, this class is passed in as the
 * bindable model.  So the data I get from my dummy device will be converted into an instance of this POJO.
 *
 */
@PersistenceCapable(detachable = "true")
public class DummyInterface implements InventoryDBItemIf, ReportableIf {
	
	//it's really important you have a field tracking account name, because you will need this to
	//determine whether this instance belongs to a certain account or not
	@Persistent
	private String accountName;
	
	//The ReportField annotation is used to tag a particular field as a column to show in a report.
	//This is an easy way leverage an existing model POJO to display as a report in the UI.
	@ReportField(label="Name")
	@Persistent
 	private String name;
	
	@ReportField(label="MTU")
	@Persistent
 	private int mtu;
	
	@ReportField(label="Status")
	@Persistent
 	private String status;
	
	@ReportField(label="IP Address")
	@Persistent
 	private String ip;
	
	@ReportField(label="MAC Address")
	@Persistent
 	private String mac;
	
	@ReportField(label="Subnet Mask")
	@Persistent
 	private String mask;

	@Override
	public String getAccountName() {
		return accountName;
	}

	@Override
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMtu() {
		return mtu;
	}

	public void setMtu(int mtu) {
		this.mtu = mtu;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	//If you want to develop a report using a POJO as the data source, you NEED to implement the ReportableIf
	//interface.  You need to provide an implementation of this getInstanceQuery which is used to determine
	//which rows in the DB to return.  In this case, when my report is displayed, I want to show all the rows
	//for a specific account.  You can do this with the query below.  This is one example why I want to have
	//an account name field in my inventory POJOs!
	@Override
	public String getInstanceQuery() {
		return "accountName == '" + this.accountName + "'";
	}

}
