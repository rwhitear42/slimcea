package com.cloupia.feature.slimcea.dummyOne.reports;

import com.cloupia.feature.slimcea.constants.SlimceaConstants;
import com.cloupia.model.cIM.DynReportContext;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.service.cIM.inframgr.reportengine.ContextMapRule;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportAction;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportWithActions;

/**
 * This is just a dummy report I'm using to demonstrate how you would place a report under a new menu location
 * for a specific context.  In this case, I have a new menu "dummy_menu_1" and this menu also has a new context
 * "dummy context one", this report does a couple of things to make sure it gets placed into that specific
 * location.
 *
 */
public class DummyOneSampleReport extends CloupiaReportWithActions {
	
	private static final String NAME = "Slimcea.dummy.one.sample.report";
	private static final String LABEL = "Dummy One Sample";
	
	public DummyOneSampleReport() {
		super();
		//IMPORTANT: this tells the framework which column of this report you want to pass as the report context id
		//when there is a UI action being launched in this report
		this.setMgmtColumnIndex(1);
		
		//you can technically speaking set the proper menu id and context map rules here in the
		//the constructor BUT i think it's better to override the functions and hardcode this logic into the function 
		//instead (as shown below), just to guarantee it can't be tweaked by anyone else!
		
		/*
		this.setMenuID(SlimceaConstants.DUMMY_MENU_WHITNEY_1);
		
		DynReportContext dummyContextOneType = ReportContextRegistry.getInstance().getContextByName(SlimceaConstants.DUMMY_CONTEXT_ONE);
		ContextMapRule rule = new ContextMapRule();
		rule.setContextName(dummyContextOneType.getId());
		rule.setContextType(dummyContextOneType.getType());
		
		ContextMapRule[] rules = new ContextMapRule[1];
		rules[0] = rule;
		
		this.setMapRules(rules);
		*/
	}

	@Override
	public CloupiaReportAction[] getActions() {
		return null;
	}

	@Override
	public Class getImplementationClass() {
		//this class handles all the report generation logic, look here for more details
		return DummyOneSampleReportImpl.class;
	}

	@Override
	public String getReportLabel() {
		return LABEL;
	}

	@Override
	public String getReportName() {
		return NAME;
	}

	//this is important, make sure this returns false.  isEasyReport should ONLY return
	//true if and only if you're using the POJO approach, which we aren't in this case.
	@Override
	public boolean isEasyReport() {
		return false;
	}

	//make sure to return true in this case, as there are no drill down reports
	@Override
	public boolean isLeafReport() {
		return true;
	}
	
	//basically hardcoding it so this report will show in the location i want it to
	@Override
	public int getMenuID() {
		return SlimceaConstants.DUMMY_MENU_1;
	}
	
	@Override
	public ContextMapRule[] getMapRules() {
		//i'm using an autogenerated report context (which I registered in SlimceaModule), as mentioned in documentation
		//the type may vary depending on deployments, so the safest way to retrieve the auto generated type value
		//is to use the getContextByName api!
		DynReportContext dummyContextOneType = ReportContextRegistry.getInstance().getContextByName(SlimceaConstants.DUMMY_CONTEXT_ONE);
		ContextMapRule rule = new ContextMapRule();
		rule.setContextName(dummyContextOneType.getId());
		rule.setContextType(dummyContextOneType.getType());
		
		ContextMapRule[] rules = new ContextMapRule[1];
		rules[0] = rule;
		
		return rules;
	}

}