package com.company;

import java.math.BigDecimal;

public class InvestmentFund
{
    private String name = "";
    private BigDecimal amountInFund = new BigDecimal("0");
    private int numOfEmployeesInFund = 0;

    public InvestmentFund(String name)
    {
        this.name = name;
    }

    public void addToAmount(BigDecimal amountToAdd)
    {
        amountInFund = amountInFund.add(amountToAdd);
    }

    public void incrementEmployeesInFund()
    {
        numOfEmployeesInFund++;
    }

    public String getName()
    {
        return name;
    }

    public BigDecimal getAmountInFund()
    {
        return amountInFund;
    }

    public int getNumOfEmployeesInFund()
    {
        return numOfEmployeesInFund;
    }
}
