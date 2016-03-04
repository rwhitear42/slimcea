package com.cloupia.feature.slimcea.accounts;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;



import com.cloupia.lib.easyui.annotations.ReportField;
import com.cloupia.model.cIM.InventoryDBItemIf;
import com.cloupia.service.cIM.inframgr.reports.simplified.ReportableIf;


/**
 * This is dummy class for POJO based approach for Account Type implementation. 
 * Data populating from DummyAccountData for this account.
 * For real implementation similar POJO based approach can be refer.
 * 
 *
 */
@PersistenceCapable(detachable = "true")
public class SlimceaPerformancePoliciesAccountReport {
	
	@ReportField(label="Performance Policy")
	@Persistent
	private String perfPolicy;
	
	@ReportField(label="Associated Volumes")
	@Persistent
 	private String associatedVolumes;
	
	
	public SlimceaPerformancePoliciesAccountReport() {
		super();
		
	}

	
	public String getPerfPolicy() {
		return perfPolicy;
	}

	public void setPerfPolicy(String perfPolicy) {
		this.perfPolicy = perfPolicy;
	}

	public String getAssociatedVolumes() {
		return associatedVolumes;
	}

	public void setAssociatedVolumes(String associatedVolumes) {
		this.associatedVolumes = associatedVolumes;
	}

}
