package com.cloupia.feature.slimcea.reports;

import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.model.cIM.DynReportContext;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.model.cIM.ReportDefinition;
import com.cloupia.service.cIM.inframgr.reportengine.ContextMapRule;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaNonTabularReport;

public class SampleBarChartReport extends CloupiaNonTabularReport {
	
	private static final String NAME = "slimcea.bar.chart.report"; 
	
	private static final String LABEL = "Slimcea Bar Chart";
	
	// returns the implementation class
	@SuppressWarnings("rawtypes")
	@Override
	public Class getImplementationClass() { 
		
		return SampleBarChartReportImpl.class;
		
	}
	
	//The below two methods are very important to shown as Bar chart in the GUI.
	//This method returns the report type for bar chart shown below.
	@Override
	public int getReportType() {

		return ReportDefinition.REPORT_TYPE_SNAPSHOT;
		
	}

	//This method returns the report hint for bar chart shown below
	@Override
	public int getReportHint() {
		
		return ReportDefinition.REPORT_HINT_BARCHART;

	}
	
	@Override
	public String getReportLabel() {
		return LABEL;
	}

	@Override
	public String getReportName() {
		return NAME;
	}
	
	// Forcing this report into the Physical->Storage part of the GUI.
	@Override
	public int getMenuID() {
		return 51; 
	}
	
	//bar charts will be display in summary if it returns true
	@Override
	public boolean showInSummary() {

		return true;
		
	}
	
	@Override
	public ContextMapRule[] getMapRules() {

		DynReportContext dummyContextOneType = ReportContextRegistry.getInstance().getContextByName(SlimceaConstants.INFRA_ACCOUNT_TYPE);
		
		ContextMapRule rule = new ContextMapRule();
		rule.setContextName(dummyContextOneType.getId());
		rule.setContextType(dummyContextOneType.getType());

		ContextMapRule[] rules = new ContextMapRule[1];
		rules[0] = rule;
		
		return rules;
	}

}