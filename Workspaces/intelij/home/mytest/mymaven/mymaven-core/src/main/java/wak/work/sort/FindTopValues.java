/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.work.sort;

/**
 *
 * @author wakkir.muzammil
 */
public interface FindTopValues
{
    /*
    * Interface has modified as below to accomodate Integer[] parameter instead of int[]
    * 1. Java primitive types are not reference types
    * 2. Java does generics using type-erasure of reference types 
    * Since both of these are true, generic Java collections can not store primitive types directly
    */
    int findMaxValue(Integer[] anyOldOrderValues);
    Integer[] findTopNValues(Integer[] anyOldOrderValues, int n);

}
