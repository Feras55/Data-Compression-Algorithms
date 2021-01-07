import java.util.*;



public  class LZ77{
    ArrayList <Tuple> tags=new ArrayList <Tuple>();
    String searchWindow = new String();


    Tuple tuple= new Tuple();

    void compress(String data, int searchWindowLen, int lookAheadWindowLen) {
        int curIdx = 0; ///current index in the data
        int searchWindowSt; ///beginning of the search window, considering having small start window size, thus might not at the beginning of data
        while (curIdx < data.length()) {
            searchWindowSt = (curIdx < searchWindowLen ? 0 : curIdx - searchWindowLen); // deciding the start of the search window
            /// if the search window starts from the beginning, start is 0, else curIdx - searchwindowLen
            if (curIdx == 0) { //Setting search window
                searchWindow = "";
            } else {
                searchWindow = data.substring(searchWindowSt,
                        curIdx);
            }
            //Finding match

            int matchLen = 1;  ///match length of one character
            String target = data.substring(curIdx, curIdx + matchLen); ///one character
            if (searchWindow.lastIndexOf(target) == -1) { //match not found for one symbol
                tuple = new Tuple(0, 0, data.charAt(curIdx));
                tags.add(tuple);
            } else {
                while(searchWindow.lastIndexOf(target) != -1 && matchLen<=lookAheadWindowLen && curIdx+matchLen<data.length()){
                    matchLen ++;
                    target=data.substring(curIdx, curIdx + matchLen);

                }
                matchLen--; ///the extra character didn't match, reset to the last longest match size
                int matchIdx = searchWindow.indexOf(data.substring(curIdx,curIdx+matchLen)); ///matching position
                curIdx  += matchLen;

                int offSet ;
                if(curIdx>= searchWindowLen + matchLen){
                    offSet = searchWindowLen - matchIdx;     ///considering the case where the searchwindow Start doesn't start from the beginning
                }else{
                    offSet = curIdx - matchIdx - matchLen; /// if start of the search window is at the start of the raw data
                }


                tuple = new Tuple(offSet,matchLen,data.charAt(curIdx));
                tags.add(tuple);

            }

            curIdx++;

        }
    }

    String decompress(){
        int offSet, symbolLen;
        String data = new String();
        for (Tuple t:
                tags) {
            offSet = t.offset;
            symbolLen=t.length;
            if(offSet == 0 && symbolLen == 0) //first char
            {
                //Do nothing
            }
            else{
                int curIdx = data.length() - offSet;  ///
                for (int i = 0; i < symbolLen; i++) {
                    data+=data.charAt(curIdx+i);
                }
            }
            data+=t.nextChar;
        }
        return data;

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
        System.out.println("NOW IT IS TIME TO DECOMPRESS");
        System.out.println(lz77Compress.decompress());

    }
}

