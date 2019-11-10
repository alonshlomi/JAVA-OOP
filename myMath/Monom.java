
package myMath;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real
 * number and a is an integer (summed a none negative), see:
 * https://en.wikipedia.org/wiki/Monomial The class implements function and
 * support simple operations as: construction, value at x, derivative, add and
 * multiply.
 * 
 * @author Boaz
 *
 */
public class Monom implements function {
	public static final Monom ZERO = new Monom(0, 0);
	public static final Monom MINUS1 = new Monom(-1, 0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();

	public static Comparator<Monom> getComp() {
		return _Comp;
	}

	public Monom(double a, int b) {
		this.set_coefficient(a);
		this.set_power(b);
		this.set_created(true);
	}

	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
		this.set_created(true);
	}

	//Getters:
	public double get_coefficient() {
		return this._coefficient;
	}

	public int get_power() {
		return this._power;
	}

	public boolean get_created() {
		return _created;
	}

	//
	/**
	 * 
	 * 
	 * this method returns the derivative monom of this.
	 * 
	 * @return
	 */

	public Monom derivative() {
		if (this.get_power() == 0) {
			return getNewZeroMonom();
		}
		return new Monom(this.get_coefficient() * this.get_power(), this.get_power() - 1);
	}

	public double f(double x) {
		double ans = 0;
		double p = this.get_power();
		ans = this.get_coefficient() * Math.pow(x, p);
		return ans;
	}

	public boolean isZero() {
		return Math.abs(this.get_coefficient()) <= EPSILON;
	}

	// ***************** add your code below **********************

	/**
	 * Initialize a new Monom from a given string.
	 * @param s is a string represents a Monom.
	 */
	public Monom(String s) {
		double coe = 0;
		int pow = 0;
		s = s.trim();
		if (s.isBlank()) {
			System.out.println("Invalid");
			set_created(false);
			return;
		}
		if (s.equals("x")) {
			set_coefficient(1);
			set_power(1);
			this.set_created(true);
			return;
		}
		if (s.equals("-x")) {
			set_coefficient(-1);
			set_power(1);
			this.set_created(true);
			return;
		}
		String[] arr = s.split("(\\*x\\^)|(x\\^)|(x)|(\\*x)");
		if (arr.length > 2) {
			System.out.println("Invalid input, cannot init " + s);
			set_created(false);
			return;
		} else if (arr.length == 0) {
			System.out.println("Invalid input, cannot init " + s);
			set_created(false);
			return;
		} else if (arr.length == 1) {
			arr = s.split("(x)|(\\*x)");
			if (arr.length > 1) {
				System.out.println("Invalid input, cannot init " + s);
				set_created(false);
				return;
			} else {
				try {
					coe = Double.parseDouble(arr[0]);
				} catch (Exception e) {
					System.out.println("Invalid input, cannot init " + s);
					set_created(false);
					return;
				}
				if (s.contains("x"))
					pow = 1;
				set_coefficient(coe);
				set_power(pow);
				this.set_created(true);
				return;
			}
		} else {
			if (!s.contains("x^")) {
				System.out.println("Invalid input, cannot init " + s);
				set_created(false);
				return;
			}
			try {
				if (arr[0].equals("-"))
					arr[0] = "-1";
				if (arr[0].isBlank())
					arr[0] = "1";
				coe = Double.parseDouble(arr[0]);
				pow = Integer.parseInt(arr[1]);
			} catch (Exception e) {
				System.out.println("Invalid input, cannot init " + s);
				set_created(false);
				return;
			}
			set_coefficient(coe);
			set_power(pow);
			this.set_created(true);
		}
	}

	/**
	 * Add m to this Monom, only if its power is equal to this Monom's power.
	 * 
	 * @param m Monom
	 */
	public void add(Monom m) {
		if (this.isZero() && m.isZero())
			return;
		if (m.get_power() != this.get_power()) {
			// System.out.println("Cannot add this monom!");
			// return;
			{
				throw new RuntimeException("Cannot add monoms with different powers: " + this + "+" + m);
			}
		}
		this.set_coefficient(this.get_coefficient() + m.get_coefficient());
	}

	/**
	 * Multiply d with this Monom.
	 * 
	 * @param d Monom
	 */
	public void multiply(Monom d) {
		this.set_coefficient(this.get_coefficient() * d.get_coefficient());
		this.set_power(this.get_power() + d.get_power());
	}

	/**
	 * Print the Monom as "ax^b". If the power is 1 it returns "ax". If the power is
	 * 0 it returns "a". And if the coefficient is zeroo it returns "0".
	 */
	public String toString() {
		if(!this.get_created()) return "The Monom isn't created!";
		String ans = this.get_coefficient() + "x^" + this.get_power();
		if (this.isZero())
			return "0";
		if (this.get_power() == 0)
			return get_coefficient() + "";
		if (this.get_power() == 1) {
			if (get_coefficient() == 1)
				return "x";
			if (get_coefficient() == -1)
				return "-x";
			return this.get_coefficient() + "x";
		}
		return ans;
	}
	// you may (always) add other methods.

	/**
	 * Check if m logically equals to this Monom.
	 * @param m Monom.
	 * @return true if and only if m is logically equals to this Monom.
	 */
	public boolean equals(Monom m) {
		if (this.get_coefficient() == 0 && m.get_coefficient() == 0)
			return true;
		return (this.get_coefficient() == m.get_coefficient() && this.get_power() == m.get_power());
	}

	/**
	 * Subtract m from this Monom.
	 * @param m Monom.
	 */
	public void subtract(Monom m) {
		if (this.isZero() && m.isZero())
			return;
		if (m.get_power() != this.get_power()) {
			throw new RuntimeException("Cannot substract monoms with different powers: " + this + "-" + m);
		}
		this.set_coefficient(this.get_coefficient() - m.get_coefficient());
	}

	// ****************** Private Methods and Data *****************

	//Setters:
	private void set_coefficient(double a) {
		this._coefficient = a;
	}

	private void set_power(int p) {
		if (p < 0) {
			throw new RuntimeException("ERR the power of Monom should not be negative, got: " + p);
		}
		this._power = p;
	}

	private void set_created(boolean b) {
		this._created = b;
	}

	//
	private static Monom getNewZeroMonom() {
		return new Monom(ZERO);
	}

	private double _coefficient;
	private int _power;
	private boolean _created; //Boolean variable that indicates if the Monom has created.

}
