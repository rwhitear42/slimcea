package com.cloupia.feature.foo.charts;

import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.ReportNameValuePair;
import com.cloupia.model.cIM.SnapshotReport;
import com.cloupia.model.cIM.SnapshotReportCategory;
import com.cloupia.service.cIM.inframgr.SnapshotReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;

/**
 * This demonstrates how to actually implement your own bar chart.
 *
 */
public class BarChartReportImpl implements SnapshotReportGeneratorIf {
	
	private final int NUM_BARS = 2;
	private final String BAR_1 = "bar1";
	private final String BAR_2 = "bar2";

	@Override
	public SnapshotReport getSnapshotReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception {
		SnapshotReport report = new SnapshotReport();
        report.setContext(context);
        report.setReportName(reportEntry.getReportLabel());
        report.setNumericalData(true);
        report.setValueAxisName("Value Axis Name");
        report.setPrecision(0);
        
        //this section of code demonstrates how to build a bar chart containing
        //two categories and two bars in each category
        
        //this is my first set of data
        ReportNameValuePair[] rnv1 = new ReportNameValuePair[NUM_BARS];
        rnv1[0] = new ReportNameValuePair(BAR_1, 5);
        rnv1[1] = new ReportNameValuePair(BAR_2, 10);

        //you'll see i have a new category here called cat1 and i'm associating the dataset i just created along with it
        SnapshotReportCategory cat1 = new SnapshotReportCategory();
        cat1.setCategoryName("cat1");
        cat1.setNameValuePairs(rnv1);
        
        //just repeating what i demonstrated before, this should show you how to create a bar chart
        //with multiple categories though
        ReportNameValuePair[] rnv2 = new ReportNameValuePair[NUM_BARS];
        rnv2[0] = new ReportNameValuePair(BAR_1, 15);
        rnv2[1] = new ReportNameValuePair(BAR_2, 20);

        SnapshotReportCategory cat2 = new SnapshotReportCategory();
        cat2.setCategoryName("cat2");
        cat2.setNameValuePairs(rnv2);
        
        report.setCategories(new SnapshotReportCategory[] { cat1, cat2 });
        
		return report;
	}

}
