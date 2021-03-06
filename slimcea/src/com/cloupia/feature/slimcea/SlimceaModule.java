package com.cloupia.feature.slimcea;

import org.apache.log4j.Logger;

import com.cloupia.feature.slimcea.accounts.SlimceaAccount;
import com.cloupia.feature.slimcea.accounts.SlimceaInitiatorGroupsReport;
import com.cloupia.feature.slimcea.accounts.SlimceaPerformancePoliciesReport;
import com.cloupia.feature.slimcea.accounts.SlimceaVolumeCollectionsReport;
import com.cloupia.feature.slimcea.accounts.SlimceaVolumesReport;
import com.cloupia.feature.slimcea.accounts.handler.SlimceaTestConnectionHandler;
import com.cloupia.feature.slimcea.accounts.inventory.SlimceaConvergedStackBuilder;
import com.cloupia.feature.slimcea.accounts.inventory.SlimceaInventoryItemHandler;
import com.cloupia.feature.slimcea.accounts.inventory.SlimceaInventoryListener;
import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.feature.slimcea.lovs.NimbleSanProtocolLovProvider;
import com.cloupia.feature.slimcea.lovs.SimpleLovProvider;
import com.cloupia.feature.slimcea.lovs.SimpleTabularProvider;
import com.cloupia.feature.slimcea.menuProvider.DummyMenuProvider;
import com.cloupia.feature.slimcea.resourceComputer.DummyVLANResourceComputer;
import com.cloupia.feature.slimcea.scheduledTasks.DummyScheduleTask;
import com.cloupia.feature.slimcea.tasks.SlimceaAddFcInitiatorToIgroupTask;
import com.cloupia.feature.slimcea.tasks.SlimceaAddIscsiInitiatorToIgroupTask;
import com.cloupia.feature.slimcea.tasks.SlimceaAssocVolToVolumeCollectionTask;
import com.cloupia.feature.slimcea.tasks.SlimceaCloneVolumeTask;
import com.cloupia.feature.slimcea.tasks.SlimceaCreateIgroupTask;
import com.cloupia.feature.slimcea.tasks.SlimceaCreateSnapshotTask;
import com.cloupia.feature.slimcea.tasks.SlimceaCreateVolumeTask;
import com.cloupia.feature.slimcea.tasks.SlimceaDeleteIgroupTask;
import com.cloupia.feature.slimcea.tasks.SlimceaDeleteSnapshotTask;
import com.cloupia.feature.slimcea.tasks.SlimceaDeleteVolumeTask;
import com.cloupia.feature.slimcea.tasks.SlimceaDisassocVolFromVolumeCollectionTask;
import com.cloupia.feature.slimcea.tasks.SlimceaGetInventoryTask;
import com.cloupia.feature.slimcea.tasks.SlimceaMapVolumeToIgroupTask;
//import com.cloupia.feature.slimcea.tasks.SlimceaMultiSelectTabularTask;
import com.cloupia.feature.slimcea.tasks.SlimceaRemoveFcInitiatorFromIgroupTask;
import com.cloupia.feature.slimcea.tasks.SlimceaRemoveIscsiInitiatorFromIgroupTask;
import com.cloupia.feature.slimcea.tasks.SlimceaUnMapVolFromIgroupTask;
import com.cloupia.feature.slimcea.triggers.MonitorDummyDeviceStatusParam;
import com.cloupia.feature.slimcea.triggers.MonitorDummyDeviceType;
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
import com.cloupia.feature.slimcea.workflow.WorkflowInputTypeDeclaration;
import com.cloupia.feature.slimcea.reports.SlimceaArrayUsagePieChartReport;
import com.cloupia.feature.slimcea.workflow.InputTypeDeclaration;

public class SlimceaModule extends AbstractCloupiaModule {
	
	private static Logger logger = Logger.getLogger(SlimceaModule.class);

	@Override
	public AbstractTask[] getTasks() {
		AbstractTask task1   = new SlimceaCreateVolumeTask();
		AbstractTask task2   = new SlimceaDeleteVolumeTask();
		AbstractTask task3   = new SlimceaCreateSnapshotTask();
		AbstractTask task4   = new SlimceaDeleteSnapshotTask();
		AbstractTask task5   = new SlimceaCreateIgroupTask();
		AbstractTask task6   = new SlimceaDeleteIgroupTask();
		AbstractTask task7  = new SlimceaAssocVolToVolumeCollectionTask();
		AbstractTask task8  = new SlimceaDisassocVolFromVolumeCollectionTask();
		AbstractTask task9  = new SlimceaMapVolumeToIgroupTask();
		AbstractTask task10  = new SlimceaUnMapVolFromIgroupTask();
		AbstractTask task11  = new SlimceaCloneVolumeTask();
		AbstractTask task12  = new SlimceaAddIscsiInitiatorToIgroupTask();
		AbstractTask task13  = new SlimceaRemoveIscsiInitiatorFromIgroupTask();
		AbstractTask task14  = new SlimceaAddFcInitiatorToIgroupTask();
		AbstractTask task15  = new SlimceaRemoveFcInitiatorFromIgroupTask();
		AbstractTask task16  = new SlimceaGetInventoryTask();
		
		AbstractTask[] tasks = new AbstractTask[16];
		tasks[0]  = task1;
		tasks[1]  = task2;
		tasks[2]  = task3;
		tasks[3]  = task4;
		tasks[4]  = task5;
		tasks[5]  = task6;
		tasks[6]  = task7;
		tasks[7]  = task8;
		tasks[8]  = task9;
		tasks[9]  = task10;		
		tasks[10] = task11;
		tasks[11] = task12;
		tasks[12] = task13;
		tasks[13] = task14;
		tasks[14] = task15;
		tasks[15] = task16;
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
		
		//SlimceaAccountSampleDrillDownReport drilReport = new SlimceaAccountSampleDrillDownReport("slimcea.drilldowntest.report", "Drill Down Test", SlimceaAccount.class);
		
		CloupiaReport[] reports = new CloupiaReport[5];		

		reports[0] = new SlimceaVolumesReport();
		reports[1] = new SlimceaPerformancePoliciesReport();
		reports[2] = new SlimceaInitiatorGroupsReport();
		reports[3] = new SlimceaVolumeCollectionsReport();	
		reports[4] = new SlimceaArrayUsagePieChartReport();
			
		//reports[0] = new SlimceaControllerMembersReport();
		//reports[4] = new SamplePieChartReport();
		//reports[5] = new SampleBarChartReport();
		//reports[6] = new SamplePieChartReport2();
		
		return reports;
		
	}

	@Override
	public void onStart(CustomFeatureRegistry cfr) {
		//this is where you would register stuff like scheduled tasks or resource computers

		//when registering new resource types to limit, you need to provide an id to uniquely identify the resource,
		//a description of how that resource is computed, and an instance of the computer itself
		this.registerResourceLimiter(SlimceaConstants.DUMMY_VLAN_RESOURCE_TYPE, SlimceaConstants.DUMMY_VLAN_RESOURCE_DESC, 
				new DummyVLANResourceComputer());

		try {
			//this is where you should register LOV providers for use in SimpleDummyAction
			cfr.registerLovProviders(SimpleLovProvider.SIMPLE_LOV_PROVIDER, new SimpleLovProvider());
			// Slimcea SAN protocol type LOV provider.
			cfr.registerLovProviders(NimbleSanProtocolLovProvider.NIMBLE_SAN_PROTOCOL_LOV_PROVIDER, new NimbleSanProtocolLovProvider());	
						
			//you need to provide a unique id for this tabular provider, along with the implementation class, and the
			//index of the selection and display columns, for most cases, you can blindly enter 0
			cfr.registerTabularField(SimpleTabularProvider.SIMPLE_TABULAR_PROVIDER, SimpleTabularProvider.class, "0", "0");
			
			//this is where you should add your schedule tasks
			addScheduleTask(new DummyScheduleTask());
			
			//registering new report context for use in my dummy menu, good rule of thumb, always register your contexts
			//as early as possible, this way you won't run into any cases where the context does not exist yet and causes
			//an issue elsewhere in the code!
			ReportContextRegistry.getInstance().register(SlimceaConstants.DUMMY_CONTEXT_ONE, SlimceaConstants.DUMMY_CONTEXT_ONE_LABEL);

			//SlimceaAccount 
			ReportContextRegistry.getInstance().register(SlimceaConstants.INFRA_ACCOUNT_TYPE, SlimceaConstants.INFRA_ACCOUNT_LABEL);
			
			//Slimcea Drill down REport 
			ReportContextRegistry.getInstance().register(SlimceaConstants.SLIMCEA_ACCOUNT_DRILLDOWN_NAME, SlimceaConstants.SLIMCEA_ACCOUNT_DRILLDOWN_LABEL);
			
			//
			// First test at registering a new drillable report.
			//
			ReportContextRegistry.getInstance().register(SlimceaConstants.SLIMCEA_MY_FIRST_DROPDOWN, SlimceaConstants.SLIMCEA_MY_FIRST_DROPDOWN_LABEL);			
			
			//register the left hand menu provider for the menu item i'm introducing
			DummyMenuProvider menuProvider = new DummyMenuProvider();
			
			//Workflow input Types
			WorkflowInputTypeDeclaration.registerWFInputs();
			
			// Nimble test for perf policies LOV provider.
			//PerformancePoliciesLOVWorkflowInputType.registerWFInputs();
			
			
			
			
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
			logger.error("Slimcea Module error registering components.", e);
		}
		
	}
	
	
	/**
	 * Creating New Account Type
	 */
	private void createAccountType(){
		
		AccountTypeEntry entry=new AccountTypeEntry();
		
		// This is mandatory, hold the information for device credential details
		entry.setCredentialClass(SlimceaAccount.class);
		
		// This is mandatory, type of the Account will be shown in GUI as drill
		// down box
		entry.setAccountType(SlimceaConstants.INFRA_ACCOUNT_TYPE);
		
		// This is mandatory, label of the Account
		entry.setAccountLabel(SlimceaConstants.INFRA_ACCOUNT_LABEL);
		
		// This is mandatory, specify the category of the account type ie.,
		// Network / Storage / //Compute
		entry.setCategory(InfraAccountTypes.CAT_STORAGE);
		
		//This is mandatory
		entry.setContextType(ReportContextRegistry.getInstance().getContextByName(SlimceaConstants.INFRA_ACCOUNT_TYPE).getType());
		
		// This is mandatory, on which accounts either physical or virtual
		// account , new account //type belong to.
		entry.setAccountClass(AccountTypeEntry.PHYSICAL_ACCOUNT);
		
		// Optional , prefix of the task
		entry.setInventoryTaskPrefix("Slimcea Inventory Task");
		
		//Optional. Group inventory system tasks under this folder. 
		//By default it is grouped under General Tasks
		entry.setWorkflowTaskCategory("Slimcea Tasks");
		
		// Optional , collect the inventory frequency, whenever required you can
		// change the
		// inventory collection frequency, in mins.
		entry.setInventoryFrequencyInMins(15);
		
		// This is mandatory,under which pod type , the new account type is
		// applicable.
		entry.setPodTypes(new String[] { "SlimceaStack" } );
		
		
		// This is optional, dependents on the need of session for collecting
		// the inventory
		//entry.setConnectorSessionFactory(new FooSessionFactory());
		
		// This is mandatory, to test the connectivity of the new account. The
		// Handler should be of type PhysicalConnectivityTestHandler.
		entry.setTestConnectionHandler(new SlimceaTestConnectionHandler());
		
		// This is mandatory, we can implement inventory listener according to
		// the account Type , collect the inventory details.
		entry.setInventoryListener(new SlimceaInventoryListener());
		
		//This is mandatory , to show in the converged stack view
		entry.setConvergedStackComponentBuilder(new SlimceaConvergedStackBuilder());
		
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
					AccountTypeEntry SlimceaRecoverPointAccountEntry) {
				@SuppressWarnings("unused")
				ConfigItemDef SlimceaRecoverPointStateInfo = SlimceaRecoverPointAccountEntry
						.createInventoryRoot("slimcea.inventory.root",
								SlimceaInventoryItemHandler.class);
			}

}
