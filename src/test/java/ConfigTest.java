

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConfigTest
{
    
    public static void main(String[] args) throws NamingException
    {
//        # javax.naming.NoInitialContextException: Need to specify class name in environment or system property, 
//        #or as an applet parameter, or in an application resource file:  java.naming.factory.initial
        InitialContext ctx = new InitialContext();
        System.out.println(ctx);
        
//        Map<Integer, Integer> rdMap = new HashMap<Integer, Integer>();
        Map<Integer, Integer> rdMap = new HashMap<Integer, Integer>();
        for(int i = 0; i < 1000; i++) {
            int rd = new Random().nextInt(3);
            if(null != rdMap.get(rd)) {
                rdMap.put(rd, (Integer)rdMap.get(rd)+1);
            }
            else {
                rdMap.put(rd, 1);
            }
//            System.out.println(rd);
        }
        System.out.println(rdMap.toString());
        
        


    }
    
}
