import java.util.LinkedList;
import Includes.DictionaryEntry;
import Includes.HashTableEntry;
import Includes.KeyAlreadyExistException;
import Includes.KeyNotFoundException;
import Includes.NullKeyException;

import java.lang.reflect.Array;

public class COL106Dictionary<K, V> {

    private LinkedList<DictionaryEntry<K, V>> dict;
    /*
     * dict is a Linked-List, where every node of linked-list is of type DictionaryEntry.
     * DictionaryEntry is a key-value pair, where the type of key and value is K and V respectively.
     */ 
    public LinkedList<HashTableEntry<K, V>>[] hashTable;
    /*
     * hashTable is an array of Linked-Lists which is initialized by the COL106Dictionary constructor.
     * Each index of hashTable stores a linked-list whose nodes are of type HashTableEntry
     * HashTableEntry is a key-address pair, where the type of key is K and the corresponding address is the address of the DictionaryEntry in the linked-list corresponding to the key of HashTableEntry
     */ 
    public int bucketsize;
    public int sizeofdictionary;
    @SuppressWarnings("unchecked")
    COL106Dictionary(int hashTableSize) {
        dict = new LinkedList<DictionaryEntry<K, V>>();
        // This statement initiailizes a linked-list where each node is of type DictionaryEntry with key and value of type K and V respectively.
        hashTable = (LinkedList<HashTableEntry<K, V>>[]) Array.newInstance(LinkedList.class, hashTableSize);
        // This statement initiailizes the hashTable with an array of size hashTableSize where at each index the element is an instance of LinkedList class and
        // this array is type-casted to an array of LinkedList where the LinkedList contains nodes of type HashTableEntry with key of type K. 
        bucketsize=hashTableSize;
        sizeofdictionary=0;
    }


    public void insert(K key, V value) throws KeyAlreadyExistException, NullKeyException {
        /*
         * To be filled in by the student
         * Input: A key of type K and it corresponding value of type V
         * Working: Inserts the argumented key-value pair in the Dictionary in O(1)
         */
        if(key==null){
            throw new NullKeyException();
        }
        DictionaryEntry<K,V> object=new DictionaryEntry<K,V>(key, value);
        //if(dict.contains(object)){
          //  throw new KeyAlreadyExistException();
        //}
       
        int insertionindex=hash(key);
        if(hashTable[insertionindex]==null){
            LinkedList<HashTableEntry<K, V>> you=new LinkedList<HashTableEntry<K,V>>();
            HashTableEntry<K,V> yup=new HashTableEntry<K,V>(key, object);
            you.addLast(yup);
            hashTable[insertionindex]=you;
            sizeofdictionary++;
        }
        else{
            LinkedList<HashTableEntry<K, V>> yos =hashTable[insertionindex];
            HashTableEntry<K,V> hehe=new HashTableEntry<K,V>(key, object);
            for (int i = 0; i < yos.size(); i++) { 
                if((yos.get(i)).key.equals(key)){
                    throw new KeyAlreadyExistException();
                } 
            }
            yos.addLast(hehe);
            sizeofdictionary++;
        }
        dict.addLast(object);
    }

    public V delete(K key) throws NullKeyException, KeyNotFoundException{
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key
         * Working: Deletes the key-value pair from the Dictionary in O(1)
         */

        if(key==null){
            throw new NullKeyException();
        }
        int insertionindex=hash(key);
        if(hashTable[insertionindex]==null){
            throw new KeyNotFoundException();
        }
        LinkedList<HashTableEntry<K, V>> cv =hashTable[insertionindex];
        boolean ans=true;
        V returnvalue=dict.get(0).value;
        for (int i = 0; i < cv.size(); i++) { 
            if((cv.get(i)).key.equals(key)){
                DictionaryEntry<K,V> object=(cv.get(i).dictEntry);
                returnvalue=object.value;
                cv.remove(i);
                dict.remove(object);
                ans=false;
            } 
        }
        if(ans){
            throw new KeyNotFoundException();
        }
        sizeofdictionary--;
        return returnvalue;
    }

    public V update(K key, V value) throws NullKeyException, KeyNotFoundException{
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the previously associated value of type V with the argumented key
         * Working: Updates the value associated with argumented key with the argumented value in O(1)
         */

        if(key==null){
            throw new NullKeyException();
        }
        int insertionindex=hash(key);
        if(hashTable[insertionindex]==null){
            throw new KeyNotFoundException();
        }
        LinkedList<HashTableEntry<K, V>> cv =hashTable[insertionindex];
        boolean ans=true;
        V returnvalue=dict.get(0).value;
        for (int i = 0; i < cv.size(); i++) { 
            if((cv.get(i)).key.equals(key)){
                DictionaryEntry<K,V> object=(cv.get(i).dictEntry);
                returnvalue=object.value;
                object.value=value;
                ans=false;
            } 
        }
        if(ans){
            throw new KeyNotFoundException();
        }
        return returnvalue;
    }

    public V get(K key) throws NullKeyException, KeyNotFoundException {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key in O(1)
         */

        if(key==null){
            throw new NullKeyException();
        }
        int insertionindex=hash(key);
        if(hashTable[insertionindex]==null){
            throw new KeyNotFoundException();
        }
        LinkedList<HashTableEntry<K, V>> cv =hashTable[insertionindex];
        boolean ans=true;
        V returnvalue=dict.get(0).value;
        for (int i = 0; i < cv.size(); i++) { 
            if((cv.get(i)).key.equals(key)){
                DictionaryEntry<K,V> object=(cv.get(i).dictEntry);
                returnvalue=object.value;
                ans=false;
            } 
        }
        if(ans){
            throw new KeyNotFoundException();
        }
        return returnvalue;
    }
    public Boolean exist(K key) throws NullKeyException {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key in O(1)
         */
        if(key==null){
            throw new NullKeyException();
        }
        int insertionindex=hash(key);
        if(hashTable[insertionindex]==null){
            return false;
        }
        LinkedList<HashTableEntry<K, V>> cv =hashTable[insertionindex];
        for (int i = 0; i < cv.size(); i++) { 
            if((cv.get(i)).key.equals(key)){
                return true;
            } 
        }
        return false;
    }

    public int size() {
        /*
         * To be filled in by the student
         * Return: Returns the size of the Dictionary in O(1)
         */
        return sizeofdictionary;
    }

    @SuppressWarnings("unchecked")
    public K[] keys(Class<K> cls) {
        /*
         * To be filled in by the student
         * Return: Returns array of keys stored in dictionary.
         */
        if(dict==null){
            return null;
        }
        K[] ans=(K[]) Array.newInstance(cls, dict.size());
        for(int i=0;i<dict.size();i++){
            ans[i]=dict.get(i).key;
        }
        return ans;
    }

    @SuppressWarnings("unchecked")
    public V[] values(Class<V> cls) {
        /*
         * To be filled in by the student
         * Return: Returns array of keys stored in dictionary.
         */
        if(dict==null){
            return null;
        }
        V[] ans=(V[]) Array.newInstance(cls, dict.size());
        for(int i=0;i<dict.size();i++){
            ans[i]=dict.get(i).value;
        }
        return ans;
    }

    public int hash(K key) {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the hash of the argumented key using the Polynomial Rolling
         * Hash Function.
         */
        if(key.equals("")){
            return 0;
        }
        String s=key+"";
        int hashcode=0;
        int currentCoeff=1;
        for(int i=0;i<s.length();i++){
            int yo=s.charAt(i);
            hashcode=hashcode+(yo+1)*currentCoeff;  
            hashcode=hashcode%bucketsize;
            currentCoeff=currentCoeff*131;
            currentCoeff=currentCoeff%bucketsize;
        }
        return hashcode%bucketsize;
    }
    /*public static void main (String[] args)throws NullKeyException, KeyAlreadyExistException, KeyNotFoundException {
        COL106Dictionary<String,Integer> c=new COL106Dictionary<String,Integer>(1765);
        c.insert("yo",78);
        c.insert("ta",69);
        c.insert("ghtg",109);
        c.update("ta",67);
        //c.insert("ghtg",19);
         System.out.println(c.delete("ta")); 
         System.out.println(c.exist("ta"));
         System.out.println(c.exist("ghtg"));
        //c.update("ta",456);
        // System.out.println(c.size());
        // for (int i = 0; i < c.dict.size(); i++){
        //     System.out.print(c.dict.get(i).key + " ");
        // }
        String arr[]=c.keys(String.class);
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
    }*/
}
