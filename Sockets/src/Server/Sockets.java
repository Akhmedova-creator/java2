package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Sockets {
    public static void main(String[] args) throws IOException {
        ServerSocket server = null;
        Socket socket = null;
        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен!");
            socket = server.accept();
            System.out.println("Клиент подключился!");
            Scanner sc=new Scanner(System.in);
            DataOutputStream out=new DataOutputStream(socket.getOutputStream());
            DataInputStream in=new DataInputStream(socket.getInputStream());

              Thread inmsg= new Thread(new Runnable() {
                @Override

              public void run() {
                while (true) {
                    try {
                        String str=in.readUTF();
                         System.out.println("Client "+str);
              } catch (IOException e) {
              e.printStackTrace();
             }
            }
             }
            });
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
            outmsg.start();
            inmsg.start();
            try {
                outmsg.join();
                inmsg.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}






