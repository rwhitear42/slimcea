package com.cloupia.feature.slimcea.tasks;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;


public class SlimceaCreateSnapshotTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaCreateSnapshotConfig config = (SlimceaCreateSnapshotConfig) context.loadConfigObject();

		actionLogger.addInfo("Username: " +config.getUsername());
		actionLogger.addInfo("Password: " +config.getPassword());
		actionLogger.addInfo("IP Address: " +config.getIpAddress());
		actionLogger.addInfo("Volume Name: " +config.getVolumeName());
		actionLogger.addInfo("Snapshot Name: " +config.getSnapshotName());
		actionLogger.addInfo("Snapshot Description: " +config.getSnapshotDescription());
		actionLogger.addInfo("Online: " +config.getSnapshotOnline());
		actionLogger.addInfo("Writable: " +config.getSnapshotWritable());
		
		//Session session = jsch.getSession(config.getUsername(), config.getIpAddress(), 22);
		//session.setPassword(config.getPassword());
			
		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
		context.getChangeTracker().undoableResourceAdded("assetType", "idString", "Nimble Create Snapshot", 
				"Rollback Create Nimble Snapshot " + config.getSnapshotName(), 
				new SlimceaDeleteSnapshotTask().getTaskName(), new SlimceaDeleteSnapshotConfig(config));
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new SlimceaCreateSnapshotConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaCreateSnapshotConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
