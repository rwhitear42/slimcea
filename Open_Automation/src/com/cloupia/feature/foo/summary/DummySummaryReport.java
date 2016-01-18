package com.cloupia.feature.foo.summary;

import com.cloupia.model.cIM.ReportDefinition;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaNonTabularReport;

/**
 * This is the first half showing you how to build your own summary report.  The second half is in
 * DummySummaryReportImpl.java.  This looks like a normal tabular report with a couple of
 * important differences to mention:
 * 
 * 1. Extend CloupiaNonTabularReport
 * 2. getReportType returns REPORT_TYPE_SUMMARY
 * 3. getReportHint returns REPORT_HINT_VERTICAL_TABLE_WITH_GRAPHS
 * 4. isManagementReport returns true
 *
 */
public class DummySummaryReport extends CloupiaNonTabularReport {
	
	private static final String NAME = "foo.dummy.summary.report";
	private static final String LABEL = "Dummy Summary";

	/**
	 * This method returns implementation class of the summary report
	 * @return implementation class
	 */
	@Override
	public Class getImplementationClass() {
		return DummySummaryReportImpl.class;
	}
	
	/**
	 * This method returns the report label to be display in UI
	 * @return label of report
	 */
	@Override
	public String getReportLabel() {
		return LABEL;
	}

	/**
	 * @return This method returns report name ,each report should have unique name
	 */
	@Override
	public String getReportName() {
		return NAME;
	}

	/**
	 * @return This report returns boolean value true/false. it returns false if report is not easy(report has implementation class)
	 */
	@Override
	public boolean isEasyReport() {
		return false;
	}

	/**
	 * @return This report returns boolean value true/false. Returns true if it is leaf report otherwise it returns false
	 */
	@Override
	public boolean isLeafReport() {
		return true;
	}
	
	/**
	 * @return This method returns type of report like summary/pie chart/Line chart/tabular etc
	 */
	@Override
	public int getReportType() {
		return ReportDefinition.REPORT_TYPE_SUMMARY;
	}
	
	/**
	 * @return This method returns type of report 
	 */
	@Override
	public int getReportHint()
	{
		return ReportDefinition.REPORT_HINT_VERTICAL_TABLE_WITH_GRAPHS;
	}
	
	/**
	 * @return This method returns boolean value true/false. if it returns true then only form will be shown in UI
	 */
	@Override
	public boolean isManagementReport()
	{
		return true;
	}

}
