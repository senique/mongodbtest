package test.java;

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
        
        
    }
    
}
