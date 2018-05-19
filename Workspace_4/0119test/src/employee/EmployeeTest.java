package employee;
import java.util.*;
public class EmployeeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Employee[] staff = new Employee[3];
		staff[0] = new Employee("Andy", 75000, 1994, 12, 5);
		staff[1] = new Employee("Bob", 55000, 1995, 10, 6);
		staff[2] = new Employee("Candy", 40000, 1996, 5, 12);
		for (int e = 0 ; e < staff.length ; e++)
			staff[e].raiseSalary(5);
		for (int e = 0 ; e < staff.length ; e++)
			System.out.println("name="+ staff[e].getName() + " ,salary=" + staff[e].getSalary() + " ,hireDay=" + staff[e].getHireDay());
		}
	}
class Employee{
	private String name;
	private double salary;
	private Date hireDay;
	public Employee(String n, double s, int y, int m, int d){
		name = n;
		salary = s;
		GregorianCalendar calendar = new GregorianCalendar(y, m-1, d);
		hireDay = calendar.getTime();
	}
	public void raiseSalary(double byPrecent){
		double raise = salary*byPrecent/100;
		salary += raise;
	}
	public String getName(){
		return name;
	}
	public double getSalary(){
		return salary;
	}
	public Date getHireDay(){
		return hireDay;
	}
}

