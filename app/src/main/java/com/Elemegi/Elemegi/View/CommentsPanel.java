package com.Elemegi.Elemegi.View;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Elemegi.Elemegi.Controller.MainManager;
import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.Model.Comment;
import com.Elemegi.Elemegi.R;

public class CommentsPanel extends ViewManager {
    LinearLayout comments;
    private String userType = "";
    private AppCompatActivity act;
    private LinearLayout sendLine;
    private long productID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.comment_page);
        sendLine = (LinearLayout) findViewById(R.id.writeComment);

        if(MainManager.getInstance().getCurrentUser() != null) {
            userType = MainManager.getInstance().getCurrentUser().getRoleType(); // User Typeına göre işlem yap customersa home_oage_page otherwise home_page_page_p
        }
        else {
            userType = "";
        }
        if(userType.equals("Producer")) {
            sendLine.setVisibility(View.INVISIBLE);
        }
        Intent intent = getIntent();
        productID = intent.getLongExtra("id",0);
        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();
        comments = (LinearLayout) findViewById(R.id.commentsList);
        Comment[] myComments = ViewManager.getMyCommentList(productID);
        for (int i = 0; i < myComments.length; i++){
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
            tempImage.setImageResource(R.drawable.comment_profile);
            TableRow.LayoutParams imageParam = new TableRow.LayoutParams(120,120);
            imageParam.setMargins(10,10,0,10);
            tempImage.setLayoutParams(imageParam);
            layoutToAdd.addView(tempImage);

            TextView tempName = new TextView(act);
            tempName.setText(myComments[i].getCustomerName());
            tempName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tempName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            tempName.setTextColor(Color.parseColor("#000F00"));
            layoutToAdd.addView(tempName);

            TextView tempText = new TextView(act);
            tempText.setText(myComments[i].getComment());
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
