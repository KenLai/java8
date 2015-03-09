package org.lambda;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.ListUtils;
import org.junit.Test;
import org.lambda.model.Employee;
import org.lambda.model.EmployeeBuilder;

/**
 * @author Ken Lai
 */
public class Java8Test {

	// final int LIST_SIZE = 10000000;
	final int LIST_SIZE = 10;

	@Test
	public void availableProcessors() {
		System.out.println("Available processors: "
				+ Runtime.getRuntime().availableProcessors());
	}

	private List<String> createListOfIntStrings(int size) {
		List<String> strings = new ArrayList<String>();

		for (int i = 0; i < size; i++) {
			strings.add("" + i);
		}

		return strings;
	}

	@Test
	public void testWithRegularLoop() throws Exception {

		List<String> strings = createListOfIntStrings(LIST_SIZE);
		long sum = 0;

		long begin = System.currentTimeMillis();
		for (String aString : strings) {
			int converted = Integer.parseInt(aString);

			if (converted % 2 == 0) {
				sum += converted;
			}
		}

		long duration = System.currentTimeMillis() - begin;

		System.err.println("Regular loop: " + sum + "  " + duration + "ms");
		System.out.println("\n");
	}

	@Test
	public void testWithLambda() throws Exception {

		List<String> strings = createListOfIntStrings(LIST_SIZE);

		long begin = System.currentTimeMillis();

		long sum = strings.parallelStream()//
				.map(s -> Long.parseLong(s))//
				.filter(l -> l % 2l == 0)// filter out the odd values
				// .reduce(0l, (l, l2) -> l + l2)
				.reduce(0l, Long::sum);

		long duration = System.currentTimeMillis() - begin;

		System.err.println("Using Lambda: " + sum + "  " + duration + "ms");
		System.out.println("\n");
	}

	@Test
	public void testSyntax() throws Exception {

		Callable<String> c = () -> "value";
		PrivilegedAction<String> pa = () -> "done";

		Object o = (Callable<String>) () -> "value";

		// Example 2: Turn everything in string array to upper case
		List<String> stringList = Arrays.asList("a", "b", "c");
		List<String> stringListUpper = new ArrayList<>();

		for (String aString : stringList) {
			stringListUpper.add(aString.toUpperCase());
		}

		// Improvement 1
		// List<String> stringListUpperFromStream = stringList.stream().map(new
		// Function<String, String>() {
		// public String apply(String input) {
		// return input.toUpperCase();
		// }
		// }).collect(Collectors.toList());
		// List<String> stringListUpperFromStream = stringList.stream()
		// .map(s -> s.toUpperCase() + "").collect(Collectors.<String>toList());
		Stream<String> stream = stringList.stream();

		List<String> stringListUpperFromStream = stream.map(
				s -> s.toUpperCase()).collect(
				Collectors.toCollection(new Supplier<List<String>>() {
					@Override
					public List<String> get() {
						return new ArrayList<String>();
					}
				}));

		System.out.println("Improvement 1:" + stringList);
		System.out.println("\n");
	}

	@Test
	public void testArrayComparison() {
		// Example 1: Sorting an array ignoring case
		String[] stringArray = { "a", "b", "c" };

		new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareToIgnoreCase(s2);
			}
		};

		Arrays.sort(stringArray, new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareToIgnoreCase(s2);
			}
		});

		Arrays.sort(stringArray,
				(String s1, String s2) -> s1.compareToIgnoreCase(s2));

		Arrays.sort(stringArray, (s1, s2) -> s1.compareToIgnoreCase(s2));

		Arrays.sort(stringArray, String::compareToIgnoreCase);

		System.out.println("testArrayComparison:" + Arrays.asList(stringArray));
		System.out.println("\n");
	}

	@Test
	public void testStringListComparison() {
		// Example: Sorting on List of String's ignoring case
		List<String> stringList = Arrays.asList("a", "b", "c");
		Collections.sort(stringList, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.compareToIgnoreCase(s2);
			}
		});

		Collections.sort(stringList,
				(String s1, String s2) -> s1.compareToIgnoreCase(s2));

		Collections.sort(stringList, (s1, s2) -> s1.compareToIgnoreCase(s2));

		Collections.sort(stringList, String::compareToIgnoreCase);

		stringList.sort((s1, s2) -> s1.compareToIgnoreCase(s2));

		stringList.sort(String::compareToIgnoreCase);

		System.out.println("testStringListComparison:" + stringList);
		System.out.println("\n");
	}

	@Test
	public void testObjectListComparison() {
		// Example: Sorting on List of Students by last name
		List<Employee> employeeList = generateEmployeeList();
		Collections.sort(employeeList, new Comparator<Employee>() {
			@Override
			public int compare(Employee e1, Employee e2) {
				return e1.getFirstName().compareTo(e2.getFirstName());
			}
		});

		Collections.sort(employeeList, (Employee e1, Employee e2) -> e1
				.getFirstName().compareTo(e2.getFirstName()));

		Collections.sort(employeeList,
				(e1, e2) -> e1.getFirstName().compareTo(e2.getFirstName()));

		employeeList.sort((e1, e2) -> e1.getFirstName().compareTo(
				e2.getFirstName()));

		employeeList.sort(Employee::compareByFirstName);

		Collections.sort(employeeList, Employee::compareByFirstName);

		Function<String, Integer> getLengthFunction = s -> s.length();

		Runnable r = () -> {
			System.out.print("Run!");
		};

		Comparator<String> c = (s1, s2) -> s1.compareToIgnoreCase(s2);

		new Thread(new Runnable() {
			public void run() {
				System.out.print("Run!");
			};
		}).start();

		System.out.println("testObjectListComparison:" + employeeList);
		System.out.println("\n");

	}

	/**
	 * *************************************************************************
	 * ******
	 */
	/**
	 * *************************************************************************
	 * ******
	 */
	/**
	 * *************************************************************************
	 * ******
	 */
	/**
	 * *************************************************************************
	 * ******
	 */
	private List<Employee> generateEmployeeList() {
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(EmployeeBuilder.employee().withFirstName("John")
				.withLastName("Director").withPersonnelNumber(1000000)
				.withYearsOfExperience(12).withTitle(Employee.Title.DIRECTOR)
				.build());
		employeeList.add(EmployeeBuilder.employee().withFirstName("Peter")
				.withLastName("Pan").withPersonnelNumber(1000200)
				.withYearsOfExperience(8)
				.withTitle(Employee.Title.SENIOR_ENGINEER).build());
		employeeList.add(EmployeeBuilder.employee().withFirstName("Megan")
				.withLastName("More").withPersonnelNumber(1003200)
				.withYearsOfExperience(7)
				.withTitle(Employee.Title.SENIOR_ENGINEER).build());

		employeeList.add(EmployeeBuilder.employee().withFirstName("David")
				.withLastName("Beckham").withPersonnelNumber(1043200)
				.withYearsOfExperience(2).withTitle(Employee.Title.ENGINEER)
				.build());

		employeeList.add(EmployeeBuilder.employee().withFirstName("Charlie")
				.withLastName("Sheen").withPersonnelNumber(1043700)
				.withYearsOfExperience(1).withTitle(Employee.Title.ENGINEER)
				.build());
		employeeList.add(EmployeeBuilder.employee().withFirstName("Denzel")
				.withLastName("Washington").withPersonnelNumber(1083800)
				.withYearsOfExperience(1).withTitle(Employee.Title.ENGINEER)
				.build());
		return employeeList;
	}

	@Test
	public void testSortEmployeesByFirstName() {
		// Example: Sorting on List of employee by last name
		List<Employee> employeeList = generateEmployeeList();
		Collections.sort(employeeList, new Comparator<Employee>() {
			@Override
			public int compare(Employee e1, Employee e2) {
				return e1.getFirstName().compareTo(e2.getFirstName());
			}
		});

		Collections.sort(employeeList, (Employee e1, Employee e2) -> e1
				.getFirstName().compareTo(e2.getFirstName()));

		Collections.sort(employeeList,
				(e1, e2) -> e1.getFirstName().compareTo(e2.getFirstName()));

		Collections.sort(employeeList, Employee::compareByFirstName);

		employeeList.parallelStream().sorted(
				(e1, e2) -> e1.getFirstName().compareTo(e2.getFirstName()));

		employeeList.sort(Employee::compareByFirstName);

		System.out.println("testSortEmployeesByFirstName:");
		employeeList.stream().forEach(
				(employee) -> System.out.println("firstName:"
						+ employee.getFirstName() + " lastName:"
						+ employee.getLastName()));
		System.out.println("\n");

		Employee anEmployee = new Employee();
		employeeList.sort(anEmployee::compareByLastName);
		System.out.println("testSortEmployeesByLastName:");
		employeeList.stream().forEach(
				(employee) -> System.out.println("Details:" + employee));
		System.out.println("Call count on anEmployee:" + anEmployee);

		System.out.println("\n");
	}

	@Test
	public void testFilterToDirectorsOnly() {
		// Example: Get only list of Directors
		List<Employee> employeeList = generateEmployeeList();

		List<Employee> directors = employeeList
				.stream()
				.filter(employee -> employee.getTitle() == Employee.Title.DIRECTOR)
				.collect(Collectors.toList());

		System.out.println("testFilterToDirectorsOnly:" + directors);
		System.out.println("\n");
	}

	@Test
	public void testReduceToTotalYearsOfExperience() {
		// Example: Get total years of experience of all the Employees in list
		List<Employee> employeeList = generateEmployeeList();

		// int totalYearsOfExperience =
		// employeeList
		// .stream()
		// //.mapToInt(employee -> employee.getYearsOfExperience())
		// .mapToInt(Employee::getYearsOfExperience)
		// //.reduce(0, Integer::sum);
		// .sum();
		int totalYearsOfExperience = employeeList.stream()
				.mapToInt(Employee::getYearsOfExperience).sum();

		System.out.println("testReduceToTotalYearsOfExperience:"
				+ totalYearsOfExperience);
		System.out.println("\n");
	}

	@Test
	public void testPrintCountByTitle() {
		// Example: Print the count of people by title
		List<Employee> employeeList = generateEmployeeList();

		Map<Employee.Title, List<Employee>> employeeListByTitle = employeeList
				.parallelStream().collect(
						Collectors.groupingBy(Employee::getTitle));

		employeeListByTitle.forEach((k, v) -> System.out.println("Title:" + k
				+ " Count:" + v.size()));

		System.out.println("\n");
	}

	@Test
	public void testPrintCountByTPre18() {
		// Example: Print the count of people by title
		List<Employee> employeeList = generateEmployeeList();

		Map<Employee.Title, List<Employee>> employeeListByTitle = new HashMap<Employee.Title, List<Employee>>();
		for (Employee employee : employeeList) {
			List<Employee> employeesByTitle = employeeListByTitle.get(employee
					.getTitle());
			if (employeesByTitle == null) {
				employeesByTitle = new ArrayList<Employee>();
				employeeListByTitle.put(employee.getTitle(), employeesByTitle);
			}
			employeesByTitle.add(employee);
		}

		System.out.println("testPrintCountByTitlePre18:");

		employeeListByTitle.forEach((k, v) -> System.out.println("Title:" + k
				+ " Count:" + v.size()));

		System.out.println("\n");
	}

	@Test
	public void testConstructorReference() throws Exception {

		List<String> stringIntList = Arrays.asList("1", "2", "3");

		List<Integer> intList = stringIntList.stream().map(Integer::new)
				.collect(Collectors.toList());

		stringIntList.stream().mapToInt(Integer::new).sum();

		System.out.println("testConstructorReference:" + intList);
	}

	@Test
	public void testThisKeyword() {
		class MyClass {

			String s = "MyClass";

			void someMethod() {
			}

		}

		Runnable r = new Runnable() {
			@Override
			public void run() {
				System.out.println("Runnable Inner class' this:" + this);
			}
		};
		r.run();

		Consumer<String> c = s -> {
			System.out.println("Function expression's this:" + this);
		};
		c.accept("test");

	}

	@Test
	public void testEffectiveFinal() {
		String variable = "effectively final?";
		// variable = "not anymore";
		Consumer<String> c = s -> {
			System.out.println("Compile error?:" + variable);
		};

	}

	@Test
	public void testCreateDistinctList() {
		List<String> stringList = Arrays.asList("1", "2", "2", "3", "3", "3");

		List<String> distinctStringList = stringList.stream().distinct()
				.collect(Collectors.toList());

		System.out.println("distinctStringList:" + distinctStringList);
	}

	@Test
	public void testCreateCombinedStringListFromCsv() {
		List<String> stringList = Arrays.asList("1,2,3,4,5", "one,two,three",
				"alpha,beta");

		List<String> combinedStringList = stringList.stream()
				.map((s) -> Arrays.asList(s.split(",")))
				.reduce(Collections.emptyList(), ListUtils::union);

		System.out.println("combinedStringList:" + combinedStringList);
	}

}
