package com.cloupia.feature.slimcea.tasks;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.rwhitear.nimbleRest.authenticate.GetSessionToken;
import com.rwhitear.nimbleRest.exceptions.SnapshotIdException;
import com.rwhitear.nimbleRest.exceptions.VolumeIdException;
import com.rwhitear.nimbleRest.snapshots.DeleteSnapshot;
import com.rwhitear.nimbleRest.snapshots.GetSnapshots;
import com.rwhitear.nimbleRest.snapshots.OfflineSnapshot;
import com.rwhitear.nimbleRest.snapshots.json.GetSnapshotDetailResponse;
import com.rwhitear.nimbleRest.volumes.GetVolumes;
import com.rwhitear.nimbleRest.volumes.json.ParseVolumeDetailResponse;
import com.rwhitear.nimbleRest.volumes.json.VolumesDetailJsonObject;


public class SlimceaDeleteSnapshotTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaDeleteSnapshotConfig config = (SlimceaDeleteSnapshotConfig) context.loadConfigObject();

		/*
		actionLogger.addInfo("Username: " +config.getUsername());
		actionLogger.addInfo("Password: " +config.getPassword());
		actionLogger.addInfo("IP Address: " +config.getIpAddress());
		actionLogger.addInfo("Volume Name: " +config.getVolumeName());
		actionLogger.addInfo("Snapshot Name: " +config.getSnapshotName());
		*/
		
		String ipAddress = config.getIpAddress();
		String username = config.getUsername();
		String password = config.getPassword();
		String volumeName = config.getVolumeName();
		String snapShotName = config.getSnapshotName();
		
		
		
		// Retrieve Nimble array auth token.
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		// Get volumeID for volumeName.
		String gvResponse = new GetVolumes(ipAddress, token).getDetail();
		
		actionLogger.addInfo("GetVolumes JSON: " + gvResponse );		
		
		
		// Parse JSON for volumeID for volumeName.
		VolumesDetailJsonObject pvsr = new ParseVolumeDetailResponse(gvResponse).parse();
		
		String volumeID = "";
		
		for( int i=0; i<pvsr.getData().size(); i++ ) {
			
			if( pvsr.getData().get(i).getName().equals(volumeName)) {
				
				volumeID = pvsr.getData().get(i).getId();
				
				break;
				
			}
			
		}
		
		if( volumeID == "" ) {
			
			throw new VolumeIdException("Failed to find volume ID for volume [" +volumeName+ "].");
				
		} else {
			
			actionLogger.addInfo("VolID for [" +volumeName+ "] is [" +volumeID+ "].");
			
		}

		
		// Now that we have the volumeID, we can go ahead and get the snapshot ID for snapShotName.
		String gsResponse = new GetSnapshots(ipAddress, token, volumeID).getDetail();

		actionLogger.addInfo("GetSnapshots JSON: " +gsResponse );
		
		GetSnapshotDetailResponse gsdr = new GetSnapshotDetailResponse(gsResponse);
		
		String snapID = gsdr.getSnapshotID(snapShotName);
		
		if( snapID == null ) {
			
			throw new SnapshotIdException("Failed to find snapshot ID for snapshot [" +snapShotName+ "].");

		}
		
		// Snapshot ID retrieved successfully. Offline the snapshot in case it is in an online state.
		String offSnapResponse = new OfflineSnapshot(ipAddress, token, snapID).execute();
				
		actionLogger.addInfo("Offline snapshot response: " +offSnapResponse );
				
		// Final step. Delete the snapshot.
				
		String delSnapResponse = new DeleteSnapshot(ipAddress, token, snapID).execute();
				
		actionLogger.addInfo("Delete snapshot response: " +delSnapResponse );
		
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
