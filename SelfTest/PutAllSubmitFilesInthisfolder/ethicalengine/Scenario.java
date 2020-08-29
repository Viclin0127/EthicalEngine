/**
 * COMP90041 Programming and Software Developing Final Project:
 * Moral Machines. The idea of Moral Machines is based on the Trolley Dilemma,
 * a fictional scenario presenting a decision maker with a moral dilemma: choosing the lesser of two evils.
 *
 * @author Yuan
 * Name: Yuan Hung Lin
 * StudentID: 1119147
 * Username: yuanhungl
 */
package ethicalengine;

import java.util.ArrayList;

// contains a list of passengers, a list of pedestrians, as well as additional scenario conditions,
// such as whether pedestrians are legally crossing at the traffic light
public class Scenario {
    private ArrayList<Character> passengers;
    private ArrayList<Character> pedestrians;
    private boolean isLegalCrossing;
    private Decision survival;
    private int totalSurvivalAge;
    private int totalSurvivalPerson;
    private int[] suvNumber = new int[27];
    private int[] totalNumber = new int[27];
    private String[] characteristic = {"doctor", "ceo", "criminal", "homeless", "unemployed", "unknown",
            "engineer", "artist", "baby", "child", "adult", "senior", "male", "female", "average", "athletic",
            "overweight", "pregnant", "person", "animal", "cat", "dog", "rabbit", "pets", "red", "green", "you"};

    /**
     * enum type Decision
     */
    public enum Decision {
        PASSENGERS, PEDESTRIANS
    }

    /**
     * constructor
     *
     * @param passengers
     * @param pedestrians
     * @param isLegalCrossing
     */
    public Scenario(ArrayList<Character> passengers, ArrayList<Character> pedestrians,
                    boolean isLegalCrossing) {
        this.passengers = passengers;
        this.pedestrians = pedestrians;
        this.isLegalCrossing = isLegalCrossing;
        for (int i = 0; i < suvNumber.length; i++) {
            suvNumber[i] = 0;
            totalNumber[i] = 0;
        }
    }

    /**
     * check if "YOU" are in the car
     *
     * @return
     */
    public boolean hasYouInCar() {
        for (int i = 0; i < this.getPassengerCount(); i++) {
            if (passengers.get(i).isYou()) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if "YOU" are in lane
     *
     * @return
     */
    public boolean hasYouInLane() {
        for (int i = 0; i < this.getPedestrianCount(); i++) {
            if (pedestrians.get(i).isYou()) {
                return true;
            }
        }
        return false;
    }

    /**
     * getter function
     *
     * @return
     */
    public ArrayList<Character> getPassengers() {
        return this.passengers;
    }

    /**
     * getter function
     *
     * @return
     */
    public ArrayList<Character> getPedestrians() {
        return this.pedestrians;
    }

    /**
     * getter function
     *
     * @return
     */
    public boolean isLegalCrossing() {
        return this.isLegalCrossing;
    }

    /**
     * getter function
     *
     * @return
     */
    public Decision getSurvival() {
        if (this.survival == null) {
            System.out.println("WARNING: you have to decide who to survive first!");
            System.exit(0);
        }
        return this.survival;
    }

    /**
     * getter function
     *
     * @return
     */
    public int getPassengerCount() {
        int count = 0;
        for (int i = 0; i < passengers.size(); i++) {
            count++;
        }
        return count;
    }

    /**
     * getter function
     *
     * @return
     */
    public int getPedestrianCount() {
        int count = 0;
        for (int i = 0; i < pedestrians.size(); i++) {
            count++;
        }
        return count;
    }

    /**
     * getter function
     *
     * @return
     */
    public int[] getTotalNumber() {
        return this.totalNumber;
    }

    /**
     * getter function
     *
     * @return
     */
    public int[] getSuvNumber() {
        return this.suvNumber;
    }

    /**
     * getter function
     *
     * @return
     */
    public String[] getCharacteristic() {
        return this.characteristic;
    }

    /**
     * getter function
     *
     * @return
     */
    public int getTotalSurvivalAge() {
        return this.totalSurvivalAge;
    }

    /**
     * setter function
     *
     * @param isLegalCrossing
     */
    public void setLegalCrossing(boolean isLegalCrossing) {
        this.isLegalCrossing = isLegalCrossing;
    }

    /**
     * setter function
     *
     * @param decision
     */
    public void setSurvival(Decision decision) {
        this.survival = decision;
    }

    //DOCTOR, CEO, CRIMINAL, HOMELESS, UNEMPLOYED, UNKNOWN, ENGINEER, ARTIST
    //    0 ,  1 ,     2   ,   3     ,     4     ,    5   ,    6    ,    7
    //BABY, CHILD, ADULT, SENIOR, MALE, FEMALE, AVERAGE, ATHLETIC, OVERWEIGHT
    //  8 ,   9  ,   10 ,    11 ,  12 ,   13  ,   14   ,    15   ,     16
    //PREGNANT, PERSON, ANIMAL, CAT, DOG, RABBIT, PETS, RED, GREEN, YOU
    //  17    ,   18  ,   19  , 20 ,  21,   22  ,  23 ,  24,   25 ,  26

    /**
     * set Total number of passengers and pedestrians (statistic)
     * characteristic array index is as below:
     * DOCTOR, CEO, CRIMINAL, HOMELESS, UNEMPLOYED, UNKNOWN, ENGINEER, ARTIST
     * 0 ,  1 ,     2   ,   3     ,     4     ,    5   ,    6    ,    7
     * BABY, CHILD, ADULT, SENIOR, MALE, FEMALE, AVERAGE, ATHLETIC, OVERWEIGHT
     * 8 ,   9  ,   10 ,    11 ,  12 ,   13  ,   14   ,    15   ,     16
     * PREGNANT, PERSON, ANIMAL, CAT, DOG, RABBIT, PETS, RED, GREEN, YOU
     * 17    ,   18  ,   19  , 20 ,  21,   22  ,  23 ,  24,   25 ,  26
     */
    public void setTotalNumber() {
        // for passengers
        for (int i = 0; i < passengers.size(); i++) {
            //only person
            if (passengers.get(i).getClass().equals(new Person().getClass())) {
                totalNumber[18] += 1;
                // first, set total number of age category
                if (passengers.get(i).getAge() <= 4) {
                    totalNumber[8] += 1;
                }
                if (passengers.get(i).getAge() > 4 && passengers.get(i).getAge() <= 16) {
                    totalNumber[9] += 1;
                }
                if (passengers.get(i).getAge() > 16 && passengers.get(i).getAge() <= 68) {
                    totalNumber[10] += 1;
                    // second, set total number of profession (Only Adult)
                    if (passengers.get(i).getProfession().equals(Person.Profession.DOCTOR)) {
                        totalNumber[0] += 1;
                    }
                    if (passengers.get(i).getProfession().equals(Person.Profession.CEO)) {
                        totalNumber[1] += 1;
                    }
                    if (passengers.get(i).getProfession().equals(Person.Profession.CRIMINAL)) {
                        totalNumber[2] += 1;
                    }
                    if (passengers.get(i).getProfession().equals(Person.Profession.HOMELESS)) {
                        totalNumber[3] += 1;
                    }
                    if (passengers.get(i).getProfession().equals(Person.Profession.UNEMPLOYED)) {
                        totalNumber[4] += 1;
                    }
                    if (passengers.get(i).getProfession().equals(Person.Profession.UNKNOWN)) {
                        totalNumber[5] += 1;
                    }
                    if (passengers.get(i).getProfession().equals(Person.Profession.ENGINEER)) {
                        totalNumber[6] += 1;
                    }
                    if (passengers.get(i).getProfession().equals(Person.Profession.ARTIST)) {
                        totalNumber[7] += 1;
                    }
                }
                if (passengers.get(i).getAge() > 68) {
                    totalNumber[11] += 1;
                }
                //third, set total number of gender
                if (passengers.get(i).getGender().equals(Person.Gender.MALE)) {
                    totalNumber[12] += 1;
                }
                if (passengers.get(i).getGender().equals(Person.Gender.FEMALE)) {
                    totalNumber[13] += 1;
                }
                //forth, set total number of body type
                if (passengers.get(i).getBodyType().equals(Person.BodyType.AVERAGE)) {
                    totalNumber[14] += 1;
                }
                if (passengers.get(i).getBodyType().equals(Person.BodyType.ATHLETIC)) {
                    totalNumber[15] += 1;
                }
                if (passengers.get(i).getBodyType().equals(Person.BodyType.OVERWEIGHT)) {
                    totalNumber[16] += 1;
                }
                // fifth, set total number of pregnant
                if (passengers.get(i).isPregnant()) {
                    totalNumber[17] += 1;
                }
                // sixth, set total number of YOU
                if (passengers.get(i).isYou()) {
                    totalNumber[26] += 1;
                }
            } else {
                // animal
                totalNumber[19] += 1;
                // set total number of species
                if (passengers.get(i).getSpecies().equals("cat")) {
                    totalNumber[20] += 1;
                }
                if (passengers.get(i).getSpecies().equals("dog")) {
                    totalNumber[21] += 1;
                }
                if (passengers.get(i).getSpecies().equals("rabbit")) {
                    totalNumber[22] += 1;
                }
                // set total number of pets
                if (passengers.get(i).isPet()) {
                    totalNumber[23] += 1;
                }
            }
        }
        // for pedestrians
        for (int i = 0; i < pedestrians.size(); i++) {
            //only person
            if (pedestrians.get(i).getClass().equals(new Person().getClass())) {
                totalNumber[18] += 1;
                // first, set total number of age category
                if (pedestrians.get(i).getAge() <= 4) {
                    totalNumber[8] += 1;
                }
                if (pedestrians.get(i).getAge() > 4 && pedestrians.get(i).getAge() <= 16) {
                    totalNumber[9] += 1;
                }
                if (pedestrians.get(i).getAge() > 16 && pedestrians.get(i).getAge() <= 68) {
                    totalNumber[10] += 1;
                    // second, set total number of profession (Adult)
                    if (pedestrians.get(i).getProfession().equals(Person.Profession.DOCTOR)) {
                        totalNumber[0] += 1;
                    }
                    if (pedestrians.get(i).getProfession().equals(Person.Profession.CEO)) {
                        totalNumber[1] += 1;
                    }
                    if (pedestrians.get(i).getProfession().equals(Person.Profession.CRIMINAL)) {
                        totalNumber[2] += 1;
                    }
                    if (pedestrians.get(i).getProfession().equals(Person.Profession.HOMELESS)) {
                        totalNumber[3] += 1;
                    }
                    if (pedestrians.get(i).getProfession().equals(Person.Profession.UNEMPLOYED)) {
                        totalNumber[4] += 1;
                    }
                    if (pedestrians.get(i).getProfession().equals(Person.Profession.UNKNOWN)) {
                        totalNumber[5] += 1;
                    }
                    if (pedestrians.get(i).getProfession().equals(Person.Profession.ENGINEER)) {
                        totalNumber[6] += 1;
                    }
                    if (pedestrians.get(i).getProfession().equals(Person.Profession.ARTIST)) {
                        totalNumber[7] += 1;
                    }
                }
                if (pedestrians.get(i).getAge() > 68) {
                    totalNumber[11] += 1;
                }
                //third, set total number of gender
                if (pedestrians.get(i).getGender().equals(Person.Gender.MALE)) {
                    totalNumber[12] += 1;
                }
                if (pedestrians.get(i).getGender().equals(Person.Gender.FEMALE)) {
                    totalNumber[13] += 1;
                }
                //forth, set total number of body type
                if (pedestrians.get(i).getBodyType().equals(Person.BodyType.AVERAGE)) {
                    totalNumber[14] += 1;
                }
                if (pedestrians.get(i).getBodyType().equals(Person.BodyType.ATHLETIC)) {
                    totalNumber[15] += 1;
                }
                if (pedestrians.get(i).getBodyType().equals(Person.BodyType.OVERWEIGHT)) {
                    totalNumber[16] += 1;
                }
                // fifth, set total number of pregnant
                if (pedestrians.get(i).isPregnant()) {
                    totalNumber[17] += 1;
                }
                // sixth, set total number of YOU
                if (pedestrians.get(i).isYou()) {
                    totalNumber[26] += 1;
                }
            } else {
                // animal
                totalNumber[19] += 1;
                // set total number of species
                if (pedestrians.get(i).getSpecies().equals("cat")) {
                    totalNumber[20] += 1;
                }
                if (pedestrians.get(i).getSpecies().equals("dog")) {
                    totalNumber[21] += 1;
                }
                if (pedestrians.get(i).getSpecies().equals("rabbit")) {
                    totalNumber[22] += 1;
                }
                // set total number of pets
                if (pedestrians.get(i).isPet()) {
                    totalNumber[23] += 1;
                }
            }
        }
        // set total number of legal crossing
        if (!this.isLegalCrossing) {
            totalNumber[24] += (passengers.size() + pedestrians.size());
        } else {
            totalNumber[25] += (passengers.size() + pedestrians.size());
        }

    }

    /**
     * set survival number after a decision has been made (passengers or pedestrians)
     */
    public void setSuvNumber() {
        if (this.getSurvival() == null) {
            System.out.println("WARNING: you have to decide who to survive first!");
            System.exit(0);
        }
        if (this.getSurvival().equals(Decision.PASSENGERS)) {
            for (int i = 0; i < passengers.size(); i++) {
                //only person
                if (passengers.get(i).getClass().equals(new Person().getClass())) {
                    totalSurvivalAge += this.passengers.get(i).getAge();
                    suvNumber[18] += 1;
                    // first, set survival number of age category
                    if (passengers.get(i).getAge() <= 4) {
                        suvNumber[8] += 1;
                    }
                    if (passengers.get(i).getAge() > 4 && passengers.get(i).getAge() <= 16) {
                        suvNumber[9] += 1;
                    }
                    if (passengers.get(i).getAge() > 16 && passengers.get(i).getAge() <= 68) {
                        suvNumber[10] += 1;
                        // second, set survival number of profession (Adult)
                        if (passengers.get(i).getProfession().equals(Person.Profession.DOCTOR)) {
                            suvNumber[0] += 1;
                        }
                        if (passengers.get(i).getProfession().equals(Person.Profession.CEO)) {
                            suvNumber[1] += 1;
                        }
                        if (passengers.get(i).getProfession().equals(Person.Profession.CRIMINAL)) {
                            suvNumber[2] += 1;
                        }
                        if (passengers.get(i).getProfession().equals(Person.Profession.HOMELESS)) {
                            suvNumber[3] += 1;
                        }
                        if (passengers.get(i).getProfession().equals(Person.Profession.UNEMPLOYED)) {
                            suvNumber[4] += 1;
                        }
                        if (passengers.get(i).getProfession().equals(Person.Profession.UNKNOWN)) {
                            suvNumber[5] += 1;
                        }
                        if (passengers.get(i).getProfession().equals(Person.Profession.ENGINEER)) {
                            suvNumber[6] += 1;
                        }
                        if (passengers.get(i).getProfession().equals(Person.Profession.ARTIST)) {
                            suvNumber[7] += 1;
                        }
                    }
                    if (passengers.get(i).getAge() > 68) {
                        suvNumber[11] += 1;
                    }
                    //third, set survival number of gender
                    if (passengers.get(i).getGender().equals(Person.Gender.MALE)) {
                        suvNumber[12] += 1;
                    }
                    if (passengers.get(i).getGender().equals(Person.Gender.FEMALE)) {
                        suvNumber[13] += 1;
                    }
                    //forth, set survival number of body type
                    if (passengers.get(i).getBodyType().equals(Person.BodyType.AVERAGE)) {
                        suvNumber[14] += 1;
                    }
                    if (passengers.get(i).getBodyType().equals(Person.BodyType.ATHLETIC)) {
                        suvNumber[15] += 1;
                    }
                    if (passengers.get(i).getBodyType().equals(Person.BodyType.OVERWEIGHT)) {
                        suvNumber[16] += 1;
                    }
                    // fifth, set survival number of pregnant
                    if (passengers.get(i).isPregnant()) {
                        suvNumber[17] += 1;
                    }
                    // sixth, set survival number of YOU
                    if (passengers.get(i).isYou()) {
                        suvNumber[26] += 1;
                    }
                } else {
                    // animal
                    suvNumber[19] += 1;
                    // set survival number of species
                    if (passengers.get(i).getSpecies().equals("cat")) {
                        suvNumber[20] += 1;
                    }
                    if (passengers.get(i).getSpecies().equals("dog")) {
                        suvNumber[21] += 1;
                    }
                    if (passengers.get(i).getSpecies().equals("rabbit")) {
                        suvNumber[22] += 1;
                    }
                    // set survival number of pets
                    if (passengers.get(i).isPet()) {
                        suvNumber[23] += 1;
                    }
                }
            }
            // set survival number of legal crossing
            if (!this.isLegalCrossing) {
                suvNumber[24] += (passengers.size());
            } else {
                suvNumber[25] += (passengers.size());
            }
        } else {
            for (int i = 0; i < pedestrians.size(); i++) {
                //only person
                if (pedestrians.get(i).getClass().equals(new Person().getClass())) {
                    this.totalSurvivalAge += this.pedestrians.get(i).getAge();
                    suvNumber[18] += 1;
                    // first, set survival number of age category
                    if (pedestrians.get(i).getAge() <= 4) {
                        suvNumber[8] += 1;
                    }
                    if (pedestrians.get(i).getAge() > 4 && pedestrians.get(i).getAge() <= 16) {
                        suvNumber[9] += 1;
                    }
                    if (pedestrians.get(i).getAge() > 16 && pedestrians.get(i).getAge() <= 68) {
                        suvNumber[10] += 1;
                        // second, set survival number of profession (Adult)
                        if (pedestrians.get(i).getProfession().equals(Person.Profession.DOCTOR)) {
                            suvNumber[0] += 1;
                        }
                        if (pedestrians.get(i).getProfession().equals(Person.Profession.CEO)) {
                            suvNumber[1] += 1;
                        }
                        if (pedestrians.get(i).getProfession().equals(Person.Profession.CRIMINAL)) {
                            suvNumber[2] += 1;
                        }
                        if (pedestrians.get(i).getProfession().equals(Person.Profession.HOMELESS)) {
                            suvNumber[3] += 1;
                        }
                        if (pedestrians.get(i).getProfession().equals(Person.Profession.UNEMPLOYED)) {
                            suvNumber[4] += 1;
                        }
                        if (pedestrians.get(i).getProfession().equals(Person.Profession.UNKNOWN)) {
                            suvNumber[5] += 1;
                        }
                        if (pedestrians.get(i).getProfession().equals(Person.Profession.ENGINEER)) {
                            suvNumber[6] += 1;
                        }
                        if (pedestrians.get(i).getProfession().equals(Person.Profession.ARTIST)) {
                            suvNumber[7] += 1;
                        }
                    }
                    if (pedestrians.get(i).getAge() > 68) {
                        suvNumber[11] += 1;
                    }
                    //third, set survival number of gender
                    if (pedestrians.get(i).getGender().equals(Person.Gender.MALE)) {
                        suvNumber[12] += 1;
                    }
                    if (pedestrians.get(i).getGender().equals(Person.Gender.FEMALE)) {
                        suvNumber[13] += 1;
                    }
                    //forth, set survival number of body type
                    if (pedestrians.get(i).getBodyType().equals(Person.BodyType.AVERAGE)) {
                        suvNumber[14] += 1;
                    }
                    if (pedestrians.get(i).getBodyType().equals(Person.BodyType.ATHLETIC)) {
                        suvNumber[15] += 1;
                    }
                    if (pedestrians.get(i).getBodyType().equals(Person.BodyType.OVERWEIGHT)) {
                        suvNumber[16] += 1;
                    }
                    // fifth, set survival number of pregnant
                    if (pedestrians.get(i).isPregnant()) {
                        suvNumber[17] += 1;
                    }
                    // sixth, set survival number of YOU
                    if (pedestrians.get(i).isYou()) {
                        suvNumber[26] += 1;
                    }
                } else {
                    // animal
                    suvNumber[19] += 1;
                    // set survival number of species
                    if (pedestrians.get(i).getSpecies().equals("cat")) {
                        suvNumber[20] += 1;
                    }
                    if (pedestrians.get(i).getSpecies().equals("dog")) {
                        suvNumber[21] += 1;
                    }
                    if (pedestrians.get(i).getSpecies().equals("rabbit")) {
                        suvNumber[22] += 1;
                    }
                    // set survival number of pets
                    if (pedestrians.get(i).isPet()) {
                        suvNumber[23] += 1;
                    }
                }
            }
            // set survival number of legal crossing
            if (!this.isLegalCrossing) {
                suvNumber[24] += (pedestrians.size());
            } else {
                suvNumber[25] += (pedestrians.size());
            }
        }

    }

    /**
     * toString method
     *
     * @return
     */
    @Override
    public String toString() {
        String legalCross;
        String allPassengers = "";
        String allPedestrians = "";
        String header = "======================================\n" +
                "# Scenario\n" +
                "======================================\n";
        if (this.isLegalCrossing()) {
            legalCross = "yes";
        } else {
            legalCross = "no";
        }
        for (int i = 0; i < this.getPassengerCount(); i++) {
            if (i == this.getPassengerCount() - 1) {
                allPassengers = allPassengers + (this.passengers.get(i).toString());
            } else {
                allPassengers = allPassengers + (this.passengers.get(i).toString()) + "\n- ";
            }
        }
        for (int i = 0; i < this.getPedestrianCount(); i++) {
            if (i == this.getPedestrianCount() - 1) {
                allPedestrians = allPedestrians + (this.pedestrians.get(i).toString());
            } else {
                allPedestrians = allPedestrians + (this.pedestrians.get(i).toString()) + "\n- ";
            }
        }
        return header + "Legal Crossing: " + legalCross + "\n" + "Passengers (" + this.getPassengerCount()
                + ")\n" + "- " + allPassengers + "\nPedestrians (" + this.getPedestrianCount() + ")\n" +
                "- " + allPedestrians;
    }
}
