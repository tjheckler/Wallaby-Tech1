package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InvestmentOptions
{
    private String name;
    private double percent;
    private BigDecimal amount;

    public InvestmentOptions(String name, double percent, BigDecimal amount)
    {
        this.name = name;
        this.percent = percent;
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public String getName()
    {
        return name;
    }

    public double getPercent()
    {
        return percent;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }
}
