package com.cloupia.feature.slimcea.tasks;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.rwhitear.nimbleRest.authenticate.GetSessionToken;
import com.rwhitear.nimbleRest.constants.NimbleRESTConstants;
import com.rwhitear.nimbleRest.exceptions.VolumeIdException;
import com.rwhitear.nimbleRest.volumeCollections.AddVolumeToVolCollection;
import com.rwhitear.nimbleRest.volumes.GetVolumes;
import com.rwhitear.nimbleRest.volumes.json.ParseVolumeDetailResponse;
import com.rwhitear.nimbleRest.volumes.json.VolumesDetailJsonObject;


public class SlimceaDisassocVolFromVolumeCollectionTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaDisassocVolFromVolumeCollectionConfig config = (SlimceaDisassocVolFromVolumeCollectionConfig) context.loadConfigObject();

		/*
		actionLogger.addInfo("Username: " +config.getUsername());
		actionLogger.addInfo("Password: " +config.getPassword());
		actionLogger.addInfo("IP Address: " +config.getIpAddress());
		actionLogger.addInfo("Volume Name: " +config.getVolumeName());
		*/
		
		String ipAddress = config.getIpAddress();
		String username = config.getUsername();
		String password = config.getPassword(); 
		String volumeName = config.getVolumeName();
		

		// Retrieve Nimble array auth token.
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();

		// Check that the volume exists and retrieve its id.
		
		// Retrieve JSON response for detailed Volume information.
		String volumesJsonData = new GetVolumes(ipAddress, token).getDetail();

		actionLogger.addInfo("Volumes Detail JSON: " + volumesJsonData );
		//actionLogger.addInfo("Volume Collections Detail JSON: " + volCollectinoJsonData );
		
		VolumesDetailJsonObject volumeDetail = new ParseVolumeDetailResponse(volumesJsonData).parse();
		
		String volID = "";
				
		for( int i=0; i < volumeDetail.getData().size(); i++ ) {
					
			//System.out.println("Volume collection name["+i+"]: " + volCollDetail.getData().get(i).getName() );

			if( volumeDetail.getData().get(i).getName().equals(volumeName) ) {
						
				actionLogger.addInfo("Found volume ["+volumeName+"] with id["+
						volumeDetail.getData().get(i).getId() +"].");
						
				volID = volumeDetail.getData().get(i).getId();
						
				break;
					
			}
					
		}
				
		// Ensure that the volume collection id for volCollName has been found.
		if( volID == "") {
					
			throw new VolumeIdException("Failed to find volume ["+volumeName+"].");
		
		}
		
		// All prerequisites satisfied. Build the volume to volume collection association.
		String removeVolFromVolCollResponse = new AddVolumeToVolCollection(ipAddress, token, volID, 
				NimbleRESTConstants.NO_VOLUME_COLLECTION_ID ).execute();
		
		actionLogger.addInfo("removeVolFromVolCollResponse: " +removeVolFromVolCollResponse );

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
