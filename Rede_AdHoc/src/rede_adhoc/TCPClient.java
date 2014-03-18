package rede_adhoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

class TCPClient {
    
    public static void main (String args[]) throws Exception { 
        InetAddress serverAddress = InetAddress.getByName("localhost");
        try (Socket socket = new Socket(serverAddress, 9999)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String pedido = "Pedido ao servidor…";
            out.println(pedido);
            String resposta = in.readLine();
            System.out.println("Resposta:" + resposta);
            socket.close();
        } 
    } 
}