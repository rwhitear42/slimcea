package com.cloupia.feature.foo.charts;

import com.cloupia.model.cIM.ReportDefinition;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaNonTabularReport;

/**
 * This is the first part of developing a bar chart report.  The second part you should
 * look in BarChartReportImpl.java.  For the most part everything resembles a plain tabular
 * report but there are a couple of important things to mention:
 * 
 * 1. Extend CloupiaNonTabularReport.
 * 2. getReportType need to return REPORT_TYPE_SNAPSHOT
 * 3. getReportHint need to return REPORT_HINT_BARCHART
 *
 */
public class BarChartReport extends CloupiaNonTabularReport {
	
	private static final String NAME = "foo.dummy.bar.chart.report";
	private static final String LABEL = "Dummy Bar Chart";
	
   /**
    * @return BarChartReport implementation class type 
    */
	@Override
	public Class getImplementationClass() {
		return BarChartReportImpl.class;
	}
	/**
	 * @return Report label
	 */
	@Override
	public String getReportLabel() {
		return LABEL;
	}
    /**
     * @return report name
     */
	@Override
	public String getReportName() {
		return NAME;
	}
    /**
     * @return true only if the report implementation followed POJO approach 
     */
	@Override
	public boolean isEasyReport() {
		return false;
	}
    /**
     * @return true if the report does not have any drillDown report
     */
	@Override
	public boolean isLeafReport() {
		return true;
	}
	/** 
	 * @return report type like tabular report,snapshot report ,summary report etc.
	 */
	@Override
	public int getReportType() {
		return ReportDefinition.REPORT_TYPE_SNAPSHOT;
	}
	/** 
	 * @return report hint
	 */
	@Override
	public int getReportHint()
	{
		return ReportDefinition.REPORT_HINT_BARCHART;
	}
	
	/**
	*@return true if you want this chart to show up in a summary report
	*/
	@Override
	public boolean showInSummary()
	{
		return true;
	}

}
