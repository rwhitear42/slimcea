package com.cloupia.feature.slimcea.tasks;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.rwhitear.nimbleRest.authenticate.GetSessionToken;
import com.rwhitear.nimbleRest.exceptions.InitiatorGroupException;
import com.rwhitear.nimbleRest.initiatorGroups.GetInitiatorGroups;
import com.rwhitear.nimbleRest.initiatorGroups.ParseInitiatorGroupsDetailResponse;
import com.rwhitear.nimbleRest.initiatorGroups.json.GetInitiatorGroupsDetailObject;
import com.rwhitear.nimbleRest.initiators.DeleteInitiator;


public class SlimceaRemoveInitiatorFromIgroupTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaRemoveInitiatorFromIgroupConfig config = (SlimceaRemoveInitiatorFromIgroupConfig) context.loadConfigObject();

		/*
		actionLogger.addInfo("Username: " +config.getUsername());
		actionLogger.addInfo("Password: " +config.getPassword());
		actionLogger.addInfo("IP Address: " +config.getIpAddress());
		actionLogger.addInfo("Initiator Group Name: " +config.getInitiatorGroupName());
		actionLogger.addInfo("Initiator Name: " +config.getInitiatorName());
		*/
		
		String ipAddress = config.getIpAddress();
		String username = config.getUsername();
		String password = config.getPassword(); 
		String initiatorGroupName = config.getInitiatorGroupName();
		String initiatorLabel = config.getInitiatorName();

		
		// Retrieve Nimble array auth token.
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		// Get iGroupID.
		String iGroupsResponse = new GetInitiatorGroups(ipAddress, token).getDetail();
		
		actionLogger.addInfo("Initiator Groups Response: " +iGroupsResponse );
		
		GetInitiatorGroupsDetailObject iGroupObj = new ParseInitiatorGroupsDetailResponse(iGroupsResponse).parse();

		String initiatorID = "";
		
		for( int i=0; i < iGroupObj.getData().size(); i++ ) {
			
			if( iGroupObj.getData().get(i).getName().equals(initiatorGroupName) ) {
				
				for( int j = 0; j < iGroupObj.getData().get(i).getIscsi_initiators().size(); j++ ) {
					
					if( iGroupObj.getData().get(i).getIscsi_initiators().get(j).getLabel().equals(initiatorLabel) ) {
						
						initiatorID = iGroupObj.getData().get(i).getIscsi_initiators().get(j).getInitiator_id();
						
						break;
						
					}
				}			
			}		
		}
		
		if( initiatorID == "") {
			
			throw new InitiatorGroupException("Failed to find initiator ID for initiator [" +initiatorLabel+ "].");
			
		}
		
		// Initiator ID found. Go ahead and delete it.
		String deleteInitiatorResponse = new DeleteInitiator(ipAddress, token, initiatorID).execute();
		
		actionLogger.addInfo("deleteInitiatorResponse: " + deleteInitiatorResponse );
				
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new SlimceaRemoveInitiatorFromIgroupConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaRemoveInitiatorFromIgroupConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
