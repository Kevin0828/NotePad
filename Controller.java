package NotesPackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;


public class Controller implements Initializable{


   private String currentFile = "";

   @FXML
   private TextArea editableText;
   
   @FXML
   private TableView<String> table;

   @FXML 
   private TableColumn<String, String> noteNamesColumn;


   private Collection<String> list;
   private ObservableList<String> details;



   private FilesManagement files = new FilesManagement();

   private String[] fileNames;


   
   public Controller()
   {

   }

   
   
   /**
    * First method to be called, it will update the table.
    * 
    */
   @Override
   public void initialize(java.net.URL location, ResourceBundle resources)
   {
      table.setPlaceholder(new Label("There are no notes. Create one! :D"));
      editableText.setDisable(true);
      updateTable();
      doubleClickTableRowName();
      saveFromControlSKey();
   }


   

   /**
    * When called it will close current note
    */
   public void closeNote()
   {
      editableText.setText("");
      currentFile = "";
      editableText.setDisable(true);
   }


   
   
   /**
    * When user press control s inside textField
    * the file will get saved automatically
    */
   public void saveFromControlSKey()
   {
      
      editableText.setOnKeyPressed(event -> 
      {
         boolean halfCtrlSPressed = false;
         
         if(event.getCode().getName() == "Ctrl") 
         {
            halfCtrlSPressed = true;
         }
         else if(event.getCode().getName() == "s"  && halfCtrlSPressed  || event.getCode().getName() == "S"  && halfCtrlSPressed) 
         {
            halfCtrlSPressed = false;
            String information = editableText.getText();
            files.saveToFile(currentFile, information);
            
         }

      });
   }


   
   

   /**
    * 
    * When called, it will make sure a row from table is double clicked
    * and it will get the name of the file name clicked.
    * 
    */
   public void doubleClickTableRowName()
   {
      table.setRowFactory(tv -> 
      { 
         TableRow<String> row = new TableRow<>();

         row.setOnMouseClicked(event -> 
         {
            if(event.getClickCount() == 2 && (! row.isEmpty())) 
            {
               editableText.setDisable(false);

               currentFile = row.getItem();

               System.out.println("\n Current File in action: " + currentFile);

               openFileContent(currentFile);

            }

         });
         return row;
      });
   }



   
   
   /**
    * Saves currentFile clicked by user
    * 
    * @param event when save file is clicked this event will 
    * save the file.
    */
   public void saveFile(ActionEvent event)
   {
      String information = editableText.getText();

      files.saveToFile(currentFile, information);
   }


   
   

   /**
    * Opens current content from desired file
    * 
    * @param fileName name of the file to open
    */
   public void openFileContent(String fileName)
   {
      String content = files.getFileContent(fileName);
      editableText.setText(content);
   }


   
   
   /**
    * Delete current file 
    * 
    * @param event when clicked it eliminates file
    */
   public void deleteFile(ActionEvent event)
   {
      editableText.setDisable(true);
      editableText.setText("");
      files.deleteFile(currentFile);
      updateTable();
   }




   /**
    * 
    * When called, it will access the get files from the folder and update the table
    * 
    */
   public void updateTable()
   {
      fileNames =  files.getFiles();

      list = new ArrayList<>();

      String name = "";

      final int FILES_LENGTH = fileNames.length;

      for(int i = 1; i < FILES_LENGTH; ++i)
      {
         if(fileNames[i].contains(".txt"))
         {
            name = fileNames[i].replace(".txt", "");
         }
         list.add(name);
      }

      details = FXCollections.observableArrayList(list);

      noteNamesColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));

      table.setItems(details);
   }


   



   /**
    * When clicked, created a new file with user's desired name.
    * 
    * @param event gets called when create file is clicked
    */
   public void createFileBtn(ActionEvent event)
   {
      final int MIN_FILE_NAME_SIZE = 4;
      final String FILE_NAME =  files.setUpFileName();

      if(FILE_NAME.replace(" ", "").length() > MIN_FILE_NAME_SIZE)
      {
         System.out.println(FILE_NAME);
         files.createFile(FILE_NAME);
      }
      else
      {
         System.out.println("error");
         files.fileCreationError();
      }

      updateTable();
   }




}
