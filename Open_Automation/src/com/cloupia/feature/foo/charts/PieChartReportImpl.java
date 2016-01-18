package com.cloupia.feature.foo.charts;

import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.ReportNameValuePair;
import com.cloupia.model.cIM.SnapshotReport;
import com.cloupia.model.cIM.SnapshotReportCategory;
import com.cloupia.service.cIM.inframgr.SnapshotReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;

/**
 * This demonstrates how to build your own pie chart.
 *
 */
public class PieChartReportImpl implements SnapshotReportGeneratorIf {
	
	@Override
	public SnapshotReport getSnapshotReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception {
		SnapshotReport report = new SnapshotReport();
        report.setContext(context);
        report.setReportName(reportEntry.getReportLabel());
        report.setNumericalData(true);
        report.setDisplayAsPie(true); //THIS IS IMPORTANT!!
        report.setPrecision(0);
   
        //pie charts generally can't handle more than ONE category, so i'm only making one category
        //my data set generated below for the pie chart is basically just 5 slices, each slice's value
        //is specified as (i+1) * 5
        ReportNameValuePair[] rnv = new ReportNameValuePair[5];

        for (int i = 0; i < rnv.length; i++)
        {
            rnv[i] = new ReportNameValuePair("category" + i, (i+1) * 5);
        }

        SnapshotReportCategory cat = new SnapshotReportCategory();
        cat.setCategoryName("");
        cat.setNameValuePairs(rnv);
        report.setCategories(new SnapshotReportCategory[] { cat });
        
		return report;
	}

}
