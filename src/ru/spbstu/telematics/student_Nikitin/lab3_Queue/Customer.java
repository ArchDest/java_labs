package ru.spbstu.telematics.student_Nikitin.lab3_Queue;

import java.util.Random;

public class Customer extends Thread {
	
	private int _customerId;
	private int _ticketNum;
	private Shop _shop;

	public Customer(int customerId, Shop shop){
		this._shop = shop;
		this._customerId = customerId;
	}
	
	public void run(){
		
		long ticketMachineWorkTime = 200;
		long serviceTime = 200 + new Random().nextInt(450);
		
		System.out.print("В магазин пришел покупатель №" + _customerId + "\n");
		_shop.startTakingTicket();
		try {
			Thread.sleep(ticketMachineWorkTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		_ticketNum = _shop.finishTakingTicket();
		System.out.print("Покупатель №" + _customerId + " взял билет №" + _ticketNum + "\n");
		_shop.startService();
		try {
			Thread.sleep(serviceTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		_shop.finishService();
		System.out.print("Покупатель №" + _customerId + " был обслужен и ушел\n");
	}
}
