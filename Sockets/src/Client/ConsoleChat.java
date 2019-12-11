package Client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ConsoleChat {

        static Scanner sc;
        static DataOutputStream out;
        static DataInputStream in;
        static Socket socket;
        final static String IP_adres="localhost";
        final static int port=8189;

  public static void main(String []args){
      try {
          socket = new Socket(IP_adres, port);
          sc=new Scanner(System.in);
          out=new DataOutputStream(socket.getOutputStream());
          in=new DataInputStream(socket.getInputStream());
          Thread outmsg= new Thread(new Runnable() {
              @Override
              public void run() {
                  while (true) {
                      String str = sc.nextLine();
                      try {
                          out.writeUTF(str);
                      } catch (IOException e) {
                          e.printStackTrace();
                      }
                  }
              }
          });
           Thread inmsg= new Thread(new Runnable() {
              @Override
            public void run() {
              while (true) {
                try {
                  String str=in.readUTF();
                    System.out.println("Server "+str);
           } catch (IOException e) {
             e.printStackTrace();
          }
          }
          }
          });
          outmsg.start();
          inmsg.start();
          try {
              outmsg.join();
           //   inmsg.join();
          }catch (InterruptedException e){
              e.printStackTrace();
          }


      }
      catch(IOException e){
          e.printStackTrace();
      }finally{
          try {
              socket.close();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
  }
}


