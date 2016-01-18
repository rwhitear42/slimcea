package com.cloupia.feature.foo.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.cloupia.service.cIM.tree.MoTaskAPI;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class FooTaskContextTask extends AbstractTask {
	private static Logger logger = Logger.getLogger(FooTaskContextTask.class);
	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		
		logger.info(" executeCustomAction ");
		
		FooTaskContextConfig config = (FooTaskContextConfig) context.loadConfigObject();

		if (config == null)
		{
			throw new Exception("No Group config found for custom action " + context.getActionDef().getName()
					+ " entryId " );
		}

		//the MoTaskAPI is sort of a shortcut way to use the REST API
		MoTaskAPI api = MoTaskAPI.getInstance();
		
		
	}

	
	public static void waitUntilCommandComplete(String terminator, BufferedReader fromServer) throws IOException, InterruptedException {
		if (terminator == null) {
			Thread.sleep(1000);
			return;
		}
		StringBuffer sb = new StringBuffer();
		while (true) {
			String line = fromServer.readLine();
			System.out.println(line);
			sb.append(line);
			if (sb.toString().indexOf(terminator) != -1) {
				break;
			}

		}
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new FooTaskContextConfig();
	}

	@Override
	public String getTaskName() {
		return FooTaskContextConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
