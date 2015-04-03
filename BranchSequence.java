public class BranchSequence<T extends Comparable<T>> implements Sequence<T> {
	Concatenation<T> c;
	T key;
	int multiplicity;

	BranchSequence(Concatenation<T> c, T key, int multiplicity) {
		this.c = c;
		this.key = key;
		this.multiplicity = multiplicity;
	}

	public Boolean isEmptyHuh() {
		return false;
	}

	public T here() {
		return this.key;
	}

	public Sequence<T> next() {
		if (this.multiplicity > 1) {
			return new BranchSequence<T>(this.c, this.key, this.multiplicity - 1);
		} else {
			return c;
		}
	}

	public Sequence<T> seq() {
		return this;
	}
}

//Need a Cat that
//If a Cat tries to sequence a Branch, then the Branches turn into BranchSequence