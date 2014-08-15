package com.indidge.mywebapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String userName,String password, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
