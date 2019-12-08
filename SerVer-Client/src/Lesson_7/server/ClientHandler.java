package Lesson_7.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    DataInputStream in;
    DataOutputStream out;
    MainServ serv;
    String nick;

    public ClientHandler(MainServ serv, Socket socket){
        try {
            this.socket = socket;
            this.serv = serv;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
             new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String[] tokens;
                        while (true) {
                            String msg = in.readUTF();
                            if (msg.startsWith("/auth")) {
                                tokens = msg.split(" ");
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (newNick != null) {
                                    sendMsg("/authok");
                                    AuthService.updateConnectTrue(tokens[1], tokens[2]);
                                    nick = newNick;
                                    serv.subscribe(ClientHandler.this,nick);
                                    break;
                                }
                                else {
                                    sendMsg("Неверный логин/пароль или вход был произведен");
                                }
                            }
                        }


                        while (true) {
                            String msg = in.readUTF();
                            if (msg.equals("/end")) {
                               AuthService.updateConnectFalse(tokens[1], tokens[2]);
                                out.writeUTF("/serverClosed");
                                break;
                            }
                           if(msg.indexOf("/w")!=-1){
                            String[] tokennick=msg.split(" ");
                                if (AuthService.existsNick(tokennick[1])) {
                                    serv.broadcastMsg(nick + " " + msg.subSequence(msg.lastIndexOf(tokennick[1]), msg.length()), tokennick[1]);
                                } else {
                                    sendMsg("Пользователь не зарегистрирован или отсутствует в сети");
                                }

                            }else
                            serv.broadcastMsg(nick + " " + msg,"");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        serv.unsubscribe(ClientHandler.this,nick);
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
