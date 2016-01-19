package com.cloupia.feature.slimcea.tasks;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.customactions.UserInputField;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

@PersistenceCapable(detachable = "true", table = "slimcea_create_group_task_config")
public class CreateGroupConfig implements TaskConfigIf {

	public static final String displayLabel = "Add group using REST API's";
	@Persistent
	private long configEntryId;
	@Persistent
	private long actionId;

	

	@FormField(label = "Group name", help = "Input for Group name", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@Persistent
	private String groupName;
	
	@FormField(label = "First name", help="Input for First name", mandatory=true)
	@Persistent
	private String firstName;
	
	@FormField(label = "Last name", help ="Input for Last name", mandatory=true)
	@Persistent
	private String lastName;
	
	@FormField(label = "Group contact", help ="Input for Group contact", mandatory = true)
	@Persistent
	private String groupContact;
		
	
	@Override
	public long getActionId() {
	
		return this.actionId;
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
	public void setActionId(long arg0) {
		this.actionId = arg0;

	}

	@Override
	public void setConfigEntryId(long arg0) {
		this.configEntryId = arg0;

	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGroupContact() {
		return groupContact;
	}

	public void setGroupContact(String groupContact) {
		this.groupContact = groupContact;
	}

	
	
	

}
