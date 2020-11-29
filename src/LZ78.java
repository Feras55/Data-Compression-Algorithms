import java.util.ArrayList;
import java.util.Scanner;

public class LZ78 {
    ArrayList<String> dictionary = new ArrayList<String>();
    ArrayList<Pair> tags = new ArrayList<Pair>();

    public LZ78() {
        dictionary.add("----");
    }

    void compress(String data) {
        int curIdx = 0;
        Pair pair = new Pair();
        while (curIdx < data.length()) {
            String symbol = new String();
            symbol+=data.charAt(curIdx);
            if(dictionary.indexOf(symbol)==-1){
                pair=new Pair(0,symbol);
                tags.add(pair);
                dictionary.add(symbol);
            }else{
                int lastPrefix = dictionary.indexOf(symbol);
                while(dictionary.indexOf(symbol)!=-1 && curIdx<data.length()){
                     lastPrefix=dictionary.indexOf(symbol);
                    curIdx++;
                    if(curIdx<data.length())
                    symbol+=data.charAt(curIdx);
                }
                symbol.substring(0,symbol.length()-1);
                String nextChar = new String();
                nextChar+=symbol.charAt(symbol.length()-1);
                pair = new Pair(lastPrefix,nextChar);
                tags.add(pair);
                if(dictionary.indexOf(symbol)==-1)
                dictionary.add(symbol);
            }
            ++curIdx;
        }
        printDictionary();
        printTags();


    }

    void printDictionary(){
        int cntr=0;
        System.out.println("Index\tSymbol");
        for (String x:dictionary
        ) {
            System.out.println(cntr +"\t\t"+ x);
            cntr++;
        }

    }
    void printTags(){
        for (Pair x :
                tags) {
            System.out.println("< " + x.indexInDictionary + ", " + x.nextSymbol + " >");

        }
    }

    public static void main(String[] args) {
        LZ78 lz78 = new LZ78();
        String data = new String();
        Scanner sc = new Scanner(System.in);
        data = sc.next();
        lz78.compress(data);



    }
}