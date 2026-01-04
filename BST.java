//AM 3230048 3220051
import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
public class BST implements WordCounter{
    
    private TreeNode head; //root of the tree
    private List stopWords=new List(); // list of stopwords
	
	public TreeNode getHead() {
        return head;
    }
    public int getSubtreesize(TreeNode node){
        return node.subtreeSize;
    }
    //Node class used in list of skipWords
    class Node {
        Node next;
        String key;

        public Node(String key) {
            this.key = key;
        }
    }
    //LinkedList which contains skipWords
    class List {
        private Node listHead; //Head pointer
        private int length;  //list's size

        public List() {

        }
        //adds a skipWord to the list
        public void add(String key) {
            key=key.toLowerCase();
            if(listHead==null){
                listHead=new Node(key);
            }
            if (!contains(key)) {
                Node newNode = new Node(key);
                newNode.next = listHead;
                listHead = newNode;
                length++;
            }

        }

        //prints the key of each node
        public void printString() {
            Node temp=listHead;
            while (temp!=null){
                System.out.println(temp.key);
                temp=temp.next;
            }

        }
        //removes a Node from the list
        public void remove(String key) {
            key = key.toLowerCase();
            Node temp = listHead, prev = null;

            while (temp != null) {
                if (temp.key.equals(key)) {
                    if (prev == null) {
                        listHead = temp.next;
                        length--;
                    } else {
                        prev.next = temp.next;
                        length--;
                    }
                    return;
                }
                prev = temp;
                temp = temp.next;
            }

        }
        //checks if the key exists in the List
        public boolean contains(String key) {
            Node temp = listHead;
            while (temp != null) {
                if (temp.key.equals(key.toLowerCase())) {
                    return true;
                }
                temp = temp.next;
            }
            return false;
        }

    }
    //prints list's items
    public void printList(){
        stopWords.printString();
    }
    //TreeNode class
    private class TreeNode {
        WordFrequency item; //Node's value
        TreeNode left; // pointer to left subtree
        TreeNode right; // pointer to right subtree
        int subtreeSize; //number of nodes in subtree starting at this node
        public TreeNode(WordFrequency item){
            this.item=item;
        }
        public String toString(){
            return item.key();
        }
    }
    public void insert(String w){
        WordFrequency word=new WordFrequency(w.toLowerCase());
        TreeNode newNode=new TreeNode(word);

        if(head==null){//empty tree
            head=newNode;
            newNode.item.increment_times_occured();
            head.subtreeSize=1;
            return;
        }

        TreeNode temp =head;

        while (true) {

            if (newNode.item.key().equals(temp.item.key())){
                temp.item.increment_times_occured();
                return;
            }

            if (newNode.item.key().compareTo(temp.item.key())<0) {
                if (temp.left == null) {
                    temp.left = newNode;
                    newNode.item.increment_times_occured();
                    break;
                }
                temp = temp.left;
            } else {
                if (temp.right == null) {
                    temp.right = newNode;
                    newNode.item.increment_times_occured();
                    break ;
                }
                temp = temp.right;
            }
        }
        TreeNode parent=newNode;//sets parent to the last node that was inserted

        while (parent != null) {
            parent.subtreeSize = 1+getSubtreeSize(parent.left) + getSubtreeSize(parent.right); //counts the subtreesize
            parent = findParent(head, parent.item.key());//goes to the next parent
        }
    }
    private int getSubtreeSize(TreeNode node) { //helper method checks if node is null and returns the subtreeSize of the Node
        return (node == null) ? 0 : node.subtreeSize;
    }
    private TreeNode findParent(TreeNode node, String key) {
        if ( node == null ||node.item.key().equals(key)) return null; //base case

        if ((node.left != null && node.left.item.key().equals(key))||(node.right != null && node.right.item.key().equals(key))) { //checks TreeNode's childs and if we find the value we were looking for we have found the parent
            return node;
        }
        if (key.compareTo(node.item.key()) < 0) { // traverse left
            return findParent(node.left, key);
        } else {
            return findParent(node.right, key); //traverse right
        }
    }

    public WordFrequency search(String w){
        if (head == null) return null;
        double meanFrequency = getMeanFrequency();
        TreeNode temp = head;

        while (temp != null) {
            if (w.compareTo(temp.item.key())<0) {
                temp = temp.left;
            } else if (w.compareTo(temp.item.key())>0) {
                temp = temp.right;
            } else {//found the correct TreeNode
                //rotates the node if it's frequency is greater than mean Frequency
                if(temp.item.getTimes_occured()>meanFrequency){
                    remove(temp.item.key());
                    insert(temp.item);
                    updateSubtreeSizes(head);
                }
                return temp.item;
            }
        }
        return null;//the node was not found
    }
    private TreeNode rotL(TreeNode h) { //rotates left
        TreeNode x = h.right;
        h.right = x.left;
        x.left = h;
        return x;
    }
    private TreeNode rotR(TreeNode h) { //rotates right
        TreeNode x = h.left;
        h.left = x.right;
        x.right = h;
        return x;
    }
    //helper method that uses recursion to insert at head
    private TreeNode insertT(TreeNode h, WordFrequency x) {
        if (h == null) return new TreeNode(x);
        if ((x.key().compareTo( h.item.key())<0)) {
            h.left = insertT(h.left, x);
            h = rotR(h);
        }
        else {
            h.right = insertT(h.right, x);
            h = rotL(h);
        }
        return h;
    }
    public void insert(WordFrequency x) {//insert at Head
        head = insertT(head, x);
    }
    //updates the subtreesize of each node
    private int updateSubtreeSizes(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftSize = updateSubtreeSizes(node.left);
        int rightSize = updateSubtreeSizes(node.right);
        node.subtreeSize = 1+ leftSize + rightSize ;
        return node.subtreeSize;
    }

    //removes the TreeNode that has a WordFrequency with the key w
    public void remove(String w){
        head=removeR(head,w);
    }
    public TreeNode removeR(TreeNode h,String w){
        if (h == null) return null;
        String key = h.item.key();
        if (w.compareTo(key)<0) h.left = removeR(h.left, w);//traverse left and keep searching
        if (key.compareTo(w)<0) h.right = removeR(h.right, w);//traverse right and keep searching
        if (w.equals(key)) h = joinLR(h.left, h.right); //combines the 2 subtrees because if we remove the node we will lose access to subtrees
        return h;
    }

    private TreeNode joinLR(TreeNode a, TreeNode b) {
        if (b == null) return a;
        b = partR(b, 0);
        b.left = a;
        return b;
    }

    public TreeNode partR(TreeNode h, int k) {
        int t = (h.left == null) ? 0 : h.left.subtreeSize;
        if (t > k) {
            h.left = partR(h.left, k);
            h = rotR(h); }
        if (t < k) {
            h.right = partR(h.right, k-t-1);
            h = rotL(h); }
        return h;
    }

    //loads the file and inserts all words in the Tree. The file should be in the same directory
    public void load(String filename){

        try {
            File file = new File(filename +".txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                line = line.replaceAll("[\"()\\[\\]{}.,!?;:<>]", "");//replaces all unnecessary punctuation
                String[] pinakas=line.split(" ");

                for(String i:pinakas){
                    if(!stopWords.contains(i)&&i.matches("[a-zA-Z']+")) { // checks if the word is not included in stopword list and if it has only letters using regex
                        insert(i);
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    //counts total words
    private int countR(TreeNode h) {
        if (h == null) return 0;
        return h.item.getTimes_occured() + countR(h.left) + countR(h.right); }
    //returns the total number of words
    public int getNumTotalWords(){
        return countR(head);
    }
    //returns the number of distinct words
    public int getNumDistinctWords() {
        return head.subtreeSize;
    }
    //returns the frequency of a word
    public int getFrequency(String w){
        WordFrequency wordFrequency = search(w);
        if (wordFrequency == null) {
            return 0;
        }
        return search(w).getTimes_occured();
    }
    //returns the maximum Frequency
    public WordFrequency getMaxFrequency() {
        if (head == null) return null;
        return getMaxFrequencyRec(head, head.item);
    }
    //finds the word that has the maximum frequency
    private WordFrequency getMaxFrequencyRec(TreeNode node, WordFrequency maxWord) {
        if (node == null) return maxWord;

        if (getFrequency(node.item.key()) > getFrequency(maxWord.key())) {
            maxWord = node.item;
        }

        maxWord = getMaxFrequencyRec(node.left, maxWord);
        maxWord = getMaxFrequencyRec(node.right, maxWord);

        return maxWord;
    }
    //calculates and returns the mean frequency
    public double getMeanFrequency() {
        if (head == null) return 0;

        int totalOccurrences = countR(head);
        int distinctWords = head.subtreeSize;

        return (double) totalOccurrences / distinctWords;
    }


    //adds a stop word
    public void addStopWord(String w){
            this.stopWords.add(w);
    }
    //removes a stopWord
    public void removeStopWord(String w){
        this.stopWords.remove(w);
    }
    //Prints tree by word
    public void printTreeByWord(PrintStream stream){
        traversePrint(head,stream);
    }
    //in order traversal
    private void traversePrint(TreeNode h, PrintStream stream) {
        if (h == null) return;

        traversePrint(h.left,stream);
        stream.println(h.item.key());
        traversePrint(h.right,stream);
    }
    //traverses through the tree and saves the values in order to the array
    private int traverseR(TreeNode h, WordFrequency[] pinakas, int thesi) {
        if (h == null) {
            return thesi;
        }
        thesi = traverseR(h.left, pinakas, thesi);
        pinakas[thesi++] = h.item;
        thesi = traverseR(h.right, pinakas, thesi);
        return thesi;
    }
    //prints tree by frequency
    public void printΤreeByFrequency(PrintStream stream){
        WordFrequency[] pinakas=new WordFrequency[head.subtreeSize];
        traverseR(head,pinakas,0);
        quickSort(pinakas);
        for(WordFrequency i:pinakas){
            stream.println(i.key());
        }
    }
    //helper method to swap two items of the array
    private static void swap(WordFrequency[] array, int firstIndex, int secondIndex) {
        WordFrequency temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }
    //helper method for quicksort
    private static int pivot(WordFrequency[] array, int pivotIndex, int endIndex) {
        int swapIndex = pivotIndex;
        for (int i = pivotIndex + 1; i <= endIndex; i++) {
            if (array[i].getTimes_occured() < array[pivotIndex].getTimes_occured()) {
                swapIndex++;
                swap(array, swapIndex, i);
            }
        }
        swap(array, pivotIndex, swapIndex);

        return swapIndex;
    }



    private static void quickSortHelper(WordFrequency[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = pivot(array, left, right);
            quickSortHelper(array, left, pivotIndex-1);
            quickSortHelper(array, pivotIndex+1, right);
        }
    }

    public static void quickSort(WordFrequency[] array) {
        quickSortHelper(array, 0, array.length-1);
    }


    private String toStringR(TreeNode h) {
            if (h == null) return "";
            String s = toStringR(h.left);
            s += h.item.toString();
            s += toStringR(h.right);
            return s;
    }
    public String toString() { return toStringR(head); }

}


