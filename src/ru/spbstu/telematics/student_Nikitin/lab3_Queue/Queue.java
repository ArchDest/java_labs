package ru.spbstu.telematics.student_Nikitin.lab3_Queue;

import java.util.Random;

public class Queue extends Thread {
	
	private static int _countOfCustomers = 30;
	
	public static void main(String[] args){
		
		long timeBeforeNextCustomer = 100 + new Random().nextInt(100);
		Shop shop = new Shop();
		
		for(int i=1; i<=_countOfCustomers; i++){
			new Customer(i,shop).start();
			try {
				Thread.sleep(timeBeforeNextCustomer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
