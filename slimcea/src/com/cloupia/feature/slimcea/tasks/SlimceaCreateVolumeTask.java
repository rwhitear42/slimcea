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
import java.util.*;
import com.cloupia.lib.util.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.auth.*;
import org.apache.commons.httpclient.protocol.*;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
//import com.cloupia.lib.cIaaS.vcd.api.*;

import java.io.ByteArrayInputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;

import com.cloupia.feature.slimcea.http.MySSLSocketFactory;
import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.rwhitear.ucsdHttpRequest.UCSDHttpRequest;
import com.rwhitear.ucsdHttpRequest.constants.HttpRequestConstants;


public class SlimceaCreateVolumeTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaCreateVolumeConfig config = (SlimceaCreateVolumeConfig) context.loadConfigObject();

		actionLogger.addInfo("Username: " +config.getUsername());
		actionLogger.addInfo("Password: " +config.getPassword());
		actionLogger.addInfo("IP Address: " +config.getIpAddress());
		actionLogger.addInfo("Volume Name: " +config.getVolumeName());
		actionLogger.addInfo("Volume Size (GB): " +config.getVolumeSizeGB());
		actionLogger.addInfo("Volume Description: " +config.getVolumeDescription());
		actionLogger.addInfo("Data Encryption: " +config.getVolumeDataEncryption());
		actionLogger.addInfo("Cache Pinning: " +config.getVolumeCachePinning());
		actionLogger.addInfo("Performance Policy: " +config.getVolumePerfPolicy());

		
		//
		// Try HttpClient using older libraries.
		//
		/*
		HttpClient httpClient = new HttpClient();

		Protocol.registerProtocol("https", new Protocol("https", new MySSLSocketFactory(), 443));

		httpClient.getHostConfiguration().setHost("10.52.249.102", 443, "https"); 

		//httpClient.getHostConfiguration().setHost("10.52.249.102", 443, "https"); 

		httpClient.getParams().setCookiePolicy("default");

		GetMethod httpMethod = new GetMethod("/");

		httpMethod.addRequestHeader("Content-Type", "application/json");

		httpClient.executeMethod(httpMethod);
		   
		int statusCode = httpMethod.getStatusCode();

		String response = httpMethod.getResponseBodyAsString();

		httpMethod.releaseConnection();

		actionLogger.addInfo("Response: " +response );
		
		actionLogger.addInfo("Status Code: " +statusCode );
		*/
		//
		//
		//
		
		// ucsdHttpRequest testing.
		
		UCSDHttpRequest hr = new UCSDHttpRequest();
		
		hr.setIpAddress("1.1.1.1");
		
		actionLogger.addInfo("ucsdHttpRequest Testing...");
		
		actionLogger.addInfo("IP Address: " +hr.getIpAddress());
		actionLogger.addInfo("Protocol: " +hr.getProtocol());
		actionLogger.addInfo("Port: " +hr.getPort());
		actionLogger.addInfo("Username: " +hr.getUsername());
		actionLogger.addInfo("Password: " +hr.getPassword());
		
		hr.addContentTypeHeader(HttpRequestConstants.CONTENT_TYPE_JSON);
		
		hr.addContentTypeHeader(HttpRequestConstants.CONTENT_TYPE_XML);
		
		ArrayList<String> blarg = hr.getContentTypeHeaders();
		
		for( String iter : blarg ) {
			actionLogger.addInfo("Content-Type header: " +iter);
		}
		
		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
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
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new SlimceaCreateVolumeConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaCreateVolumeConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		TaskOutputDefinition[] ops = new TaskOutputDefinition[1];
		//NOTE: If you want to use the output of this task as input to another task. Then the second argument 
		//of the output definition MUST MATCH the type of UserInputField in the config of the task that will
		//be receiving this output.  Take a look at HelloWorldConfig as an example.
		ops[0] = new TaskOutputDefinition( SlimceaConstants.NIMBLE_VOLUME_NAME, SlimceaConstants.GENERIC_TEXT_INPUT, "Three" );
		return ops;
	}
}
