
package findduplicatefiles;

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
    private File A = mock(File.class);
    private File B = mock(File.class);
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
       
       when(A.isFile()).thenReturn(Boolean.TRUE);
       when(A.isDirectory()).thenReturn(Boolean.FALSE);
       when(A.getName()).thenReturn("A");
       
       when(B.isFile()).thenReturn(Boolean.TRUE);
       when(B.isDirectory()).thenReturn(Boolean.FALSE);
       when(B.getName()).thenReturn("B");
       
       when(subDir1.isDirectory()).thenReturn(Boolean.TRUE);
       when(subDir1.isFile()).thenReturn(Boolean.FALSE);
       when(subDir1.getPath()).thenReturn("");
       when(subDir1.length()).thenReturn((long)1);
       File[] listSubDir1 = { A };
       when(subDir1.listFiles()).thenReturn(listSubDir1);
       
       when(subDir2.isDirectory()).thenReturn(Boolean.TRUE);
       when(subDir2.isFile()).thenReturn(Boolean.FALSE);
       when(subDir2.getPath()).thenReturn("");
       when(subDir2.length()).thenReturn((long)1);
       
       when(subDir3.isDirectory()).thenReturn(Boolean.TRUE);
       when(subDir3.isFile()).thenReturn(Boolean.FALSE);
       when(subDir3.getPath()).thenReturn("");
       when(subDir3.length()).thenReturn((long)1);
       
       try{
          fileA.createNewFile();
          FileWriter fileWritter = new FileWriter(fileA.getName(),true);
          BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
          bufferWritter.write("123");
    	  bufferWritter.close();
          
          fileB.createNewFile();
          FileWriter fileWritter2 = new FileWriter(fileB.getName(),true);
          BufferedWriter bufferWritter2 = new BufferedWriter(fileWritter2);
          bufferWritter2.write("456");
    	  bufferWritter2.close();
         
          fileC.createNewFile();
          FileWriter fileWritter3 = new FileWriter(fileC.getName(),true);
          BufferedWriter bufferWritter3 = new BufferedWriter(fileWritter3);
          bufferWritter3.write("123");
    	  bufferWritter3.close();
        }catch (Exception e)
        {}
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
    public void testGetFileConten(){
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
              
       File[] list = { A };
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
      
       when(sourceDir.length()).thenReturn((long)2);
       
       File[] list = {A, B};
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
      
       when(sourceDir.length()).thenReturn((long)2);
                                           
       File[] listSourceDir = { subDir1 , subDir2 };
       when(sourceDir.listFiles()).thenReturn(listSourceDir);
       
       File[] listSubDir1 = { A };
       when(subDir1.listFiles()).thenReturn(listSubDir1);
       
       File[] listSubDir2 = { B };
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
       
       File[] listSubDir1 = { fileA };
       when(subDir1.listFiles()).thenReturn(listSubDir1);
       
       File[] listSubDir2 = { fileB };
       when(subDir2.listFiles()).thenReturn(listSubDir2);
       
       File[] listSubDir3  = { fileC };
       when(subDir3.listFiles()).thenReturn(listSubDir3);
             
       FindDuplicateFiles fd = new FindDuplicateFiles(sourceDir);
       
       String expected = "A and C are identical\n";
       String result = fd.findDuplicate();
      
       assertEquals("Compate results", expected, result);
        
        
        
        
    }
}