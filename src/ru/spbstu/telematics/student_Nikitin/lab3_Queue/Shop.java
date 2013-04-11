package ru.spbstu.telematics.student_Nikitin.lab3_Queue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Shop {
	
	private final int _maxTicketNum;
	private int _ticketNum;
	private int _servicedNum;
	private boolean _serviceBusy;
	private boolean _ticketMachineBusy;
	private Lock _lockTicketMachine = new ReentrantLock();
	private Lock _lockService = new ReentrantLock();
	private Condition _condTicketMachine = _lockTicketMachine.newCondition();
	private Condition _condService = _lockService.newCondition();
	
	public Shop(){
		_maxTicketNum = 20;
		_ticketNum = 1;
		_servicedNum = 1;
		_serviceBusy = false;
		_ticketMachineBusy = false;
	}
	
	public void startTakingTicket(){
		
		_lockTicketMachine.lock();
		if(_ticketMachineBusy){
			try {
				_condTicketMachine.await();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			finally{
				_ticketMachineBusy = true;
				_lockTicketMachine.unlock();
			}			
		}
		
		if(!_ticketMachineBusy){
			_ticketMachineBusy = true;
			_lockTicketMachine.unlock();
		}
	}

	public int finishTakingTicket(){
		
		int tempTicketNum;
		
		_lockTicketMachine.lock();		
		_ticketMachineBusy = false;
		tempTicketNum = _ticketNum;
		if(_ticketNum < _maxTicketNum)
			_ticketNum ++;
		else 
			_ticketNum = 1;
		_condTicketMachine.signal();
		_lockTicketMachine.unlock();
		
		return tempTicketNum;
	}
	
	public void startService(){
		
		_lockService.lock();
		if(_serviceBusy){
			try {
				_condService.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			finally {
				_serviceBusy = true;
				_lockService.unlock();
			}
		}
		
		if(!_serviceBusy){
			_serviceBusy = true;
			_lockService.unlock();
		}
	}
	
	public void finishService(){
		
		_lockService.lock();
		if(_servicedNum < _maxTicketNum)
			_servicedNum ++;
		else 
			_servicedNum = 1;
		_serviceBusy = false;
		_condService.signal();
		_lockService.unlock();		
	}
}
