package com.cloupia.feature.foo.summary;

import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.TabularReport;
import com.cloupia.service.cIM.inframgr.TabularReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;
import com.cloupia.service.cIM.inframgr.reports.SummaryReportInternalModel;

/**
 * This demonstrates how to build your own summary report(Tabular report).
 *
 */
public class DummySummaryReportImpl implements TabularReportGeneratorIf {
	
	private static final String DUMMY_TABLE_ONE = "Dummy Table One";
	private static final String DUMMY_TABLE_TWO = "Dummy Table Two";
	
	private static final String[] GROUP_ORDER = { DUMMY_TABLE_ONE, DUMMY_TABLE_TWO };

	/**
	 * This method returns implemented tabular report,and also perform cleanup process and updating report. 
	 * 
	 * @param reportEntry
	 *             This parameter contains Object of  ReportRegistryEntry class which is used to register newly created report
	 * @param context
	 *           This parameter contains context of the report
	 * @return report
	 */
	@Override
	public TabularReport getTabularReportReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception {
		TabularReport report = new TabularReport();
        report.setContext(context);
        report.setGeneratedTime(System.currentTimeMillis());
        report.setReportName(reportEntry.getReportLabel());

        //showing how to add two tables to your summary panel
        //the tables in summary panel are always two column tables
        SummaryReportInternalModel model = new SummaryReportInternalModel();        

        //this will be my first table
        //two rows, column one and column two values shown, note the last value
        //this is how you group what data is grouped together
        model.addText("table one key one", "table one property one", DUMMY_TABLE_ONE);
        model.addText("table one key two", "table one property two", DUMMY_TABLE_ONE);
        
        model.addText("table two key one", "table two property one", DUMMY_TABLE_TWO);
        model.addText("table two key two", "table two property two", DUMMY_TABLE_TWO);
        
        //you'll notice the charts that show in summary panels aren't mentioned here
        //that's because you'll need to specify in the chart report whether or not the
        //chart should be displayed in the summary panel or not, look in the BarChartReport
        //example for more detail
        
        //finally perform last clean up steps
        model.setGroupOrder(GROUP_ORDER);
        model.updateReport(report);
        
        return report;
	}

}
