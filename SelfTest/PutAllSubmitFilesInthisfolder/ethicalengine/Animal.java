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

public class Animal extends Character {
    private String species;
    private boolean isPet = false;

    /**
     * constructor
     */
    public Animal() {
        super();
        this.species = "Undefined Animal";
        this.isPet = false;
    }

    /**
     * constructor
     *
     * @param species
     */
    public Animal(String species) {
        this.species = species;
    }

    /**
     * copy constructor
     *
     * @param otherAnimal
     */
    public Animal(Animal otherAnimal) {
        this.species = otherAnimal.getSpecies();
    }

    /**
     * getter function
     *
     * @return
     */
    public String getSpecies() {
        return this.species;
    }

    /**
     * getter function
     *
     * @return
     */
    public boolean isPet() {
        return this.isPet;
    }

    /**
     * setter function
     *
     * @param species
     */
    public void setSpecies(String species) {
        this.species = species;
    }

    /**
     * setter function
     *
     * @param isPet
     */
    public void setPet(boolean isPet) {
        this.isPet = isPet;
    }

    /**
     * toString method
     *
     * @return
     */
    @Override
    public String toString() {
        String pet = "is pet";
        if (this.isPet()) {
            return species + " " + pet;
        } else {
            return species;
        }
    }
}
