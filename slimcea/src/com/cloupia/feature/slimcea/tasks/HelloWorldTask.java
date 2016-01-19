package com.cloupia.feature.slimcea.tasks;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;

/**
 * This is a simple task that just prints out "Hello World: name" in the logs.
 * It also demonstrates how you can consume output from another task.
 * You can follow up the EmailDatacentersTask with this task, and configure it
 * such that this task consumes the output from that task as its input.
 *
 */
public class HelloWorldTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context, CustomActionLogger actionLogger) throws Exception {
		long configEntryId = context.getConfigEntry().getConfigEntryId();
		//retrieving the corresponding config object for this handler
		HelloWorldConfig config = (HelloWorldConfig) context.loadConfigObject();

		if (config == null)
		{
			throw new Exception("No hello world configuration found for custom action " + context.getActionDef().getName()
					+ " entryId " + configEntryId);
		}

		actionLogger.addInfo("Hello World: " + config.getLogin());
		actionLogger.addInfo("the task type is: " + this.getTaskType());
		
		//this is how you would register a roll back task, the first two values are displayed in the roll back
		//workflow UI.  the following two values are basically descriptions for what has taken place in the current task.
		//the final two values are what is important, you need to use the getTaskType() method for the task will
		//rollback with and the config to go with that task.  Usually we have a constructor for the rollback config
		//that takes in the current config so we know all the details in which the original task was executed.
		context.getChangeTracker().undoableResourceAdded("assetType", "idString", "Hello World", 
				"Hello World task", 
				new RollbackHelloWorldTask().getTaskName(), new RollbackHelloWorldConfig(config));
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new HelloWorldConfig();
	}

	@Override
	public String getTaskName() {
		return HelloWorldConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		return null;
	}

}
