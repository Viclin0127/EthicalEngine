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

// abstract class
public abstract class Character {
    private static int DEFAULT_AGE = 0;
    private int age;
    private Gender gender;
    private BodyType bodyType;
    private boolean isYou;
    private boolean isPregnant;
    private Person.Profession profession;
    private String species;
    private boolean isPet;

    /**
     * enum type of Gender
     */
    public enum Gender {
        MALE, FEMALE, UNKNOWN
    }

    /**
     * enum type of BodyType
     */
    public enum BodyType {
        AVERAGE, ATHLETIC, OVERWEIGHT, UNSPECIFIED
    }

    /**
     * constructor
     */
    public Character() {
        this.age = DEFAULT_AGE;
        this.gender = Gender.UNKNOWN;
        this.bodyType = BodyType.UNSPECIFIED;
    }

    /**
     * constructor
     *
     * @param age
     * @param gender
     * @param bodytype
     */
    public Character(int age, Gender gender, BodyType bodytype) {
        this.age = age;
        this.gender = gender;
        this.bodyType = bodytype;
    }

    /**
     * copy constructor
     *
     * @param character
     */
    public Character(Character character) {
        this.age = character.getAge();
        this.gender = character.getGender();
        this.bodyType = character.getBodyType();
    }

    /**
     * getter function
     *
     * @return
     */
    public int getAge() {
        return this.age;
    }

    /**
     * getter function
     *
     * @return
     */
    public Gender getGender() {
        return this.gender;
    }

    /**
     * getter function
     *
     * @return
     */
    public BodyType getBodyType() {
        return this.bodyType;
    }

    /**
     * getter function
     *
     * @return
     */
    public boolean isPregnant() {
        return this.isPregnant;
    }

    /**
     * getter function
     *
     * @return
     */
    public Person.Profession getProfession() {
        return this.profession;
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
     * @param age
     */
    public void setAge(int age) {
        if (age < 0) {
            System.out.println("WARNING: Age could not be negative! Set to DEFAULT: 10y");
            this.age = DEFAULT_AGE;
        } else {
            this.age = age;
        }
    }

    /**
     * setter function
     *
     * @param gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * setter function
     *
     * @param bodytype
     */
    public void setBodyType(BodyType bodytype) {
        this.bodyType = bodytype;
    }

    /**
     * getter function
     *
     * @return
     */
    public boolean isYou() {
        return isYou;
    }

    /**
     * abstract toString function
     *
     * @return
     */
    public abstract String toString();
}
