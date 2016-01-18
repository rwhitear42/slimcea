package com.cloupia.feature.foo.reports;

import com.cloupia.feature.foo.charts.BarChartReport;
import com.cloupia.feature.foo.charts.LineChartReport;
import com.cloupia.feature.foo.charts.PieChartReport;
import com.cloupia.feature.foo.constants.FooConstants;
import com.cloupia.feature.foo.formReport.DummyFormReport;
import com.cloupia.feature.foo.heatmap.DummyHeatmapReport;
import com.cloupia.feature.foo.summary.DummySummaryReport;
import com.cloupia.model.cIM.InfraAccountTypes;
import com.cloupia.service.cIM.inframgr.collector.impl.GenericInfraAccountReport;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReport;

/**
 * This report will show under Physical -> Network in the UI.  By extending GenericInfraAccountReport,
 * you get all the necessary account management features for free.  So adding a new account for my dummy
 * collector, editing properties of the account, deleting, it is all taken care of by using GenericInfraAccountReport!
 *
 */
public class DummyAccountMgmtReport extends GenericInfraAccountReport {

	private static final String NAME = "DummyAccount";

	//SUPER IMPORTANT MAKE SURE THIS IS ONLY INSTANTIATED ONCE!!!!
	//this is the best way to declare what reports can be drilled down to from the dummy account mgmt report
	private CloupiaReport[] ddReports = new CloupiaReport[] {
			new DummyInterfacesReport(),
			new DummyVLANsReport(),
			new DummyHeatmapReport(),
			new LineChartReport(),
			new BarChartReport(),
			new PieChartReport(),
			new DummySummaryReport(),
			new DummyFormReport()
	};

	public DummyAccountMgmtReport() {
		super(NAME, FooConstants.DUMMY_ACCOUNT_TYPE, InfraAccountTypes.CAT_STORAGE);
		//you'll need to provide the a name for the report which will be shown in the UI
		//the account type you used when creating your collector
		//the category type you used when creating your collector
	}

	@Override
	public CloupiaReport[] getDrilldownReports() {
		//Warning: again make sure you DO NOT create new instances of this report, when your reports
		//are registered the framework uses whatever you pass in, so if you pass new instances each
		//time it won't recognize them and probably will not display them in the UI!!!
		return ddReports;
	}

}
