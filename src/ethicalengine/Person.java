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

public class Person extends Character {

    private Profession profession;
    private boolean isPregnant;
    private boolean isYou = false;

    /**
     * enum type Profession
     */
    public enum Profession {
        DOCTOR, CEO, CRIMINAL, HOMELESS, UNEMPLOYED, UNKNOWN, NONE, ENGINEER, ARTIST
    }

    /**
     * enum type AgeCategory
     */
    public enum AgeCategory {
        BABY, CHILD, ADULT, SENIOR
    }

    /**
     * constructor
     */
    public Person() {
        super();
        this.setProfession(Profession.NONE);
        this.setPregnant(false);
    }

    /**
     * constructor
     *
     * @param age
     * @param profession
     * @param gender
     * @param bodytype
     * @param isPregnant
     */
    public Person(int age, Profession profession, Gender gender, BodyType bodytype, boolean
            isPregnant) {
        super(age, gender, bodytype);
        if (this.getAgeCategory().equals(AgeCategory.ADULT)) {
            this.profession = profession;
        } else {
            this.profession = Profession.NONE;
        }
        if (isPregnant) {
            if ((gender.equals(Gender.FEMALE)) && (this.getAgeCategory().equals(AgeCategory.ADULT))) {
                this.isPregnant = isPregnant;
            } else {
                System.out.println("WARNING: ONLY Female Adult can be pregnant!");
                this.isPregnant = false;
            }
        } else {
            this.isPregnant = false;
        }
    }

    /**
     * copy constructor
     *
     * @param otherPerson
     */
    public Person(Person otherPerson) {
        this.setAge(otherPerson.getAge());
        this.setGender(otherPerson.getGender());
        this.setBodyType(otherPerson.getBodyType());
        this.profession = otherPerson.getProfession();
        this.isPregnant = otherPerson.isPregnant();
    }

    /**
     * getter function
     *
     * @return
     */
    public Profession getProfession() {
        return this.profession;
    }

    /**
     * getter function
     *
     * @return
     */
    public AgeCategory getAgeCategory() {
        int a = this.getAge();
        if (a <= 4) {
            return AgeCategory.BABY;
        } else if (a > 4 && a <= 16) {
            return AgeCategory.CHILD;
        } else if (a > 16 && a <= 68) {
            return AgeCategory.ADULT;
        } else {
            return AgeCategory.SENIOR;
        }
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
    public boolean isYou() {
        return this.isYou;
    }

    /**
     * setter function
     *
     * @param profession
     */
    public void setProfession(Profession profession) {
        if (this.getAgeCategory().equals(AgeCategory.ADULT)) {
            this.profession = profession;
        } else {
            this.profession = Profession.NONE;
        }
    }

    /**
     * setter function
     *
     * @param pregnant
     */
    public void setPregnant(boolean pregnant) {
        this.isPregnant = pregnant;
    }

    /**
     * setter function
     *
     * @param isYou
     */
    public void setAsYou(boolean isYou) {
        this.isYou = isYou;
    }

    /**
     * toString function
     *
     * @return
     */
    @Override
    public String toString() {
        String ageCate = this.getAgeCategory().toString().toLowerCase();
        String g = this.getGender().toString().toLowerCase();
        String body = this.getBodyType().toString().toLowerCase();
        String you = "you";
        String preg = "pregnant";
        String prof = this.profession.toString().toLowerCase();


        if (this.isYou()) {
            if (this.getAgeCategory().equals(AgeCategory.ADULT)) {
                if (this.isPregnant()) {
                    return you + " " + body + " " + ageCate + " " + prof + " " + g + " " + preg;
                } else {
                    return you + " " + body + " " + ageCate + " " + prof + " " + g;
                }
            } else {
                if (this.isPregnant()) {
                    return you + " " + body + " " + ageCate + " " + g + " " + preg;
                } else {
                    return you + " " + body + " " + ageCate + " " + g;
                }
            }

        } else {
            if (this.getAgeCategory().equals(AgeCategory.ADULT)) {
                if (this.isPregnant()) {
                    return body + " " + ageCate + " " + prof + " " + g + " " + preg;
                } else {
                    return body + " " + ageCate + " " + prof + " " + g;
                }
            } else {
                if (this.isPregnant()) {
                    return body + " " + ageCate + " " + g + " " + preg;
                } else {
                    return body + " " + ageCate + " " + g;
                }
            }
        }
    }

}
