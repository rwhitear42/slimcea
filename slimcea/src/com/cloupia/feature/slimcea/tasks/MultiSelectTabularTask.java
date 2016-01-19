package com.cloupia.feature.slimcea.tasks;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;

/**
 * This is a simple task that just prints out multiselect tabular values  in the logs.
 * It also demonstrates how you can consume output from another task.
 *
 */
public class MultiSelectTabularTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context, CustomActionLogger actionLogger) throws Exception {
		long configEntryId = context.getConfigEntry().getConfigEntryId();
		MultiSelectTabularConfig config = (MultiSelectTabularConfig) context.loadConfigObject();

		if (config == null)
		{
			throw new Exception("No multiselect tabular values configuration found for custom action " + context.getActionDef().getName()
					+ " entryId " + configEntryId);
		}

		actionLogger.addInfo("MultiSelect Tabular Values: " + config.getTabularValues());
		actionLogger.addInfo("the task type is: " + this.getTaskType());
		
	}
  /**
   * This method register/enable the config 
   */
	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new MultiSelectTabularConfig();
	}
     /**
	   * This method returns the task name  
	   */
	@Override
	public String getTaskName() {
		return MultiSelectTabularConfig.displayLabel;
	}
	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		return null;
	}

}
