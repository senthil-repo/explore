package uk.co.explore;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by s.nathan on 22/05/2020.
 To build a model, we need to run the program 'n' times, to find the probabilities of
 winning the game under the following cases:
 1) Player choosen to stick with his initial choice
 2) Player change his mind and choose a second choice.

 By choosing random numbers for playerDoor, hostDoor & carDoor during each run, we can able to find the percentage
 of which the above scenarios are played. From the above, we can able to advise anyone on whether to stick
 with original choice or to go for second choice of choosing a door.
 */
public class MontyHall {
    public static void main(String[] args) {
        int noOfTimesToRun = 10000;
        System.out.println(" To find the probability, lets run the program for " + noOfTimesToRun + " times ");
        getChancesOfWinning(noOfTimesToRun);
    }

    private static void getChancesOfWinning(int noOfTimesToRun) {
        Random random = new Random();
        int doorArray[] = {1, 2, 3};
        double firstChoiceCounter = 0;
        double secondChoiceCounter = 0;

        for (int i = 0; i < noOfTimesToRun; i++) {

            //randomly find the door behind which the car is located
            int carBehindDoor = random.nextInt(3) + 1;

            //radomly find the door which a player can choose initially
            int playerChoosenDoor = random.nextInt(3) + 1;
            final int playerChoosenDoor_lambda = playerChoosenDoor;
            int remainingDoors[] = IntStream.of(doorArray).filter(value -> value != playerChoosenDoor_lambda).toArray();

            //radomly find the host picking door
            int hostPickupDoor = random.nextBoolean() ? remainingDoors[0] : remainingDoors[1];

            //note : if the player already choose a door which has got goat, then HOST will choose only the door which has
            //got other goat
            if (playerChoosenDoor != carBehindDoor) {
                hostPickupDoor = IntStream.of(remainingDoors).filter(value -> value != playerChoosenDoor_lambda && value != carBehindDoor).findFirst().getAsInt();
            }

            int secondChoiceDoor = remainingDoors[0] != hostPickupDoor ? remainingDoors[0] : remainingDoors[1];

            //give a chance for the player to swap the options
            boolean hasSecondChoiceChoosen = random.nextBoolean();
            playerChoosenDoor = hasSecondChoiceChoosen ? secondChoiceDoor : playerChoosenDoor;

            StringBuilder builder = new StringBuilder();
            builder.append("Player choosen door : ").append(playerChoosenDoor).append(", Remaining doors : ").append(remainingDoors[0] + "  &  " + remainingDoors[1]).
                    append(", Host pickup door : ").append(hostPickupDoor).append(", Second choice door :").append(secondChoiceDoor).append(", Car behind door :").
                    append(carBehindDoor).append(", Final door choosen by player : ").append(playerChoosenDoor);
            //System.out.println(builder.toString());

            if (playerChoosenDoor == carBehindDoor) {
                if (!hasSecondChoiceChoosen)
                    firstChoiceCounter++;
                else
                    secondChoiceCounter++;
            }
        }

        double firstChoicePercentage = (firstChoiceCounter / noOfTimesToRun) * 100;
        double secondChoicePercentage = (secondChoiceCounter / noOfTimesToRun) * 100;
        System.out.println("FirstChoicePercentage  :" + firstChoicePercentage + "%");
        System.out.println("SecondChoicePercentage :" + secondChoicePercentage + "%");
        System.out.println("");

        StringBuilder result = new StringBuilder();
        System.out.println(" ******* R E S U L T ******* ");
        String message = " By running the program for " + noOfTimesToRun + " times, we can find that it is better to ";
        result.append(message).append(firstChoicePercentage > secondChoicePercentage ? "stick with the original choice. "
                : "change your mind and go for the second choice. ").toString();

        System.out.println(result.toString());
    }

}
