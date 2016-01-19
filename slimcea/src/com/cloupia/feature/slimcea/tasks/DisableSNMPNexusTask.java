package com.cloupia.feature.slimcea.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class DisableSNMPNexusTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		DisableSNMPNexusConfig config = (DisableSNMPNexusConfig) context.loadConfigObject();

		JSch jsch = new JSch();
		Session session = jsch.getSession(config.getLogin(), config.getIpAddress(), 22);
		session.setPassword(config.getPassword());

		// Avoid asking for key confirmation
		Properties prop = new Properties();
		prop.put("StrictHostKeyChecking", "no");
		session.setConfig(prop);
		session.setTimeout(1000 * 30); //30 seconds
		session.connect();

		Channel channel = session.openChannel("shell");//only shell  
		PrintStream shellStream = new PrintStream(channel.getOutputStream());  // printStream for convenience 
		BufferedReader fromServer = new BufferedReader(new InputStreamReader(channel.getInputStream()));
		channel.connect(); 

		waitUntilPromptReady(fromServer);

		String enableConfig = "config";
		shellStream.println(enableConfig); 
		shellStream.flush();
		waitUntilCommandComplete("one", fromServer);

		String disableSNMP = "no snmp-server community whitney ro";
		shellStream.println(disableSNMP); 
		shellStream.flush();
		waitUntilCommandComplete(null, fromServer);

		channel.disconnect();
		session.disconnect();
	}

	private void waitUntilPromptReady(BufferedReader fromServer) throws IOException {
		StringBuffer sb = new StringBuffer();
		while (true) {
			String line = fromServer.readLine();
			System.out.println(line);
			sb.append(line);
			if (sb.toString().indexOf("http://www.opensource.org/licenses/lgpl-2.1.php") != -1) {
				break;
			}
		}
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
		return new DisableSNMPNexusConfig();
	}

	@Override
	public String getTaskName() {
		return DisableSNMPNexusConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
