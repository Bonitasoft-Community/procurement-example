package org.bonitasoft.procurement;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.bonitasoft.engine.platform.Tenant;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.api.TenantAPIAccessor;
import org.bonitasoft.engine.bpm.flownode.ActivityInstance;
import org.bonitasoft.engine.session.APISession;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bonitasoft.ut.tooling.BonitaBPMAssert;
import com.bonitasoft.ut.tooling.ProcessExecutionDriver;
import com.bonitasoft.ut.tooling.Server;
import com.company.model.Request;
import com.company.model.RequestAssert;
import com.company.model.Supplier;
import com.company.model.SupplierAssert;
import com.company.model.Quotation;
import com.company.model.QuotationAssert;


public class IntegrationTest {

	private static final String CLAIM_SUMMARY = "My computer won't start";

	private static final String CLAIM_DESCRIPTION = "A black screen and a strange noise";

	private static final String CLAIM_ANSWER = "Have you tried turning it off and on again";

	private static final String NEW_PROCUREMENT_REQUEST = "Procurement request";

	private static final String INIT_SUPPLIERS = "Init sample procurement data";

	private static final String CREATE_SUPPLIER = "Create supplier";

	private static final String SUPPLIER_NAME = "Dell";

	private static final String SUPPLIER_DESCRIPTION = "Computer technology company";

	private static final String PROCESSES_VERSION = "1.0";

	private static APISession session;

	private static ProcessAPI processAPI;

	@BeforeClass
	public static void setUpClass() throws Exception {
		session = Server.httpConnect();
		processAPI = TenantAPIAccessor.getProcessAPI(session);

		BonitaBPMAssert.setUp(session, processAPI);
		ProcessExecutionDriver.setUp(processAPI);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		BonitaBPMAssert.tearDown();
		Server.logout(session);
	}

	@Before
	public void setUp() throws Exception {
		ProcessExecutionDriver.prepareServer();
	}

	// @After
	// public void tearDown() throws Exception {
	//
	// }

	@Test
	public void testHappyPath() throws Exception {
		// Create process instance to initialize suppliers
		ProcessExecutionDriver.createProcessInstance(INIT_SUPPLIERS, PROCESSES_VERSION);

		// Create process instance to add an extra supplier
		long createSupplierProcessInstanceId = ProcessExecutionDriver.createProcessInstance(CREATE_SUPPLIER,
				PROCESSES_VERSION, newSupplierInputs());

		// Create new procurement request process instance
		long newProcurementRequestProcessInstanceId = ProcessExecutionDriver.createProcessInstance(NEW_PROCUREMENT_REQUEST,
				PROCESSES_VERSION, newProcurementRequestInputs());

		// Step Complete quotation
		BonitaBPMAssert.assertHumanTaskIsPending(newProcurementRequestProcessInstanceId, "Complete quotation");
	}

	private Map<String, Serializable> newSupplierInputs() {
		Map<String, Serializable> newSupplierInputs = new HashMap<>();

		HashMap<String, Serializable> complex = new HashMap<>();
		complex.put("name", SUPPLIER_NAME);
		complex.put("description", SUPPLIER_DESCRIPTION);

		newSupplierInputs.put("supplierInput", complex);

		return newSupplierInputs;
	}

	private Map<String, Serializable> newProcurementRequestInputs() {
		Map<String, Serializable> procurementRequestInputs = new HashMap<>();

		procurementRequestInputs.put("summary", CLAIM_SUMMARY);
		procurementRequestInputs.put("description", CLAIM_DESCRIPTION);
		procurementRequestInputs.put("supplierIds", new ArrayList<>(Arrays.asList("1", "2", "3")));

		return procurementRequestInputs;
	}

}
