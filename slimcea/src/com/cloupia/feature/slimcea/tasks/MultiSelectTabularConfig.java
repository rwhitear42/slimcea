

package com.cloupia.feature.slimcea.tasks;

import javax.jdo.annotations.PersistenceCapable;


import javax.jdo.annotations.Persistent;

import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.customactions.UserInputField;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;
import com.cloupia.feature.slimcea.lovs.SimpleTabularProvider;
import com.cloupia.model.cIM.FormFieldDefinition;

/**
 * This class is used for multiselect workflow inputs and task inputs ,user can select multiple inputs whenever they want.
 */
@PersistenceCapable(detachable = "true", table = "slimcea_multiselecttabularconfig")
public class MultiSelectTabularConfig implements TaskConfigIf {

	public static final String displayLabel = "MultiSelectTabularConfig";
	@Persistent
	private long configEntryId;
	@Persistent
	private long actionId;
	
	/**
	 * This field is multi select field user can select multi no of tabular values as input.
	 */
	
	@FormField(label = "TabularValues", help = "TabularValues ", multiline = true,type=FormFieldDefinition.FIELD_TYPE_TABULAR_POPUP, table=SimpleTabularProvider.SIMPLE_TABULAR_PROVIDER)
	@UserInputField(type = SlimceaConstants.SLIMCEA_MULTI_SELECT_TABULARVALUE_NAME)
	@Persistent
	private String tabularvalues;
	
	public String getTabularValues() {
		return tabularvalues;
	}

	public void setTabularValues(String tabularvalues) {
		this.tabularvalues = tabularvalues;
	}

	/**
	 * This method return actionId usiing which specific action can performed.
	 */
	@Override
	public long getActionId() {
		return actionId;
	}
	/**
	 * This method returns configentryId which is unique for each task.
	 */
	@Override
	public long getConfigEntryId() {
		return configEntryId;
	}
	
  
	/**
	 * This method set the actionId.
	 */
	@Override
	public void setActionId(long actionId) {
		this.actionId = actionId;
	}
	/**
	 * This method set the configentryId.
	 */
	@Override
	public void setConfigEntryId(long configEntryId) {
		this.configEntryId = configEntryId;
	}
	@Override
	public String getDisplayLabel() {
		// TODO Auto-generated method stub
		return displayLabel;
	}
	public MultiSelectTabularConfig(){
		
	}

	

}
