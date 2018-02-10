package grp.anon.ancode.text;
import java.util.ArrayList;

public class AnCodeText {
    private Integer ancodeType = 0;
    public AnCodeText(Integer ancodetype){
        ancodeType = ancodetype;
    }
    public String encrypt(String data,Integer key){
        if (ancodeType.equals(0)){
            key = keyDesigner(key);
            char[] pureData = data.toCharArray();
            char[] encrypted = new char[pureData.length];
            for (int i=0;i<pureData.length;i++){
                encrypted[i] = incrementor(pureData[i],key);
            }
            data =  String.valueOf(encrypted);
        }
        else if (ancodeType.equals(1)){
            key = keyDesigner(key);
            char[] pureData = data.toCharArray();
            char[] rawEncrypted = new char[pureData.length];
            String[] encrypted = new String[pureData.length];
            StringBuilder pureEncryptData = new StringBuilder();
            for (int i=0;i<pureData.length;i++){
                rawEncrypted[i] = incrementor(pureData[i],key);
                encrypted[i] = Long.toHexString(getASCII(rawEncrypted[i]));
                pureEncryptData.append(encrypted[i]+":");
            }
            data =  pureEncryptData.toString();
        }

        return data;
    }

    public String decrypt(String data,Integer key){
        if (ancodeType.equals(0)){
            key = keyDesigner(key);
            char[] pureData = data.toCharArray();
            char[] decrypted = new char[pureData.length];
            for (int i=0;i<pureData.length;i++){
                decrypted[i] = decrementor(pureData[i],key);
            }
            data =  String.valueOf(decrypted);
        }
        else if (ancodeType.equals(1)){
            key = keyDesigner(key);
            int fs=0,li=data.indexOf(":",fs+1);
            ArrayList<Character> pureData = new ArrayList<Character>();
            while (li != -1){
                String d = data.substring(fs,li);
                pureData.add((char)Integer.parseInt(d,16));
                fs = li+1;
                try {
                    li = data.indexOf(":",fs);
                }catch (Exception e){

                }
            }
            StringBuilder decryptedData = new StringBuilder();
            char[] decrypted = new char[pureData.size()];
            for (int i=0;i<pureData.size();i++){
                if (pureData.get(i) != ':'){
                    decrypted[i] = decrementor(pureData.get(i),key);
                }
            }
            data =  String.valueOf(decrypted);
        }
        return data;
    }

    private static char incrementor(char data,int noOfTimes) {
        for (int i = 0; i < noOfTimes; i++) {
            data = ++data;
        }
        return data;
    }

    private static char decrementor(char data,int noOfTimes){
        for (int i=0;i<noOfTimes;i++){
            data = --data;
        }
        return data;
    }

    private static Integer keyDesigner(Integer key){
        Integer finalKey = 0;
        for (int i=0;i<key.toString().length();i++){
            finalKey += Integer.parseInt(String.valueOf(key.toString().toCharArray()[i]));
        }
        finalKey += key;
        return finalKey;
    }
    private static int getASCII(char data){
        data = ++data;
        return --data;
    }

    public final static class AnCodeType{
        public final static Integer GOOD = 0;
        public final static Integer BETTER = 1;
    }
}
