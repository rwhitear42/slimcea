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
public class SlimceaVolumeCollectionsReportImpl implements TabularReportGeneratorIf {
	
	private static Logger logger = Logger.getLogger(SlimceaVolumeCollectionsReportImpl.class);

	@Override
	public TabularReport getTabularReportReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception {
		
		TabularReport report = new TabularReport();

		report.setGeneratedTime(System.currentTimeMillis());
		report.setReportName(reportEntry.getReportLabel());
		report.setContext(context);
		
		TabularReportInternalModel model = new TabularReportInternalModel();

		model.addTextColumn("Volume Collection", "Volume Collection");
		model.addTextColumn("Synchronization", "Synchronization");
		model.addTextColumn("Last Snapshot", "Last Snapshot");
		model.addTextColumn("Replication Partner", "Replication Partner");
		model.addTextColumn("Last Replication", "Last Replication");
		model.addTextColumn("Total Snapshots to Retain", "Total Snapshots to Retain");
		model.completedHeader();

		model.addTextValue("myVolColl");
		model.addTextValue("None");
		model.addTextValue("Unknown");
		model.addTextValue("No replication partner");
		model.addTextValue("Unknown");
		model.addTextValue("48");
		model.completedRow();

		model.addTextValue("russVolColl");
		model.addTextValue("None");
		model.addTextValue("2016-03-02 10:00");
		model.addTextValue("No replication partner");
		model.addTextValue("Unknown");
		model.addTextValue("48");
		model.completedRow();

		model.updateReport(report);

		return report;
	}

}
