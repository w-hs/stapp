package de.whs.stapp.tabs;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

/**
 * Einfache Factory die views zu den Tabhost gibt.
 * @author Daniel
 */
//CHECKSTYLE:OFF
public class TabFactory implements TabContentFactory {
//CHECKSTYLE:ON
	private final Context mContext;
	  
      /**
       * Konstruktor der Factory.
       * @param context Context
       */
      public TabFactory(Context context) {
          mContext = context;
      }

      /**
       * Erstellt View für die Tabs.
       * 
       * @param tag Beschreibung eines Tabs
       */
      public View createTabContent(String tag) {
          View v = new View(mContext);
          v.setMinimumWidth(0);
          v.setMinimumHeight(0);
          return v;
      }
}
