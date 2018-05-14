package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.*;
public class FundAllocationToolFixed
{

    Scanner scanner = new Scanner(System.in);
    Map<String, Employee> mapOfEmployees = new TreeMap<>();
    //creates temporary list of investment options that are added to employee profiles
    List<InvestmentOptions> buffer = new ArrayList<>();
    //temporary list of investment option names used to check if an employee is already invested in that fund
    List<String> stringBuffer = new ArrayList<>();
    List<InvestmentFund> investmentFunds = new ArrayList<>();
    final String[] investmentOptions = {"End Of World 2012", "Super Risky Optimists", "Y2K Survivors", "End Of Time 2038"};
    Employee employee;
    BigDecimal min = new BigDecimal("9");
    BigDecimal max = new BigDecimal("201");
    BigDecimal amountAdded = new BigDecimal("0").setScale(2,RoundingMode.HALF_UP);
    String command;
    Boolean isValid = true;
    String investmentName;
    double percent;
    double totalPercent;
    BigDecimal totalAmount;
    BigDecimal employerContribution;
    LocalTime startTime;
    LocalTime endTime;

    //Create fund objects
    InvestmentFund endOfWorld2012 = new InvestmentFund("End Of World 2012");
    InvestmentFund superRiskyOptimists = new InvestmentFund("Super Risky Optimists");
    InvestmentFund Y2KSurvivors = new InvestmentFund("Y2K Survivors");
    InvestmentFund endOfTime2038 = new InvestmentFund("End Of Time 2038");

    public static void main(String[] args)
    {
        FundAllocationToolFixed fundAllocationTool = new FundAllocationToolFixed();
        fundAllocationTool.run();
    }

    void run()
    {

        //initialize investment fund list
        investmentFunds.add(endOfWorld2012);
        investmentFunds.add(superRiskyOptimists);
        investmentFunds.add(Y2KSurvivors);
        investmentFunds.add(endOfTime2038);

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
                stringBuffer.clear();
                totalPercent = 0;
            }

            else if (command.equalsIgnoreCase("ViewAccount"))
            {
                if (commands.length == 1)
                {
                    System.out.println("Please enter a valid employee ID.");
                }

                else
                {
                    printAccount(mapOfEmployees.get(commands[1]), commands[1]);
                }
            }

            else if (command.equalsIgnoreCase("ViewAllAccounts"))
            {
                printAllAccounts();
            }

            else if (command.equalsIgnoreCase("exit"))
            {
                System.out.println("Thanks for using our 401(k) system, goodbye.");
            }

            else if(command.equalsIgnoreCase("viewFundInformation"))
            {
                printInvestmentFunds();
            }

            else if(command.equalsIgnoreCase("ViewTimeSpent"))
            {
                if (commands.length == 1)
                {
                    System.out.println("Please enter a valid employee ID.");
                }

                else if(mapOfEmployees.containsKey(commands[1]))
                {
                    System.out.println(mapOfEmployees.get(commands[1]).getTotalTime());
                }

                else
                {
                    System.out.println("Please enter a valid id.");
                }
            }

            else
            {
                System.out.println("Invalid command.");
            }

            if (!command.equalsIgnoreCase("exit"))
            {
                System.out.println("Enter another command");
                printCommands();
            }
        } while (!command.equalsIgnoreCase("exit"));
    }

    void printCommands()
    {
        System.out.println();
        System.out.println("AddAccount");
        System.out.println("Viewaccount <employeeID>");
        System.out.println("ViewAllaccounts");
        System.out.println("ViewFundInformation");
        System.out.println("ViewTimeSpent <employeeID>");
        System.out.println("Exit");
        System.out.println();
    }

    void addAccount()
    {
        addEmployee();
        System.out.println("Your account has been completed.");
    }

    //prints an individual account
    void printAccount(Employee employee, String id)
    {
        if (mapOfEmployees.size() == 0)
        {
            System.out.println("No accounts have been added yet.");
        } else
        {
            System.out.println("Current account: ");
            System.out.println("Name:                  " + employee.getName());
            System.out.println("Employee ID:           " + id);
            System.out.println("Total amount invested: $" + employee.getAmountAdded());

            printInvestments(employee);
        }
    }

    //prints information in all accounts
    void printAllAccounts()
    {
        for (String id : mapOfEmployees.keySet())
        {
            System.out.println();

            System.out.println("Name:                   " + mapOfEmployees.get(id).getName());
            System.out.println("Employee ID:            " + id);
            System.out.println("Total amount invested: $" + mapOfEmployees.get(id).getAmountAdded());
            printInvestments(mapOfEmployees.get(id));
        }
        System.out.println();

    }

    //prints information by employee
    void printInvestments(Employee employee)
    {
        if (employee.getEmployeeInvestments().size() == 0)
        {
            System.out.println();
            System.out.println("This employee has no investments");
            System.out.println();
        }

        else
        {
            System.out.println();
            for (InvestmentOptions investment : employee.getEmployeeInvestments())
            {
                System.out.println("Fund name:                      " + investment.getName());
                System.out.println("Percent of total fund invested: " + investment.getPercent() + "%");
                System.out.println("Amount invested in fund:        $" + investment.getAmount());
            }
            System.out.println();
        }
    }

    //prints information by fund
    void printInvestmentFunds()
    {
        System.out.println();
        System.out.println("Fund details: ");
        System.out.println();
        for(InvestmentFund investmentFund : investmentFunds)
        {
            System.out.println("Investment fund name:              " + investmentFund.getName());
            System.out.println("Number of employees enrolled:      " + investmentFund.getNumOfEmployeesInFund());
            System.out.println("Total amount invested in the fund: " + investmentFund.getAmountInFund());
            System.out.println();
        }
        System.out.println();
    }

    //collects all information needed to add an account, and creates the account
    void addEmployee()
    {

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        Employee employee = new Employee(name);
        employee.setStartTime(LocalTime.now());
        System.out.print("Enter your employee ID: ");
        String employeeId;

        do
        {
            employeeId = scanner.nextLine();
            if(isAlreadyEntered(employeeId))
            {
                System.out.println("That's " + mapOfEmployees.get(employeeId).getName() + "'s account. Enter a different employee ID");

                isValid = false;
            }
            else
                isValid = true;
        }while(!isValid);

        System.out.println("How much would you like to contribute to your 401(k) per month? (min 10, max 200)");
        do
        {
            isValid = true;
            amountAdded = scanner.nextBigDecimal().setScale(2,RoundingMode.HALF_UP);
            scanner.nextLine();

            if (amountAdded.compareTo(min) <= 0 || amountAdded.compareTo(max) >= 0)
            {
                isValid = false;
                System.out.print("That is not a valid amount. Please enter a value from 10 to 200: ");
            }

            else if(((amountAdded.compareTo(new BigDecimal("50")) <= 0)))
            {
                employerContribution = amountAdded.multiply(new BigDecimal(".5").setScale(2,RoundingMode.HALF_UP));
                amountAdded = amountAdded.add(employerContribution);
                System.out.println("Your employer contributed &" + employerContribution);
                System.out.println("Your total amount to invest is $" + amountAdded);
            }
            else
            {
                employerContribution = new BigDecimal("50").multiply(new BigDecimal(".5").setScale(2,RoundingMode.HALF_UP));
                amountAdded = amountAdded.add(employerContribution);
                System.out.println("Your employer contributed &" + employerContribution);
                System.out.println("Your total amount to invest is $" + amountAdded);
            }

        } while (!isValid);

        System.out.println("Adding " + amountAdded + " to " + name + "'s 401(k) every month.");

        do
        {
            buffer.add(addInvestmentOption());
            System.out.println("You have invested " + totalPercent + "% so far, and have " + (100 - totalPercent) + "% left to invest");

        } while (totalPercent != 100);

        employee.setAmountAdded(amountAdded);
        employee.setEmployeeInvestments(new ArrayList<>(buffer));
        mapOfEmployees.put(employeeId, employee);
        employee.setEndTime(LocalTime.now());
    }

    //determines if an employee ID is already in the system
    private boolean isAlreadyEntered(String id)
    {
        if(mapOfEmployees.containsKey(id))
        {
            return true;
        }
        return false;
    }

    //adds an investment profile to an employee profile
    InvestmentOptions addInvestmentOption()
    {
        String planName = getOptionName();
        double percent = 0.0;
        BigDecimal amount = amountAdded;

        percent = getOptionPercent(planName);

        BigDecimal optionAmount = (getOptionAmount(amount, percent, planName)).setScale(2,RoundingMode.HALF_UP);
        if(planName.equals("End Of World 2012"))
        {
            endOfWorld2012.incrementEmployeesInFund();
            endOfWorld2012.addToAmount(optionAmount);
        }

        if(planName.equals("Super Risky Optimists"))
        {
            superRiskyOptimists.incrementEmployeesInFund();
            superRiskyOptimists.addToAmount(optionAmount);
        }

        if(planName.equals("Y2K Survivors"))
        {
            Y2KSurvivors.incrementEmployeesInFund();
            Y2KSurvivors.addToAmount(optionAmount);
        }

        if(planName.equals("End Of Time 2038"))
        {
            endOfTime2038.incrementEmployeesInFund();
            endOfTime2038.addToAmount(optionAmount);
        }
        return new InvestmentOptions(planName, percent, optionAmount);
    }

    //determines valid input for the name of an investment option, and if it is valid, returns the name of the investment option
    String getOptionName()
    {
        int index = 1;
        int choice;
        isValid = true;

        System.out.println("Choose which funds to invest in: ");

        for (String option : investmentOptions)
        {
            System.out.println("Enter " + index + " to invest in " + option);
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

                if (stringBuffer.contains(investmentName))
                {
                    System.out.println("You are already investing in that fund.");
                    stringBuffer.remove(stringBuffer.size() - 1);
                    isValid = false;
                }
                stringBuffer.add(investmentName);

                System.out.println("You have invested in: ");
                for(String fund : stringBuffer)
                {
                    System.out.println(fund);
                }
            }

            else
            {
                System.out.println("No such investment option exists. Pick another one.");
                isValid = false;
            }
        } while (!isValid);

        return investmentName;
    }

    //determines whether an entered percentage is valid, and it if is, returns that percentage
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

    //determines the amount invested in a certain employee's fund based on the percentage allocated to that fund
    BigDecimal getOptionAmount(BigDecimal amount, double percent, String fund)
    {
        BigDecimal factor = new BigDecimal(percent * .01);
        System.out.println("Percentage: " + percent);
        System.out.println("Amount in this fund: $" + (amount.multiply(factor).setScale(2,RoundingMode.HALF_UP)));
        return amount.multiply(factor).setScale(2,RoundingMode.HALF_UP);
    }

}
