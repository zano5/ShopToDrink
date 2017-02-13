package za.co.codetribe.shoptodrink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import za.co.codetribe.shoptodrink.classes.Feedback;

public class FeedbackActivity extends AppCompatActivity {

    private EditText etSubject,etComment;
    private Button btnSubmit;
    private DatabaseReference db;
    private Feedback feedback;
    private FirebaseAuth mAth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        mAth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        etSubject = (EditText) findViewById(R.id.etSubject);
        etComment = (EditText) findViewById(R.id.etComment);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }


    public void onSubmit(View view)
    {
        feedback = new Feedback();

        if("".equals(etSubject.getText().toString()))
        {

            Toast.makeText(FeedbackActivity.this,"Enter Subject Field",Toast.LENGTH_SHORT).show();

        }else if("".equals(etComment.getText().toString()))
        {

            Toast.makeText(FeedbackActivity.this,"Enter Comment Field",Toast.LENGTH_SHORT).show();

        }else
        {

            feedback.setComment(etComment.getText().toString());
            feedback.setSubject(etSubject.getText().toString());

            db.child("Feedback").child("FeedbackList").child(mAth.getCurrentUser().getUid()).push().setValue(feedback);

            Intent intent = new Intent(FeedbackActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }
}
