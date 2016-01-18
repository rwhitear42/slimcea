package com.cloupia.feature.foo.tasks;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.model.cIM.GlobalConstants;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.customactions.UserInputField;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

@PersistenceCapable(detachable = "true", table = "foo_context_task")
public class FooTaskContextConfig implements TaskConfigIf {

	public static final String displayLabel = "Foo Context Task";
	@Persistent
    private long               configEntryId;
    @Persistent
    private long               actionId;
    //@FormField(label = "Select Device", help = "Select Device", mandatory = true, validate = true, type = FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV)
    //@FormField(label = "Select Device", help = "Select Device", mandatory = true, validate = true, type = FormFieldDefinition.FIELD_TYPE_TABULAR_POPUP, table = GlobalConstants.TABULAR_FIELDS.INFRA_NETWORK_NXOS_5K_7K_MDS_DEVICE_TABULAR_FIELD_REPORT )
    @FormField(label = "Select Device", help = "Select Device",rbid="NetworkController.form.common.field.netdevice", mandatory = true, validate = true, type = FormFieldDefinition.FIELD_TYPE_TABULAR_POPUP, table = GlobalConstants.TABULAR_FIELDS.INFRA_NETWORK_NXOS_DEVICE_TABULAR_FIELD_REPORT_EXCEPT_N1K_N9K_N3K )
    @UserInputField(type = WorkflowInputFieldTypeDeclaration.NETWORKING_DEVICE)
    @Persistent
    private String             netdevice;
    @FormField(label = "VDC Name", help = "VDC Name.",rbid="NetworkController.form.common.field.vdcName",hidden=true, mandatory = true, validate = true,type = FormFieldDefinition.FIELD_TYPE_TABULAR_POPUP, table = GlobalConstants.TABULAR_FIELDS.INFRA_NETWORK_VDC_TABULAR_FIELD_REPORT)
    @UserInputField(type = WorkflowInputFieldTypeDeclaration.NXOS_VDC_IDENTITY)
    @Persistent
    private String             vdcName;
    @FormField(label = "Device Alias Name", help = "Device Alias Name",rbid="NetworkController.form.VDC.field.devAliasName", validate = true,mandatory = true,/*type = FormFieldDefinition.FIELD_TYPE_MULTI_SELECT_LIST*/type = FormFieldDefinition.FIELD_TYPE_TABULAR_POPUP, table = GlobalConstants.TABULAR_FIELDS.INFRA_NETWORK_DEVICE_ALIAS_TABULAR_FIELD_REPORT_FOR_CONFIG,multiline = true)//CSCul12703
    @UserInputField(type = WorkflowInputFieldTypeDeclaration.NETWORK_SAN_DEVICE_ALIAS_IDENTITY)
    @Persistent
    private String             devAliasName;
    @FormField(label = "Copy Running configuration to Startup configuration",rbid="NetworkController.form.common.field.copyRunToStartConfig", help = "Select this option to copy running configuration to startup configuration", type = FormFieldDefinition.FIELD_TYPE_BOOLEAN)
    @Persistent
    private boolean            copyRunToStartConfig = true;
	
	public FooTaskContextConfig() {
		
	}

	/**
	 * @return the configEntryId
	 */
	public long getConfigEntryId() {
		return configEntryId;
	}

	/**
	 * @param configEntryId the configEntryId to set
	 */
	public void setConfigEntryId(long configEntryId) {
		this.configEntryId = configEntryId;
	}

	/**
	 * @return the actionId
	 */
	public long getActionId() {
		return actionId;
	}

	/**
	 * @param actionId the actionId to set
	 */
	public void setActionId(long actionId) {
		this.actionId = actionId;
	}

	/**
	 * @return the netdevice
	 */
	public String getNetdevice() {
		return netdevice;
	}

	/**
	 * @param netdevice the netdevice to set
	 */
	public void setNetdevice(String netdevice) {
		this.netdevice = netdevice;
	}

	/**
	 * @return the vdcName
	 */
	public String getVdcName() {
		return vdcName;
	}

	/**
	 * @param vdcName the vdcName to set
	 */
	public void setVdcName(String vdcName) {
		this.vdcName = vdcName;
	}

	/**
	 * @return the devAliasName
	 */
	public String getDevAliasName() {
		return devAliasName;
	}

	/**
	 * @param devAliasName the devAliasName to set
	 */
	public void setDevAliasName(String devAliasName) {
		this.devAliasName = devAliasName;
	}

	/**
	 * @return the copyRunToStartConfig
	 */
	public boolean isCopyRunToStartConfig() {
		return copyRunToStartConfig;
	}

	/**
	 * @param copyRunToStartConfig the copyRunToStartConfig to set
	 */
	public void setCopyRunToStartConfig(boolean copyRunToStartConfig) {
		this.copyRunToStartConfig = copyRunToStartConfig;
	}

	/**
	 * @return the displaylabel
	 */
	public static String getDisplaylabel() {
		return displayLabel;
	}

	@Override
	public String getDisplayLabel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
