public class Leaf<T extends Comparable<T>> implements MultiSet<T> {
	Leaf () {}
	
	@Override public String toString() {
		return "new Leaf()";
	}

	public int cardinality() {
		return 0;
	}

	public Boolean isEmptyHuh() {
		return true;
	}

	public Boolean member(T t) {
		return false;
	}

	public int multiplicity(T t) {
		return 0;
	}

	public MultiSet<T> add(T t) {
		return new Branch<T> (this, t, 1, this);
	}

	public MultiSet<T> add(T t, int num) {
		return new Branch<T> (this, t, num, this);
	}

	public MultiSet<T> remove(T t) {
		return this;
	}

	public MultiSet<T> remove(T t, int num) {
		return this;
	}

	public MultiSet<T> union(MultiSet<T> s) {
		return s;
	}

	public MultiSet<T> inter(MultiSet<T> s) {
		return this;
	}

	public MultiSet<T> diff(MultiSet<T> s) {
		return this;
	}

	public Boolean equal(MultiSet<T> s) {
		return s.isEmptyHuh();
	}

	public Boolean subset(MultiSet<T> s) {
		return true;
	}

	public T max() {
		throw new RuntimeException();
	}
}