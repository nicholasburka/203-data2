public class Concatenation<T extends Comparable<T>> implements Sequence<T> {
	Sequence<T> left;
	Sequence<T> right;

	Concatenation(Sequence<T> l, Sequence<T> r) {
		this.left = l;
		this.right = r;
	}

	public Boolean isEmptyHuh() {
		return this.left.isEmptyHuh() && this.right.isEmptyHuh();
	}

	public T here() {
		if (!this.left.isEmptyHuh()) {
			return this.left.here();
		} else {
			return this.right.here();
		}
	}

	public Sequence<T> next() {
		if (!this.left.isEmptyHuh()) {
			return new Concatenation<T>(this.left.next(), this.right);
		} else {
			return this.right.next();
		}
	}

	public Sequence<T> seq() {
		return this;
	}
}