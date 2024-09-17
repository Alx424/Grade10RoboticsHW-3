package src.main.java;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class Main {
  public static String CSV_FILE_NAME = "src/main/java/records.csv";
  public static File csvOutputFile;
  public static PrintWriter pw;
  public static Scanner in;
  //               teamNumber, [notes, totalMatches]
  public static Map<Integer, Integer[]> teamTotals = new HashMap<Integer, Integer[]>();
  public static List<Integer[]> ranking = new ArrayList<Integer[]>();
  public static int teamNumber;
  public static int numAns = -1; // placeholder used for number answers
  
  public static void main(String[] args) throws FileNotFoundException, IOException {
    System.out.println("Hello world!");

    /*
    // testing:
    System.out.println("Testing...");
    quickRead(2); // checking for a team that has no games
    String[] tempTestLine = new String[]{"8043","1","2","false","false","false"};
    write(tempTestLine);
    tempTestLine = new String[]{"8043","2","1","true","true","false"};
    write(tempTestLine);
    quickRead(8043); // check to see if records are written and read right
    tempTestLine = new String[]{"9999","10","23","true","true","true"};
    write(tempTestLine);
    quickRead(0); // see leaderboard
    System.out.println("Testing Done!!!\n");
    */
    
    in = new Scanner(System.in);
    int whatToDo = 0;
    System.out.println("1 to read, 2 to write: ");
    whatToDo = in.nextInt();
    while(whatToDo < 1 || whatToDo > 2) {
      System.out.println("Invalid answer, please try again");
      System.out.println("1 to read, 2 to write: ");
      whatToDo = in.nextInt();
    }
    if(whatToDo == 1) readInfo();
    else if(whatToDo == 2) addInfo();
  }

  public static void quickRead(int num) {
    ReadLineByLine(num);

    if(num == 0) {
      // ranking
      for(String[] line : linesRead) {
        if( teamTotals.containsKey(Integer.parseInt(line[0]))) {
            teamTotals.get(Integer.parseInt(line[0]))[0] += Integer.parseInt(line[1]) + Integer.parseInt(line[2]);
            teamTotals.get(Integer.parseInt(line[0]))[1] += 1;
        } else {
          Integer[] temp = {Integer.parseInt(line[1])+Integer.parseInt(line[2]), 1};
            teamTotals.put(Integer.parseInt(line[0]), temp);
        }
      }



      teamTotals.forEach((key, val) -> {
        System.out.println(key + " " + val + " added");
        Integer[] temp = new Integer[2];
        temp[0] = key;
        temp[1] =  val[0]/val[1];
        ranking.add(temp);
      });

      // sort
      Collections.sort(ranking, new Comparator<Integer[]>() {
            public int compare(Integer[] team1, Integer[] team2) {
                return team1[1].compareTo(team2[1]);
            }
        });
      Collections.reverse(ranking);

      System.out.println("** TEAM LEADERBOARD **");
      for(Integer[] team : ranking) {
        System.out.println(team[0] + " -> " + team[1] + " notes per match");
        
      };
    return;
    }


    // calculate stats:
    int TOTAL_NOTES_EVER = 0;
    int TOTAL_SPEAKERS_EVER = 0;
    int TOTAL_AMPS_EVER = 0;
    int TOTAL_CLIMBS = 0;
    int TOTAL_PARKS = 0;
    int TOTAL_TRAPS = 0;
    int TOTAL_MATCHES = 0;
    for(String[] line : linesRead) {
      if(line != null) TOTAL_MATCHES++;
      TOTAL_SPEAKERS_EVER += Integer.valueOf(line[1]);
      TOTAL_AMPS_EVER += Integer.valueOf(line[2]);
      TOTAL_NOTES_EVER += Integer.valueOf(line[1]) + Integer.valueOf(line[2]);
      if(Boolean.valueOf(line[3]) == true) TOTAL_PARKS++;
      if(Boolean.valueOf(line[4]) == true) TOTAL_CLIMBS++;
      if(Boolean.valueOf(line[5]) == true) TOTAL_TRAPS++;
    }
    double AVG_NOTES = (double)TOTAL_NOTES_EVER/TOTAL_MATCHES;
    double AVG_SPEAKERS = (double)TOTAL_SPEAKERS_EVER/TOTAL_MATCHES;
    double AVG_AMPS = (double)TOTAL_AMPS_EVER/TOTAL_MATCHES;
    double CLIMB_PERCENT = (double)TOTAL_CLIMBS/TOTAL_MATCHES*100;
    double PARK_PERCENT = (double)TOTAL_PARKS/TOTAL_MATCHES*100;
    double TRAP_PERCENT = (double)TOTAL_TRAPS/TOTAL_MATCHES*100;

    
    // display stats
    if(TOTAL_MATCHES == 0) {
      System.out.println("** NO RECORDED MATCHES FOR TEAM " + teamNumber + " **");
      return;
    }
    System.out.println("** STATS FOR TEAM " + teamNumber + " **");
    System.out.println("Total Recorded Matches: " + TOTAL_MATCHES);
    System.out.println("Avg Notes per Match: " + AVG_NOTES);
    System.out.println("Avg Speakers per Match: " + AVG_SPEAKERS);
    System.out.println("Avg Amps per Match: " + AVG_AMPS);
    System.out.println(TOTAL_PARKS);
    System.out.println("Park: " + PARK_PERCENT + "%");
    System.out.println(TOTAL_CLIMBS);
    System.out.println("Climb: " + CLIMB_PERCENT + "%");
    System.out.println(TOTAL_TRAPS);
    System.out.println("Trap: " + TRAP_PERCENT + "%");
    
    return;
  }
  
  public static void addInfo() throws FileNotFoundException, IOException {
    numAns = -1;
    boolean again = true;
    String[] teamInfo = new String[6];
  while(again) {
    again = false;
    System.out.println("Team number: ");
    teamNumber = in.nextInt();
    while(teamNumber < 0 || teamNumber > 9999) {
      System.out.println("Invalid team number, please try again");
      System.out.println("Team number: ");
      teamNumber = in.nextInt();
    }
    teamInfo[0] = Integer.toString(teamNumber);
    
    System.out.println("Speakers scored: ");
    numAns = in.nextInt();
    while(numAns < 0 || numAns > 50) {
      System.out.println("Invalid number, please try again");
      System.out.println("Speakers scored: ");
      numAns = in.nextInt();
    }
    teamInfo[1] = numAns+"";

    System.out.println("Amps scored: ");
    numAns = in.nextInt();
    while(numAns < 0 || numAns > 50) {
      System.out.println("Invalid number, please try again");
      System.out.println("Amps scored: ");
      numAns = in.nextInt();
    }
    teamInfo[2] = numAns+"";

    System.out.println("Park? 1 for yes, 2 for no: ");
    numAns = in.nextInt();
    while(numAns < 1 || numAns > 2) {
      System.out.println("Invalid answer, please try again");
      System.out.println("Log another team? 1 for yes, 2 for no");
      numAns = in.nextInt();
    }
    if(numAns == 1) teamInfo[3] = "true";
    else if(numAns == 2) teamInfo[3] = "false";

    System.out.println("Climb? 1 for yes, 2 for no: ");
    numAns = in.nextInt();
    while(numAns < 1 || numAns > 2) {
      System.out.println("Invalid answer, please try again");
      System.out.println("Log another team? 1 for yes, 2 for no");
      numAns = in.nextInt();
    }
    if(numAns == 1) teamInfo[4] = "true";
    else if(numAns == 2) teamInfo[4] = "false";

    System.out.println("Trap? 1 for yes, 2 for no: ");
    numAns = in.nextInt();
    while(numAns < 1 || numAns > 2) {
      System.out.println("Invalid answer, please try again");
      System.out.println("Log another team? 1 for yes, 2 for no");
      numAns = in.nextInt();
    }
    if(numAns == 1) teamInfo[5] = "true";
    else if(numAns == 2) teamInfo[5] = "false";
    
    System.out.println("Log another team? 1 for yes, 2 for no");
    numAns = in.nextInt();
    while(numAns < 1 || numAns > 2) {
      System.out.println("Invalid answer, please try again");
      System.out.println("Log another team? 1 for yes, 2 for no");
      numAns = in.nextInt();
    }
    if(numAns == 1) again = true;
    else if(numAns == 2) again = false;
    write(teamInfo);
  }
    return;
  }
  
  public static void write(String[] in) throws IOException {
    String convertedToCSV = convertToCSV(in);
    csvOutputFile = new File(CSV_FILE_NAME);
    PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile, true));

      
    System.out.println("Writing: \"" + convertedToCSV + "\" to " + CSV_FILE_NAME);
    pw.append(convertedToCSV + "\n");
    pw.close();
    return;
  }

  public static String convertToCSV(String[] data) {
    String ans = String.join(",", data);
    return ans;
  }
  
  public static String escapeSpecialCharacters(String data) {
      if (data == null) {
          throw new IllegalArgumentException("Input data cannot be null");
      }
      String escapedData = data.replaceAll("\\R", " ");
      if (data.contains(",") || data.contains("\"") || data.contains("'")) {
          data = data.replace("\"", "\"\"");
          escapedData = "\"" + data + "\"";
      }
      return escapedData;
  }

  public static ArrayList<String[]> linesRead = new ArrayList<String[]>();
  public static void readInfo() {
    teamNumber = -1;
    System.out.println("Team number (type \"0\" to see leaderboard): ");
    teamNumber = in.nextInt();
    while(teamNumber < 0 || teamNumber > 9999) {
      System.out.println("Invalid team number, please try again");
      System.out.println("Team number: ");
      teamNumber = in.nextInt();
    }

    ReadLineByLine(teamNumber);

    if(teamNumber == 0) {
      // ranking
      for(String[] line : linesRead) {
        if( teamTotals.containsKey(Integer.parseInt(line[0]))) {
            teamTotals.get(Integer.parseInt(line[0]))[0] += Integer.parseInt(line[1]) + Integer.parseInt(line[2]);
            teamTotals.get(Integer.parseInt(line[0]))[1] += 1;
        } else {
          Integer[] temp = {Integer.parseInt(line[1])+Integer.parseInt(line[2]), 1};
            teamTotals.put(Integer.parseInt(line[0]), temp);
        }
      }



      teamTotals.forEach((key, val) -> {
        System.out.println(key + " " + val + " added");
        Integer[] temp = new Integer[2];
        temp[0] = key;
        temp[1] =  val[0]/val[1];
        ranking.add(temp);
      });

      // sort
      Collections.sort(ranking, new Comparator<Integer[]>() {
            public int compare(Integer[] team1, Integer[] team2) {
                return team1[1].compareTo(team2[1]);
            }
        });
      Collections.reverse(ranking);

      System.out.println("** TEAM LEADERBOARD **");
      for(Integer[] team : ranking) {
        System.out.println(team[0] + " -> " + team[1] + " notes per match");
        
      };
      
      
      return;
    }

    // calculate stats:
    int TOTAL_NOTES_EVER = 0;
    int TOTAL_SPEAKERS_EVER = 0;
    int TOTAL_AMPS_EVER = 0;
    int TOTAL_CLIMBS = 0;
    int TOTAL_PARKS = 0;
    int TOTAL_TRAPS = 0;
    int TOTAL_MATCHES = 0;
    for(String[] line : linesRead) {
      if(line != null) TOTAL_MATCHES++;
      TOTAL_SPEAKERS_EVER += Integer.valueOf(line[1]);
      TOTAL_AMPS_EVER += Integer.valueOf(line[2]);
      TOTAL_NOTES_EVER += Integer.valueOf(line[1]) + Integer.valueOf(line[2]);
      if(Boolean.valueOf(line[3]) == true) TOTAL_PARKS++;
      if(Boolean.valueOf(line[4]) == true) TOTAL_CLIMBS++;
      if(Boolean.valueOf(line[5]) == true) TOTAL_TRAPS++;
    }
    double AVG_NOTES = (double)TOTAL_NOTES_EVER/TOTAL_MATCHES;
    double AVG_SPEAKERS = (double)TOTAL_SPEAKERS_EVER/TOTAL_MATCHES;
    double AVG_AMPS = (double)TOTAL_AMPS_EVER/TOTAL_MATCHES;
    double CLIMB_PERCENT = (double)TOTAL_CLIMBS/TOTAL_MATCHES*100;
    double PARK_PERCENT = (double)TOTAL_PARKS/TOTAL_MATCHES*100;
    double TRAP_PERCENT = (double)TOTAL_TRAPS/TOTAL_MATCHES*100;

    
    // display stats
    if(TOTAL_MATCHES == 0) {
      System.out.println("** NO RECORDED MATCHES FOR TEAM " + teamNumber + " **");
      return;
    }
    System.out.println("** STATS FOR TEAM " + teamNumber + " **");
    System.out.println("Total Recorded Matches: " + TOTAL_MATCHES);
    System.out.println("Avg Notes per Match: " + AVG_NOTES);
    System.out.println("Avg Speakers per Match: " + AVG_SPEAKERS);
    System.out.println("Avg Amps per Match: " + AVG_AMPS);
    System.out.println(TOTAL_PARKS);
    System.out.println("Park: " + PARK_PERCENT + "%");
    System.out.println(TOTAL_CLIMBS);
    System.out.println("Climb: " + CLIMB_PERCENT + "%");
    System.out.println(TOTAL_TRAPS);
    System.out.println("Trap: " + TRAP_PERCENT + "%");
    
    return;
  }
  
  public static void ReadLineByLine(int num) {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(CSV_FILE_NAME));
      String line = reader.readLine();
      //line = reader.readLine();
      
      while (line != null) {
        String[] split = line.split(",");
        if(split[0].equals(Integer.toString(num)) || num == 0) {
          linesRead.add(split);
        }
          // read next line
        line = reader.readLine();
      }

      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  
}