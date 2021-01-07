import java.util.ArrayList;
import java.util.Scanner;

public class Standard_Huffman {
ArrayList<Node>vlcTable = new ArrayList<Node>();

void takeInput(){
    System.out.println("Enter number of characters");
    Scanner sc = new Scanner(System.in);
    int numberOfSymbols = sc.nextInt();

    for (int i = 0; i < numberOfSymbols; i++) {
        System.out.print("Enter letter: ");
        String symbol = sc.next();
        System.out.print("Enter Frequency: ");
        int freq = sc.nextInt();
        Node node = new Node(symbol,freq);
        vlcTable.add(node);
    }
}

int getMinimumFreqIdx(){
   int minIdx = 0;
   int mini = 1000000000;
    for (int i = 0; i < vlcTable.size(); i++) {
        if(vlcTable.get(i).frequency < mini)
        {
            minIdx = i ;
            mini = vlcTable.get(i).frequency;
        }

    }

    return  minIdx;
}

void buildCodeArrayList(){
    ///Base case
    if(vlcTable.size()==1){
        vlcTable.get(0).codeWord = "";
        return;
    }

    ///Do: Combine least two symbols
    //Symbol 1
    int mini = getMinimumFreqIdx();
    Node n1 = vlcTable.get(mini);
    vlcTable.remove(mini);

    ///Symbol 2
    mini = getMinimumFreqIdx();
    Node n2 = vlcTable.get(mini);
    vlcTable.remove(mini);

    ///combine the nodes into a new node
    Node nPrime = new Node(n1.symbol+n2.symbol,n1.frequency+ n2.frequency);
    vlcTable.add(nPrime);

    ///Recurse
    buildCodeArrayList();

    //Undo: take the codeWord of the combined Node and remove it, bring back the two children with their codeWord
    n1.codeWord = nPrime.codeWord + '1';
    n2.codeWord = nPrime.codeWord + '0';

    vlcTable.remove(nPrime);
    vlcTable.add(n1);
    vlcTable.add(n2);

}

void printVLCTable(){
    System.out.println("Symbol\tCode");
    for (Node n :
            vlcTable) {
        System.out.println(n.symbol+"\t\t  "+n.codeWord);
    }

}



public static void main(String[] args) {
        Standard_Huffman standardHuffman = new Standard_Huffman();
        standardHuffman.takeInput();
        standardHuffman.buildCodeArrayList();
        standardHuffman.printVLCTable();

    }
}
