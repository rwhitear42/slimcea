package com.cloupia.feature.slimcea.tasks;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;


public class SlimceaDisassocVolFromVolumeCollectionTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaDisassocVolFromVolumeCollectionConfig config = (SlimceaDisassocVolFromVolumeCollectionConfig) context.loadConfigObject();

		actionLogger.addInfo("Username: " +config.getUsername());
		actionLogger.addInfo("Password: " +config.getPassword());
		actionLogger.addInfo("IP Address: " +config.getIpAddress());
		actionLogger.addInfo("Volume Name: " +config.getVolumeName());
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new SlimceaDisassocVolFromVolumeCollectionConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaDisassocVolFromVolumeCollectionConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
