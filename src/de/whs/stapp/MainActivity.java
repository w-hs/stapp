package de.whs.stapp;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import de.whs.stapp.presentation.TrainingseinheitWebView;
import android.os.Handler;
import android.os.IBinder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.widget.Toast;

import de.whs.stapp.helper.Constants;
import de.whs.stapp.services.BTCommunicationEvent;
import de.whs.stapp.services.BTCommunicationListener;
import de.whs.stapp.services.BTCommunicationService;
import de.whs.stapp.services.BTServiceBinder;

/**
 * The applications MainActivity.
 * 
 * @author Chris
 */
public class MainActivity extends Activity {

		private TrainingseinheitWebView mTrainingseinheitWebview;
	 	private BTServiceConnection btServiceBindConnection;
	 	private BTCommunicationService btService;
	 	private Handler mainThreadHandler = new Handler(); 	
	 	
	 	/**
	 	 * 
	 	 * Diese Methode blendet eine sogenannte 
	 	 * Toastmessage (Popup-Fenster) ein.
	 	 * 
	 	 * @param msg - Anzuzeigende Textnachricht
	 	 */
	 	private void postToastMessage(final String msg)
	 	{
	 		mainThreadHandler.post(new Runnable() {
	 			public void run() {
	 				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	 			}
	 		});
	 	}
	 	
	    
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);    
		    
	        // bind to BT Service
	        Intent intent = new Intent(this, BTCommunicationService.class);	
			btServiceBindConnection = new BTServiceConnection();
			bindService(intent, btServiceBindConnection, Context.BIND_AUTO_CREATE);
				
			mTrainingseinheitWebview = new TrainingseinheitWebView(this);

			RelativeLayout relativeLayout = 
					(RelativeLayout) findViewById(R.id.Wrapper);

			RelativeLayout.LayoutParams relParams = 
				new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			relParams.addRule(RelativeLayout.BELOW, R.id.btnSettings);
			
			relativeLayout.addView(mTrainingseinheitWebview, relParams);
			
			//OnClick Event Handler zu Testzwecken. Kann später wieder entfernt werden.
			Button startTraining = (Button)findViewById(R.id.btnStartRun);
			startTraining.setOnClickListener(new OnClickListener(){
				
				@Override
				public void onClick(View v) {

					TrainingseinheitWebView trainingseinheitWebView = 
						((MainActivity)v.getContext()).getmTrainingseinheitWebview();
					if(trainingseinheitWebView != null)
						trainingseinheitWebView.startTraining();
				}
			});
			
			//Missing Button to Connect
			//connect();
	 	}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action 
			// bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		
		/**
		 * Gibt die WebView der Trainingseinheit-Seite
		 * zurück.
		 * @return Trainingseinheit WebView
		 */
		public TrainingseinheitWebView getmTrainingseinheitWebview() {
			return mTrainingseinheitWebview;
		}
		
		/**
		 * Diese Methode versucht eine Bluetoothverbindung herzustellen
		 * und meldet den anschließenden BT-Status.
		 */
		private void connect() {
			int status;
			// try to connect
			if (btService != null) {
				status = btService.connectBT();
			} else {
				status = -1;
			}
			switch (status) {
			case Constants.BT_NO_ADAPTER_STATUS:
				postToastMessage("No Bluetooth adapter");
				break;
			case Constants.BT_CONNECTION_SUCCESS:
				postToastMessage("Connection Succesful");
				break;
			case Constants.BT_CONNECTION_FAILURE:
				postToastMessage("Bluetooth sensor (HxM) not found");
				break;
			default:
				postToastMessage("Default");
				break;
				
			}
		}
		
		final BTCommunicationListener listener = new BTCommunicationListener() {
			
			@Override
			public void getHxMData(BTCommunicationEvent e) {
				
				//Viel Spaß beim Benutzen der Daten, hoffentlich :D
				//e.getXXX
			}
		};
		
	 	/**
	 	 * Diese Methode ist von Nöten um den BTService zu binden.
		 **/
		class BTServiceConnection implements ServiceConnection {
			
			/**
			 * Diese Methode wird aufgerufen, wenn der Service gebunden wurde.
			 * 
			 * @param className - Klassenname
			 * 
			 * @param service - Service Binder
			 */
			public void onServiceConnected(ComponentName className, IBinder service) {
				BTServiceBinder serviceBinder = (BTServiceBinder) service; 
				btService = serviceBinder.getService();
				if (btService != null) {
					btService.registerHxMListener(listener);
				}
			}
			 /**
			  * Diese Methode wird aufgerufen, wenn der Verbund mit 
			  * dem Service aufgelöst wurde.
			  * 
			  * @param arg0 - Komponentenname
			  */
			public void onServiceDisconnected(ComponentName arg0) {
			}
			
		}
			
}
