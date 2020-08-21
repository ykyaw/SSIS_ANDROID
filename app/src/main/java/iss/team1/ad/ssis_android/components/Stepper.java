package iss.team1.ad.ssis_android.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import iss.team1.ad.ssis_android.R;

public class Stepper extends LinearLayout {

    private Button plus,minus;
    private EditText number_text;
    private int number=0;

    public Stepper(Context context) {
        super(context);
        init(context);
    }

    public Stepper(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Stepper(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public Stepper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.customize_stepper, this);
        plus=(Button)this.findViewById(R.id.plus);
        minus=(Button)this.findViewById(R.id.minus);
        number_text=(EditText)this.findViewById(R.id.number_text);

        number_text.setText(number+"");
//        number_text.setText(number);

        plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                number++;
                number_text.setText(number+"");
            }
        });

        minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                number--;
                number_text.setText(number+"");
            }
        });
    }
}
