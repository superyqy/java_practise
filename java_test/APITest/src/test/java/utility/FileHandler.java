package utility;


import java.io.*;
import java.util.Map;
import java.util.HashMap;


public class FileHandler {

    public boolean checkFileExistence(String filePath){
        boolean isExist = false;

        File f = new File(filePath);
        if(f.exists()){
            isExist = true;
        }

        return isExist;
    }

    public boolean checkPathExistence(String path){
        boolean isExist = true;

        File f = new File(path);
        if(!f.exists()&& !f.isDirectory()){
            isExist = false;
        }

        return isExist;
    }

    public void createFolder(String path){
        if(! checkPathExistence(path)){
            File f = new File(path);
            f.mkdir();
        }
    }

    //递归删除目录
    public boolean deletePath(File dir){
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deletePath(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public Map<String, String> readFile(String filePath)throws IOException{
        Map<String, String> result = new HashMap<>();
        BufferedReader reader = null;
        File f = new File(filePath);

        if(checkFileExistence(filePath)){
            try {
                reader = new BufferedReader(new FileReader(f));
                int line = 0;
                String tempString = null;
                while ((tempString = reader.readLine())!=null){
                    result.put(String.valueOf(line), tempString);
                    line ++;
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                reader.close();
            }
        }

        return result;
    }

    public void writeFile(String filePath, String content, boolean isAppend)throws IOException{
        FileWriter writer = null;

        try{
            if(!isAppend) {
                writer = new FileWriter(filePath);
            }else{
                writer = new FileWriter(filePath,true);  //追加信息
            }
            writer.write(content+"\r");
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            writer.close();
        }

    }


    public void createFile(String filePath){
        if(! checkFileExistence(filePath)){
            File f = new File(filePath);
            try{
                f.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void deleteFile(String filePath){
        File f = new File(filePath);
        f.deleteOnExit();
    }

}
