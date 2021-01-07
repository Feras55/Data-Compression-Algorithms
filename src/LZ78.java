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



    }


    String decompress(){
    String data = new String();
    dictionary.clear();
    dictionary.add("----"); //Setting up dictionary
        for (Pair p :
                tags) {
            String symbol = new String();
            int curIdx = p.indexInDictionary;
            if(curIdx!=0){
                symbol = dictionary.get(curIdx); //getting the symbol from the dictionary
            }
            symbol+=p.nextSymbol; //concatenating the next char to the symbol
            dictionary.add(symbol); //adding the new symbol to the dictionary
            data+=symbol; ///adding the symbol to the raw data

        }
    return data;
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
        lz78.printDictionary();
        lz78.printTags();
        System.out.println("NOW IT IS TIME TO DECOMPRESS");
        System.out.println(lz78.decompress());


    }
}