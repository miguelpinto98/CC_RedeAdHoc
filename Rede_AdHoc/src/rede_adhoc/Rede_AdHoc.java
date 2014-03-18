package rede_adhoc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

//servidor UDP

public class Rede_AdHoc {

    public static void main(String[] args) throws SocketException, IOException {
        DatagramSocket s= new DatagramSocket(9999); byte[] aReceber = new byte[1024];

        while(true) {
            DatagramPacket pedido = new DatagramPacket(aReceber, aReceber.length);
            s.receive(pedido);
            String pedidoString = new String(pedido.getData(), 0, pedido.getLength());
            
            InetAddress IPAddress = pedido.getAddress();
            int porta = pedido.getPort();
            byte[] aEnviar= pedidoString.toUpperCase().getBytes();
            DatagramPacket resposta = new DatagramPacket(aEnviar, aEnviar.length, IPAddress, porta);
            s.send(resposta);
        }
    }
}
