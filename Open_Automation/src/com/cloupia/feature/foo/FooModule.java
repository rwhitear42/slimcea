package com.cloupia.feature.foo;

import org.apache.log4j.Logger;

import com.cloupia.feature.foo.accounts.AccountSystemTaskReport;
import com.cloupia.feature.foo.accounts.DummyAccount;
import com.cloupia.feature.foo.accounts.FooAccount;
import com.cloupia.feature.foo.accounts.FooAccountSampleReport;
import com.cloupia.feature.foo.accounts.handler.FooTestConnectionHandler;
import com.cloupia.feature.foo.accounts.inventory.FooConvergedStackBuilder;
import com.cloupia.feature.foo.accounts.inventory.FooInventoryItemHandler;
import com.cloupia.feature.foo.accounts.inventory.FooInventoryListener;
import com.cloupia.feature.foo.constants.FooConstants;
import com.cloupia.feature.foo.drilldownreports.FooAccountSampleDrillDownReport;
import com.cloupia.feature.foo.dummyOne.reports.DummyOneSampleReport;
import com.cloupia.feature.foo.lovs.SimpleLovProvider;
import com.cloupia.feature.foo.lovs.SimpleTabularProvider;
import com.cloupia.feature.foo.menuProvider.DummyMenuProvider;
import com.cloupia.feature.foo.resourceComputer.DummyVLANResourceComputer;
import com.cloupia.feature.foo.scheduledTasks.DummyScheduleTask;
import com.cloupia.feature.foo.tasks.CreateGroupTask;
import com.cloupia.feature.foo.tasks.DisableSNMPNexusTask;
import com.cloupia.feature.foo.tasks.EmailDatacentersTask;
import com.cloupia.feature.foo.tasks.EnableSNMPNexusTask;
import com.cloupia.feature.foo.tasks.HelloWorldTask;
import com.cloupia.feature.foo.tasks.MultiSelectTabularTask;
import com.cloupia.feature.foo.tasks.RollbackHelloWorldTask;
import com.cloupia.feature.foo.tasks.FooTaskContextTask;
import com.cloupia.feature.foo.triggers.MonitorDummyDeviceStatusParam;
import com.cloupia.feature.foo.triggers.MonitorDummyDeviceType;
import com.cloupia.lib.connector.ConfigItemDef;
import com.cloupia.lib.connector.account.AccountTypeEntry;
import com.cloupia.lib.connector.account.PhysicalAccountTypeManager;
import com.cloupia.model.cIM.InfraAccountTypes;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.service.cIM.inframgr.AbstractCloupiaModule;
import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.CustomFeatureRegistry;
import com.cloupia.service.cIM.inframgr.collector.controller.CollectorFactory;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReport;
import com.cloupia.service.cIM.inframgr.thresholdmonitor.MonitoringTrigger;
import com.cloupia.service.cIM.inframgr.thresholdmonitor.MonitoringTriggerUtil;
import com.cloupia.feature.foo.workflow.WorkflowInputTypeDeclaration;
import com.cloupia.feature.foo.multiselecttabularreports.MultiSelectTabularReport;
import com.cloupia.feature.foo.workflow.InputTypeDeclaration;

public class FooModule extends AbstractCloupiaModule {
	
	private static Logger logger = Logger.getLogger(FooModule.class);

	@Override
	public AbstractTask[] getTasks() {
		AbstractTask task1 = new CreateGroupTask();
		AbstractTask task2 = new EmailDatacentersTask();
		AbstractTask task3 = new HelloWorldTask();
		AbstractTask task4 = new EnableSNMPNexusTask();
		AbstractTask task5 = new DisableSNMPNexusTask();
		AbstractTask task6 = new RollbackHelloWorldTask();
		AbstractTask task7 = new FooTaskContextTask();
		AbstractTask task8 = new MultiSelectTabularTask();
		AbstractTask[] tasks = new AbstractTask[8];
		tasks[0] = task1;
		tasks[1] = task2;
		tasks[2] = task3;
		tasks[3] = task4;
		tasks[4] = task5;
		tasks[5] = task6;
		tasks[6] = task7;
		tasks[7] = task8;
		return tasks;
	}

	/**
	 * Getcollectors is not required for the creating new account type.
	 * getCollectors method be deprecated for the new account type.
	 * 
	 */
	@Override
	public CollectorFactory[] getCollectors() {
		return null;
	}

	@Override
	public CloupiaReport[] getReports() {
		//this is where you register all your top level reports, i'm only registering the report
		//extending genericinfraaccountreport because all my other reports are actually drilldown
		//reports of that report
		FooAccountSampleDrillDownReport drilReport = new FooAccountSampleDrillDownReport("foo.drilldown.report", "Drill Down", DummyAccount.class);
		
		CloupiaReport[] reports = new CloupiaReport[5];		
		reports[0] = new DummyOneSampleReport();
		reports[1] = new FooAccountSampleReport();
		
		reports[2] = drilReport;
		reports[3] = new AccountSystemTaskReport();
		reports[4] = new MultiSelectTabularReport();
		return reports;
	}

	@Override
	public void onStart(CustomFeatureRegistry cfr) {
		//this is where you would register stuff like scheduled tasks or resource computers

		//when registering new resource types to limit, you need to provide an id to uniquely identify the resource,
		//a description of how that resource is computed, and an instance of the computer itself
		this.registerResourceLimiter(FooConstants.DUMMY_VLAN_RESOURCE_TYPE, FooConstants.DUMMY_VLAN_RESOURCE_DESC, 
				new DummyVLANResourceComputer());

		try {
			//this is where you should register LOV providers for use in SimpleDummyAction
			cfr.registerLovProviders(SimpleLovProvider.SIMPLE_LOV_PROVIDER, new SimpleLovProvider());
			//you need to provide a unique id for this tabular provider, along with the implementation class, and the
			//index of the selection and display columns, for most cases, you can blindly enter 0
			cfr.registerTabularField(SimpleTabularProvider.SIMPLE_TABULAR_PROVIDER, SimpleTabularProvider.class, "0", "0");
			//this is where you should add your schedule tasks
			addScheduleTask(new DummyScheduleTask());
			
			//registering new report context for use in my dummy menu, good rule of thumb, always register your contexts
			//as early as possible, this way you won't run into any cases where the context does not exist yet and causes
			//an issue elsewhere in the code!
			ReportContextRegistry.getInstance().register(FooConstants.DUMMY_CONTEXT_ONE, FooConstants.DUMMY_CONTEXT_ONE_LABEL);

			//FooAccount 
			ReportContextRegistry.getInstance().register(FooConstants.INFRA_ACCOUNT_TYPE, FooConstants.INFRA_ACCOUNT_LABEL);
			
			//Foo Drill down REport 
			ReportContextRegistry.getInstance().register(FooConstants.FOO_ACCOUNT_DRILLDOWN_NAME, FooConstants.FOO_ACCOUNT_DRILLDOWN_LABEL);
			
			//register the left hand menu provider for the menu item i'm introducing
			DummyMenuProvider menuProvider = new DummyMenuProvider();
			
			//Workflow input Types
			WorkflowInputTypeDeclaration.registerWFInputs();
			//Workflow input Types for multi select
			InputTypeDeclaration.registerWFInputs();
			
			//adding new monitoring trigger, note, these new trigger components utilize the dummy context one i've just registered
			//you have to make sure to register contexts before you execute this code, otherwise it won't work
	        MonitoringTrigger monTrigger = new MonitoringTrigger(new MonitorDummyDeviceType(), new MonitorDummyDeviceStatusParam());
	        MonitoringTriggerUtil.register(monTrigger);
			menuProvider.registerWithProvider();
			//support for new Account Type
			createAccountType();
		} catch (Exception e) {
			logger.error("Foo Module error registering components.", e);
		}
		
	}
	
	
	/**
	 * Creating New Account Type
	 */
	private void createAccountType(){
		AccountTypeEntry entry=new AccountTypeEntry();
		// This is mandatory, hold the information for device credential details
		entry.setCredentialClass(FooAccount.class);
		
		// This is mandatory, type of the Account will be shown in GUI as drill
		// down box
		entry.setAccountType(FooConstants.INFRA_ACCOUNT_TYPE);
		
		// This is mandatory, label of the Account
		entry.setAccountLabel(FooConstants.INFRA_ACCOUNT_LABEL);
		
		// This is mandatory, specify the category of the account type ie.,
		// Network / Storage / //Compute
		entry.setCategory(InfraAccountTypes.CAT_STORAGE);
		
		//This is mandatory
		entry.setContextType(ReportContextRegistry.getInstance().getContextByName(FooConstants.INFRA_ACCOUNT_TYPE).getType());
		
		// This is mandatory, on which accounts either physical or virtual
		// account , new account //type belong to.
		entry.setAccountClass(AccountTypeEntry.PHYSICAL_ACCOUNT);
		// Optional , prefix of the task
		entry.setInventoryTaskPrefix("Open Automation Inventory Task");
		
		//Optional. Group inventory system tasks under this folder. 
		//By default it is grouped under General Tasks
		entry.setWorkflowTaskCategory("Foo Tasks");
		// Optional , collect the inventory frequency, whenever required you can
		// change the
		// inventory collection frequency, in mins.
		entry.setInventoryFrequencyInMins(15);
		// This is mandatory,under which pod type , the new account type is
		// applicable.
		entry.setPodTypes(new String[] { "FlexPod" });
		
		
		// This is optional, dependents on the need of session for collecting
		// the inventory
		//entry.setConnectorSessionFactory(new FooSessionFactory());
		
		// This is mandatory, to test the connectivity of the new account. The
		// Handler should be of type PhysicalConnectivityTestHandler.
		entry.setTestConnectionHandler(new FooTestConnectionHandler());
		// This is mandatory, we can implement inventory listener according to
		// the account Type , collect the inventory details.
		entry.setInventoryListener(new FooInventoryListener());
		
		//This is mandatory , to show in the converged stack view
		entry.setConvergedStackComponentBuilder(new FooConvergedStackBuilder());
		
		//This is required to show up the details of the stack view in the GUI 
		//entry.setStackViewItemProvider(new FooStackViewProvider());
		
		// This is required credential.If the Credential Policy support is
		// required for this Account type then this is mandatory, can implement
		// credential check against the policyname.
		//entry.setCredentialParser(new FooAccountCredentialParser());
				try {

					// Adding inventory root
					registerInventoryObjects(entry);
					PhysicalAccountTypeManager.getInstance().addNewAccountType(entry);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			private void registerInventoryObjects(
					AccountTypeEntry fooRecoverPointAccountEntry) {
				ConfigItemDef fooRecoverPointStateInfo = fooRecoverPointAccountEntry
						.createInventoryRoot("foo.inventory.root",
								FooInventoryItemHandler.class);
			}

}
