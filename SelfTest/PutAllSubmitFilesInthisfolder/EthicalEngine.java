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

import ethicalengine.Animal;
import ethicalengine.Character;
import ethicalengine.Person;
import ethicalengine.Scenario;
import ethicalengine.ScenarioGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// coordinate the program flow
// -i , -i -c path, -i -r path, -i -c path -r path2, -c path, -c path -r path2
public class EthicalEngine {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        // if there are some input arguments
        if (args.length != 0) {
            // help method, print help document for user
            if (args[0].equals("--help") || args[0].equals("-h")) {
                PrintHelp();
            }
            // config method, read file and run ethical engine decision
            // -c path, -c path -r path2
            if (args[0].equals("--config") || args[0].equals("-c")) {
                // if wrong argument in --config method, print help document
                // -c
                if (args.length == 1) {
                    PrintHelp();
                }
                //-c -r or -c --result
                if ((args.length == 2) && (args[1].equals("-r") || args[1].equals("--result"))) {
                    PrintHelp();
                }
                // -c -r path or -c path -r
                if ((args.length == 3) &&
                        (args[1].equals("-r") || args[1].equals("--result") || args[2].equals("-r") || args[1].equals("--result"))) {
                    PrintHelp();
                }

                // --config method
                BufferedReader reader;
                ArrayList<Scenario> scenarioList = new ArrayList<Scenario>();
                try {
                    int countLine = 0; // use to track particluar line
                    reader = new BufferedReader(new FileReader(args[1]));
                    // read header, can be ignored
                    String line = reader.readLine();
                    countLine += 1;
                    // start from second line
                    line = reader.readLine();
                    countLine += 1;

                    String title = line.split(":")[0];
                    while (title.equals("scenario")) {
                        // create scenario in the end of while loop and set legal cross
                        String scenDetails = line.split(":")[1];
                        scenDetails = scenDetails.split(",")[0];
                        ArrayList<Character> passengers = new ArrayList<Character>();
                        ArrayList<Character> pedestrians = new ArrayList<Character>();
                        boolean legalCross;
                        if (scenDetails.equals("green")) {
                            legalCross = true;
                        } else {
                            legalCross = false;
                        }
                        //start reading passengers and pedestrians
                        line = reader.readLine();
                        countLine += 1;
                        String[] content = line.split(",");
                        // catch invalid line error, jump into next line
                        while (content.length != 10) {
                            try {
                                if (content.length == 1) {
                                    break;
                                }
                                throw new InvalidDataFormatException(countLine);
                            } catch (InvalidDataFormatException e) {
                                System.out.println(e.getMessage());
                                line = reader.readLine();
                                content = line.split(",");
                                countLine += 1;
                            }
                        }
                        while ((content[0].equals("person") || content[0].equals("animal"))) {
                            // set default value
                            Person.Gender gender = Person.Gender.UNKNOWN;
                            int age = 0;
                            // catch number format error, set to default value
                            try {
                                age = Integer.parseInt(content[2]);
                            } catch (NumberFormatException e) {
                                System.out.printf
                                        ("WARNING: invalid number format in config file in line <%d>\n", countLine);
                            }
                            Person.BodyType bodyType = Person.BodyType.UNSPECIFIED;
                            Person.Profession prof = Person.Profession.NONE;
                            boolean isPregnant;
                            boolean isYou;
                            String species;
                            boolean isPet;
                            String role = content[9];
                            // create Person
                            if (content[0].equals("person")) {
                                //gender
                                if (content[1] != null) {
                                    int genderCheck = 0;
                                    for (int i = 0; i < Person.Gender.values().length; i++) {
                                        if (content[1].equals(Person.Gender.values()[i].toString().toLowerCase())) {
                                            gender = Person.Gender.values()[i];
                                            genderCheck = 1;
                                        }
                                    }
                                    try {
                                        if (genderCheck == 0) {
                                            throw new InvalidCharacteristicException(countLine);
                                        }
                                    } catch (InvalidCharacteristicException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                //bodytype
                                if (!content[3].equals("")) {
                                    int bodyTypeCheck = 0;
                                    for (int i = 0; i < Person.BodyType.values().length; i++) {
                                        if (content[3].equals(Person.BodyType.values()[i].toString().toLowerCase())) {
                                            bodyType = Person.BodyType.values()[i];
                                            bodyTypeCheck = 1;
                                        }
                                    }
                                    try {
                                        if (bodyTypeCheck == 0) {
                                            throw new InvalidCharacteristicException(countLine);
                                        }
                                    } catch (InvalidCharacteristicException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                // profession
                                if (age > 16 && age <= 68) {
                                    if (!content[4].equals("")) {
                                        int professionCheck = 0;
                                        for (int i = 0; i < Person.Profession.values().length; i++) {
                                            if (content[4].equals(Person.Profession.values()[i].toString().toLowerCase())) {
                                                prof = Person.Profession.values()[i];
                                                professionCheck = 1;
                                            }
                                        }
                                        try {
                                            if (professionCheck == 0) {
                                                prof = Person.Profession.UNKNOWN;
                                                throw new InvalidCharacteristicException(countLine);
                                            }
                                        } catch (InvalidCharacteristicException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                }
                                // pregant
                                if (content[5].equals("true")) {
                                    isPregnant = true;
                                } else {
                                    isPregnant = false;
                                }
                                Person person = new Person(age, prof, gender, bodyType, isPregnant);
                                //isYou
                                if (content[6].equals("true")) {
                                    person.setAsYou(true);
                                }
                                //rolecheck
                                if (role.equals("passenger")) {
                                    passengers.add(person);
                                } else {
                                    pedestrians.add(person);
                                }
                            }
                            // create Animal
                            else {
                                species = content[7];
                                if (content[8].equals("true")) {
                                    isPet = true;
                                } else {
                                    isPet = false;
                                }
                                Animal animal = new Animal(species);
                                animal.setPet(isPet);
                                if (role.equals("passenger")) {
                                    passengers.add(animal);
                                } else {
                                    pedestrians.add(animal);
                                }
                            }
                            // read next passenger or pedestrain if exist
                            line = reader.readLine();
                            countLine += 1;
                            if (line != null) {
                                content = line.split(",");
                                while (content.length != 10) {
                                    try {
                                        if (content.length == 1) {
                                            break;
                                        }
                                        throw new InvalidDataFormatException(countLine);
                                    } catch (InvalidDataFormatException e) {
                                        System.out.println(e.getMessage());
                                        line = reader.readLine();
                                        content = line.split(",");
                                        countLine += 1;
                                    }
                                }
                            }
                            // if not exist(a new scenario or end of file), jump out this while loop
                            else {
                                content[0] = " "; // use to jump out while loop
                            }
                        }
                        // end of one scenario, add it into scenarioList
                        Scenario scen = new Scenario(passengers, pedestrians, legalCross);
                        scenarioList.add(scen);
                        // if there is another scenario
                        if (line != null) {
                            title = line.split(":")[0];
                        }
                        // if not (which means end of file), jump out while loop
                        else {
                            title = " ";// use to jump out while loop
                        }
                    }
                    reader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR: could not find config file.");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                // Audit
                Audit a = new Audit(scenarioList);
                a.run();
                a.printStatistic();
                // store this Audit statistic to file
                if (args.length == 4) {
                    String resultPath = args[3];
                    try {
                        a.printToFile(resultPath + "/results.log");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (args.length == 2) {
                    try {
                        a.printToFile("results.log");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                System.exit(0);
            }
            // -i , -i -c path, -i -r path, -i -c path -r path2
            // interactive mode, user can determine to save data or not
            if (args[0].equals("--interactive") || args[0].equals("-i")) {
                // if argument missing -i -c/ -i -c -r/ -i -r/-i -c path -r/ -i -c -r path
                if ((args.length == 2) || (args.length == 4) ||
                        ((args.length == 3) && (args[2].equals("-r") || args[2].equals("--result")))) {
                    PrintHelp();
                }
                // if interactive method has argument[-i -c config.csv], run config.csv scenarios
                // -i -c path / -i -c path -r path2 come in this if statement
                if ((args.length == 3 && (args[1].equals("-c") || args[1].equals("--config")))
                        || (args.length == 5 && ((args[1].equals("-c") || args[1].equals("--config")) &&
                        (args[3].equals("-r") || args[3].equals("--result"))))) {
                    // load config file
                    BufferedReader reader;
                    ArrayList<Scenario> scenarioList = new ArrayList<Scenario>();
                    try {
                        int countLine = 0; // use to track particular line
                        reader = new BufferedReader(new FileReader(args[2]));
                        // read header, can be ignored
                        String line = reader.readLine();
                        countLine += 1;
                        // start from second line
                        line = reader.readLine();
                        countLine += 1;

                        // only catch first word, "scenario"
                        String title = line.split(":")[0];
                        while (title.equals("scenario")) {
                            // this will create scenario in the end of while loop and set legal cross
                            String scenDetails = line.split(":")[1];
                            scenDetails = scenDetails.split(",")[0];
                            ArrayList<Character> passengers = new ArrayList<Character>();
                            ArrayList<Character> pedestrians = new ArrayList<Character>();
                            boolean legalCross;
                            if (scenDetails.equals("green")) {
                                legalCross = true;
                            } else {
                                legalCross = false;
                            }
                            //start reading passengers and pedestrians
                            line = reader.readLine();
                            countLine += 1;
                            String[] content = line.split(",");
                            // catch invalid argument number error, jump into next line
                            while (content.length != 10) {
                                try {
                                    if (content.length == 1) {
                                        break;
                                    }
                                    throw new InvalidDataFormatException(countLine);
                                } catch (InvalidDataFormatException e) {
                                    System.out.println(e.getMessage());
                                    line = reader.readLine();
                                    content = line.split(",");
                                    countLine += 1;
                                }
                            }
                            while ((content[0].equals("person") || content[0].equals("animal"))) {
                                // set default value
                                Person.Gender gender = Person.Gender.UNKNOWN;
                                int age = 0;       // default number
                                // catch number format error, set to default value
                                try {
                                    age = Integer.parseInt(content[2]);
                                } catch (NumberFormatException e) {
                                    System.out.printf
                                            ("WARNING: invalid number format in config file in line <%d>\n", countLine);
                                }
                                Person.BodyType bodyType = Person.BodyType.UNSPECIFIED;
                                Person.Profession prof = Person.Profession.NONE;
                                boolean isPregnant;
                                boolean isYou;
                                String species;
                                boolean isPet;
                                String role = content[9];
                                // create Person
                                if (content[0].equals("person")) {
                                    //set gender
                                    if (content[1] != null) {
                                        for (int i = 0; i < Person.Gender.values().length; i++) {
                                            if (content[1].equals(Person.Gender.values()[i].toString().toLowerCase())) {
                                                gender = Person.Gender.values()[i];
                                            }
                                        }
                                    }
                                    //set bodytype
                                    if (!content[3].equals("")) {
                                        int bodyTypeCheck = 0;
                                        for (int i = 0; i < Person.BodyType.values().length; i++) {
                                            if (content[3].equals(Person.BodyType.values()[i].toString().toLowerCase())) {
                                                bodyType = Person.BodyType.values()[i];
                                                bodyTypeCheck = 1;
                                            }
                                        }
                                        try {
                                            if (bodyTypeCheck == 0) {
                                                throw new InvalidCharacteristicException(countLine);
                                            }
                                        } catch (InvalidCharacteristicException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    // set profession
                                    if (age > 16 && age <= 68) {
                                        if (!content[4].equals("")) {
                                            int professionCheck = 0;
                                            for (int i = 0; i < Person.Profession.values().length; i++) {
                                                if (content[4].equals(Person.Profession.values()[i].toString().toLowerCase())) {
                                                    prof = Person.Profession.values()[i];
                                                    professionCheck = 1;
                                                }
                                            }
                                            try {
                                                if (professionCheck == 0) {
                                                    prof = Person.Profession.UNKNOWN;
                                                    throw new InvalidCharacteristicException(countLine);
                                                }
                                            } catch (InvalidCharacteristicException e) {
                                                System.out.println(e.getMessage());
                                            }
                                        }
                                    }
                                    // set pregant
                                    if ((content[5].equals("true")) && (age > 16 && age <= 68)) {
                                        isPregnant = true;
                                    } else {
                                        isPregnant = false;
                                    }
                                    //generate Person
                                    Person person = new Person(age, prof, gender, bodyType, isPregnant);
                                    //isYou
                                    if (content[6].equals("true")) {
                                        person.setAsYou(true);
                                    }
                                    //rolecheck
                                    if (role.equals("passenger")) {
                                        passengers.add(person);
                                    } else {
                                        pedestrians.add(person);
                                    }
                                }
                                // create Animal
                                else {
                                    // set species
                                    species = content[7];
                                    // set isPet
                                    if (content[8].equals("true")) {
                                        isPet = true;
                                    } else {
                                        isPet = false;
                                    }
                                    // generate Animal
                                    Animal animal = new Animal(species);
                                    animal.setPet(isPet);
                                    //rolecheck
                                    if (role.equals("passenger")) {
                                        passengers.add(animal);
                                    } else {
                                        pedestrians.add(animal);
                                    }
                                }
                                // read next passenger or pedestrian if exist, it will continue run while loop
                                line = reader.readLine();
                                countLine += 1;
                                if (line != null) {
                                    content = line.split(",");
                                    while (content.length != 10) {
                                        try {
                                            if (content.length == 1) {
                                                break;
                                            }
                                            throw new InvalidDataFormatException(countLine);
                                        } catch (InvalidDataFormatException e) {
                                            System.out.println(e.getMessage());
                                            line = reader.readLine();
                                            content = line.split(",");
                                            countLine += 1;
                                        }
                                    }
                                }
                                // if not exist(end of file), jump out this while loop
                                // and pass to the outter one while loop
                                else {
                                    content[0] = " "; // use to jump out this while loop
                                }
                            }
                            // end of one scenario, add it into scenarioList
                            Scenario scen = new Scenario(passengers, pedestrians, legalCross);
                            scenarioList.add(scen);
                            // if there is another scenario
                            if (line != null) {
                                title = line.split(":")[0];
                            }
                            // if not (which means end of file), jump out while loop
                            else {
                                title = " ";// use to jump out this while loop
                            }
                        }
                        reader.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("ERROR: could not find config file.");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    } // ----- end of reading config file ------
                    // welcome message and check if user want to save statistic
                    PrintWelcomeMessage();
                    boolean saveUser;
                    System.out.println("Do you consent to have your decisions saved to a file? (yes/no)");
                    while (true) {
                        String saveUserData = scan.nextLine();
                        try {
                            if (saveUserData.equals("yes")) {
                                saveUser = true;
                                break;
                            } else if (saveUserData.equals("no")) {
                                saveUser = false;
                                break;
                            } else {
                                throw new InvalidInputException();
                            }
                        } catch (InvalidInputException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    // create Audit
                    Audit a = new Audit();
                    a.setAuditType("User Audit");
                    Scenario.Decision decision = null;
                    // run through scenarioList, each 3 scenarios is a set
                    for (int i = 0; i < scenarioList.size(); i++) {
                        System.out.println(scenarioList.get(i).toString());
                        System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
                        String survival = scan.nextLine();
                        if (survival.equals("passenger") || survival.equals("passengers") || survival.equals("1")) {
                            decision = Scenario.Decision.PASSENGERS;
                        }
                        if (survival.equals("pedestrian") || survival.equals("pedestrians") || survival.equals("2")) {
                            decision = Scenario.Decision.PEDESTRIANS;
                        }
                        a.run(scenarioList.get(i), decision);
                        // if this is end of the scenario, save Data if user want to save, and exit
                        if (i == scenarioList.size() - 1) {
                            a.printStatistic();
                            if (saveUser) {
                                if (args.length == 5) {
                                    String resultPath = args[4];
                                    try {
                                        a.printToFile(resultPath + "/user.log");
                                    } catch (IOException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                if (args.length == 3) {
                                    try {
                                        a.printToFile("user.log");
                                    } catch (IOException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            }
                            System.out.println("That's all. Press Enter to quit.");
                            String exit = scan.nextLine();
                            System.exit(0);
                        }
                        // if not the end of scenarioLst but is the end of a set (3 scenarios)
                        if (i % 3 == 2) {
                            a.printStatistic();
                            System.out.println("Would you like to continue? (yes/no)");
                            String continueAudit = scan.nextLine();
                            if (continueAudit.equals("no")) {
                                if (saveUser) {
                                    // -r argument is presented, save file to specific path
                                    if (args.length == 5) {
                                        String resultPath = args[4];
                                        try {
                                            a.printToFile(resultPath + "/user.log");
                                        } catch (IOException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    if (args.length == 3) {
                                        try {
                                            a.printToFile("user.log");
                                        } catch (IOException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                }
                                System.exit(0);
                            }
                            if (continueAudit.equals("yes")) {
                            }
                        }
                    }

                }

                // if --interactive method does not have argument, run random scenarios
                // -i or -i -r path comes in this if statement
                if ((args.length == 1) || ((args.length == 3) && (args[1].equals("-r") || args[1].equals("--result")))) {
                    // welcome message and check if user want to save statistic
                    PrintWelcomeMessage();
                    boolean saveUser;
                    System.out.println("Do you consent to have your decisions saved to a file? (yes/no)");
                    while (true) {
                        String saveUserData = scan.nextLine();
                        try {
                            if (saveUserData.equals("yes")) {
                                saveUser = true;
                                break;
                            } else if (saveUserData.equals("no")) {
                                saveUser = false;
                                break;
                            } else {
                                throw new InvalidInputException();
                            }
                        } catch (InvalidInputException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    Audit a = new Audit();
                    a.setAuditType("User Audit");
                    ScenarioGenerator scg = new ScenarioGenerator();
                    Scenario.Decision decision = null;
                    // 3 scenarios is a set, after deciding 3 scenarios, ask user to continue or not
                    // if yes, another 3 scenarios are shown up
                    for (int i = 0; i < 3; i++) {
                        Scenario scenario = scg.generate();
                        System.out.println(scenario.toString());
                        System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
                        String survival = scan.nextLine();
                        if (survival.equals("passenger") || survival.equals("passengers") || survival.equals("1")) {
                            decision = Scenario.Decision.PASSENGERS;
                        }
                        if (survival.equals("pedestrian") || survival.equals("pedestrians") || survival.equals("2")) {
                            decision = Scenario.Decision.PEDESTRIANS;
                        }
                        a.run(scenario, decision);
                        // the end of the third scenario in a set
                        if (i == 2) {
                            a.printStatistic();
                            System.out.println("Would you like to continue? (yes/no)");
                            String continueAudit = scan.nextLine();
                            // if not continue
                            if (continueAudit.equals("no")) {
                                // check saveUser, if true, save data
                                if (saveUser) {
                                    if (args.length == 3) {
                                        String resultPath = args[2];
                                        try {
                                            a.printToFile(resultPath + "/user.log");
                                        } catch (IOException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    if (args.length == 1) {
                                        try {
                                            a.printToFile("user.log");
                                        } catch (IOException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                }
                                System.exit(0);
                            }
                            // continue making decision, another 3 scenarios will pop out
                            if (continueAudit.equals("yes")) {
                                i = -1;     // -1 + 1 = 0 in the beginning
                            }
                        }
                    }
                }

            }
        }
        // without command-line arguments, generate random scenarios and audit them by designed algorithm
        else {
            ScenarioGenerator scg = new ScenarioGenerator();
            BufferedReader reader;
            Audit a = new Audit();

            // create file
            try {
                File file = new File("results.log");
                //if file exists, read data and append our results in
                if (!file.createNewFile()) {
                    reader = new BufferedReader(new FileReader(file));
                    // read runs
                    String line = reader.readLine();
                    a.setRuns(Integer.parseInt(line));
                    // read total survival age
                    line = reader.readLine();
                    a.setTotalSurvivalAge(Integer.parseInt(line));
                    // read total survival person
                    line = reader.readLine();
                    a.setTotalSurvivalPerson(Integer.parseInt(line));

                    // read the statistic details for every characters
                    line = reader.readLine();
                    while (line != null) {
                        for (int i = 0; i < a.getDynamicCharacteristic().length; i++) {
                            if (line.equals(a.getDynamicCharacteristic()[i])) {
                                line = reader.readLine();
                                a.setSuvNumberAudit(i, Integer.parseInt(line));
                                line = reader.readLine();
                                a.setTotalNumberAudit(i, Integer.parseInt(line));
                                line = reader.readLine();
                                a.setSuvRateAudit(i, Double.parseDouble(line));
                            }
                        }
                        line = reader.readLine();
                    }
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("ERROR: could not print results. Target directory does not exist.");
            }
            // run audit
            a.run(2);
            a.printStatistic();
            try {
                a.printToFile("results.log");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * print Welcome Message to user when using "interactive" mode
     */
    public static void PrintWelcomeMessage() {
        BufferedReader reader;
        try {
            File welcomFile = new File("welcome.ascii");
            reader = new BufferedReader(new FileReader(welcomFile));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * print Help Document
     */
    public static void PrintHelp() {
        System.out.println("EthicalEngine - COMP90041 - Final Project\n");
        System.out.println("Usage: java EthicalEngine [arguments]\n");
        System.out.println("Arguments:");
        System.out.println("    -c or --config      Optional: path to config file");
        System.out.println("    -h or --help        Print Help (this message) and exit");
        System.out.println("    -r or --results     Optional: path to results log file");
        System.out.println("    -i or --interactive Optional: launches interactive mode");
        System.exit(0);
    }

    /**
     * Invalid Data Format Exception class
     */
    public static class InvalidDataFormatException extends Exception {
        //constructor
        public InvalidDataFormatException(int countLine) {
            super("WARNING: invalid data format in config file in line " + countLine);
        }
    }

    /**
     * Invalid Characteristic Exception class
     */
    public static class InvalidCharacteristicException extends Exception {
        //constructor
        public InvalidCharacteristicException(int countLine) {
            super("WARNING: invalid characteristic in config file in line " + countLine);
        }
    }

    /**
     * Invalid Input Exception class
     */
    public static class InvalidInputException extends Exception {
        //constructor
        public InvalidInputException() {
            super("Invalid response. Do you consent to have your decisions saved to a file? (yes/no)");
        }
    }

    /**
     * decision making algorithm, run my own survival solution of Trolley Dilemma to a scenario
     *
     * @param scenario
     */
    public static void decide(Scenario scenario) {
        // output PASSENGERS or PEDESTRIANS

        // Priority 1 : Doctor should be survived
        int passengersDoc = 0;
        int pedestriansDoc = 0;

        for (int i = 0; i < scenario.getPassengers().size(); i++) {
            if (scenario.getPassengers().get(i).getClass().equals(new Person().getClass())) {
                if (scenario.getPassengers().get(i).getProfession().equals(Person.Profession.DOCTOR)) {
                    passengersDoc++;
                }
            }
        }
        for (int i = 0; i < scenario.getPedestrians().size(); i++) {
            if (scenario.getPedestrians().get(i).getClass().equals(new Person().getClass())) {
                if (scenario.getPedestrians().get(i).getProfession().equals(Person.Profession.DOCTOR)) {
                    pedestriansDoc++;
                }
            }
        }
        if (passengersDoc > pedestriansDoc) {
            scenario.setSurvival(Scenario.Decision.PASSENGERS);
            return;
        }
        if (passengersDoc < pedestriansDoc) {
            scenario.setSurvival(Scenario.Decision.PEDESTRIANS);
            return;
        }

        // Priority 2 : Pregnant should be survived
        int passengersPreg = 0;
        int pedestriansPreg = 0;
        for (int i = 0; i < scenario.getPassengers().size(); i++) {
            if (scenario.getPassengers().get(i).isPregnant()) {
                passengersPreg++;
            }
        }
        for (int i = 0; i < scenario.getPedestrians().size(); i++) {
            if (scenario.getPedestrians().get(i).isPregnant()) {
                pedestriansPreg++;
            }
        }
        if (passengersPreg > pedestriansPreg) {
            scenario.setSurvival(Scenario.Decision.PASSENGERS);
            return;
        }
        if (passengersPreg < pedestriansPreg) {
            scenario.setSurvival(Scenario.Decision.PEDESTRIANS);
            return;
        }

        // Priority 3 : baby and child should be survived
        int passengersBabyChild = 0;
        int pedestriansBabyChild = 0;
        for (int i = 0; i < scenario.getPassengers().size(); i++) {
            if (scenario.getPassengers().get(i).getAge() <= 16) {
                passengersBabyChild++;
            }
        }
        for (int i = 0; i < scenario.getPedestrians().size(); i++) {
            if (scenario.getPedestrians().get(i).getAge() <= 16) {
                pedestriansBabyChild++;
            }
        }
        if (passengersBabyChild > pedestriansBabyChild) {
            scenario.setSurvival(Scenario.Decision.PASSENGERS);
            return;
        }
        if (passengersBabyChild < pedestriansBabyChild) {
            scenario.setSurvival(Scenario.Decision.PEDESTRIANS);
            return;
        }

        // Priority 4 : provide weight to each characters (except Doctor, Pregnant Female, Baby and Child)
        // based on the more "general" reason like if this person survived, can he/she rescues more life?

        int passengersWeight = 0;
        int pedestriansWeight = 0;
        for (int i = 0; i < scenario.getPassengers().size(); i++) {
            if (scenario.getPassengers().get(i).getClass().equals(new Person().getClass())) {
                if (scenario.getPassengers().get(i).getProfession().equals(Person.Profession.CRIMINAL)) {
                    passengersWeight -= 1;
                }
                if (scenario.getPassengers().get(i).getProfession().equals(Person.Profession.ARTIST)) {
                    passengersWeight += 1;
                }
                if (scenario.getPassengers().get(i).getProfession().equals(Person.Profession.ENGINEER)) {
                    passengersWeight += 2;
                }
                if (scenario.getPassengers().get(i).getProfession().equals(Person.Profession.CEO)) {
                    passengersWeight += 3;
                }
                if (scenario.getPassengers().get(i).getGender().equals(Person.Gender.FEMALE)) {
                    passengersWeight += 1;
                }
            }
        }
        for (int i = 0; i < scenario.getPedestrians().size(); i++) {
            if (scenario.getPedestrians().get(i).getClass().equals(new Person().getClass())) {
                if (scenario.getPedestrians().get(i).getProfession().equals(Person.Profession.CRIMINAL)) {
                    pedestriansWeight -= 1;
                }
                if (scenario.getPedestrians().get(i).getProfession().equals(Person.Profession.ARTIST)) {
                    pedestriansWeight += 1;
                }
                if (scenario.getPedestrians().get(i).getProfession().equals(Person.Profession.ENGINEER)) {
                    pedestriansWeight += 2;
                }
                if (scenario.getPedestrians().get(i).getProfession().equals(Person.Profession.CEO)) {
                    pedestriansWeight += 3;
                }
                if (scenario.getPedestrians().get(i).getGender().equals(Person.Gender.FEMALE)) {
                    pedestriansWeight += 1;
                }
            }
        }
        if (passengersWeight > pedestriansWeight) {
            scenario.setSurvival(Scenario.Decision.PASSENGERS);
            return;
        }
        if (passengersWeight < pedestriansWeight) {
            scenario.setSurvival(Scenario.Decision.PEDESTRIANS);
            return;
        }

        // Priority 5 : Nothing else but is legal crossing or not
        if (scenario.isLegalCrossing()) {
            scenario.setSurvival(Scenario.Decision.PEDESTRIANS);
        } else {
            scenario.setSurvival(Scenario.Decision.PASSENGERS);
        }
    }
}
