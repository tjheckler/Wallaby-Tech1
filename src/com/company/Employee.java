package com.company;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Employee
{
    private String name;
    private BigDecimal amountAdded;
    private String employeeId;
    List<InvestmentOptions> employeeInvestments = new LinkedList<>();


    public Employee(String name, BigDecimal amountAdded, List<InvestmentOptions> employeeInvestments)
    {
        this.name = name;
        this.amountAdded = amountAdded;
        this.employeeId = employeeId;
        this.employeeInvestments = employeeInvestments;
    }
    public String getName()
    {
        return name;
    }

    public BigDecimal getAmountAdded()
    {
        return amountAdded;
    }

    public String getEmployeeId()
    {
        return employeeId;
    }

    public List<InvestmentOptions> getEmployeeInvestments()
    {
        return employeeInvestments;
    }



}
