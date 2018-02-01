package multicastimagesender;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class MulticastImageSender {

 private static final int TIMEOUT = 3000;     // Time between sends (milliseconds)
  private static final int MAXFILELEN = 65000; // File must fit in single datagram

  public static void main(String[] args) throws IOException, InterruptedException {


    //InetAddress multicastAddress = InetAddress.getByName("234.4.5.6");
    InetAddress multicastAddress = InetAddress.getByName("239.255.255.250");
      int destPort = 1234;  // Destination port of multicast packets
    //int destPort = 5000;
    int TTL = 10;

    // Create a UDP multicast socket with any available local port
    MulticastSocket socket = new MulticastSocket();
    socket.setTimeToLive(TTL);  // Set the TTL

//    for (int i=3; i < args.length; i++)s
 JOptionPane.showMessageDialog(null, "Pilih Gambar", "Warning", JOptionPane.PLAIN_MESSAGE);
      JFileChooser  chooser = new JFileChooser();
        chooser.setDialogTitle("PILIH GAMBAR");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            System.out.println(chooser.getSelectedFile());
            RandomAccessFile file = new RandomAccessFile(chooser.getSelectedFile(), "r");

      if (file.length() > MAXFILELEN)
        throw new IOException("File too big");

      byte [] fileBuffer = new byte[(int) file.length()];
      file.read(fileBuffer);
      file.close();

      // Create a datagram to send
      DatagramPacket sendPacket = new DatagramPacket(fileBuffer,
        fileBuffer.length, multicastAddress, destPort);

      socket.send(sendPacket);  // Send the echo string
      System.out.println("Sent " + "file" + " to " +
                  sendPacket.getAddress().getHostAddress() +
                 " on port " + sendPacket.getPort());
      Thread.sleep(TIMEOUT);
      
      socket.send(sendPacket);  // Send the echo string
      System.out.println("Sent " + "file" + " to " +
                  sendPacket.getAddress().getHostAddress() +
                 " on port " + sendPacket.getPort());
//    }
    socket.close();
        }
//    {   
  }   
}