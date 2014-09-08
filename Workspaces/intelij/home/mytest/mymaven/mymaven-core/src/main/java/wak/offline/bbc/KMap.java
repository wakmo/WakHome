package wak.offline.bbc;

/**
 *
 * @author wakkir.muzammil
 */
public class KMap
{    
     
    public static void main(String[] args)
    {

       //KMap x=new KMap();
       long startTme=System.currentTimeMillis();
       int[][] region3=new int[][] {{1,1,0,1},
                                               {1,0,0,1},
                                               {0,1,0,0},
                                               {0,0,0,0}};
       int[] cellCount=new int[region3.length]
        for (int row = 0; row < region3.length; row++)        
        {
           int countRow=0;
           for (int cell = 0; cell < region3[row].length; cell++)        
           {
                System.out.print(region3[row][cell]);
                countRow+=region3[row][cell];
                
                
           } 
           System.out.println("-"+countRow);
        }
       long endTme=System.currentTimeMillis();
       System.out.println(region3.length);
       System.out.println("TimeSpent :"+String.valueOf(endTme-startTme));
       System.out.println("\nEnded...");
        

    }
    
}
