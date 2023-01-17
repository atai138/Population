/**
 *	City data - the city name, state name, location designation,
 *				and population est. 2017
 *
 *	@author	Alan Tai
 *	@since	January 10, 2023
 */
public class City implements Comparable<City> {
	
	private String name, state, designation;
	private int population;
	
	// Constructor
	public City(String name, String state, String type, int population) {
		this.name = name;
		this.state = state;
		designation = type;
		this.population = population;
	}
	
	/**	Compare two cities populations
	 *	@param other		the other City to compare
	 *	@return				the following value:
	 *		If populations are different, then returns (this.population - other.population)
	 *		else if states are different, then returns (this.state - other.state)
	 *		else returns (this.name - other.name)
	 */
	public int compareTo(City other) {
		if (population != other.population)
			return population - other.population;
		else if (!state.equals(other.state))
			return state.compareTo(other.state);
		else
			return name.compareTo(other.name);
	}
	
	/**	Equal city name and state name
	 *	@param other		the other City to compare
	 *	@return				true if city name and state name equal; false otherwise
	 */
	 public boolean equals(City other) {
		return other.state.equals(state) && other.name.equals(name);
	 }
	
	/**
	 * Accessor method to get city name
	 * @return  city name 
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Accessor method to get state
	 * @return  state name
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Accessor method to get city type
	 * @return  city designation/type
	 */
	public String getDesignation() {
		return designation;
	}
	
	/**
	 * Accessor method to get city population
	 * @return  city population
	 */
	public int getPopulation() {
		return population;
	}
	
	/**	toString */
	@Override
	public String toString() {
		return String.format("%-22s %-22s %-12s %,12d", state, name, designation,
						population);
	}
}
