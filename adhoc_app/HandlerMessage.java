package adhoc_app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HandlerMessage extends Thread {
    private InetAddress address;
    
    HandlerMessage() {
    }
    
    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(9999);
            
            while(true) {
                Socket client = ss.accept();

                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String str = inFromClient.readLine();
                String[] parts = str.split(" ",3);
                
                String host = Utilities.encontraHostName();
                this.address = InetAddress.getByName("localhost");
                
                TW th = new TW(address, host, parts[1].toUpperCase(),parts[2]);
                byte[] bs = Utilities.serializa(th);
                
                if(bs.length <= TW.messagesize) {
                    DatagramPacket packet = new DatagramPacket(bs,bs.length,this.address,9999);
                    DatagramSocket clientSocket = new DatagramSocket();
                    clientSocket.send(packet);
                }
                client.close();
            } 
        } catch (IOException ex) {
            Logger.getLogger(HandlerMessage.class.getName()).log(Level.SEVERE, "HANDLERMESSAGE", ex);
        }
    }
}
