package ru.nsu.kozoliy;

public class Vertex<T> {
    private T object;

    Vertex(T object) {
        this.object = object;
    }

    T getObject() {
        return this.object;
    }

    void setObject(T object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vertex<T> vertex = (Vertex<T>) obj;
        return this.object.equals(vertex.object);
    }

    /**
     * Override hashCode method.
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.object.hashCode();
        result = result * 7;
        return result;
    }
}