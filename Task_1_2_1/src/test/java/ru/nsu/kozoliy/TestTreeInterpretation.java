package ru.nsu.kozoliy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTreeInterpretation {

    private TreeInterpretation.Tree<String> tree;

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
    public void testBFS() {
        tree = new TreeInterpretation.Tree<>("R1");
        tree.addChild("A");
        tree.addChild("B");
        TreeInterpretation.Tree<String> subtree = new TreeInterpretation.Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("E");
        tree.addChild(subtree);

        String expected = "R1 A B R2 C E ";
        assertEquals(expected, tree.breadthFirstTraversal());
    }

    @Test
    public void testDFS() {
        tree = new TreeInterpretation.Tree<>("R1");
        tree.addChild("A");
        tree.addChild("B");
        TreeInterpretation.Tree<String> subtree = new TreeInterpretation.Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("E");
        tree.addChild(subtree);
        tree.addChild("F");

        String expected = "R1 A B R2 C E F ";
        assertEquals(expected, tree.depthFirstTraversal());
    }

    @Test
    public void testZeroTreeDFS() {
        tree = new TreeInterpretation.Tree<>("");

        String expected = " ";
        assertEquals(expected, tree.depthFirstTraversal());
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
    public void  testMain() {
        TreeInterpretation.main(new String[0]);
    }


}
