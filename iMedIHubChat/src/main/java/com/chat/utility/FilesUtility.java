/**
 * 
 */
package com.chat.utility;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author aparnareddychalla
 *
 */
public class FilesUtility {
   
	
	// convert InputStream to String
		public static String getStringFromInputStream(InputStream is) {

			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();

			String line;
			try {

				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return sb.toString();

		}
	
	public static File creatFileWithFileName(String file) throws IOException {
		File filePath = new File(file);
		if(!filePath.exists()) {
			if(filePath.createNewFile()) {
				System.out.println("File Created");
			}
		}
		return filePath;
	}
	
	
	public static Boolean isFileExists(String file) throws IOException{
		File filePath = new File(file);
		
		return filePath.exists();
	}
	
	/**

     * List all the files and folders from a directory

     * @param directoryName to be listed

     */

    public static File listFilesAndFolders(String directoryName,String userDirctory){

        File directory = new File(directoryName);

        //get all the files from a directory
        File userPath = null;

        File[] fList = directory.listFiles();

        for (File file : fList){
        if(file.getName().equalsIgnoreCase(userDirctory)) {
        	userPath = file;
        }
            System.out.println(file.getName());
        }
        
        return userPath;

    }

    /**

     * List all the files under a directory

     * @param directoryName to be listed

     */

    public void listFiles(String directoryName){

        File directory = new File(directoryName);

        //get all the files from a directory

        File[] fList = directory.listFiles();
        

        for (File file : fList){

            if(file.isDirectory()) {
            	File[] fsubList = file.listFiles();
            	for(File subfile : fsubList) {
            	if (file.isFile()){

                    System.out.println(file.getName());

                }	
            	}
            }

        }

    }

    /**

     * List all the folder under a directory

     * @param directoryName to be listed

     */

    public void listFolders(String directoryName){

        File directory = new File(directoryName);

        //get all the files from a directory

        File[] fList = directory.listFiles();

        for (File file : fList){

            if (file.isDirectory()){

                System.out.println(file.getName());

            }

        }

    }

    /**

     * List all files from a directory and its subdirectories

     * @param directoryName to be listed

     */

    public void listFilesAndFilesSubDirectories(String directoryName){

        File directory = new File(directoryName);

        //get all the files from a directory

        File[] fList = directory.listFiles();

        for (File file : fList){

            if (file.isFile()){

                System.out.println(file.getAbsolutePath());

            } else if (file.isDirectory()){

                listFilesAndFilesSubDirectories(file.getAbsolutePath());

            }

        }

    }
}
