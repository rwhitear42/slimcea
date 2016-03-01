package com.cloupia.feature.foo.inventory;

// Piece of shite.

import com.cloupia.feature.foo.constants.FooConstants;
import com.cloupia.model.cIM.InfraAccountTypes;
import com.cloupia.service.cIM.inframgr.reports.contextresolve.AbstractConvergedStackComponentBuilder;

/**
 * In order to properly display your device in the Converged tab of the UI, you need to provide 3 components.
 * 1. the code portion, as shown here. 
 * 2. the xml portion, look at device_icon_mapping.xml. 
 * 3. the image files to be used, look in the resources folder.
 * The easiest way to handle number 1 is to use this AbstractConvergedStackComponentBuilder which handles
 * everything for you and only requires that you provide a couple of details.
 *
 */
public class DummyConvergedStackBuilder extends AbstractConvergedStackComponentBuilder {

	public DummyConvergedStackBuilder() {
		super(FooConstants.DUMMY_ACCOUNT_TYPE, InfraAccountTypes.CAT_STORAGE);
		//it's really important you make sure you are use the same account type and category you
		//used in your collector!
	}

	@Override
	public String getModel() {
		//you can retrieve the account name this stack builder is being used for at the moment by calling
		//getIdentifier
		String accountName = this.getIdentifier();
		if (accountName != null) {
			return "dummyModel";
		}
		//make sure this string matches exactly with the DeviceType field in the device_icon_mapping.xml.
		return "dummyModel";
	}

	@Override
	public String getOSVersion() {
		return "dummyOS";
	}

	@Override
	public String getVendor() {
		//make sure this string matches exactly with the Vendor field in the device_icon_mapping.xml.
		return "dummyCorp";
	}

	@Override
	public String getVendorImageName() {
		//make sure this string matches exactly with the file name of the image you placed in the resources folder.
		return "dummy_logo.png";
	}

}
