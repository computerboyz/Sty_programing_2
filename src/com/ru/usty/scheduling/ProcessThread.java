package com.ru.usty.scheduling;

public class ProcessThread implements Runnable {
	private Scheduler scheduler;
	
	private long timeTotal;
	private long timeElapsed;
	private long currentTime;
	
	public int id;
	private int quantum;
	
	private boolean isRunning;
	
	public ProcessThread(int id, Scheduler scheduler, int quantum) {
		this.scheduler = scheduler;
		this.quantum = quantum;
		this.id = id;
	}
	
	public int getID() {
		return 0;
	}
	
	public void runProcess() {
		System.out.println("Process " + id + " running");
		currentTime = System.nanoTime();
		isRunning = true;
	}
	
	@Override
	public void run() {
		long timeLeft = scheduler.getExecutionInfo().getProcessInfo(id).totalServiceTime;
		
		while(timeLeft > 0) {
			timeTotal = scheduler.getExecutionInfo().getProcessInfo(id).totalServiceTime;
			timeElapsed = scheduler.getExecutionInfo().getProcessInfo(id).elapsedExecutionTime;
			
			long a = System.nanoTime();
			long currentTimeElapsed = (a - currentTime)/1000;
			
			System.out.println("process " + id + ": " + currentTimeElapsed);
			
			if(currentTimeElapsed >= quantum) {				
				int newID = scheduler.roundRobinSwitch(); 
				
				if(newID == id) {
					currentTime = System.nanoTime();
					isRunning = true;
				} else {
					currentTime = 0;
					isRunning = false;
				}
			}
				
			if(isRunning) {				
				timeLeft -= timeElapsed;
					
				if(timeLeft <= 0) {
					System.out.println("BREAK");
					break;
				}			
			}
		}
					
		System.out.println("Process " + id + " completed");
	}
}
