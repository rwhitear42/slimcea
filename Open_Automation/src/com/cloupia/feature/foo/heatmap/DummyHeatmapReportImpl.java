package com.cloupia.feature.foo.heatmap;

import java.util.ArrayList;
import java.util.List;

import com.cloupia.model.cIM.HeatMapCell;
import com.cloupia.model.cIM.HeatMapReport;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.service.cIM.inframgr.HeatMapReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;

/**
 * This demonstrates how to build your own heatmap.
 *
 */
public class DummyHeatmapReportImpl implements HeatMapReportGeneratorIf {

	@Override
	public HeatMapReport getHeatMapReportReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception {
		//long beginTime = System.currentTimeMillis();
		HeatMapReport report = new HeatMapReport();

		report.setUnUsedChildSize(0.0);

		report.setSizeLabel("Size Label");
		report.setSizeUnits("Size Units");
		report.setValueLabel("Value Label");
		report.setValueUnits("Value Units");
		report.setValueLowerBound(0);
		report.setValueUpperBound(100);

		List<HeatMapCell> cells = new ArrayList<HeatMapCell>();

		//it'll be easier to load this example and compare the code to the UI to make this easier to understand
		//but i will describe what this code is doing for the sake of completeness
		//in this dataset, i'm creating 3 sections in the heatmap (the parents), inside each section i'm "splitting"
		//it further into 4 equal sections (the children), as you can see where i set size to 25.
		//you can continue to break these sections into other sections and so on.
		
		//create parent cells
		for (int i=0; i<3; i++) {
			String parentName = "parent" + i;
			HeatMapCell root = new HeatMapCell();
			root.setLabel(parentName);
			root.setUnUsedChildSize(0.0);
			//create child cells within parent cell
			HeatMapCell[] childCells = new HeatMapCell[4];
			for (int j=0; j<4; j++) {
				HeatMapCell child = new HeatMapCell();
				child.setLabel(parentName + "child" + j);
				child.setValue((j+1)*25); //sets color, the color used for each section is relative, there is a scale in the UI
				child.setSize(25);	//sets weight
				childCells[j] = child;
			}
			root.setChildCells(childCells);
			cells.add(root);
		}

		HeatMapCell[] chArray = new HeatMapCell[cells.size()];
		cells.toArray(chArray);
		report.setCells(chArray);

		return report;
	}

}
