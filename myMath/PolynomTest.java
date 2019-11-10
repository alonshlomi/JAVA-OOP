package myMath;

public class PolynomTest {
	public static void main(String[] args) {
		test1();
		//test2();
	}
	public static void test1() {
		Polynom p = new Polynom(),p1 = new Polynom();
		Polynom equals_check = new Polynom("5x^5+3x^2+1.3x");
		String[] monoms1 = {"3x^2","5x^5","2.3x","-x"};
		String[] monoms2 = {"-x^2","2*x","x","7x^0","1"};
		for(int i=0;i<monoms1.length;i++) {
			Monom m = new Monom(monoms1[i]);
			p.add(m);
		}
		for(int i=0;i<monoms2.length;i++) {
			Monom m = new Monom(monoms2[i]);
			p1.add(m);
		}
		System.out.println("Equals check Polynom: "+equals_check+"\n");
		System.out.println("Polynom p - "+p+" : \n\tf(1) = "+p.f(1)+", isZero: "+p.isZero()+", equals: "+p.equals(equals_check));
		System.out.println("Polynom p1 - "+p1+" : \n\tf(1) = "+p1.f(1)+", isZero: "+p1.isZero()+", equals: "+p1.equals(equals_check));

	}
	public static void test2() {
		Polynom p1 = new Polynom(), p2 =  new Polynom();
		String[] monoms1 = {"2", "-x","-3.2x^2","4","-1.5x^2"};
		String[] monoms2 = {"5", "1.7x","3.2x^2","-3","-1.5x^2"};
		for(int i=0;i<monoms1.length;i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}
		for(int i=0;i<monoms2.length;i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}
		System.out.println("p1: "+p1);
		System.out.println("p2: "+p2);
		p1.add(p2);
		System.out.println("p1+p2: "+p1);
		p1.multiply(p2);
		System.out.println("(p1+p2)*p2: "+p1);
		String s1 = p1.toString();
		Polynom pp = new Polynom("0.1x");
		Polynom pp1 = new Polynom("3.2x-3.1x");
		System.out.println(pp1);
		System.out.println(pp.equals(pp1));
		//		Polynom_able pp1 = Polynom(s1);
//		System.out.println("from string: "+pp1);
	}
}
