package com.cloupia.feature.slimcea.tasks;

import java.util.Map;

import org.apache.log4j.Logger;

import com.cisco.cuic.api.models.accounts.AddGroupConfig;
import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.cloupia.service.cIM.tree.MoTaskAPI;
/**
 * This task demonstrates how you can utilize REST APIs with a shortcut through open automation.
 * Technically speaking, you could utilize the REST API SDK as you normally would, however, if you'd like
 * there is this shortcut we're demonstrating here.
 *
 */
public class CreateGroupTask extends AbstractTask
{

	static Logger logger = Logger.getLogger(CreateGroupTask.class);


	public void executeCustomAction(CustomActionTriggerContext context, CustomActionLogger actionLogger) throws Exception
	{

		long configEntryId = context.getConfigEntry().getConfigEntryId();
		//retrieving the corresponding config object for this handler
		CreateGroupConfig config = (CreateGroupConfig) context.loadConfigObject();

		if (config == null)
		{
			throw new Exception("No Group config found for custom action " + context.getActionDef().getName()
					+ " entryId " + configEntryId);
		}

		//the MoTaskAPI is sort of a shortcut way to use the REST API
		MoTaskAPI api = MoTaskAPI.getInstance();

		//in order to utilize MoTaskAPI you need to make sure to use the REST model POJOs
		//NOTE: i am passing null to the constructor, normally, you need to pass an instance of CuicServer to
		//the REST POJOs, however, in this shortcut we do not need to provide these details!
		AddGroupConfig gr = new AddGroupConfig(null);
		gr.setGroupName(config.getGroupName());
		gr.setFirstName(config.getFirstName());
		gr.setLastName(config.getLastName());
		gr.setGroupContact(config.getGroupContact());
		
		//now you can simply execute the task here, you just need to pass in a username (this is mostly used
		//for logging purposes) and the config object of the REST API you're trying to access
		Map<String,String> response = api.executeTask("admin", gr);

		actionLogger.addInfo("created a group with response : " + response.toString());
		
	}

	@Override
	public String getTaskName() {
		return "Slimcea Create Group";
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		return null;
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new CreateGroupConfig();
	}



}
