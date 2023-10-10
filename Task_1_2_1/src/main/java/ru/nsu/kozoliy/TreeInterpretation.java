package ru.nsu.kozoliy;


import java.util.*;

/**
 * Класс для представления графов деревьев.
 */
public class TreeInterpretation {
    public static void main(String[] args) {
        Tree<String> tree = new Tree<>("R1");
        tree.addChild("A");
        var b = tree.addChild("C");
        var c = tree.addChild("B");
        b.addChild("D");
        b.addChild("E");
        c.addChild("F");

        Tree<String> tree2 = new Tree<>("R1");
        tree2.addChild("A");
        var b2 = tree2.addChild("B");
        var c2 = new Tree<>("C");
        c2.addChild("E");
        c2.addChild("D");
        b2.addChild("F");
        tree2.addChild(c2);

        var removeMe = c2.addChild("RemoveMe");
        removeMe.remove();

        System.out.println("\nBFS traversal:\n" + tree.breadthFirstTraversal());

        System.out.println("\nDFS traversal:\n" + tree.depthFirstTraversal());

        System.out.println("\nBFS2 traversal:\n" + tree2.breadthFirstTraversal());

        System.out.println("\nDFS2 traversal:\n" + tree2.depthFirstTraversal());

        System.out.println("\n");

        System.out.println(tree.printTree());
        System.out.println("\n" + tree2.printTree());

        System.out.println(tree.equals(tree2));
    }

    /**
     * Обобщенный класс для представления деревьев.
     *
     * @param <T> Тип данных, хранящихся в узлах дерева.
     */
    public static class Tree<T> {
        private final T value;
        private final List<Tree<T>> children = new ArrayList<>();
        private Tree<T> parent;

        /**
         * Конструктор для того, чтобы создавать потомка дерева с уже переданным значением.
         *
         * @param value Значение узла.
         */
        public Tree(T value) {
            this.value = value;
        }

        /**
         * Добавляет новый узел, который является листом, то есть у него нет потомков.
         *
         * @param value Значение узла-потомка.
         * @return Новый узел-потомок.
         */
        public Tree<T> addChild(T value) {
            Tree<T> newLeaf = new Tree<>(value);
            newLeaf.parent = this;
            this.children.add(newLeaf);
            return newLeaf;
        }

        /**
         * Добавляет подддерево к текущему узлу.
         *
         * @param child Узел-потомок для добавления.
         */
        public void addChild(Tree<T> child) {
            child.parent = this;
            this.children.add(child);
        }

        /**
         * Удаляет узел и всех его потомков из дерева.
         */
        public void remove() {
            this.children.clear();
            if (this.parent != null) {
                this.parent.children.remove(this);
            }
        }



        /**
         * Выполняет обход дерева в ширину и выводит значения узлов в порядке обхода.
         */
        public String breadthFirstTraversal(StringBuilder bfsString) {
            Queue<Tree<T>> queue = new LinkedList<>();
            queue.add(this);

            while (!queue.isEmpty()) {
                Tree<T> current = queue.poll();
                bfsString.append(current.value).append(" ");
                queue.addAll(current.children);
            }

            return bfsString.toString();
        }

        public String breadthFirstTraversal() {
            return breadthFirstTraversal(new StringBuilder());
        }

        /**
         * Выполняет обход дерева в глубину и выводит значения узлов в порядке обхода.
         */
        public String depthFirstTraversal() {
            return depthFirstTraversal(this, new StringBuilder());
        }

        private String depthFirstTraversal(Tree<T> node, StringBuilder dfsString) {
            dfsString.append(node.value).append(" ");

            for (Tree<T> child : node.children) {
                depthFirstTraversal(child, dfsString);
            }

            return dfsString.toString();
        }

        /**
         * Переопределение метода `equals` для сравнения двух деревьев.
         *
         * @param obj Объект для сравнения.
         * @return `true`, если деревья равны, иначе `false`.
         */
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Tree<?>)) {
                return false;
            }
            Tree<?> otherTree = (Tree<?>) obj;
            if (!otherTree.value.equals(this.value)) {
                return false;
            }
            if (this.value == otherTree.value && this.children.size() == otherTree.children.size()) {
                otherTree.sortChildren();
                this.sortChildren();
                for (int i = 0; i < this.children.size(); i++) {
                    if (this.children.get(i) == null || otherTree.children.get(i) == null) {
                        return false;
                    }
                    if (!this.children.get(i).equals(otherTree.children.get(i))) {
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

        /**
         * Сортирует потомков текущего узла по значению.
         */
        public void sortChildren() {
            children.sort(Comparator.comparing(child -> child.value.toString()));
        }

        /**
         * Генерирует строковое представление дерева.
         */
        public String printTree() {
            return printTreeRecursive(this, 0, new StringBuilder());
        }

        private String printTreeRecursive(Tree<T> elem, int depth, StringBuilder treeString) {
            treeString.append("  ".repeat(Math.max(0, depth))).append(elem.value).append("\n");

            for (Tree<T> child : elem.children) {
                printTreeRecursive(child, depth + 1, treeString);
            }
            return treeString.toString();
        }
    }
}
