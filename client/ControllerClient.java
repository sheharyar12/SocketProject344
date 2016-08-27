package client;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;



public class ControllerClient extends Thread{

	public PrintWriter out ;
	private String[] ControllerFunctions = new String[4];
	
	public ControllerClient(PrintWriter pw) throws UnknownHostException, IOException{
		System.out.println("Controller Client Connected");
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
	
	//setting Controller as thread name at element 0 and the rest function numbers
	public void SetControllerMethods(){
		ControllerFunctions[0]= "Controller";
		ControllerFunctions[1]= "0";
		ControllerFunctions[2] = "1";
		ControllerFunctions[3] = "2";
	}
	
	public void run(){
		try {
			SetControllerMethods();
			//writing the message into the printWriter where the Client helper in server side can read.
			functionToCHelper(ControllerFunctions[0].toString()+ControllerFunctions[1].toString());
			functionToCHelper(ControllerFunctions[0].toString()+ControllerFunctions[2].toString());
			functionToCHelper(ControllerFunctions[0].toString()+ControllerFunctions[3].toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}

