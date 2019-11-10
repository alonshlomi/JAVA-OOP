package myMath;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import myMath.Monom;

/**
 * This class represents a Polynom with add, multiply functionality, it also
 * should support the following: 1. Riemann's Integral: / 2. Finding a numerical
 * value between two values (currently support root only f(x)=0). 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able {

	private LinkedList<Monom> poly;
	private static final Comparator<Monom> _Comp = new Monom_Comperator();

	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		poly = new LinkedList<Monom>();
	}

	/**
	 * init a Polynom from a String such as: {"x", "3+1.4X^3-34x",
	 * "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * 
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {
		poly = new LinkedList<Monom>();
		String[] arr = s.split("(?=-)|(\\+)");
		Monom[] marr = new Monom[arr.length];
		for (int i = 0; i < arr.length; i++) {
			Monom m = new Monom(arr[i]);
			marr[i] = m;
			if (!m.get_created()) {
				// System.out.println("Invalid input, cannot init "+arr[i]);
				return;
			}
		}
		for (int i = 0; i < marr.length; i++) {
			add(marr[i]);
		}
	}

	// ****************** Private Methods and Data *****************
	

	@Override
	public double f(double x) {
		double sum = 0;
		for (int i = 0; i < poly.size(); i++) {
			sum += poly.get(i).f(x);
		}
		return sum;
	}

	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> p = p1.iteretor();
		while (p.hasNext()) {
			Monom m = new Monom(p.next());
			this.add(m);
		}
	}

	@Override
	public void add(Monom m1) {
		for (int i = 0; i < this.poly.size(); i++) {
			if (this.poly.get(i).get_power() == m1.get_power()) {
				this.poly.get(i).add(m1);
				return;
			}
		}
		this.poly.add(m1);
		this.poly.sort(_Comp);
	}

	public void subtract(Monom m1) {
		for (int i = 0; i < this.poly.size(); i++) {
			if (this.poly.get(i).get_power() == m1.get_power()) {
				this.poly.get(i).subtract(m1);
				return;
			}
		}
		m1.multiply(Monom.MINUS1);
		this.poly.add(m1);
		this.poly.sort(_Comp);
	}

	@Override
	public void substract(Polynom_able p1) {
		Iterator<Monom> p = p1.iteretor();
		while (p.hasNext()) {
			Monom m = new Monom(p.next());
			this.subtract(m);
		}
	}

	@Override
	public void multiply(Polynom_able p1) {
		Polynom tmp = new Polynom();
		for (int i = 0; i < this.poly.size(); i++) {
			Iterator<Monom> p = p1.iteretor();
			while (p.hasNext()) {
				Monom m = new Monom(p.next());
				Monom m1 = new Monom(this.poly.get(i));
				m1.multiply(m);
				tmp.add(new Monom(m1));
			}
		}
		poly = tmp.poly;
	}

	@Override
	public boolean equals(Polynom_able p1) {
		Polynom_able p = this.copy();
		p.substract(p1);
		return p.isZero();
	}

	@Override
	public boolean isZero() {
		for (int i = 0; i < poly.size(); i++) {
			if (!poly.get(i).isZero())
				return false;
		}
		return true;
	}

	@Override
	public double root(double x0, double x1, double eps) {
		if (this.f(x0) * this.f(x1) > 0) {
			{
				throw new RuntimeException("Invalid input: " + "f(" + x0 + ")*f(" + x1 + ")>0");
			}
		}
		double h = x0;
		while (x1 - x0 >= eps) {
			h = (x0 + x1) / 2.0;
			if (Math.abs(this.f(h)) <= eps)
		//	if(this.f(h) == 0.0)
				return h;
			else if (this.f(h) * this.f(x0) < 0)
				x1 = h;
			else
				x0 = h;
		}
		return h;
	}

	@Override
	public Polynom_able copy() {
		Polynom_able p = new Polynom();
		for (int i = 0; i < poly.size(); i++) {
			p.add(new Monom(poly.get(i)));
		}
		return p;
	}

	@Override
	public Polynom_able derivative() {
		Polynom_able p = new Polynom();
		for (int i = 0; i < poly.size(); i++) {
			p.add(poly.get(i).derivative());
		}
		return p;
	}

	@Override
	public double area(double x0, double x1, double eps) { // ? epsilon
		double d_x = Math.abs((x1 - x0) / eps);
		double x_i, x_i_minus1, approx, sum = 0;
		for (int i = 1; i <= eps; i++) {
			x_i = (x0 + d_x * i);
			x_i_minus1 = (x0 + d_x * (i - 1));
			approx = Math.abs((this.f(x_i) + this.f(x_i_minus1)) / 2);
			sum += approx * d_x;
		}
		return sum;
	}

	@Override
	public Iterator<Monom> iteretor() {
		// TODO Auto-generated method stub
		return this.poly.iterator();
	}

	@Override
	public void multiply(Monom m1) {
		for (int i = 0; i < poly.size(); i++) {
			poly.get(i).multiply(m1);
		}
	}

	public String toString() {
		String ans = "";
		if (this.isZero())
			return "0";
		for (int i = 0; i < poly.size(); i++) {
			if (poly.get(i).isZero())
				continue;
			if (i != 0 && poly.get(i).get_coefficient() >= 0)
				ans += "+";
			ans += poly.get(i) + "";
		}
		return ans;
	}

}
