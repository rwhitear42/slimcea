package com.cloupia.feature.foo.heatmap;

import com.cloupia.model.cIM.ReportDefinition;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaNonTabularReport;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportAction;

/**
 * This is the first half showing you how to build your own heat map.  The second half is in
 * DummyHeatmapReportImpl.java.  This looks like a normal tabular report with a couple of
 * important differences to mention:
 * 
 * 1. Extend CloupiaNonTabularReport
 * 2. getReportType returns REPORT_TYPE_HEATMAP
 *
 */
public class DummyHeatmapReport extends CloupiaNonTabularReport {
	
	private static final String NAME = "foo.dummy.heatmap.report";
	private static final String LABEL = "Dummy Heatmap";
	
	public DummyHeatmapReport() {
		super();
	}

	@Override
	public Class getImplementationClass() {
		//this class handles all the report generation logic, look here for more details
		return DummyHeatmapReportImpl.class;
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
		return ReportDefinition.REPORT_TYPE_HEATMAP;
	}

}
