package com.myapp.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import com.myapp.beans.Employee;

public class FindAverageSalaryApp {

	/**
	 * Find average salary for each designation by city
	 * 
	 * @param employees
	 */
	public void findAverageSalary(List<Employee> employees) {
		Map<String, ConcurrentMap<String, Double>> result = employees.parallelStream()
				.collect(Collectors.groupingByConcurrent(Employee::getOfficeLocation, Collectors.groupingByConcurrent(
						Employee::getDesignation, Collectors.averagingDouble(Employee::getSalary))));

		result.forEach((officeLocation, designationMap) -> {
			designationMap.forEach((designation, averageSalary) -> System.out
					.println(officeLocation + "  --> " + designation + " --> " + Math.round(averageSalary) + "   "));

		});
	}
}