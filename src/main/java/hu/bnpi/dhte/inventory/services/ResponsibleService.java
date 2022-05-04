package hu.bnpi.dhte.inventory.services;

import hu.bnpi.dhte.inventory.entities.responsibility.Employee;
import hu.bnpi.dhte.inventory.repositories.ResponsibleDao;

public class ResponsibleService {

    private ResponsibleDao responsibleDao;

    public ResponsibleService(ResponsibleDao responsibleDao) {
        this.responsibleDao = responsibleDao;
    }

    public String saveEmployee(Employee employee) {
        if (responsibleDao.findEmployeeByEmail(employee.getEmail()).isPresent()) {
            return "There is already an employee with name " + employee.getName() + "in the database!";
        }
        if (responsibleDao.saveEmployee(employee).isEmpty()) {
            return "Unable to save employee to the database!";
        }
        return "Employee " + employee.getName() + " saved successfully.";
    }
}
