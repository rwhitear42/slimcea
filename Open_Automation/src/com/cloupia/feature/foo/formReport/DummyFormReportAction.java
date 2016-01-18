package com.cloupia.feature.foo.formReport;

import org.apache.log4j.Logger;

import com.cloupia.model.cIM.ConfigTableAction;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.service.cIM.inframgr.FileManagementUtil;
import com.cloupia.service.cIM.inframgr.forms.wizard.Page;
import com.cloupia.service.cIM.inframgr.forms.wizard.PageIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.WizardSession;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaPageAction;

/**
 * This is the controller portion of the action, where all the execution logic for the form is.
 *
 */
public class DummyFormReportAction extends CloupiaPageAction {

	private static Logger logger = Logger.getLogger(DummyFormReportAction.class);

	//need to provide a unique string to identify this form and action (note: prefix is module id, good practice)
	private static final String formId = "foo.dummy.report.form";
	private static final String ACTION_ID = "foo.dummy.report.action";
	//this is the label show in UI for this action
	private static final String label = "label unused, report label overrides this when using a config form!";

	@Override
	public String getActionId() {
		return ACTION_ID;
	}

	public String getFormId()
	{
		return formId;
	}
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public int getActionType() {
		//just assume you need to pass this constant for this method for any UI actions
		return ConfigTableAction.ACTION_TYPE_POPUP_FORM;
	}

	@Override
	public boolean isSelectionRequired() {
		//return true when a row needs to be selected for this action to proceed
		//return false if no row selection is required
		return false;
	}

	@Override
	public boolean isDoubleClickAction() {
		return false;
	}

	@Override
	public boolean isDrilldownAction() {
		return false;
	}

	@Override
	public void definePage(Page page, ReportContext context) {
		//this is where you define the layout of your action
		//the easiest way to do this is to use this "bind" method
		//since i already have my form object, i just need to provide a unique id and the POJO itself
		//the framework will handle all the other details
		page.bind(formId, DummyFormReportObject.class);
		//a common request is to hide the submit button which normally comes for free with any form, in
		//this particular case because this form will show as a report, i would like to hide the submit button
		//which is what i'm demonstrating in this line
		page.setSubmitButton("");
	}

	@Override
	public void loadDataToPage(Page page, ReportContext context, WizardSession session) throws Exception {
		//call this to make sure everything in the form is loaded properly
		//you need to make sure to call this, otherwise it's possible certain form widgets will not be
		//initialized properly!
		page.marshallFromSession(formId);
	}

	//this is called when the user hits the submit button in the UI
	@Override
	public int validatePageData(Page page, ReportContext context, WizardSession session) throws Exception {
		return PageIf.STATUS_OK;
	}

	@Override
	public String getTitle() {
		return label;
	}


}
