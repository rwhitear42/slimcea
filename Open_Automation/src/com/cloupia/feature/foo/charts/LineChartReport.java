package com.cloupia.feature.foo.charts;

import com.cloupia.model.cIM.ReportDefinition;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaNonTabularReport;

/**
 * This is the first part of developing a line chart report.  The second part you should
 * look in LineChartReportImpl.java.  For the most part everything resembles a plain tabular
 * report but there are a couple of important things to mention:
 * 
 * 1. Extend CloupiaNonTabularReport.
 * 2. getReportType need to return REPORT_TYPE_HISTORICAL
 *
 */
public class LineChartReport extends CloupiaNonTabularReport {
	
	private static final String NAME = "foo.dummy.line.chart.report";
	private static final String LABEL = "Dummy Line Chart";

	@Override
	public Class getImplementationClass() {
		return LineChartReportImpl.class;
	}
	
	@Override
	public String getReportLabel() {
		return LABEL;
	}

	@Override
	public String getReportName() {
		return NAME;
	}

	@Override
	public boolean isEasyReport() {
		return false;
	}

	@Override
	public boolean isLeafReport() {
		return true;
	}
	
	@Override
	public int getReportType() {
		return ReportDefinition.REPORT_TYPE_HISTORICAL;
	}

}
