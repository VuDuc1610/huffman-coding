import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanSubmit{

    //hashmap to store each character and its HuffmanCharacter
    HashMap<Character,String> hashStore = new HashMap<>(); 

    //node class
    private class Node{
        public Character character;
        public int count;
        public Node left;
        public Node right;
        public String HuffmanChar;
        public boolean isLeaf;

        //constructor 
        public Node(char character, int count){
            this.count = count;
            this.character = character;
            HuffmanChar = "";
            isLeaf = true;
        }
        public Node(Node node1, Node node2, int count){
            this.count = count;
            left = node1;
            right = node2;
            HuffmanChar = "";
            isLeaf = false;
        }
        public Node(char character, int count, String HuffmanChar){
            this.character = character;
            this.count = count;
            this.HuffmanChar = HuffmanChar;
            isLeaf = true;
        }
        public void Left(String string){
            left.HuffmanChar = string;
        }
        public void Right(String string){
            right.HuffmanChar = string;
        }
        public String toString(){
            return this.character + ":" + this.count;
        }
    }

    //method assign HuffmanChar to the letters
    public void assignHuffmanChar(Node node, BinaryOut out){
        if(node.left != null){
            node.Left(node.HuffmanChar + "0");
            assignHuffmanChar(node.left,out);
        } 
        if(node.right != null){
            node.Right(node.HuffmanChar + "1");
            assignHuffmanChar(node.right,out);
        }
        if(node.character != null){
            hashStore.put(node.character,node.HuffmanChar);
            // out.write(node.HuffmanChar);
        }
    }
    
    //class allows Priority Queue to compare the frequency
    public class nodeCompare implements Comparator<Node>{
        @Override
        public int compare(Node node1, Node node2){
            return node1.count-node2.count;
        }
    }

    public void encode (String inputFile , String outputFile , String freqFile){
        
        BinaryIn in1 = new BinaryIn(inputFile);
        BinaryIn in2 = new BinaryIn(inputFile);
        BinaryOut frequency = new BinaryOut(freqFile);
        BinaryOut out = new BinaryOut(outputFile);

        //add all characters and their frequency to a hashmap
        HashMap<Character,Integer> hash = new HashMap<>();
        while(in1.isEmpty() == false){
            char character = in1.readChar();
            // System.out.println(character);
            hash.put(character, hash.getOrDefault(character,0)+1);
        }

        //store frequency of each character present in the input file
        for(Map.Entry<Character,Integer> entry : hash.entrySet()){
            Character key = entry.getKey();
            int value = entry.getValue();
            String binary = String.format("%08d", Integer.parseInt(Integer.toBinaryString((int) key)));
            frequency.write(binary + ":" + value + "\n"); 
        }
        frequency.close();

        //add the characters and frequency to the priority queue
        PriorityQueue<Node> queue = new PriorityQueue<>(new nodeCompare());
        for(Map.Entry<Character,Integer> entry : hash.entrySet()){
            Character key = entry.getKey();
            int value = entry.getValue();
            queue.add(new Node(key,value));
        }

        //create a Huffman Tree using the Priority Queue 
        while(queue.size() > 1){
            Node first = queue.peek();
            queue.poll();
            Node second = queue.peek();
            queue.poll();
            queue.add(new Node(first,second,first.count+second.count));
        }

        Node root = queue.peek();
        assignHuffmanChar(root,out);


        while(in2.isEmpty() == false){
            char character = in2.readChar();
            String encode = hashStore.get(character);
            for(int i = 0; i < encode.toCharArray().length; i++){
                if(encode.charAt(i) == '0'){
                    out.write(false);
                }
                else {
                    out.write(true);
                }
            }
        }
        out.close();
    }
    public void decode (String inputFile , String outputFile , String freqFile){
        BinaryIn input = new BinaryIn(inputFile);
		BinaryIn freqInput = new BinaryIn(freqFile);
		BinaryOut out = new BinaryOut(outputFile);

        int sum = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>(new nodeCompare());

        while(!freqInput.isEmpty()){
        String s = "";
        for(int i = 0; i < 8; i++){
            Character c = freqInput.readChar();
            s += c;
        }
        Integer n = Integer.parseInt(s,2);
		String result = Character.toString((char) n.intValue());

        freqInput.readChar();
        
        String stringCount = "";
        while(!freqInput.isEmpty()){
            Character c = freqInput.readChar();
            if(c == '\n'){
                break;
            }
            stringCount += c;
        }
        int freq = Integer.parseInt(stringCount);
        sum += freq;
        queue.add(new Node(result.charAt(0), freq, s));
    }
        //create a Huffman Tree using the Priority Queue 
          while(queue.size() > 1){
            Node first = queue.peek();
            queue.poll();
            Node second = queue.peek();
            queue.poll();
            queue.add(new Node(first,second,first.count+second.count));
        }

        Node root = queue.peek();
        Node temp = root;

        System.out.println(temp.toString());
        //Decoding
        while(!input.isEmpty()){
            if(sum < 1){
                break;
            }
            while(temp.isLeaf == false &&
             !input.isEmpty()){
                if(input.readBoolean()){
                    temp = temp.right;
                }
                else {
                    temp = temp.left;
                }
            }
            if(temp.character != null){
                out.write(temp.character);
            }
            temp = root;
            sum--;
        }
        out.close();
    }
    public static void main (String[] args) throws Exception{

       HuffmanSubmit huff = new HuffmanSubmit();
       huff.encode("ur.jpg ","ur.enc","freq.txt");
       huff.decode("ur.enc","ur_dec.jpg","freq.txt");
    }
}