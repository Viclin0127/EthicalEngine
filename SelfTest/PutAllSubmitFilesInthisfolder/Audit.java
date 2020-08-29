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

import ethicalengine.Scenario;
import ethicalengine.ScenarioGenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Audit {

    private String auditType;
    private int runs;
    private int totalSurvivalAge;
    private int totalSurvivalPerson;
    private int[] totalNumberAudit = new int[27];
    private int[] suvNumberAudit = new int[27];
    private double[] suvRateAudit = new double[27];
    // due to sorting the result, survival number and total number array elements may change their positions
    // use dynamic characteristic array to look up which characters are
    private String[] dynamicCharacteristic = {"doctor", "ceo", "criminal", "homeless", "unemployed", "unknown",
            "engineer", "artist", "baby", "child", "adult", "senior", "male", "female", "average", "athletic",
            "overweight", "pregnant", "person", "animal", "cat", "dog", "rabbit", "pets", "red", "green", "you"};
    public EthicalEngine engine = new EthicalEngine();
    public ScenarioGenerator scg = new ScenarioGenerator();
    private ArrayList<Scenario> scenarioList = null;

    /**
     * constructor
     */
    public Audit() {
        this.auditType = "Unspecified";
    }

    /**
     * constructor
     *
     * @param scenarios
     */
    public Audit(ArrayList<Scenario> scenarios) {
        this.auditType = "Unspecified";
        this.scenarioList = scenarios;
    }

    /**
     * // getter function
     *
     * @return
     */
    public int getRuns() {
        return this.runs;
    }

    /**
     * // getter function
     *
     * @return
     */
    public int getTotalSurvivalAge() {
        return this.totalSurvivalAge;
    }

    /**
     * // getter function
     *
     * @return
     */
    public int getTotalSurvivalPerson() {
        return this.totalSurvivalPerson;
    }

    /**
     * // getter function
     *
     * @return
     */
    public int[] getTotalNumberAudit() {
        return this.totalNumberAudit;
    }

    /**
     * // getter function
     *
     * @return
     */
    public int[] getSuvNumberAudit() {
        return this.suvNumberAudit;
    }

    /**
     * // getter function
     *
     * @return
     */
    public double[] getSuvRateAudit() {
        return this.suvRateAudit;
    }

    /**
     * // getter function
     *
     * @return
     */
    public String[] getDynamicCharacteristic() {
        return this.dynamicCharacteristic;
    }

    /**
     * // getter function
     *
     * @return
     */
    public String getAuditType() {
        return this.auditType;
    }

    /**
     * setter function
     *
     * @param name
     */
    public void setAuditType(String name) {
        this.auditType = name;
    }

    /**
     * setter function
     *
     * @param runs
     */
    public void setRuns(int runs) {
        this.runs = runs;
    }

    /**
     * setter function
     *
     * @param totalSurvivalAge
     */
    public void setTotalSurvivalAge(int totalSurvivalAge) {
        this.totalSurvivalAge = totalSurvivalAge;
    }

    /**
     * setter function
     *
     * @param totalSurvivalPerson
     */
    public void setTotalSurvivalPerson(int totalSurvivalPerson) {
        this.totalSurvivalPerson = totalSurvivalPerson;
    }

    /**
     * setter function
     *
     * @param totalNumberAudit
     */
    public void setTotalNumberAudit(int[] totalNumberAudit) {
        this.totalNumberAudit = totalNumberAudit;
    }

    /**
     * setter function
     *
     * @param index
     * @param totalNumberAudit
     */
    public void setTotalNumberAudit(int index, int totalNumberAudit) {
        this.totalNumberAudit[index] = totalNumberAudit;
    }

    /**
     * setter function
     *
     * @param suvNumberAudit
     */
    public void setSuvNumberAudit(int[] suvNumberAudit) {
        this.suvNumberAudit = suvNumberAudit;
    }

    /**
     * setter function
     *
     * @param index
     * @param suvNumberAudit
     */
    public void setSuvNumberAudit(int index, int suvNumberAudit) {
        this.suvNumberAudit[index] = suvNumberAudit;
    }

    /**
     * setter function
     *
     * @param suvRateAudit
     */
    public void setSuvRateAudit(double[] suvRateAudit) {
        this.suvRateAudit = suvRateAudit;
    }

    /**
     * setter function
     *
     * @param index
     * @param suvRateAudit
     */
    public void setSuvRateAudit(int index, double suvRateAudit) {
        this.suvRateAudit[index] = suvRateAudit;
    }

    /**
     * RUN method, for "interactive" mode users to audit their decision
     *
     * @param scenario
     * @param decision
     */
    public void run(Scenario scenario, Scenario.Decision decision) {
        // calculate run time(aggregate)
        this.runs += 1;
        scenario.setTotalNumber();
        scenario.setSurvival(decision);
        scenario.setSuvNumber();
        // store statistic details by using dynamic characteristic array to match the characters
        // and save into Audit array
        for (int j = 0; j < dynamicCharacteristic.length; j++) {
            for (int k = 0; k < scenario.getCharacteristic().length; k++) {
                if (dynamicCharacteristic[j].equals(scenario.getCharacteristic()[k])) {
                    totalNumberAudit[j] += scenario.getTotalNumber()[k];
                    suvNumberAudit[j] += scenario.getSuvNumber()[k];
                    if (dynamicCharacteristic[j].equals("person")) {
                        this.totalSurvivalPerson = suvNumberAudit[j];
                    }
                }
            }
        }
        this.totalSurvivalAge += scenario.getTotalSurvivalAge();
    }

    /**
     * RUN method, for "config using" mode users to audit file scenarios and make algorithm's decision
     */
    public void run() {
        if (this.scenarioList == null) {
            System.out.println("WARNING: Scenario List is empty");
            System.exit(0);
        }
        // aggregate run times
        this.runs += this.scenarioList.size();
        // for each scenario, run my algorithm decision
        for (int i = 0; i < this.scenarioList.size(); i++) {
            scenarioList.get(i).setTotalNumber();
            engine.decide(scenarioList.get(i));
            scenarioList.get(i).setSuvNumber();


            // store statistic details by using dynamic characteristic array to match the characters
            // and save into Audit array
            for (int j = 0; j < dynamicCharacteristic.length; j++) {
                for (int k = 0; k < scenarioList.get(i).getCharacteristic().length; k++) {
                    if (dynamicCharacteristic[j].equals(scenarioList.get(i).getCharacteristic()[k])) {
                        totalNumberAudit[j] += scenarioList.get(i).getTotalNumber()[k];
                        suvNumberAudit[j] += scenarioList.get(i).getSuvNumber()[k];
                    }
                }
            }
            this.totalSurvivalAge += scenarioList.get(i).getTotalSurvivalAge();
        }
        this.totalSurvivalPerson = this.suvNumberAudit[18];
    }

    /**
     * RUN method, for non command line mode, setting run times and run algorithm decision
     * DOCTOR, CEO, CRIMINAL, HOMELESS, UNEMPLOYED, UNKNOWN, ENGINEER, ARTIST
     * 0 ,  1 ,     2   ,   3     ,     4     ,    5   ,    6    ,    7
     * BABY, CHILD, ADULT, SENIOR, MALE, FEMALE, AVERAGE, ATHLETIC, OVERWEIGHT
     * 8 ,   9  ,   10 ,    11 ,  12 ,   13  ,   14   ,    15   ,     16
     * PREGNANT, PERSON, ANIMAL, CAT, DOG, RABBIT, PETS, RED, GREEN
     * 17    ,   18  ,   19  , 20 ,  21,   22  ,  23 ,  24,   2
     *
     * @param runs
     */
    public void run(int runs) {
        // aggregate run times
        this.runs += runs;

        // generate scenarios and run algorithm decision
        for (int i = 0; i < runs; i++) {
            Scenario scen = scg.generate();
            scen.setTotalNumber();
            engine.decide(scen);
            scen.setSuvNumber();


            // store statistic details by using dynamic characteristic array to match the characters
            // and save into Audit array
            for (int j = 0; j < dynamicCharacteristic.length; j++) {
                for (int k = 0; k < scen.getCharacteristic().length; k++) {
                    if (dynamicCharacteristic[j].equals(scen.getCharacteristic()[k])) {
                        totalNumberAudit[j] += scen.getTotalNumber()[k];
                        suvNumberAudit[j] += scen.getSuvNumber()[k];
                    }
                }
            }
            this.totalSurvivalAge += scen.getTotalSurvivalAge();
        }
        this.totalSurvivalPerson = this.suvNumberAudit[18];
    }

    /**
     * Sorting function, using insertion sort to implement this function
     */
    public void sortArrayDesc() {
        // set up survived rate
        for (int i = 0; i < this.suvRateAudit.length; i++) {
            if (totalNumberAudit[i] != 0) {
                this.suvRateAudit[i] = (this.suvNumberAudit[i] / (double) this.totalNumberAudit[i]);
            } else {
                this.suvRateAudit[i] = 0.0;
            }
        }

        // insertion sort
        for (int j = 1; j < this.suvRateAudit.length; j++) {
            double v = this.suvRateAudit[j];
            int w = this.totalNumberAudit[j];
            int x = this.suvNumberAudit[j];
            String y = this.dynamicCharacteristic[j];
            int k = j - 1;
            while ((k >= 0) && (v < this.suvRateAudit[k])) {
                this.suvRateAudit[k + 1] = this.suvRateAudit[k];
                this.totalNumberAudit[k + 1] = this.totalNumberAudit[k];
                this.suvNumberAudit[k + 1] = this.suvNumberAudit[k];
                this.dynamicCharacteristic[k + 1] = this.dynamicCharacteristic[k];
                k = k - 1;
            }
            this.suvRateAudit[k + 1] = v;
            this.totalNumberAudit[k + 1] = w;
            this.suvNumberAudit[k + 1] = x;
            this.dynamicCharacteristic[k + 1] = y;
        }
    }

    /**
     * toString method
     *
     * @return
     */
    public String toString() {
        this.sortArrayDesc();
        String header = "======================================\n";
        String title = "# " + this.getAuditType() + "\n";
        String runs = "- % SAVED AFTER " + Integer.toString(this.runs) + " RUNS\n";
        String statistic = "";
        for (int i = this.suvRateAudit.length - 1; i >= 0; i--) {
            if (this.totalNumberAudit[i] != 0) {
                statistic = statistic + this.dynamicCharacteristic[i] + ": "
                        + String.format("%.1f", Math.floor(this.suvRateAudit[i] * 10.0) / 10.0) + "\n";
            }
        }
        String end = "--\n";
        String averageAge = "average age: "
                + String.format("%.1f", (this.totalSurvivalAge / (double) this.totalSurvivalPerson));
        return header + title + header + runs + statistic + end + averageAge;

    }

    /**
     * print toString method
     */
    public void printStatistic() {
        System.out.println(this.toString());
    }

    /**
     * save audit results into file by using filepath
     *
     * @param filepath
     * @throws IOException
     */
    public void printToFile(String filepath) throws IOException {
        PrintWriter writer = new PrintWriter(filepath);
        writer.println(this.runs);
        writer.println(this.totalSurvivalAge);
        writer.println(this.totalSurvivalPerson);
        for (int i = 0; i < this.dynamicCharacteristic.length; i++) {
            writer.println(this.dynamicCharacteristic[i]);
            writer.println(this.suvNumberAudit[i]);
            writer.println(this.totalNumberAudit[i]);
            writer.println(this.suvRateAudit[i]);
        }
        writer.close();
    }
}
