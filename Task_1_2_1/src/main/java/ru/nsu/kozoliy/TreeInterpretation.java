package ru.nsu.kozoliy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

/**
 * Класс для представления графов деревьев.
 */
public class TreeInterpretation {
    /**
     * Обобщенный класс для представления деревьев.
     *
     * @param <T> Тип данных, хранящихся в узлах дерева.
     */
    public static class Tree<T> implements Iterable<T> {
        private final T value;
        private final List<Tree<T>> children = new ArrayList<>();
        private Tree<T> parent;
        private int countModifications = 0;

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
            countModifications++;
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
            if (parent != null) {
                parent.children.remove(this);
                parent.countModifications++;
            }
        }

        /**
         * Выполняет обход дерева в ширину и выводит значения узлов в порядке обхода.
         */
        @Override
        public Iterator<T> iterator() {
            return new Bfs();
        }

        public Iterator<T> bfsIterator() {
            return new Bfs();
        }

        public Iterator<T> dfsIterator() {
            return new Dfs();
        }

        /**
         * Выполняет обход дерева в ширину и выводит значения узлов в порядке обхода.
         */
        private class Bfs implements Iterator<T> {
            private final Queue<Tree<T>> queue = new LinkedList<>();
            private final int expected = countModifications;

            Bfs() {
                queue.add(Tree.this);
            }

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public T next() {
                if (expected != countModifications) {
                    throw new ConcurrentModificationException("ERROR: Concurrent modification!");
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Tree<T> current = queue.poll();
                assert current != null;
                queue.addAll(current.children);
                return current.value;
            }
        }

        /**
         * Выполняет обход дерева в глубину и выводит значения узлов в порядке обхода.
         */
        private class Dfs implements Iterator<T> {
            private final Stack<Tree<T>> stack = new Stack<>();
            private final int expected = countModifications;

            Dfs() {
                stack.push(Tree.this);
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expected != countModifications) {
                    throw new ConcurrentModificationException("ERROR: Concurrent modification!");
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Tree<T> current = stack.pop();
                for (int i = current.children.size() - 1; i >= 0; i--) {
                    stack.push(current.children.get(i));
                }
                return current.value;
            }
        }


        /**
         * Переопределение метода `equals` для сравнения двух деревьев.
         * Деревья считаются равными, в том случае, если значения во всех его элементах равны.
         * Даже если элементы добавлены в разной последовательности.
         * Сравниваются именно значения
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
            if (this.value == otherTree.value
                    && this.children.size() == otherTree.children.size()) {
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
            if (elem.value == "") {
                return "";
            }
            treeString.append("  ".repeat(Math.max(0, depth))).append(elem.value).append("\n");

            for (Tree<T> child : elem.children) {
                printTreeRecursive(child, depth + 1, treeString);
            }
            return treeString.toString();
        }

    }


    /**
     * Точка входа в программу.
     */
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        Tree<String> tree = new Tree<>("R1");
        tree.addChild("A");
        var b = tree.addChild("C");
        var c = tree.addChild("B");
        b.addChild("D");
        b.addChild("E");
        var d = c.addChild("F");
        var t = d.addChild("T");
        t.addChild("E");


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


        System.out.println("BFS traversal:");
        Iterator<String> bfsIterator = tree.bfsIterator();
        while (bfsIterator.hasNext()) {
            System.out.print(bfsIterator.next() + " ");
        }

        System.out.println("\nDFS traversal:");
        Iterator<String> dfsIterator = tree.dfsIterator();
        while (dfsIterator.hasNext()) {
            System.out.print(dfsIterator.next() + " ");
        }

    }

}
