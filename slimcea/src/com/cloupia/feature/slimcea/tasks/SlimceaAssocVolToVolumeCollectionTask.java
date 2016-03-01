package com.cloupia.feature.slimcea.tasks;

import org.apache.log4j.Logger;

import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.rwhitear.nimbleRest.authenticate.GetSessionToken;
import com.rwhitear.nimbleRest.exceptions.VolumeIdException;
import com.rwhitear.nimbleRest.exceptions.volumeCollectionException;
import com.rwhitear.nimbleRest.volumeCollections.AddVolumeToVolCollection;
import com.rwhitear.nimbleRest.volumeCollections.GetVolumeCollections;
import com.rwhitear.nimbleRest.volumeCollections.json.ParseVolCollectionsDetailResponse;
import com.rwhitear.nimbleRest.volumeCollections.json.VolCollectionsDetailJsonObject;
import com.rwhitear.nimbleRest.volumes.GetVolumes;
import com.rwhitear.nimbleRest.volumes.json.ParseVolumeDetailResponse;
import com.rwhitear.nimbleRest.volumes.json.VolumesDetailJsonObject;


public class SlimceaAssocVolToVolumeCollectionTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( SlimceaAssocVolToVolumeCollectionTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaAssocVolToVolumeCollectionConfig config = (SlimceaAssocVolToVolumeCollectionConfig) context.loadConfigObject();

		
		String ipAddress = config.getIpAddress();
		String username = config.getUsername();
		String password = config.getPassword(); 
		String volumeName = config.getVolumeName();
		String volCollName = config.getVolumeCollectionName();

		// Retrieve Nimble array auth token.
		actionLogger.addInfo( "Retrieving authentication token." );

		String token = new GetSessionToken(ipAddress, username, password).getNewToken();

		// Check that the volume exists and retrieve its id.
		actionLogger.addInfo( "Checking for the existence of volume[" + volumeName + "]." );
		
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
	
		
		// Check that the volume collection exists and get its id.
		actionLogger.addInfo( "Checking for the existence of volume collection[" + volCollName + "]." );

		// Retrieve JSON response for detailed volume collections information.
		String volCollectionJsonData = new GetVolumeCollections(ipAddress, token).getDetail();

		logger.info("Volume Collections Detail JSON: " + volCollectionJsonData );
		//actionLogger.addInfo("Volume Collections Detail JSON: " + volCollectinoJsonData );
		
		VolCollectionsDetailJsonObject volCollDetail = new ParseVolCollectionsDetailResponse(volCollectionJsonData).parse();
		
		String volCollID = "";
		
		for( int i=0; i < volCollDetail.getData().size(); i++ ) {
			
			if( volCollDetail.getData().get(i).getName().equals(volCollName) ) {
				
				actionLogger.addInfo("Found volume collection ["+volCollName+"] with id["+
						volCollDetail.getData().get(i).getId() +"].");
				
				volCollID = volCollDetail.getData().get(i).getId();
				
				break;
				
			}
			
		}
		
		// Ensure that the volume collection id for volCollName has been found.
		if( volCollID == "") {
			
			throw new volumeCollectionException("Failed to find volume collection ["+volCollName+"].");
		}
		
		// All prerequisites satisfied. Build the volume to volume collection association.
		actionLogger.addInfo( "Adding volume ["+ volumeName +"] to volume collection[" + volCollName + "]." );
		
		String addVol2VolCollResponse = new AddVolumeToVolCollection(ipAddress, token, volID, volCollID).execute();
		
		logger.info("addVol2VolColl Response: " +addVol2VolCollResponse );

		actionLogger.addInfo( "Task completed successfully." );

		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
		context.getChangeTracker().undoableResourceAdded("assetType", "idString", "Nimble Associate Volume with Volume Collection", 
				"Rollback Associate Volume ["+config.getVolumeName()+"] with Volume Collection [" + config.getVolumeCollectionName() +"].", 
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
