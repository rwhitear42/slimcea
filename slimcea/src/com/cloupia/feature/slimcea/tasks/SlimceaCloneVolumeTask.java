package com.cloupia.feature.slimcea.tasks;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.rwhitear.nimbleRest.accessControlRecords.CreateAccessControlRecord;
import com.rwhitear.nimbleRest.accessControlRecords.DeleteAccessControlRecord;
import com.rwhitear.nimbleRest.accessControlRecords.GetAccessControlRecords;
import com.rwhitear.nimbleRest.accessControlRecords.json.ACRobject;
import com.rwhitear.nimbleRest.accessControlRecords.json.GetACRdetailResponse;
import com.rwhitear.nimbleRest.authenticate.GetSessionToken;
import com.rwhitear.nimbleRest.exceptions.InitiatorGroupException;
import com.rwhitear.nimbleRest.initiatorGroups.GetInitiatorGroups;
import com.rwhitear.nimbleRest.initiatorGroups.ParseInitiatorGroupsDetailResponse;
import com.rwhitear.nimbleRest.initiatorGroups.json.GetInitiatorGroupsDetailObject;
import com.rwhitear.nimbleRest.snapshots.GetSnapshots;
import com.rwhitear.nimbleRest.snapshots.json.GetSnapshotDetailResponse;
import com.rwhitear.nimbleRest.volumes.GetVolumes;
import com.rwhitear.nimbleRest.volumes.VolumeClone;
import com.rwhitear.nimbleRest.volumes.json.GetVolumesSummaryResponse;


public class SlimceaCloneVolumeTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaCloneVolumeConfig config = (SlimceaCloneVolumeConfig) context.loadConfigObject();

		
		// nimbleREST library testing.
		
		String username = config.getUsername();
		String password = config.getPassword();
		String ipAddress = config.getIpAddress();
		String volumeName = config.getVolumeName();
		String baseSnapshotName = config.getBaseSnapshotName();
		String cloneName = config.getCloneName();
		String initiatorGroupName = config.getInitiatorGroupName();
		
		
		// Retrieve Nimble array auth token.
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		
		// Get volume ID for 'volumeName'.
		actionLogger.addInfo("Retrieving volume ID for volume ["+ volumeName + "].");
		
		String volumeJsonData = new GetVolumes(ipAddress, token).getSummary();
				
		String volID = new GetVolumesSummaryResponse(volumeJsonData).getVolumeID(volumeName);
		
		
		// Get snapshot ID for volume snapshot 'baseSnapshotName'.
		actionLogger.addInfo("Retrieving snapshot ID for snapshot ["+ baseSnapshotName + "].");
		
		String volumeSnapshotJsonData = new GetSnapshots(ipAddress, token, volID).getSnapshotSummary();
		
		String snapID = new GetSnapshotDetailResponse(volumeSnapshotJsonData).getSnapshotID(baseSnapshotName);

			
		// Create clone.
		actionLogger.addInfo("Creating volume clone [" + cloneName + "] from snapshot ["+ baseSnapshotName + "].");
		
		new VolumeClone(ipAddress, token).create(cloneName, snapID);
				
		
		// Initiator Groups
		actionLogger.addInfo("Retrieving Initiator Group ID for iGroup ["+ initiatorGroupName + "].");
		
		String iGroupsResponse = new GetInitiatorGroups(ipAddress, token).getDetail();
		
		System.out.println("iGroupsResponse: " + iGroupsResponse);
		
		GetInitiatorGroupsDetailObject iGroupObj = new ParseInitiatorGroupsDetailResponse(iGroupsResponse).parse();
		
		System.out.println("Initiator Groups size: " + iGroupObj.getData().size() );
		

		String iGroupID = "";
		
		for( int i=0; i < iGroupObj.getData().size(); i++ ) {
			
			if( iGroupObj.getData().get(i).getName().equals(initiatorGroupName)) {
				
				iGroupID = iGroupObj.getData().get(i).getId();
				
				System.out.println("iGroup ID ["+iGroupID+"] found for iGroup name [" +iGroupObj.getData().get(i).getName()+ "]." );
				
				break;
				
			}
			
		}
		
		// If iGroupID is unset, then the previous for loop failed to match the iGroup name.
		if( iGroupID == "" ) {
			
			throw new InitiatorGroupException("Initiator group [" +initiatorGroupName+ "] doesn't exist." );
			
		}
		
		// Delete Access Control Records for 'cloneName'.
		String acrJsonData =  new GetAccessControlRecords(ipAddress, token, cloneName).getACRsummary();

		ACRobject acrObj = new GetACRdetailResponse(acrJsonData).getResponseObject();
		
		for( int i = 0; i < acrObj.getData().size(); i++ ) {
			actionLogger.addInfo( "Deleting ACR ID " + acrObj.getData().get(i).getId() );
			
			new DeleteAccessControlRecord( ipAddress, token, acrObj.getData().get(i).getId()).deleteRecord();
		}
				
		
		// Retrieve cloned Volume ID.
		actionLogger.addInfo("Retrieving volume ID for volume ["+ cloneName + "].");
		
		String cloneVolumeJsonData = new GetVolumes(ipAddress, token).getSummary();
		
		String cloneVolID = new GetVolumesSummaryResponse(cloneVolumeJsonData).getVolumeID(cloneName);

		
		// Create new ACR.
		if( !cloneVolID.equals(null) ) {
			
			actionLogger.addInfo("Creating new Access Control Record for volume ["+ cloneName + "] and Initiator Group [" + initiatorGroupName + "].");
			
			actionLogger.addInfo("Create ACR JSON Response:\n"  + new CreateAccessControlRecord(ipAddress, token, cloneVolID, iGroupID).create());
		}
			
		
		
		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
		
		/*
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
        */
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new SlimceaCloneVolumeConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaCloneVolumeConfig.displayLabel;
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
		ops[0] = new TaskOutputDefinition( SlimceaConstants.NIMBLE_VOLUME_NAME, SlimceaConstants.GENERIC_TEXT_INPUT, "Three" );
		return ops;
	}
	*/
}
