/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prog6112_assignment1_st10509664;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PROG6112_Assignment1_ST10509664 {
    static int count = 0;
    static String searchID = "";
    static String beds = "";
    static int countInpatient = 0;
    static int countAvailable = 0;
    //array declaration
   static String[] ID = new String[50];
   static String[] name1 = new String[50];
   static String[] name2 = new String[50];
   static String[] Age = new String[50];
   static String[] Gender = new String[50];
   static String[] Condition = new String[50];
   static String[] Category = new String[50];
   
   static String[][] availableBeds = new String[4][5]; 
   static String[][] occupiedBeds = new String[4][5];
   static String[][] wardLayout = new String[4][5];             
     
    public static void main(String[] args) {
      
      //check that text file exists to read and write details
        File file = new File("Patients.txt");
        if (!file.exists()) {
          try {
              file.createNewFile();
          } catch (IOException e) {
              System.out.println("Error creating file: " + e.getMessage());
          }
            System.out.println("File created");
        }
        
      Scanner input = new Scanner(System.in);  
      boolean running = true;  
      
        System.out.println("=====================================================");  
        System.out.println("\t\tWelcome!");
        System.out.println("====================================================="); 
        
      while(running == true){
        System.out.println("Please select one of the following options below");
        System.out.println("1) Register a new patient");
        System.out.println("2) Search for a patient");
        System.out.println("3) Update an existing patient's details");
        System.out.println("4) Delete a patient");
        System.out.println("5) Display all registered patients");
        System.out.println("6) Bed Management");
        System.out.println("7) Exit application");
        int option = input.nextInt();
        input.nextLine();
        
        switch (option) {
            case 1:
              System.out.println("--------------------------------------------------");
              System.out.print("Please enter patient ID: ");
              String patientID = input.nextLine();
              System.out.print("Please enter your first name: ");
              String firstName = input.nextLine();
              System.out.print("Please enter your last name: ");
              String lastName = input.nextLine();
              System.out.print("Please enter your age: ");
              String age = input.nextLine();
              System.out.print("Please enter your gender (female/male): ");
              String gender = input.nextLine();
              System.out.print("Please enter your medical condition: ");
              String medicalCondition = input.nextLine();
              System.out.print("Please enter the applicable category as a petient; Inpatient, Outpatient or Emergency: ");
              String category = input.nextLine();

              //wtite to the textfile (append)
              try ( BufferedWriter writeFile = new BufferedWriter(new FileWriter("Patients.txt", true))) { 
               writeFile.newLine();
               writeFile.write(patientID + "#" + firstName + "#" + lastName + "#" + age + "#" + gender + "#" + medicalCondition + "#" + category);
               writeFile.close();
               
               System.out.println("\n--------------------------------------------------");
               System.out.println("New patient successfully registered");
              } catch(IOException e) {
                  System.out.println("\n--------------------------------------------------");
                  System.out.println("An error occurred.");
              }
            break;    

            case 2: 
                System.out.print("Please enter a patient ID: ");
                searchID = input.nextLine();
                
                //create object to call method
                PROG6112_Assignment1_ST10509664 details = new PROG6112_Assignment1_ST10509664();
                details.getDetails();
                
               //loop through array ID and search for the appropriate ID
                boolean validID = false;
                for(int i = 0; i < count; i++){
                  if (ID[i].equals(searchID)){
                      validID = true;
                      System.out.println("--------------------------------------------------");
                      System.out.println("\tMatching patient ID found");
                      System.out.println("--------------------------------------------------");
                      System.out.println("Patient ID: " + ID[i]);
                      System.out.println("First Name " + name1[i]);
                      System.out.println("Last Name: " + name2[i]);
                      System.out.println("Age: " + Age[i]);
                      System.out.println("Gender: " + Gender[i]);
                      System.out.println("Medical Condition: " + Condition[i]);
                      System.out.println("Patient Category: " + Category[i]);
                      System.out.println("--------------------------------------------------");
                  }
                }
                
                if(validID == false) {
                    System.out.println("--------------------------------------------------");
                    System.out.println("Patient ID not found!");
                }
                
            break;
            
            case 3: 
                System.out.print("Please enter a patient ID: ");
                searchID = input.nextLine();
                PROG6112_Assignment1_ST10509664 details2 = new PROG6112_Assignment1_ST10509664();
                details2.getDetails();
                
                //find the correct index to delete the correct line
                int matchIndex = -1;
                for (int i = 0; i < count; i++) {
                    if (ID[i].equals(searchID)) {
                        matchIndex = i;
                        break;
                    }
                }
                
                if (matchIndex == -1) {
                    System.out.println("Patient ID not found!");
                } else {
                    String lineToRemove = ID[matchIndex] + "#" + name1[matchIndex] + "#" + name2[matchIndex] + "#" + Age[matchIndex] + "#" + Gender[matchIndex] + "#" + Condition[matchIndex] + "#" + Category[matchIndex];

                //remove the line existing within the textfile
                Path originalPath = Paths.get("Patients.txt");
                Path tempPath = Paths.get("Patients_temp.txt");
                
                try (BufferedReader reader = new BufferedReader(new FileReader(originalPath.toFile()));
                     BufferedWriter writer = new BufferedWriter(new FileWriter(tempPath.toFile()))) {
                    String currentLine;

                    while ((currentLine = reader.readLine()) != null) {
                        // If it's not the line to delete, write it to the temp file
                        if (!currentLine.trim().equals(lineToRemove)) {
                            writer.write(currentLine);
                            writer.newLine();
                        }
                    }
                    writer.close();
                    reader.close();
                    // Replace the original file with the temporary file
                    Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
                
                //input information for update
                System.out.println("------------------------------------------------------------------");
                System.out.println("\tMatching patient ID found; Update details");
                System.out.println("------------------------------------------------------------------");
                System.out.print("Please enter your first name: ");
                String newFirstName = input.nextLine();
                System.out.print("Please enter your last name: ");
                String newLastName = input.nextLine();
                System.out.print("Please enter your age: ");
                String newAge = input.nextLine();
                System.out.print("Please enter your gender (female/male): ");
                String newGender = input.nextLine();
                System.out.print("Please enter your medical condition: ");
                String newMedicalCondition = input.nextLine();
                System.out.print("Please enter the applicable category as a petient; Inpatient, Outpatient or Emergency: ");
                String newCategory = input.nextLine();
                System.out.println("--------------------------------------------------");
                
              //wtite to the textfile (append)
              try ( BufferedWriter writeFile = new BufferedWriter(new FileWriter("Patients.txt", true))) { 
               writeFile.write(searchID + "#" + newFirstName + "#" + newLastName + "#" + newAge + "#" + newGender + "#" + newMedicalCondition + "#" + newCategory);
               writeFile.newLine();
               writeFile.close();
               
               System.out.println("\n--------------------------------------------------");
               System.out.println("New patient successfully registered");
              } catch(IOException e) {
                  System.out.println("\n--------------------------------------------------");
                  System.out.println("An error occurred.");
              } 
            break;
            
            case 4:
                System.out.print("Please enter a patient ID: ");
                searchID = input.nextLine();
                
                PROG6112_Assignment1_ST10509664 details3 = new PROG6112_Assignment1_ST10509664();
                details3.getDetails();
                
                //find the correct index to delete the correct line
                int matchIndex2 = -1;
                for (int i = 0; i < count; i++) {
                    if (ID[i].equals(searchID)) {
                        matchIndex2 = i;
                        break;
                    }
                }
                
                if (matchIndex2 == -1) {
                    System.out.println("Patient ID not found!");
                } else {
                    String lineToRemove = ID[matchIndex2] + "#" + name1[matchIndex2] + "#" + name2[matchIndex2] + "#" + Age[matchIndex2] + "#" + Gender[matchIndex2] + "#" + Condition[matchIndex2] + "#" + Category[matchIndex2];

                //remove the line existing within the textfile
                Path originalPath = Paths.get("Patients.txt");
                Path tempPath = Paths.get("Patients_temp.txt");
                
                try (BufferedReader reader = new BufferedReader(new FileReader(originalPath.toFile()));
                     BufferedWriter writer = new BufferedWriter(new FileWriter(tempPath.toFile()))) {
                    String currentLine;

                    while ((currentLine = reader.readLine()) != null) {
                        // If it's not the line to delete, write it to the temp file
                        if (!currentLine.trim().equals(lineToRemove)) {
                            writer.write(currentLine);
                            writer.newLine();
                        }
                    }
                    writer.close();
                    reader.close();
                    // Replace the original file with the temporary file
                    Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("\n--------------------------------------------------");
                    System.out.println("Patient successfully deleted");
                    System.out.println("\n--------------------------------------------------");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            break;
            
            case 5:
                PROG6112_Assignment1_ST10509664 details4 = new PROG6112_Assignment1_ST10509664();
                details4.getDetails();
                
                if (count == 0) {
                    System.out.println("No patients registered yet.");
                } else {
                    System.out.println("\n--------------------------------------------------");
                    System.out.println("\tAll Registered Patients");
                    System.out.println("--------------------------------------------------");

                    for (int i = 0; i < count; i++) {
                        System.out.println("Patient ID: " + ID[i]);
                        System.out.println("First Name: " + name1[i]);
                        System.out.println("Last Name: " + name2[i]);
                        System.out.println("Age: " + Age[i]);
                        System.out.println("Gender: " + Gender[i]);
                        System.out.println("Medical Condition: " + Condition[i]);
                        System.out.println("Patient Category: " + Category[i]);
                        System.out.println("--------------------------------------------------");
                    }
                }
            break;
            
            
            case 6:
                System.out.println("--------------------------------------------------");
                System.out.println("Please select one of the following options below");
                System.out.println("--------------------------------------------------");
                System.out.println("1) Allocate beds to inpatients");
                System.out.println("2) Discharge a patient");
                System.out.println("3) Display ward layout");
                System.out.println("4) Display available beds");
                System.out.println("5) Display occupied beds");
                int option2 = input.nextInt();
                input.nextLine();
                
                PROG6112_Assignment1_ST10509664 details5 = new PROG6112_Assignment1_ST10509664();
                details5.getDetails();
                switch(option2) {
                    case 1: 
                        details5.getOccupiedBeds();
                        //allocate beds
                        countAvailable = 0;
                        for(int r = 0; r < 4; r++) {
                            for(int c = 0; c < 5; c++) {
                                if( countAvailable < countInpatient ){
                                    countAvailable++;
                                    occupiedBeds[r][c] = "B" + countAvailable;  
                                }
                            }
                        }
                        if(countAvailable < 20){
                        //allocate the occupied beds in the 2D array
                        beds = "";
                        for(int r = 0; r < 4; r++) {
                            String out = "";
                            for(int c = 0; c < 5; c++) {
                                out = out + occupiedBeds[r][c] + "\t";
                                if(!occupiedBeds[r][c].equals("-")){
                                    beds = beds + occupiedBeds[r][c] + " ";
                                }
                            }
                        }
                        System.out.println("Beds successfully allocated. Occupied beds updated to: " + beds);
                        System.out.println("--------------------------------------------------");
                        } else {
                        System.out.println("No beds available. A new inpatient cannot be allocated a bed at this time");
                        System.out.println("--------------------------------------------------");   
                        }
                    break;
                    
                    case 2:      
                        //select the patient you want to discharge, and change their category to outpatient in the textfile
                        System.out.print("Please enter a patient ID: ");
                        searchID = input.nextLine();
                        details5.getDetails();

                        //find the correct index to delete the correct line
                        int matchIndex3 = -1;
                        for (int i = 0; i < count; i++) {
                            if (ID[i].equals(searchID)) {
                                matchIndex3 = i;
                                break;
                            }
                        }

                        if (matchIndex3 == -1) {
                            System.out.println("Patient ID not found!");
                        } else {
                            String lineToRemove = ID[matchIndex3] + "#" + name1[matchIndex3] + "#" + name2[matchIndex3] + "#" + Age[matchIndex3] + "#" + Gender[matchIndex3] + "#" + Condition[matchIndex3] + "#" + Category[matchIndex3];

                        //remove the line existing within the textfile
                        Path originalPath = Paths.get("Patients.txt");
                        Path tempPath = Paths.get("Patients_temp.txt");


                        //wtite to the textfile (append)
                        try ( BufferedWriter writeFile = new BufferedWriter(new FileWriter("Patients.txt", true))) { 
                         writeFile.write(ID[matchIndex3] + "#" + name1[matchIndex3] + "#" + name2[matchIndex3] + "#" + Age[matchIndex3] + "#" + Gender[matchIndex3] + "#" + Condition[matchIndex3] + "#Outpatient" );
                         writeFile.newLine();
                         writeFile.close();

                        } catch(IOException e) {
                            System.out.println("\n--------------------------------------------------");
                            System.out.println("An error occurred.");
                        } 

                        try (BufferedReader reader = new BufferedReader(new FileReader(originalPath.toFile()));
                        BufferedWriter writer = new BufferedWriter(new FileWriter(tempPath.toFile()))) {
                        String currentLine;

                        while ((currentLine = reader.readLine()) != null) {
                            // If it's not the line to delete, write it to the temp file
                            if (!currentLine.trim().equals(lineToRemove)) {
                                writer.write(currentLine);
                                writer.newLine();
                            }
                        }
                        writer.close();
                        reader.close();
                        // Replace the original file with the temporary file
                        Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);

                        } catch (IOException e) {
                            e.printStackTrace();
                            }
                        }         
                        
                        details5.getOccupiedBeds();
                        //allocate beds
                        countAvailable = 0;
                        for(int r = 0; r < 4; r++) {
                            for(int c = 0; c < 5; c++) {
                                if( countAvailable < countInpatient - 1 ) {
                                    countAvailable++;
                                    occupiedBeds[r][c] = "B" + countAvailable;  
                                }
                            }
                        }
                        //allocate the occupied beds in the 2D array
                        beds = "";
                        for(int r = 0; r < 4; r++) {
                            String out = "";
                            for(int c = 0; c < 5; c++) {
                                out = out + occupiedBeds[r][c] + "\t";
                                if(!occupiedBeds[r][c].equals("-")){
                                    beds = beds + occupiedBeds[r][c] + " ";
                                }
                          }
                        }
                        System.out.println("Patient successfully discharged. Occupied beds updated to: " + beds);
                        System.out.println("--------------------------------------------------");
                    break;
                 
                    case 3:
                        //initialise display
                        int n = 0;
                        for(int r = 0; r < 4; r++) {
                            for(int c = 0; c < 5; c++) {
                                n++;
                                wardLayout[r][c] = "B" + n;  
                            }
                        }
                        //display
                        for(int r = 0; r < 4; r++) {
                            String out = "";
                            for(int c = 0; c < 5; c++) {
                                out = out + wardLayout[r][c] + "\t";
                          }
                            System.out.println(out);
                        }
                        System.out.println("--------------------------------------------------");
                    break;
                    
                    case 4: 
                        details5.getAvailableBeds();
                        System.out.println("\n--------------------------------------------------");
                    break;
                    
                    case 5: 
                        details5.getOccupiedBeds();
                        //allocate beds
                        countAvailable = 0;
                        for(int r = 0; r < 4; r++) {
                            for(int c = 0; c < 5; c++) {
                                if( countAvailable < countInpatient ){
                                    countAvailable++;
                                    occupiedBeds[r][c] = "B" + countAvailable;  
                                }
                            }
                        }
                        //allocate the occupied beds in the 2D array
                        beds = "";
                        for(int r = 0; r < 4; r++) {
                            String out = "";
                            for(int c = 0; c < 5; c++) {
                                out = out + occupiedBeds[r][c] + "\t";
                                if(!occupiedBeds[r][c].equals("-")){
                                    beds = beds + occupiedBeds[r][c] + " ";
                                }
                            }
                            System.out.println(out);
                        }
                        System.out.println("\n--------------------------------------------------");
                    break;
                    
                }
            break;
            
            
            case 7:
                System.out.println("--------------------------------------------------");
                System.out.println("Goodbye!");
                running = false;
                System.exit(0);
            default:
            System.out.println("Invalid option");
            break;
          }  

        }
    }
    
    public void getDetails() {
        try {
            count = 0;
            BufferedReader reader = new BufferedReader(new FileReader("Patients.txt"));
            String line;
            //loop from begining of textfile till the end
             while ((line = reader.readLine()) != null && count < ID.length) {
            //spilt everytime a # is found
              String[] parts = line.split("#", 7);
              if (parts.length < 5) {
                  System.out.println("Skipping line: " + line);
              }
              //assign the corrrect parts from the textfile to the appropriate array
              ID[count] = parts[0];
              name1[count] = parts[1];
              name2[count] = parts[2];
              Age[count] = parts[3];
              Gender[count] = parts[4];
              Condition[count] = parts[5];
              Category[count] = parts[6];

              count++;
              }
             reader.close();
             
        } catch (IOException e) {
            System.out.println("\n--------------------------------------------------");
            System.out.println("An error occurred.");
      }    
    }
    public void getOccupiedBeds() {
        //count the amount of inpatients
        countInpatient = 0;
        for(int i = 0; i < count; i++) {
            if (Category[i].equals("Inpatient" ) || Category[i].equals("inpatient")){
                countInpatient++;
            }
        }
        //initialize the array
        for(int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                occupiedBeds[r][c] = "-";
            }
        }
    }
    
    public void getAvailableBeds() {
        //count the amount of inpatients
        countInpatient = 0;
        for(int i = 0; i < count; i++) {
            if (Category[i].equals("Inpatient" ) || Category[i].equals("inpatient")){
                countInpatient++;
            }
        }
        //initialize the array
        countAvailable = 0;
        for(int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                countAvailable++;
                occupiedBeds[r][c] = "B" + countAvailable; 
            }
        }
        //allocate beds
        countAvailable = 0;
        for(int r = 0; r < 4; r++) {
           for(int c = 0; c < 5; c++) {
               if( countAvailable < countInpatient ){
                   countAvailable++;
                   occupiedBeds[r][c] = "-";  
               }
           }
        }
        //allocate the occupied beds in the 2D array
        beds = "";
        for(int r = 0; r < 4; r++) {
           String out = "";
           for(int c = 0; c < 5; c++) {
               out = out + occupiedBeds[r][c] + "\t";
               if(!occupiedBeds[r][c].equals("-")){
                   beds = beds + occupiedBeds[r][c] + " ";
               }
           }
           System.out.println(out);                            
        }
    }

}
