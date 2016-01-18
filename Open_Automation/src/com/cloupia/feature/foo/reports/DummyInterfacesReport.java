package com.cloupia.feature.foo.reports;

import com.cloupia.feature.foo.accounts.model.DummyInterface;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaEasyReportWithActions;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportAction;

/**
 * This is an example demonstrating how to generate a report using the POJO approach.
 * There isn't much to it, a lot of the work was already done in the DummyInterface POJO.  I already
 * added all the annotations to specify which fields should show in the report and what each column is named.
 * 
 * All you need to do here is make sure you extend CloupiaEasyReportWithActions, provide a unique name
 * for the report and the label for the report you want shown in the UI and the report POJO.
 */
public class DummyInterfacesReport extends CloupiaEasyReportWithActions {
	
	private static final String name = "foo.dummy.interface.report";
	private static final String label = "Dummy Interfaces";
	private static final Class dbSource = DummyInterface.class;

	public DummyInterfacesReport() {
		super(name, label, dbSource);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CloupiaReportAction[] getActions() {
		//I don't have any actions I want show in this report, so just return null
		return null;
	}

}
