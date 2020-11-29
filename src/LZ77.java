import java.util.*;



public  class LZ77{
    ArrayList <Tuple> tags=new ArrayList <Tuple>();
    String searchWindow = new String();
    String lookAheadWindow = new String();

    Tuple tuple= new Tuple();

    void compress(String data, int searchWindowLen, int lookAheadWindowLen) {
        int curIdx = 0;
        int searchWindowSt;
        while (curIdx < data.length()) {
            searchWindowSt = (curIdx < searchWindowLen ? 0 : curIdx - searchWindowLen); // deciding the start of the search window

            if (curIdx == 0) { //Setting search window
                searchWindow = "";
            } else {
                searchWindow = data.substring(searchWindowSt,
                        curIdx);
            }
            //Finding match

            int matchLen = 1;
            String target = data.substring(curIdx, curIdx + matchLen);
            if (searchWindow.lastIndexOf(target) == -1) { //match not found for one symbol
                tuple = new Tuple(0, 0, data.charAt(curIdx));
                tags.add(tuple);
            } else {
                while(searchWindow.lastIndexOf(target) != -1 && matchLen<=lookAheadWindowLen && curIdx+matchLen<data.length()){
                    matchLen ++;
                    target=data.substring(curIdx, curIdx + matchLen);

                }
                matchLen--;
                int matchIdx = searchWindow.indexOf(data.substring(curIdx,curIdx+matchLen));
                curIdx  += matchLen;

                int offSet ;
                if(curIdx>= searchWindowLen + matchLen){
                    offSet = searchWindowLen - matchIdx;
                }else{
                    offSet = curIdx - matchIdx - matchLen;
                }


                tuple = new Tuple(offSet,matchLen,data.charAt(curIdx));
                tags.add(tuple);

            }

            curIdx++;

        }
    }

    void printTags(){
        System.out.println("Compressed Tags:");
        for(Tuple t : tags){
            System.out.println("< "+t.offset+", "+t.length+", "+t.nextChar+" >");
        }
    }

    public static void main(String []args){
        LZ77 lz77Compress = new LZ77();
        Scanner sc = new Scanner(System.in);
        String data= sc.next();
        int searchWindowLen = sc.nextInt();
        int lookAheadWindowLen = sc.nextInt();
        lz77Compress.compress(data,searchWindowLen,lookAheadWindowLen);
        lz77Compress.printTags();

    }
}

