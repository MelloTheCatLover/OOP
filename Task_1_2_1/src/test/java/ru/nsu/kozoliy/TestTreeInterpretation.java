package ru.nsu.kozoliy;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для методов работы с классом представления деревьев.
 */
public class TestTreeInterpretation {

    private TreeInterpretation.Tree<String> tree;

    @BeforeEach
    void setup() {
        tree = new TreeInterpretation.Tree<>("R1");
        tree.addChild("A");
        var b = tree.addChild("C");
        var c = tree.addChild("B");
        b.addChild("D");
        b.addChild("E");
        var d = c.addChild("F");
        var t = d.addChild("T");
        t.addChild("E");
    }


    @Test
    public void testEquality() {
        TreeInterpretation.Tree<String> tree1 = new TreeInterpretation.Tree<>("Root");
        TreeInterpretation.Tree<String> tree2 = new TreeInterpretation.Tree<>("Root");
        assertEquals(tree1, tree2);

        TreeInterpretation.Tree<String> child1 = tree1.addChild("Child");
        TreeInterpretation.Tree<String> child2 = tree2.addChild("Child");
        assertEquals(tree1, tree2);
        assertEquals(child1, child2);

        tree1.addChild("AnotherChild");
        assertNotEquals(tree1, tree2);
    }

    @Test
    public void testBuildAndPrint() {
        tree = new TreeInterpretation.Tree<>("R1");
        tree.addChild("A");
        tree.addChild("B");
        TreeInterpretation.Tree<String> subtree = new TreeInterpretation.Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("E");
        tree.addChild(subtree);

        String expected = "R1\n  A\n  B\n  R2\n    C\n    E\n";
        assertEquals(expected, tree.printTree());
    }

    @Test
    void testBfs() {
        String expected = "R1 A C B D E F T E ";
        StringBuilder result = new StringBuilder();
        Iterator<String> bfsIterator = tree.bfsIterator();
        while (bfsIterator.hasNext()) {
            result.append(bfsIterator.next()).append(" ");
        }
        assertEquals(expected, result.toString());
    }

    @Test
    void testDfs() {
        String expected = "R1 A C D E B F T E ";
        StringBuilder result = new StringBuilder();
        Iterator<String> dfsIterator = tree.dfsIterator();
        while (dfsIterator.hasNext()) {
            result.append(dfsIterator.next()).append(" ");
        }
        assertEquals(expected, result.toString());
    }


    @Test
    void testAddChild() {
        tree = new TreeInterpretation.Tree<>("ROOT");
        var a = tree.addChild("A");

        assertTrue(tree.getChildren().contains(a));
    }

    @Test
    void testRemoveChild() {
        tree = new TreeInterpretation.Tree<>("ROOT");
        var a = tree.addChild("A");

        assertTrue(tree.getChildren().contains(a));

        a.remove();
        assertFalse(tree.getChildren().contains(a));
    }

}
