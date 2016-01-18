package com.cloupia.feature.foo.accounts.session;
import com.cloupia.lib.connector.pooling.ConnectorSession;
import com.cloupia.lib.connector.pooling.ConnectorSessionFactory;

/**
 * This class is used to create the session from the sessionFactory.
 * Override the createsession to return the connector session .
 */

public class FooSessionFactory implements ConnectorSessionFactory{

	@Override
	public ConnectorSession createSession(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
