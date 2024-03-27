/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.Random;

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

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+";

    private static BufferedReader inputFileName;
    private static BufferedWriter outputFileName;
    
    private static String[] cources;
    
    public void generateData(){
        load_cource_names();
    }
    
    private void load_cource_names(){
        System.out.print("Loading cources... ");
        cources = readStringsFromFile("dataset/dataset_programmes.csv");
        System.out.println( cources.length + " done");
    }
    
    private int randomRangeRandom(int start, int end) {
        Random random = new Random();
        int number = random.nextInt((end - start) + 1) + start;
        return number;
    }
    
    private String[] readStringsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.lines().toArray(String[]::new);
        } catch (Exception e) {
            return new String[0];
        }
    }
    
}

