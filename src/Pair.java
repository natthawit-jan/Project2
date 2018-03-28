public class Pair <U, V> {
    private U left;
    private V right;

    public U getLeft() {
        return left;
    }

    public V getRight() {
        return right;
    }

    public Pair(U left, V right) {

        this.left = left;
        this.right = right;
    }
}
