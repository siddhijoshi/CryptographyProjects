/* Author: Siddhita Joshi*/

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class LettersToTemplarAttack {
    static List<Integer> keyList;
    static String lineFormat="";

    private static void generatePermutations(String keyStr, int left, int right)
    {
        if (left == right)
        {
            keyList.add(Integer.parseInt(keyStr));
        }
        else
        {
            for (int i = left; i <= right; i++)
            {
                keyStr = swapCharacters(keyStr,left,i);
                generatePermutations(keyStr, left+1, right);
                keyStr = swapCharacters(keyStr,left,i);
            }
        }
    }

    public static String swapCharacters(String keyStr, int i, int j)
    {
        char t;
        char[] charArr = keyStr.toCharArray();
        t = charArr[i] ;
        charArr[i] = charArr[j];
        charArr[j] = t;
        return String.valueOf(charArr);
    }

    public static String decryptAndAttack(String ciphertext, int key, int len){
        HashMap<Integer,Integer> map = new HashMap<>();   // map to store shifted position and its index
        String res="";
        int k = len-1;
        int key1 = key;
        while(key1>0)
        {
            int t = key1%10;
            key1=key1/10;
            map.put(k,t-1);
            k--;
        }
        for(int i=0;i<ciphertext.length();i=i+len)
        {
            String temp = ciphertext.substring(i,i+len);
            String ans="";
            for(int j=0;j<len;j++)
            {
                ans+=temp.charAt((int)map.get(j)) + "";
            }
            res+=" " + ans;
        }
        System.out.println("KEY Generated:" + key);
        System.out.println("Plain text after decryption: " + res);
        System.out.println(lineFormat);
        String result = "KEY Generated:" + key + "\n" + "Plain text after decryption: " + res +"\n" + lineFormat + "\n";
        return result;
    }
    public static void util(){
        for(int i=0;i<100;i++)
            lineFormat+="-";
    }

    public static void main(String[] args) throws IOException {

        util();
        System.out.println("Reading ciphertext from file");
        Scanner s = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new FileReader("CipherText.txt"));
        String cipherText = null;
        System.out.println(lineFormat + "Cipher Text" + lineFormat);
        String line;
        while ((line = br.readLine()) != null) {
            cipherText = line;
        }
        System.out.println(cipherText);
        System.out.println("Please enter key length for attack");

        int len = s.nextInt();
        String initKey = "";        //generate initial key of length len.
        for(int i=1;i<=len;i++)
            initKey+=i;

        keyList=new ArrayList<>();      //contains permutations of all possible keys
        generatePermutations(initKey,0,len-1);
        BufferedWriter bw = new BufferedWriter(new FileWriter("DecryptedOutput.txt"));
        for(int k:keyList )
        {
            String output= decryptAndAttack(cipherText,k,len);
            bw.write(output);
        }
        System.out.println("Decrypted output saved to file DecryptedOutput.txt");
        br.close();
        bw.close();
        s.close();
}
}