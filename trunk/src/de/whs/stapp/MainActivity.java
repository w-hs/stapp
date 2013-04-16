package de.whs.stapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import de.whs.stapp.liveDataTracking.BTCommunicationService;
import de.whs.stapp.liveDataTracking.BTServiceConnection;
import de.whs.stapp.tabs.TabsViewPagerFragmentActivity;

/**
 * The applications MainActivity.
 * 
 * @author Chris
 */
public class MainActivity extends Activity {

	
	 	private BTServiceConnection btServiceBindConnection;	
	 	
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	      
		    
	        this.startActivity(new Intent(MainActivity.this, TabsViewPagerFragmentActivity.class));
	        
	        // bind to BT Service
	        Intent intent = new Intent(this, BTCommunicationService.class);	
			btServiceBindConnection = new BTServiceConnection();
			bindService(intent, btServiceBindConnection, Context.BIND_AUTO_CREATE);

	 	}
}
