//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P04 File Explorer - Using recursion to list and sort through file folders
// Course:   CS 300 Fall 2020
//
// Author:   Dylan Krejci
// Email:    dkrejci@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         Ji Lau - helped on Piazza to resolve deepFolderContents recursion problem
//                  Michelle Jensen - suggested a fix for my searchByName method, path wasn't reporting properly
//                  Hobbes LeGault - helped with exception handling in searchByName method

// Online Sources:  https://docs.oracle.com/javase/7/docs/api/java/io/File.html
//						provided information on the File class and related methods, such as .list()
//
///////////////////////////////////////////////////////////////////////////////

import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class FileExplorerTester {

  /**
   * Tester method for listContents method
   * @param folder to be searched through and contents of listed
   * @return false if any tests fail or exceptions aren't thrown when needed, true if all tests pass
   */
  public static boolean testListContents(File folder) {
	try {
      // Scenario 1 - list the basic contents of the cs300 folder
	  ArrayList<String> listContent = FileExplorer.listContents(new File("cs300"));
	  // expected output content
	  String[] contents = new String[] {"grades", "lecture notes", "programs", 
	      "quizzes preparation", "reading notes", "syllabus.txt", "todo.txt"};
	  List<String> expectedList = Arrays.asList(contents);
	  // check the size and the contents of the output
	  if (listContent.size() != 7) {
		System.out.println("Problem detected; cs300 folder must contain 7 elements.");
		return false;
	  }
	  for (int i = 0; i < expectedList.size(); i++) {
		if (!listContent.contains(expectedList.get(i))) {
		  System.out.println("Problem detected: " + expectedList.get(i)
		      + " is missing from the output of the list contents of the cs300 folder.");
		  return false;
		}
	  }
	  
	  // Scenario 2 - list the contents of the grades folder
	  File f = new File(folder.getPath() + File.separator + "grades");
	  listContent = FileExplorer.listContents(f);
	  if (listContent.size() != 0) {
		System.out.println("Problem detected: grades folder must be empty.");
		return false;
	  }
	  
	  // Scenario 3 = list the contents of the po2 folder
	  f = new File(folder.getPath() + File.separator + "programs"
	      + File.separator + "p02");
	  listContent = FileExplorer.listContents(f);
	  if (listContent.size() != 1 || !listContent.contains("WisconsinPrairie.java")) {
		System.out.println("Problem detected: p02 folder must contain only one file named "
		    + "WisconsinPrairie.java.");
		return false;
	  }
	  
	  // Scenario 4 - Try to list the contents of a file
	  f = new File(folder.getPath() + File.separator + "todo.txt");
	  try {
		listContent = FileExplorer.listContents(f);
		System.out.println("Problem detected: Your FileExplorer.listContents() must "
			+ "throw a NotDirectoryException if it is provided an input which is not"
			+ "a directory");
		return false;
	  } catch (NotDirectoryException e) { // catch only the expected exception
		// Expected behavior - no problem detected
	  }
	  
	  // Scenario 5 Try to list the contents of not found directory/file
	  f = new File(folder.getPath() + File.separator + "music.txt");
	  try {
	    listContent = FileExplorer.listContents(f);
	    System.out.println("Problem detected: Your FileExplorer.listContents() must "
	        + "throw a NotDirectoryException if the provided File does not exist.");
	        return false; 
	  } catch (NotDirectoryException e) {
		// catch only the expected exception to be thrown - no problem detected
	  }
	} catch (Exception e) {
	  System.out.println("Problem detected: Your FileExplorer.listContents() has thrown "
		  + "a non expected exception."); e.printStackTrace();
	  return false;
	}
	return true;
  }
  
  /**
   * Tester method for deepListContents - base case only
   * @param f file to be searched through and have all contents listed
   * @return false if any tests fail or exceptions aren't thrown when needed, true if all tests pass
   */
  public static boolean testDeepListBaseCase(File f) {
	// Only needs to check that the base case is correct
	// Call on a folder with no sub-folders, so no recursion needed
	  try {
		ArrayList<String> listContent = FileExplorer.deepListContents(new File("cs300" + File.separator + "grades"));
		if (listContent.size() != 0) {
		  System.out.println("Problem detected; grades folder must be empty.");
		  return false;
		}
	} catch (NotDirectoryException e) {
		e.printStackTrace();
	}
    return true;
  }
  
  /**
   * Tester method for deepListContents - with recursion
   * @param f file to be searched through and have all contents listed
   * @return false if any tests fail or exceptions aren't thrown when needed, true if all tests pass
   */
  public static boolean testDeepListRecursiveCase(File f) {
	// Checks method WITH recursion
	try {
	  // Scenario 1 - Accessing every directory, folder, and file in cs300 folder
	  ArrayList<String> listContent = FileExplorer.deepListContents(new File("cs300"));
	  // Checks that the size of deepListContents for cs300 is as expected
	  if (listContent.size() != 33) {
		  System.out.println("Scenario 1: Size = " + listContent.size());
		  return false;
	  }
	  String[] expectedOutput = {"grades", "lecture notes", "unit1", "ExceptionHandling.txt", "proceduralProgramming.txt", 
			  "UsingObjectsAndArrayLists.txt", "unit2", "AlgorithmAnalysis.txt", "Recursion.txt", "programs", "p01", 
			  "COVIDTracker.java", "COVIDTrackerTester.java", "p02", "WisconsinPrairie.java", "p03", "Benchmarker.java", 
			  "ComparisonMethods.java", "writeUps", "Program01.pdf", "Program02.pdf", "Program03.pdf", "quizzes preparation", 
			  "cquiz1", "codeSamples.java", "outline.txt", "reading notes", "zyBooksCh1.txt", "zyBooksCh2.txt", "zyBooksCh3.txt", 
			  "zyBooksCh4.txt", "syllabus.txt", "todo.txt"};
	  // Checks that the content and order of elements is as expected
	  for (int i = 0; i < expectedOutput.length; i++) {
		if (!listContent.get(i).equals(expectedOutput[i])) {
	      System.out.println("Scenario 1: Values at " + i + " do not match.");
		  return false;
		}
	  }
	  
	  // Scenario 2 - Accessing an inner directory - programs
	  listContent = FileExplorer.deepListContents(new File("cs300" + File.separator + "programs"));
	  // Checks that the size of deepListContents for programs is as expected
	  if (listContent.size() != 12) {
		  System.out.println("Scenario 2: Size = " + listContent.size());
		  return false;
	  }
	  String[] expectedOutput2 = {"p01", "COVIDTracker.java", "COVIDTrackerTester.java", "p02", "WisconsinPrairie.java", "p03", 
			  "Benchmarker.java", "ComparisonMethods.java", "writeUps", "Program01.pdf", "Program02.pdf", "Program03.pdf"};
	  // Checks that the content and order of elements is as expected
	  for (int i = 0; i < expectedOutput2.length; i++) {
		  if (!listContent.get(i).equals(expectedOutput2[i])) {
			System.out.println("Scenario 2: Values at " + i + " do not match.");
			return false;
		  }
	  }
	  
	  // Scenario 3 - Accessing an innermost directory - cquiz1
	  listContent = FileExplorer.deepListContents(new File("cs300" + File.separator + "quizzes preparation" + File.separator + "cquiz1"));
	  // Checks that the size of deepListContents for cquiz1 is as expected
	  if (listContent.size() != 2) {
		System.out.println("Scenario 3: Size = " + listContent.size());
		return false;
	  }
	  String[] expectedOutput3 = {"codeSamples.java", "outline.txt"};
	  // Checks that the content and order of elements is as expected
	  for (int i = 0; i < expectedOutput3.length; i++) {
		if (!listContent.get(i).equals(expectedOutput3[i])) {
		  System.out.println("Scenario 3: Values at " + i + " do not match.");
	      return false;
		}
	  }
	  
	  // Scenario 4 - Try to call method on a file - Recursion.txt
	  try {
	    listContent = FileExplorer.deepListContents(new File("cs300" + File.separator + "lecture notes" + File.separator + "unit2" + File.separator + "Recursion.txt"));
	    System.out.println("Problem detected: Your FileExplorer.listContents() must "
		  		+ "throw a NotDirectoryException if it is provided an input which is not"
				+ "a directory");
	    return false;
	  } catch (NotDirectoryException e) { // catch only the expected exception
			// Expected behavior - no problem detected
	  }
	  
	  // Scenario 5 - Try to call method on a not found directory/file
	  try {
		listContent = FileExplorer.deepListContents(new File("cs300" + File.separator + "methods"));
		System.out.println("Problem detected: Your FileExplorer.listContents() must "
		  		+ "throw a NotDirectoryException if input does not exist");
	    return false;
	  } catch (NotDirectoryException e) { // catch only the expected exception
		 	// Expected behavior - no problem detected
	  }  
 	} catch (Exception e) {
 	  System.out.println("Problem detected: Your FileExplorer.listContents() has thrown "
 				+ "a non expected exception."); e.printStackTrace();
 	  return false;
	}
	return true;
  }
  
  /**
   * Tester method for searchByName method
   * @param f file to be searched and filtered through
   * @return false if any tests fail or exceptions aren't thrown when needed, true if all tests pass
 * @throws NotDirectoryException 
   */
  public static boolean testSearchByName(File f) throws NotDirectoryException {
    // Scenario 1 - One match, no recursion needed to find it
	try {
	  String path = FileExplorer.searchByName(f, "cs300");
	  if (!path.equals("cs300")) {
		System.out.println("Error 1");
		return false;
	  }
	} catch (NoSuchElementException e) {
	  System.out.println("NoSuchElementException occured."); 
	}
	
	// Scenario 2 - One match, recursion needed to find it
	try {
	  String path = FileExplorer.searchByName(f, "grades");
	  if (!path.equals("grades")) {
	    System.out.println("Error 2 " + path);
		return false;
	  }
	} catch (NoSuchElementException e) {
		  System.out.println("NoSuchElementException occured."); 
	}
	
	// Scenario 3 - No matches
	try {
		String path = FileExplorer.searchByName(f, "methods");
	    System.out.println("Problem detected: Your FileExplorer.searchByName() must "
	        + "throw a NoSuchElementException if the provided File does not exist.");
	  } catch (NoSuchElementException e) {
		// catch only the expected exception to be thrown - no problem detected
	  }
	
	return true;
  }
  
  /**
   * Tester method for searchByKey - base case only
   * @param f file to be searched and filtered through
   * @return false if any tests fail or exceptions aren't thrown when needed, true if all tests pass
   */
  public static boolean testSearchByKeyBaseCase(File f) {
	// Only checks that the base case is correct
	// Scenario 1 - Searching for "cs" - only 1 occurrence
	ArrayList<String> outputList = FileExplorer.searchByKey(f, "cs");
	if (outputList.size() != 1) {
	  System.out.println("Error 1 " + outputList.size());
	  return false;
	}
	// Scenario 2 - Searching for ".txt" - multiple occurrences
	ArrayList<String> outputList2 = FileExplorer.searchByKey(f, ".txt");
	if (outputList2.size() != 12) {
	  System.out.println("Error 2 " + outputList2.size());
	  return false;
	}
	
	// Scenario 3 - Searching for "hello" - no occurrences
	ArrayList<String> outputList3 = FileExplorer.searchByKey(f, "hello");
	if (outputList3.size() != 0) {
	  System.out.println("Error 3");
	  return false;
	}
	return true;
  }
  
  /**
   * Tester method for searchBySize method
   * @param f file to be searched and filtered through
   * @return false if any tests fail or exceptions aren't thrown when needed, true if all tests pass
   */
  public static boolean testSearchBySize(File f) {
	// Scenario 1 - Every file included
	ArrayList<String> outputList = FileExplorer.searchBySize(f, 0, 1000);
	if (outputList.size() != 33) {
      System.out.println("Error 1 " + outputList.size());
	  return false;
	}
	
	// Scenario 2 - Some files included
	ArrayList<String> outputList2 = FileExplorer.searchBySize(f, 0, 1);
	if (!(outputList2.size() <= 33) || !(outputList.size() >= 0)) {
	  System.out.println("Error 2");
      return false;
	}
	
	// Scenario 3 - No files included
	ArrayList<String> outputList3 = FileExplorer.searchBySize(f, -5, -1);
	if (!(outputList3.size() == 0)) {
	  System.out.println("Error 3");
	  return false;
	}
	return true;
  }
 
  /**
   * Call all test methods
   * @param args
   * @throws NotDirectoryException
   * @throws NoSuchElementException
   */
  public static void main(String[] args) throws NotDirectoryException, NoSuchElementException {
    System.out.println("testListContents: " + testListContents(new File("cs300")));
    System.out.println("testDeepListBaseCase: " + testDeepListBaseCase(new File("cs300")));
    System.out.println("testDeepListRecursiveCase: " + testDeepListRecursiveCase(new File("cs300")));
    System.out.println("testSearchByName: " + testSearchByName(new File("cs300")));
    System.out.println("testSearchByKey: " + testSearchByKeyBaseCase(new File("cs300")));
    System.out.println("testSearchBySize: " + testSearchBySize(new File("cs300")));
  }

}
