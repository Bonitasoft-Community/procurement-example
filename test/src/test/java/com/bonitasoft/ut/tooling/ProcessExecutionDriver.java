package com.bonitasoft.ut.tooling;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.bpm.flownode.ActivityInstance;
import org.bonitasoft.engine.bpm.process.ActivationState;
import org.bonitasoft.engine.bpm.process.ProcessDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfoCriterion;
import org.bonitasoft.engine.bpm.process.ProcessInstance;

public class ProcessExecutionDriver {

	private static ProcessAPI processAPI;

	public static void setUp(ProcessAPI processAPI) {
		ProcessExecutionDriver.processAPI = processAPI;
	}

	public static void prepareServer() throws Exception {
		removeAllProcessInstancesAndDefinitions();
	}

	private static void removeAllProcessInstancesAndDefinitions() throws Exception {
		List<ProcessDeploymentInfo> processDeploymentInfos = processAPI.getProcessDeploymentInfos(0, 100,
				ProcessDeploymentInfoCriterion.DEFAULT);

		// For all deployed process definitions
		for (ProcessDeploymentInfo processDeploymentInfo : processDeploymentInfos) {
			long processDefinitionId = processDeploymentInfo.getProcessId();
      
      if(!ActivationState.DISABLED.name().equals(processDeploymentInfo.getActivationState())) {
        processAPI.disableProcess(processDefinitionId);
      }

			// Delete archived instances
			int startIndex = 0;
			long nbDeleted = 0;
			do {
				nbDeleted = processAPI.deleteArchivedProcessInstances(processDefinitionId, startIndex, 100);
			} while (nbDeleted > 0);

			// Delete running instances
			startIndex = 0;
			nbDeleted = 0;
			do {
				nbDeleted = processAPI.deleteProcessInstances(processDefinitionId, startIndex, 100);
			} while (nbDeleted > 0);

			// Enable definition
			processAPI.enableProcess(processDefinitionId);
		}
	}

	public static long createProcessInstance(String processName, String processVersion) throws Exception {
		return createProcessInstance(processName, processVersion, null);
	}

	public static long createProcessInstance(String processName, String processVersion,
			Map<String, Serializable> newProcessInstanceInputs) throws Exception {

		final ProcessDefinition processDefinition = processAPI.getProcessDefinition(processAPI.getProcessDefinitionId(
				processName, processVersion));

		ProcessInstance processInstance;

		if (newProcessInstanceInputs == null) {
			processInstance = processAPI.startProcess(processDefinition.getId());
		} else {
			processInstance = processAPI.startProcessWithInputs(processDefinition.getId(), newProcessInstanceInputs);
		}

		long processInstanceId = processInstance.getId();

		return processInstanceId;
	}

	public static void executePendingHumanTask(ActivityInstance activityInstance, Long userId,
			Map<String, Serializable> taskInputs) throws Exception {
		long humanTaskInstanceId = activityInstance.getId();

		processAPI.executeUserTask(userId, humanTaskInstanceId, taskInputs);
	}

}
