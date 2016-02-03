package com.cloupia.feature.slimcea.tasks;

import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;


public class SlimceaAddInitiatorToIgroupTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaAddInitiatorToIgroupConfig config = (SlimceaAddInitiatorToIgroupConfig) context.loadConfigObject();

		actionLogger.addInfo("Username: " +config.getUsername());
		actionLogger.addInfo("Password: " +config.getPassword());
		actionLogger.addInfo("IP Address: " +config.getIpAddress());
		actionLogger.addInfo("Initiator Group Name: " +config.getInitiatorGroupName());
		actionLogger.addInfo("Initiator Name: " +config.getInitiatorName());

		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
		context.getChangeTracker().undoableResourceAdded("assetType", "idString", "Nimble Add Initiator to Initiator Group", 
				"Rollback Nimble Add Initiator to Initiator Group " + config.getInitiatorGroupName(), 
				new SlimceaRemoveInitiatorFromIgroupTask().getTaskName(), new SlimceaRemoveInitiatorFromIgroupConfig(config));
		
        try
        {
            context.saveOutputValue(SlimceaConstants.NIMBLE_INITIATOR_GROUP_NAME, config.getInitiatorGroupName());
            context.saveOutputValue(SlimceaConstants.NIMBLE_INITIATOR_NAME, config.getInitiatorName());

        } catch (Exception e)
        {
            actionLogger.addWarning("Failed to set output variable(s): " + e.getMessage());
        }
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new SlimceaAddInitiatorToIgroupConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaAddInitiatorToIgroupConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		TaskOutputDefinition[] ops = new TaskOutputDefinition[2];
		//NOTE: If you want to use the output of this task as input to another task. Then the second argument 
		//of the output definition MUST MATCH the type of UserInputField in the config of the task that will
		//be receiving this output.  Take a look at HelloWorldConfig as an example.
		ops[0] = new TaskOutputDefinition( SlimceaConstants.NIMBLE_INITIATOR_GROUP_NAME, SlimceaConstants.GENERIC_TEXT_INPUT, "Three" );
		ops[1] = new TaskOutputDefinition( SlimceaConstants.NIMBLE_INITIATOR_NAME, SlimceaConstants.GENERIC_TEXT_INPUT, "Three" );
		return ops;
	}
}
