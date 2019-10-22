package cn.yqy.apitest.utility;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Regex {

    public  static String getId(String targetData){
        String id = "";
        String p = "(.*)(.{8}-.{4}-.{4})(.*)";
        Pattern r = Pattern.compile(p);

        Matcher m = r.matcher(targetData);
        if(m.find()){
            id = m.group(2);
        }

        return id;
    }
}
