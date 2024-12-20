import org.junit.Test;

import static org.junit.Assert.*;

public class RBTreeTest {

    @Test
    public void insertTest(){
        RBTree rbTree = new RBTree();
        rbTree.insert(45);
        rbTree.insert(22);
        rbTree.insert(10);
        rbTree.insert(77);
        assertEquals("10 22 45 77", rbTree.toString());
    }

    @Test
    public void findTest(){
        RBTree rbTree = new RBTree();
        rbTree.insert(46);
        rbTree.insert(33);
        assertTrue(rbTree.find(33));
        assertFalse(rbTree.find(10));
    }

    @Test
    public void deleteTest(){
        RBTree rbTree = new RBTree();
        rbTree.insert(100);
        rbTree.insert(75);
        assertTrue(rbTree.delete(100));
        assertFalse(rbTree.find(100));
    }

}
