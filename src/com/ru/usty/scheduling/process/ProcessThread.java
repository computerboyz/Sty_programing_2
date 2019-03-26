package com.ru.usty.scheduling.process;
import com.ru.usty.scheduling.Scheduler;

public class ProcessThread implements Runnable {
	private Scheduler scheduler;
	public int id;
	private int quantum;
	private long currentTime;
	
	private long timeTotal;
	private long timeElapsed;
	
	private boolean isRunning;
	
	public ProcessThread(int id, Scheduler scheduler, int quantum) {
		this.scheduler = scheduler;
		this.quantum = quantum;
		this.id = id;
		
		System.out.println(quantum);
		
		//System.out.println("creating thread " + id);
		//System.out.println(scheduler.getExecutionInfo().getProcessInfo(id).totalServiceTime);
	}
	
	public int getID() {
		return 0;
	}
	
	public void runProcess() {
		//System.out.println("Running process " + id);
		currentTime = System.currentTimeMillis();
		isRunning = true;
	}
	
	
	@Override
	public void run() {
		long timeLeft = scheduler.getExecutionInfo().getProcessInfo(id).totalServiceTime;
		
		//System.out.println(timeLeft);
		
		while(timeLeft > 0) {
			//System.out.println(id);
			timeTotal = scheduler.getExecutionInfo().getProcessInfo(id).totalServiceTime;
			timeElapsed = scheduler.getExecutionInfo().getProcessInfo(id).elapsedExecutionTime;
			
			long a = System.currentTimeMillis();
			
			long currentTimeElapsed = (a - currentTime);
			
			System.out.println(currentTimeElapsed);
			
			if(currentTimeElapsed >= quantum) {
				System.out.println("Switching");
				System.out.println("quantum: " + quantum);
				System.out.println("currentTime: " + currentTime);
				
				isRunning = scheduler.roundRobinSwitch();
			}
			
			if(isRunning) {				
				timeLeft -= timeElapsed;
				
				if(timeLeft <= 0) {
					System.out.println("BREAK");
					System.out.println("time left: " + timeLeft);
					break;
				}
			}			
		}
				
	}
}
