/*
	PURE (and balanced) IMPLEMENTATION of a multiset
*/

public interface MultiSet<T extends Comparable<T>> extends Sequence<T> {

	/**
	* Returns the number of things in a multiset. Since multisets can have
	* more than one thing with the same value, cardinality counts the 
	* number of non-unique values -- if there are 2 "george" values, then
	* the cardinality is 2. It is the sum of the multiplicity of each unique
	* element in the multiset.
	*
	* @return   an int equal to the total number of elements in the multiset
	*/
	public int cardinality();

	/**
	* Returns a Boolean value true if the set is empty and false if it isn't
	*
	* @return   a Boolean value stating whether the set is empty
	*/
	public Boolean isEmptyHuh();

	/**
	* Determines whether or not a particular value appears at least
	* once in the multiset
	*
	* @param t  the T to look for and compare to
	* @return   a Boolean stating whether or not the element is in the multiset
	*/
	public Boolean member(T t);

	/**
	* Returns the number of times an element appears in the multiset. If there
	* are three 2's, then multiplicity(2) returns 3.
	*
	* @param t  the element of type T to obtain the count of
	* @return   the number of the element in the multiset
	*/
	public int multiplicity(T t);

	/**
	* Adds an element T to the multiset
	* 
	* @param t  the element of type T to add to the multiset
	* @return a new multiset with the element added
	*/
	public MultiSet<T> add(T t);

	/**
	* Adds some number of T's to the multiset
	* 
	* @param t  the element of type T to add to the multiset
	* @return a new multiset with the element added
	*/
	public MultiSet<T> add(T t, int num);

	/**
	* Removes an element T from the multiset if it's there. Only removes
	* one if there are multiple with the same value.
	*
	* @param t  the element of type T to remove from the multiset
	* @return the MultiSet with one less T of value t
	*/
	public MultiSet<T> remove(T t);

	/**
	* Removes some number of T's from the multiset if the t is present.
	*
	* @param t  the element of type T to remove from the multiset
	* @return the MultiSet with one less T of value t
	*/
	public MultiSet<T> remove(T t, int num);

	/**
	* Creates the union of two multisets -- the returned set has all the 
	* elements of both sets, including all repeats. In other words, the
	* multiplicity of each element in the new multiset is the sum of the
	* two multiplicities of that element, one from each set pre-union.
	*
	* @param s  the MultiSet to join this MultiSet with (of type T)
	* @return   a new MultiSet with the elements of both input MultiSets
	*/
	public MultiSet<T> union(MultiSet<T> s);

	/**
	* Creates the intersection of two multisets -- the returned set has all the 
	* elements that are only in both sets. In other words, the
	* multiplicity of each element in the new multiset is the minimum of the
	* two multiplicities of that element, one from each set pre-intersection.
	*
	* @param s  the MultiSet to intersect with this MultiSet
	* @return   a new MultiSet with the elements only in both input MultiSets
	*/
	public MultiSet<T> inter(MultiSet<T> s);

	/**
	* Creates the difference of two multisets, so that the returned multiset
	* has only those elements that are in this multiset but not the input set s.
	* The multiplicity of each element in the returned multiset is the difference
	* of the multiplicity of it in this multiset and in the input, where possible
	* resulting multiplicities are only 0 and the natural numbers. 
	*
	* @param s  the MultiSet to subtract from this MultiSet
	* @return   a new MultiSet with the elements of both input MultiSets
	*/
	public MultiSet<T> diff(MultiSet<T> s);

	/**
	* Determines whether or not two multisets are equal, meaning that the
	* multiplicity of each element in the multisets are equal.
	*
	* @param s  the MultiSet to compare to this multiset
	* @return   the Boolean value true if they are equal, and false otherwise
	*/
	public Boolean equal(MultiSet<T> s);

	/**
	* Determines whether this multiset is a subset of the input multiset.
	* This is true if each element in this multiset has multiplicities
	* less than or equal to the multiplicities of the elements in the input.
	* 
	*
	* @param s  the MultiSet to compare to this multiset (potential superset)
	* @return   the Boolean value true if this multiset is a subset, and false otherwise
	*/
	public Boolean subset(MultiSet<T> s);

	/**
	* Finds the height of a BST node in a MultiSet, which is the max distance of that
	* node from a Leaf [where distance is defined only in downward motion through children].
	* 
	* 
	* @return   int the height of the MultiSet node
	*/
	public int height();

	/**
	* Finds the height of a BST node in a MultiSet, which is the max distance of that
	* node from a Leaf [where distance is defined only in downward motion through children],
	* and recalculates rather than relying on precalculated values. This is UNUSED AND MUTATIVE.
	* 
	* @param   a Boolean flag that means nothing
	* @return   int the height of the MultiSet node
	*/
	public int height(Boolean on);

	/**
	* Finds the loadFactor of a BST node in a MultiSet, which is the difference between
	* the height of the left child and right child. In the AVL tree, the absolute value
	* should never be greater than 1
	*
	* @return    int the loadFactor of the MultiSet
	*/
	public int loadFactor();

	public T max();

}