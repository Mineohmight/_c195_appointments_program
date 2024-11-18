package database;
import java.io.Serializable;
import java.util.Objects;

public class _Triplet<K, V> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private K key; // The first value (key)
    private V value; // The second value (value)

    /**
     * Constructs a new Pair with the specified key and value.
     *
     * @param key The first value of the pair.
     * @param value The second value of the pair.
     */
    public _Triplet(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the first value (key) of the pair.
     *
     * @return The first value (key).
     */
    public K getKey() {
        return key;
    }

    /**
     * Returns the second value (value) of the pair.
     *
     * @return The second value (value).
     */
    public V getValue() {
        return value;
    }

    /**
     * Sets the first value (key) of the pair.
     *
     * @param key The new key to set.
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Sets the second value (value) of the pair.
     *
     * @param value The new value to set.
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Checks whether this pair is equal to another object.
     * Two pairs are equal if both the key and the value are equal.
     *
     * @param o The object to compare this pair to.
     * @return true if the specified object is a pair and both the key and value are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        _Triplet<?, ?> pair = (_Triplet<?, ?>) o;
        return Objects.equals(key, pair.key) &&
               Objects.equals(value, pair.value);
    }

    /**
     * Returns the hash code for this pair.
     * The hash code is based on the key and value.
     *
     * @return The hash code for this pair.
     */
    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    /**
     * Returns a string representation of the pair.
     *
     * @return A string representation of the pair in the format "key=value".
     */
    @Override
    public String toString() {
        return key + "=" + value;
    }
}