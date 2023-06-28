
// Import JAva packages used for this Application
import java.util.*;
import java.io.*;
import java.text.NumberFormat;

/**
 * This Application is used to provide the user with detail cost analysis
 * between a electrical vehicle and a gas-powered vehicle based in the users
 * input for their expected vehicle price, anual travel distance and prefered
 * vehicle type.
 *
 * @author Ryan Xiang
 * 
 * @version 1.0
 * 
 * @since 2022-04-11
 */
class Main {

  // Declare constant int varible for each menu to avoid magic numbers
  private static final int MENU_VEHICLE_TYPE = 1;
  private static final int MENU_PRICE_RANGE = 2;
  private static final int MENU_ANUAL_KILOMETRAGE = 3;
  private static final int MENU_PREFERED_EV_TYPE = 4;

  // Declare constant int varibles for menu items in "vehicl type" menu
  // to avoid magic numbers
  private static final int MENU_VEHICLE_TYPE_SEDAN = 1;
  private static final int MENU_VEHICLE_TYPE_EXIT = 3;

  // Declare constant int varibles for menu items in "price range type" menu
  // to avoid magic numbers
  private static final int MENU_PRICE_RANGE_UNDER_40K = 1;
  private static final int MENU_PRICE_RANGE_BACK = 4;
  private static final int MENU_PRICE_RANGE_EXIT = 5;

  // Declare constant int varibles for menu items in "price range type" menu
  // to avoid magic numbers
  private static final int MENU_ANUAL_KILOMETRAGE_5K_10K = 1;
  private static final int MENU_ANUAL_KILOMETRAGE_BACK = 7;
  private static final int MENU_ANUAL_KILOMETRAGE_EXIT = 8;

  // Declare constant String variables for Electrical/Gas vehicles to avoid magin
  // numbers
  private static final String ELECTRICAL_VEHICLE = "EV";
  private static final String GAS_POWERED_VEHICLE = "GV";

  // Declare constant int variables for Electrical vehicle battery
  // warranty in year and km to avoid magin numbers
  private static final int EV_BATTERY_WARRANTY_YEAR = 8;
  private static final int EV_BATTERY_WARRANTY_KM = 160000;

  // Store the contents of static menus loaded from menu.txt for easy display
  private static String menuStr = "";

  // Initialize two dimensional array to all menus and their corresponding menu
  // items
  private static String[][] menu_data_mapping = new String[10][10];
  // Initialize an array to to store the user inputs from each menu
  private static String[] input_summary = new String[5];
  // Declare varialbe to srore the price of selected EV
  private static double ev_price;
  // Declare variable to store the gorvenment rebate for the selected EV
  private static double ev_rebate;
  // Declare variable to store the price of the selected EV after rebate
  private static double ev_price_after_rebate;
  // Declare variable to store the price gas vehicle equivalent to the selected
  // electrical vehicle
  private static double match_gv_price;
  // Declare variable to store estimated fuel cost of the EV selected and other
  // relevant user input such as annual travel distance
  private static double ev_fuel_cost;
  // Declare variable to store estimated fuel cost of the equivalent GV
  private static double match_gv_fuel_cost;
  // Declare variable to store estimated anual upkeep cost of the selected EV
  private static double ev_upkeep_cost;
  // Declare variable to store estimated anual upkeep cost of the equivalent GV
  private static double match_gv_upkeep_cost;

  /**
   *
   * main method
   * 
   * @param args commandline arguments
   */
  public static void main(String[] args) {

    // Populate each menu and its corresponding menu items
    populateMenuDataMapping();
    // Load menu contents from 'menu.txt' file for easy display of formatted menu
    loadMenusContext();
    // Display the starting menu of the application
    displayMenu("MENU_APPLICATION_LOAD");

    // Initizlize int variable 'curMenu' to the 1st menu after application load and
    // use it to track the menu navigation forward and back easily
    int curMenu = MENU_VEHICLE_TYPE;

    // Initialize Scanner object to read in user input
    Scanner input = new Scanner(System.in);

    // While loop is used to all the users to repeatedly navigate through menus,
    // make selection and display the cost results, unless the selection to exit
    // the application execution is selected.
    while (true) {

      if (curMenu == MENU_VEHICLE_TYPE) {
        // Menu navigation comes to the menu for selecting the type of vehicle
        // like 'Sedan' or 'Minivan'

        // Invoke mentod 'displayMenu' to display the menu for selecting vehicle type
        displayMenu("MENU_VEHICLE_TYPE");
        // Prompt the users to select the type of the vehicle
        System.out.print("Please select the type of vehicle: ");
        int menuSelection = Integer.parseInt(input.next());

        // Prompt the users to re-enter a valid selection if they have entered an
        // invalid one
        if (menuSelection < MENU_VEHICLE_TYPE_SEDAN || menuSelection > MENU_VEHICLE_TYPE_EXIT) {
          System.out.println("Invalid selection. You should enter a number 1 - 3");
          System.out.println();
          continue;
        }

        // Exit the application execution if the users select to exit
        if (menuSelection == MENU_VEHICLE_TYPE_EXIT) {
          System.out.println("Thank you for using the program. Goodbye.");
          break;
        }

        // A valid selection is entered if the code execution reaches here.
        // Store the user input from this menu
        input_summary[curMenu] = "-\tVehicle Type: " + getSelecteditemText(curMenu, menuSelection);
        System.out.println("");

        clearScreen();
        // Navigate to the next menu
        curMenu++;

      } else if (curMenu == MENU_PRICE_RANGE) {
        // Menu navigation comes to the menu for selecting the price range of the EV
        // the users are look for.

        // Invoke mentod 'displayMenu' to display the menu for selecting price range
        displayMenu("MENU_PRICE_RANGE");

        // Prompt the users to select price range
        System.out.print("Please select the price range: ");
        int menuSelection = Integer.parseInt(input.next());

        // Prompt the users to re-enter a valid selection if they have entered an
        // invalid one
        if (menuSelection < MENU_PRICE_RANGE_UNDER_40K || menuSelection > MENU_PRICE_RANGE_EXIT) {
          System.out.println("Invalid selectioN. You should enter a number 1 - 5");
          continue;
        }

        // If the users selected to go back to prev. menu, decrement 'curMenu' by 1 to
        // navigate back
        if (menuSelection == MENU_PRICE_RANGE_BACK) {
          curMenu--;
          continue;
        }

        // Exit the application execution if the users select to exit
        if (menuSelection == MENU_PRICE_RANGE_EXIT) {
          System.out.println("Thank you for using the program. Goodbye.");
          break;
        }

        // A valid selection for this menu is entered if the code execution reaches
        // here. Store the user input from this menu
        System.out.println("You have selected: " + getSelecteditemText(curMenu, menuSelection));
        input_summary[curMenu] = "-\tPrice Range: " + getSelecteditemText(curMenu, menuSelection);
        System.out.println("");

        clearScreen();
        // Navigate to next menu
        curMenu++;

      } else if (curMenu == MENU_ANUAL_KILOMETRAGE) {
        // Menu navigation comes to the menu for selecting the anual kilometerage

        // Invoke mentod 'displayMenu' to display the menu for selecting price range
        displayMenu("MENU_ANUAL_KILOMETRAGE");

        // Prompt the users to select anual kilometerage
        System.out.print("Please select estimated anual travel in Kilometers: ");
        int menuSelection = Integer.parseInt(input.next());

        // Prompt the users to re-enter a valid selection if they have entered an
        // invalid one
        if (menuSelection < MENU_ANUAL_KILOMETRAGE_5K_10K || menuSelection > MENU_ANUAL_KILOMETRAGE_EXIT) {
          System.out.println("Invalid selectio. You should enter a number 1 - 8");
          continue;
        }

        // If the users selected to go back to prev. menu, decrement 'curMenu' by 1 to
        // navigate back
        if (menuSelection == MENU_ANUAL_KILOMETRAGE_BACK) {

          clearScreen();
          curMenu--;
          continue;
        }

        // Exit the application execution if the users select to exit
        if (menuSelection == MENU_ANUAL_KILOMETRAGE_EXIT) {
          System.out.println("Thank you for using the program. Goodbye.");
          break;
        }

        // A valid selection for this menu is entered if the code execution reaches
        // here.
        input_summary[curMenu] = "-\tEstimated Anual kilometrage: " +
            getSelecteditemText(curMenu, menuSelection);
        System.out.println("");

        clearScreen();
        // Navigate to next menu
        curMenu++;

      } else if (curMenu == MENU_PREFERED_EV_TYPE) {
        // Menu navigation comes to the menu for selecting EVs based on the vehicle type
        // and price range the users has selected in the menus earlier

        // Retrieve the type of vehicle (Sedan, Minivan..) the users has selected
        String vehicleType = input_summary[1].split(":")[1].trim();
        // Retrieve the price range the users has selected
        double[] priceRange = getPriceRange();
        // Invoke the method 'findEVByTypeAndPriceRange' to get the EVs matching the
        // user selected vehicle type and price range
        ArrayList<String> matchEVs = findEVByTypeAndPriceRange(vehicleType, priceRange[0], priceRange[1]);
        // Load the menu 'prefered EV type' based on the EVs found above
        loadDynamicMenu(MENU_PREFERED_EV_TYPE, matchEVs);

        System.out.print("Please select your prefered EV type: ");
        int menuSelection = Integer.parseInt(input.next());

        // Prompt the users to re-enter a valid selection if they have entered an
        // invalid one
        if (menuSelection < 1 || menuSelection > matchEVs.size() + 2) {
          System.out.println("Invalid selection. You should enter a number 1-" + (matchEVs.size() + 2));
          continue;
        }

        // If the users selected to go back to prev. menu, decrement 'curMenu' by 1 to
        // navigate back
        if (menuSelection == matchEVs.size() + 1) {
          curMenu--;
          continue;
        }

        // Exit the application execution if the users select to exit
        if (menuSelection == matchEVs.size() + 2) {
          System.out.println("Thank you for using the program. Goodbye.");
          break;
        }

        // A valid selection for this menu is entered if the code execution reaches
        // here. Store the user input from this menu.
        System.out.println("You have selected: " + matchEVs.get(menuSelection - 1).split(",")[1].trim());
        input_summary[curMenu] = "-\tPreferred EV Type: " + matchEVs.get(menuSelection - 1).split(",")[1].trim();
        System.out.println("");

        // This is the last menu that the users need to make selection. So now display
        // all the selections the users have made before proceed for a cost summary
        // report.
        clearScreen();
        displayUserInputSummary();

        // Promopt the users to enter 1 to proceed with the cost summary report
        // or enter 2 to ga back change selections
        System.out.println("Please enter 1 to get the cost summary.");
        System.out.println("Or enter 2 to go back to modify your selection.");
        int decisionSelection = Integer.parseInt(input.next());

        if (decisionSelection == 1) {
          // The users have selected to proceed to cost summary report

          // Invoke the method 'calculateCostData' to calculate all values necessary
          // for the cost summary
          calculateCostData();
          // Invoke the method 'displayCostSummary' to display the cost summary
          displayCostSummary();

          // Invoke the method 'recordOrderPreference' to ask for users preference in UV
          // vs GV, and record the user response together with user selection input into
          // database. The data may be used by government or non-profit agencies to track
          // carbon emit potential from automobile sector and review relevant incentive or
          // rebate policy
          recordOrderPreference(input);

          // Ask the users if they want to continue with another cost assessment
          System.out.println("Do you want to continue with another cost assessment?");
          System.out.print("Enter 1 to continue, or Enter 2 to exit the program: ");
          decisionSelection = Integer.parseInt(input.next());
          System.out.println("");

          if (decisionSelection == 1) {
            // The user has selected to continue with another cost assessment
            // Clear console, reeset navigation to the first menu and continue
            curMenu = 1;

            clearScreen();
            continue;
          } else {
            // The user has selected to exit the program execution.
            clearScreen();
            System.out.println("Thank you for using the program. Goodbye.");
            break;
          }

        } else {
          curMenu--;
          continue;
        }
      }
    }
  }

  /**
   *
   * This method is used to load all menu items into a two dimensional array. It
   * helps
   * to retrieve the menu item contents easily with number entered by the users.
   */
  public static void populateMenuDataMapping() {

    // Assign the global two dimensional array with all the statice menu option
    // items. The row index refers to the menu and the col index refer to the
    // menu items within the menu.
    menu_data_mapping[1][1] = "Sedan";
    menu_data_mapping[1][2] = "Minivan";
    menu_data_mapping[1][3] = "Exit";

    menu_data_mapping[2][1] = "Under $40,000";
    menu_data_mapping[2][2] = "$40,000 - $ 60,000";
    menu_data_mapping[2][3] = "Over $60,000";
    menu_data_mapping[2][4] = "Back to previous menu";
    menu_data_mapping[2][5] = "Exit";

    menu_data_mapping[3][1] = "5,000 - 10,000";
    menu_data_mapping[3][2] = "10,000 - 15,000";
    menu_data_mapping[3][3] = "15,000 - 20,000";
    menu_data_mapping[3][4] = "20,000 - 25,000";
    menu_data_mapping[3][5] = "25,000 - 30,000";
    menu_data_mapping[3][6] = "30,000 - 40,000";
    menu_data_mapping[3][7] = "Back to previous menu";
    menu_data_mapping[3][8] = "Exit";
  }

  /**
   *
   * This method is used to image the static menus stored in file 'menu.txt' to
   * a global string so the application can easily display each menu, with the
   * format easily specified in the file.
   *
   */
  public static void loadMenusContext() {

    try {
      // Initialize the Scanner object to read data from file 'menu.txt'
      Scanner fileInput = new Scanner(new File("menu.txt"));

      // Traverse the menu.txt line by line to image the content of the file
      // into String var 'menuStr', with all format of the context reserved.
      while (fileInput.hasNextLine()) {
        menuStr += fileInput.nextLine() + "\n";
      }

      // Close the scanner to release resource
      fileInput.close();
    } catch (IOException ex) {

      // Catch exception if file 'menu.txt' does not exist and print
      // out friendly message to the users
      System.out.println("The file 'menu.txt' is not found.");
    }
  }

  /**
   *
   * This method is used to load each menu specified by its parameter.
   *
   * @param selectedMenu The menu to be displayed
   *
   */
  public static void displayMenu(String selectedMenu) {

    // From the all menuStr 'menuStr', find the start and end position of the menu
    // 'selectedMenu' to be displayed.
    int start = menuStr.indexOf("[" + selectedMenu + "_START]") + ("[" + selectedMenu + "_START]").length();
    int end = menuStr.indexOf("[" + selectedMenu + "_END]", start);

    // Using the subString method to carve out the string for 'selectedMenu' from
    // 'menuStr'
    String menuContent = menuStr.substring(start, end);

    // Plug in actual formatting code as per specified in the 'menuStr'. Ex. the
    // [BlueColor] specified in the 'menuStr' (from menu.txt file) will be replaced
    // by actual format code for blue font "\033[94m".
    menuContent = menuContent.replace("[BlueColor]", "\033[94m")
        .replace("[ColorEnd]", "\033[0m")
        .replace("[CyanColor]", "\033[96m")
        .replace("[RedColor]", "\033[91m")
        .replace("[Bold]", "\033[1m")
        .replace("[Cyan_underscore]", "\033[96m\033[4m");

    // Display the menu 'selectedMenu'
    System.out.println(menuContent);

  }

  /**
   *
   * This method is used to retrieve the text of a menu item.
   *
   * @param menuIdx     The index number of menu
   * @param menuItemIdx The index number of menu item in the menu specified
   *                    in 'menuIdx' above.
   * @return double The text of the menu item specified by method parameters.
   *
   */
  public static String getSelecteditemText(int menuIdx, int menuItemIdx) {

    // Simply retrieve the text of the menu item from 'menu_data_mapping' with
    // row/col index 'menuIdx'/'menuItemIdx'
    String result = menu_data_mapping[menuIdx][menuItemIdx];
    return result;
  }

  /**
   *
   * This method is used search for electrical vehicles matching the vehicle type
   * and price
   * range stored in the datafile "EV_inventory.txt".
   *
   * @param vehicleType The type of vehicle (like Sedan, Minivan ..)
   * @param min         The minimum price of the vehicle the users are looking for
   * @param max         The minimum price of the vehicle the users are looking for
   *
   * @return the list of strings representing the information of electrical vehicles 
   *         matching vehicle type and price ranges (min, max) passed in as parameters.
   *
   */
  public static ArrayList<String> findEVByTypeAndPriceRange(String vehicleType, double min, double max) {

    // Initialize a ArrayList of Strings to store string representation of vehicles
    // matching serarch criteria for vehicle type and min and max price range
    ArrayList<String> matchItems = new ArrayList<String>();

    try {

      // Initialize the Scanner object to read data from file 'EV_inventory.txt'
      Scanner fileInput = new Scanner(new File("EV_inventory.txt"));

      // Skip the first line as it stores the header information
      if (fileInput.hasNextLine()) {
        fileInput.nextLine();
      }

      // Traverse the content of "EV_inventory.txt" line by line and store each line,
      // each line contains all the information of an EV
      while (fileInput.hasNextLine()) {

        // Get the next line
        String line = fileInput.nextLine();
        // Split the line to store each piece of information into 'temp[]'
        String[] temp = line.split(",");
        // Retrieve the type of the vehicle
        String myVehicleType = temp[0];
        // Retrieve the price of the vehicle
        double myPrice = Double.parseDouble(temp[2].trim());
        // Add to the item to the list 'matchItems' if it matches the search criteria
        // for vehicle type and price range
        if (myVehicleType.compareToIgnoreCase(vehicleType) == 0 && myPrice >= min && myPrice <= max) {
          matchItems.add(line);
        }
      }

      // Close the scanner to release resource
      fileInput.close();
    } catch (IOException ex) {
      // Catch exception if file 'EV_inventory.txt' cannot be loaded and print
      // out friendly message to the users
      System.out.println("The file 'EV_inventory.txt' cannot be loaded.");
    }

    // Return matching EVs found.
    return matchItems;
  }

  /**
   *
   * This method is used to parse the price range from the user input and return
   * the result to the caller.
   *
   * @return double[] return the price range represented as an array of two elements, 
   * where the 1st element is min price, and 2nd element is max price.
   *
   */
  public static double[] getPriceRange() {

    // Initialize an array of double with 2 elements.
    double[] minMax = new double[2];

    // Get the string representation of price range stored in 'input_summary'
    String priceRange = input_summary[MENU_PRICE_RANGE];
    // Initialize the min / max prices
    double minPrice = 0;
    double maxPrice = Double.MAX_VALUE;

    if (priceRange.trim().indexOf("Under") != -1) {
      // Parsing price that 'Under' some value

      // Replace any letter with empty space if it is not a digit or '.'
      priceRange = priceRange.replaceAll("[^0-9.]", "");
      // Convert the 'priceRange' to double and assign it to 'maxPrice'
      // in this case 'minPrice' remains to be 0
      maxPrice = Double.parseDouble(priceRange);
    } else if (priceRange.indexOf("Over") != -1) {
      // Parsing price that 'Over' some value

      // Replace any letter with empty space if it is not a digit or '.'
      priceRange = priceRange.replaceAll("[^0-9.]", "");
      // Convert the 'priceRange' to double and assign it to 'minPrice'
      // in this case 'minPrice' remains to be Double.MAX_VALUE
      minPrice = Double.parseDouble(priceRange);
    } else {
      // Parding price range represented as "ddddd - ddddd"

      minPrice = Double.parseDouble(input_summary[2].split("-")[1].trim().replaceAll("[^0-9.]", ""));
      maxPrice = Double.parseDouble(input_summary[2].split("-")[2].trim().replaceAll("[^0-9.]", ""));
    }

    // Assign 'minPrice' and 'maxPrice' to the 1st and 2nd elements of 'minMax[]'
    // respectively
    minMax[0] = minPrice;
    minMax[1] = maxPrice;

    // Return the array that stores min / max prices
    return minMax;
  }

  /**
   *
   * This method is used to parse the kilometrage range from the user input and
   * return the result to the caller.
   *
   * @return double[] return the kilometrage range represented as an array of two elements, 
   *         where the 1st element is min kilometrage, and 2nd element is max kilometrage.
   *
   */
  public static double[] getkilometrageRange() {

    // Initialize an array of double with 2 elements.
    double[] minMax = new double[2];

    // Parsing out min and max Kilometrage
    minMax[0] = Double
        .parseDouble(input_summary[MENU_ANUAL_KILOMETRAGE].split("-")[1].trim().replaceAll("[^0-9.]", ""));
    minMax[1] = Double
        .parseDouble(input_summary[MENU_ANUAL_KILOMETRAGE].split("-")[2].trim().replaceAll("[^0-9.]", ""));

    // Return the array that stores min / max Kilometrage
    return minMax;
  }

  /**
   *
   * This method is used to display a summary of all user inputs.
   *
   */
  public static void displayUserInputSummary() {

    System.out.println("You have entered all information.");
    System.out.println("\033[1m\033[96m******* Summary of your selection *******\033[0m");
    System.out.println("");
    for (int i = 1; i < input_summary.length; i++) {
      System.out.println("  " + input_summary[i]);
    }
    System.out.println("");
    System.out.println("\033[1m\033[96m******************************************\033[0m");
    System.out.println("");
  }

  /**
   *
   * This method is used to load and display dynamic menu requested by the
   * invoker.
   *
   * @param selectedMenu the menu to load and display
   * @param menuItems    The menu item to be loaded in the 'selectedMenu'
   *
   */
  public static void loadDynamicMenu(int selectedMenu, ArrayList<String> menuItems) {

    if (selectedMenu == MENU_PREFERED_EV_TYPE) {
      // To load and display the menu for the EVs matching the user's vehicle type and
      // price range

      // Display menu header
      System.out.println("=============================================");
      System.out.println("\033[94mWhat's your prefered EV type?\033[0m");
      System.out.println("");

      // Traverse 'menuItems' and display each item with designed format
      int menuItemNo = 1;
      for (int i = 0; i < menuItems.size(); i++) {

        String[] temp = menuItems.get(i).split(",");
        // Format then number with currency price and keeps zero decimal point
        String formattedPrice = getCurrencyFormattedNum(Double.parseDouble(temp[2]), 0);
        // Display the item with specifically designed format
        System.out.printf("\t%d\t%s\t%s\n", menuItemNo++, temp[1], formattedPrice);
        System.out.printf("\t\t%s\n", temp[3]);
        System.out.printf("\t\t%s\n", temp[4]);

        System.out.println();
      }

      // Add the menu item to allow the users to navigate to prev. menu
      System.out.println("\t" + menuItemNo++ + "\t" + "Back to previous menu");
      System.out.println();
      // Add the menu item to allow the users to exit program execution
      System.out.println("\t" + menuItemNo++ + "\t" + "Exit");

      System.out.println("=============================================");
    }
  }

  /**
   *
   * This method is used to calculate all the data that need to displayed in the
   * cost summary report.
   *
   */
  public static void calculateCostData() {

    ev_price = 0; // Store the price of selected EV
    ev_rebate = 0; // Store the government rebate for the selected EV
    ev_price_after_rebate = 0; // Store the price of the selected EV after rebate
    match_gv_price = 0; // Store price of the equivalent gas vehicle
    ev_fuel_cost = 0; // Store average anual fuel cost of the selected EV
    match_gv_fuel_cost = 0; // Store average anual fuel cost of the equivalent GV
    ev_upkeep_cost = 0; // Store average upkeep cost of the selected EV
    match_gv_upkeep_cost = 0; // Store average upkeep cost of the equivalen GV

    // Parsing out the type and name of the selected EV
    String vehicleType = input_summary[1].split(":")[1].trim();
    String vehicleName = input_summary[4].split(":")[1].trim();

    // Invoke method 'findVehicleByTypeAndName' to retrieve the complete information
    // of the selected EV by its type and name
    String ev_data = findVehicleByTypeAndName(ELECTRICAL_VEHICLE, vehicleType, vehicleName);
    // Invoke method 'findVehicleByTypeAndName' to retrieve the complete information
    // of the equivalent GV by EV' type and name
    String gv_data = findVehicleByTypeAndName(GAS_POWERED_VEHICLE, vehicleType, vehicleName);
    // Parse out the price of the selected EV
    ev_price = Double.parseDouble(ev_data.split(",")[2]);
    // Parse out the price of the equivalent GV
    match_gv_price = Double.parseDouble(gv_data.split(",")[2]);
    // Parse out the rebate for the selected EV
    ev_rebate = Double.parseDouble(ev_data.split(",")[5]);
    // Calculate EV price after rebate
    ev_price_after_rebate = ev_price - ev_rebate;

    // Parse out the fuel cost per 100km for the selected EV
    double evFuelCostPer100km = Double.parseDouble(ev_data.split(",")[6]);
    // Parse out the fuel cost per 100km for the equivalent GV
    double gvFuelCostPer100km = Double.parseDouble(gv_data.split(",")[4]);

    // Calculate the average annual kilometrage by average over the min / max
    // mkilometrages
    double[] minMaxkilometrage = getkilometrageRange();
    double avgkilometrage = (minMaxkilometrage[0] + minMaxkilometrage[1]) / 2;

    // Calculate the average annual fuel cost for the selected EV and kilometrage
    ev_fuel_cost = avgkilometrage * evFuelCostPer100km / 100;
    // Calculate the average annual fuel cost for the equivalent EV and kilometrage
    match_gv_fuel_cost = avgkilometrage * gvFuelCostPer100km / 100;

    // Parse out the battery replacement cost of the selected EV
    double batteryReplCost = Double.parseDouble(ev_data.split(",")[7]);
    // Parse out other upkeep cost per 100km for the selected EV
    double otherEVCostPer100km = avgkilometrage * Double.parseDouble(ev_data.split(",")[8]) / 100;
    // The battery replacement is distributed evenly with the EV Battery warranty,
    // ususally 8 years for now, Or 160,000km. So we calculate whichever comes
    // first. We introduce the concept of 'batterry life year', which is the
    // warranty km divided by anual avgkilometrage (round to the nearest integer). If it is
    // greater than 8, we take EV_BATTERY_WARRANTY_YEAR though.
    int batteryLifeYear = (int) Math.round(EV_BATTERY_WARRANTY_KM / avgkilometrage);
    if (batteryLifeYear > 8)
      batteryLifeYear = EV_BATTERY_WARRANTY_YEAR;

    // Calculate the total anual EV upkeep cost.
    ev_upkeep_cost = batteryReplCost / batteryLifeYear + otherEVCostPer100km;

    // Parse out the upkeep base rate of the equivalent GV
    double gvUpkeepBase = Double.parseDouble(gv_data.split(",")[5]);
    // Invoke 'getUpkeepCost' method recursively to calculate the total upkeep cost
    // in 'batteryLifeYear' year used in evaluate corresponding EV upkeep cost
    double todal_upkeep_cost = getUpkeepCost(batteryLifeYear, gvUpkeepBase, avgkilometrage / 100);
    // Average out the total GV upkeep cost over 8 years to get estimated anual GV
    // upkeep cost
    match_gv_upkeep_cost = todal_upkeep_cost / batteryLifeYear;
  }

  /**
   *
   * This method is to caculate the upkeep cost of a gas powered vehicle
   * recusively over a number of years number of years specfied by parameterd 'numYears'.
   *
   * @param numYears     total number of years the upkeep cost is calculated
   * @param baseCostRate The baseCost of the first year
   * @param kilometrage  The anual kilometerage
   * 
   * @return double the total cost in the number of years specified by 'numberOfYears'
   *
   */
  private static double getUpkeepCost(int numberOfYears, double baseCostRate, double kilometrage) {

    if (numberOfYears == 1) {
      return baseCostRate * kilometrage;
    }
    double result = (baseCostRate + getGVUpkeepCostCoeffByYear(numberOfYears)) * kilometrage +
        getUpkeepCost(numberOfYears - 1, baseCostRate, kilometrage);
    return result;
  }

  /**
   *
   * This method is used to calculate GV upkeep cost coefficient over different
   * years
   *
   * @param numberOfYears: the number of year the vehicles is used
   *
   * @return double the upkeep coefficient for the specific year after the GV is used.
   *
   */
  public static double getGVUpkeepCostCoeffByYear(int numberOfYears) {

    double coefficient = 0;
    if (numberOfYears <= 1)
      coefficient = 0.0;
    else if (numberOfYears <= 3 && numberOfYears > 1)
      coefficient = 2.7;
    else if (numberOfYears > 3 && numberOfYears <= 5)
      coefficient = 4.6;
    else if (numberOfYears > 5 && numberOfYears <= 7)
      coefficient = 7.9;
    else if (numberOfYears > 7 && numberOfYears <= 8)
      coefficient = 11.9;
    else
      coefficient = 5.0;

    return coefficient;
  }

  /**
   *
   * This method is used to display cost summary report.
   *
   */
  public static void displayCostSummary() {

    // Display report header
    System.out.println("");
    System.out.println("\033[1m\033[96m************************ Cost summary ************************\033[0m");
    System.out.println("");

    // Display EV vs GV 'Purchase Price' section
    System.out.println("\033[3m\033[96m 1.Purchase price\033[0m");
    sleep(100);
    System.out.println("");

    System.out.println("\tThe price of your selected EV is: " + getCurrencyFormattedNum(ev_price, 0));
    sleep(100);
    System.out
        .println("\tYou are entitled to get " + getCurrencyFormattedNum(ev_rebate, 0) + " rebate from government.");
    sleep(100);
    System.out.println("\tYour actual payment is " + getCurrencyFormattedNum(ev_price_after_rebate, 0));
    sleep(100);
    System.out.println("\tAn equivlent gas-powered vwhicle is around " + getCurrencyFormattedNum(match_gv_price, 0));
    sleep(100);
    System.out.println("");

    // Display EV vs GV 'Fuel Cost' section
    System.out.println("\033[3m\033[96m 2.Fuel Costs\033[0m");
    System.out.println("");

    System.out.println("\tYour EV vehicle: around " + getCurrencyFormattedNum(ev_fuel_cost, 0) + "/year.");
    System.out.println("\tA equivalent gas power vehicle: around " +
        getCurrencyFormattedNum(match_gv_fuel_cost, 0) + "/year");
    System.out.println("");

    // Display EV vs GV 'Upkeep Cost' section
    System.out.println("\033[3m\033[96m 3.Upkeep Costs\033[0m");
    System.out.println("");

    System.out.println("\tYour EV vehicle: around " + getCurrencyFormattedNum(ev_upkeep_cost, 0) + "/year.");
    System.out.println("\tA equivalent gas power vehicle around " +
        getCurrencyFormattedNum(match_gv_upkeep_cost, 0) + "/year");
    System.out.println("");

    System.out.println("\033[1m\033[96m***************************************************************\033[0m");
  }

  /**
   *
   * This method is to wrap java sleep method in try catch block so we don't need
   * to wrtie try..catch with the built-in java sleep everywhere we use it.
   *
   * @param milsecond time (in milsecond) to sleep
   *
   */
  public static void sleep(int milsecond) {
    try {
      Thread.sleep(milsecond);
    } catch (InterruptedException ex) {
    }
  }

  /**
   *
   * This method is used to retrieve the vehicle information based on the vehicle
   * category (GV or EV), vehocle type (sedan, minivan) and add vehicle name as 
   * passed into the method as parameters.
   *
   * @param vehicleCategory This is the category of vehicle such as electrical
   *                        vehicles, gas vehicles
   * @param vehicleType     This is the type of vehicle, such as Sedan, minivan
   * @param vehicleName     This is the name of the vehicle
   *
   * @return String This returns the full information of the vehicle determined by
   *         the category, type and name of the vehicle.
   *
   */
  public static String findVehicleByTypeAndName(String vehicleCategory, String vehicleType, String vehicleName) {

    String matchVeh = "";
    try {

      // Determin appropriate data file to load based on the 'vehicleCategory' passed
      // in.
      String file = "EV_inventory.txt";
      if (vehicleCategory.compareToIgnoreCase(GAS_POWERED_VEHICLE) == 0) {
        file = "GV_inventory.txt";
      }

      // Initialize the Scanner object to read data from file 'menu.txt'
      Scanner fileInput = new Scanner(new File(file));

      // Skip the first line from the datafile as it contains the header infomation
      if (fileInput.hasNextLine()) {
        fileInput.nextLine();
      }

      // Traverse the datafile loaded line by line
      while (fileInput.hasNextLine()) {

        String line = fileInput.nextLine();
        String[] temp = line.split(",");
        // Parse out vehicle type and name from the data line
        String myVehicleType = temp[0];
        String myVehicleName = temp[1].trim();
        // If match found, store the 'line' into var 'matchVeh' to return later, and
        // then break out from the search loop
        if (myVehicleType.compareToIgnoreCase(vehicleType) == 0
            && myVehicleName.compareToIgnoreCase(vehicleName) == 0) {
          matchVeh = line;
          break;
        }
      }

      // Close the scanner to release resource
      fileInput.close();

    } catch (IOException ex) {
      // Catch exception if data file cannot be loaded and print
      // out friendly message to the users
      System.out.println("The file 'EV_inventory.txt' is not found.");
    }

    // Return the retrived information of the matching vehicle
    return matchVeh;
  }

  /**
   *
   * This method is used to record the users' purchase preference between EV
   * and GV, together with the infromation of the vehicle they are interested in.
   *
   * @param input The Scanner passed in for collect user input
   * 
   */
  public static void recordOrderPreference(Scanner input) {

    try {

      // Display prompt to ask the users to enter their purchase preference between
      // electrical and gas vehicles
      System.out.println("Do you want to buy an Electrical vehicle or a Gas Powered vehicle?");
      System.out.print("Enter 1 for Electrical vehicle, 2 for Gas Powered vehicle: ");
      int decisionSelection = Integer.parseInt(input.next());

      // Determine if the user is interested in EV or GV based on their input
      boolean isEVPrefered = decisionSelection == 1;

      // Initialize a FileWrite object for writing contents to 'order_preferences.txt'
      FileWriter fw = new FileWriter("order_preferences.txt", true);

      // Construct the string representation of the user's inputs
      String inputStr = "";
      for (int i = 1; i < input_summary.length; i++) {
        inputStr += input_summary[i].substring(input_summary[i].indexOf("-") + 1).trim() + ";";
      }
      inputStr += isEVPrefered + "\n";

      // Write to datafile the users inputs together with their purchase preference
      fw.write(inputStr);

      // If code execution reaches here, the write-to file operation are successfully
      // completed, notify the users the status
      System.out.println("Thank you for your information!");
      System.out.println("The information has been sucessfully recorded!");
      System.out.println();
      System.out.println("The data may be used by government or non-profit agencies " +
          "to track\ncarbon emit control potential from the automobile" +
          "sector, and review\nrelevant incentive or rebate policy.");
      System.out.println();
      fw.close();
    } catch (IOException ex) {
      // IO exception is caught in writing data to 'order_preferences.txt' file
      // Display error message to the users
      System.out.println("Error: " + ex.getMessage());
    }
  }

  /**
   *
   * This method is used format a double number to US currency format.
   *
   * @param num          the double num to be formatted
   * @param decimalPoint number of decimal points to keep
   *
   * @return String the us currency value representation of the double number 'num'.
   *
   */
  public static String getCurrencyFormattedNum(double num, int decimalPoint) {

    NumberFormat priceFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
    priceFormat.setMaximumFractionDigits(decimalPoint);
    String formattedPrice = priceFormat.format(num);
    return formattedPrice;
  }

  /**
   *
   * This method is to clear console contents.
   *
   */
  public static void clearScreen() {
    // Clear console contents
    System.out.print("\033[H\033[2J");
    System.out.flush();

    // Rediplay applicaion description block
    displayMenu("MENU_APPLICATION_LOAD");
  }
}