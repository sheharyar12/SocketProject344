package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import server.Car;
import server.Passengers;
import server.controller;

public class PassengerClient extends Thread{
	private String[] PassengersFunction = new String[4];
	public static AtomicInteger seatSize = new AtomicInteger(5);
	public PrintWriter out;
	
	public PassengerClient(PrintWriter pw) throws UnknownHostException, IOException{
		System.out.println("Passenger Client Connected");
		out = pw;
	}
	
	/**
	 * functionToCHelper
	 * Description: function passes writes a message to the printWriter
	 * so a beufferedReader can read from the server side.
	 * @param name
	 * @throws IOException
	 */
	public void functionToCHelper(String name) throws IOException{
		out.println(name);	
	}
	
	
	//setting Passenger as thread name at element 0 and the rest function numbers
	public void SetPassengersMethods(){
		PassengersFunction[0]= "Passenger";
		PassengersFunction[1]= "0";
		PassengersFunction[2]= "1";
		PassengersFunction[3]= "2";
	}

	
	public void run(){
		SetPassengersMethods();
		try {
			//writing the message into the printWriter where the Client helper in server side can read.
			functionToCHelper(PassengersFunction[0].toString()+PassengersFunction[1].toString());
			functionToCHelper(PassengersFunction[0].toString()+PassengersFunction[2].toString());
			functionToCHelper(PassengersFunction[0].toString()+PassengersFunction[3].toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
