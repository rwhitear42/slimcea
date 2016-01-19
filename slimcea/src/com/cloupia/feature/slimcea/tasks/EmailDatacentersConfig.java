package com.cloupia.feature.slimcea.tasks;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

//the table name MUST begin with your feature ID, this is enforced to prevent developers from potentially
//interfering with crucial CUIC tables
@PersistenceCapable(detachable = "true", table = "slimcea_email_datacenters_config")
public class EmailDatacentersConfig implements TaskConfigIf
{

    public static final String HANDLER_NAME = "Send Email of Datacenters";

    //configEntryId and actionId are mandatory fields
	@Persistent
    private long               configEntryId;
    @Persistent
    private long               actionId;
    
    //for this workflow task I need to know the e-mail address to send to, so I include
    //a user input field which will be populated by a user when this task is executed
	@FormField(label = "E-mail Addresses", help = "Comma separated additional E-mail addresses", mandatory = true, maxLength = 255, size = FormFieldDefinition.FIELD_SIZE_MEDIUM)
    @Persistent
    private String             addresses;
    
    public EmailDatacentersConfig()
    {
        
    }

    public long getConfigEntryId()
    {
        return configEntryId;
    }

    public void setConfigEntryId(long configEntryId)
    {
        this.configEntryId = configEntryId;
    }

    public long getActionId()
    {
        return actionId;
    }

    public void setActionId(long actionId)
    {
        this.actionId = actionId;
    }

    public String getAddresses()
    {
        return addresses;
    }

    public void setAddresses(String addresses)
    {
        this.addresses = addresses;
    }

    @Override
    public String getDisplayLabel()
    {
        return HANDLER_NAME;
    }        
    
}
