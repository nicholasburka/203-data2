public class Leaf implements FiniteSet {
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

	public Boolean member(int x) {
		return false;
	}

	public FiniteSet add(int x) {
		return new Branch (this, x, this);
	}

	public FiniteSet remove(int x) {
		return this;
	}

	public FiniteSet union(FiniteSet s) {
		return s;
	}

	public FiniteSet inter(FiniteSet s) {
		return this;
	}

	public FiniteSet diff(FiniteSet s) {
		return this;
	}

	public Boolean equal(FiniteSet s) {
		return s.isEmptyHuh();
	}

	public Boolean subset(FiniteSet s) {
		return true;
	}

	public int max() {
		throw new RuntimeException();
	}
}