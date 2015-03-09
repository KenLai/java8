package org.lambda.model;

import java.util.Comparator;

public class EmployeeComparator implements
		Comparator<Employee> {

	@Override
	public int compare(Employee e1, Employee e2) {
		return e1.getFirstName().compareTo(e2.getFirstName());
	}

}
