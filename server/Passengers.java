package server;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class Passengers extends Thread{
	
	private String name;
	private Thread t;
	private static boolean waitForCar = true;
	private volatile int count =0;
	public  static Vector<Object> WaitingPassengers = new Vector<Object>();
	public static int carNum;
	public static boolean ridCar = false;
	public static long time = System.currentTimeMillis();
	public static volatile AtomicInteger counter = new AtomicInteger(0);
	public static volatile AtomicInteger left = new AtomicInteger(main.m);
	
	public Passengers(String name, int num){
		count = num;
		this.name = name;
		waitForCar = true;
		ridCar = false;
	}
	
	public void test(){
		System.out.println("PASSENGER Method is called WORKS for passenger" + counter.get());
	}
	
	public void setWaitForCarfalse(){
		waitForCar = false;
	}

	public synchronized void sitInCar(int num){
		carNum = num;
		if(WaitingPassengers.size()>0){
			synchronized (WaitingPassengers.elementAt(0)) {
	            WaitingPassengers.elementAt(0).notify();
	         }
	         WaitingPassengers.removeElementAt(0);
		}
	}
	
	@SuppressWarnings("static-access")
	public void sleep(){
		try {
			t.sleep((long)(Math.random() * 1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void PassengerLooksForCar(){
		Object convey = new Object();
		synchronized(convey){
			loop:
			while(waitForCar)
			{
	            try{
	            	System.out.println("["+(System.currentTimeMillis()-time)+"]" + "Passenger " + this.count + " is wandering");
	            	this.sleep();
	            	counter.incrementAndGet();
	            	left.decrementAndGet();
	            	//System.out.println(counter+ " COUNTER" + " Left:" + left + " SeatSize" + main.seatSize.get());
	            	
	            	
	            	if(counter.get()==client.Client.seatSize.get() || left.get()==0)
	            	{
	            		Car.carAv=true;
	            		ClientHelper.car.notifyCar();
	            	}
	            	if(counter.get()>client.Client.seatSize.get())
	            	{
	            		counter.set(0);
	            	}
	            	System.out.println("["+(System.currentTimeMillis()-time)+"]" + "Passenger " + this.count + " gets in line for a Car");
	            	WaitingPassengers.addElement(convey);
	                ridCar = true;
	                //System.out.println(Car.PassengersLeft.get() + " Passengersleft" );
	                
	                convey.wait();
	                counter.set(0);
	                t.sleep((long)(Math.random() * 1000));
	                System.out.println("["+(System.currentTimeMillis()-time)+"]" + this.name + this.count + " Sits in Car");
	                if(ridCar==true)
	                {
	                	//System.out.println("BREAKSSS");
	                	break loop;
	                }
	                
	            }catch(InterruptedException e){
	                e.printStackTrace();
	            }
			}
		}
	}
	
	
	public void LookForCar(){
		System.out.println("Passenger is wandering");
		System.out.println("Passenger is looking for a Car");
	}
	
	public void EnjoyRide() throws InterruptedException{
		t.sleep((long)(Math.random() * 1000));
		System.out.println("Passengers are enjoying the ride");
	}
	
	public void PassengersGettingReadyToLeave() throws InterruptedException{
		t.sleep((long)(Math.random() * 4000));
		System.out.println("Passenger gets out the Car");
	}
	
	public void run(){
		PassengerLooksForCar();	
	}
	

	public void start(){
		System.out.println("Starting " + name + " "+ count);
		if(t== null){
			t = new Thread(this, name);
			t.start();
		}
	}
}
