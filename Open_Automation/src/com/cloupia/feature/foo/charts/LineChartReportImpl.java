package com.cloupia.feature.foo.charts;

import com.cloupia.model.cIM.DataSample;
import com.cloupia.model.cIM.HistoricalDataSeries;
import com.cloupia.model.cIM.HistoricalReport;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.service.cIM.inframgr.HistoricalReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;

/**
 * This demonstrates how to implement your own line chart.
 *
 */
public class LineChartReportImpl implements HistoricalReportGeneratorIf {

	@Override
	public HistoricalReport generateReport(ReportRegistryEntry reportEntry, ReportContext repContext,
            String durationName, long fromTime, long toTime)
			throws Exception {
		//set basic properties of the report
		HistoricalReport report = new HistoricalReport();
        report.setContext(repContext);
        report.setFromTime(fromTime);
        report.setToTime(toTime);
        report.setDurationName(durationName);
        report.setReportName(reportEntry.getReportLabel());
        
        //specifies we will show two lines in this report
        int numLines = 2;
        HistoricalDataSeries[] hdsList = new HistoricalDataSeries[numLines];
        
        //create the first line
        HistoricalDataSeries line1 = new HistoricalDataSeries();
        line1.setParamLabel("param1");
        line1.setPrecision(0); //number of decimal points to show if y values are not integers

        DataSample[] dataset1 = createDataset1(fromTime, toTime);
        line1.setValues(dataset1);
        
        //create the second line
        HistoricalDataSeries line2 = new HistoricalDataSeries();
        line2.setParamLabel("param2");
        line2.setPrecision(0); //number of decimal points to show if y values are not integers

        DataSample[] dataset2 = createDataset2(fromTime, toTime);
        line2.setValues(dataset2);
        
        hdsList[0] = line1;
        hdsList[1] = line2;
        
        report.setSeries(hdsList);
        
		return report;
	}
	
	//create a super simple dataset which is just y = 5 * x, values along x axis are timestamps, which we just
	//divide into 5 points between start and end.
	private DataSample[] createDataset1(long start, long end) {
		long interval = (end - start) / 5;
		long timestamp = start;
		double yValue = 1.0;
		DataSample[] dataset = new DataSample[5];
		for (int i=0; i<dataset.length; i++) {
			DataSample data = new DataSample();
			
			//the x value is timestamp
			//the y value is avg
			data.setTimestamp(timestamp);
			data.setAvg(yValue);
			
			timestamp += interval;
			yValue += 5.0;
			
			dataset[i] = data;
		}
		return dataset;
	}

	//create a super simple dataset which is just y = 1, values along x axis are timestamps, which we just
	//divide into 5 points between start and end.
	private DataSample[] createDataset2(long start, long end) {
		long interval = (end - start) / 5;
		long timestamp = start;
		double yValue = 1.0;
		DataSample[] dataset = new DataSample[5];
		for (int i=0; i<dataset.length; i++) {
			DataSample data = new DataSample();
			data.setTimestamp(timestamp);
			data.setAvg(yValue);
			
			timestamp += interval;
			
			dataset[i] = data;
		}
		return dataset;
	}

}
