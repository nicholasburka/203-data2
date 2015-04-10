import java.util.Random;

/*
	Test suite for data2
*/

public class Tester {
	private final int NUM_TIMES = 10;
	private final int UPPER_BOUND = 1000;
	private int numElements = 20;

	public Tester () {}

	public static void main (String[] args) {
		Tester t = new Tester();
		System.out.println("Beginning tests!");
		if (t.Test()) {
			System.out.println("\nAll tests passed!");
		} else {
			System.out.println("\nTest(s) failed.");
		}
	}


	//needs revisions on printouts
	public Boolean Test() {
		System.out.println("\nRunning each test " + this.NUM_TIMES + " times.");
		System.out.println("Generating keys between 0 (inclusive) and " + this.UPPER_BOUND + " (exclusive).");
		System.out.println("Generating sets with " + this.numElements + " elements.");

		Boolean passed = true;

		setUpTest();
		MakeSet m = new MakeSet();
		m.procedure(NUM_TIMES, UPPER_BOUND, numElements);
		printTest(m.testDescription, m.passed);
		passed &= m.passed;

		setUpTest();
		Cardinality c = new Cardinality();
		c.procedure(NUM_TIMES, UPPER_BOUND, numElements);
		printTest(c.testDescription, c.passed);
		passed &= c.passed;

		
		setUpTest();
		Empty e = new Empty();
		e.procedure(NUM_TIMES, UPPER_BOUND, numElements);
		printTest(e.testDescription, e.passed);
		passed &= e.passed;

		
		setUpTest();
		Remove r = new Remove();
		r.procedure(NUM_TIMES, UPPER_BOUND, numElements);
		printTest(r.testDescription, r.passed);
		passed &= r.passed;

		
		setUpTest();
		Union u = new Union();
		u.procedure(NUM_TIMES, UPPER_BOUND, numElements);
		printTest(u.testDescription, u.passed);
		passed &= u.passed;

		/*
		setUpTest();
		Inter i = new Inter();
		i.procedure(NUM_TIMES, UPPER_BOUND, numElements);
		printTest(i.testDescription, i.passed);
		passed &= i.passed;

		setUpTest();
		Diff d = new Diff();
		d.procedure(NUM_TIMES, UPPER_BOUND, numElements);
		printTest(d.testDescription, d.passed);
		passed &= d.passed;

		setUpTest();
		Equal eq = new Equal();
		eq.procedure(NUM_TIMES, UPPER_BOUND, numElements);
		printTest(eq.testDescription, eq.passed);
		passed &= eq.passed;

		setUpTest();
		Subset s = new Subset();
		s.procedure(NUM_TIMES, UPPER_BOUND, numElements);
		printTest(s.testDescription, s.passed);
		passed &= s.passed;
*/
		return passed;
	}

	public void setUpTest() {
		System.out.println("");
	}

	public void printTest(String str, Boolean boole) {
		System.out.println(str);
		System.out.println("Test result was " + boole);
	}
}

abstract class Test {
	public static String testDescription;
	public Boolean passed;

	//should update passed at end of procedure
	public abstract Boolean procedure(int numTimes, int upperBound, int numElements);

	//generates a MultiSet with numElements number of elements (not necessarily unique)
	public MultiSet<Integer> generateMultiSet(int upperBound, int numElements) {
		if (upperBound < numElements) {
			throw new RuntimeException("Need a larger upperBound to fit desired numElements");
		} else {
			MultiSet<Integer> set = new Leaf<Integer>();
			Random rand = new Random();
			int next = 0;
			for (int i = 0; i < numElements; i++) {
				next = rand.nextInt(upperBound);


				//REMEMBER TO ASSIGN SET TO RESULT OF EXPRESSION, because there's no mutation!
				set = set.add(next);
			}
			return set;
		}
	}

	//generates a MultiSet with numElements number of UNIQUE elements
	public MultiSet<Integer> generateMultiSetUnique(int upperBound, int numElements) {
		if (upperBound < numElements) {
			throw new RuntimeException("Need a larger upperBound to fit desired numElements");
		} else {
			MultiSet<Integer> set = new Leaf<Integer>();
			Random rand = new Random();
			int next = 0;
			for (int i = 0; i < numElements; i++) {
				next = rand.nextInt(upperBound);

				
				//ensures uniqueness of added value
				while (set.member(next)) {
					next = rand.nextInt(upperBound);
				}

				//REMEMBER TO ASSIGN SET TO RESULT OF EXPRESSION, because there's no mutation!
				set = set.add(next);
			}
			return set;
		}
	}
}

class MakeSet extends Test {
	public String testDescription = "Check that a MultiSet of Integers contains all the elements added to it";

	MakeSet() {}

	public Boolean procedure(int numTimes, int upperBound, int numElements) {
		MultiSet<Integer> set = new Leaf<Integer>();
		int[] nums = new int[numElements];
		Random rand = new Random();
		Boolean passed = true;
		for (int h = 0; h < numTimes; h++) {
			for (int i = 0; i < numElements; i++) {
				nums[i] = rand.nextInt(upperBound);
			}
			for (int j = 0; j < nums.length; j++) {
				set = set.add(nums[j]);
			}
			for (int e = 0; e < nums.length; e++) {
				passed &= set.member(nums[e]);
				if (!passed) {
					System.out.println("Element " + nums[e] + " was not a member of the set");
					System.out.println(set);
				}
			}
		}
		this.passed = passed;
		return passed;
	}
}

class Cardinality extends Test {
	public String testDescription = "Check that a MultiSet of Integers has the number of elements added to it by sequencing through";

	Cardinality() {}

	public Boolean procedure(int numTimes, int upperBound, int numElements) {
		MultiSet<Integer> set;
		Sequence<Integer> seq;
		int count;
		for (int i = 0; i < numTimes; i++) {
			set = generateMultiSet(upperBound, numElements);
			this.passed = set.cardinality() == numElements;
			seq = set.seq();
			count = 0;
			while (!seq.isEmptyHuh()) {
				count++;
				seq = seq.next();
			}
			this.passed = count == set.cardinality();
			if (!passed) {
				System.out.println("Cardinality of set " + set + " was not " + numElements);
			}
			set = new Leaf<Integer>();
			this.passed = set.cardinality() == 0;
		}
		this.passed = passed;
		return this.passed;
	}
}

class Empty extends Test {
	public String testDescription = "Check that empty sets are empty, and non-empty sets are not";

	Empty() {}

	public Boolean procedure(int numTimes, int upperBound, int numElements) {
		MultiSet<Integer> set;
		for (int i = 0; i < numTimes; i++) {
			set = new Leaf<Integer>();
			this.passed = set.isEmptyHuh();
			if (!passed) {
				System.out.println("Empty set " + set + " was not empty");
				return false;
			}

			set = generateMultiSet(upperBound, 1);
			this.passed = !(set.isEmptyHuh());
			if (!passed) {
				System.out.println("Nonempty set " + set + " was empty");
				return false;
			}

			set = generateMultiSet(upperBound, numElements);
			this.passed = !(set.isEmptyHuh());

			if (!passed) {
				System.out.println("Nonempty set " + set + " was empty");
				return false;
			}
		}
		return this.passed;
	}
}

class Remove extends Test {
	public String testDescription = "Check that the set produced by removing the root is equal to the union of its children \n by sequencing through one set to see that each element is a member of the other set with identical multiplicity";

	Remove() {}

	public Boolean procedure(int numTimes, int upperBound, int numElements) {
		Branch<Integer> set;
		MultiSet<Integer> setMinusOne;
		Sequence<Integer> seq;
		MultiSet<Integer> setUnion;
		Integer el;
		this.passed = true;
		for (int i = 0; i < numTimes; i++) {
			set = (Branch<Integer>) generateMultiSet(upperBound, numElements);
			setMinusOne = set.remove(set.key, set.multiplicity);
			setUnion = set.left.union(set.right);
			seq = setMinusOne.seq();

			while (!seq.isEmptyHuh()) {
				el = seq.here();
				System.out.println(setUnion);
				System.out.println(setMinusOne);
				System.out.println(el);
				System.out.println(setUnion.member(el));
				System.out.println(setUnion.member(el));
				System.out.println(setMinusOne.multiplicity(el));
				this.passed = passed && (setUnion.member(el) && (setUnion.multiplicity(el) == setMinusOne.multiplicity(el)));
				if (!this.passed) {
					System.out.println("Element " + el + " did not return the correct result.");
					System.out.println(setUnion);
					System.out.println(setMinusOne);
					System.out.println(el);
					return this.passed;
				}
			}
		}
		return this.passed;
	}
}
/*
//this test is especially slow because of naive removal implementation
class Remove extends Test {
	public String testDescription = "Check that sets containing elements contain one less of each of those elements after removal";

	Remove() {}

	public Boolean procedure(int numTimes, int upperBound, int numElements) {
		MultiSet<Integer> set = new Leaf<Integer>();
		int[] nums = new int[numElements];

		Random rand = new Random();
		Boolean passed = true;
		for (int h = 0; h < numTimes; h++) {
			for (int i = 0; i < numElements; i++) {
				nums[i] = rand.nextInt(upperBound);
			}
			for (int j = 0; j < nums.length; j++) {
				set = set.add(nums[j]);
			}
			for (int q = 0; q < nums.length; q++) {

			} 
			//System.out.println("Starting removals...");
			for (int l = 0; l < nums.length; l++) {
				set = set.remove(nums[l]);
			}
			//System.out.println("Finished removals");
			for (int e = 0; e < nums.length; e++) {
				passed &= !(set.member(nums[e]));
				if (!passed) {
					System.out.println("Element " + nums[e] + " was a member of the set");
					System.out.println(set);
				}
			}
		}
		this.passed = passed;
		return passed;
	}
}
*/
class Union extends Test {
	public String testDescription = "Check that the union of two sets contains only elements that are in at least one of the sets, \nand that both sets are subsets of their union";

	Union() {}

	public Boolean procedure(int numTimes, int upperBound, int numElements) {
		MultiSet<Integer> set1 = new Leaf<Integer>();
		MultiSet<Integer> set2 = new Leaf<Integer>();
		MultiSet<Integer> unionSet = set1.union(set2);
		int[] nums1 = new int[numElements];
		int[] nums2 = new int[numElements];
		Random rand = new Random();
		Boolean passed = true;
		Integer currentMax;
		for (int i = 0; i < numTimes; i++) {
			set1 = new Leaf<Integer>();
			set2 = new Leaf<Integer>();
			unionSet = set1.union(set2);
			for (int j = 0; j < numElements; j++) {
				nums1[j] = rand.nextInt(upperBound);
				nums2[j] = rand.nextInt(upperBound);
			}
			for (int j = 0; j < numElements; j++) {
				set1 = set1.add(nums1[j]);
				set2 = set2.add(nums2[j]);
			}

			unionSet = set1.union(set2);
			passed &= set1.subset(unionSet);
			if (!passed) {
				System.out.println("set1 wasn't a subset of unionSet");
			}
			passed &= set2.subset(unionSet);
			for (int j = 0; j < numElements; j++) {
				passed &= unionSet.member(nums1[j]);
				passed &= unionSet.member(nums2[j]);
			}
			for (int h = 0; h < numElements; h++) {
				currentMax = unionSet.max();
				unionSet = unionSet.remove(currentMax);
				passed &= (set1.member(currentMax) || set2.member(currentMax)); 
			}
		}
		this.passed = passed;
		return passed;
	}
}
/*
class Inter extends Test {
	public String testDescription = "Check that the intersection of two sets contains only elements that are in both of the sets.";

	Inter() {}

	public Boolean procedure(int numTimes, int upperBound, int numElements) {
		MultiSet set1;
		MultiSet set2;
		MultiSet intersect;
		int currentMax;
		for (int i = 0; i < numTimes; i++) {
			set1 = generateMultiSet(upperBound, numElements);
			set2 = generateMultiSet(upperBound, numElements);
			intersect = set1.inter(set2);
			while (!intersect.isEmptyHuh()) {
				currentMax = intersect.max();
				intersect = intersect.remove(currentMax);
				passed &= (set1.member(currentMax) && set2.member(currentMax));
				if (!passed) {
					System.out.println(currentMax + " wasn't a member of both " + set1 + " and " + set2);
				}
			}
		}
		this.passed = passed;
		return passed;
	}
}

class Diff extends Test {
	public String testDescription = "Check that the difference of two sets contains only elements from the first set";

	Diff() {}

	public Boolean procedure(int numTimes, int upperBound, int numElements) {
		MultiSet set1;
		MultiSet set2;
		MultiSet diff;
		int currentMax;
		for (int i = 0; i < numTimes; i++) {
			set1 = generateMultiSet(upperBound, numElements);
			set2 = generateMultiSet(upperBound, numElements);
			diff = set1.diff(set2);
			while (!diff.isEmptyHuh()) {
				currentMax = diff.max();
				diff = diff.remove(currentMax);
				passed &= ((!set2.member(currentMax)) && set1.member(currentMax));
				if (!passed) {
					System.out.println(currentMax + " wasn't a member of " + set1 + " or was a member of " + set2);
				}
			}
		}
		this.passed = passed;
		return passed;
	}
}

class Equal extends Test {
	public String testDescription = "Check that two sets with the same elements inserted in both different or identical orders are equal, and that the difference is empty";

	Equal() {}

	public Boolean procedure(int numTimes, int upperBound, int numElements) {
		MultiSet set1;
		MultiSet set2;
		int[] nums = new int[numElements];
		Random rand = new Random();
		int offset;
		passed = true;
		for (int i = 0; i < numTimes; i++) {
			set1 = new Leaf();
			set2 = new Leaf();
			for (int j = 0; j < numElements; j++) {
				nums[j] = rand.nextInt(upperBound);
			}
			offset = rand.nextInt(numElements);
			for (int j = 0; j < nums.length; j++) {
				set1 = set1.add(nums[j]);
				set2 = set2.add(nums[(j + offset)%nums.length]);
			}
			passed &= (set1.equal(set2) && set2.equal(set1));
			if (!passed) {
				System.out.println(set1 + " is not equal to " + set2);
			}
			passed &= (set1.diff(set2).isEmptyHuh() && set2.diff(set1).isEmptyHuh());
		}
		return passed;
	}
}

class Subset extends Test {
	public String testDescription = "Check that a set that has only some of the elements of another set is a subset of that set, and that they are not equal";

	Subset() {}

	public Boolean procedure(int numTimes, int upperBound, int numElements) {
		MultiSet superSet = new Leaf();
		MultiSet subSet = new Leaf();
		int[] nums = new int[numElements];
		Random rand = new Random();
		passed = true;
		for (int i = 0; i < numTimes; i++) {
			superSet = new Leaf();
			subSet = new Leaf();
			for (int j = 0; j < nums.length; j++) {
				nums[j] = rand.nextInt(upperBound);
			}
			for (int j = 0; j < nums.length; j++) {
				superSet = superSet.add(nums[j]);
				if (j % 2 == 0) {
					subSet = subSet.add(nums[j]);
				}
			}
			passed &= (subSet.subset(superSet));
			if (!passed) {
				System.out.println(subSet + " is not a subset of " + superSet);
			}
			passed &= !subSet.equal(superSet);
		}
		return passed;
	}
}*/
