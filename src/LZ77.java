import java.util.*;

import static java.lang.Math.min;


public  class LZ77{
    ArrayList <Tuple> tags=new ArrayList <Tuple>();
    String searchWindow;
    String lookAheadWindow;

    Tuple tuple;

void compress(String data, int searchWindowLen, int lookAheadWindowLen){
    int offSet=0;
    int searchWindowSt;
    int lookAheadWindowEn;
    while(offSet<data.length()){
        searchWindowSt=(offSet<=searchWindowLen?0:offSet-searchWindowLen); // deciding the start of the search window
        lookAheadWindowEn=min(offSet+lookAheadWindowLen,data.length()); //deciding the end of the look Ahead window

        if(offSet==0){ //Setting search window
            searchWindow="";
        }else{
            searchWindow=data.substring(searchWindowSt,
                    offSet);
        }
        //Finding match

        int matchLen = 1;
        String target = data.substring(offSet,offSet+matchLen);
        if(searchWindow.lastIndexOf(target)==-1){ //match not found for one symbol
            tuple = new Tuple(0,0,data.charAt(offSet));
            tags.add(tuple);
        }
        else{
            matchLen++;
            while (matchLen<=lookAheadWindowLen){ //found match, attempting to found a longer match
                target=data.substring(offSet,offSet+matchLen);
                int matchLoc=searchWindow.lastIndexOf(target);
                if(matchLoc!=-1 && offSet+matchLen<data.length()){ //if a longer match is found and in bounds
                        matchLen++;
                }else{
                    break;
                }

            }
            matchLen--; //decreasing back to the longest matched string
            int longestMatchLoc=searchWindow.indexOf(data.substring(offSet,offSet+matchLen));
            offSet+=matchLen;

            ///Calculating the tag's offset
            int tagOffset;
//            if(offSet> (searchWindowLen + matchLen)){ ///if the search window doesn't start from the beginning of the data window
//               tagOffset = searchWindowLen - longestMatchLoc;
//            }
//            else{ ///search window starts from the beginning of the data window
//                tagOffset = offSet;
//            }
            tagOffset = (offSet < (searchWindowLen + matchLen))
                    ? offSet - longestMatchLoc - matchLen
                    :searchWindowLen - longestMatchLoc;
            tuple = new Tuple(tagOffset,matchLen,data.charAt(offSet));
            tags.add(tuple);

        }
        offSet++;



    }


    printTags();
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

}
}

