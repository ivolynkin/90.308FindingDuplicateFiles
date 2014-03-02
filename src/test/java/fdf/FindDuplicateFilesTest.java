
package fdf;

import java.io.*;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.FileWriter;
import java.io.BufferedWriter;


/**
 *
 * @author Ivan Volynkin
 * Set of tests for TDD exercise.
 */
public class FindDuplicateFilesTest {
    
    private File sourceDir = mock(File.class);
    private File subDir1 = mock(File.class);
    private File subDir2 = mock(File.class);
    private File subDir3 = mock(File.class);
    private File fileA = new File("A");
    private File fileB = new File("B");
    private File fileC = new File("C");
   
    
    public FindDuplicateFilesTest() {
    }
    
    @Before
    public void setUp() {
       when(sourceDir.isDirectory()).thenReturn(Boolean.TRUE);
       when(sourceDir.isFile()).thenReturn(Boolean.FALSE);
       when(sourceDir.getPath()).thenReturn("");
        
       when(subDir1.isDirectory()).thenReturn(Boolean.TRUE);
       when(subDir1.isFile()).thenReturn(Boolean.FALSE);
       when(subDir1.getPath()).thenReturn("");
       when(subDir1.length()).thenReturn((long)1);
              
       when(subDir2.isDirectory()).thenReturn(Boolean.TRUE);
       when(subDir2.isFile()).thenReturn(Boolean.FALSE);
       when(subDir2.getPath()).thenReturn("");
       when(subDir2.length()).thenReturn((long)1);
       
       when(subDir3.isDirectory()).thenReturn(Boolean.TRUE);
       when(subDir3.isFile()).thenReturn(Boolean.FALSE);
       when(subDir3.getPath()).thenReturn("");
       when(subDir3.length()).thenReturn((long)1);
       
       
    }
   
    
    
    private void setFileContent(File file, String content){
        try{
          file.createNewFile();
          FileWriter fileWritter = new FileWriter(file.getName(),false);
          BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
          bufferWritter.write(content);
    	  bufferWritter.close();
         
        }catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        } 
    }
    
    @After
    public void tearDown() {
        System.gc();
        fileA.delete();
        fileB.delete();
        fileC.delete();
    }

   /*
    * Test for getFileConten
    */
    @Test
    public void testGetFileContent(){
        File[] listSubDir1 = { fileA };
        when(subDir1.listFiles()).thenReturn(listSubDir1);
        
        setFileContent(fileA, "123");
        FindDuplicateFiles findDF = new  FindDuplicateFiles(subDir1);
        String result = findDF.getFileContent(fileA);
        String expected = "123";    
        assertEquals("Compare File content", result, expected);
     }
    
    /*
     * Test for equalFlieConten
     */
    
    @Test
    public void testEqualFileContent(){
        setFileContent(fileA, "123");
        setFileContent(fileB, "456");
        setFileContent(fileC, "123");
        
        File[] listSubDir1 = { fileA, fileB, fileC };
        when(subDir1.listFiles()).thenReturn(listSubDir1);
        
        FindDuplicateFiles findDF = new  FindDuplicateFiles(subDir1);
        assertTrue("File A and File C are equal", findDF.equalFileContent(fileA, fileC));
        assertFalse("File A and file B are not equal", findDF.equalFileContent(fileA, fileB));
    }
    
   /*
    * Test with one mocked file in mocked sourece directory
    */
    
    @Test
    public void testMockOneEmptyFilesSoureDirectory() {
       
       when(sourceDir.length()).thenReturn((long)1);
       
       setFileContent(fileA, "");
       
       File[] list = { fileA };
       when(sourceDir.listFiles()).thenReturn(list);
       FindDuplicateFiles fd = new FindDuplicateFiles(sourceDir);
             
       String expected = "No files are duplicated";
       String result = fd.findDuplicate();
       assertEquals("Compare results", expected, result);
    }
    
     /*
    * Test with two mocked files in mocked sourece directory
    */
    
    @Test
    public void testMockTwoEmptyFilesSoureDirectory() {
        
       setFileContent(fileA, "");
       setFileContent(fileB, "");
      
       when(sourceDir.length()).thenReturn((long)2);
       
       File[] list = {fileA, fileB};
       when(sourceDir.listFiles()).thenReturn(list);
       FindDuplicateFiles fd = new FindDuplicateFiles(sourceDir);
              
       String expected = "A and B are identical\n";
       String result = fd.findDuplicate();
       assertEquals("Compare results", expected, result);
    }
    
     /*
    * Test with two mocked files in mocked sub directories
    */
    
    @Test
    public void testMockTwoEmptyFilesSubDirectories(){
        
       setFileContent(fileA, "");
       setFileContent(fileB, "");
      
       when(sourceDir.length()).thenReturn((long)2);
                                           
       File[] listSourceDir = { subDir1 , subDir2 };
       when(sourceDir.listFiles()).thenReturn(listSourceDir);
       
       File[] listSubDir1 = { fileA };
       when(subDir1.listFiles()).thenReturn(listSubDir1);
       
       File[] listSubDir2 = { fileB };
       when(subDir2.listFiles()).thenReturn(listSubDir2);
              
       FindDuplicateFiles fd = new FindDuplicateFiles(sourceDir);
       
       String expected = "A and B are identical\n";
       String result = fd.findDuplicate();
       assertEquals("Compare results", expected, result);
    }
    
     /*
    * Test with three files in mocked sub directories
    */
    
    
    @Test
    public void testMockThreeFiles(){
      
       when(sourceDir.length()).thenReturn((long)3);
       
       File[] listSourceDir = { subDir1 , subDir2, subDir3 };
       when(sourceDir.listFiles()).thenReturn(listSourceDir);
       
       setFileContent(fileA,"123");
       File[] listSubDir1 = { fileA };
       when(subDir1.listFiles()).thenReturn(listSubDir1);
       
       
       setFileContent(fileB,"456");
       File[] listSubDir2 = { fileB };
       when(subDir2.listFiles()).thenReturn(listSubDir2);
       
       setFileContent(fileC,"123");
       File[] listSubDir3  = { fileC };
       when(subDir3.listFiles()).thenReturn(listSubDir3);
             
       FindDuplicateFiles fd = new FindDuplicateFiles(sourceDir);
       
       String expected = "A and C are identical\n";
       String result = fd.findDuplicate();
      
       assertEquals("Compate results", expected, result);
        
        
        
        
    }
}