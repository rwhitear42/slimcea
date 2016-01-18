package com.cloupia.feature.foo.formReport;

import com.cloupia.model.cIM.ReportDefinition;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaNonTabularReport;

/**
 * This demonstrates how you can utilize the form framework to build a form in the space of a report.
 * We call forms that consume the space of an entire tab in the UI (normally reserved for reports), config forms.
 * This looks like very similar to a report action but there are a couple of important things to notice:
 * 
 * 1. Extend CloupiaNonTabularReport
 * 2. getReportType returns REPORT_TYPE_CONFIG_FORM
 * 3. isManagementReport returns true
 *
 */
public class DummyFormReport extends CloupiaNonTabularReport {
	
	private static final String NAME = "foo.dummy.form.report";
	private static final String LABEL = "Dummy Form Report";

	@Override
	public Class getImplementationClass() {
		return DummyFormReportAction.class;
	}
	
	@Override
	public String getReportLabel() {
		return LABEL;
	}

	@Override
	public String getReportName() {
		return NAME;
	}

	//If your form implementation uses CloupiaReportAction, then you need to specify it is an easy report
	//otherwise, the framework assumes you know what you are doing and are building a form with our low level APIs!
	@Override
	public boolean isEasyReport() {
		return true;
	}

	@Override
	public boolean isLeafReport() {
		return true;
	}
	
	//Make sure to return the proper report type
	@Override
	public int getReportType() {
		return ReportDefinition.REPORT_TYPE_CONFIG_FORM;
	}
	
	//Make sure this returns true, if you return false, the UI will not show your form!
	@Override
	public boolean isManagementReport()
	{
		return true;
	}

}
