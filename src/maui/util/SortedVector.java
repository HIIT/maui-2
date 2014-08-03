/*
 *    SortedVector.java
 *    Copyright (C) 2007 David Milne, d.n.milne@gmail.com
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package maui.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.Vector;

/**
 * 
 * This class implements the Set interface, backed by a simple Vector. 
 * <p>
 * Provided the add methods are used correctly, this class guarantees that the set 
 * will be in ascending element order, according to the natural order  of the elements (see Comparable).
 * It is designed to take advantage of situations where quick lookups are required 
 * (making unsorted Vectors undesirable) but where most of the values being inserted into the set are 
 * known to be in order (making the time and memory overhead of a TreeSet unnecessary).
 * <p>
 * This implementation provides guaranteed log(n) time cost for the basic operations (add, remove and contains). 
 * If the value to be inserted is known to be greater than any other value in the set, then adds are obviously performed in constant time.
 * 
 * @param <T> The type of item to be stored and sorted. Must be comparable
 * 
 * @author David Milne
 */
public class SortedVector<T> implements SortedSet<T> {
	
	Vector<T> vector ;
	
	/**
	 * Initializes a new, empty SortedVector.
	 */
	public SortedVector() {
		vector = new Vector<T>() ;
	}
	
	public Comparator<? super T> comparator() {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public SortedSet<T> subSet(T fromElement, T toElement) {
		int fromIndex = expectedIndexOf(fromElement) ;
		int toIndex = expectedIndexOf(toElement) ;
		
		return (SortedVector<T>) vector.subList(fromIndex, toIndex) ;
	}
	
	@SuppressWarnings("unchecked")
	public SortedSet<T> headSet(T toElement) {
		int toIndex = expectedIndexOf(toElement) ;
		
		return (SortedVector<T>) vector.subList(0, toIndex) ;
	}
	
	@SuppressWarnings("unchecked")
	public SortedSet<T> tailSet(T fromElement) {
		int fromIndex = expectedIndexOf(fromElement) ;
		
		return (SortedVector<T>) vector.subList(fromIndex, size()) ;
	}
	
	public T first() {
		return vector.firstElement() ;
	}
	
	public T last() {
		return vector.lastElement() ;
	}
	
	public int size() {
		return vector.size() ;
	}
	
	public boolean isEmpty() {
		return vector.isEmpty() ;
	}

	public boolean contains(Object o) {
		
		int index = expectedIndexOf(o) ;
		if (index < 0 || index >= vector.size())
			return false ;
		else
			return vector.elementAt(index).equals(o) ;
	}
	
	public Iterator<T> iterator() {
		return vector.iterator() ;
	}
	
	public Object[] toArray() {
		return vector.toArray() ;
	}
	
	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a) {
		return vector.toArray(a) ;
	}
	
	/**
	 * This will always result in an UnsupportedOperationException. Use add(Object, boolean) instead, so we know if sorting is required.
	 * 
	 * @param element	element to be added to this set.
	 */
	public boolean add(T element) {
		throw new UnsupportedOperationException("Use add(Object, boolean) instead, so we know if sorting is required") ; 
	}
	
	/**
	 * Adds the specified element to this set if it is not already present.
	 * If ordering of values being inserted is assumed (the value being inserted is known to be 
	 * greater than any item already in the set) then this is done in constant time. Otherwise a 
	 * log(n) lookup is required. 
	 * 
	 * @param element	element to be added to this set.
	 * @param assumeOrdering	true if the element being inserted is known to be greater than items already in list and fast insert is desired, otherwise false 
	 * @return true if the set did not already contain the specified element.
	 */
	@SuppressWarnings("unchecked")
	public boolean add(T element, boolean assumeOrdering) {
		try {
			//System.out.println("adding " + element + " to " + this) ;
			
			int size = vector.size() ;
			
			if (size==0) {
				vector.add(element) ;
				return true ;
			}
			
			if (assumeOrdering) {
				int compare = ((Comparable<T>)last()).compareTo(element) ;
				
				if (compare == 0)
					return false ;
				
				if (compare > 0)
					throw new IllegalArgumentException("Ordering assumption is invalid") ;
				
				vector.add(element) ;
				return true ;
			} else {
				int index = expectedIndexOf(element) ;
				
				if (index < size) {
					int compare = ((Comparable<T>)vector.elementAt(index)).compareTo(element) ;
					
					if (compare==0)
						return false ;
				}
				
				vector.add(index, element) ;
				return true ;
			}
		
		} finally {
		
			//System.out.println("added " + element + " to " + this) ;
			
	
		}
	}
	
	/**
	 * Removes the element at the specified index
	 * 
	 * @param index the index of the element to remove.
	 */
	public void removeElementAt(Integer index) {
		vector.remove(index) ;
	}
	
	public boolean remove(Object o) {
		int index = expectedIndexOf(o) ;
		
		if (vector.elementAt(index).equals(o)) {
			vector.remove(index) ;
			return true ;
		} else
			return false ;
	}
	
	public boolean containsAll(Collection<?> c) {
		for(Object o: c) {
			if (!contains(o)) 
				return false ;
		}
		
		return true ;
	}
	
	public boolean addAll(Collection<? extends T> c) {
		throw new UnsupportedOperationException("Use add(Object, boolean) instead") ; 
	}
	
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException() ; 
	}
	
	public boolean removeAll(Collection<?> c) {
		boolean changed = false ;
		
		for(Object o: c) {
			if (remove(o))
				changed = true ;
		}
		
		return changed ;
	}
	
	public void clear() {
		vector.clear() ;
	}
	
	
	/**
	 * Returns the component at the specified index.
	 * 
	 * @param index an index into this vector.
	 * @return the component at the specified index.
	 */
	public T elementAt(int index) {
		return vector.elementAt(index) ;
	}
	
	@SuppressWarnings("unchecked")
	private int expectedIndexOf(Object o) {
		Comparable<T> c = (Comparable<T>) o ;
		
		int low = 0 ;
		int high = size() - 1 ;
		
		while (low <= high) {
			int mid = (low + high) / 2 ;
			
			int compare = c.compareTo(vector.elementAt(mid)) ;
			
			if (compare == 0)
				return mid ;
			
			if (compare < 0){
				if (mid == low)
					return low ;
				else
					high = mid -1 ;
			} else {
				if (mid == high)
					return high + 1 ;
				else
					low = mid + 1 ;
			}
			
		}
		
		return high ; 
		// not found
	}
	
	public String toString() {
		String str = "" ;
		
		for (Object o:vector) {
			str = str + o + ", " ;
		}
		
		if (str.length() > 0)
			return "{" + str.substring(0, str.length() - 2) + "}" ;
		else
			return "{}" ;
	}
}
