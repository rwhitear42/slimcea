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
import com.rwhitear.nimbleRest.initiatorGroups.CreateInitiatorGroup;
import com.rwhitear.nimbleRest.initiatorGroups.GetInitiatorGroups;
import com.rwhitear.nimbleRest.initiatorGroups.ParseInitiatorGroupsDetailResponse;
import com.rwhitear.nimbleRest.initiatorGroups.json.GetInitiatorGroupsDetailObject;


public class SlimceaCreateIgroupTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( SlimceaCreateIgroupTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaCreateIgroupConfig config = (SlimceaCreateIgroupConfig) context.loadConfigObject();

		
		String ipAddress 			= config.getIpAddress();
		String username 			= config.getUsername();
		String password 			= config.getPassword(); 
		String initiatorGroupName 	= config.getInitiatorGroupName();
		String description			= config.getDescription();
		String sanProtocol 			= config.getSanProtocol();
		
		// Retrieve Nimble array auth token.
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		// Check that the initiator group doesn't already exist.
		String iGroupsResponse = new GetInitiatorGroups(ipAddress, token).getDetail();
		
		logger.info("Initiator Groups Response: " +iGroupsResponse );
		
		GetInitiatorGroupsDetailObject iGroupObj = new ParseInitiatorGroupsDetailResponse(iGroupsResponse).parse();

		for( int i=0; i < iGroupObj.getData().size(); i++ ) {
			
			if( iGroupObj.getData().get(i).getName().equals(initiatorGroupName) ) {
				
				throw new InitiatorGroupException("Initiator group [" +initiatorGroupName+ "] already exists." );
				
			}
			
		}

		// Initiator group doesn't exist. Go ahead and create the initiator group.
		CreateInitiatorGroup cig = new CreateInitiatorGroup(ipAddress, token);
		
		String createResp = cig.create(initiatorGroupName, description, sanProtocol);
		
		logger.info("Create Initiator Group Response: " + createResp);

		actionLogger.addInfo("HTTP status code: " + cig.getHttpStatusCode() );
		
		if( (cig.getHttpStatusCode() != 201) && (cig.getHttpStatusCode() != 200) ) {
			
			ErrorResponseObject ero = cig.getErrorResponse();
			
			for( int i = 0; i < ero.getMessages().size(); i++ ) {
				
				actionLogger.addError("Error ["+ero.getMessages().get(i).getCode()+"]: " + ero.getMessages().get(i).getText() );
				
			}
			
			throw new InitiatorGroupException("Request failed.");
			
		}

		
		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
		context.getChangeTracker().undoableResourceAdded("assetType", "idString", "Nimble Create Initiator Group", 
				"Rollback Create Nimble Initiator Group " + config.getInitiatorGroupName(), 
				new SlimceaDeleteIgroupTask().getTaskName(), new SlimceaDeleteIgroupConfig(config));
		
        try
        {
            context.saveOutputValue(SlimceaConstants.NIMBLE_INITIATOR_GROUP_NAME, config.getInitiatorGroupName());

        } catch (Exception e)
        {
            actionLogger.addWarning("Failed to set output variable(s): " + e.getMessage());
        }
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new SlimceaCreateIgroupConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaCreateIgroupConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		TaskOutputDefinition[] ops = new TaskOutputDefinition[1];
		//NOTE: If you want to use the output of this task as input to another task. Then the second argument 
		//of the output definition MUST MATCH the type of UserInputField in the config of the task that will
		//be receiving this output.  Take a look at HelloWorldConfig as an example.
		ops[0] = new TaskOutputDefinition( SlimceaConstants.NIMBLE_INITIATOR_GROUP_NAME, SlimceaConstants.GENERIC_TEXT_INPUT, "Three" );
		return ops;
	}
}
