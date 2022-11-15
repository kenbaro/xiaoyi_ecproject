package com.yeeshop.dao;

import java.util.List;

public interface IReportDAO {
    public List<Object[]> inventory();
	public List<Object[]> revenueByCategory();
	public List<Object[]> revenueByCustomer();
	public List<Object[]> revenueByYear();
	public List<Object[]> revenueByQuarter();
	public List<Object[]> revenueByMonth();
}
