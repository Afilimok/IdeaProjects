package flashcards;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        List<String> log = new ArrayList<>();
        Map<String, String> mapForKeyD = new LinkedHashMap<>();
        Map<String, String> mapForKeyC = new LinkedHashMap<>();
        Map<String, Integer> mapForW_C = new LinkedHashMap<>();


        for (int tt = 0; tt < args.length;tt = tt + 2){
            if (args[tt].equals("-import")){

                String pathToFile = args[tt+1];

                File file = new File(pathToFile);
                try {
                    Scanner scannerFile = new Scanner(file);
                    int n = Integer.parseInt(scannerFile.nextLine());
                    for (int i = 0; i < n; i++) {
                        String term = scannerFile.nextLine();
                        String defi = scannerFile.nextLine();
                        String  num = scannerFile.nextLine();
                        int mistakes = Integer.parseInt(num);
                        if (mapForKeyC.get(term) == null && mapForKeyC.get(term) == null) {
                            mapForKeyD.put(defi, term);
                            mapForKeyC.put(term, defi);
                            mapForW_C.put(term, mistakes);
                            continue;
                        }
                        if (mapForKeyC.get(term) != null) {
                            mapForKeyD.remove(mapForKeyC.get(term));
                            mapForKeyC.remove(term);
                        }
                        if (mapForKeyD.get(defi) != null) {
                            mapForKeyC.remove(mapForKeyD.get(defi));
                            mapForKeyC.remove(defi);

                        }
                        mapForKeyD.put(defi, term);
                        mapForKeyC.put(term, defi);
                        mapForW_C.put(term, mistakes);
                    }

                    System.out.println(n + " cards have been loaded.");
                    log.add(n + " cards have been loaded.");

                } catch (FileNotFoundException e) {
                    System.out.println("File not found.");
                }
            }
        }



        boolean end = false;
        while (!end) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            log.add("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String command = scanner.nextLine();
            log.add("> " + command);
            switch (command) {
                case "log":
                    System.out.println("File name:");
                    log.add("File name:");
                    String textF = scanner.nextLine();
                    File fileForLog = new File(textF);
                    log.add("> " + textF);
                    try (PrintWriter printWriter = new PrintWriter(fileForLog)) {
                        for (String s : log) {
                            printWriter.println(s);
                        }
                    } catch (IOException e) {
                        System.out.printf("An exception occurs %s", e.getMessage());
                    }
                    System.out.println("The log has been saved.");
                    log.add("The log has been saved.");
                    break;
                case "hardest card":
                    List<String> hardestCard = new ArrayList<>();
                    int res = 0;


                    for (Map.Entry<String, Integer> entry : mapForW_C.entrySet()) {
                        if (entry.getValue() == res) {
                            hardestCard.add(entry.getKey());
                        }
                        if (entry.getValue() > res) {
                            res = entry.getValue();
                            hardestCard.clear();
                            hardestCard.add(entry.getKey());

                        }
                    }

                    if (res == 0) {
                        System.out.println("There are no cards with errors.");
                        log.add("There are no cards with errors.");

                    } else {
                        StringBuffer ansForPrintf = new StringBuffer("");
                        if (hardestCard.size() == 1) {
                            ansForPrintf.append("The hardest card is \"").append(hardestCard.get(0)).append("\".");
                            System.out.print("The hardest card is \"" + hardestCard.get(0) + "\".");
                        } else {
                            System.out.print("The hardest cards are ");
                            ansForPrintf.append("The hardest cards are ");
                            for (int i = 0; i < hardestCard.size(); i++) {
                                System.out.print("\"" + hardestCard.get(i) + "\"");
                                ansForPrintf.append("\"").append(hardestCard.get(i)).append("\"");

                                if (i != hardestCard.size() - 1) {
                                    System.out.print(", ");
                                    ansForPrintf.append(", ");

                                } else {
                                    System.out.print(". ");
                                    ansForPrintf.append(". ");

                                }
                            }

                        }
                        System.out.println(" You have " + res + " errors answering it.");
                        ansForPrintf.append(" You have ").append(res).append(" errors answering it.");


                        log.add(ansForPrintf.toString());
                    }

                    break;
                case "reset stats":
                    mapForW_C.replaceAll((k, v) -> 0);
                    System.out.println("Card statistics has been reset.");
                    log.add("Card statistics has been reset.");
                    break;
                case "add":
                    System.out.println("The card:");
                    log.add("The card:");
                    String t = scanner.nextLine();
                    log.add("> " + t);
                    if (mapForKeyC.get(t) != null) {
                        System.out.println("The card \"" + t + "\" already exists.");
                        log.add("The card \"" + t + "\" already exists.");
                        break;
                    }
                    System.out.println("The definition of the card:");
                    log.add("The definition of the card:");
                    String def = scanner.nextLine();
                    log.add("> " + def);
                    if (mapForKeyD.get(def) != null) {
                        System.out.println("The definition \"" + def + "\" already exists.");
                        log.add("The definition \"" + def + "\" already exists.");
                        break;
                    }


                    mapForKeyD.put(def, t);
                    mapForKeyC.put(t, def);
                    mapForW_C.put(t, 0);
                    //System.out.println(def +  " : " + t + "     =" + map.get(def));
                    System.out.println("The pair (\"" + t + "\":\"" + def + "\") has been added.");
                    log.add("The pair (\"" + t + "\":\"" + def + "\") has been added.");
                    break;

                case "remove":
                    System.out.println("The card:");
                    log.add("The card:");
                    String card = scanner.nextLine();
                    log.add("> " + card);
                    if (mapForKeyC.get(card) == null) {
                        System.out.println("Can't remove \"" + card + "\": there is no such card.");
                        log.add("Can't remove \"" + card + "\": there is no such card.");
                    } else {

                        mapForKeyD.remove(mapForKeyC.get(card));
                        mapForKeyC.remove(card);
                        mapForW_C.remove(card);
                        System.out.println("The card has been removed.");
                        log.add("The card has been removed.");
                    }
                    break;
                case "import":


                    System.out.println("File name:");

                    log.add("File name:");
                    String pathToFile = scanner.nextLine();
                    log.add("> " + pathToFile);
                    File file = new File(pathToFile);
                    try {
                        Scanner scannerFile = new Scanner(file);
                        int n = Integer.parseInt(scannerFile.nextLine());
                        //todo
                        //System.out.println("n == " + n);
                        for (int i = 0; i < n; i++) {
                            //todo
                            //System.out.println("$$$" + i);

                            String term = scannerFile.nextLine();
                            //System.out.println("term" + term);

                            String defi = scannerFile.nextLine();
                            ///System.out.println("defi" + defi);
                            String  num = scannerFile.nextLine();
                            //System.out.println("num" + "&" + num + "&");
                            //todo



                            int mistakes = Integer.parseInt(num);


                            if (mapForKeyC.get(term) == null && mapForKeyC.get(term) == null) {
                                mapForKeyD.put(defi, term);
                                mapForKeyC.put(term, defi);
                                mapForW_C.put(term, mistakes);
                                continue;
                            }
                            if (mapForKeyC.get(term) != null) {
                                mapForKeyD.remove(mapForKeyC.get(term));
                                mapForKeyC.remove(term);

                            }
                            if (mapForKeyD.get(defi) != null) {
                                mapForKeyC.remove(mapForKeyD.get(defi));
                                mapForKeyC.remove(defi);

                            }

                            mapForKeyD.put(defi, term);
                            mapForKeyC.put(term, defi);
                            mapForW_C.put(term, mistakes);

                        }

                        System.out.println(n + " cards have been loaded.");
                        log.add(n + " cards have been loaded.");

                    } catch (FileNotFoundException e) {
                        System.out.println("File not found.");
                    }
                    break;
                case "export":
                    System.out.println("File name:");
                    log.add("File name:");
                    String textF1 = scanner.nextLine();
                    File fileForExport = new File(textF1);
                    log.add("> " + textF1);
                    try (PrintWriter printWriter = new PrintWriter(fileForExport)) {

                        printWriter.println(mapForKeyC.size());
                        for (Map.Entry<String, String> entry : mapForKeyC.entrySet()) {
                            printWriter.println(entry.getKey());
                            printWriter.println(entry.getValue());
                            printWriter.println(mapForW_C.get(entry.getKey()));
                        }

                    } catch (IOException e) {
                        System.out.printf("An exception occurs %s", e.getMessage());
                    }


                    System.out.println(mapForKeyC.size() + " cards have been saved.");
                    log.add(mapForKeyC.size() + " cards have been saved.");

                    break;
                case "ask":
                    System.out.println("How many times to ask?");
                    log.add("How many times to ask?");
                    String input = scanner.nextLine();
                    int n = Integer.parseInt(input);
                    log.add("> " + input);
                    while (n > 0) {
                        Iterator<Map.Entry<String, String>> entries = mapForKeyC.entrySet().iterator();
                        for (int i = 0; i < mapForKeyC.size() && n > 0; i++) {
                            n--;

                            Map.Entry<String, String> entry = entries.next();
                            String term = entry.getKey();
                            String definition = entry.getValue();
                            System.out.println("Print the definition of \"" + term + "\":");
                            log.add("Print the definition of \"" + term + "\":");
                            String answer = scanner.nextLine();
                            log.add("> " + answer);
                            if (answer.equals(definition)) {
                                System.out.println("Correct answer.");
                                log.add("Correct answer.");
                            } else {

                                mapForW_C.put(term, mapForW_C.get(term) + 1);
                                if (mapForKeyD.get(answer) == null) {
                                    System.out.println("Wrong answer. The correct one is \"" + definition + "\".");
                                    log.add("Wrong answer. The correct one is \"" + definition + "\".");
                                } else {
                                    System.out.println("Wrong answer. The correct one is \"" + definition + "\", you've just written the definition of \"" + mapForKeyD.get(answer) + "\".");
                                    log.add("Wrong answer. The correct one is \"" + definition + "\", you've just written the definition of \"" + mapForKeyD.get(answer) + "\".");
                                }
                            }

                        }
                    }

                    break;
                case "exit":
                    end = true;
                    System.out.print("Bye bye!");
                    break;
            }
            System.out.println("");
            log.add("");
        }

        for (int tt = 0; tt < args.length; tt= tt + 2){
            if (args[tt].equals("-export")) {

                String textF1 = args[tt+1];
                File fileForExport = new File(textF1);
                log.add("> " + textF1);
                try (PrintWriter printWriter = new PrintWriter(fileForExport)) {
                    printWriter.println(mapForKeyC.size());
                    for (Map.Entry<String, String> entry : mapForKeyC.entrySet()) {
                        printWriter.println(entry.getKey());
                        printWriter.println(entry.getValue());
                        printWriter.println(mapForW_C.get(entry.getKey()));
                    }

                } catch (IOException e) {
                    System.out.printf("An exception occurs %s", e.getMessage());
                }
                System.out.println(mapForKeyC.size() + " cards have been saved.");

            }
        }
    }
}
/*

______________________________________________________________
Start test 3
Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):
> hardest card
There are no cards with errors.

Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):
> add
The card:
> France
The definition of the card:
> UpdateMeFromImport
The pair ("France":"UpdateMeFromImport") has been added.

Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):
> ask
How many times to ask?
> 2
Print the definition of "France":
> ??
Wrong answer. The correct one is "UpdateMeFromImport".
Print the definition of "France":
> ??
Wrong answer. The correct one is "UpdateMeFromImport".

Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):
> import
File name:
> capitals.txt
> hardest card

 */