package server;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;


public class Car extends Thread{
	private String name;
	public volatile int count =0;
	private Thread t;
	public static AtomicInteger seats = new AtomicInteger();
	public  static Vector<Object> WaitingCars = new Vector<Object>();
	public  static Vector<Object> FullCars = new Vector<Object>();
	public static boolean carAv = false;
	private static int tempSeats = 5;
	public static boolean departure;
	public static AtomicInteger PassengersLeft = new AtomicInteger(0);
	public static long time = System.currentTimeMillis();
	public static int car = 0;
	public static Object convey = new Object();

	public Car(String name, int num, AtomicInteger seat){
		seats = seat;
		this.name = name;
		count = num;
		departure = false;	
		System.out.println("["+(System.currentTimeMillis()-time)+"]" + this.name + this.count + " arrives.");
	}
	
	public synchronized void notifyCar(){	
		if(WaitingCars.size()>0){
			synchronized (WaitingCars.elementAt(0)) {
	            WaitingCars.elementAt(0).notify();
	         }
	         WaitingCars.removeElementAt(0);
		}
	}
	
	public synchronized void notifyFullCar(){	
		if(FullCars.size()>0){
			synchronized (FullCars.elementAt(0)) {
	            FullCars.elementAt(0).notify();
	         }
	         FullCars.removeElementAt(0);
		}
	}
	
	public void ReleasePassengers() throws InterruptedException{
		t.sleep((long)(Math.random() * 1000));
		System.out.println("["+(System.currentTimeMillis()-time)+"]" + "Car Releases Passengers");
	}
	
	public void takePassengers(){
		convey = new Object();
		synchronized(convey){
			while(Passengers.counter.get()!=5){}		
			carAv=true;
			if(carAv==true){
        		while(seats.get()>0){
        			//System.out.println(seats);
        			if(!Passengers.WaitingPassengers.isEmpty()){
        				seats.decrementAndGet();
        				ClientHelper.passenger.sitInCar(this.count);
        			}
        		}
			}
		}
	}
	
	public void waitForController() throws InterruptedException{
		Object convey = new Object();
		System.out.println("["+(System.currentTimeMillis()-time)+"]" + "Waiting for Controller");
		synchronized(convey){
			FullCars.addElement(convey);
			t.sleep((long)(Math.random() * 1000));
			convey.wait();
			System.out.println("["+(System.currentTimeMillis()-time)+"]" + "Controller gives permission since car is full");
			t.sleep((long)(Math.random() * 1000));
			System.out.println("["+(System.currentTimeMillis()-time)+"]" + "Car Drives around the park");
		}
	}
	
	public void run(){	

	}
	
	public void start(){	
		System.out.println("Starting " + name + " "+ count);
		if(t== null){
			t = new Thread(this, name);
			t.start();	
		}
	}



}
