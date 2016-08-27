package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientHelper extends Thread{


	private Thread t;
	private String name;
	public static Passengers passenger;
	private static int CarCount =0,ControllerCount = 0;
	public static AtomicInteger pCount= new AtomicInteger(0),cCount= new AtomicInteger(0),conCount= new AtomicInteger(0),seats = new AtomicInteger(5);
	public static Car car;
	public static controller controller;
	public static String text= "";
	public BufferedReader in;
	
	public ClientHelper(Socket ClientSocket,BufferedReader br) throws UnknownHostException, IOException
	{
		name = "Client Helper";
		in = br;
	}

	/**
	 * ReadInput
	 * Description: Reads from the bufferedReader 
	 * @return
	 * @throws NumberFormatException
	 * @throws InterruptedException
	 */
	public String readInput() throws NumberFormatException, InterruptedException{
		try {
			System.out.println("Client Helper is reading from buffer");
			text = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	
	public void run(){
		try {
			//readInput function reads from the buffered reader
			String functionNumber = readInput();
			
			/**
			 * Call functions where the text "functionNumber" is separated into two things, thread name 
			 * and method number which gets passed to run method. Where run method calls functions 
			 * from other classes
			 */
			createPassengers(functionNumber);
			createCars(functionNumber);
			sleep(1000);	
			RideCar(functionNumber);
			WaitOnController(functionNumber);
			CheckCar(functionNumber);
			CheckCar(functionNumber);
			ReadyToleave(functionNumber);
			sleep(1000);
			ControllerChecksPassenger(functionNumber);
			ControllerLeaves(functionNumber);
		} catch (NumberFormatException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	

	/**
	 * RunMethod
	 * Description: whatever the thread name is and the desire method number is being passed to the cases.
	 * @param name
	 * @param methodNumber
	 * @throws InterruptedException
	 */

	public void runMethod(String name , int methodNumber) throws InterruptedException
	{
		if (name.equals("Passenger")){
			if(passenger==null){
				passenger = new Passengers("Passenger",pCount.incrementAndGet());
			}
			switch (methodNumber){
				case 0: 
						passenger.PassengerLooksForCar();
						break;
				case 1:
					sleep(1000);
					passenger.EnjoyRide();
					break;
				case 2:
					passenger.PassengersGettingReadyToLeave();
					break;
			}
		}
		else
			if (name.equals("Car")){
				switch (methodNumber){
					case 0: 
							car.takePassengers();
							break;
					case 1:
							if(car==null)
								break;
							car.waitForController();
							break;
				}
			}
			else
				if (name.equals("Controller")){
					switch (methodNumber){
						case 0: 
							controller.checkingCar();
							break;
						case 1:
							if(controller==null)
								break;
							controller.ControllerReadyToLeave();
							break;
						case 2:
							controller.noMorePassengers();
							break;
					}
				}
			
				
	}
	
	
	/**
	 * ----------------------------------------------------------------------------------------------------------------------------------------------------------
	 * Functions that accepts the parameter text depending on what is being passed and will call 
	 * the run method to choose one of the cases.
	 */
	
	public void CheckCar(String text) throws NumberFormatException, InterruptedException{
		if(text.equals("Controller0")){
			if(ControllerCount<=1){
				ControllerCount++;
				controller = new controller("Controller",conCount.incrementAndGet());
			}
			runMethod(text.substring(0, text.length()-1),Integer.parseInt(text.substring(text.length()-1, text.length())));	
		}
	}
	
	public void createCars(String text) throws NumberFormatException, InterruptedException{
		if(text.equals("Car0")){
			if(CarCount<=1){
				CarCount++;
				car = new Car("Car",cCount.incrementAndGet(),seats);
			}
			runMethod(text.substring(0, text.length()-1),Integer.parseInt(text.substring(text.length()-1, text.length())));
		}
	}
	
	private void ControllerLeaves(String functionNumber) throws NumberFormatException, InterruptedException {
		if(text.equals("Controller1"))
			runMethod(text.substring(0, text.length()-1),Integer.parseInt(text.substring(text.length()-1, text.length())));		
	}
	
	private void ControllerChecksPassenger(String functionNumber) throws NumberFormatException, InterruptedException {
		if(text.equals("Controller2"))
			runMethod(text.substring(0, text.length()-1),Integer.parseInt(text.substring(text.length()-1, text.length())));	
	}
	
	public void createPassengers(String text) throws NumberFormatException, InterruptedException{
		if(text.equals("Passenger0"))
			runMethod(text.substring(0, text.length()-1),Integer.parseInt(text.substring(text.length()-1, text.length())));	
	}
	
	public void RideCar(String text) throws NumberFormatException, InterruptedException{
		if(text.equals("Passenger1"))
			runMethod(text.substring(0, text.length()-1),Integer.parseInt(text.substring(text.length()-1, text.length())));	
	}
	
	public void ReadyToleave(String text) throws NumberFormatException, InterruptedException{
		if(text.equals("Passenger2"))
			runMethod(text.substring(0, text.length()-1),Integer.parseInt(text.substring(text.length()-1, text.length())));	
	}

	public void WaitOnController(String text) throws NumberFormatException, InterruptedException{
		if(text.equals("Car1"))
			runMethod(text.substring(0, text.length()-1),Integer.parseInt(text.substring(text.length()-1, text.length())));
	}

	/**
	 * --------------------------------------------------------------------------------------------------------------------------------------------
	 */
	//thread function being overwritten.
	public void start(){
		System.out.println("Main Server is starting "+ name);
		if(t== null){
			t = new Thread(this, name);
			t.start();
		}
	}
}
