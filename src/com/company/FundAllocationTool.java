package com.company;

import java.math.RoundingMode;
import java.util.*;
import java.math.BigDecimal;

public class FundAllocationTool
{
    Scanner scanner = new Scanner(System.in);
    List<Employee> listOfEmployees = new LinkedList<>();
    List<InvestmentOptions> buffer = new LinkedList<>();
    List<String> stringBuffer = new LinkedList<>();
    final String[] investmentOptions = {"End Of World 2012", "Super Risky Optimists", "Y2K Survivors", "End Of Time 2038"};
    Employee employee;
    BigDecimal min = new BigDecimal("9");
    BigDecimal max = new BigDecimal("201");
    BigDecimal amountAdded = new BigDecimal("0");

    String command = "";

    public static void main(String[] args)
    {
        FundAllocationTool fundAllocationTool = new FundAllocationTool();
        fundAllocationTool.run();
    }

    void run()
    {
        System.out.println("Welcome to the 401(k) fund allocation tool. Enter one of the following commands: ");
        printCommands();

        do
        {
            do
            {

            command = scanner.nextLine();
            if(command.equalsIgnoreCase("addAccount"))
            {
                System.out.println("Enter your name");
                String name = scanner.nextLine();

                System.out.println("Welcome, " + name + "! What is your employee ID?");
                String employeeId = scanner.nextLine();

                System.out.println("How much per month would you like to add to your 401(k)? (amount must be between $10 and $200)");

                BigDecimal amountAdded;
                BigDecimal employerContribution;

                    amountAdded = scanner.nextBigDecimal();
                    scanner.nextLine();

                    if ((amountAdded.compareTo(min) <= 0) || (amountAdded.compareTo(max) >= 0))
                    {
                        System.out.println("Please enter a value between 10 and 200");
                    }

                    else
                    {
                        if(((amountAdded.compareTo(new BigDecimal("50")) <= 0)))
                        {
                            employerContribution = amountAdded.multiply(new BigDecimal(".5").setScale(2,RoundingMode.HALF_UP));
                            amountAdded = amountAdded.add(employerContribution);
                            System.out.println("Your employee contributed &" + employerContribution);
                            System.out.println("Your total amount to invest is $" + amountAdded);
                        }
                        else
                        {
                            employerContribution = new BigDecimal("50").multiply(new BigDecimal(".5").setScale(2,RoundingMode.HALF_UP));
                            amountAdded = amountAdded.add(employerContribution);
                            System.out.println("Your employee contributed &" + employerContribution);
                            System.out.println("Your total amount to invest is $" + amountAdded);
                        }

                        System.out.println("Which programs would you like to invest in?");
                        printInvestmentOptions();
                        int investmentChoice = 1;
                        String investmentName = "";
                        double percentTotal = 0;
                        BigDecimal investmentTotal = new BigDecimal("0");

                        do
                        {
                            investmentChoice = scanner.nextInt();
                            scanner.nextLine();

                            if (investmentChoice > 0 && investmentChoice <= investmentOptions.length)
                            {
                                investmentName = investmentOptions[investmentChoice - 1];

                                if(stringBuffer.contains(investmentName))
                                {
                                    System.out.println("You are already investing in that fund. Pick another one.");
                                }
                                else
                                {
                                    stringBuffer.add(investmentName);
                                    System.out.println("What percentage would you like to add to " + investmentName + "?");
                                    double percentage = scanner.nextDouble();
                                    scanner.nextLine();
                                    percentTotal += percentage;

                                    if (percentTotal > 100)
                                    {
                                        percentTotal -= percentage;
                                        System.out.println("Percentage has exceed 100%. You have " + (100 - percentTotal) + "% left to invest.");
                                        System.out.println("Enter another investment.");
                                        printInvestmentOptions();
                                    } else
                                    {
                                        BigDecimal investmentAmount = amountAdded.multiply(new BigDecimal(percentage * .01));

                                        InvestmentOptions investmentOption = new InvestmentOptions(investmentOptions[investmentChoice - 1], percentage, investmentAmount);

                                        buffer.add(investmentOption);

                                        System.out.println(investmentOptions[investmentChoice - 1] + " added to you list of investments.");
                                        System.out.println("You have allocated " + percentTotal + "% of your funds. " + (100 - percentTotal) + "% remains.");
                                        System.out.println("Enter another investment, or enter -1 to finish.");
                                        printInvestmentOptions();
                                    }
                                }
                            } else if (investmentChoice > investmentOptions.length)
                            {
                                System.out.println("No such investment. Try again.");
                            } else if (investmentChoice != -1)
                            {
                                System.out.println("Invalid input. Try again.");
                            }

                        } while (investmentChoice != -1 || percentTotal != 100);

                        Employee employee = new Employee(name, amountAdded, buffer);

                        listOfEmployees.add(employee);
                        System.out.println("Enter another command: ");
                        printCommands();
                    }
                }

            if(command.equalsIgnoreCase("viewEmployee"))
            {
                printEmployee(employee);
            }

            if(command.equalsIgnoreCase("viewAllEmployees"))
            {
                printAllEmployees();
            }

            if(!command.equalsIgnoreCase("EXIT"))
                System.out.println("Enter another name to start another account.");
            else
                System.out.println("Thank you for using our system. Goodbye.");
            } while ((amountAdded.compareTo(min) <= 0) || (amountAdded.compareTo(max) >= 0));
        }while(!command.equalsIgnoreCase("EXIT"));

        buffer.clear();
        stringBuffer.clear();
    }

    void printInvestmentOptions()
    {
        System.out.println("Here are your investment options: ");
        int choice = 1;
        for(String option : investmentOptions)
        {
            System.out.println("Enter " + choice + " " +  option);
            choice++;
        }
    }

    void printEmployee(Employee employee)
    {
        System.out.println("Name:     " + employee.getName());
        System.out.println("ID:       " + employee.getEmployeeId());
        System.out.println("Amount:   " + employee.getAmountAdded());
        System.out.println("Investments: ");

        for(InvestmentOptions investment : employee.getEmployeeInvestments())
        {
            System.out.print(investment.getName());
            System.out.print("\t" + investment.getPercent());
            System.out.println("\t" + investment.getAmount());
        }
    }

    void printAllEmployees()
    {
        for(Employee employeee : listOfEmployees)
        {
            System.out.println("Name:     " + employeee.getName());
            System.out.println("ID:       " + employeee.getEmployeeId());
            System.out.println("Amount:   " + employeee.getAmountAdded());
            System.out.println("Investments: ");

            for(InvestmentOptions investment : employee.getEmployeeInvestments())
            {
                System.out.print(investment.getName());
                System.out.print("\t" + investment.getPercent());
                System.out.println("\t" + investment.getAmount());
            }

        }
    }

    void printCommands()
    {
        System.out.println("AddAccount");
        System.out.println("ViewEmployee");
        System.out.println("ViewAllEmployees");
        System.out.println("Exit");
    }

}
