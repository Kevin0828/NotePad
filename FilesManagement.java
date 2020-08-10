package NotesPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

public class FilesManagement {


   private final String PATH = "/Users/kevinfontela/eclipse-workspace/NotesApp/src/NotePadUsersSavedNotes/";


   public FilesManagement()
   {

   }




   /**
    * Creates a new File into the directory the user specified at the beginning of installation
    * 
    * @param fileName name of the file user wanted to create.
    */
   public void createFile(String fileName)
   {
      File file =  new File(PATH + fileName);
      try
      {
         if(file.exists())
         {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error creating file");
            alert.setContentText("File name \"" + fileName.replace(".txt", "") + "\" already exists");
            alert.showAndWait();
         }
         else
         {
            file.createNewFile();
         }
      }
      catch (IOException e) 
      {
         e.printStackTrace();
      } 
   }



   /**
    * The user will get a GUI prompt message asking for file name desired
    * 
    * @return the name of the file desired.
    */
   public String setUpFileName()
   {
      String fileName = "";

      TextInputDialog dialog = new TextInputDialog();

      dialog.setResizable(false);
      dialog.setTitle("NotePad");
      dialog.setHeaderText("Create new file");
      dialog.setContentText("Please enter the name of the file. :");
      dialog.showAndWait();

      if(dialog.getResult() != null)
      {
         fileName += dialog.getResult();

         dialog.close();

         fileName = fileName.trim();

         if(!(fileName.contains(".txt")))
         {
            fileName += ".txt";
         }
         return  fileName;
      }
      else
      {
         return "";
      }

   }


   /**
    * Throws a gui error when creating file with empty name
    */
   public void fileCreationError()
   {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setHeaderText("File could not be created");
      alert.setContentText("File name must have at least 1 letter");
      alert.showAndWait();
   }




   /**
    * When called, it will return a string full of file names
    * 
    * @return file names inside folder
    */
   public String[] getFiles()
   {

      File folder = new File(PATH);
      File[] listOfFiles = folder.listFiles();

      final int FILES_LENGTH = listOfFiles.length;

      final String[] FILE_NAMES = new String[listOfFiles.length];

      for(int i = 0; i < FILES_LENGTH; ++i)
      {
         if(listOfFiles[i].isFile() && !listOfFiles[i].toString().equals(".txt"))
         {
            FILE_NAMES[i] = listOfFiles[i].getName();
            System.out.println(FILE_NAMES[i]);
         }
      }

      return FILE_NAMES;
   }


   /**
    * When called, it saves a sequence of strings inside the desired file
    * it will override data every time
    * 
    * @param fileName file name without any extension
    * @param userContent the text we are going to add into the file
    */
   public void saveToFile(String fileName, String userContent)
   {
      PrintWriter print = null;
      final String FILE_NAME = fileName + ".txt";


      try
      {
         print = new PrintWriter(PATH + FILE_NAME);
         print.write(userContent);

      }
      catch(FileNotFoundException e)
      {
         fileNotFound();
      }
      finally
      {
         print.close();
      }

   }


   /**
    * Access desired file an returns a full string of all data inside of it
    * 
    * @param fileName name of the file without extension, ext is added inside method
    * 
    * @return returns full string of all data inside document.
    */
   public String getFileContent(String fileName)
   {

      String fileText = "";
      Scanner scan = null;

      final String FILE_NAME = fileName + ".txt";
      try {

         File file = new File(PATH + FILE_NAME);

         scan = new Scanner(file);

         while(scan.hasNextLine())
         {
            fileText += scan.nextLine() + "\n";
         }

      } 
      catch (FileNotFoundException e) 
      {
         fileNotFound();
      }

      return fileText;
   }




   /**
    * When called it will display a message that a file was not found.
    */
   public void fileNotFound()
   {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setHeaderText("Error trying to save file");
      alert.setContentText("File may have been removed from the system.");
      alert.showAndWait();
   }



   /**
    * Deletes the file using the specified directory of it.
    * 
    * @param currentFile The directory of the file to delete
    */
   public void deleteFile(String currentFile)
   {
      final String FILE_NAME = currentFile + ".txt";

      File file = new File(PATH + FILE_NAME);

      if (file.delete()) 
      { 
         Alert alert = new Alert(AlertType.INFORMATION, "Note " + FILE_NAME + " has been deleted");
         alert.setHeaderText("The desired note has been deleted from the system");
         alert.showAndWait();
      } 
      else 
      {
         Alert alert = new Alert(AlertType.ERROR);
         alert.setHeaderText("Error trying to delete file");
         alert.setContentText("File may have been removed from the system already or moved.");
         alert.showAndWait();
      } 
   } 
}
