package ru.nsu.kozoliy;


import java.util.*;


public class TreeInterpretation {
    public static void main(String[] args) {
        Tree<String> tree = new Tree<>("35");
        var a = tree.addChild("A");
        var b = tree.addChild("C");
        var c = tree.addChild("B");
        var d = b.addChild("D");
        var e = b.addChild("E");
        var f = c.addChild("F");

        Tree<String> tree2 = new Tree<>("35");
        var a2 = tree2.addChild("A");
        var b2 = tree2.addChild("B");
        var c2 = tree2.addChild("C");
        var d2 = c2.addChild("E");
        var e2 = c2.addChild("D");
        var f2 = b2.addChild("F");

        f2.remove();
        System.out.println("BFS traversal:");
        tree.breadthFirstTraversal();

        System.out.println("\nDFS traversal:");
        tree.depthFirstTraversal();

        System.out.println("\nBFS2 traversal:");
        tree2.breadthFirstTraversal();

        System.out.println("\nDFS2 traversal:");
        tree2.depthFirstTraversal();

        System.out.println("\n");

        tree2.printTree();
        tree.printTree();

        System.out.println("\n" + tree.equals(tree2));


    }

    public static class Tree<T> {
        private final T value;
        private final ArrayList<Tree<T>> children = new ArrayList<>();
        private Tree<T> parent;

        public Tree(T value) {
            this.value = value;
        }
        public Tree<T> addChild(T value) {
            Tree<T> newLeaf = new Tree<>(value);
            newLeaf.parent = this;
            this.children.add(newLeaf);
            return newLeaf;
        }

        public Tree<T> addChild(Tree<T> child) {
            child.parent = this;
            this.children.add(child);
            return child;
        }
        public void remove() {
            this.children.clear();
            if (this.parent != null) {
                this.parent.children.remove(this);
            }
        }

        public void breadthFirstTraversal() {
            Queue<Tree<T>> queue = new LinkedList<>();
            queue.add(this);

            while (!queue.isEmpty()) {
                Tree<T> current = queue.poll();
                System.out.print(current.value + " ");

                queue.addAll(current.children);
            }
        }

        public void depthFirstTraversal() {
            depthFirstTraversal(this);
        }

        private void depthFirstTraversal(Tree<T> node) {
            System.out.print(node.value + " ");

            for (Tree<T> child : node.children) {
                depthFirstTraversal(child);
            }
        }


        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Tree<?>)) {
                return false;
            }
            Tree<T> otherTree = (Tree<T>) obj;
            if (!otherTree.value.equals(this.value)) {
                return false;
            }
            if (this.value == otherTree.value && this.children.size() == otherTree.children.size()) {
                otherTree.sortChildren();
                this.sortChildren();
                for(int i = 0; i < this.children.size(); i++) {
                    if(this.children.get(i) == null || otherTree.children.get(i) == null){
                        return false;
                    }
                    if(!this.children.get(i).equals(otherTree.children.get(i))){
                        return false;
                    }

                }
                return true;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }

        public void sortChildren() {
            children.sort((child1, child2) -> {
                return child1.value.toString().compareTo(child2.value.toString());
            });
        }

        public void printTree() {
            printTreeRecursive(this, 0);
        }

        private void printTreeRecursive(Tree<T> node, int depth) {

            System.out.println("  ".repeat(Math.max(0, depth)) + node.value);

            for (Tree<T> child : node.children) {
                printTreeRecursive(child, depth + 1);
            }
        }

        public void printChildren() {
            for (Tree<T> child : children) {
                System.out.print(child.value + " ");
            }
        }

        public T getValue() {
            return value;
        }
    }
}