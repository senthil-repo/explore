package uk.co.explore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by s.nathan on 22/05/2020.
 * To derive the model that advices on success percentage on the following strategies, we need to run the program for 'n' times
 * 1) Random choice of drawers
 * 2) Sequential choice of drawers based on prisoner number
 */

public class Prisoner {
     //Random choice of drawers
     private static int randomChoice(int noOfPrisoners, int noOfChances) {
         int drawerArray[] = new int[100];
         int prionerNumberFoundCounter = 0;
         drawerArray = getUniqueNumbers();

         for(int i=1; i<=noOfPrisoners; i++) {
             int prisonerRandomDrawerArray[] = getUniqueNumbers();
             for(int j=1; j<=noOfChances; j++) {
                 int prisonerChoosenDrawer = prisonerRandomDrawerArray[i-1];

                 //check if priosner number same as number inside the random drawer
                 if(i == drawerArray[prisonerChoosenDrawer-1]) {
                     prionerNumberFoundCounter++;
                     break;
                 }
             }
         }
         return prionerNumberFoundCounter == noOfPrisoners ? 1 : 0;
     }

     public static void main(String[] args) {
         double prionerNumberFoundCounter_Random = 0;
         double prionerNumberFoundCounter_ownNumberChoice = 0;
         int numberOfPrionsers = 100;
         int numberOfChances = 50;
         int noOfTimesToRunTheProgram = 10000;
         //run Random choice for 1000 times
         for(int i=0; i<noOfTimesToRunTheProgram; i++) {
             prionerNumberFoundCounter_Random += randomChoice(numberOfPrionsers, numberOfChances);
             prionerNumberFoundCounter_ownNumberChoice +=  sequentialChoice(numberOfPrionsers, numberOfChances);
         }
         System.out.println(" Success percentage for random choice :"+ (prionerNumberFoundCounter_Random/noOfTimesToRunTheProgram) * 100+ "%");
         System.out.println(" Success percentage for own number choice :"+ (prionerNumberFoundCounter_ownNumberChoice/noOfTimesToRunTheProgram) * 100+ "%");

     }

     //Sequential choice of drawers based on prisoner nunber
     private static int sequentialChoice(int noOfPrisoners, int noOfChances) {
         int drawerArray[] = new int[100];
         int prionerNumberFoundCounter = 0;
         drawerArray = getUniqueNumbers();

         for(int i=1; i<=noOfPrisoners; i++) {
             int nextDrawer = 0;
             for(int j=1; j<=noOfChances; j++) {
                 //check if priosner number 'i' same as the number inside the j'th position drawer
                 if((j != 1 && i == drawerArray[nextDrawer-1]) || (j == 1 && i == drawerArray[j-1])) {
                     prionerNumberFoundCounter++;
                     break;
                 }
                 nextDrawer = drawerArray[j-1];
             }
         }
         return prionerNumberFoundCounter == noOfPrisoners ? 1 : 0;
     }

     private static int[] getUniqueNumbers() {
         List<Integer> range = IntStream.range(1, 101).boxed()
                 .collect(Collectors.toCollection(ArrayList::new));
         Collections.shuffle(range);
         return range.subList(0, 100).stream().mapToInt(value -> value).toArray();
     }
}
