package com.ru.usty.scheduling;

import java.time.Duration;
import java.time.Instant;

public class ProcessThread implements Runnable {
	private Scheduler scheduler;
	
	private volatile long currentTime;
	private volatile long timeLeft;
	
	private long timeTotal;
	private long timeElapsed;
	
	public int id;
	private long quantum;
	
	private boolean isRunning;
	
	public ProcessThread(int id, Scheduler scheduler, int quantum) {
		this.scheduler = scheduler;
		this.quantum = quantum;
		this.id = id;
		this.timeLeft = scheduler.getExecutionInfo().getProcessInfo(id).totalServiceTime;
		//this.currentTime = System.currentTimeMillis();
		this.currentTime = 0;
	}
	
	public int getID() {
		return 0;
	}
	
	public void runProcess() {
		//System.out.println("Process " + id + " running");
		currentTime = System.currentTimeMillis();
		isRunning = true;
	}
	
	@Override
	public void run() {		
		while(timeLeft > 0) {
			timeElapsed = scheduler.getExecutionInfo().getProcessInfo(id).elapsedExecutionTime;
			
			
			long a = System.currentTimeMillis();
			
			if(currentTime == 0) {
				currentTime = a;
			}
			
			long currentTimeElapsed = (a - currentTime);
			
			if(currentTimeElapsed >= quantum) {
				System.out.println("process " + id + ": " + currentTimeElapsed);
				int newID = scheduler.roundRobinSwitch(); 
				
				if(newID == id) {
					currentTime = System.currentTimeMillis();
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
