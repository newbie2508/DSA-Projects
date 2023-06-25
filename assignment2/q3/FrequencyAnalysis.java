import java.util.ArrayList;

import Includes.*;

public class FrequencyAnalysis {
    //sizes of hash-tables are updated
    static final int[] hashTableSizes = {173, 6733, 100003};
    COL106Dictionary<String, Integer> dict1 = new COL106Dictionary<String, Integer>(hashTableSizes[0]);
    COL106Dictionary<String, Integer> dict2 = new COL106Dictionary<String, Integer>(hashTableSizes[1]);
    COL106Dictionary<String, Integer> dict3 = new COL106Dictionary<String, Integer>(hashTableSizes[2]);

    void fillDictionaries(String inputString) throws NullKeyException, KeyAlreadyExistException, KeyNotFoundException {
        /*
         * To be filled in by the student
         */
        String original="";
        for(int i=0;i<inputString.length();i++){
            int ascii=inputString.charAt(i);
            if((ascii>=65 && ascii<=90)){
                char s=(char)(ascii+32);
                original=original+s;
            }
            else if((ascii>=97 && ascii<=122) || inputString.charAt(i)==' '){
                original=original+inputString.charAt(i);
            }
            else{
                continue;
            }
        }
        //System.out.println(original);
        String s="";
        for(int i=0;i<original.length();i++){
            if(original.charAt(i)!=' '){
                s=s+original.charAt(i);
            }
            if(i==original.length()-1){
                //System.out.println(s+" ");
                if(s!=""){
                    if(dict1.exist(s)){
                        int value=dict1.get(s);
                        dict1.update(s, value+1);
                    }
                    else{
                        dict1.insert(s,1);
                    }
                    if(dict2.exist(s)){
                        int value=dict2.get(s);
                        dict2.update(s, value+1);
                    }
                    else{
                        dict2.insert(s,1);
                    }
                    if(dict3.exist(s)){
                        int value=dict3.get(s);
                        dict3.update(s, value+1);
                    }
                    else{
                        dict3.insert(s,1);
                    }
                    s="";
                }
            }
            else if(original.charAt(i)==' '){
                if(s!=""){ 
                    //System.out.println();
                    //System.out.print(s+" ");   
                    if(dict1.exist(s)){
                        int value=dict1.get(s);
                        dict1.update(s,value+1);
                    }
                    else{
                        //System.out.print(s);
                        dict1.insert(s,1);
                    }
                    if(dict2.exist(s)){
                        int value=dict2.get(s);
                        dict2.update(s, value+1);
                    }
                    else{
                        dict2.insert(s,1);
                    }
                    if(dict3.exist(s)){
                        int value=dict3.get(s);
                        dict3.update(s, value+1);
                    }
                    else{
                        dict3.insert(s,1);
                    }
                    s="";
                    //System.out.print(s+" ");
                }
                else{
                    continue;
                }
            }
        }
    }
    
    long[] profile() {
        /*
         * To be filled in by the student
         */
        return new long[4];
    }

    int[] noOfCollisions() {
        /*
         * To be filled in by the student
         */
        int [] output=new int [3];
        int count1=0;
        for(int i=0;i<=dict1.bucketsize-1;i++){
            if(dict1.hashTable[i]!=null){
                count1=count1+dict1.hashTable[i].size()-1;
            }
        }
        output[0]=count1;
        int count2=0;
        for(int i=0;i<=dict2.bucketsize-1;i++){
            if(dict2.hashTable[i]!=null){
                count2=count2+dict2.hashTable[i].size()-1;
            }
        }
        output[1]=count2;
        int count3=0;
        for(int i=0;i<=dict3.bucketsize-1;i++){
            if(dict3.hashTable[i]!=null){
                count3=count3+dict3.hashTable[i].size()-1;
            }
        }
        output[2]=count3;
        return output;
    }

    String helper2(String binary){
        int decimal=0;
        int yo=1;
        for(int i=binary.length()-1;i>=0;i--){
            int digit=binary.charAt(i)-'0';
            decimal=decimal+digit*yo;
            yo=yo*2;
        }
        char arr[] = { 'A', 'B', 'C', 'D', 'E', 'F' };
        String hexnumber = "";
        if(decimal>=10){
            hexnumber=hexnumber+arr[decimal-10];
        }
        else{
            hexnumber=hexnumber+decimal;
        }
        return hexnumber;

    }
    String helper1(COL106Dictionary<String, Integer> help){
        String binary="";
        for(int i=0;i<=(help.bucketsize-1);i++){
            if(help.hashTable[i]!=null){
                binary=binary+"1";
            }
            else{
                binary=binary+"0";
            }
        }
        int zeroestobeadded=0;
        int tu=binary.length();
        while(tu%4!=0){
            tu++;
            zeroestobeadded++;
        }
        String tobeadded="";
        for(int i=1;i<=zeroestobeadded;i++){
            tobeadded=tobeadded+"0";
        }
        binary=tobeadded+binary;
        String tobesent="";
        String output="";
        for(int i=0;i<binary.length();i++){  
            if(i%4==0 && i!=0){
                output=output+helper2(tobesent);
                tobesent="";
                tobesent=tobesent+binary.charAt(i);
                continue;
            }
            if(i==binary.length()-1){
                tobesent=tobesent+binary.charAt(i);
                output=output+helper2(tobesent);
                tobesent="";
                continue;
            }
            tobesent=tobesent+binary.charAt(i);
        }
        return output;
        /*int tostart=0;
        for(int i=0;i<output.length();i++){
            if(output.charAt(i)=='0'){
                tostart++;
            }
            else{
                break;
            }
        }
        String newoutput="";
        for(int i=tostart;i<output.length();i++){
            newoutput=newoutput+output.charAt(i);
        }
        return newoutput;*/
    }
    String[] hashTableHexaDecimalSignature() {
        /*
         * To be filled in by the student
         */
        String [] output=new String [3];
        String hex1=helper1(dict1);
        String hex2=helper1(dict2);
        String hex3=helper1(dict3);
        output[0]=hex1;
        output[1]=hex2;
        output[2]=hex3;
        return output;
    }
    
    
    String[] distinctWords() {
        /*
         * To be filled in by the student
         */
        return dict1.keys(String.class);
    }

    Integer[] distinctWordsFrequencies() {
        /*
         * To be filled in by the student
         */
        return dict1.values(Integer.class);
    }
    /*void printmy(){
        for(int i=0;i<=dict1.bucketsize-1;i++){
            if(dict1.hashTable[i]!=null){
                for(int j=0;j<dict1.hashTable[i].size();j++){
                    System.out.print(dict1.hashTable[i].get(j).key);
                }
            }
            System.out.println();
        }
    }*/
    /*public static void main(String[] args) throws NullKeyException, KeyAlreadyExistException, KeyNotFoundException{
        FrequencyAnalysis yo=new FrequencyAnalysis();
        String s="The Indian Institute of Technology Delhi (IIT Delhi) is a public institute of technology located in New Delhi, India. It is one of the twenty-three Indian Institutes of Technology created to be Centres of Excellence for India’s training, research and development in science, engineering and technology.";
        yo.fillDictionaries(s);
        // String arr[]=yo.distinctWords();
        // for(int i=0;i<arr.length;i++){
        //     System.out.print(arr[i]+" ");
        // }
        // yo.printmy();
        String [] yup=yo.hashTableHexaDecimalSignature();
        for(int i=0;i<yup.length;i++){
            System.out.println(yup[i]);
        }
    }*/
}
