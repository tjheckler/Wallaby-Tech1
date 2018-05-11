package com.company;

import java.math.BigDecimal;
import java.util.*;

public class FundAllocationToolFixed
{

    Scanner scanner = new Scanner(System.in);
    Map<String, Employee> mapOfEmployees = new TreeMap<>();
    List<InvestmentOptions> buffer = new LinkedList<>();
    final String[] investmentOptions = {"End Of World 2012", "Super Risky Optimists", "Y2K Survivors", "End Of Time 2038"};
    Employee employee;
    BigDecimal min = new BigDecimal("9");
    BigDecimal max = new BigDecimal("201");
    BigDecimal amountAdded = new BigDecimal("0");
    String command;
    Boolean isValid = true;
    String investmentName;
    double percent;
    double totalPercent;
    BigDecimal totalAmount;

    public static void main(String[] args)
    {
        FundAllocationToolFixed fundAllocationTool = new FundAllocationToolFixed();
        fundAllocationTool.run();
    }

    void run()
    {
        System.out.println("Welcome to the 401(k) fund allocation tool. Enter one of the following commands: ");
        printCommands();

        do
        {
            command = scanner.nextLine();
            String[] commands = command.split(" ");
            command = commands[0];

            if (command.equalsIgnoreCase("AddAccount"))
            {
                addAccount();
                buffer.clear();
                totalPercent = 0;
            }

            if (command.equalsIgnoreCase("ViewAccount"))
            {
                if (commands.length == 1)
                {
                    System.out.println("Please enter a valid employee ID.");
                } else
                {
                    printAccount(mapOfEmployees.get(commands[1]), commands[1]);
                }
            }

            if (command.equalsIgnoreCase("ViewAllAccounts"))
            {
                printAllAccounts();
            }

            if (command.equalsIgnoreCase("exit"))
            {
                System.out.println("Thanks for using our 401(k) system, goodbye.");
            }

            if (!command.equalsIgnoreCase("exit"))
            {
                System.out.println("Enter another command");
                printCommands();
            } else
            {
                System.out.println("Invalid command.");
            }
        } while (!command.equalsIgnoreCase("exit"));
    }

    void printCommands()
    {
        System.out.println("AddAccount");
        System.out.println("Viewaccount <employeeID>");
        System.out.println("ViewAllaccounts");
        System.out.println("Exit");
    }

    void addAccount()
    {
        addEmployee();
        System.out.println("Your account has been completed.");
    }

    void printAccount(Employee employee, String id)
    {
        if (mapOfEmployees.size() == 0)
        {
            System.out.println("No accounts have been added yet.");
        } else
        {
            System.out.println("Current account: ");
            System.out.println("Name: " + employee.getName());
            System.out.println("Employee ID: " + id);
            System.out.println("Total amount invested: " + employee.getAmountAdded());

            printInvestments(employee);
        }
    }

    void printAllAccounts()
    {

        for (String id : mapOfEmployees.keySet())
        {
            System.out.println("Name: " + mapOfEmployees.get(id).getName());
            System.out.println("Employee ID: " + id);
            System.out.println("Total amount invested: " + mapOfEmployees.get(id).getAmountAdded());
            printInvestments(mapOfEmployees.get(id));
        }

    }

    void printInvestments(Employee employee)
    {
        if (employee.getEmployeeInvestments().size() == 0)
        {
            System.out.println("This employee has no investments");
        } else
        {
            for (InvestmentOptions investment : employee.getEmployeeInvestments())
            {
                System.out.println("Fund name: " + investment.getName());
                System.out.println("Percent of total fund invested: " + investment.getPercent());
                System.out.println("Amount invested in fund: " + investment.getAmount());
            }
        }
    }

    void addEmployee()
    {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your employee ID: ");
        String employeeId = scanner.nextLine();
        System.out.println("How much would you like to contribute to your 401(k) per month? (min 10, max 200)");

        do
        {
            isValid = true;
            amountAdded = scanner.nextBigDecimal();
            scanner.nextLine();

            if (amountAdded.compareTo(min) <= 0 || amountAdded.compareTo(max) >= 0)
            {
                isValid = false;
                System.out.print("That is not a valid amount. Please enter a value from 10 to 200: ");
            }

        } while (!isValid);

        System.out.println("Adding " + amountAdded + " to " + name + "'s 401(k) every month.");

        do
        {
            buffer.add(addInvestmentOption());
            System.out.println("You have invested " + totalPercent + "% so far, and have " + (100 - totalPercent) + "% left to invest");

        } while (totalPercent != 100);

        Employee employee = new Employee(name, amountAdded, new LinkedList<>(buffer));
        mapOfEmployees.put(employeeId, employee);
    }

    InvestmentOptions addInvestmentOption()
    {
        String planName = getOptionName();
        double percent = 0.0;
        BigDecimal amount = amountAdded;

        percent = getOptionPercent(planName);

        BigDecimal optionAmount = getOptionAmount(amount, percent, planName);
        return new InvestmentOptions(planName, percent, optionAmount);
    }

    String getOptionName()
    {
        int index = 1;
        int choice;
        isValid = true;

        System.out.println("Choose which funds to invest in: ");

        for (String option : investmentOptions)
        {
            System.out.println("Enter " + index + "to invest in " + option);
            index++;
        }
        do
        {
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice > 0 && choice <= investmentOptions.length)
            {
                investmentName = investmentOptions[choice - 1];
                isValid = true;
            } else
            {
                System.out.println("No such investment option exists. Pick another one.");
                isValid = false;
            }
        } while (!isValid);

        return investmentName;
    }

    double getOptionPercent(String optionName)
    {
        double percent;
        System.out.println("How much of your total investment do you want to allocate to " + optionName + "?");

        do
        {
            percent = scanner.nextDouble();
            scanner.nextLine();
            totalPercent += percent;
            if (totalPercent <= 100)
            {
                return percent;
            } else
            {
                totalPercent -= percent;
                System.out.println("You can't pick a percentage greater than 100. Enter another percentage. You have " + (100 - totalPercent) + "% left to invest.");
            }
        } while (true);
    }

    BigDecimal getOptionAmount(BigDecimal amount, double percent, String fund)
    {
        BigDecimal factor = new BigDecimal(percent * .01);
        System.out.println("Percentage: " + percent);
        System.out.println(amount.multiply(factor) + " added to " + fund);
        return amount.multiply(factor);
    }
}
