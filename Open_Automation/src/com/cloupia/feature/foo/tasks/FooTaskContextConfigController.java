package com.cloupia.feature.foo.tasks;

import org.apache.log4j.Logger;

import com.cloupia.feature.foo.FooModule;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.service.cIM.inframgr.forms.wizard.AbstractObjectUIController;
import com.cloupia.service.cIM.inframgr.forms.wizard.Page;

public class FooTaskContextConfigController extends AbstractObjectUIController {

	private static Logger logger = Logger.getLogger(FooTaskContextConfigController.class);
	@Override
    public void beforeMarshall(Page page, String id, ReportContext context, Object pojo) throws Exception
    {
		FooTaskContextConfig config = (FooTaskContextConfig) pojo;
		logger.info(" before Marshall ");
    }
}
