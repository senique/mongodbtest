

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
        StringBuilder hql = new StringBuilder();
        hql.append("SELECT u FROM User u, ChannelAuthUser cu    ");
        hql.append("WHERE cu.channelId = :channelId     ");
        hql.append("AND cu.status = :status     ");
        hql.append("AND u.userid = cu.authuserId    ");
        hql.append("AND u.roleId = :roleId  ");
        System.out.println(hql);

    }
    
}
