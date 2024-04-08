package ru.nsu.kozoliy;

import java.util.concurrent.atomic.AtomicBoolean;

public class BroadcastChannel {
    private final AtomicBoolean hasComposite = new AtomicBoolean(false);

    public synchronized void sendMessage(boolean hasComposite) {
        if (!this.hasComposite.get()) {
            this.hasComposite.set(hasComposite);
        }
    }

    public boolean hasComposite() {
        return hasComposite.get();
    }
}
