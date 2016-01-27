package com.cloupia.feature.slimcea.tasks;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.customactions.UserInputField;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

@PersistenceCapable(detachable = "true", table = "slimcea_slimceacreatevolumetask")
public class SlimceaCreateVolumeConfig implements TaskConfigIf {

	public static final String displayLabel = "Nimble Create Volume";
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

	@FormField(label = "Nimble Volume Name", help = "Enter Nimble Volume Name", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = SlimceaConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String             volumeName;

	@FormField(label = "Nimble Volume Size GB", help = "Enter Nimble Volume Size in GB", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = SlimceaConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String             volumeSizeGB;
	
	@FormField(label = "Nimble Volume Description", help = "Enter Nimble Volume Description", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = SlimceaConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String             description;
	
	@FormField(label = "Nimble Volume Data Encryption", help = "Should Volume be Encrypted?", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_BOOLEAN)
	@UserInputField(type = SlimceaConstants.BOOLEAN)
	@Persistent
	private String             dataEncryption;
	
	@FormField(label = "Nimble Volume Cache Pinning", help = "Should Volume be Pinned in Cache?", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_BOOLEAN)
	@UserInputField(type = SlimceaConstants.BOOLEAN)
	@Persistent
	private String             cachePinning;
	
	@FormField(label = "Nimble Volume Performance Policy", help = "Select an Appropriate Performance Policy", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = SlimceaConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String             perfPolicy;

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
	
	public String getVolumeName() {
		return volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}
	
	public String getVolumeSizeGB() {
		return volumeSizeGB;
	}

	public void setVolumeSizeGB(String volumeSizeGB) {
		this.volumeSizeGB = volumeSizeGB;
	}
	
	public String getVolumeDescription() {
		return description;
	}

	public void setVolumeDescription(String description) {
		this.description = description;
	}
	
	public String getVolumeDataEncryption() {
		return dataEncryption;
	}

	public void setVolumeDataEncryption(String dataEncryption) {
		this.dataEncryption = dataEncryption;
	}
	
	public String getVolumeCachePinning() {
		return cachePinning;
	}

	public void setVolumeDataCachePinning(String cachePinning) {
		this.cachePinning = cachePinning;
	}
	
	public String getVolumePerfPolicy() {
		return perfPolicy;
	}

	public void setVolumePerfPolicy(String perfPolicy) {
		this.perfPolicy = perfPolicy;
	}

}
