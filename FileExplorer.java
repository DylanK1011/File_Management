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
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FileExplorer {
  /**
   * Returns a list of the names of all files and directories in the given folder.
   * @param currentFolder File folder that contains files to be printed
   * @return list of file names in the given folder
   * @throws NotDirectoryException with a descriptive error message if the provided 
   *         currentFolder does not exist of if it is not a directory
   */
  public static ArrayList<String> listContents(File currentFolder) throws NotDirectoryException {
    if (!currentFolder.isDirectory()) {
      throw new NotDirectoryException(currentFolder + " is not a directory. NotDirectoryException was thrown.");
    }
    
    ArrayList<String> folderContents = new ArrayList<String>();
    String[] fileNamesArray = currentFolder.list();
    
    for (int i = 0; i < fileNamesArray.length; i++)
      folderContents.add(fileNamesArray[i]);
    return folderContents;
  }
  
  /**
   * Recursive method that lists the names of all the files (not directories)
   * in the given folder and its sub-folders
   * @param currentFolder File folder that contains files (and sub-files) to be printed
   * @return list of all file names from the folder
   * @throws NotDirectoryException with a descriptive error message if the provided 
   *         currentFolder does not exist or if it is not a directory
   */
  public static ArrayList<String> deepListContents(File currentFolder) throws NotDirectoryException {
	if (!currentFolder.isDirectory())
	  throw new NotDirectoryException(currentFolder + " is not a directory. NotDirectoryException was thrown.");
	ArrayList<String> deepFolderContents = new ArrayList<String>();
	String[] fileNamesArray = currentFolder.list();	

	// Base case: No folders within currentFolder, so no recursion required
	if (fileNamesArray.length == 0) {
	  return deepFolderContents;
	}
	
	// Recursive case: subfolders
    for (int i = 0; i < fileNamesArray.length; i++) {
      deepFolderContents.add(fileNamesArray[i]);
      File f = new File(currentFolder + File.separator + fileNamesArray[i]);
      if (f.isDirectory())
        deepFolderContents.addAll(deepListContents(f));
    }
	return deepFolderContents;
  }
  
  /**
   * Searches the given folder and all of its subfolders for an exact match
   * to the provided fileName
   * @param currentFolder File folder to be searched
   * @param fileName name to be searched for
   * @return String path to the file, if it exists
   * @throws NoSuchElementException with a descriptive error message if the
   * 		 search operation returns with no results found
 * @throws NotDirectoryException 
   */
  public static String searchByName(File currentFolder, String fileName) throws NoSuchElementException, NotDirectoryException {
	ArrayList<String> contents;
	try {
		contents = deepListContents(currentFolder);
	} catch (NotDirectoryException e) {
		if (currentFolder.toString().equals(fileName))
			return currentFolder.toString();
		throw new NoSuchElementException();
	}
	if (currentFolder.toString().equals(fileName))
		return currentFolder.toString(); 
	for (String s : contents)
		if (s.equals(fileName))
			return s;
	throw new NoSuchElementException();
  }

  
  /**
   * Searches the given folder and its subfolders
   * for all files that contain the given key in part of their name.
   * @param currentFolder File folder to be searched
   * @param key string of characters to be looked for in file names
   * @return ArrayList of all the names of files that match and an empty ArrayList
   *         when the operation returns with no results found
   */
  public static ArrayList<String> searchByKey(File currentFolder, String key) {
	  ArrayList<String> filesWithKey = new ArrayList<String>();
	  ArrayList<String> contents;
	  try {
		  contents = deepListContents(currentFolder);
	  } catch (NotDirectoryException e) {
		  if (currentFolder.toString().contains(key))
			  filesWithKey.add(currentFolder.toString());
    	  return filesWithKey; 
      }
	  if (currentFolder.toString().contains(key))
		  filesWithKey.add(currentFolder.toString());
	  for (String s : contents)
		  if (s.contains(key))
			  filesWithKey.add(s);
	  return filesWithKey;
  }
  
  /**
   * Search the given folder and its subfolders for
   * 	all files whose size is within the given max and min values, inclusive.
   * @param currentFolder File folder to be searched
   * @param sizeMin minimum storage size of a file allowed
   * @param sizeMax maximum storage size of a file allowed
   * @return ArrayList<String> of files within the range of sizes
   */
  public static ArrayList<String> searchBySize(File currentFolder, long sizeMin, long sizeMax) {
	  ArrayList<String> filesWithinSizeRange = new ArrayList<String>();
	  ArrayList<String> contents;
	  try {
		  contents = deepListContents(currentFolder);
	  } catch (NotDirectoryException e) {
		  if (currentFolder.toString().length() >= sizeMin && currentFolder.toString().length() <= sizeMax)
			  filesWithinSizeRange.add(currentFolder.toString());
    	  return filesWithinSizeRange; 
      }
	  for (String s : contents)
		  if (s.length() >= sizeMin && s.length() <= sizeMax)
			  filesWithinSizeRange.add(s);
	  return filesWithinSizeRange;
  }
}
