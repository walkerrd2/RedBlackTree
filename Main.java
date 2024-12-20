import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Testing Insert
        RBTree rb = new RBTree();
        rb.insert(10);
        rb.insert(55);
        rb.insert(20);
        rb.insert(5);
        rb.insert(2);
        System.out.println("Tree after insert: "+ rb); //Expected 2,5,10,20,55

        System.out.println("-------------------------------");

        // Testing Find
        System.out.println("Find 10: "+rb.find(20)); //Expected true
        System.out.println("Find 25: "+rb.find(25)); //Expected false

        System.out.println("-------------------------------");

        // Testing Delete
        rb.delete(55);
        rb.delete(2);
        System.out.println("Tree after removing 55 and 2: "+rb); //Expected 5,10,20
        System.out.println("Find 55: "+rb.find(55)); // Expect false
        System.out.println("Find 2: "+rb.find(2)); //Expect false

        System.out.println("-------------------------------");

        // Testing run time of tree insertion
        Scanner scan = new Scanner(System.in);
        RBTree redBlack = new RBTree();

        System.out.println("Input how many elements for tree:");
        int elements = scan.nextInt();
        System.out.println("Now enter "+elements+" integers");
        long start = System.nanoTime();
        for(int i = 0; i < elements; i++){
            int val = scan.nextInt();
            redBlack.insert(val);
            System.out.println("tree after inserting "+val+": "+redBlack);
        }
        long end = System.nanoTime();
        System.out.println("Time taken for "+elements+" insertions: "+(end-start)+ " ns");

        System.out.println("-------------------------------");

        // Testing run time for find
        System.out.println("How many elements to find in tree:");
        int find = scan.nextInt();
        System.out.println("Now enter the values: ");
        long start2 = System.nanoTime();
        for(int i = 0; i < find; i++){
            int value = scan.nextInt();
            redBlack.find(value);
            if(!redBlack.find(value)){
                System.out.println("Value not in list");
            }
        }
        long end2 = System.nanoTime();
        System.out.println("Time taken for "+find+" finds: "+(end2-start2)+ " ns");

        System.out.println("-------------------------------");

        // Testing for deletion
        System.out.println("How many nodes do you want to delete: ");
        int delete = scan.nextInt();
        System.out.println("Enter the node you want to remove");
        long start3 = System.nanoTime();
        for(int i = 0; i < delete; i++){
            int vals = scan.nextInt();
            redBlack.delete(vals);
            System.out.println("tree after deleting "+vals+": "+redBlack);
        }
        long end3 = System.nanoTime();
        System.out.println("Time taken for "+delete+" deletions: "+(end3-start3)+ " ns");

        scan.close();
    }
}