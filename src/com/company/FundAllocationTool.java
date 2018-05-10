package com.company;

import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.SortedMap;

public class FundAllocationTool
{
    Scanner scanner = new Scanner(System.in);
    List<Employee> listOfEmployees = new LinkedList<>();
    List<InvestmentOptions> buffer = new LinkedList<>();
    final String[] investmentOptions = {"End Of World 2012", "Super Risky Optimists", "Y2K Survivors", "End Of Time 2038"};
    String command;

    public static void main(String[] args)
    {
        FundAllocationTool fundAllocationTool = new FundAllocationTool();
        fundAllocationTool.run();
    }

    void run()
    {
        System.out.println("Welcome to the 401(k) fund allocation tool. Enter your first and last name.");
        String name = scanner.nextLine();

        System.out.println("Welcome, " + name + "! What is your employee ID?");
        String employeeId = scanner.nextLine();

        System.out.println("How much per month would you like to add to your 401(k)? (amount must be between $10 and $200)");
        BigDecimal amountAdded = scanner.nextBigDecimal();
        scanner.nextLine();

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

            if(investmentChoice > 0 && investmentChoice <= investmentOptions.length)
            {
                investmentName = investmentOptions[investmentChoice - 1];

                System.out.println("What percentage would you like to add to " + investmentName + "?");
                double percentage = scanner.nextDouble();
                scanner.nextLine();
                percentTotal += percentage;
                if(percentTotal > 100)
                {
                    System.out.println("Percentage has exceeded 100.");
                }

                BigDecimal investmentAmount = amountAdded.multiply(new BigDecimal(percentage * .01));

                InvestmentOptions investmentOption = new InvestmentOptions(investmentOptions[investmentChoice - 1], percentage, investmentAmount);

                buffer.add(investmentOption);

                System.out.println(investmentOptions[investmentChoice - 1] + " added to you list of investments.");
                System.out.println("You have allocated " + percentTotal + "% of your funds. " + (100 - percentTotal) + "% remains.");
                System.out.println("Enter another investment, or enter -1 to finish.");
            }
            else if(investmentChoice > investmentOptions.length)
            {
                System.out.println("No such investment. Try again.");
            }
            else
            {
                System.out.println("Invalid input. Try again.");
            }

        }while(investmentChoice != -1);

        System.out.println("Printing all investment invformation: ");

        Employee employee = new Employee(name, amountAdded, employeeId, buffer);

        listOfEmployees.add(employee);

        printEmployee(employee);
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
        for(Employee employeee : listOfEmployees)
        {
            System.out.println("Name: " + employeee.getName());
            System.out.println("ID: " + employeee.getEmployeeId());
            System.out.println("Amount: " + employeee.getAmountAdded());
            System.out.println("Investments: ");

            for(InvestmentOptions investment : employee.getEmployeeInvestments())
            {
                System.out.print(investment.getName());
                System.out.print("\t" + investment.getPercent());
                System.out.println("\t" + investment.getAmount());

            }

        }
    }
}
