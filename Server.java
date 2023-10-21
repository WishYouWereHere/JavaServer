/***** import java server libs *****/
import java.net.ServerSocket;
import java.net.Socket;
/***********************************/
import java.io.BufferedReader;
/***** import logging libs *********/
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javafx.concurrent.Task;

import java.util.logging.Level;
/***********************************/

/***** import threading libs *********/
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**********************************/

public class Server {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
        Logger logger = log();
        logger.info("start logging");
        System.out.println("attempt to start server");
        try{
            ServerSocket aesServerSocket = new ServerSocket(8000);
            //Socket clientSocket = aesServerSocket.accept();
            while(true){
                try{
                Socket clientSocket = aesServerSocket.accept();
                System.out.println(clientSocket.getRemoteSocketAddress());
                executor.submit(new ClientHandler(clientSocket));
                }
                catch(RejectedExecutionException e){
                logger.log(Level.SEVERE, "while running", e);
                }
            }
        
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // ClientHandler class 
    private static class ClientHandler implements Runnable { 
        private final Socket clientSocket; 
  
        // Constructor 
        public ClientHandler(Socket socket) 
        { 
            this.clientSocket = socket; 
        } 
  
        public void run() 
        { 
            PrintWriter out = null; 
            BufferedReader in = null; 
            try { 
                    
                  // get the outputstream of client 
                out = new PrintWriter( 
                    clientSocket.getOutputStream(), true); 
  
                  // get the inputstream of client 
                in = new BufferedReader( 
                    new InputStreamReader( 
                        clientSocket.getInputStream())); 
  
                String line; 
                while ((line = in.readLine()) != null) { 
  
                    // writing the received message from 
                    // client 
                    System.out.printf( 
                        " Sent from the client: %s\n", 
                        line); 
                    out.println(line); 
                } 
            } 
            catch (IOException e) { 
                e.printStackTrace(); 
            } 
            finally { 
                try { 
                    if (out != null) { 
                        out.close(); 
                    } 
                    if (in != null) { 
                        in.close(); 
                        clientSocket.close(); 
                    } 
                } 
                catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
        } 
    } 
    
    public static Logger log(){
        Logger logger = Logger.getLogger(aesServer.class.getName());
        logger.setLevel(Level.FINE);
        String FileDir = System.getProperty("user.dir");
        try{
            FileHandler handler = new FileHandler(FileDir + "\\Logs");
            logger.addHandler(handler);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return logger;
    }
}
