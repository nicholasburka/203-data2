public class Branch<T extends Comparable<T>> implements MultiSet<T> {
	public MultiSet<T> left;
	public T key;
	public int multiplicity;
	public MultiSet<T> right;
	public int height;

	public Branch (MultiSet<T> left, T key, int multiplicity, MultiSet<T> right) {
		this.left = left;
		this.key = key;
		this.multiplicity = multiplicity;
		this.right = right;
		this.height = 1 + Math.max(this.left.height(), this.right.height());
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
		if (comp == 0 && this.multiplicity > 0) {
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
		Branch<T> newThis;
		if (comp == 0) {
			newThis =  new Branch<T>(this.left, this.key, this.multiplicity + 1, this.right);
		} else if (comp > 0) {
			newThis =  new Branch<T>(this.left, this.key, this.multiplicity, this.right.add(t));
		} else {
			newThis = new Branch<T>(this.left.add(t), this.key, this.multiplicity, this.right);
		}
		return newThis.avlCheckInsert();
	}

	public MultiSet<T> add (T t, int num) {
		int comp = t.compareTo(this.key);
		Branch<T> newThis;
		if (comp == 0) {
			newThis = new Branch<T>(this.left, this.key, this.multiplicity + num, this.right);
		} else if (comp > 0) {
			newThis = new Branch<T>(this.left, this.key, this.multiplicity, this.right.add(t));
		} else {
			newThis = new Branch<T>(this.left.add(t), this.key, this.multiplicity, this.right);
		}
		return newThis.avlCheckInsert();
	}

	/*public MultiSet successor*/

	public MultiSet<T> remove (T t) {
		int comp = t.compareTo(this.key);
		if (comp == 0) {
			if (this.multiplicity > 1) {
				return new Branch<T> (this.left, this.key, this.multiplicity - 1, this.right);
			} else {
				return new Branch<T> (this.left, this.key, 0, this.right);
			}
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
			} else {
				return new Branch<T> (this.left, this.key, 0, this.right);//this.left.union(this.right);
			}
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
/*
	public Branch<T> min () {
		if (this.left.isEmptyHuh()) {
			return this;
		} else {
			return this.left.min();
		}
	}*/

	//TIME FOR SEQUENCES!!!
	public Sequence<T> seq() {
		return new BranchSequence<T>(new Concatenation<T>(this.left.seq(), this.right.seq()), this.key, this.multiplicity);
	}

	public T here() {
		return this.seq().here();
	}

	public Sequence<T> next() {
		return this.seq().next();
	}

	//AVL Trees
	public int height() {
		return this.height;
	}

	public int height(Boolean on) {
		int height = 1 + Math.max(this.left.height(true), this.right.height(true));
		this.height = height;
		return height;
	}

	public int loadFactor() {
		return left.height() - right.height();
	}

	public Branch<T> avlCheckInsert() {
		Branch<T> newThis = this;
		//left case
		if (this.loadFactor() > 1) {
			if (this.left.loadFactor() == -1) {
				Branch<T> theLeft = ((Branch<T>) this.left);
				newThis = new Branch<T>(theLeft.rotateLeft(), this.key, this.multiplicity, this.right);
			} 
			newThis = newThis.rotateRight();

		//right case
		} else if (this.loadFactor() < -1) {
			if (this.right.loadFactor() == 1) {
				Branch<T> theRight = ((Branch<T>) this.right);
				newThis = new Branch<T>(this.left, this.key, this.multiplicity, theRight.rotateRight());
			}
			newThis = newThis.rotateLeft();
		}

		if (newThis.loadFactor() > 1 || newThis.loadFactor() < -1) {
			System.out.println(newThis.loadFactor());
			System.out.println(newThis);
		} 
		return newThis;
	}

	private Branch<T> rotateRight() {
		Branch<T> theLeft = (Branch<T>) this.left;
		Branch<T> newParent = new Branch<T>(theLeft.left, 
											theLeft.key, 
											theLeft.multiplicity,
											 new Leaf<T>());
		Branch<T> newThis = new Branch<T>(theLeft.right,
										this.key,
										this.multiplicity,
										this.right);
		newParent.right = newThis;
		return newParent;
	}

	private Branch<T> rotateLeft() {
		Branch<T> theRight = (Branch<T>) this.right;
		Branch<T> newParent = new Branch<T>(new Leaf<T>(), 
											theRight.key, 
											theRight.multiplicity, 
											theRight.right);
		Branch<T> newThis = new Branch<T>(this.left, 
										this.key, 
										this.multiplicity, 
										theRight.left);
		newParent.left = newThis;	
		return newParent;
	}

/*
	private Branch<T> predecessor() {
		return this.left.max();
	}

	private Branch<T> sucessor() {
		return this.right.min();
	}*/

}