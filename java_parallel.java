import java.util.*;

public class Code {
  public static void main(String[] args) {    
    List<String> myList = new ArrayList();
    myList.add("apple");
    myList.add("orange");
    myList.add("cherry");
    Pair somePair = new Pair<String>("key1", "value1");
    System.out.println(myList);
    System.out.println(somePair.key);
    System.out.println(somePair.value);
    
    Pair somePair2 = new Pair<Double>("quantity1", 2.4);
    System.out.println(somePair2.value);
  }
  
  public static class Pair<V> {
  	public String key;
 		public V value;

    public Pair(String key, V value) {
      this.key = key; 
      this.value = value;
    }
  }
}

