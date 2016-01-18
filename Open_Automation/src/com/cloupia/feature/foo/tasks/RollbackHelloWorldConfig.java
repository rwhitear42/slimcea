package com.cloupia.feature.foo.tasks;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.feature.foo.constants.FooConstants;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.customactions.UserInputField;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

/**
 * This is just a copy of Hello World Config to demonstrate how to use roll back.  For a real example that
 * actually does something you may want to take a look at DisableSNMPNexusConfig.  
 *
 */
@PersistenceCapable(detachable = "true", table = "foo_rollbackhelloworldconfig")
public class RollbackHelloWorldConfig implements TaskConfigIf {

	public static final String displayLabel = "RollbackHelloWorldConfig";
	@Persistent
	private long configEntryId;
	@Persistent
	private long actionId;
	
	//This field is supposed to consume output from the EmailDatacentersTask, you'll see the
	//type in user input field below matches the output type in EmailDatacentersTask's output definition.
	@FormField(label = "Name", help = "Name passed in from previous task", mandatory = true)
	@UserInputField(type = FooConstants.FOO_HELLO_WORLD_NAME)
    @Persistent
    private String             login;
	
	public RollbackHelloWorldConfig() {
		
	}
	
	/**
	 * Note this constructor takes in a HelloWorldConfig which is the task that initiates the rollback.
	 * The idea here is you can pass all the details from the original config to this config that way
	 * you know exactly what was done in the original task and what needs to be "rollbacked".
	 */
	public RollbackHelloWorldConfig(HelloWorldConfig config) {
		this.login = config.getLogin();
	}

	@Override
	public long getActionId() {
		return actionId;
	}

	@Override
	public long getConfigEntryId() {
		return configEntryId;
	}

	@Override
	public String getDisplayLabel() {
		return displayLabel;
	}

	@Override
	public void setActionId(long actionId) {
		this.actionId = actionId;
	}

	@Override
	public void setConfigEntryId(long configEntryId) {
		this.configEntryId = configEntryId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
