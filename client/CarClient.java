package client;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class CarClient extends Thread{

	public PrintWriter out ;
	private String[] CarFunction = new String[4];

	public CarClient(PrintWriter pw) throws UnknownHostException, IOException{
		System.out.println("Car Client Connected");
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
		out.println(name);//testing here		
	}
	
	//setting CarFunction as thread name at element 0 and the rest function numbers
	public void SetCarMethods(){
		CarFunction[0]= "Car";
		CarFunction[1]= "0";
		CarFunction[2] = "1";
		CarFunction[3] = "2";
	}
	
	
	
	public void run(){	
		SetCarMethods();
		try {
			//writing the message into the printWriter where the Client helper in server side can read.
			functionToCHelper(CarFunction[0].toString()+CarFunction[1].toString());
			functionToCHelper(CarFunction[0].toString()+CarFunction[2].toString());
			functionToCHelper(CarFunction[0].toString()+CarFunction[3].toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

