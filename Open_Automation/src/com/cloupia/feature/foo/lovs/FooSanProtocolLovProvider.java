package com.cloupia.feature.foo.lovs;

import com.cloupia.model.cIM.FormLOVPair;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.WizardSession;

public class FooSanProtocolLovProvider implements LOVProviderIf {
	
	public static final String FOO_SAN_PROTOCOL_LOV_PROVIDER = "foo_san_protocol_lov_provider";

	@Override
	public FormLOVPair[] getLOVs(WizardSession session) {
		FormLOVPair[] pairs = new FormLOVPair[2];
    	FormLOVPair pair1 = new FormLOVPair("iscsi", "iscsi");
    	FormLOVPair pair2 = new FormLOVPair("fc", "fc");
    	pairs[0] = pair1;
    	pairs[1] = pair2;
    	return pairs;
	}

}
