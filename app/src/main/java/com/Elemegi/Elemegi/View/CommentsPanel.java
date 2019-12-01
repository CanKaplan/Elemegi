package com.Elemegi.Elemegi.View;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.R;

public class CommentsPanel extends ViewManager {
    LinearLayout comments;
    private AppCompatActivity act;
    String[] commentArray= {"aaaaaaaa","bbbbbbbbb","ccccccccc","dddddddd","eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee","fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff","aaaaaaaa","bbbbbbbbb","ccccccccc","dddddddd","eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee","fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff","aaaaaaaa","bbbbbbbbb","ccccccccc","dddddddd","eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee","fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"};
    String[] nameArray= {"can","can1","can2","can3","can4","can5","can","can1","can2","can3","can4","can5","can","can1","can2","can3","can4","can5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.comment_page);
        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();
        comments = (LinearLayout) findViewById(R.id.commentsList);

        for (int i = 0; i < commentArray.length; i++){
            LinearLayout layoutToAdd = new LinearLayout(act);

            layoutToAdd.setBackgroundColor(Color.parseColor("#FFFFFF"));
            layoutToAdd.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f);
            if(i == 0){
                params.setMargins(0, 1, 0, 0);
            }
            else{
                params.setMargins(0, 1, 0, 0);
            }
            layoutToAdd.setLayoutParams(params);
            layoutToAdd.setWeightSum(1);
            ImageView tempImage = new ImageView(act);
            String text = "R.drawable.pic" + (i + 1);
            tempImage.setImageResource(R.drawable.comment_profile);
            TableRow.LayoutParams imageParam = new TableRow.LayoutParams(120,120);
            imageParam.setMargins(10,10,0,10);
            tempImage.setLayoutParams(imageParam);
            layoutToAdd.addView(tempImage);
            TextView tempName = new TextView(act);
            tempName.setText(nameArray[i]);
            tempName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tempName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            tempName.setTextColor(Color.parseColor("#000F00"));
            layoutToAdd.addView(tempName);
            TextView tempText = new TextView(act);
            tempText.setText(commentArray[i]);
            TableRow.LayoutParams paramText = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
            paramText.setMargins(50,30,0,30);
            tempText.setLayoutParams(paramText);
            tempText.setTextColor(Color.parseColor("#000000"));
            tempText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            layoutToAdd.addView(tempText);
            comments.addView(layoutToAdd);
        }
    }
}
