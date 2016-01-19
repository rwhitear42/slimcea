package com.cloupia.feature.slimcea.tasks;

import org.apache.log4j.Logger;

import com.cloupia.feature.slimcea.SlimceaModule;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.service.cIM.inframgr.forms.wizard.AbstractObjectUIController;
import com.cloupia.service.cIM.inframgr.forms.wizard.Page;

public class SlimceaTaskContextConfigController extends AbstractObjectUIController {

	private static Logger logger = Logger.getLogger(SlimceaTaskContextConfigController.class);
	@Override
    public void beforeMarshall(Page page, String id, ReportContext context, Object pojo) throws Exception
    {
		SlimceaTaskContextConfig config = (SlimceaTaskContextConfig) pojo;
		logger.info(" before Marshall ");
    }
}
