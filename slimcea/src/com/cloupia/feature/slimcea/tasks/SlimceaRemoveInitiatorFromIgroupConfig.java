package com.cloupia.feature.slimcea.tasks;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.customactions.UserInputField;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

@PersistenceCapable(detachable = "true", table = "slimcea_removeinitiatorfromigrouptask")
public class SlimceaRemoveInitiatorFromIgroupConfig implements TaskConfigIf {

	public static final String displayLabel = "Nimble Remove Initiator from Initiator Group";
	@Persistent
	private long configEntryId;
	@Persistent
	private long actionId;
	
	@FormField(label = "Nimble Array IP", help = "Nimble Array IP Address", mandatory = true)
	@UserInputField(type = WorkflowInputFieldTypeDeclaration.IPADDRESS)
	@Persistent
	private String             ipAddress         = "";

	@FormField(label = "Nimble Username", help = "Nimble username", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = SlimceaConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String             username;

	@FormField(label = "Password", help = "Password", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_PASSWORD)
	@UserInputField(type = SlimceaConstants.PASSWORD)
	@Persistent
	private String             password;

	@FormField(label = "Nimble Initator Group Name", help = "Enter Nimble Initator Group Name", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = SlimceaConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String             initiatorGroupName;

	@FormField(label = "Nimble Initator Name", help = "Enter Nimble Initator Name (Starting with wwpn or iqn)", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = SlimceaConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String             initiatorName;

	public SlimceaRemoveInitiatorFromIgroupConfig() {

	}
	
	public SlimceaRemoveInitiatorFromIgroupConfig(SlimceaAddInitiatorToIgroupConfig config) {
		this.ipAddress = config.getIpAddress();
		this.username = config.getUsername();
		this.password = config.getPassword();
		this.initiatorGroupName = config.getInitiatorGroupName();
		this.initiatorName = config.getInitiatorName();
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public String getInitiatorGroupName() {
		return initiatorGroupName;
	}

	public void setInitiatorGroupName(String initiatorGroupName) {
		this.initiatorGroupName = initiatorGroupName;
	}

	public String getInitiatorName() {
		return initiatorName;
	}

	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}
	
}
