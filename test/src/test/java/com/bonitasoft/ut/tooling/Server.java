package com.bonitasoft.ut.tooling;

import java.util.HashMap;
import java.util.Map;

import org.bonitasoft.engine.api.ApiAccessType;
import org.bonitasoft.engine.api.LoginAPI;
import org.bonitasoft.engine.api.TenantAPIAccessor;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.platform.LoginException;
import org.bonitasoft.engine.session.APISession;
import org.bonitasoft.engine.util.APITypeManager;

public class Server {

	/** name of the Bonita web app */
	private static final String BONITA_WEBAPP_NAME = "bonita";

	/** Username of the user use to drive process instantiation and execution */
	private static final String TEST_USER = "walter.bates";

	public static APISession httpConnect() throws BonitaHomeNotSetException, ServerAPIException,
			UnknownAPITypeException, LoginException {
		// Create a Map to configure Bonita Client
		Map<String, String> apiTypeManagerParams = new HashMap<>();

		// URL for server (without web app name)
		apiTypeManagerParams.put("server.url", System.getProperty("server.url", "http://localhost:8080/"));

		// Bonita web application name
		apiTypeManagerParams.put("application.name", BONITA_WEBAPP_NAME);

		// Use HTTP connection to Bonita Engine API
		APITypeManager.setAPITypeAndParams(ApiAccessType.HTTP, apiTypeManagerParams);

		// Get a reference to login API and create a session for user
		// walter.bates (this user exist in default organization available in
		// Bonita Studio test environment)
		LoginAPI loginAPI = TenantAPIAccessor.getLoginAPI();

		APISession session = loginAPI.login(TEST_USER, "bpm");

		return session;
	}

	public static void logout(APISession session) throws Exception {
		LoginAPI loginAPI = TenantAPIAccessor.getLoginAPI();

		loginAPI.logout(session);
	}

}
