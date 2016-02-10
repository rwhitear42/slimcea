package com.cloupia.feature.slimcea.tasks;

/*
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
*/
//import java.util.*;
//import com.cloupia.lib.util.*;
//import org.apache.commons.httpclient.*;
//import org.apache.commons.httpclient.cookie.*;
//import org.apache.commons.httpclient.methods.*;
//import org.apache.commons.httpclient.auth.*;
//import org.apache.commons.httpclient.protocol.*;
//import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
//import com.cloupia.lib.cIaaS.vcd.api.*;

//import java.io.ByteArrayInputStream;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.protocol.Protocol;

//import com.cloupia.feature.slimcea.http.MySSLSocketFactory;
import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.rwhitear.nimbleRest.authenticate.GetSessionToken;
//import com.rwhitear.nimbleRest.initiatorGroups.GetInitiatorGroups;
//import com.rwhitear.nimbleRest.initiatorGroups.json.GetInitiatorGroupsDetailResponse;
import com.rwhitear.nimbleRest.snapshots.GetSnapshots;
import com.rwhitear.nimbleRest.snapshots.json.GetSnapshotDetailResponse;
import com.rwhitear.nimbleRest.volumes.GetVolumes;
import com.rwhitear.nimbleRest.volumes.VolumeClone;
//import com.rwhitear.ucsdHttpRequest.UCSDHttpRequest;
//import com.rwhitear.ucsdHttpRequest.constants.HttpRequestConstants;
import com.rwhitear.nimbleRest.volumes.json.GetVolumesSummaryResponse;


public class SlimceaCloneVolumeTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaCloneVolumeConfig config = (SlimceaCloneVolumeConfig) context.loadConfigObject();

		actionLogger.addInfo("Username: " +config.getUsername());
		actionLogger.addInfo("Password: " +config.getPassword());
		actionLogger.addInfo("IP Address: " +config.getIpAddress());
		actionLogger.addInfo("Volume Name: " +config.getVolumeName());

		
		// nimbleREST library testing.
		
		String username = config.getUsername();
		String password = config.getPassword();
		String ipAddress = config.getIpAddress();
		String volumeName = config.getVolumeName();
		String baseSnapshotName = config.getBaseSnapshotName();
		String cloneName = config.getCloneName();
		//String initiatorGroupName = "UCS3-iGroup";
		
		
		// Retrieve Nimble array auth token.
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		actionLogger.addInfo("Session Token: " + token);
		
		// Get volume ID for 'volumeName'.
		String volumeJsonData = new GetVolumes(ipAddress, token).getSummary();
				
		String volID = new GetVolumesSummaryResponse(volumeJsonData).getVolumeID(volumeName);

		if( !volID.equals(null) ) {
			
			actionLogger.addInfo("Volume ID for volume "+ volumeName +" is: " +volID);
		}
		
		// Get snapshot ID for volume snapshot 'baseSnapshotName'.
		String volumeSnapshotJsonData = new GetSnapshots(ipAddress, token, volID).getSnapshotSummary();
		
		actionLogger.addInfo("Snapshot ID for snapshot "+baseSnapshotName+": " +new GetSnapshotDetailResponse(volumeSnapshotJsonData).getSnapshotID(baseSnapshotName));
	
		String snapID = new GetSnapshotDetailResponse(volumeSnapshotJsonData).getSnapshotID(baseSnapshotName);
		
		// Initiator Groups
		//String iGroupJsonData = new GetInitiatorGroups(ipAddress, token).getInitiatorGroupSummary();
		
		//actionLogger.addInfo("Initiator Group ID: " +new GetInitiatorGroupsDetailResponse(iGroupJsonData).getInitiatorGroupID(initiatorGroupName));
		 
		String response = new VolumeClone(ipAddress, token).create(cloneName, snapID);
		
		actionLogger.addInfo("Create clone response: " + response);

		
			
		
		
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
