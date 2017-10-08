package dch.com.myalipush;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by lenovo on 2017/9/20.
 */

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setText("hello");
        textView.setBackgroundColor(Color.parseColor("#ff8041"));
        setContentView(textView);
    }
}
