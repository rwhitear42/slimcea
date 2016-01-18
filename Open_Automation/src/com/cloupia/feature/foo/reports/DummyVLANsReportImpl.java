package com.cloupia.feature.foo.reports;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.cloupia.feature.foo.accounts.model.DummyVLAN;
import com.cloupia.feature.foo.actions.AssignDummyVLANToGroupForm;
import com.cloupia.fw.objstore.ObjStore;
import com.cloupia.fw.objstore.ObjStoreHelper;
import com.cloupia.model.cIM.Group;
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
public class DummyVLANsReportImpl implements TabularReportGeneratorIf {
	
	private static Logger logger = Logger.getLogger(DummyVLANsReportImpl.class);

	@Override
	public TabularReport getTabularReportReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception {
		TabularReport report = new TabularReport();

		report.setGeneratedTime(System.currentTimeMillis());
		report.setReportName(reportEntry.getReportLabel());
		report.setContext(context);
		//logger.info("context id =" + context.getId());
		
		//here i'm just retrieving all the objects that track dummy vlan to group associations and putting them
		//into a hashmap for easy look up
		ObjStore<AssignDummyVLANToGroupForm> dummyAssignStore = ObjStoreHelper.getStore(AssignDummyVLANToGroupForm.class);
		List<AssignDummyVLANToGroupForm> dummyAssignList = dummyAssignStore.queryAll();
		HashMap<String, AssignDummyVLANToGroupForm> dummyMap = new HashMap<String, AssignDummyVLANToGroupForm>();
		for (AssignDummyVLANToGroupForm assignDummy:dummyAssignList) {
			dummyMap.put(assignDummy.getVlanID(), assignDummy);
		}
		
		//here i'm just retrieving all the groups in the system and putting them into a hash map for easy look up
		//i want to be able to map the group id quickly and easily to its group name
		ObjStore<Group> groupStore = ObjStoreHelper.getStore(Group.class);
		List<Group> groups = groupStore.queryAll();
		HashMap<Integer, Group> groupMap = new HashMap<Integer, Group>();
		for (Group group:groups) {
			groupMap.put(new Integer(group.getGroupId()), group);
		}
		
		TabularReportInternalModel model = new TabularReportInternalModel();

		model.addTextColumn("Name", "Name");
		model.addTextColumn("VLAN ID", "VLAN ID");
		model.addTextColumn("Group", "Assigned To Group");
		model.completedHeader();

		ObjStore<DummyVLAN> store = ObjStoreHelper.getStore(DummyVLAN.class);
		String contextID = context.getId();
		logger.info("mmmmmm " + contextID + " mmmmm");
		List<DummyVLAN> objs = store.query(contextID);
		for (int i=0; i<objs.size(); i++)
		{
			DummyVLAN vlan = objs.get(i);
			model.addTextValue(vlan.getName());
			model.addTextValue(vlan.getVlanID());
			AssignDummyVLANToGroupForm group = dummyMap.get(vlan.getVlanID());
			if (group != null) {
				Integer groupKey = new Integer(group.getGroupId());
				Group selectedGroup = groupMap.get(groupKey);
				model.addTextValue(selectedGroup.getGroupName());
			} else {
				model.addTextValue("N/A");
			}
			model.completedRow();
		}

		model.updateReport(report);

		return report;
	}

}
