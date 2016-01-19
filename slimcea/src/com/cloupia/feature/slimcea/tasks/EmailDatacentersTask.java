package com.cloupia.feature.slimcea.tasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.feature.slimcea.pojo.EmailMessageRequest;
import com.cloupia.feature.slimcea.util.MailManager;
import com.cloupia.fw.objstore.ObjStore;
import com.cloupia.fw.objstore.ObjStoreHelper;
import com.cloupia.model.cIM.DataCenter;
import com.cloupia.model.cIM.MailSettings;
import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;

public class EmailDatacentersTask extends AbstractTask
{

    static Logger logger = Logger.getLogger(EmailDatacentersTask.class);
    
    @Override
    public void executeCustomAction(CustomActionTriggerContext context, CustomActionLogger actionLogger) throws Exception
    {

        long configEntryId = context.getConfigEntry().getConfigEntryId();
        //retrieving the corresponding config object for this handler
        EmailDatacentersConfig config = (EmailDatacentersConfig) context.loadConfigObject();

        if (config == null)
        {
            throw new Exception("No email configuration found for custom action " + context.getActionDef().getName()
                    + " entryId " + configEntryId);
        }
        
        String toAddresses = "";
        //retrieving the e-mail addresses from the config object
        if(config.getAddresses() != null && config.getAddresses().trim().length() >0)
        {
            toAddresses = config.getAddresses();
        }

        if ((toAddresses == null) || (toAddresses.trim().length() == 0))
        {
            throw new Exception("No email addresses specified in custom action " + context.getActionDef().getName()
                    + " entryId " + configEntryId);
        }

        String[] toAddrList = toAddresses.split(",");
        
        EmailMessageRequest message = new EmailMessageRequest();

        for (int i = 0; i < toAddrList.length; i++)
        {
            toAddrList[i] = toAddrList[i].trim();
        }
        
        String subject = "This is the current list of data centers";
        String body = "";
        
        //retrieving the list of all datacenters through this CUIC API
       // List<DataCenter> dcs = InfraPersistenceUtil.getDataCenterList();
        ObjStore<DataCenter> catStore = ObjStoreHelper.getStore(DataCenter.class);

        List<DataCenter> dcs = catStore.queryAll();
        
        for (DataCenter dc: dcs) {
        	body += dc.getDcName() + "\r\n";
        }
        
        //logging execution of custom logic taking place
        actionLogger.addDebug("Sending email to " + toAddresses + ", with Subject " + subject);
  
        //creating e-mail
        message.setToAddrs(getValidAddressList(toAddrList));
        message.setSubject(subject);
        message.setMessageBody(body);
        message.setContentType("text/plain");

        //getting mail server details 
        MailSettings settings = (MailSettings) ObjStoreHelper.getStore(MailSettings.class).getSingleton();
        message.setFromAddress(settings.getFromEmailAddress());

        //sending e-mail with cloupia library utility
        MailManager.sendEmail(
                "CUST-EMAIL-ACTTION-" + context.getActionDef().getId() + "-" + System.currentTimeMillis(), settings,
                message);

        try
        {
            context.saveOutputValue(SlimceaConstants.EMAIL_TASK_OUTPUT_NAME, toAddresses);

        } catch (Exception e)
        {
            actionLogger.addWarning("Action " + EmailDatacentersConfig.HANDLER_NAME + ":" + e.getMessage());
        }
    
        
    }

    public static String[] getValidAddressList(String[] addrs)
    {
        if(addrs == null || addrs.length == 0)
            return new String[0];
        
        List<String> addrsList = new ArrayList<String>();
        for(String str : addrs)
        {
            //TODO add email format validation.
            if(str != null && str.trim().length() > 0)
            {
                addrsList.add(str.trim());                
            }
        }
        
        String[] newAddrsList = new String[addrsList.size()];
        addrsList.toArray(newAddrsList);
        
        return newAddrsList;        
    }

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new EmailDatacentersConfig();
	}

	@Override
	public String getTaskName() {
		return EmailDatacentersConfig.HANDLER_NAME;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		TaskOutputDefinition[] ops = new TaskOutputDefinition[1];
		//NOTE: If you want to use the output of this task as input to another task. Then the second argument 
		//of the output definition MUST MATCH the type of UserInputField in the config of the task that will
		//be receiving this output.  Take a look at HelloWorldConfig as an example.
		ops[0] = new TaskOutputDefinition(
				SlimceaConstants.EMAIL_TASK_OUTPUT_NAME,
				SlimceaConstants.SLIMCEA_HELLO_WORLD_NAME,
				"EMAIL IDs");
		return ops;
	}

}
