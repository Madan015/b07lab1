import java.io.*;
import java.util.*;

public class Polynomial{
	double [] coefficients;
	int [] exponents;

	public Polynomial(){
		coefficients = new double [1];
		exponents = new int [1];
		coefficients[0] = 0;
		exponents[0] = 0;	
	}

	public Polynomial(double [] inputted_coefficients, int [] inputted_exponents){
		coefficients = new double [inputted_coefficients.length];
		for (int i = 0; i < inputted_coefficients.length; i++) { 	
			coefficients[i] = inputted_coefficients[i];
		}
		exponents = new int [inputted_exponents.length];
		for (int i = 0; i < inputted_exponents.length; i++) {
			exponents[i] = inputted_exponents[i];
		}
	}
	
	public Polynomial(File f){
		try (Scanner sc = new Scanner(f)){
			String line = sc.nextLine();
			String[] terms = line.split("(?=[+-])");
			int poly_length = terms.length;
			coefficients = new double [poly_length];
			exponents = new int [poly_length];

			for (int i = 0; i < poly_length; i++) {
				String term = terms[i];
				String[] parts = term.split("x");
				if (parts.length == 1) {
					coefficients[i] = Double.parseDouble(parts[0]);
					exponents[i] = 0;
				} else {
					coefficients[i] = Double.parseDouble(parts[0]);
					exponents[i] = Integer.parseInt(parts[1]);
				}
			}
		}catch (FileNotFoundException e){
			System.out.println("File not found");	
		}
	}

	public int get_num_of_terms(Polynomial p) {
		int num_of_terms = coefficients.length;
		for (int i = 0; i < coefficients.length; i++) {
			boolean no_matches = true;
			for (int j = 0; j < p.coefficients.length; j++) {
				if (exponents[i] == p.exponents[j]) {
					no_matches = false;
					break;
				}
			}
			if (no_matches) {
				num_of_terms++;
			}
		}
		return num_of_terms;
	}
	
	public Polynomial remove_zeros(Polynomial p) {
		int num_of_zeros = 0;
		for (int i = 0; i < p.coefficients.length; i++) {
			if (p.coefficients[i] == 0) {
				num_of_zeros++;
			}
		}
		Polynomial removed = new Polynomial();
		if (num_of_zeros == p.coefficients.length){
			removed.coefficients = null;
			removed.exponents = null;
			return removed;
		}
		int new_length = p.coefficients.length - num_of_zeros;
		removed.coefficients = new double [new_length];
		removed.exponents = new int [new_length];
		int count = 0;
		for (int i = 0; i < p.coefficients.length; i++) {
			if (p.coefficients[i] != 0) {
				removed.coefficients[count] = p.coefficients[i];
				removed.exponents[count] = p.exponents[i];
				count++;
			}
		}
		return removed;
	}

	public Polynomial add(Polynomial p) {
		int num_of_terms = get_num_of_terms(p);
		Polynomial added = new Polynomial();
		added.coefficients = new double [num_of_terms];
		added.exponents = new int [num_of_terms];
		int count = 0;
		
		for (int i = 0; i < coefficients.length; i++) {
			added.coefficients[count] = coefficients[i];
			added.exponents[count] = exponents[i];
			count++;
		}

		for (int i = 0; i < p.coefficients.length; i++) {
			boolean match_found = false;
			for (int j = 0; j < coefficients.length; j++) {
				if (p.exponents[i] == added.exponents[j]) {
					added.coefficients[j] += p.coefficients[i];
					match_found = true;
					break;
				}
			}
			if (!match_found) {
				added.coefficients[count] = p.coefficients[i];
				added.exponents[count] = p.exponents[i];
				count++;
			}
		}

		added = remove_zeros(added);
		return added;
	}
	
	
	public double evaluate(double x) {
		if (coefficients == null) {
			return 0;
		}

		double final_value = 0;
		for (int i = 0; i < coefficients.length; i++) {
			final_value += coefficients[i] * Math.pow(x, exponents[i]);
		}
		return final_value;
	}
	
	public boolean hasRoot(double x) {
		if (evaluate(x) == 0) {
			return true;
		}
		return false;
	}

	public Polynomial multiply(Polynomial p) {
		int num_of_terms = coefficients.length * p.coefficients.length;
		Polynomial multiplied = new Polynomial();
		multiplied.coefficients = new double [num_of_terms];
		multiplied.exponents = new int [num_of_terms];
		int count = 0;
		for (int i = 0; i < coefficients.length; i++) {
			for (int j = 0; j < p.coefficients.length; j++) {
				multiplied.coefficients[count] = coefficients[i] * p.coefficients[j];
				multiplied.exponents[count] = exponents[i] + p.exponents[j];
				count++;
			}
		}
		multiplied = simplify(multiplied);
		return multiplied;
	}
	
	public int get_unique_exponents(Polynomial p){
		int unique_exponents = 0;
		for (int i = 0; i < p.coefficients.length; i++) {
			boolean no_matches = true;
			for (int j = 0; j < i; j++) {
				if (p.exponents[i] == p.exponents[j]) {
					no_matches = false;
					break;
				}
			}
			if (no_matches) {
				unique_exponents++;
			}
		}
		return unique_exponents;
	}

	public Polynomial simplify(Polynomial p){
		int num_of_terms = get_unique_exponents(p);
		Polynomial simplified = new Polynomial();
		simplified.coefficients = new double [num_of_terms];
		simplified.exponents = new int [num_of_terms];
		int count = 0;

		for (int i = 0; i < p.coefficients.length; i++) {
			boolean match_found = false;
			for (int j = 0; j < count; j++) {
				if (p.exponents[i] == simplified.exponents[j]) {
					simplified.coefficients[j] += p.coefficients[i];
					match_found = true;
					break;
				}
			}
			if (!match_found) {
				simplified.coefficients[count] = p.coefficients[i];
				simplified.exponents[count] = p.exponents[i];
				count++;
			}
		}
		simplified = remove_zeros(simplified);
		return simplified;
	}

	public void saveToFile (String filename){
		if (coefficients == null){
			return;
		}
		String polynomial = coefficients[0] + "x" + exponents[0];
		for (int i = 1; i < coefficients.length; i++) {
			if (coefficients[i]<0){
				polynomial += coefficients[i] + "x" + exponents[i];
			} else{
				polynomial += "+" + coefficients[i] + "x" + exponents[i];
			}
		}

		try (FileWriter output = new FileWriter(filename, false)){
			output.write(polynomial);
		}catch (IOException e){
			System.out.println("Error writing to file");
		}
	}
}
