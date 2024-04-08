package ru.nsu.kozoliy;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private static Socket clientSocket; //сокет для общения

    private static ObjectInputStream inputStream;

    private static ObjectOutputStream outputStream;



    public static void main(String[] args) throws IOException {
        int[] numbers = {1, 2, 3, 4, 5};

        try {
                try {
                    // адрес - локальный хост, порт - 4004, такой же как у сервера
                    clientSocket = new Socket("localhost", 4004); // этой строкой мы запрашиваем
                    //  у сервера доступ на соединение
                    outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    inputStream = new ObjectInputStream(clientSocket.getInputStream());


                    outputStream.writeObject(numbers);
                    outputStream.flush();

                    boolean result = inputStream.readBoolean();
                    System.out.println("Result from server: " + result);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Клиент был закрыт...");
                clientSocket.close();
                inputStream.close();
                outputStream.close();
            }
    }

}
