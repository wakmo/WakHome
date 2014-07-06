package com.wakkir.common;

public class ValuePair
{
	private String	name;
	private Object	value;

	public ValuePair(String name, Object value)
	{
		this.name = name;
		this.value = value;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}
	
	public String toString()
	{
		return "{name :"+this.name+", value:"+this.value+"}";
	}

}