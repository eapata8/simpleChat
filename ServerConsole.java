//This file contains material supporting section 3.7 of the textbook:
//"Object Oriented Software Engineering" and is issued under the open-source
//license found at www.lloseng.com 

import java.io.*;
import java.util.Scanner;

import common.*;

/**
* This class constructs the UI for a EchoServer.  It implements the
* chat interface in order to activate the display() method.
* Warning: Some of the code here is cloned in ServerConsole 
*
* @author Fran&ccedil;ois B&eacute;langer
* @author Dr Timothy C. Lethbridge  
* @author Dr Robert Lagani&egrave;re
* @version September 2020
*/
public class ServerConsole implements ChatIF 
{
//Class variables *************************************************

/**
* The default port to connect on.
*/
final public static int DEFAULT_PORT = 5555;

//Instance variables **********************************************

/**
* The instance of the server
*/
EchoServer server;





/**
* Scanner to read from the console
*/
Scanner fromConsole; 


//Constructors ****************************************************

/**
* Constructs an instance of the ServerConsole UI.
*
* @param port The port to be connected with clients.
*/
public ServerConsole(int port) 
{
	try 
    {
      server = new EchoServer(port, this);
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!");
      System.exit(1);
    }

 try {
	 server.listen();// start listening for connections
 }catch(IOException s){
	 System.out.println("Error: Can't listen for connections");
	
 }
 
 fromConsole = new Scanner(System.in);
}


//Instance methods ************************************************

/**
* This method waits for input from the console.  Once it is 
* received, it sends it to the server's message handler.
*/
public void accept() 
{
 try
 {

   String message;

   while (true) 
   {
     message = fromConsole.nextLine();
     server.handleMessageFromServerUI(message);
   }
 } 
 catch (Exception ex) 
 {
   System.out.println
     ("Unexpected error while reading from console!");
 }
}

/**
* This method overrides the method in the ChatIF interface.  It
* displays a message onto the screen.
*
* @param message The string to be displayed.
*/
public void display(String message) 
{
 System.out.println(message);
}



//Class methods ***************************************************

/**
* This method is responsible for the creation of the Client UI.
*
* @param args[0] The host to connect to.
*/
public static void main(String[] args) 
{
  int port = 0; //Port to listen on

  try
  {
    port = Integer.parseInt(args[0]); //Get port from command line
  }
  catch(Throwable t)
  {
    port = DEFAULT_PORT; //Set port to 5555
  }
  
  
  ServerConsole serverChat = new ServerConsole(port);
  
    serverChat.accept(); //Start waiting for console
}

}
//End of ConsoleChat class
