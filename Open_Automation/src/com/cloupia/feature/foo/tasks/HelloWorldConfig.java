package com.cloupia.feature.foo.tasks;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.feature.foo.constants.FooConstants;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.customactions.UserInputField;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

@PersistenceCapable(detachable = "true", table = "foo_helloworldconfig")
public class HelloWorldConfig implements TaskConfigIf {

	public static final String displayLabel = "HelloWorldConfig";
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
