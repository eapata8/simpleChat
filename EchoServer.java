// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
import java.io.*;
import common.ChatIF;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
 //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the server.
   */
   ChatIF serverUI;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   * @param serverUI The interface type variable.
   */
  public EchoServer(int port, ChatIF serverUI)throws IOException
  {
    super(port);
    this.serverUI = serverUI;
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
	  serverUI.display("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
  }

  public void handleMessageFromServerUI(String message)
  {
	  if(message.startsWith("#")) {
  		handleCommand(message);
  	}
  	else
  		serverUI.display("SERVER MSG> " +message);
        this.sendToAllClients("SERVER MSG> " + message); 
  }
  
  public void handleCommand(String message)
  {
	  if(message.equals("#quit")) {
  		System.exit(0);
  	}
  	else if(message.equals("#stop")) {
  		stopListening();
  	}
  	else if(message.equals("#close")) {
  		try {
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
  	}
  	else if(message.startsWith("#setport")) {
  		if (!isListening()& getNumberOfClients()==0) {
  			String port = message.substring(message.indexOf("<") + 1, message.indexOf(">"));
  		  try {
  			  setPort(Integer.parseInt(port));
  		  	  serverUI.display("The port has been set at "+port);
  		  	}
  		  catch(NumberFormatException n) {
  				n.printStackTrace();
  		  	}
  		}
  	}
  	else if(message.equals("#start")) {
  		if (!isListening()){
          try{
            listen();
          }
          catch(Exception s)
          {
            serverUI.display("The server cannot listen for clients!");
          }
        }
        else
        { 
        	serverUI.display("The server is already listening for clients.");
        }
  	}
  	else if (message.equals("#getport"))
    {
      serverUI.display("The current port is "+Integer.toString(getPort()));
    }
	  
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
	  serverUI.display("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
	  serverUI.display("Server has stopped listening for connections.");
  }
  
  /**
   * Implementation of Hook method called each time a new client connection is
   * accepted. The default implementation does nothing.
   * @param client the connection connected to the client.
   */
  @Override
  protected void clientConnected(ConnectionToClient client) {
	  serverUI.display("The connexion has started with" + client);
  }

  /**
   * Implementation of Hook method called each time a client disconnects.
   * The default implementation does nothing. The method
   * may be overridden by subclasses but should remains synchronized.
   *
   * @param client the connection with the client.
   */
  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	  serverUI.display("The connexion with" + client+"has stopped");
  }
 
}
//End of EchoServer class
