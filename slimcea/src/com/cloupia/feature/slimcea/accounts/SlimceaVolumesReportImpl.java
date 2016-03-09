package com.cloupia.feature.slimcea.accounts;

import org.apache.log4j.Logger;

import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;
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
		
		String accountName = "";
		String contextId = context.getId();
		logger.info("###### Context at System Task report#####: "+contextId);
		if(contextId != null)
			//As the contextId returns as: "account Name;POD Name"
			accountName = contextId.split(";")[0];
		
        PhysicalInfraAccount acc = AccountUtil.getAccountByName(accountName);
        if (acc == null)
        {
            throw new Exception("Unable to find the account:" + accountName);
        }
		
		
		TabularReportInternalModel model = new TabularReportInternalModel();

		model.addTextColumn("Account Name", "Account Name");
		model.addTextColumn("Username", "Username");
		model.addTextColumn("Password", "Password");
		model.addTextColumn("IP", "IP");
		model.addTextColumn("Name", "Name");
		model.addTextColumn("Size", "Size");
		model.addTextColumn("Volume Usage", "Volume Usage");
		model.addTextColumn("Snapshot Usage", "Snapshot Usage");
		model.addTextColumn("Total Usage", "Total Usage");
		model.completedHeader();

		model.addTextValue("accountName");
		model.addTextValue("login");
		model.addTextValue("password");
		model.addTextValue("deviceIp");
		model.addTextValue("russVol01");
		model.addTextValue("10.0 GB");
		model.addTextValue("0 B");
		model.addTextValue("0 B");
		model.addTextValue("0 B");
		model.completedRow();

		model.updateReport(report);

		return report;
	}

}
