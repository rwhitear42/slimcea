package com.cloupia.feature.slimcea.tasks;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;


public class SlimceaDeleteSnapshotTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaDeleteSnapshotConfig config = (SlimceaDeleteSnapshotConfig) context.loadConfigObject();

		actionLogger.addInfo("Rollback logic here using the following values: ");
		actionLogger.addInfo("Username: " +config.getUsername());
		actionLogger.addInfo("Password: " +config.getPassword());
		actionLogger.addInfo("IP Address: " +config.getIpAddress());
		actionLogger.addInfo("Volume Name: " +config.getVolumeName());
		actionLogger.addInfo("Snapshot Name: " +config.getSnapshotName());
		
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new SlimceaDeleteSnapshotConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaDeleteSnapshotConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
