/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

/**
* This class is designed to populate the database with 
* the minimum required set of data.
* Also to generate sql format files as per CA (the database part)
*/
public class ObservationsMaker {

    private final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+";

    private BufferedReader inputFileName;
    private BufferedWriter outputFileName;
    private boolean verboseMode = true;     // For DEBUG only

    private Set<Integer> intUniqueRandomSet;
    
    private String[] names;
    private String[] surnames;
    private String[] cources;
    private String[] modules;
    private String[] feedbacks;
    
    public void generateData(){
        load_cource_names();
        load_surnames();
        load_names();
        load_modules_names();
        load_feedbacks();
        
        make_random_students("datasets/out_students.csv", 100);
        make_random_courses("datasets/out_courses.csv", 100);
        make_random_lecturers("datasets/out_lecturers.csv", 100);
        make_random_modules("datasets/out_modules.csv", 100);
        make_random_feedbacks("datasets/out_feedbacks.csv", 100);
        
        
    }

    public void insertGeneratedDataToDatabase(){
        //ToDo
    }

    private void load_feedbacks(){
        if (verboseMode) System.out.print("Loading feedbacks... ");
        feedbacks = readStringsFromFile("datasets/feedbacks.txt");
        if (verboseMode) System.out.println( cources.length + " done");
    }
    
    private void load_modules_names(){
        if (verboseMode) System.out.print("Loading modules... ");
        modules = readStringsFromFile("datasets/modules.txt");
        if (verboseMode) System.out.println( cources.length + " done");
    }
    
    private void load_cource_names(){
        if (verboseMode) System.out.print("Loading cources... ");
        cources = readStringsFromFile("datasets/modules.txt");
        if (verboseMode) System.out.println( cources.length + " done");
    }

    private void load_surnames(){
        if (verboseMode) System.out.print("Loading surnames... ");
        surnames = readStringsFromFile("datasets/surnames.txt");
        if (verboseMode) System.out.println( surnames.length + " done");
    }

    private void load_names(){
        if (verboseMode) System.out.print("Loading names... ");
        names = readStringsFromFile("datasets/names.txt");
        if (verboseMode) System.out.println( names.length + " done");
    }     

    private void make_random_feedbacks(String fileName, int how_many_generate){
                 
        boolean in_progress = true;
        int cnt = 0;
        String delimiter = ";";
        boolean first_line = true;

        try {
            outputFileName  = new BufferedWriter(new FileWriter(fileName, false ));
            if (verboseMode) System.out.print("Generating feedbacks... ");
            while (in_progress) {

                   int student_id = randomRangeRandom(0, 99);
                   int course_id = randomRangeRandom(0, 99);
                   
                    if (first_line){ // Header
                           outputFileName.write("student_id" + delimiter + "course_id" + delimiter + "text\n" );
                            first_line = false;
                    }                    
                    outputFileName.write( student_id + delimiter + course_id + delimiter + feedbacks[cnt]);
                    cnt++;
                    if (cnt>how_many_generate-1) {
                        in_progress = false;
                    } else {
                        outputFileName.newLine();
                    }
               }        
                outputFileName.close();
            
            } catch (Exception e) {
                  System.out.println("Error: " + e.getMessage() );
            }
            System.out.println(cnt + " Done");
    }         
    
    private void make_random_modules(String fileName, int how_many_generate){
                 
        boolean in_progress = true;
        int cnt = 0;
        String delimiter = ";";
        boolean first_line = true;

        try {
            outputFileName  = new BufferedWriter(new FileWriter(fileName, false ));
            if (verboseMode) System.out.print("Generating modules... ");
            while (in_progress) {

                   int course_id = randomRangeRandom(0, 99);
                   int lecturer_id = randomRangeRandom(0, 99);
                   int room_int =  randomRangeRandom(100, 400);
                   String room = String.valueOf(room_int);
                   if (room_int>300) room = "online";
                   int semester = randomRangeRandom(1, 4);
                   
                    if (first_line){ // Header
                           outputFileName.write("name" + delimiter + "course_id" + delimiter + "lecturer_id" + delimiter + "room" + delimiter + "semester\n" );
                            first_line = false;
                    }                    
                    outputFileName.write(modules[cnt] + delimiter + course_id + delimiter + lecturer_id + delimiter + room + delimiter + semester);
                    cnt++;
                    if (cnt>how_many_generate-1) {
                        in_progress = false;
                    } else {
                        outputFileName.newLine();
                    }
               }        
                outputFileName.close();
            
            } catch (Exception e) {
                  System.out.println("Error: " + e.getMessage() );
            }
            System.out.println(cnt + " Done");
    }     
    
    private void make_random_lecturers(String fileName, int how_many_generate){
                 
        boolean in_progress = true;
        int cnt = 0;
        String delimiter = ";";
        boolean first_line = true;
        HashSet<String> usedSurnames  = new HashSet<>();

        String[] roles = {"associate lecturer", "senior lecturer", "professor", "programme manager"};
        String[] types = {"Java", "Web Dev", "Python", "Maths"};

        try {
            outputFileName  = new BufferedWriter(new FileWriter(fileName, false ));
            if (verboseMode) System.out.print("Generating lecturers... ");
            while (in_progress) {

                   String random_name = (names[randomRangeRandom(0, names.length-1)]);
                   String random_surname = (surnames[randomRangeRandom(0, surnames.length-1)]);
                   String random_role = (roles[randomRangeRandom(0, roles.length-1)]);
                   String random_type = (types[randomRangeRandom(0, types.length-1)]);

                   int prev_count = usedSurnames.size();
                   usedSurnames.add(random_surname);
                   if (usedSurnames.size()>prev_count) {

                        if (first_line){ // Header
                            outputFileName.write("fullname" + delimiter + "role" + delimiter + "type\n" );
                            first_line = false;
                        }                    
                        outputFileName.write(random_name + " " + random_surname + delimiter + random_role + delimiter + random_type);

                       cnt++;
                       if (cnt>how_many_generate-1) {
                           in_progress = false;
                       } else {
                         outputFileName.newLine();
                       }
                   }
               }        
                outputFileName.close();
            
            } catch (Exception e) {
                  System.out.println("Error: " + e.getMessage() );
            }
            System.out.println(cnt + " Done");
    }        
    
    
    private void make_random_courses(String fileName, int how_many_generate){
                 
        boolean in_progress = true;
        int cnt = 0;
        String delimiter = ";";
        boolean first_line = true;
        HashSet<String> usedSurnames  = new HashSet<>();

        try {
            outputFileName  = new BufferedWriter(new FileWriter(fileName, false ));
            if (verboseMode) System.out.print("Generating courses... ");
            while (in_progress) {

                String random_surname = (cources[randomRangeRandom(0, cources.length-1)]);

                   int prev_count = usedSurnames.size();
                   usedSurnames.add(random_surname);
                   if (usedSurnames.size()>prev_count) {

                        if (first_line){ // Header
                            outputFileName.write("name" + delimiter + "semester_number\n" );
                            first_line = false;
                        }                    
                        outputFileName.write(random_surname + delimiter + randomRangeRandom(3,12) );

                       cnt++;
                       if (cnt>how_many_generate-1) {
                           in_progress = false;
                       } else {
                         outputFileName.newLine();
                       }
                   }
               }        
                outputFileName.close();
            
            } catch (Exception e) {
                  System.out.println("Error: " + e.getMessage() );
            }
            System.out.println(cnt + " Done");
    }    
    
    private void make_random_students(String fileName, int how_many_generate){
                 
        boolean in_progress = true;
        int cnt = 0;
        String delimiter = ";";
        boolean first_line = true;
        HashSet<String> usedSurnames  = new HashSet<>();
        generateRandomUniqSet(1, 1000, 100);

        int[] student_numbers = intUniqueRandomSet.stream().mapToInt(Integer::intValue).toArray();

        try {
            outputFileName  = new BufferedWriter(new FileWriter(fileName, false ));
            if (verboseMode) System.out.print("Generating students... ");
            while (in_progress) {

                   String random_name = (names[randomRangeRandom(0, names.length-1)]);
                   String random_surname = (surnames[randomRangeRandom(0, surnames.length-1)]);

                   int prev_count = usedSurnames.size();
                   usedSurnames.add(random_surname);
                   if (usedSurnames.size()>prev_count) {

                        if (first_line){ // Header
                            outputFileName.write("fullname" + delimiter + "number\n" );
                            first_line = false;
                        }                    
                        outputFileName.write(random_name + " " + random_surname + delimiter + student_numbers[cnt] );

                       cnt++;
                       if (cnt>how_many_generate-1) {
                           in_progress = false;
                       } else {
                         outputFileName.newLine();
                       }
                   }
               }        
                outputFileName.close();
            
            } catch (Exception e) {
                  System.out.println("Error: " + e.getMessage() );
            }
            System.out.println(cnt + " Done");
    }    
    
    private int randomRangeRandom(int start, int end) {
        Random random = new Random();
        int number = random.nextInt((end - start) + 1) + start;
        return number;
    }

    private void generateRandomUniqSet(int minimum_value, int maximum_value, int how_many) {

        intUniqueRandomSet = new HashSet<>();
        Random random = new Random();

        // Generate unique random integers
        while (intUniqueRandomSet.size() < how_many) {
            int randomNum = random.nextInt(maximum_value - minimum_value + 1) + minimum_value;
            intUniqueRandomSet.add(randomNum);
        }

    }
    
    private String[] readStringsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.lines().toArray(String[]::new);
        } catch (Exception e) {
            return new String[0];
        }
    }
    
}

