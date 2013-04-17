package de.whs.stapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import de.whs.stapp.data.bluetooth.BluetoothDevice;
import de.whs.stapp.tabs.TabsViewPagerFragmentActivity;

/**
 * The applications MainActivity.
 * 
 * @author Chris
 */
public class MainActivity extends Activity {
	
	 	private BluetoothDevice device = new BluetoothDevice();
	 	
	 	/**
	 	 * Erstellt eine neue Instanz der {@link MainActivity}.
	 	 */
	 	public MainActivity() {
	 	
	 	}
	 	
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        this.startActivity(new Intent(MainActivity.this, TabsViewPagerFragmentActivity.class));
	        device.connect(this);
	 	}
}
