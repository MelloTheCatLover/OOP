package ru.nsu.kozoliy;

import java.io.Serializable;

/**
 * <p>A convenience class to represent name-value pairs.</p>
 *
 * @since JavaFX 2.0
 */

public class Pair<K, V> implements Serializable {

    /**
     * Key of this <code>Pair</code>.
     */
    private final K key;

    /**
     * Gets the key for this pair.
     *
     * @return key for this pair
     */
    public K getKey() {
        return key;
    }

    /**
     * Value of this <code>Pair</code>.
     */
    private V value;

    /**
     * Gets the value for this pair.
     *
     * @return value for this pair
     */
    public V getValue() {
        return value;
    }

    /**
     * Creates a new pair.
     *
     * @param key The key for this pair
     *
     * @param value The value to use for this pair
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * <p><code>String</code> representation of this
     * <code>Pair</code>.</p>
     *
     * <p>The default name/value delimiter '=' is always used.</p>
     *
     *  @return <code>String</code> representation of this <code>Pair</code>
     */
    @Override
    public String toString() {
        return key + ": " + value;
    }

    /**
     * <p>Generate a hash code for this <code>Pair</code>.</p>.
     *
     * <p>The hash code is calculated using both the name and
     * the value of the <code>Pair</code>.</p>
     *
     * @return hash code for this <code>Pair</code>
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (key != null ? key.hashCode() : 0);
        hash = 31 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    /**
     * <p>Test this <code>Pair</code> for equality with another.
     * <code>Object</code>.</p>
     *
     * <p>Two <code>Pair</code>s are considered equal if and only if
     * both the names and values are equal.</p>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Pair) {
            Pair pair = (Pair) o;
            if (key != null ? !key.equals(pair.key) : pair.key != null) {
                return false;
            }
            return value != null ? value.equals(pair.value) : pair.value == null;
        }
        return false;
    }
}