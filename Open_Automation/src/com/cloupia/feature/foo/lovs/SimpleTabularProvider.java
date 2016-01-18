package com.cloupia.feature.foo.lovs;

import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.TabularReport;
import com.cloupia.service.cIM.inframgr.TabularReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;
import com.cloupia.service.cIM.inframgr.reports.TabularReportInternalModel;

public class SimpleTabularProvider implements TabularReportGeneratorIf {

	public static final String SIMPLE_TABULAR_PROVIDER = "foo_tabular_provider";
	
	private static final int NUM_ROWS = 8;

	@Override
	public TabularReport getTabularReportReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception {
		TabularReport report = new TabularReport();

		report.setGeneratedTime(System.currentTimeMillis());
		report.setReportName(reportEntry.getReportLabel());
		report.setContext(context);

		TabularReportInternalModel model = new TabularReportInternalModel();
		model.addTextColumn("Name", "Name");
		model.addTextColumn("Value", "Value");
		model.completedHeader();

		for (int i=0; i<NUM_ROWS; i++)
		{
			model.addTextValue("name" + i);
			model.addTextValue("value" + i);
			model.completedRow();
		}

		model.updateReport(report);

		return report;
	}

}

