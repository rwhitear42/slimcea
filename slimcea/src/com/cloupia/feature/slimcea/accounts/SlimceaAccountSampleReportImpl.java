package com.cloupia.feature.slimcea.accounts;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Logger;

import com.cloupia.fw.objstore.ObjStore;
import com.cloupia.fw.objstore.ObjStoreHelper;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.TabularReport;
import com.cloupia.service.cIM.inframgr.TabularReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;
import com.cloupia.service.cIM.inframgr.reports.TabularReportInternalModel;

/**
 * This is an example demonstrating how to develop a report programmatically. If
 * you would rather generate your report from scratch, this is how you would go
 * about it. This class is called when a request to render the report is made,
 * you need to compile all the data into a TabularReport which will be returned
 * to the UI for rednering.
 * 
 */
public class SlimceaAccountSampleReportImpl implements TabularReportGeneratorIf {

	private static Logger logger = Logger
			.getLogger(SlimceaAccountSampleReportImpl.class);

	@Override
	public TabularReport getTabularReportReport(
			ReportRegistryEntry reportEntry, ReportContext context)
			throws Exception {

		TabularReport report = new TabularReport();

		report.setGeneratedTime(System.currentTimeMillis());
		report.setReportName(reportEntry.getReportLabel());
		report.setContext(context);
		/**
		 * This is dummy account retrieval from database. Data populating from
		 * DummyAccountData for this account.
		 */
		ObjStore<DummyAccount> dummyAssignStore = ObjStoreHelper
				.getStore(DummyAccount.class);
		List<DummyAccount> objs = dummyAssignStore.queryAll();

		TabularReportInternalModel model = new TabularReportInternalModel();

		model.addTextColumn("Account Name", "Account Name");
		model.addTextColumn("IP Address", "IP Address");
		model.addTextColumn("Status", "Status");
		model.completedHeader();

		for (int i = 0; i < objs.size(); i++) {
			DummyAccount pojo = objs.get(i);
			model.addTextValue(pojo.getAccountName());
			model.addTextValue(pojo.getIp());
			model.addTextValue(pojo.getStatus());
			model.completedRow();
		}

		model.updateReport(report);

		return report;
	}

}
