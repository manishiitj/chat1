
import java.io.*;
import java.net.*;
import java.util.Scanner;



public class Server {
    private InputStream inStream;
    private OutputStream outStream; 
    private Scanner in;
    private PrintWriter out;
    private ServerSocket serverSocket;
    private Socket socket;

    public void run()
    {
        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Waiting for connection");
            socket = serverSocket.accept(); 

            outStream = socket.getOutputStream();
            inStream = socket.getInputStream(); 
            out = new PrintWriter(outStream, true);
            in = new Scanner(inStream);             

            while(true) {
            //======== PROBLEM HERE COULD BE HERE=======//
                if(in.hasNextInt()) {
                    int x = in.nextInt();
                    int y = in.nextInt();
                    System.out.println(x);
                    System.out.println(y);
                }
            }   
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            // close everything closable
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } // end try/catch
        } // end finally
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();   
    }}
