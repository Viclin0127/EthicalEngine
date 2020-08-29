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
import java.util.Random;

public class ScenarioGenerator {
    private long seed;
    private int passengerCountMinimum = 1;
    private int passengerCountMaximum = 5;
    private int pedestrianCountMinimum = 1;
    private int pedestrianCountMaximum = 5;
    private String[] speciesList = {"cat", "dog", "rabbit"};
    Random r = new Random(seed);
    private int age = r.nextInt(100);
    private int prof = r.nextInt(100);
    private int gen = r.nextInt(50);
    private int body = r.nextInt(100);
    private int preg = r.nextInt(2);
    private int species = r.nextInt(100);
    private int animalIndex = r.nextInt(50);
    private int legalCross = r.nextInt(10);
    private int isYouIndex = r.nextInt(3);
    private int isPetIndex = r.nextInt(2);

    /**
     * constructor
     */
    public ScenarioGenerator() {
        this.seed = 77;
    }

    /**
     * constructor
     *
     * @param seed
     */
    public ScenarioGenerator(long seed) {
        this.seed = seed;
    }

    /**
     * constructor
     *
     * @param seed
     * @param passengerCountMinimum
     * @param passengerCountMaximum
     * @param pedestrianCountMinimum
     * @param pedestrianCountMaximum
     */
    public ScenarioGenerator(long seed, int passengerCountMinimum, int passengerCountMaximum,
                             int pedestrianCountMinimum, int pedestrianCountMaximum) {
        this.seed = seed;
        this.passengerCountMinimum = passengerCountMinimum;
        this.passengerCountMaximum = passengerCountMaximum;
        this.pedestrianCountMinimum = pedestrianCountMinimum;
        this.pedestrianCountMaximum = pedestrianCountMaximum;
    }

    /**
     * getter function
     *
     * @return
     */
    public int getPassengerCountMaximum() {
        return this.passengerCountMaximum;
    }

    /**
     * getter function
     *
     * @return
     */
    public int getPedestrianCountMaximum() {
        return this.pedestrianCountMaximum;
    }

    /**
     * generate a random Person class
     *
     * @return
     */
    public Person getRandomPerson() {

        Person person = new Person();
        person.setAge(age);

        // set profession(only adult)
        if (person.getAgeCategory().equals(Person.AgeCategory.ADULT)) {
            if (prof % 8 == 0) {
                person.setProfession(Person.Profession.DOCTOR);
            } else if (prof % 8 == 1) {
                person.setProfession(Person.Profession.CRIMINAL);
            } else if (prof % 8 == 2) {
                person.setProfession(Person.Profession.HOMELESS);
            } else if (prof % 8 == 3) {
                person.setProfession(Person.Profession.UNEMPLOYED);
            } else if (prof % 8 == 4) {
                person.setProfession(Person.Profession.UNKNOWN);
            } else if (prof % 8 == 5) {
                person.setProfession(Person.Profession.ENGINEER);
            } else if (prof % 8 == 6) {
                person.setProfession(Person.Profession.ARTIST);
            } else {
                person.setProfession(Person.Profession.CEO);
            }
        } else {
            person.setProfession(Person.Profession.NONE);
        }
        //set gender
        if (gen < 10) {
            person.setGender(Person.Gender.UNKNOWN);
        } else {
            if (gen % 2 == 0) {
                person.setGender(Person.Gender.MALE);
            } else {
                person.setGender(Person.Gender.FEMALE);
            }
        }
        //set body type
        if (body % 4 == 0) {
            person.setBodyType(Person.BodyType.ATHLETIC);
        } else if (body % 4 == 1) {
            person.setBodyType(Person.BodyType.AVERAGE);
        } else if (body % 4 == 2) {
            person.setBodyType(Person.BodyType.OVERWEIGHT);
        } else {
            person.setBodyType(Person.BodyType.UNSPECIFIED);
        }
        // set pregnant (only female, adult can be pregnant)
        if (person.getGender().equals(Person.Gender.FEMALE) && (preg % 2 == 0) &&
                (person.getAgeCategory().equals(Person.AgeCategory.ADULT))) {
            person.setPregnant(true);
        } else {
            person.setPregnant(false);
        }
        age = r.nextInt(100);
        prof = r.nextInt(100);
        gen = r.nextInt(50);
        body = r.nextInt(100);
        preg = r.nextInt(2);

        return person;
    }

    /**
     * generate a random Animal class
     *
     * @return
     */
    public Animal getRandomAnimal() {
        Animal animal = new Animal();
        animal.setAge(age);
        // set gender
        if (gen < 10) {
            animal.setGender(Animal.Gender.UNKNOWN);
        } else {
            if (gen % 2 == 0) {
                animal.setGender(Animal.Gender.MALE);
            } else {
                animal.setGender(Animal.Gender.FEMALE);
            }
        }
        // set body type
        if (body % 4 == 0) {
            animal.setBodyType(Animal.BodyType.ATHLETIC);
        } else if (body % 4 == 1) {
            animal.setBodyType(Animal.BodyType.AVERAGE);
        } else if (body % 4 == 2) {
            animal.setBodyType(Animal.BodyType.OVERWEIGHT);
        } else {
            animal.setBodyType(Animal.BodyType.UNSPECIFIED);
        }
        // set species
        if (species % 3 == 0) {
            animal.setSpecies(speciesList[0]);
        } else if (species % 3 == 1) {
            animal.setSpecies(speciesList[1]);
        } else {
            animal.setSpecies(speciesList[2]);
        }

        //set isPet
        if (animal.getSpecies().equals("cat") || animal.getSpecies().equals("dog")) {
            if (isPetIndex % 2 == 0) {
                animal.setPet(true);
            }
        }
        age = r.nextInt(100);
        gen = r.nextInt(50);
        body = r.nextInt(100);
        species = r.nextInt(100);
        isPetIndex = r.nextInt(2);
        return animal;
    }

    /**
     * setter function
     *
     * @param min
     */
    public void setPassengerCountMin(int min) {
        if (min > this.getPassengerCountMaximum()) {
            System.out.println("WARNING: min can not be larger than max");
            this.passengerCountMinimum = passengerCountMaximum;
        } else {
            this.passengerCountMinimum = min;
        }
    }

    /**
     * setter function
     *
     * @param max
     */
    public void setPassengerCountMax(int max) {
        this.passengerCountMaximum = max;
    }

    /**
     * setter function
     *
     * @param min
     */
    public void setPedestrianCountMin(int min) {
        if (min > this.getPedestrianCountMaximum()) {
            System.out.println("WARNING: min can not be larger than max");
            this.pedestrianCountMinimum = pedestrianCountMaximum;
        } else {
            this.pedestrianCountMinimum = min;
        }
    }

    /**
     * setter function
     *
     * @param max
     */
    public void setPedestrianCountMax(int max) {
        this.pedestrianCountMaximum = max;
    }

    /**
     * generate a Scenario with random Persons and Animals (in passengers and pedestrians)
     * meet passengers/pedestrians number constraint
     *
     * @return
     */
    public Scenario generate() {
        boolean legalCrossing;
        ArrayList<Character> passengers = new ArrayList<Character>();
        ArrayList<Character> pedestrians = new ArrayList<Character>();

        // generate passengers recursively below the maximum number
        for (int i = 0; i < this.getPassengerCountMaximum(); i++) {
            if (animalIndex < 10) {
                Animal a = this.getRandomAnimal();
                passengers.add(a);
            } else {
                if (isYouIndex == 1) {
                    Person myPerson = this.getRandomPerson();
                    myPerson.setAsYou(true);
                    passengers.add(myPerson);
                    isYouIndex = -1;
                } else {
                    Person p = this.getRandomPerson();
                    passengers.add(p);
                }
            }
            animalIndex = r.nextInt(50);
        }

        //generate pedestrians recursively below the maximum number
        for (int i = 0; i < this.getPedestrianCountMaximum(); i++) {
            if (animalIndex < 15) {
                Animal a = this.getRandomAnimal();
                pedestrians.add(a);
            } else {
                if (isYouIndex == 2) {
                    Person myPerson = this.getRandomPerson();
                    myPerson.setAsYou(true);
                    pedestrians.add(myPerson);
                    isYouIndex = -1;
                } else {
                    Person p = this.getRandomPerson();
                    pedestrians.add(p);
                }
            }
            animalIndex = r.nextInt(50);
        }
        //set legal cross or not
        if (legalCross % 2 == 0) {
            legalCrossing = true;
        } else {
            legalCrossing = false;
        }
        // generate scenario, random index set for the next scenario
        Scenario scen = new Scenario(passengers, pedestrians, legalCrossing);
        animalIndex = r.nextInt(50);
        legalCross = r.nextInt(10);
        isYouIndex = r.nextInt(3);
        return scen;
    }
}
