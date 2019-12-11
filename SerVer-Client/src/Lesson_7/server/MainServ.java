package Lesson_7.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MainServ {
   // private Vector<ClientHandler> clients;

   private Map<ClientHandler,String> clients;
    public MainServ() {
        clients = new HashMap<ClientHandler, String>();
        ServerSocket server = null;
        Socket socket = null;
        try {
            AuthService.connect();
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен!");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился!");
                new ClientHandler(this, socket);
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
            AuthService.updateConnectAll();
            AuthService.disconnect();
        }
    }

    public synchronized void broadcastMsg(String msg,String nick) {
        if(!nick.equals("")){
            for (Map.Entry<ClientHandler, String> pair : clients.entrySet()) {
                if(nick.equals(pair.getValue())){
                    pair.getKey().sendMsg(msg);
                }
            }
        }
        else{
            for(ClientHandler name : clients.keySet()) {
                name.sendMsg(msg);
            }
        }
    }

    public synchronized void subscribe(ClientHandler client,String nick) {
        clients.put(client,nick);
    }

    public synchronized void unsubscribe(ClientHandler client, String nick) {
        clients.remove(client,nick);
    }
}
