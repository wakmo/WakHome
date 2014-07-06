package jms.util;
import java.io.File;

import org.apache.activemq.broker.BrokerService;

public class StartBroker
{
	public static void main ( String[] args ) 
	throws Exception
	{
		if ( args.length != 1 ) {
			System.err.println ( "Arg required: broker port" );
			System.exit ( 1 );
		}
		//create temp lock file on URL
		String fileName = "../" + args[0] + ".lck";
		File lockFile = new File ( fileName );
		if ( lockFile.createNewFile() ) {
			System.out.println ( "Starting broker on " + args[0] );
			lockFile.deleteOnExit();
			String[]  brokerArgs = new String[1];
			BrokerService broker = new BrokerService();

			// configure the broker
			broker.addConnector("tcp://localhost:" + args[0]);

			broker.start();
		}	
		else {
			System.out.println ( "Broker already running." );
		}
	
	}
}
