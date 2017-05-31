package com.cassandra_driver_test;

import java.util.Iterator;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "start" );
        Cluster cluster = Cluster.builder()
                .addContactPoint("127.0.0.1")
                .build();        

        cluster.init();
        
        Session session = cluster.connect("thingsboard");
        
        String cql = "select * from thingsboard.user;";
        ResultSet result = session.execute(cql);
        
        Iterator<Row> iterator = result.iterator();
        while(iterator.hasNext())
        {
        	Row row = iterator.next();
        	System.out.print( "authority :" +  row.getString("authority"));
        	System.out.println( "\t email :" +  row.getString("email"));
        }        
        cluster.close();
        System.out.println( "end!" );
        
    }
}
