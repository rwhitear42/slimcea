package com.cloupia.feature.slimcea.tasks;

import org.apache.log4j.Logger;

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
	
	private static Logger logger = Logger.getLogger( SlimceaDisassocVolFromVolumeCollectionTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaDisassocVolFromVolumeCollectionConfig config = (SlimceaDisassocVolFromVolumeCollectionConfig) context.loadConfigObject();

		
		String ipAddress = config.getIpAddress();
		String username = config.getUsername();
		String password = config.getPassword(); 
		String volumeName = config.getVolumeName();
		

		// Retrieve Nimble array auth token.
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();

		// Check that the volume exists and retrieve its id.
		
		// Retrieve JSON response for detailed Volume information.
		String volumesJsonData = new GetVolumes(ipAddress, token).getDetail();

		logger.info("Volumes Detail JSON: " + volumesJsonData );
		
		VolumesDetailJsonObject volumeDetail = new ParseVolumeDetailResponse(volumesJsonData).parse();
		
		String volID = "";
				
		for( int i=0; i < volumeDetail.getData().size(); i++ ) {
					
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
		
		logger.info("removeVolFromVolCollResponse: " +removeVolFromVolCollResponse );

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
