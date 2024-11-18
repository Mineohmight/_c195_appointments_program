package database;
import javafx.util.Pair;

/**
 * The Duo class extends the Pair clas and allows for the management of two additional key-value pairs.
 * It inherits the behavier from Pair while introducing new fields (key2 and value2) and adding 
 * three additional fields (one, two, and three) with coresponding getter and setter methods.
 *
 * @param <K> Type of the key, which must extend Object
 * @param <V> Type of the value, which must extend Object
 */
public class _Duo<K extends Object, V extends Object> extends Pair<K, V> {

    // Additional key-value pair fields, key2 and value2
    private K key2;   // Second key of generic type K
    private V value2; // Second value of generic type V

    // Fields with specific data types (String and int)
    private String one;   // A String field
    private String two;   // Another String field
    private int three;    // An integer field

    /**
     * Constructor for the Duo class, which initializes the first key and value through the Pair constructor.
     * The additional key-value pair (key2 and value2) is not initialized in this constructor.
     *
     * @param key The first key (inherited from Pair)
     * @param value The first value (inherited from Pair)
     */
    public _Duo(K key, V value) {
        super(key, value); // Call to the Pair constructor to initialize the key and value
    }

    // Getter and setter methods for the "one" field
    /**
     * Retrieves the value of the field 'one'.
     *
     * @return the current value of 'one'
     */
    public String getOne() {
        return one;
    }

    /**
     * Sets the value of the field 'one'.
     *
     * @param one The new value to assign to 'one'
     */
    public void setOne(String one) {
        this.one = one;
    }

    // Getter and setter methods for the "two" field
    /**
     * Retrieves the value of the field 'two'.
     *
     * @return the current value of 'two'
     */
    public String getTwo() {
        return two;
    }

    /**
     * Sets the value of the field 'two'.
     *
     * @param two The new value to assign to 'two'
     */
    public void setTwo(String two) {
        this.two = two;
    }

    // Getter and setter methods for the "three" field
    /**
     * Retrieves the value of the field 'three'.
     *
     * @return the current value of 'three'
     */
    public int getThree() {
        return three;
    }
}