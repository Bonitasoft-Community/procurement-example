package com.bonitasoft.ut.tooling;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.bdm.BusinessObjectDAOFactory;
import org.bonitasoft.engine.bdm.dao.BusinessObjectDAO;
import org.bonitasoft.engine.bpm.flownode.ActivityInstance;
import org.bonitasoft.engine.bpm.process.ProcessInstanceNotFoundException;
import org.bonitasoft.engine.business.data.SimpleBusinessDataReference;
import org.bonitasoft.engine.exception.BonitaException;
import org.bonitasoft.engine.session.APISession;
import org.junit.Assert;

import org.bonitasoft.engine.api.APIClient;

public class BonitaBPMAssert {

	/** ProcessAPI is used to verify process instance state, retrieve execution context... */
	private static ProcessAPI processAPI;

	/** Session is needed to access business data value */
	private static APISession session;

	/** APITestUtil is used to wait for task to be ready or process instance to be finished using Engine call back */
	private static APITestUtil apiTestUtil;

	/**
	 * setUp method must be call before using assertion. Recommendation is to add it to setUpClass / @BeforeClass
	 * 
	 * @throws BonitaException
	 */
	public static void setUp(APISession session, ProcessAPI processAPI) throws BonitaException {
		BonitaBPMAssert.processAPI = processAPI;
		BonitaBPMAssert.session = session;
		APIClient apiClient = new APIClient(session);
		BonitaBPMAssert.apiTestUtil = new APITestUtil(apiClient);
		ClientEventUtil.deployCommand(session);
	}
	
	public static void tearDown() throws BonitaException {
		ClientEventUtil.undeployCommand(BonitaBPMAssert.session);
	}

	/**
	 * Check that a task with {@code name} is pending for process instance with id {@code processInstanceId}. If task
	 * exist it is executed on behalf of user with id {@code userId} with inputs {@code taskInputs}.
	 * 
	 * @param processInstanceId
	 *            id of the running process instance.
	 * @param string
	 *            name of the task as defined in Bonita BPM Studio.
	 * @param taskInputs
	 *            inputs that match contract definition.
	 * @param userId
	 *            id of a user existing in the test environment. You can use {@link APISession#getUserId()} to set this
	 *            value.
	 * @throws Exception
	 */
	public static void assertHumanTaskIsPendingAndExecute(long processInstanceId, String string,
			Map<String, Serializable> taskInputs, long userId) throws Exception {

		ActivityInstance activityInstance = assertHumanTaskIsPending(processInstanceId, string);

		ProcessExecutionDriver.executePendingHumanTask(activityInstance, userId, taskInputs);
	}

	public static ActivityInstance assertHumanTaskIsPending(long processInstanceId, String string) throws Exception {
		return waitForHumanTask(processInstanceId, string, TestStates.READY);
	}

	public static void assertProcessInstanceIsFinished(long processInstanceId) throws Exception {
		waitForProcessInstanceCompletion(processInstanceId);
	}

	/**
	 * Checks that business data is not null and returns the business data value.
	 * 
	 * @param businessObjectClass
	 *            business object class. E.g. VacationRequest.class.
	 * @param processInstanceId
	 *            id of the running process instance. If process instance is archived it will be used to search latest
	 *            archived process instance informations.
	 * @param businessDataName
	 *            the name of the business data as declared in the process definition in the Studio.
	 * @return the business data value.
	 * @throws Exception
	 */
	public static <T> T assertBusinessDataNotNull(Class<T> businessObjectClass, long processInstanceId,
			String businessDataName) throws Exception {

		Map<String, Serializable> processInstanceExecutionContext;
		try {
			// Retrieve process reference to business data
			processInstanceExecutionContext = processAPI.getProcessInstanceExecutionContext(processInstanceId);
		} catch (ProcessInstanceNotFoundException e) {
			Long archivedProcessInstanceId = processAPI.getFinalArchivedProcessInstance(processInstanceId).getId();
			processInstanceExecutionContext = processAPI
					.getArchivedProcessInstanceExecutionContext(archivedProcessInstanceId);
		}

		SimpleBusinessDataReference businessDataReference = (SimpleBusinessDataReference) processInstanceExecutionContext
				.get(businessDataName + "_ref");

		// Create DAO to access business data value
		BusinessObjectDAOFactory daoFactory = new BusinessObjectDAOFactory();

		@SuppressWarnings("unchecked")
		Class<? extends BusinessObjectDAO> daoClass = (Class<? extends BusinessObjectDAO>) Class
				.forName(businessObjectClass.getCanonicalName() + "DAO");

		BusinessObjectDAO businessObjectDAO = daoFactory.createDAO(session, daoClass);

		Method findByPersistenceIdMethod = daoClass.getMethod("findByPersistenceId", Long.class);

		@SuppressWarnings("unchecked")
		T businessObject = (T) findByPersistenceIdMethod
				.invoke(businessObjectDAO, businessDataReference.getStorageId());

		Assert.assertNotNull(businessObject);

		return businessObject;
	}

	public static <T> void assertBusinessDataReferenceNull(Class<T> businessObjectClass, long processInstanceId,
			String businessDataName) throws Exception {

		Map<String, Serializable> processInstanceExecutionContext;
		try {
			// Retrieve process reference to business data
			processInstanceExecutionContext = processAPI.getProcessInstanceExecutionContext(processInstanceId);
		} catch (ProcessInstanceNotFoundException e) {
			Long archivedProcessInstanceId = processAPI.getFinalArchivedProcessInstance(processInstanceId).getId();
			processInstanceExecutionContext = processAPI
					.getArchivedProcessInstanceExecutionContext(archivedProcessInstanceId);
		}

		SimpleBusinessDataReference businessDataReference = (SimpleBusinessDataReference) processInstanceExecutionContext
				.get(businessDataName + "_ref");

		Assert.assertNull(businessDataReference);
	}

	private static ActivityInstance waitForHumanTask(final long processInstanceId, final String taskName,
			final TestStates state) throws Exception {
		return apiTestUtil.waitForTaskInState(processInstanceId, taskName, state);
	}

	private static void waitForProcessInstanceCompletion(long processInstanceId) throws Exception {
		apiTestUtil.waitForProcessToFinish(processInstanceId);
	}

}
