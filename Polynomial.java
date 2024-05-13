public class Polynomial{
	double [] coefficients;

	public Polynomial(){
		coefficients = new double [1];
		coefficients[0] = 0;
	}

	public Polynomial(double [] inputted_coefficients){
		coefficients = new double [inputted_coefficients.length];
		for (int i = 0; i < inputted_coefficients.length; i++) { 	
			coefficients[i] = inputted_coefficients[i];
		}
	}
	
	public Polynomial add(Polynomial p) {
		double [] coefficients_to_initialize;
		double [] coefficients_to_add;
		
		if (coefficients.length >= p.coefficients.length){
			coefficients_to_initialize = coefficients;
			coefficients_to_add = p.coefficients;
		} else {
			coefficients_to_initialize = p.coefficients;
			coefficients_to_add = coefficients;
		}
		
		Polynomial added = new Polynomial(coefficients_to_initialize);
		
		for (int i = 0; i < coefficients_to_add.length; i++) { 	
			added.coefficients[i] += coefficients_to_add[i];
		}
		return added;
	}
	
	public double evaluate(double x) {
		double final_value = coefficients[0];
		for (int i = 1; i < coefficients.length; i++) {
			final_value += (coefficients[i] * Math.pow(x, i));
		}
		return final_value;
	}
	
	public boolean hasRoot(double x) {
		if (evaluate(x) == 0) {
			return true;
		}
		return false;
	}
}
