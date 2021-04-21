
import java.util.HashMap;
import java.util.TreeMap;

public class TreeMapPriorityQueue<Priority extends Comparable<Priority>, Key extends Comparable<Key>> {
	
	private class Node implements Comparable<Node>{
		Priority m_p;
		Key m_k;
		
		Node(Key k, Priority p){
			m_p = p;
			m_k = k;
		}
		
		@Override
		public int compareTo(Node other){
			int pComp = m_p.compareTo(other.m_p);
			if (pComp == 0) {
				return m_k.compareTo(other.m_k);
			}
			else return pComp;
		}
	}
	
	TreeMap<Node,Key> m_tree = new TreeMap<Node,Key>();
	HashMap<Key,Node> m_map = new HashMap<Key,Node>();
	
	
	TreeMapPriorityQueue(){
		
	}
	
	void add(Key k, Priority p){
		Node n = new Node(k, p);
		m_tree.put(n, k);
		m_map.put(k, n);
	}
	
	void decreasePriority(Key k, Priority p){
		Node n = m_map.get(k);
		if (n == null) throw new IllegalArgumentException("Bad key.");
		if (n.m_p.compareTo(p) > 0){
			m_tree.remove(n);
			m_map.remove(k);
			n.m_p = p;
			m_tree.put(n, k);
			m_map.put(k, n);
		}
	}
	
	Key extractMin(){
		Node n = m_tree.firstKey();
		m_tree.remove(n);
		Key k = n.m_k;
		m_map.remove(k);
		return k;
	}
	
	public Priority lookup(Key k){
		Node n = m_map.get(k);
		if (n != null)
			return n.m_p;
		else return null;
	}
	
	public boolean isEmpty(){
		return m_tree.isEmpty();
	}
	
	
	public static void main(String[] arg){
		TreeMapPriorityQueue<Integer, String> pq = new TreeMapPriorityQueue<Integer, String>();
		
		pq.add("Seth", 36);
		pq.add("Jane", 42);
		pq.add("Valerie", 36);
		pq.decreasePriority("Valerie", 24);
		pq.decreasePriority("Jane", 18);


		while (!pq.isEmpty()){
			System.out.println(pq.extractMin());
		}
		
	}

}
