package twistter.android.client.layout;

import twistter.android.client.activities.UpdateStatusActivity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MenuBar extends LinearLayout {

	public MenuBar(final Context context, AttributeSet attrs) {
		super(context, attrs);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setWeightSum(1.0f);
        
        String text = "Tweet";
        final Button tweetButton = new Button(context);
        tweetButton.setText(text);
        tweetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(context, UpdateStatusActivity.class);
    	    	context.startActivity(myIntent);
            }
        });
        addView(tweetButton);
        
        text = "Filters";
        final Button filterButton = new Button(context);
        filterButton.setText(text);
        filterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            }
        });
        addView(filterButton);
	}

}
