package wak.test.technologyconversations.tddbestpractices;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringCalculator
{
    private static final Logger logger = LoggerFactory.getLogger(StringCalculator.class.getName());


    public static int add(final String numbers)
    {
        logger.debug("executing add ....");
        String delimiter = ",|\n";
        String numbersWithoutDelimiter = numbers;
        if (numbers.startsWith("//"))
        {
            int delimiterIndex = numbers.indexOf("//") + 2;
            delimiter = numbers.substring(delimiterIndex, delimiterIndex + 1);
            numbersWithoutDelimiter = numbers.substring(numbers.indexOf("\n") + 1);
        }
        return add(numbersWithoutDelimiter, delimiter);
    }

    private static int add(final String numbers, final String delimiter)
    {
        logger.debug("executing add2 ....");
        int returnValue = 0;
        String[] numbersArray = numbers.split(delimiter);
        List<Integer> negativeNumbers = new ArrayList<Integer>();
        for (String number : numbersArray)
        {
            if (!number.trim().isEmpty())
            {
                int numberInt = Integer.parseInt(number.trim());
                if (numberInt < 0)
                {
                    negativeNumbers.add(numberInt);
                }
                else if (numberInt <= 1000)
                {
                    returnValue += numberInt;
                }
            }
        }
        if (negativeNumbers.size() > 0)
        {
            throw new RuntimeException("Negatives not allowed: " + negativeNumbers.toString());
        }
        return returnValue;
    }
    
    public static void main(String[] args) 
    {
        System.out.println("Hello World");
        System.out.println("ASDASD > "+add("1,2"));
        System.out.println("Done");
    }
}


