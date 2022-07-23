package jaxb.test;

import jaxb.model.Department;
import jaxb.model.Employee;
import jaxb.model.Organisation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Task {

    private static final String XML_FILE = "dept-info.xml";

    public static void main(String[] args) {

        Employee emp1 = new Employee("E01", "Tom", null);
        Employee emp2 = new Employee("E02", "Mary", "E01");
        Employee emp3 = new Employee("E03", "John", null);

        Employee emp4 = new Employee("E04", "Allison", null);
        Employee emp5 = new Employee("E05", "Jack", null);

        List<Employee> employeeList = new ArrayList<Employee>();
        employeeList.add(emp1);
        employeeList.add(emp2);
        employeeList.add(emp3);

        List<Employee> employeeList2 = new ArrayList<Employee>();
        employeeList2.add(emp4);
        employeeList2.add(emp5);

        Department dept = new Department("D01", "ACCOUNTING", "NEW YORK");
        List<Department> departmentList = new ArrayList<Department>();
        departmentList.add(dept);

        Department dept2 = new Department("D02", "MANAGER", "LONDON");
        departmentList.add(dept2);

        dept.setEmployees(employeeList);
        dept2.setEmployees(employeeList2);

        Organisation organisation = new Organisation();
        organisation.setName("KRONOS");
        organisation.setDepartments(departmentList);

        try {
            // create JAXB context and instantiate marshaller
            JAXBContext context = JAXBContext.newInstance(Organisation.class);

            // (1) Marshaller : Java Object to XML content.
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(organisation, System.out);

            // Write to File
            File outFile = new File(XML_FILE);
            m.marshal(organisation, outFile);

            System.err.println("Write to file: " + outFile.getAbsolutePath());
            // (2) Unmarshaller : Read XML content to Java Object.
            Unmarshaller um = context.createUnmarshaller();

            // XML file create before.

            Organisation organisationFromFile = (Organisation) um.unmarshal(new FileReader(
                    XML_FILE));
            List<Department> departmentsFromFile = organisationFromFile.getDepartments();
            System.out.println("Organisation: " + organisationFromFile.getName());
            for (Department department : departmentsFromFile) {
                System.out.println("Department: " + department.getDeptName());
                final List<Employee> employeesFromFile = department.getEmployees();
                for (Employee employee : employeesFromFile) {
                    System.out.println("Employee: " + employee.getEmpName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}