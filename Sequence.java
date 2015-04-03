public interface Sequence<T extends Comparable<T>> extends Sequenced<T>{
	public T here();
	public Boolean isEmptyHuh();
	public Sequence<T> next();
}