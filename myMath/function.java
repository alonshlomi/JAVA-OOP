package myMath;
/** This interface represents a simple function of type y=f(x), where both y and x are real numbers.
**/
public interface function {
	
	/**
	 * Compute the value of x at this function.
	 * @param x a real number.
	 * @return f(x) a real number.
	 */
	public double f(double x);
}
