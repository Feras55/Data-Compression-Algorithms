import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        LZ77 lz77Compress = new LZ77();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Data");
        String data= sc.next();
        System.out.println("Enter search window length, Look ahead window length");
        int searchWindowLen = sc.nextInt();
        int lookAheadWindowLen = sc.nextInt();
        lz77Compress.compress(data,searchWindowLen,lookAheadWindowLen);
        lz77Compress.printTags();
        System.out.println("NOW IT IS TIME TO DECOMPRESS");
        System.out.println(lz77Compress.decompress());
        System.out.println("LZ78 COMPRESSION");
        LZ78 lz78 = new LZ78();
        System.out.println("Enter Data");
        data = sc.next();
        lz78.compress(data);
        lz78.printDictionary();
        lz78.printTags();
        System.out.println("NOW IT IS TIME TO DECOMPRESS");
        System.out.println(lz78.decompress());
        System.out.println("STANDARD HUFFMAN COMPRESSION");
        Standard_Huffman standardHuffman = new Standard_Huffman();
        standardHuffman.takeInput();
        standardHuffman.buildCodeArrayList();
        standardHuffman.printVLCTable();
    }
}
