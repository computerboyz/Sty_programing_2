package com.ru.usty.scheduling;

import com.ru.usty.scheduling.process.ProcessExecution;

import java.util.List;
import java.util.ArrayList;

public class Scheduler {
	ProcessExecution processExecution;
	Policy policy;
	int quantum;
	int currentProc;
	public List<Integer> listproc = new ArrayList<Integer>();
	List<ProcessThread> threadList;

	/**
	 * Add any objects and variables here (if needed)
	 */


	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public Scheduler(ProcessExecution processExecution) {
		this.processExecution = processExecution;
		 
	}

	private void initializeRR() {
		this.threadList = new ArrayList<ProcessThread>();
	}
	
	private void createThread(int processID) {
		ProcessThread thread = new ProcessThread(processID, this, quantum);
		Thread newThread = new Thread(thread);
		
		newThread.start();
		this.threadList.add(thread);
	}
	
	public ProcessExecution getExecutionInfo() {
		return processExecution;
	}
	
	public int roundRobinSwitch() {
		System.out.println("Checking switch");
		
		for(int i = 0; i < listproc.size(); i++) {
			int id = listproc.get(i);
			
			if((processExecution.getProcessInfo(id).totalServiceTime - 
				processExecution.getProcessInfo(id).elapsedExecutionTime) < 
				(processExecution.getProcessInfo(currentProc).totalServiceTime - 
				processExecution.getProcessInfo(currentProc).elapsedExecutionTime)) {	
								
				System.out.println("KJAHKSJDHAKSJHDKJH");
				currentProc = id;
			}
		}
			
		processExecution.switchToProcess(currentProc);
		threadList.get(currentProc).runProcess();			
		
		return currentProc;
	}

	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void startScheduling(Policy policy, int quantum) {

		this.policy = policy;
		this.quantum = quantum;


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
			listproc = new ArrayList<Integer>();
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

	public void processAdded(int processID) {
		switch(this.policy) {
			case RR:
				createThread(processID);
				listproc.add(processID);
				
				if(listproc.size() == 1) {
					currentProc = listproc.get(processID);
					
					processExecution.switchToProcess(currentProc);
					threadList.get(currentProc).runProcess();
				}			
				
				break;

			case FCFS:
				listproc.add(processID);
				//if(policy == FCFS) {
				if (listproc.size() == 1) {
					currentProc = processID;
					processExecution.switchToProcess(processID);
				}
				
				break;
				
			case SRT:
				listproc.add(processID);
				
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
				
				break;
				
			case SPN:
				System.out.println("SPN");
				break;
		}
	}

	public void processFinished(int processID) {
		switch(this.policy) {
			case SRT:
				listproc.remove(listproc.indexOf(processID));
				
				if(listproc.size() != 0) {					
					currentProc = listproc.get(listproc.size()-1);
				
					for(int i = 0; i < listproc.size(); i++) {
						int id = listproc.get(i);
						
						if((processExecution.getProcessInfo(id).totalServiceTime - 
							processExecution.getProcessInfo(id).elapsedExecutionTime) < 
							(processExecution.getProcessInfo(currentProc).totalServiceTime - 
							processExecution.getProcessInfo(currentProc).elapsedExecutionTime)) {	
							currentProc = id;
						}
					}
					
					processExecution.switchToProcess(currentProc);
				}
				break;
			
			case FCFS:
				listproc.remove(listproc.indexOf(processID));
				
				if(listproc.size() != 0) {
					processExecution.switchToProcess(processID + 1);
				}
				
				break;
			
			case RR:
				listproc.remove(listproc.indexOf(processID));
				
				if(listproc.size() > 1) {
					int lowestID = listproc.get(0);
					
					for(int i = 1; i < listproc.size(); i++) {
						if((processExecution.getProcessInfo(listproc.get(i)).totalServiceTime - 
							processExecution.getProcessInfo(listproc.get(i)).elapsedExecutionTime) < 
							(processExecution.getProcessInfo(lowestID).totalServiceTime - 
							processExecution.getProcessInfo(lowestID).elapsedExecutionTime)) {	
								lowestID = listproc.get(i);
						}
					}
						
					currentProc = lowestID;
					processExecution.switchToProcess(currentProc);
					threadList.get(currentProc).runProcess();
				}
					
				else if(listproc.size() == 1) {
					currentProc = listproc.get(0);
					processExecution.switchToProcess(currentProc);
					threadList.get(currentProc).runProcess();
				}				
		}
	}
}


