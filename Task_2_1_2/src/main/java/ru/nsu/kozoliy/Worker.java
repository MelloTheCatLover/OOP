package ru.nsu.kozoliy;

public class Worker implements Runnable {
    private final int[] array;
    private final BroadcastChannel broadcastChannel;

    public Worker(int[] array, BroadcastChannel broadcastChannel) {
        this.array = array;
        this.broadcastChannel = broadcastChannel;
    }

    @Override
    public void run() {
        boolean hasComposite = false;
        for (int number : array) {
            if (isComposite(number)) {
                hasComposite = true;
                break; // Если найдено непростое число, прекратить дальнейшую проверку
            }
        }
        broadcastChannel.sendMessage(hasComposite);
    }

    private boolean isComposite(int n) {
        if (n <= 1) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return true;
        }
        return false;
    }
}
