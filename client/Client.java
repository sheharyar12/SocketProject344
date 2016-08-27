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

public class Client{
	public static PassengerClient passenger;
	public static CarClient car;
	public static ControllerClient controller;
	public static AtomicInteger seatSize = new AtomicInteger(5);
	public static PrintWriter pw;
	
	public static void main(String args[]) throws UnknownHostException, IOException, InterruptedException{
		Socket kksocket = new Socket("127.0.0.1",3700);
		PrintWriter pw = new PrintWriter(kksocket.getOutputStream(),true);

		for(int i=1;i<=5;i++){
			passenger = new PassengerClient(pw);
			passenger.start();	
		}
		for(int i=1;i<=2;i++){	
			car = new CarClient(pw);
			car.start();
		}
		
		for(int i=1;i<=1;i++){	
			controller = new ControllerClient(pw);
			controller.start();
		}
	}
}
