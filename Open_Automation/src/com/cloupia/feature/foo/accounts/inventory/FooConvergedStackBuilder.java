package com.cloupia.feature.foo.accounts.inventory;

import java.util.ArrayList;
import java.util.List;

import com.cloupia.feature.foo.constants.FooConstants;
import com.cloupia.model.cIM.ConvergedStackComponentDetail;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.service.cIM.inframgr.reports.contextresolve.ConvergedStackComponentBuilderIf;

// Piece of shite.

/**
 * FooConvergedStackBuilder.java is the base class for providing account details in converged view.
 */
public class FooConvergedStackBuilder implements ConvergedStackComponentBuilderIf{
	
	/**
	 * Need to override this method providing account details to be shown in converged view.
	 * 
	 * @param : account context Id
	 * 
	 * @return: returns ConvergedStackComponentDetail instance
	 */
	@Override
	public ConvergedStackComponentDetail buildConvergedStackComponent(
			String contextId) throws Exception {
		ConvergedStackComponentDetail detail = new ConvergedStackComponentDetail();
		detail.setModel("dummyModel");
		detail.setOsVersion("1.0");
		detail.setVendorLogoUrl("/app/uploads/openauto/NimbleArray01_100x100.png");
		detail.setMgmtIPAddr("172.29.109.219");
		detail.setStatus("OK");
		detail.setIconUrl("/app/uploads/openauto/NimbleArray01_100x100.png");
		detail.setVendorName("dummyCorp");
		//setting account context type
		detail.setContextType(ReportContextRegistry.getInstance().getContextByName(FooConstants.INFRA_ACCOUNT_TYPE).getType());
		//setting context value that should be passed to report implementation
		detail.setContextValue(contextId);
		detail.setLayerType(3);
		detail.setComponentSummaryList(getSummaryReports());
		return detail;
	}

	private List<String> getSummaryReports()
			throws Exception {
		
		 List<String> rpSummaryList = new ArrayList<String>();
		 rpSummaryList.add("test");
		 rpSummaryList.add("test2");
				return rpSummaryList;
	
	}
}
