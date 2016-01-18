package com.cloupia.feature.foo.reports;

import com.cloupia.feature.foo.actions.AssignDummyVLANToGroupAction;
import com.cloupia.feature.foo.actions.SimpleDummyAction;
import com.cloupia.service.cIM.inframgr.reportengine.ContextMapRule;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportAction;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportWithActions;

/**
 * This example demonstrates how to develop a report programmatically.  
 * I'm extending CloupiaReportWithActions, this is the base class you should use for programmatic reports.
 * I'm providing my own implementation class, take a look in DummyVLANsReportImpl.java for more detail.
 *
 */
public class DummyVLANsReport extends CloupiaReportWithActions {
	
	private static final String NAME = "foo.dummy.vlans.report";
	private static final String LABEL = "Dummy VLANs";
	
	public DummyVLANsReport() {
		super();
		//IMPORTANT: this tells the framework which column of this report you want to pass as the report context id
		//when there is a UI action being launched in this report
		this.setMgmtColumnIndex(1);

		//to view the report in the location you wanted it to be.
		ContextMapRule map = new ContextMapRule();
		map.setContextName("hostnode1");
		map.setContextType(40);
		
		ContextMapRule[] rules = new ContextMapRule[1];
		rules[0] = map;
		
		this.setMapRules(rules);
	}

	@Override
	public CloupiaReportAction[] getActions() {
		AssignDummyVLANToGroupAction vlanAction = new AssignDummyVLANToGroupAction();
		SimpleDummyAction dummyAction = new SimpleDummyAction();
		CloupiaReportAction[] actions = new CloupiaReportAction[2];
		actions[0] = vlanAction;
		actions[1] = dummyAction;
		return actions;
	}

	@Override
	public Class getImplementationClass() {
		//this class handles all the report generation logic, look here for more details
		return DummyVLANsReportImpl.class;
	}

	@Override
	public String getReportLabel() {
		return LABEL;
	}

	@Override
	public String getReportName() {
		return NAME;
	}

	//this is important, make sure this returns false.  isEasyReport should ONLY return
	//true if and only if you're using the POJO approach, which we aren't in this case.
	@Override
	public boolean isEasyReport() {
		return false;
	}

	//make sure to return true in this case, as there are no drill down reports
	@Override
	public boolean isLeafReport() {
		return true;
	}

}
