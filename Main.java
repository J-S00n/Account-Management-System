import java.util.*;
import java.io.File;
// import java.io.FileOutputStream;
import java.io.FileNotFoundException;
// import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
  public static Scanner input = new Scanner (System.in);
  public static Random rand = new Random ();
  public static String accounts [][];
  public static int failedAttempts = 0, passwordNumber=0;
  public static String black = "\u001B[30m", red = "\u001B[31m", green = "\u001B[32m", yellow = "\u001B[33m", blue = "\u001B[34m", purple = "\u001B[35m", cyan = "\u001B[36m", white = "\u001B[37m", bold = "\033[1m", italic = "\033[3m", normal = "\033[0m";

  public static String[][] readFromFile(){
    String outputAsArray1D [];
    String outputAsArray2D [][];
    String outputAsString = "";

    try {
      File file = new File("accounts.txt");
      Scanner fileInput = new Scanner (file);

      while(fileInput.hasNextLine()) {
        outputAsString+= fileInput.nextLine() + "\n";
      }
      fileInput.close();
      
    }catch (FileNotFoundException e) {
      System.out.println("An error has occured.");;
      e.printStackTrace();
    };
    
      outputAsArray1D = outputAsString.split("\n");
      outputAsArray2D = new String[outputAsArray1D.length/2][2];
    
    int j = 0;
    for (int i=0; i<outputAsArray1D.length; i++) {
      if (i%2==0) {
        outputAsArray2D[j][0]=outputAsArray1D[i];
      }else {
        outputAsArray2D[j][1]=outputAsArray1D[i];
        j++;
      }
    }
    return outputAsArray2D;
  }

  public static void writeToFile(String newLine) {
    try {
      FileWriter myWriter = new FileWriter("accounts.txt", true);
      myWriter.write("\n"+newLine);
      myWriter.close();

    }catch (IOException e) {
      System.out.println("An error has occured.");;
      e.printStackTrace();    
    }
  }

  public static void writeNewPass(String newLine) {
    try {
      FileWriter myWriter = new FileWriter("accounts.txt", false);
      myWriter.write(newLine);
      myWriter.close();

    }catch (IOException e) {
      System.out.println("An error has occured.");;
      e.printStackTrace();    
    }
  }

  public static void clearScreen() {  
      System.out.print("\033[H\033[2J");  
      System.out.flush();  
  }  

  public static void mainMenu() {
    clearScreen();
    System.out.print(bold+red+"--------Main Menu-------"+normal+white);
    System.out.print("\n\nWelcome to the main menu! Please select an option!");

    String choice;
    boolean trueValue = false;

    while (trueValue == false) {
      System.out.print("\n\n"+yellow+italic+"Enter: "+white+normal+green+"\n>> L, "+white+"to login"+blue+"\n>> C, "+white+"to create an account\n"+purple+">> E, "+white+"to exit\n");
      choice = input.nextLine ();

      if (choice.equalsIgnoreCase("L")) {
        login();
        trueValue = true;
      }else if (choice.equalsIgnoreCase("C")) {
        createAccount();
        trueValue = true;
      }else if (choice.equalsIgnoreCase("E")) {
        trueValue = true;
        System.exit(0);
      }else {
        System.out.print("\nError! Please enter a valid input!");
        mainMenu();
      }
    } 
  }

  public static void login() {
    clearScreen();
    accounts=readFromFile();
    System.out.print(bold+red+"--------Login Menu-------"+normal+white);

    String username, password;
    boolean userCheck = false, passCheck = false;
    int passNum=0, passAttempts=6;

    failedAttempts=0;

    System.out.print("\n\nTo return to the main menu, input"+blue+" return"+white);
    System.out.print("\n\nPlease enter your "+black+"username"+white+": ");
    username = input.nextLine ();

    if (username.equalsIgnoreCase("return")) {
      mainMenu(); 
    }

    
    while (userCheck==false) {
      for (int i=0; i<accounts.length; i++) {
        if (username.equals(accounts[i][0])) {
          userCheck = true;
          passNum = i;
          passwordNumber=i;
        }
      }
      if (username.equalsIgnoreCase("return")) {
        mainMenu(); 
      }
      if (userCheck==false) {
        System.out.print("\nThis "+black+"username"+white+" does not exist. \n\nPlease enter a valid username: ");
        username = input.nextLine ();  
      }
    }

    System.out.print("\nPlease enter your "+black+"password"+white+": ");
    password = input.nextLine ();

    if (password.equalsIgnoreCase("return")) {
      mainMenu(); 
    }

    while (passCheck==false) {
      if (password.equals(accounts[passNum][1])) {
        loggedMenu();
        passCheck=true;
      }else {
        if (password.equalsIgnoreCase("return")) {
          mainMenu(); 
        }else {
          if (passAttempts>1) {
            passAttempts--;
            failedAttempts++;
          System.out.print("\nIncorrect "+black+"password"+white+". You have " +passAttempts+ " attempts left.\nPlease try again: ");
          password = input.nextLine ();
          }else {
            System.out.print("\nYou got locked out!");
            mainMenu();
          }
        }
      }
    }
  }

  public static void loggedMenu() {
    clearScreen();
    System.out.print(bold+red+"--------Logged in Menu-------"+normal+white);
    String userInput;
    boolean validInput = false;
    System.out.print("\n\nWelcome! You have now logged in!\n\nThere were previously "+red+failedAttempts+white+" failed attempt(s) to log into this account\n\n"+yellow+italic+"Enter: "+white+normal+green+"\n>> logout"+white+" to log out \n"+blue+">> changepass"+white+" to change your "+black+"password"+white+purple+"\n>> 1"+white+" to access Rock, Paper, Scissors \n"+green+">> 2"+white+" to access 100m Dash Simulator\n"+blue+">> 3"+white+" to access Cash Machine\n"+purple+">> 4"+white+" to access Quadratic Formula Calculator\n");


    userInput = input.nextLine ();

    while (validInput == false) {
      switch (userInput) {
        case "logout":
          validInput = true;
          mainMenu();
          break;
        case  "changepass":
          validInput = true;
          changePassword();
          break;
        case "1":
          validInput = true;
          program1();
          break;
        case "2":
          validInput = true;
          program2();
          break;
        case "3":
          validInput = true;
          program3();
          break;
        case "4":
          validInput = true;
          program4();
          break;
        default:
          System.out.print("\nError! Please enter a valid input!\n");
          userInput = input.nextLine ();
      }
    }
  }

  public static void createAccount() {
    clearScreen();
    accounts=readFromFile();
    System.out.print(bold+red+"--------Create Account-------"+normal+white);

    String newUser="", newPass="", key="";
    boolean existingUser=true;

    System.out.print("\n\nTo return to the main menu, input"+blue+" return"+white);
    System.out.print("\n\nCreate a "+black+"username"+white+": ");
    newUser = input.nextLine ();
    

    if (newUser.equalsIgnoreCase("return")) {
      mainMenu(); 
    }

    while (existingUser==true) {
      for (int i=0; i<accounts.length; i++) {
        if (newUser.equals(accounts[i][0])) {
          System.out.print("\nThe "+black+"username"+white+" you have entered already exists. Please create a different "+black+"username"+white+": ");
          newUser = input.nextLine ();
          existingUser=true;
          if (newUser.equalsIgnoreCase("return")) {
            mainMenu(); 
          }
        }else {
          existingUser=false;
        }
      }
    }
    System.out.print ("\nCreate a "+black+"password"+white+": ");
    newPass = input.nextLine ();

    if (newPass.equalsIgnoreCase("return")) {
      mainMenu(); 
    }
    writeToFile(newUser);
    writeToFile(newPass);
    System.out.print("\nYou have successfully created an account! ");

    while (!key.equalsIgnoreCase("return")) {
      System.out.print("Input "+blue+"return"+white+" to go back to the main menu: ");
      key = input.nextLine ();
      if (key.equalsIgnoreCase("return")) {
        mainMenu();
      }
    }
  }
  public static void changePassword() {
    clearScreen();
    accounts=readFromFile();
    String oldPass, newPass, newAccount="";
    int passNumber=0, counter=0;
    boolean passwordCheck=false;

    System.out.print(bold+red+"--------Password Change-------"+normal+white);
    System.out.print("\n\nTo return to the main menu, input"+blue+" return"+white+"\n\nPlease input your old "+black+"password"+white+": ");
    oldPass = input.nextLine ();

    while (passwordCheck==false) {
      for (int i=0; i<accounts.length; i++) {
        if (oldPass.equals(accounts[passwordNumber][1])) {
          passNumber = passwordNumber;
          passwordCheck=true;
        }
      }
      if (oldPass.equalsIgnoreCase("return")) {
            loggedMenu();
            passwordCheck=true;
      }
      if (passwordCheck==false){
        passwordCheck=false;
        System.out.print("\nIncorrect "+black+"password"+white+". Please try again: ");
        oldPass = input.nextLine ();
      }
    }

    System.out.print("\nPlease input your new "+black+"password"+white+": ");
    newPass = input.nextLine ();

    while(newPass.equals(oldPass)) {
      System.out.print("\nPlease input a NEW "+black+"password"+white+": ");
      newPass = input.nextLine ();
    }

    if (newPass.equalsIgnoreCase("return")) {
      loggedMenu();
    }
    accounts[passNumber][1] = newPass;
    System.out.print(passNumber);
    for (String [] user : accounts) {
      for (String output : user) {
        if (counter==0) {
          newAccount += output;
          counter++;
        }else {
          newAccount += "\n"+output;
        }
      }
    }
    writeNewPass(newAccount);

    System.out.print("\n\nYour "+black+"password"+white+" has now been updated!");
    returnToLoggedMenu();
  }
  public static void program1() {
    clearScreen();
    int ai;
    String player;
    boolean validOption = false;

    System.out.print(bold+red+"--------Rock, Paper, Scissor-------"+normal+white);
    System.out.print("\n\nWelcome to Rock, Paper, Scissors!");

    ai = rand.nextInt (1, 4); //1=rock, 2=paper, 3=scissor
    System.out.print("\n\nPlease choose either"+green+" rock"+white+", "+blue+"paper"+white+", or "+purple+"scissor"+white+": ");
    player = input.nextLine ().toLowerCase();

    while (validOption == false) {
      switch (player) {
        case "rock":
          validOption = true;

          try {
            System.out.print(yellow+"\nReady?"+white);
            Thread.sleep(1000);
            System.out.print(green+"\n\nRock!"+white);
            Thread.sleep(500);
            System.out.print(blue+"\nPaper!"+white);
            Thread.sleep(500);
            System.out.print(purple+"\nScissor!\n"+white);
            Thread.sleep(500);
          }catch (Exception e) {
            e.printStackTrace();
          }

          if (ai==1) {
            System.out.print ("\nIt's a draw! \nYou and the computer both chose rock!");
          }
          else if (ai==2) {
            System.out.print ("\nBetter luck next time. \nYou chose rock while the computer chose paper. \nYou lost!");
          }
          else {
            System.out.print ("\nCongratulations! \nYou chose rock while the computer chose scissor!\nYou won!");
          }
          break;
        case "paper":
          validOption = true;

          try {
            System.out.print(yellow+"\nReady?"+white);
            Thread.sleep(1000);
            System.out.print(green+"\n\nRock!"+white);
            Thread.sleep(500);
            System.out.print(blue+"\nPaper!"+white);
            Thread.sleep(500);
            System.out.print(purple+"\nScissor!\n"+white);
            Thread.sleep(500);
          }catch (Exception e) {
            e.printStackTrace();
          }

          if (ai==1) {
            System.out.print ("\nCongratulations! \nYou chose paper while the computer chose rock!\nYou won!");
          }
          else if (ai==2) {
            System.out.print ("\nIt's a draw! \nYou and the computer both chose paper!");
          }
          else {
            System.out.print ("\nBetter luck next time. \nYou chose paper while the computer chose scissor. \nYou lost!");
          }
          break;
        case "scissor":
          validOption = true;

          try {
            System.out.print(yellow+"\nReady?"+white);
            Thread.sleep(1000);
            System.out.print(green+"\n\nRock!"+white);
            Thread.sleep(500);
            System.out.print(blue+"\nPaper!"+white);
            Thread.sleep(500);
            System.out.print(purple+"\nScissor!\n"+white);
            Thread.sleep(500);
          }catch (Exception e) {
            e.printStackTrace();
          }

          if (ai==1) {
            System.out.print ("\nBetter luck next time. \nYou chose scissor while the computer chose rock. \nYou lost!");
          }
          else if (ai==2) {
            System.out.print ("\nCongratulations! \nYou chose scissor while the computer chose paper!\nYou won!");
          }
          else {
            System.out.print ("\nIt's a draw! \nYou and the computer both chose scissor!");
          }
        break;
        default: 
          System.out.print ("\nInvalid Option. Please try again.\n\nPlease choose either"+green+" rock"+white+", "+blue+"paper"+white+", or "+purple+"scissor"+white+": ");
          player = input.nextLine ().toLowerCase();
      }
    }
     returnToLoggedMenu();
  }
  public static void program2() {
    clearScreen();

    int distCovered, distCovered2, distCovered3, distCovered4, r1Position=0, r2Position=0, r3Position=0, r4Position=0, time=0, r1End=0, r2End=0, r3End=0, r4End=0, firstEnd=0, secEnd=0, thirdEnd=0, fourthEnd=0, raceStartTime = rand.nextInt (50,1000);
    boolean r1finished=false, r2finished=false, r3finished=false, r4finished=false, r1Check=false, r2Check=false, r3Check=false, r4Check=false, playerSelected=false;
    String output="", first="", second="", third="", fourth="", player="";
    double speed1, speed2, speed3, speed4;


    System.out.print(bold+red+"--------100m Dash Simulator-------"+normal+white);
    System.out.print("\n\nWelcome to a 100m Dash Simulator!");
    System.out.print("\n\nPlease select a "+yellow+"runner: \n>> 1 \n>> 2\n>> 3\n>> 4"+white+"\n");
    player = input.nextLine ();

    while (playerSelected==false) {
      if (player.equals("1") || player.equals("2") || player.equals("3") || player.equals("4")) {
        playerSelected=true;
      }else {
        System.out.print("Please selected a "+yellow+"runner"+white+" who is in the race!\n");
        player = input.nextLine ();
      }
    }

    try {
      System.out.print(red+italic+"\nOn your marks..."+white+normal);
      Thread.sleep(500);
      System.out.print(yellow+italic+"\n\nGet set..."+white+normal);
      Thread.sleep(raceStartTime);
      System.out.print(green+italic+"\n\nGo!"+white+normal);
    }catch (Exception e) {
      e.printStackTrace();
    }

    System.out.print(purple+bold+"\n         R 1   R 2   R 3   R 4"+white+normal);

    MainLoop:
    while (true) {
      time++;
      distCovered = rand.nextInt (2,8);
      distCovered2 = rand.nextInt (2,8);
      distCovered3 = rand.nextInt (2,8);
      distCovered4 = rand.nextInt (2,8);
      r1Position+=distCovered;
      r2Position+=distCovered2;
      r3Position+=distCovered3;
      r4Position+=distCovered4;


      if (r1Position<=100 && r2Position<=100 && r3Position<=100 && r4Position<=100 ) {
        if (time>=10) {
          if (r1Position<=100) {
            if (r1Position==100) {
              output+=r1Position;
              output +="m  ";
            }else{
              output+=r1Position;
              output +="m   ";
            }
          }
          if (r2Position<=100) {
            if (r2Position==100) {
              output+=r2Position;
              output +="m  ";
            }else{
              output+=r2Position;
              output +="m   ";
            }
          }
          if (r3Position<=100) {
            if (r3Position==100) {
              output+=r3Position;
              output +="m  ";
            }else{
              output+=r3Position;
              output +="m   ";
            }
          }
          if (r4Position<=100) {
            if (r4Position==100) {
              output+=r4Position;
              output +="m  ";
            }else{
              output+=r4Position;
              output +="m   ";
            }
          }
          try {
            System.out.print(blue+"\nt = "+time+"   "+white+output);
            Thread.sleep(250);
          }catch (Exception e) {
            e.printStackTrace();
          }
          output="";
        }else{
          if (r1Position<=100) {
              output+=r1Position;
              output +="m";
          }
          if (r2Position<=100) {
            if (r2Position<10) {
                output +="    ";
                output+=r2Position;
                output +="m";
            }else{
              output +="   ";
              output+=r2Position;
              output +="m";
              }
          }
          if (r3Position<=100) {
            if (r3Position<10) {
              output +="    ";
              output+=r3Position;
              output +="m";
            }else{
              output +="   ";
              output+=r3Position;
              output +="m";
              }
          }
          if (r4Position<=100) {
            if (r4Position<10) {
              output +="    ";
              output+=r4Position;
              output +="m";
            }else{
              output +="   ";
              output+=r4Position;
              output +="m";
              }
          }
          if (r1Position<10) {
            try {
              System.out.print(blue+"\nt = "+time+"     "+white+output);
              Thread.sleep(250);
            }catch (Exception e) {
              e.printStackTrace();
            }
            output="";
          }else {
            try {
              System.out.print(blue+"\nt = "+time+"    "+white+output);
              Thread.sleep(250);
            }catch (Exception e) {
              e.printStackTrace();
            }
            output="";
          }
        }
      }

      if (r1Position>=100 || r2Position>=100 || r3Position>=100 || r4Position>=100) {
        if (r1Position>=100 && r1finished==false) {
          r1finished=true;
          System.out.print ("\nRunner 1 has finished the race!");
          output +="      ";   
        }else {
          if (r1finished) {
            output +="      ";
            r1End+=1;
          }else {
          output+=r1Position;
          output +="m   ";
          }
        }
        if (r2Position>=100 && r2finished==false) {
          r2finished=true;
          System.out.print ("\nRunner 2 has finished the race!");
          output +="      ";
        }else {
          if (r2finished) {
            output +="      ";
            r2End+=1;
          }else {
          output+=r2Position;
          output +="m   ";
          }
        }
        if (r3Position>=100 && r3finished==false) {
          r3finished=true;
          System.out.print ("\nRunner 3 has finished the race!");
          output +="      ";
        }else {
          if (r3finished==true) {
            output +="      ";
            r3End+=1;
          }else {
          output+=r3Position;
          output +="m   ";
          }
        }
        if (r4Position>=100 && r4finished==false) {
          r4finished=true;
          System.out.print ("\nRunner 4 has finished the race!");
          output +="      ";
        }else {
          if (r4finished==true) {
            output +="     ";
            r4End+=1;
          }else {
          output+=r4Position;
          output +="m   ";
          }
        }
        try {
          System.out.print(blue+"\nt = "+time+"   "+white+output);
          Thread.sleep(250);
        }catch (Exception e) {
          e.printStackTrace();
        }
        output="";
      }


      if (r1finished && r2finished && r3finished && r4finished) {             //when all four runners finish
        //determines the order in which wach runner finishes
        if (r1End>r2End && r1End>r3End && r1End>r4End) {
          if (player.equals("1")) {
            first =yellow+"You"+white;
          }else {
            first ="Runner 1";
          }
          firstEnd=r1End;
          r1Check=true;
        }else if ((r1End>r2End && r1End>r3End && r1End<r4End) || (r1End>r2End && r1End>r4End && r1End<r3End) || (r1End>r3End && r1End>r4End && r1End<r2End)) {
          if (player.equals("1")) {
            second =yellow+"You"+white;
          }else {
            second ="Runner 1";
          }
          secEnd=r1End;
          r1Check=true;
        }else if ((r1End>r2End && r1End<r3End && r1End<r4End) || (r1End>r3End && r1End<r4End && r1End<r2End) || (r1End>r4End && r1End<r3End && r1End<r2End)) {
          if (player.equals("1")) {
            third =yellow+"You"+white;
          }else {
            third ="Runner 1";
          }
          thirdEnd=r1End;
          r1Check=true;
        }else if (r1End<r2End && r1End<r3End && r1End<r4End) {
          if (player.equals("1")) {
            fourth =yellow+"You"+white;
          }else {
            fourth ="Runner 1";
          }
          fourthEnd=r1End;
          r1Check=true;
        }

        if (r2End>r1End && r2End>r3End && r2End>r4End) {
          if (player.equals("2")) {
            first =yellow+"You"+white;
          }else {
            first ="Runner 2";
          }
          firstEnd=r2End;
          r2Check=true;
        }else if ((r2End>r1End && r2End>r3End && r2End<r4End) || (r2End>r1End && r2End>r4End && r2End<r3End) || (r2End>r3End && r2End>r4End && r2End<r1End)) {
          if (player.equals("2")) {
            second =yellow+"You"+white;
          }else {
            second ="Runner 2";
          }
          secEnd=r2End;
          r2Check=true;
        }else if ((r2End>r1End && r2End<r3End && r2End<r4End) || (r2End>r3End && r2End<r4End && r2End<r1End) || (r2End>r4End && r2End<r3End && r2End<r1End)) {
          if (player.equals("2")) {
            third =yellow+"You"+white;
          }else {
            third ="Runner 2";
          }
          thirdEnd=r2End;
          r2Check=true;
        }else if (r2End<r1End && r2End<r3End && r2End<r4End) {
          if (player.equals("2")) {
            fourth =yellow+"You"+white;
          }else {
            fourth ="Runner 2";
          }
          fourthEnd=r2End;
          r2Check=true;
        }

        if (r3End>r2End && r3End>r1End && r3End>r4End) {
          if (player.equals("3")) {
            first =yellow+"You"+white;
          }else {
            first ="Runner 3";
          }
          firstEnd=r3End;
          r3Check=true;
        }else if ((r3End>r2End && r3End>r1End && r3End<r4End) || (r3End>r2End && r3End>r4End && r3End<r1End) || (r3End>r1End && r3End>r4End && r3End<r2End)) {
          if (player.equals("3")) {
            second =yellow+"You"+white;
          }else {
            second ="Runner 3";
          }
          secEnd=r3End;
          r3Check=true;
        }else if ((r3End>r2End && r3End<r2End && r3End<r4End) || (r3End>r1End && r3End<r4End && r3End<r2End) || (r3End>r4End && r3End<r1End && r3End<r2End)) {
          if (player.equals("3")) {
            third =yellow+"You"+white;
          }else {
            third ="Runner 3";
          }
          thirdEnd=r3End;
          r3Check=true;
        }else if (r3End<r2End && r3End<r1End && r3End<r4End) {
          if (player.equals("3")) {
            fourth =yellow+"You"+white;
          }else {
            fourth ="Runner 3";
          }
          fourthEnd=r3End;
          r3Check=true;
        }

        if (r4End>r2End && r4End>r3End && r4End>r1End) {
          if (player.equals("4")) {
            first =yellow+"You"+white;
          }else {
            first ="Runner 4";
          }
          firstEnd=r4End;
          r4Check=true;
        }else if ((r4End>r2End && r4End>r3End && r4End<r1End) || (r4End>r2End && r4End>r1End && r4End<r3End) || (r4End>r3End && r4End>r1End && r4End<r2End)) {
          if (player.equals("4")) {
            second =yellow+"You"+white;
          }else {
            second ="Runner 4";
          }
          secEnd=r4End;
          r4Check=true;
        }else if ((r4End>r2End && r4End<r3End && r4End<r1End) || (r4End>r3End && r4End<r1End && r4End<r2End) || (r4End>r1End && r4End<r3End && r4End<r2End)) {
          if (player.equals("4")) {
            third =yellow+"You"+white;
          }else {
            third ="Runner 4";
          }
          thirdEnd=r4End;
          r4Check=true;
        }else if (r4End<r2End && r4End<r3End && r4End<r1End) {
          if (player.equals("4")) {
            fourth =yellow+"You"+white;
          }else {
            fourth ="Runner 4";
          }
          fourthEnd=r4End;
          r4Check=true;
        }

        if (first.equals("")) {
          if (r1Check==false) {
            if (player.equals("1")) {
              first =yellow+"You"+white;
            }else {
              first ="Runner 1";
            }
            firstEnd=r1End;
            r1Check=true;
          }else if (r2Check==false) {
            if (player.equals("2")) {
              first =yellow+"You"+white;
            }else {
              first ="Runner 2";
            }
            firstEnd=r2End;
            r2Check=true;
          }else if (r3Check==false) {
            if (player.equals("3")) {
              first =yellow+"You"+white;
            }else {
              first ="Runner 3";
            }
            firstEnd=r3End;
            r3Check=true;
          }else if (r4Check==false) {
            if(player.equals("4")) {
              first =yellow+"You"+white;
            }else {
              first ="Runner 4";
            }
            firstEnd=r4End;
            r4Check=true;
          }
        }
        if (second.equals("")) {
          if (r1Check==false) {
            if (player.equals("1")) {
              second =yellow+"You"+white;
            }else {
              second ="Runner 1";
            }
            secEnd=r1End;
            r1Check=true;
          }else if (r2Check==false) {
            if (player.equals("2")) {
              second =yellow+"You"+white;
            }else {
              second ="Runner 2";
            }
            secEnd=r2End;
            r2Check=true;
          }else if (r3Check==false) {
            if (player.equals("3")) {
              second =yellow+"You"+white;
            }else {
              second ="Runner 3";
            }
            secEnd=r3End;
            r3Check=true;
          }else if (r4Check==false) {
            if (player.equals("4")) {
              second =yellow+"You"+white;
            }else {
              second ="Runner 4";
            }
            secEnd=r4End;
            r4Check=true;
          }
        }
        if (third.equals("")) {
          if (r1Check==false) {
            if (player.equals("1")) {
              third =yellow+"You"+white;
            }else {
              third ="Runner 1";
            }
            thirdEnd=r1End;
            r1Check=true;
          }else if (r2Check==false) {
            if (player.equals("2")) {
              third =yellow+"You"+white;
            }else {
              third ="Runner 2";
            }
            thirdEnd=r2End;
            r2Check=true;
          }else if (r3Check==false) {
            if (player.equals("3")) {
              third =yellow+"You"+white;
            }else {
              third ="Runner 3";
            }
            thirdEnd=r3End;
            r3Check=true;
          }else if (r4Check==false) {
            if (player.equals("4")) {
              third =yellow+"You"+white;
            }else {
              third ="Runner 4";
            }
            thirdEnd=r4End;
            r4Check=true;
          }
        }
        if (fourth.equals("")) {
          if (r1Check==false) {
            if (player.equals("1")) {
              fourth =yellow+"You"+white;
            }else {
              fourth ="Runner 1";
            }
            fourthEnd=r1End;
            r1Check=true;
          }else if (r2Check==false) {
            if (player.equals("2")) {
              fourth =yellow+"You"+white;
            }else {
              fourth ="Runner 2";
            }
            fourthEnd=r2End;
            r2Check=true;
          }else if (r3Check==false) {
            if (player.equals("3")) {
              fourth =yellow+"You"+white;
            }else {
              fourth ="Runner 3";
            }
            fourthEnd=r3End;
            r3Check=true;
          }else if (r4Check==false) {
            if (player.equals("4")) {
              fourth =yellow+"You"+white;
            }else {
              fourth ="Runner 4";
            }
            fourthEnd=r4End;
            r4Check=true;
          }
        }

        //calculates average speed of each runner
        speed1 = Math.round((100.0 / (time-firstEnd))*100);
        speed1 = speed1/100;
        speed2 = Math.round((100.0 / (time-secEnd))*100);
        speed2 = speed2/100;
        speed3 = Math.round((100.0 / (time-thirdEnd))*100);
        speed3 = speed3/100;
        speed4 = Math.round((100.0 / (time-fourthEnd))*100);
        speed4 = speed4/100;

        //output report
        System.out.print(cyan+bold+"\n\nResults: "+white+normal);
        System.out.print("\n"+first + " finished first with an average speed of " + green + speed1 + white + " m/s, ");
        System.out.print("\n"+second + " finished second with an average speed of " + green + speed2 + white + " m/s, ");
        System.out.print("\n"+third + " finished third with an average speed of " + green + speed3 + white + " m/s, ");
        System.out.print("\n"+fourth + " finished fourth with an average speed of " + green + speed4 + white + " m/s!");
        break MainLoop;
      }
    }

    returnToLoggedMenu();
  }
  public static void program3() {
    clearScreen();

    String moneyAsString="";
    double money=0;
    int hundredBill, fiftyBill, twentyBill, tenBill, fiveBill, toonie, loonie, quarter, dime, nickel;
    boolean validMoney=false;

    System.out.print(bold+red+"--------Cash Machine-------"+normal+white);
    System.out.print("\n\nWelcome to "+green+"Cash"+white+" Machine!");

    MainLoop:
    while (validMoney==false) {
      if (moneyAsString.equals("")) {
        System.out.print("\n\nPlease enter a "+green+"money"+white+" amount you want to convert to \ncash: "+green+"$");
        moneyAsString = input.nextLine ();
      }else {
        System.out.print(white+"\nPlease enter a valid amount of money: "+green+"$");
        moneyAsString = input.nextLine ();
      }
      try {
        money = Double.parseDouble(moneyAsString); 
      }catch (Exception e) {
        continue MainLoop;
      }

      money = (Math.round(money*100.0))/100.0;
      money*= 100;
      money = 5*(Math.round(money/5));
      int change = (int) Math.round(money); 

      hundredBill = change/10000;
      fiftyBill = (change-(10000*hundredBill))/5000;
      twentyBill = (change-((10000*hundredBill)+(5000*fiftyBill)))/2000;
      tenBill = (change-((10000*hundredBill)+(5000*fiftyBill)+(2000*twentyBill)))/1000;
      fiveBill = (change-((10000*hundredBill)+(5000*fiftyBill)+(2000*twentyBill)+(1000*tenBill)))/500;
      toonie = (change-((10000*hundredBill)+(5000*fiftyBill)+(2000*twentyBill)+(1000*tenBill)+(500*fiveBill)))/200;
      loonie = (change-((10000*hundredBill)+(5000*fiftyBill)+(2000*twentyBill)+(1000*tenBill)+(500*fiveBill)+(200*toonie)))/100;
      quarter = (change-((10000*hundredBill)+(5000*fiftyBill)+(2000*twentyBill)+(1000*tenBill)+(500*fiveBill)+(200*toonie)+(100*loonie)))/25;
      dime = (change-((10000*hundredBill)+(5000*fiftyBill)+(2000*twentyBill)+(1000*tenBill)+(500*fiveBill)+(200*toonie)+(100*loonie)+(25*quarter)))/10;
      nickel = (change-((10000*hundredBill)+(5000*fiftyBill)+(2000*twentyBill)+(1000*tenBill)+(500*fiveBill)+(200*toonie)+(100*loonie)+(25*quarter)+(10*dime)))/5;

      if (money>=0.03) {
        validMoney=true;
        System.out.print(white+"\nYour change is ");
        if (hundredBill==1) {
          System.out.print(hundredBill+ " $100 bill");
        }else if (hundredBill>1) {
          System.out.print(hundredBill+ " $100 bills");
        }
        if (fiftyBill==1) {
          if (change>=9997) {
          System.out.print(", ");
          }
          System.out.print(fiftyBill+ " $50 bill");
        }
        if (twentyBill==1) {
          if (change>=4997) {
          System.out.print(", ");
          }
          System.out.print(twentyBill+ " $20 bill");
        }else if (twentyBill>1) {
          if (change>=4997) {
          System.out.print(", ");
          }
          System.out.print(twentyBill+ " $20 bills");
          }
        if (tenBill==1) {
          if (change>=1997) {
          System.out.print(", ");
          }
          System.out.print(tenBill+ " $10 bill");
        }
        if (fiveBill==1) {
          if (change>=997) {
          System.out.print(", ");
          }
          System.out.print(fiveBill+ " $5 bill");
        }
        if (toonie==1) {
          if (change>=497) {
          System.out.print(", ");
          }
          System.out.print(toonie+ " Toonie");
        }else if (toonie>1) {
          if (change>=497) {
          System.out.print(", ");
          }
          System.out.print(toonie+ " Toonies");
          }
        if (loonie==1) {
          if (change>=197) {
          System.out.print(", ");
          }
          System.out.print(loonie+ " Loonie");
        }
        if (quarter==1) {
          if (change>=97) {
          System.out.print(", ");
          }
          System.out.print(quarter+ " Quarter");
        }else if (quarter>1) {
          if (change>=97) {
          System.out.print(", ");
          }
          System.out.print(quarter+ " Quarters");
        }
        if (dime==1) {
          if (change>=23) {
          System.out.print(", ");
          }
          System.out.print(dime+ " dime");
        }else if (dime>1) {
          if (change>=23) {
          System.out.print(", ");
          }
          System.out.print(dime+ " dimes");
        }
        if (nickel==1) {
          if (change>=8) {
          System.out.print(", ");
          }
          System.out.print(nickel+ " nickel");
        }
      System.out.print(".");
      }else {
        continue MainLoop;
      }
    } 
    returnToLoggedMenu();
  }
  public static void program4() {
    clearScreen();

    String aAsString, bAsString, cAsString;
    double a=0, b=0, c=0, discriminant, solution1, solution2;
    boolean aCheck=false, bCheck=false, cCheck=false;
    int aErrorCount=0, bErrorCount=0, cErrorCount=0;

    System.out.print(bold+red+"--------Quadratic Formula Calculator-------"+normal+white);
    System.out.print("\n\nThis program gives you the roots of a quadratic equation given the values of "+green+italic+"a"+white+normal+","+blue+italic+" b"+white+normal+", and "+purple+italic+"c"+white+normal+"!\n");

    ALoop:
    while (aCheck==false) {
      if (aErrorCount==0) {
        System.out.print("\nPlease enter your "+green+italic+"a"+white+normal+" value: ");
      }else {
        System.out.print("\nPlease enter a valid "+green+italic+"a"+white+normal+" value: ");
      }
      aAsString = input.nextLine ();
      try {
        a = Double.parseDouble(aAsString);
        if (a==0) {
          System.out.print("\nYour "+green+italic+"a"+white+normal+" value should not be 0 in a quadratic equation.");
        aErrorCount++;
        continue ALoop;
        }
      }catch (Exception e){
        aErrorCount++;
        continue ALoop;
      }
      aCheck=true;
    }

    BLoop:
    while (bCheck==false) {
      if (bErrorCount==0) {
        System.out.print("\nPlease enter your "+blue+italic+"b"+white+normal+" value: ");
      }else {
        System.out.print("\nPlease enter a valid "+blue+italic+"b"+white+normal+" value: ");
      }
      bAsString = input.nextLine ();
      try {
        b = Double.parseDouble(bAsString);
      }catch (Exception e){
        bErrorCount++;
        continue BLoop;
      }
      bCheck=true;
    }

    CLoop:
    while (cCheck==false) {
      if (cErrorCount==0) {
        System.out.print("\nPlease enter your "+purple+italic+"c"+white+normal+" value: ");
      }else {
        System.out.print("\nPlease enter a valid "+purple+italic+"c"+white+normal+" value: ");
      }
      cAsString = input.nextLine ();
      try {
        c = Double.parseDouble(cAsString);
      }catch (Exception e){
        cErrorCount++;
        continue CLoop;
      }
      cCheck=true;
    }

    discriminant = (Math.pow (b, 2) - (4*a*c));

    solution1 = ((b*-1)-(Math.sqrt(Math.pow(b,2)-(4*a*c))))/(2*a);
    solution2 = ((b*-1)+(Math.sqrt(Math.pow(b,2)-(4*a*c))))/(2*a);

    solution1 = Math.round(solution1*100.0)/100.0;
    solution2 = Math.round(solution2*100.0)/100.0;

    if (discriminant>0) {
    System.out.print("\n"+yellow+bold+"Answer:"+white+normal+"\n\nThere are "+cyan+"two"+white+" real roots: " +solution1+ " and " +solution2);
  }else if (discriminant==0) {
    System.out.print("\n"+yellow+bold+"Answer:"+white+normal+"\n\nThere is "+cyan+"one"+white+" real root: "+solution1);
  }else {
    System.out.print("\n"+yellow+bold+"Answer:"+white+normal+"\n\nThere are "+cyan+"no"+white+" real roots.");
    }
    returnToLoggedMenu();
  }
  public static void returnToLoggedMenu() {
    String back="";
    int counter=0;

    while (!back.equalsIgnoreCase("return")) {
      if (counter==0) {
        System.out.print("\n\nInput "+blue+"return"+white+ " to go back: ");
        back = input.nextLine ();
        counter++;
      }else {
        System.out.print("Input "+blue+"return"+white+ " to go back: ");
        back = input.nextLine ();
      }
      if (back.equalsIgnoreCase("return")) {
        loggedMenu();
      }
    }
  }

  public static void main(String[] args) {
    accounts=readFromFile();
    mainMenu();
  }
}