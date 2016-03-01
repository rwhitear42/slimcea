package com.cloupia.feature.slimcea.tasks;

import org.apache.log4j.Logger;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;


public class SlimceaUnMapVolFromIgroupTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( SlimceaUnMapVolFromIgroupTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		SlimceaUnMapVolFromIgroupConfig config = (SlimceaUnMapVolFromIgroupConfig) context.loadConfigObject();

		logger.info("Username: " +config.getUsername());
		logger.info("Password: " +config.getPassword());
		logger.info("IP Address: " +config.getIpAddress());
		logger.info("Volume Name: " +config.getVolumeName());
		logger.info("Initiator Group Name: " +config.getInitiatorGroupName());
		
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new SlimceaUnMapVolFromIgroupConfig();
	}

	@Override
	public String getTaskName() {
		return SlimceaUnMapVolFromIgroupConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
