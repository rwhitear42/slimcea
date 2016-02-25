package com.cloupia.feature.slimcea.tasks;

import org.apache.log4j.Logger;

import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.rwhitear.nimbleRest.authenticate.GetSessionToken;
import com.rwhitear.nimbleRest.exceptions.InitiatorGroupException;
import com.rwhitear.nimbleRest.httpErrorHandling.json.ErrorResponseObject;
import com.rwhitear.nimbleRest.initiatorGroups.GetInitiatorGroups;
import com.rwhitear.nimbleRest.initiatorGroups.ParseInitiatorGroupsDetailResponse;
import com.rwhitear.nimbleRest.initiatorGroups.json.GetInitiatorGroupsDetailObject;
import com.rwhitear.nimbleRest.initiators.CreateIscsiInitiator;


public class SlimceaAddIscsiInitiatorToIgroupTask extends AbstractTask {

	private static Logger logger = Logger.getLogger( SlimceaAddIscsiInitiatorToIgroupTask.class );
	
	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaAddIscsiInitiatorToIgroupConfig config = (SlimceaAddIscsiInitiatorToIgroupConfig) context.loadConfigObject();


		String ipAddress = config.getIpAddress();
		String username = config.getUsername();
		String password = config.getPassword(); 
		String initiatorGroupName = config.getInitiatorGroupName();
		String initiatorLabel = config.getInitiatorName();
		String iqn = config.getIqn();

		// Retrieve Nimble array auth token.
		actionLogger.addInfo( "Retrieving authentication token." );

		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		// Get iGroupID.
		String iGroupsResponse = new GetInitiatorGroups(ipAddress, token).getDetail();
		
		logger.info("Initiator Groups Response: " +iGroupsResponse );
		
		GetInitiatorGroupsDetailObject iGroupObj = new ParseInitiatorGroupsDetailResponse(iGroupsResponse).parse();

		String initiatorGroupID = "";
		
		for( int i=0; i < iGroupObj.getData().size(); i++ ) {
			
			if( iGroupObj.getData().get(i).getName().equals(initiatorGroupName) ) {
				
				initiatorGroupID = iGroupObj.getData().get(i).getId();
				
				break;
			}
			
		}

		if( initiatorGroupID == "" ) {
			
			throw new InitiatorGroupException("Failed to find initiator group [" +initiatorGroupName+ "]." );
		}
		
		// Getting this far means an iGroup match was made. Continue.
		actionLogger.addInfo( "Adding initiator ["+ initiatorLabel +"]["+ iqn +"] to initiator group ["+ initiatorGroupName +"]." );
		
		CreateIscsiInitiator cii = new CreateIscsiInitiator(ipAddress, token);
		
		String ciiCreateResponse = cii.create(initiatorGroupID, initiatorLabel, iqn);
		
		logger.info("ciiCreateResponse: " + ciiCreateResponse );

		actionLogger.addInfo("HTTP status code: " + cii.getHttpStatusCode() );
		
		if( (cii.getHttpStatusCode() != 201) && (cii.getHttpStatusCode() != 200) ) {
			
			ErrorResponseObject ero = cii.getErrorResponse();
			
			for( int i = 0; i < ero.getMessages().size(); i++ ) {
				
				actionLogger.addError("Error ["+ero.getMessages().get(i).getCode()+"]: " + ero.getMessages().get(i).getText() );
				
			}
			
			throw new InitiatorGroupException("Request failed.");
			
		}
		
		actionLogger.addInfo( "Task completed successfully." );
		
		
		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
		context.getChangeTracker().undoableResourceAdded("assetType", "idString", "Nimble Add Initiator to Initiator Group", 
				"Rollback Nimble Add Initiator to Initiator Group " + config.getInitiatorGroupName(), 
				new SlimceaRemoveIscsiInitiatorFromIgroupTask().getTaskName(), new SlimceaRemoveIscsiInitiatorFromIgroupConfig(config));
		
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
		return new SlimceaAddIscsiInitiatorToIgroupConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaAddIscsiInitiatorToIgroupConfig.displayLabel;
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
