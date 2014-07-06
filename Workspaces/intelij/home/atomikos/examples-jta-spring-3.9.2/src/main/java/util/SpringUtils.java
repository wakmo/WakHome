package util;

import java.io.File;

/**
 * User: wakkir
 * Date: 22/02/14
 * Time: 14:48
 */
public class SpringUtils
{

    public static String getSpringPath(String xmlRefFromResources)
    {
        String path = (new File("src\\main\\resources")).getAbsolutePath();

        System.out.println("Current path is: " + path);

        path=path+"\\"+xmlRefFromResources;

        return path;

    }
}
