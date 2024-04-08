package ru.nsu.kozoliy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            int[] array = (int[]) in.readObject();
            boolean result = processRequest(array);
            out.writeBoolean(result);
            out.flush();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean processRequest(int[] array) {
        for (int number : array) {
            if (isComposite(number)) {
                return true;
            }
        }
        return false;
    }

    private boolean isComposite(int n) {
        if (n <= 1) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return true;
        }
        return false;
    }
}
