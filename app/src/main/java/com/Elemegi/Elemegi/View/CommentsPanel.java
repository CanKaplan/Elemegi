package com.Elemegi.Elemegi.View;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Elemegi.Elemegi.Controller.MainManager;
import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.Model.Comment;
import com.Elemegi.Elemegi.R;

import java.util.List;

public class CommentsPanel extends ViewManager {
    LinearLayout comments;
    private String userType = "";
    private RelativeLayout layout;
    private AnimationDrawable anim;
    private AppCompatActivity act;
    private EditText commentAdd;
    private String commentAddString;
    private LinearLayout sendLine;
    private ImageView sendImage;
    private long productID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        productID = intent.getLongExtra("id",0);
        setContentView(R.layout.comment_page);
        sendLine = (LinearLayout) findViewById(R.id.writeComment);
        List<Comment> commentObject = ViewManager.getInstance().updateComments(productID);
        boolean statement = ViewManager.getInstance().checkIfOrdered(productID,ViewManager.getInstance().getCurrentUser().getID());
        if(MainManager.getInstance().getCurrentUser() != null) {
            userType = MainManager.getInstance().getCurrentUser().getRoleType(); // User Typeına göre işlem yap customersa home_oage_page otherwise home_page_page_p
        }
        else {
            userType = "";
        }
        if(userType.equals("Producer") || !statement) {
            sendLine.setVisibility(View.INVISIBLE);
        }
        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();
        comments = (LinearLayout) findViewById(R.id.commentsList);
        commentAdd = (EditText) findViewById(R.id.commentSection);
        sendImage = (ImageView) findViewById(R.id.sendComment);
        for (int i = 0; i < commentObject.size(); i++){
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
            tempName.setText(commentObject.get(i).getCustomerName());
            tempName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tempName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            tempName.setTextColor(Color.parseColor("#000F00"));
            layoutToAdd.addView(tempName);

            TextView tempText = new TextView(act);
            tempText.setText(commentObject.get(i).getComment());
            TableRow.LayoutParams paramText = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
            paramText.setMargins(50,30,0,30);
            tempText.setLayoutParams(paramText);
            tempText.setTextColor(Color.parseColor("#000000"));
            tempText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            layoutToAdd.addView(tempText);
            comments.addView(layoutToAdd);
        }
        sendImage.bringToFront();
        sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentAddString = commentAdd.getText().toString();
                ViewManager.getInstance().sendComment(commentAddString,productID,ViewManager.getInstance().getCurrentUser().getID());

                Intent myIntent = new Intent(act, CommentsPanel.class);
                myIntent.putExtra("id",productID);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();

                startActivity(myIntent);

            }
        });
    }
}
