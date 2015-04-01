public class Branch<T extends Comparable<T>> implements MultiSet<T> {
	private MultiSet<T> left;
	private T key;
	private int multiplicity;
	private MultiSet<T> right;

	public Branch (MultiSet<T> left, T key, int multiplicity, MultiSet<T> right) {
		this.left = left;
		this.key = key;
		this.multiplicity = multiplicity;
		this.right = right;
	}

	@Override public String toString() {
		return "new Branch(" + this.left + "," + this.key + "," + this.multiplicity + "," + this.right + ")";
	}

	public int cardinality() {
		return multiplicity + this.left.cardinality() + this.right.cardinality();
	}

	public Boolean isEmptyHuh() {
		return false;
	}

	public Boolean member(T t) {
		int comp = t.compareTo(this.key);
		if (comp == 0) {
			return true;
		} else if (comp > 0) {
			return this.right.member(t);
		} else {//if (t < this.key)
			return this.left.member(t);
		}
	}

	public int multiplicity(T t) {
		int comp = t.compareTo(this.key);
		if (comp == 0) {
			return this.multiplicity;
		} else if (comp > 0) {
			return this.right.multiplicity(t);
		} else {
			return this.left.multiplicity(t);
		}
	}
	
	public MultiSet<T> add (T t) {
		int comp = t.compareTo(this.key);
		if (comp == 0) {
			return new Branch<T>(this.left, this.key, this.multiplicity + 1, this.right);
		} else if (comp > 0) {
			return new Branch<T>(this.left, this.key, this.multiplicity, this.right.add(t));
		} else {
			return new Branch<T>(this.left.add(t), this.key, this.multiplicity, this.right);
		}
	}

	public MultiSet<T> add (T t, int num) {
		int comp = t.compareTo(this.key);
		if (comp == 0) {
			return new Branch<T>(this.left, this.key, this.multiplicity + num, this.right);
		} else if (comp > 0) {
			return new Branch<T>(this.left, this.key, this.multiplicity, this.right.add(t));
		} else {
			return new Branch<T>(this.left.add(t), this.key, this.multiplicity, this.right);
		}
	}

	/*public MultiSet successor*/

	public MultiSet<T> remove (T t) {
		int comp = t.compareTo(this.key);
		if (comp == 0) {
			if (this.multiplicity > 1) {
				return new Branch<T> (this.left, this.key, this.multiplicity - 1, this.right);
			} else return this.left.union(this.right);
		} else if (comp > 0) {
			return new Branch<T> (this.left, this.key, this.multiplicity, this.right.remove(t));
		} else {
			return new Branch<T> (this.left.remove(t), this.key, this.multiplicity, this.right.remove(t));
		}
	}

	public MultiSet<T> remove (T t, int num) {
		int comp = t.compareTo(this.key);
		if (comp == 0) {
			if (this.multiplicity > num) {
				return new Branch<T> (this.left, this.key, this.multiplicity - num, this.right);
			} else return this.left.union(this.right);
		} else if (comp > 0) {
			return new Branch<T> (this.left, this.key, this.multiplicity, this.right.remove(t));
		} else {
			return new Branch<T> (this.left.remove(t), this.key, this.multiplicity, this.right.remove(t));
		}
	}

	public MultiSet<T> union (MultiSet<T> s) {
		return s.union(this.left).union(this.right).add(this.key, this.multiplicity);
	}


	public MultiSet<T> inter (MultiSet<T> s) {
		if (s.member(this.key)) {
			return new Branch<T> (this.left.inter(s), 
								this.key, 
								Math.min(this.multiplicity, s.multiplicity(this.key)), 
								this.right.inter(s));
		} else {
			return this.remove(this.key).inter(s);
		}
	}

	public MultiSet<T> diff (MultiSet<T> s) {
		if (s.member(this.key)) {
			//remove s.multiplicity number of keys if it's in s
			return this.remove(this.key, s.multiplicity(this.key)).diff(s);
		} else {
			//check both branches if it's not
			return new Branch<T> (this.left.diff(s), this.key, this.multiplicity, this.right.diff(s));
		}
	}

	public Boolean equal (MultiSet<T> s) {
		return this.subset(s) && s.subset(this);
	}

	public Boolean subset (MultiSet<T> s) {
		return (s.multiplicity(this.key) == this.multiplicity)
			&& this.left.subset(s)
			&& this.right.subset(s);
	}

	public T max () {
		if (this.right.isEmptyHuh()) {
			return this.key;
		} else {
			return this.right.max();
		}
	}



}