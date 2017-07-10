package org.sagy.dp;

/**
 * 
 *  LRU Cache
 * 
 *  Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and set.
 *  
 *  get(key) - Get the value of the key if the key exists in the cache, otherwise return null.
 *  set(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
 *  
 *  Analysis
 *  
 *  The key to solve this problem is using a double linked list which enables us to quickly move nodes.
 *  
 *  LRU-Cache
 *  
 *  The LRU cache is a hash table of keys and double linked nodes. The hash table makes the time of get() to be O(1). The list of double linked nodes make the nodes adding/removal operations O(1).
 *  @author coolsagy
 */

import java.util.HashMap;
import java.util.Map;

class DLNode<K, V>{
	DLNode<K, V> prev, next;
	K key;
	V data;	
	DLNode(K key, V data){
		this.key=key;
		this.data=data;
	}

	@Override
	public String toString() {
		return "["+key+":"+data+"]";
	}
}

public class LRUCache<K, V> {
	Map<K, DLNode<K, V>> map = new HashMap<>();
	DLNode<K, V> head=null, tail=null;
	int capacity=5;

	public V get(K key){
		V res = null;
		if(map.containsKey(key)){
			DLNode<K, V> n = map.get(key);
			remove(n);
			setHeadnTail(n);
			res = n.data;
		}
		currentStatus();
		return res;
	}

	private void remove(DLNode<K, V> n) {
		if(n.prev!=null){
			n.prev.next = n.next;
		}
		else{
			head=n.next;
		}
		if(n.next!=null){
			n.next.prev = n.prev;
		}
		else{
			tail = n.prev;
		}
	}


	private void setHeadnTail(DLNode<K, V> n) {
		n.prev=n.next=null;
		if(head!=null){
			n.next = head;
			head.prev = n;
		}
		head=n;
		if(tail==null)
			tail=n;
	}

	public void set(K key, V data){
		if(map.containsKey(key)){
			DLNode<K, V> n = map.get(key);
			n.data = data;
			remove(n);
			setHeadnTail(n);
		}
		else{
			if(map.size()>=capacity){
				map.remove(tail);
				remove(tail);
			}
			DLNode<K, V> n = new DLNode<>(key, data);
			map.put(key, n);
			setHeadnTail(n);
		}
		currentStatus();
	}

	private void currentStatus(){
		DLNode<K, V> n = head;
		while(n!=null){
			System.out.print(n+" ");
			n = n.next;
		}
		System.out.println();
		//System.out.println("\nH:"+head+" T:"+tail);
	}


	public static void main(String[] args) {
		LRUCache<Integer, Integer> cache = new LRUCache<>();
		cache.set(1, 234);cache.set(4, 764);cache.set(2, 204);cache.set(9, 534);
		cache.get(2);
		System.out.println(cache.get(67));
		cache.set(6, 204);cache.set(7, 534);
		cache.set(5, 504);cache.set(8, 834);
		
		LRUCache<Integer, String> strCache = new LRUCache<>();
		strCache.set(1, "S234");strCache.set(4, "S764");strCache.set(2, "S204");strCache.set(9, "S734");
		strCache.get(2);
		strCache.set(6, "S204");strCache.set(7, "S534");
		strCache.set(5, "S504");strCache.set(8, "S834");
	}
}
