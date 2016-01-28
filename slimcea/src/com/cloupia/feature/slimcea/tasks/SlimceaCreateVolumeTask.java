package com.cloupia.feature.slimcea.tasks;

import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;


public class SlimceaCreateVolumeTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaCreateVolumeConfig config = (SlimceaCreateVolumeConfig) context.loadConfigObject();

		actionLogger.addInfo("Username: " +config.getUsername());
		actionLogger.addInfo("Password: " +config.getPassword());
		actionLogger.addInfo("IP Address: " +config.getIpAddress());
		actionLogger.addInfo("Volume Name: " +config.getVolumeName());
		actionLogger.addInfo("Volume Size (GB): " +config.getVolumeSizeGB());
		actionLogger.addInfo("Volume Description: " +config.getVolumeDescription());
		actionLogger.addInfo("Data Encryption: " +config.getVolumeDataEncryption());
		actionLogger.addInfo("Cache Pinning: " +config.getVolumeCachePinning());
		actionLogger.addInfo("Performance Policy: " +config.getVolumePerfPolicy());

		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
		context.getChangeTracker().undoableResourceAdded("assetType", "idString", "Nimble Create Volume", 
				"Rollback Create Nimble Volume " + config.getVolumeName(), 
				new SlimceaDeleteVolumeTask().getTaskName(), new SlimceaDeleteVolumeConfig(config));
		
        try
        {
            context.saveOutputValue(SlimceaConstants.NIMBLE_VOLUME_NAME, config.getVolumeName());

        } catch (Exception e)
        {
            actionLogger.addWarning("Failed to set output variable(s): " + e.getMessage());
        }
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new SlimceaCreateVolumeConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaCreateVolumeConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		TaskOutputDefinition[] ops = new TaskOutputDefinition[1];
		//NOTE: If you want to use the output of this task as input to another task. Then the second argument 
		//of the output definition MUST MATCH the type of UserInputField in the config of the task that will
		//be receiving this output.  Take a look at HelloWorldConfig as an example.
		ops[0] = new TaskOutputDefinition( SlimceaConstants.NIMBLE_VOLUME_NAME, SlimceaConstants.GENERIC_TEXT_INPUT, "Three" );
		return ops;
	}
}
