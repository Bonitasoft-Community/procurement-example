/*******************************************************************************
 * Copyright (C) 2016 Bonitasoft S.A.
 * Bonitasoft is a trademark of Bonitasoft SA.
 * This software file is BONITASOFT CONFIDENTIAL. Not For Distribution.
 * For commercial licensing information, contact:
 * Bonitasoft, 32 rue Gustave Eiffel – 38000 Grenoble
 * or Bonitasoft US, 51 Federal Street, Suite 305, San Francisco, CA 94107
 *******************************************************************************/
package com.bonitasoft.ut.tooling;

import java.io.Serializable;
import java.util.Map;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.bpm.flownode.ActivityInstance;
import org.bonitasoft.engine.bpm.flownode.ActivityInstanceNotFoundException;
import org.bonitasoft.engine.exception.RetrieveException;

public class APITestUtil {

	private static Integer DEFAULT_TIMEOUT;

	static {
		final String strTimeout = System.getProperty("sysprop.bonita.default.test.timeout");
		if (strTimeout != null) {
			DEFAULT_TIMEOUT = Integer.valueOf(strTimeout);
		} else {
			DEFAULT_TIMEOUT = 30 * 1000;
		}
	}

	private final APIClient apiClient;

	public APITestUtil(final APIClient apiClient) {
		this.apiClient = apiClient;
	}

	/**
	 * Wait for given process instance id to finish. Default time out is used.
	 * 
	 * @param processInstanceId
	 *            the id of a running instance.
	 * @throws Exception
	 */
	public void waitForProcessToFinish(final long processInstanceId) throws Exception {
		waitForProcessToFinish(processInstanceId, DEFAULT_TIMEOUT);
	}

	private void waitForProcessToFinish(final long processInstanceId, final int timeout) throws Exception {
		ClientEventUtil.executeWaitServerCommand(apiClient.getCommandAPI(),
				ClientEventUtil.getProcessInstanceFinishedEvent(processInstanceId), timeout);
	}

	/**
	 * Wait for given task name in given process instance id to reach given state. Default timeout is used.
	 * 
	 * @param processInstanceId
	 *            the id of a running instance.
	 * @param flowNodeName
	 *            the name of the task as defined in process definition.
	 * @param state
	 *            the state of the task to wait for.
	 * @return the task.
	 * @throws Exception
	 */
	public ActivityInstance waitForTaskInState(final long processInstanceId, final String flowNodeName,
			final TestStates state) throws Exception {
		final Long activityId = waitForFlowNode(processInstanceId, state, flowNodeName, true, DEFAULT_TIMEOUT);
		return getActivityInstance(activityId);
	}

	private Long waitForFlowNode(final long processInstanceId, final TestStates state, final String flowNodeName,
			final boolean useRootProcessInstance, final int timeout) throws Exception {
		Map<String, Serializable> params;
		if (useRootProcessInstance) {
			params = ClientEventUtil.getFlowNodeInState(processInstanceId, state.getStateName(), flowNodeName);
		} else {
			params = ClientEventUtil.getFlowNodeInStateWithParentId(processInstanceId, state.getStateName(),
					flowNodeName);
		}
		return ClientEventUtil.executeWaitServerCommand(apiClient.getCommandAPI(), params, timeout);
	}

	private ActivityInstance getActivityInstance(final Long id) throws ActivityInstanceNotFoundException,
			RetrieveException {
		if (id != null) {
			return apiClient.getProcessAPI().getActivityInstance(id);
		}
		throw new RuntimeException("no id returned for activity instance");
	}
}
