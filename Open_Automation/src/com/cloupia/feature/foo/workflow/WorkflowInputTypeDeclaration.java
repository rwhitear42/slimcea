package com.cloupia.feature.foo.workflow;

import org.apache.log4j.Logger;


import com.cloupia.feature.foo.accounts.FooAccountSampleReportImpl;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionHandlerIf;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionRegistry;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.cloupia.service.cIM.inframgr.customactions.WFTaskDefinition;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputTypeRegistry;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderRegistry;
import com.cloupia.service.cIM.inframgr.forms.wizard.TabularFieldRegistry;

/**
 * 
 * @author anadipin
 * Using this class we can create custom workflow input types.
 *
 */
public class WorkflowInputTypeDeclaration {
	
	private static Logger logger = Logger.getLogger(WorkflowInputTypeDeclaration.class);
	
	public WorkflowInputTypeDeclaration(){}
	
  /**
	* This method is used to register Workflow Input Types.
	* @return void This returns sum of numA and numB.
	*/
	public static void registerWFInputs(){
		
		registerSampleLOVWorkflowInputType();
		registerSampleTabularWorkflowInputType();
		
		
	}
	
  /**
	* This method is used to register Workflow LOV Type Inputs.
	* @return void
	*/
	private static void registerSampleLOVWorkflowInputType(){
		
		WorkflowInputTypeRegistry sampleInputType = WorkflowInputTypeRegistry.getInstance();
		sampleInputType.addDeclaration(new WorkflowInputFieldTypeDeclaration(
				"SampleLOVInput", "Sample LOV Input",
				FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, "sampleInputTypeLOV"));
		
		
		 LOVProviderRegistry.getInstance()
         .registerProvider(SampleLOVProvider.NAME,
         new SampleLOVProvider());
		 
		
		
	}
	/**
	* This method is used to register Workflow input of type tabular. 
	* @return void
	*/
	
	private static void registerSampleTabularWorkflowInputType(){
		String fieldName = "SampleInputTypeTabuler";
		
		WorkflowInputTypeRegistry sampleInputType = WorkflowInputTypeRegistry.getInstance();
		sampleInputType.addDeclaration(new WorkflowInputFieldTypeDeclaration(
                "SampleTabulerInput", "Sample Tabuler Input",
                FormFieldDefinition.FIELD_TYPE_TABULAR,fieldName));
		
		TabularFieldRegistry.getInstance().registerTabularField(fieldName,
				FooAccountSampleReportImpl.class, "0", "2");
	}
	
}
