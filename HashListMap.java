//Class skeleton written by instructor, class methods written by Milap Naik

//Note to self: rename variables to something more understandable


public class HashListMap<K, V> implements Map<K, V>
{
   public HashListEntry<K,V>[] bucket;   // The hash table (buckets)
   public int capacity;                  // capacity == bucket.length
   public int NItems;                    // NItems = # entries in hash table

   public int MAD_p;
   public int MAD_a;
   public int MAD_b;

   /* ===============================================================
      General Constructor:  initialize prime number p
                            Create an array (bucket) of given size
      =============================================================== */
   @SuppressWarnings("unchecked")
public HashListMap(int p, int MapArraySize)
   {
      capacity = MapArraySize;
      bucket = new HashListEntry[ capacity ]; // Java can't create gen arrays
      NItems = 0;

      java.util.Random rand = new java.util.Random(1234);

      MAD_p = p;                              // Prime number in MAD
      MAD_a = rand.nextInt(MAD_p-1) + 1;      // Multiplier in MAD
      MAD_b = rand.nextInt(MAD_p);            // Shift amount in MAD
   }

   /* ===============================================================
      Common Constructor:  pick the default prime number 109345121
                           Create an array (bucket) of given size
      =============================================================== */
   public HashListMap(int MapArraySize)
   {
      this( 109345121, MapArraySize );   // Calls general constructor
   }

   public HashListMap()
   {
      this( 109345121, 1000 );   // Default array size = 1000
   }


   public int size()
   {
      return NItems;
   }

   public boolean isEmpty()
   {
      return NItems==0;
   }

   /* ===============================================================
      Hash function:

          1. uses the Hash Code inside the K class
          2. uses the MAD compression function

                hash index = [ (a*HashCode + b) % p ] % N
      =============================================================== */
   public int hashValue(K key)
   {
     int x = key.hashCode();      // K has a built-in hashCode() !!!

     return (int) ((Math.abs(x*MAD_a + MAD_b) % MAD_p) % capacity);
   }

   // ****************************************************************
   // Todo: implement these 3 methods
   //
   //     1. insert()
   //     2. delete()
   //     3. findEntry()
   //
   //  See project description for details
   // ****************************************************************


   // ****************************************************************
   // insert(bucketID, e): insert entry e in bucket[bucketID]
   //
   // Write this method
   // ****************************************************************
   public void insert(int bucketID, HashListEntry<K, V> e)
   {
	   NItems = NItems + 1;
	   HashListEntry<K, V> l = e;
	   l.value = e.value;
	   l.key = e.key;
	   if (bucket[bucketID] != null){
		   l.next = bucket[bucketID];
		   bucket[bucketID].prev = e;
	   }
	   bucket[bucketID] = l;
	}
	  
   // *****************************************************************
   // delete(bucketID, e): delete entry e from bucket[bucketID]
   //
   // Write this method
   // *****************************************************************
   public void delete(int bucketID, HashListEntry<K, V> p)
   {
	   NItems = NItems - 1;
	  /* if (p != null){ 
		   p.next = bucket[bucketID].prev;
		   p.prev = bucket[bucketID].next;
		   p = null;
	   }*/
	   
	   HashListEntry<K, V> new1 = bucket[bucketID];
	   if (new1 == p){
		   if (p.next == null){
			   bucket[bucketID] = null;
		   }
	   else {
		   bucket[bucketID] = p.next;
		   p.next.prev = null;
	   }
	   return;
	   }
	   if (p.next == null) {
		   p.prev.next = null;
		   return;
	   }
   }

   // *********************************************************************
   // findEntry(key): find entry for given key
   //
   // Return value:
   //
   //       The reference to entry that contains the given key
   //       If key is NOT found, return null
   //
   // Write this method
   // ********************************************************************* 
    public HashListEntry<K,V> findEntry(K key)
    {
    	int bucketID = hashValue(key);
    	HashListEntry<K, V> new2 = bucket[bucketID];
		while (new2 != null){
    		if (new2.key.equals(key)){
    			return new2;
    		}
		    new2 = new2.next;
		}
    	return null;
    }

    /* ===========================================================
       get(), put() and remove() are very easily implemented
       using 

		findEntry()
		insert()
		delete()
       =========================================================== */

    public V get(K k)
    {
       HashListEntry<K, V> e;
 
       e = findEntry(k);  // Find entry for the key k
 
       if ( e != null )
       {
          return e.getValue();       // return value
       }
       else
       {
          return null;               // Key not found
       }
    }
 
 
   public V put (K k, V v)
   {
       HashListEntry<K, V> e;
 
       e = findEntry(k);  // Find entry for the key k
 
       if ( e != null )
       {
          V oldValue = e.setValue(v);// set new value
          return ( oldValue );  // Return old value
       }
       else
       {
          int bucketID = hashValue( k );
 
          insert( bucketID, new HashListEntry<K,V>(k, v) );
 
          return null;
       }
    }
 
 
    public V remove(K k)
    {
       HashListEntry<K, V> e = findEntry(k);  // Find entry for the key k
 
       if ( e != null )
       {
          V oldValue = e.getValue();
 
          int bucketID = hashValue( k );
          delete ( bucketID, e );
 
          return oldValue;
       }
       else
       {
          return null;               // Key not found
       }
    }
 
    public String toString()
    {
        boolean more;
 
        String output = "{";
  
        for (int i = 0; i < bucket.length; i++)
        {
           HashListEntry<K,V> e;
  
           e = bucket[i];
  
           if ( e != null && e.next != null )
           {
 	     more = true;
              output += "[";
           }
 	  else
 	     more = false;
 
           while ((e != null) )
           { 
              output = output + e + " ";
              e = e.next;
           }
 
 	  if ( more )
              output += "] ";
        }
  
        output += "} (" + NItems + " entries)";
        return output;
    }
}