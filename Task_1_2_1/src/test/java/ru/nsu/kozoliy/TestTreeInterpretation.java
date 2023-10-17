package ru.nsu.kozoliy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты для методов работы с классом представления деревьев.
 */
public class TestTreeInterpretation {

    private TreeInterpretation.Tree<String> tree;

    @BeforeEach
    void setup() {
        tree = new TreeInterpretation.Tree<>("R1");
        tree.addChild("A");
        var b = tree.addChild("B");
        var c = tree.addChild("C");
        b.addChild("D");
        b.addChild("E");
        var f = c.addChild("F");
        var t = f.addChild("T");
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
    public void testEqualityWithSubTree() {
        TreeInterpretation.Tree<String> tree2 = new TreeInterpretation.Tree<>("R1");
        tree2.addChild("A");
        var b =  new TreeInterpretation.Tree<>("B");
        var c = tree2.addChild("C");
        b.addChild("D");
        b.addChild("E");
        var f = c.addChild("F");
        var t = f.addChild("T");
        t.addChild("E");
        tree2.addChild(b);

        assertEquals(tree, tree2);
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
        String expected = "R1 A B C D E F T E ";
        StringBuilder result = new StringBuilder();
        Iterator<String> bfsIterator = tree.bfsIterator();
        while (bfsIterator.hasNext()) {
            result.append(bfsIterator.next()).append(" ");
        }
        assertEquals(expected, result.toString());
    }

    @Test
    void testDfs() {
        String expected = "R1 A B D E C F T E ";
        StringBuilder result = new StringBuilder();
        Iterator<String> dfsIterator = tree.dfsIterator();
        while (dfsIterator.hasNext()) {
            result.append(dfsIterator.next()).append(" ");
        }
        assertEquals(expected, result.toString());
    }

    @Test
    void testConcurrentModificationExceptionInBfs() {
        Iterator<String> bfsIterator = tree.bfsIterator();
        try {
            while (bfsIterator.hasNext()) {
                String value = bfsIterator.next();
                // Simulate a concurrent modification
                if (value.equals("A")) {
                    tree.addChild("NewNode");
                }
            }
            fail("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException e) {
            // This is expected
        }
    }

    @Test
    void testConcurrentModificationDfs() {
        Iterator<String> dfsIterator = tree.dfsIterator();
        tree.addChild("New");
        assertThrows(ConcurrentModificationException.class, dfsIterator::next);
    }

    @Test
    void testConcurrentModificationBfs() {
        Iterator<String> bfsIterator = tree.bfsIterator();
        tree.addChild("New");
        assertThrows(ConcurrentModificationException.class, bfsIterator::next);
    }

    @Test
    void testRemoveConcurrentExceptionDfs() {
        var removeMe = tree.addChild("RemoveMe");
        Iterator<String> dfsIterator = tree.dfsIterator();
        removeMe.remove();
        assertThrows(ConcurrentModificationException.class, dfsIterator::next);
    }

    @Test
    void testRemoveConcurrentExceptionBfs() {
        var removeMe = tree.addChild("RemoveMe");
        Iterator<String> bfsIterator = tree.bfsIterator();
        removeMe.remove();
        assertThrows(ConcurrentModificationException.class, bfsIterator::next);
    }

    @Test
    void testDfsAndBfs() {
        tree = new TreeInterpretation.Tree<>("R1");
        tree.addChild("A");
        var b = tree.addChild("B");
        var c = tree.addChild("C");
        b.addChild("D");
        b.addChild("E");
        var f = c.addChild("F");
        var t = f.addChild("T");
        t.addChild("E");

        StringBuilder resultAfterFirstDfs = new StringBuilder();
        Iterator<String> dfsIterator = tree.dfsIterator();
        while (dfsIterator.hasNext()) {
            resultAfterFirstDfs.append(dfsIterator.next()).append(" ");
        }
        String expectedFirstDfs = "R1 A B D E C F T E ";
        assertEquals(expectedFirstDfs, resultAfterFirstDfs.toString());

        b.remove();
        StringBuilder resultAfterSecondDfs = new StringBuilder();
        dfsIterator = tree.dfsIterator();
        while (dfsIterator.hasNext()) {
            resultAfterSecondDfs.append(dfsIterator.next()).append(" ");
        }
        String expectedSecondDfs = "R1 A C F T E ";
        assertEquals(expectedSecondDfs, resultAfterSecondDfs.toString());
    }

    @Test
    void testSort() {
        TreeInterpretation.Tree<String> sortMe = new TreeInterpretation.Tree<>("ROOT");
        sortMe.addChild("B");
        sortMe.addChild("A");
        sortMe.addChild("D");
        sortMe.addChild("C");
        sortMe.addChild("C");
        sortMe.addChild("E");

        String unsorted = sortMe.printTree();
        String unsortedExpected = "ROOT\n  B\n  A\n  D\n  C\n  C\n  E\n";
        assertEquals(unsortedExpected, unsorted);

        sortMe.sortChildren();

        String sorted = sortMe.printTree();
        String sortedExpected = "ROOT\n  A\n  B\n  C\n  C\n  D\n  E\n";
        assertEquals(sortedExpected, sorted);
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

    @Test
    void tesZeroPrinting() {
        TreeInterpretation.Tree<String> zero = new TreeInterpretation.Tree<>("");
        assertEquals("", zero.printTree());
    }
}
