package com.cloupia.feature.foo.inventory;

import org.apache.log4j.Logger;

import com.cloupia.lib.cIaaS.network.model.DeviceIdentity;
import com.cloupia.service.cIM.inframgr.stackView.AbstractOANetworkStackViewProvider;

public class DummyStackViewProvider extends AbstractOANetworkStackViewProvider {
	
	private static Logger logger = Logger.getLogger(DummyStackViewProvider.class);

	@Override
	public String getBottomLabel(DeviceIdentity identity) {
		return "some other info";
	}

	@Override
	public String getTopLabel(DeviceIdentity identity) {
		return "dummyDevice";
	}

	@Override
	public boolean isApplicable(DeviceIdentity identity) {
		logger.info("fsfsfs");
		if (identity != null) {
			logger.info("fsfsfs: " + identity.getDatacenter());
			logger.info("fsfsfs: " + identity.getDeviceIp());
			//this is a dumb example, but there for testing, if the device ip
			//of the vm is exactly this, then we show this entry in the stack view
			//a real implementation would need to determine if it is truly applicable
			//given the account name and device ip for vm in question
			if (identity.getDeviceIp().equals("172.25.168.60")) {
				return true;
			}
		}
		return false;
	}

}
