package com.cloupia.feature.slimcea.tasks;

import org.apache.log4j.Logger;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.rwhitear.nimbleRest.authenticate.GetSessionToken;
import com.rwhitear.nimbleRest.exceptions.InitiatorGroupException;
import com.rwhitear.nimbleRest.httpErrorHandling.json.ErrorResponseObject;
import com.rwhitear.nimbleRest.initiatorGroups.DeleteInitiatorGroup;
import com.rwhitear.nimbleRest.initiatorGroups.GetInitiatorGroups;
import com.rwhitear.nimbleRest.initiatorGroups.ParseInitiatorGroupsDetailResponse;
import com.rwhitear.nimbleRest.initiatorGroups.json.GetInitiatorGroupsDetailObject;


public class SlimceaDeleteIgroupTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( SlimceaDeleteIgroupTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaDeleteIgroupConfig config = (SlimceaDeleteIgroupConfig) context.loadConfigObject();

		
		String ipAddress = config.getIpAddress();
		String username = config.getUsername();
		String password = config.getPassword();
		String initiatorGroupName = config.getInitiatorGroupName(); 

		// Retrieve Nimble array auth token.
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		// Check that iGroup exists.
		String iGroupsResponse = new GetInitiatorGroups(ipAddress, token).getDetail();
		
		logger.info("Initiator Groups Response: " +iGroupsResponse );
		
		GetInitiatorGroupsDetailObject iGroupObj = new ParseInitiatorGroupsDetailResponse(iGroupsResponse).parse();

		String iGroupID = "";
		
		for( int i=0; i < iGroupObj.getData().size(); i++ ) {
			
			if( iGroupObj.getData().get(i).getName().equals(initiatorGroupName) ) {
				
				iGroupID = iGroupObj.getData().get(i).getId();
				
				break;
				
			}
			
		}
		
		// If iGroupID didn't get set in the previous for loop, then error.
		if( iGroupID == "" ) {
			
			throw new InitiatorGroupException("Initiator group [" +initiatorGroupName+ "] doesn't exist." );
			
		}

		// Initiator group exists, go ahead and delete it.
		DeleteInitiatorGroup dig = new DeleteInitiatorGroup(ipAddress, token, iGroupID);
		
		String deleteResponse = dig.execute();
		
		logger.info("Initiator Group Delete Response: " + deleteResponse );
		
		actionLogger.addInfo("HTTP status code: " + dig.getHttpStatusCode() );
		
		if( (dig.getHttpStatusCode() != 201) && (dig.getHttpStatusCode() != 200) ) {
			
			ErrorResponseObject ero = dig.getErrorResponse();
			
			for( int i = 0; i < ero.getMessages().size(); i++ ) {
				
				actionLogger.addError("Error ["+ero.getMessages().get(i).getCode()+"]: " + ero.getMessages().get(i).getText() );
				
			}
			
			throw new InitiatorGroupException("Request failed.");
			
		}

		
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new SlimceaDeleteIgroupConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaDeleteIgroupConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
