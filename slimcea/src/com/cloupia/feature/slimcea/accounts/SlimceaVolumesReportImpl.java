package com.cloupia.feature.slimcea.accounts;

import org.apache.log4j.Logger;

import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.TabularReport;
import com.cloupia.service.cIM.inframgr.TabularReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;
import com.cloupia.service.cIM.inframgr.reports.TabularReportInternalModel;

/**
 * This is an example demonstrating how to develop a report programmatically.  If you would rather
 * generate your report from scratch, this is how you would go about it.  This class is called when
 * a request to render the report is made, you need to compile all the data into a TabularReport
 * which will be returned to the UI for rednering.
 *
 */
public class SlimceaVolumesReportImpl implements TabularReportGeneratorIf {
	
	private static Logger logger = Logger.getLogger(SlimceaVolumesReportImpl.class);

	@Override
	public TabularReport getTabularReportReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception {
		
		TabularReport report = new TabularReport();

		report.setGeneratedTime(System.currentTimeMillis());
		report.setReportName(reportEntry.getReportLabel());
		report.setContext(context);
		
		TabularReportInternalModel model = new TabularReportInternalModel();

		model.addTextColumn("Name", "Name");
		model.addTextColumn("Size", "Size");
		model.addTextColumn("Volume Usage", "Volume Usage");
		model.addTextColumn("Snapshot Usage", "Snapshot Usage");
		model.addTextColumn("Total Usage", "Total Usage");
		model.completedHeader();

		model.addTextValue("russVol01");
		model.addTextValue("10.0 GB");
		model.addTextValue("0 B");
		model.addTextValue("0 B");
		model.addTextValue("0 B");
		model.completedRow();

		model.addTextValue("sql-1-db");
		model.addTextValue("10.0 GB");
		model.addTextValue("0 B");
		model.addTextValue("0 B");
		model.addTextValue("0 B");
		model.completedRow();

		model.addTextValue("sql-2-db");
		model.addTextValue("10.0 GB");
		model.addTextValue("0 B");
		model.addTextValue("0 B");
		model.addTextValue("0 B");
		model.completedRow();

		model.addTextValue("test");
		model.addTextValue("10.0 GB");
		model.addTextValue("0 B");
		model.addTextValue("0 B");
		model.addTextValue("0 B");
		model.completedRow();

		model.addTextValue("test1");
		model.addTextValue("25.0 GB");
		model.addTextValue("0 B");
		model.addTextValue("0 B");
		model.addTextValue("0 B");
		model.completedRow();

		model.updateReport(report);

		return report;
	}

}
