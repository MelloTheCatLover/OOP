package ru.nsu.kozoliy;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static ObjectInputStream inputStream;

    private static ObjectOutputStream outputStream;
    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                System.out.println("Сервер запущен!"); // хорошо бы серверу
                //   объявить о своем запуске
                clientSocket = server.accept(); // accept() будет ждать пока
                //кто-нибудь не захочет подключиться
                try { // установив связь и воссоздав сокет для общения с клиентом можно перейти
                    // к созданию потоков ввода/вывода.
                    // теперь мы можем принимать сообщения
                    // и отправлять
                    inputStream = new ObjectInputStream(clientSocket.getInputStream());
                    outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

                    int[] numbers = (int[]) inputStream.readObject(); // ждём пока клиент что-нибудь нам напишет
                    System.out.println(numbers.length);

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } finally { // в любом случае сокет будет закрыт
                    clientSocket.close();
                    // потоки тоже хорошо бы закрыть
                    inputStream.close();
                    outputStream.close();

                    server.
                }
            } finally {
                System.out.println("Сервер закрыт!");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}