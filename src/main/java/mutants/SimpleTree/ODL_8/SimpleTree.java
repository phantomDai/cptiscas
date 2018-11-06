// This is a mutant program.// Author : ysmapackage mutants.SimpleTree.ODL_8;import java.util.ArrayList;import java.util.List;import java.util.concurrent.atomic.AtomicInteger;public class SimpleTree<T> implements PQueue<T>{    int range;    List<TreeNode> leaves;    TreeNode root;    public SimpleTree( int logRange )    {        range = 1 << logRange;        leaves = new ArrayList<TreeNode>( range );        root = buildTree( logRange, 0 );    }     TreeNode buildTree( int height, int slot )    {        TreeNode root = new TreeNode();        root.counter = new AtomicInteger( 0 );        if (height == 0) {            root.bin = new Bin<T>();            leaves.add( slot, root );        } else {            root.left = buildTree( height - 1, slot );            root.right = buildTree( height - 1, 2 * slot + 1 );            root.left.parent = root.right.parent = root;        }        return root;    }    public  void add( T item, int priority )    {        TreeNode node = leaves.get( priority );        node.bin.put( item );        while (node != root) {            TreeNode parent = node.parent;            if (node == parent.left) {                parent.counter.getAndIncrement();            }            node = parent;        }    }    public  T removeMin()    {        TreeNode node = root;        while (!node.isLeaf()) {            if (node.counter.getAndDecrement() > 0) {                node = node.left;            } else {                node = node.right;            }        }        return (T) node.bin.get();    }    public class TreeNode    {        AtomicInteger counter;        TreeNode parent;        TreeNode right;        TreeNode left;        Bin<T> bin;        public  boolean isLeaf()        {            return right == null;        }    }}