package com.ru.usty.scheduling;

import com.ru.usty.scheduling.process.ProcessExecution;
import java.util.List;
import java.util.ArrayList;

public class Scheduler {

	ProcessExecution processExecution;
	Policy policy;
	int quantum;
	int currentProc;
	List<Integer> listproc = new ArrayList<Integer>();
	ArrayList<Thread> threadList;

	/**
	 * Add any objects and variables here (if needed)
	 */


	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public Scheduler(ProcessExecution processExecution) {
		this.processExecution = processExecution;
		 
		/**
		 * Add general initialization code here (if needed)
		 */
	}

	private void initializeRR() {
		this.threadList = new ArrayList<Thread>();
	}
	
	private void createThread(int processID) {
		this.threadList.add(processID, new Thread());
	}

	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void startScheduling(Policy policy, int quantum) {

		this.policy = policy;
		this.quantum = quantum;

		/**
		 * Add general initialization code here (if needed)
		 */

		switch(policy) {
		case FCFS:	//First-come-first-served
			System.out.println("Starting new scheduling task: First-come-first-served");
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case RR:	//Round robin
			System.out.println("Starting new scheduling task: Round robin, quantum = " + quantum);
			initializeRR();
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case SPN:	//Shortest process next
			System.out.println("Starting new scheduling task: Shortest process next");
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case SRT:	//Shortest remaining time
			System.out.println("Starting new scheduling task: Shortest remaining time");
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case HRRN:	//Highest response ratio next
			System.out.println("Starting new scheduling task: Highest response ratio next");
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case FB:	//Feedback
			System.out.println("Starting new scheduling task: Feedback, quantum = " + quantum);
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		}

		/**
		 * Add general scheduling or initialization code here (if needed)
		 */

	}

	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void processAdded(int processID) {
		switch(this.policy) {
			case RR:
				createThread(processID);
				
				for(int i = 0; i < threadList.size(); i++) {
					System.out.println(threadList.get(i));
				}

			case FCFS:
				listproc.add(processID);
				//if(policy == FCFS) {
				if (listproc.size() == 1) {
					currentProc = processID;
					processExecution.switchToProcess(processID);
				}

				if((processExecution.getProcessInfo(processID).totalServiceTime - 
					processExecution.getProcessInfo(processID).elapsedExecutionTime) < 
					(processExecution.getProcessInfo(currentProc).totalServiceTime - 
					processExecution.getProcessInfo(currentProc).elapsedExecutionTime)) {
					
					processExecution.switchToProcess(processID);
					currentProc = processID;
				}
		}

		
		//System.out.println(processExecution.getProcessInfo(currentProc).totalServiceTime - processExecution.getProcessInfo(currentProc).elapsedExecutionTime);
		//System.out.println((processExecution.getProcessInfo(processID).totalServiceTime- processExecution.getProcessInfo(processID).elapsedExecutionTime));
		//}else if (policy == SRT) {
		
		}
		/**
		 * Add scheduling code here
		 */

	//}

	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void processFinished(int processID) {
//		if(policy ==  SRT) {
		listproc.remove(listproc.indexOf(processID));
		if(listproc.size() != 0) {
		currentProc = listproc.get(listproc.size()-1);
		for(int i = 0; i < listproc.size(); i++) {
			int id = listproc.get(i);
			if((processExecution.getProcessInfo(id).totalServiceTime - processExecution.getProcessInfo(id).elapsedExecutionTime) < (processExecution.getProcessInfo(currentProc).totalServiceTime - processExecution.getProcessInfo(currentProc).elapsedExecutionTime)) {	
					currentProc = id;
				}}
		processExecution.switchToProcess(currentProc);
		}
			
//		}
		/**
		 * Add scheduling code here
		 */
	}
	}
