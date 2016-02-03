package com.cloupia.feature.slimcea.tasks;

import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;


public class SlimceaAssocVolToVolumeCollectionTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaAssocVolToVolumeCollectionConfig config = (SlimceaAssocVolToVolumeCollectionConfig) context.loadConfigObject();

		actionLogger.addInfo("Username: " +config.getUsername());
		actionLogger.addInfo("Password: " +config.getPassword());
		actionLogger.addInfo("IP Address: " +config.getIpAddress());
		actionLogger.addInfo("Volume Name: " +config.getVolumeName());
		actionLogger.addInfo("Volume Collection Name: " +config.getVolumeCollectionName());

		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
		context.getChangeTracker().undoableResourceAdded("assetType", "idString", "Nimble Associate Volume with Volume Collection", 
				"Rollback Associate Volume with Volume Collection " + config.getVolumeName(), 
				new SlimceaDisassocVolFromVolumeCollectionTask().getTaskName(), new SlimceaDisassocVolFromVolumeCollectionConfig(config));
		
        try
        {
            context.saveOutputValue(SlimceaConstants.NIMBLE_VOLUME_NAME, config.getVolumeName());
            context.saveOutputValue(SlimceaConstants.NIMBLE_VOLUME_COLLECTION_NAME, config.getVolumeCollectionName());

        } catch (Exception e)
        {
            actionLogger.addWarning("Failed to set output variable(s): " + e.getMessage());
        }
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new SlimceaAssocVolToVolumeCollectionConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaAssocVolToVolumeCollectionConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		TaskOutputDefinition[] ops = new TaskOutputDefinition[2];
		//NOTE: If you want to use the output of this task as input to another task. Then the second argument 
		//of the output definition MUST MATCH the type of UserInputField in the config of the task that will
		//be receiving this output.  Take a look at HelloWorldConfig as an example.
		ops[0] = new TaskOutputDefinition( SlimceaConstants.NIMBLE_VOLUME_NAME, SlimceaConstants.GENERIC_TEXT_INPUT, "Three" );
		ops[1] = new TaskOutputDefinition( SlimceaConstants.NIMBLE_VOLUME_COLLECTION_NAME, SlimceaConstants.GENERIC_TEXT_INPUT, "Three" );
		return ops;
	}
}
