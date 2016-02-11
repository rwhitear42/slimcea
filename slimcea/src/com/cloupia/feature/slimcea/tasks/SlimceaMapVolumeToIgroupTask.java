package com.cloupia.feature.slimcea.tasks;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.rwhitear.nimbleRest.accessControlRecords.CreateAccessControlRecord;
import com.rwhitear.nimbleRest.authenticate.GetSessionToken;
import com.rwhitear.nimbleRest.initiatorGroups.GetInitiatorGroups;
import com.rwhitear.nimbleRest.initiatorGroups.json.GetInitiatorGroupsDetailResponse;
import com.rwhitear.nimbleRest.volumes.GetVolumes;
import com.rwhitear.nimbleRest.volumes.json.GetVolumesSummaryResponse;


public class SlimceaMapVolumeToIgroupTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaMapVolumeToIgroupConfig config = (SlimceaMapVolumeToIgroupConfig) context.loadConfigObject();

		
		String ipAddress = config.getIpAddress();		
		String username = config.getUsername();
		String password = config.getPassword();
		String volumeName = config.getVolumeName();
		String initiatorGroupName = config.getInitiatorGroupName();
		
		
		// Retrieve Nimble array auth token.
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		
		// Get volume ID for 'volumeName'.
		actionLogger.addInfo("Retrieving volume ID for volume ["+ volumeName + "].");
		
		String volumeJsonData = new GetVolumes(ipAddress, token).getSummary();
				
		String volID = new GetVolumesSummaryResponse(volumeJsonData).getVolumeID(volumeName);

		
		// Initiator Groups
		actionLogger.addInfo("Retrieving Initiator Group ID for iGroup ["+ initiatorGroupName + "].");
		
		String iGroupJsonData = new GetInitiatorGroups(ipAddress, token).getInitiatorGroupSummary();
		
		//actionLogger.addInfo("iGroupJsonData: " + iGroupJsonData);
		
		String iGroupID = new GetInitiatorGroupsDetailResponse(iGroupJsonData).getInitiatorGroupID(initiatorGroupName);

		//actionLogger.addInfo("iGroupID: " + iGroupID );
		
		
		// Create new ACR.
		if( !volID.equals(null) ) {
			
			actionLogger.addInfo("Creating new Access Control Record for volume ["+ volumeName + "] and Initiator Group [" + initiatorGroupName + "].");
			
			actionLogger.addInfo("Create ACR JSON Response:\n"  + new CreateAccessControlRecord(ipAddress, token, volID, iGroupID).create());
		}

		
		/*
		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
		context.getChangeTracker().undoableResourceAdded("assetType", "idString", "Nimble Map Volume to Initiator Group", 
				"Rollback Create Nimble Map Volume to Initiator Group " + config.getInitiatorGroupName(), 
				new SlimceaUnMapVolFromIgroupTask().getTaskName(), new SlimceaUnMapVolFromIgroupConfig(config));
	
        try
        {
            context.saveOutputValue(SlimceaConstants.NIMBLE_INITIATOR_GROUP_NAME, config.getInitiatorGroupName());

        } catch (Exception e)
        {
            actionLogger.addWarning("Failed to set output variable(s): " + e.getMessage());
        }
        */
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new SlimceaMapVolumeToIgroupConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaMapVolumeToIgroupConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		return null;
	}
	
	/*
	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		TaskOutputDefinition[] ops = new TaskOutputDefinition[1];
		//NOTE: If you want to use the output of this task as input to another task. Then the second argument 
		//of the output definition MUST MATCH the type of UserInputField in the config of the task that will
		//be receiving this output.  Take a look at HelloWorldConfig as an example.
		ops[0] = new TaskOutputDefinition( SlimceaConstants.NIMBLE_INITIATOR_GROUP_NAME, SlimceaConstants.GENERIC_TEXT_INPUT, "Three" );
		return ops;
	}
	*/
}
