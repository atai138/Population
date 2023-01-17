import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;
/**
 *	Population - Allows user to view data of a list of cities sorted by
 *  population or name. The user can also view cities with a given
 *  name or state.
 *
 *	Requires FileUtils and Prompt classes.
 *
 *	@author	Alan Tai
 *	@since	January 16, 2023
 */
public class Population {
	
	// List of cities
	private List<City> cities;
	// List of state names
	private List<String> states;
	// List of city names
	private List<String> cityNames;
	
	// US data file
	private final String DATA_FILE = "usPopData2017.txt";
	
	private SortMethods sort = new SortMethods();
	private long milliSec = -1;
	
	public static void main(String[] args) {
		Population pop = new Population();
		pop.runner();
	}

	/**
	 *  Handles user input and output.
	 */
	public void runner() {
		printIntroduction();
		readData();
		System.out.println(cities.size() + " cities in database\n");

		// User menu selection
		int selection;
		// State or city name to match
		String match = "";

		do {
			printMenu();
			selection = Prompt.getInt("Enter selection");
			if (selection != 9) {
				if (selection == 1) {
					milliSec = sort.selectionSort(cities, new AscendingPopulation());
					System.out.println("\nFifty least populous cities");
				} else if (selection == 2) {
					milliSec = sort.mergeSort(cities, new DescendingPopulation());
					System.out.println("\nFifty most populous cities");
				} else if (selection == 3) {
					milliSec = sort.insertionSort(cities, new AscendingName());
					System.out.println("\nFifty cities sorted by name");
				} else if (selection == 4) {
					milliSec = sort.mergeSort(cities, new DescendingName());
					System.out.println("\nFifty cities sorted by name descending");
				} else if (selection == 5) {
					System.out.println();
					do {
						match = Prompt.getString("Enter state name (i.e. Alabama)").trim();
						if (!states.contains(match))
							System.out.println("ERROR: " + match + " is not valid");
					} while (!states.contains(match));
					System.out.println("\nFifty most populous cities in " + match);
					sort.mergeSort(cities, new DescendingPopulation());
				} else if (selection == 6) {
					System.out.println();
					do {
						match = Prompt.getString("Enter city name").trim();
						if (!cityNames.contains(match))
							System.out.println("ERROR: " + match + " is not valid");
					} while (!cityNames.contains(match));
					System.out.println("City " + match + " by population");
					sort.mergeSort(cities, new DescendingPopulation());
				}
				System.out.printf("%4s%-23s%-23s%-13s%13s\n", "", "State", "City", "Type", "Population");
				if (selection >= 1 && selection <= 4)
					printFifty();
				else if (selection == 5)
					printFifty(true, match);
				else if (selection == 6)
					printFifty(false, match);
			}
		} while (selection != 9);
		System.out.println("\nThanks for using Population!");
	}
	
	/**
	 * Prints the first fifty cities in the City list as well as the time
	 * it took to sort the list.
	 */
	public void printFifty() {
		for (int i=0; i<50; i++) {
			System.out.printf("%2d: %s\n", i+1, cities.get(i));
		}
		System.out.println("\nElapsed Time " + milliSec + " milliseconds\n");
	}
	
	/**
	 *  Prints the first fifty cities in the City list that match the given
	 *  state or city name.
	 * 
	 *  @param matchState true if matching state, false if matching city name
	 *  @param match      state or city name to match
	 */
	public void printFifty(boolean matchState, String match) {
		int count = 0;
		for (int i=0; i<cities.size() && count < 50; i++) {
			City city = cities.get(i);
			if (matchState && city.getState().equals(match) ||
			        !matchState && city.getName().equals(match)) {
				System.out.printf("%2d: %s\n", count+1, city);
				count++;
			}
		}
		System.out.println();
	}

	/**	Prints the introduction to Population */
	public void printIntroduction() {
		System.out.println("   ___                  _       _   _");
		System.out.println("  / _ \\___  _ __  _   _| | __ _| |_(_) ___  _ __ ");
		System.out.println(" / /_)/ _ \\| '_ \\| | | | |/ _` | __| |/ _ \\| '_ \\ ");
		System.out.println("/ ___/ (_) | |_) | |_| | | (_| | |_| | (_) | | | |");
		System.out.println("\\/    \\___/| .__/ \\__,_|_|\\__,_|\\__|_|\\___/|_| |_|");
		System.out.println("           |_|");
		System.out.println();
	}
	
	/**
	 *  Read the city data and put it in the list.
	 */
	public void readData() {
		Scanner reader = FileUtils.openToRead(DATA_FILE);
		reader.useDelimiter("[\t\n]");
		cities = new ArrayList<City>();
		states = new ArrayList<String>();
		cityNames = new ArrayList<String>();
		while (reader.hasNext()) {
			String state = reader.next();
			String name = reader.next();
			String type = reader.next();
			int pop = Integer.parseInt(reader.next());
			cities.add(new City(name, state, type, pop));
			if (!states.contains(state))
				states.add(state);
			if (!cityNames.contains(name))
				cityNames.add(name);
		}
	}
	
	/**	Print out the choices for population sorting */
	public void printMenu() {
		System.out.println("1. Fifty least populous cities in USA (Selection Sort)");
		System.out.println("2. Fifty most populous cities in USA (Merge Sort)");
		System.out.println("3. First fifty cities sorted by name (Insertion Sort)");
		System.out.println("4. Last fifty cities sorted by name descending (Merge Sort)");
		System.out.println("5. Fifty most populous cities in named state");
		System.out.println("6. All cities matching a name sorted by population");
		System.out.println("9. Quit");
	}
	
	/**
	 *  Comparator to sort City objects by population,
	 *  ascending.
	 */
	class AscendingPopulation implements Comparator<City> {
		/**
		 * Compare two City objects using the City's compareTo method,
		 * which sorts by population.
		 */
		public int compare(City c1, City c2) {
			return c1.compareTo(c2);
		}
	}
	
	/**
	 * Comparator to sort City objects by population,
	 * descending.
	 */
	class DescendingPopulation implements Comparator<City> {
		/**
		 * Compare two City objects using the City's compareTo method,
		 * which sorts by population, but reverse the order so that it
		 * is descending.
		 */
		public int compare(City c1, City c2) {
			return c1.compareTo(c2)*(-1);
		}
	}
	
	/**
	 * Comparator to sort City objects by city name,
	 * ascending.
	 */
	class AscendingName implements Comparator<City> {
		/**
		 * Compare two City objects by comparing the names. If the names
		 * are the same, use the City's compareTo method to compare.
		 */
		public int compare(City c1, City c2) {
			int name = c1.getName().compareTo(c2.getName());
			if (name == 0)
				return c1.compareTo(c2);
			return name;
		}
	}
	
	/**
	 * Comparator to sort City objects by city name,
	 * descending.
	 */
	class DescendingName implements Comparator<City> {
		/**
		 * Compare two City objects by comparing the names, but reverse
		 * the order so that it is descending. If the names are the same,
		 * use the City's compareTo method to compare.
		 */
		public int compare(City c1, City c2) {
			int name = c1.getName().compareTo(c2.getName())*(-1);
			if (name == 0)
				return c1.compareTo(c2);
			return name;
		}
	}
}
