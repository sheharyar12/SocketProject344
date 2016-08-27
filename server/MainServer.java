package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.*;

import org.omg.CORBA.portable.OutputStream;

import client.CarClient;
import client.Client;
import client.ControllerClient;
import client.PassengerClient;

public class MainServer {
	
	public static Socket clientSocket;
	public static ControllerClient controller;
	public static CarClient car;
	public static AtomicInteger seatSize;
	public static ServerSocket serverSocket;
	
	
	public MainServer() throws UnknownHostException, IOException
	{		
				int portNumber = 3700;
				try {
					System.out.println("Looking for server to connect.");
					serverSocket = new ServerSocket(portNumber);
					clientSocket = serverSocket.accept();
					BufferedReader in = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));
					System.out.println("Server CONNECTED!");
					
					for(int i=1;i<=24;i++){
						ClientHelper cHelper = new ClientHelper(clientSocket,in);
						cHelper.start();
					}
				}
				catch (Exception e) {
					System.err.println("Exception:  " + e);
				}

	}
	
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		new MainServer();
	}

}
