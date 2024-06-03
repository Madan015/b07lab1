
public class Driver {
	public static void main(String [] args) {
		double [] p_coefficients = {-1, -2, -3};
		int [] p_exponents = {0, 1, 3};
		Polynomial p = new Polynomial(p_coefficients, p_exponents);
		
		double [] q_coefficients = {1, 2, 4};
		int [] q_exponents = {0, 1, 3};
		Polynomial q = new Polynomial(q_coefficients, q_exponents);	

		print(p);
		print(q);
		System.out.println(p.get_num_of_terms(q));
		Polynomial added = p.add(q);
		print(added);
		Polynomial multiplied = p.multiply(q);
		print(multiplied);
		System.out.println(multiplied.get_unique_exponents(multiplied));
	}

	public static void print(Polynomial p) {
		for (int i = 0; i < p.coefficients.length; i++) {
			System.out.print(p.coefficients[i] + "x^" + p.exponents[i] + " ");
		}
		System.out.println();
	}
}