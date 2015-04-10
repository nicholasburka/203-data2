/**
* A pure implementation of an iterator -- an interface that allows safe access and
* operations on different members in a set without forcing the entire set to be accessed
*/
public interface Sequence<T extends Comparable<T>> extends Sequenced<T>{

	/**
	* Get the current element in the sequence of type T
	* @return    the current element in the sequence of type T
	*/
	public T here();

	/**
	* Determines whether the current sequence is empty (there are no elements left)
	* @return    a Boolean value answering the question "is the sequence empty?"
	*/
	public Boolean isEmptyHuh();
	/**
	* Returns a sequence with one less element from the current sequence (whichever
	* element was returned by this.here())
	*
	* @return    a sequence "advanced" by one element from the current sequence
	*/
	public Sequence<T> next();
}