/*
 * @author Ivan Volynkin
 * Assignment Week Two - Finding duplicate files
 * 
 * Purpose:
 * Find duplicate files in a given source directory
 * and subdirectoories
 * 
 * Usage:
 * Asks user to enter directory to find duplicate files.
 * Returns names of duplicate files. 
  */
package findduplicatefiles;
import java.io.File;
import java.util.*;

public class FindDuplicateFiles {
        
    public List<File> list = new ArrayList<File>();
    
    /**
     * Creates a  FindDuplicateFiles instance
     */
    
    public FindDuplicateFiles(File directory){
          
        fillFileList(directory);
    }

    /*
     *  Fills List with files from given directory and subdirectories
     */
    
    private void fillFileList(File directory){
     
     File[] listOfFiles = directory.listFiles();
     
     for (int i = 0; i < listOfFiles.length; i++) {
         if (listOfFiles[i].isFile()) {
             list.add(listOfFiles[i]);
             
         } else if (listOfFiles[i].isDirectory()) {
             fillFileList(listOfFiles[i]);
         }
         
     }
    }
 

 /*
  * Returs content of the file 
  * @return
  */
  String getFileContent(File file){
     String str = "";
     
     try{
     Scanner sc = new Scanner(file);
     while(sc.hasNextLine()) {
           str = sc.nextLine();
      } 
     }catch(Exception e){
        
     }
      
     return str; 
 }
 
 /*
  * Compare files by content
  * @return
  */ 
 boolean equalFileContent(File file1, File file2){
     
     boolean result = false;
     
     if(getFileContent(file1).equals(getFileContent(file2))) result = true;
    
     return result;
 }
 

 /*
  * Retuns string with the names of duplicate files of "No files are duplicated"
  * @return
  */
 
 public String findDuplicate(){
     
     String result = "";
     
     for(int i =0; i< list.size(); i++){
        for(int j = i+1; j< list.size(); j++){
          
            if (equalFileContent(list.get(i), list.get(j))){
                result += list.get(i).getName()+ " and " + list.get(j).getName() + " are identical" + "\n"; 
               
                list.remove(j);
                j--;
        
             }
        }
     }
     if (result.equals("")) result = "No files are duplicated";
     
    return result;
 }
    
 
    public static void main(String[] args) {
        
        
        System.out.println("Enter directory to find duplicates");
        Scanner input = new Scanner(System.in);
        String dir = input.next();
        File initFile = new File(dir);
        FindDuplicateFiles findDP = new  FindDuplicateFiles(initFile);
        System.out.println(findDP.findDuplicate());
        
        
    }
}
