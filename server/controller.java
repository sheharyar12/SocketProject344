package server;

import java.util.Vector;



public class controller extends Thread{

	
	private String name;
	private Thread t;
	public volatile int count =0;
	public  static Vector<Object> WaitingCon = new Vector<Object>();
	public static long time = System.currentTimeMillis();
	public static boolean leaving = false;
	public static boolean leaving2= false;
	
	public controller(String name, int num){
		this.name = name;
		count = num;
		System.out.println(name + " " + num + " is created");
	}
	
	public synchronized void notifyController(){	
		if(WaitingCon.size()>0){
			synchronized (WaitingCon.elementAt(0)) {
	            WaitingCon.elementAt(0).notify();
	         }
	         WaitingCon.removeElementAt(0);
		}
	}
	
	public void checkingCar(){
		Object convey = new Object();
		synchronized(convey){
			ClientHelper.car.notifyFullCar();
		}
	}
	
	public void ControllerReadyToLeave(){
		if(leaving == false){
			leaving = true;
			System.out.println("["+(System.currentTimeMillis()-time)+"]" + "Controller Leaves");
		}	
	}
	
	public void noMorePassengers(){
		if(leaving2 == false){
			leaving2=true;
			System.out.println("["+(System.currentTimeMillis()-time)+"]" + "Controller sees No more passengers");
		}	
	}

	public void run()
	{
		//checkForFullCar();
	}
	
	public void start(){
		System.out.println("Starting " + name + " "+ count);
		if(t== null){
			t = new Thread(this, name);
			t.start();
		}
	}
}
