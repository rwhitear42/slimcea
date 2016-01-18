package com.cloupia.feature.foo.accounts;

import com.cloupia.lib.connector.account.credential.CredentialParserIf;

/**
 * This is sample class for the Credential Policy Check implementation.
 * This is not mandatory to implement this class. Whenever the Crdential Policy check in enabled.
 * It is required to implement credential check of the Policy.
 */
public class FooAccountCredentialParser implements CredentialParserIf {

	@Override
	public Object getCredentialsFromPolicy(String arg0, Object arg1)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
