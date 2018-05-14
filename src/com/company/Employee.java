package com.company;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalTime;
public class Employee
{
    private String name;
    private BigDecimal amountAdded;
    private String employeeId;
    private List<InvestmentOptions> employeeInvestments;
    private LocalTime startTime;
    private LocalTime endTime;
    private String totalTime;

    public Employee(String name)
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

    public LocalTime getStartTime()
    {
        return startTime;
    }

    public LocalTime getEndTime()
    {
        return endTime;
    }

    public void setStartTime(LocalTime startTime)
    {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime)
    {
        this.endTime = endTime;
    }

    public void setAmountAdded(BigDecimal amountAdded)
    {
        this.amountAdded = amountAdded;
    }

    public void setEmployeeInvestments(List<InvestmentOptions> employeeInvestments)
    {
        this.employeeInvestments = employeeInvestments;
    }

    public String getTotalTime()
    {
        int hour = Math.abs(startTime.getHour() - endTime.getHour());
        int minute = Math.abs(startTime.getMinute() - endTime.getMinute());
        int second = Math.abs(startTime.getSecond() - endTime.getSecond());

        totalTime = String.format(("The employee has used this system for %d hours, %d minutes, and %d seconds."), hour, minute, second);

        return totalTime;
    }
}
