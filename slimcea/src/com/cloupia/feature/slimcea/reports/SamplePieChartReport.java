package com.cloupia.feature.slimcea.reports;

import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.model.cIM.DynReportContext;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.model.cIM.ReportDefinition;
import com.cloupia.service.cIM.inframgr.reportengine.ContextMapRule;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaNonTabularReport;

public class SamplePieChartReport extends CloupiaNonTabularReport {
	
	// Unique report name here.
	private static final String NAME = "my.first.pie.chart";
	
	private static final String LABEL = "Who Makes The Best Storage Array?";

	
	//Returns implementation class
	@SuppressWarnings("rawtypes")
	@Override
	public Class getImplementationClass() { 
		
		return SamplePieChartReportImpl.class;
		
	}
	
	//Returns report type for pie chart as shown below
	@Override
	public int getReportType() {
	
		return ReportDefinition.REPORT_TYPE_SNAPSHOT;
	
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
	
	//Returns report hint for pie chart as shown below
	@Override
	public int getReportHint() {
	
		return ReportDefinition.REPORT_HINT_PIECHART;
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