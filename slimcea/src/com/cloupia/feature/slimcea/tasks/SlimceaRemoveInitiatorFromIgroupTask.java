package com.cloupia.feature.slimcea.tasks;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;


public class SlimceaRemoveInitiatorFromIgroupTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaRemoveInitiatorFromIgroupConfig config = (SlimceaRemoveInitiatorFromIgroupConfig) context.loadConfigObject();

		actionLogger.addInfo("Username: " +config.getUsername());
		actionLogger.addInfo("Password: " +config.getPassword());
		actionLogger.addInfo("IP Address: " +config.getIpAddress());
		actionLogger.addInfo("Initiator Group Name: " +config.getInitiatorGroupName());
		actionLogger.addInfo("Initiator Name: " +config.getInitiatorName());
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new SlimceaRemoveInitiatorFromIgroupConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaRemoveInitiatorFromIgroupConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
