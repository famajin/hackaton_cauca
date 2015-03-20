package com.example.focusgroupheroican.views;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.focusgroupheroican.R;
import com.example.focusgroupheroican.R.color;
import com.example.focusgroupheroican.R.drawable;
import com.example.focusgroupheroican.R.id;
import com.example.focusgroupheroican.R.layout;
import com.example.focusgroupheroican.clases.ClsQuestion;

public class ViewQuestionBool extends LinearLayout implements OnClickListener{

	private Button btnYes, btnNot;
	private EditText edt;
	private ClsQuestion question;
	
	public ViewQuestionBool(Context context, ClsQuestion question) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context, question);
	}
	
	private void init(Context context, ClsQuestion question) {
		this.question=question;
		inflate(context, R.layout.view_question_bool, this);
		TextView txtDescription=(TextView)findViewById(R.id.viewBool_description);
		txtDescription.setText(question.ques_description);
		edt=(EditText) findViewById(R.id.viewBool_edt);
		edt.setHint(question.ques_hint);
		btnYes=(Button) findViewById(R.id.viewBool_yes);
		btnNot=(Button) findViewById(R.id.viewBool_not);
		btnYes.setOnClickListener(this);
		btnNot.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.viewBool_yes:
			btnYes.setBackgroundResource(R.color.press_bool_yes);
			btnNot.setBackgroundResource(R.drawable.selector_light);
			question.answer_id=1;
			break;

		case R.id.viewBool_not:
			btnNot.setBackgroundResource(R.color.press_bool_not);
			btnYes.setBackgroundResource(R.drawable.selector_light);
			question.answer_id=2;
			break;
		}
	}
	
	public boolean saveComment() {
		question.comment=edt.getText().toString().toLowerCase().trim();
		if(question.comment.length()==0){
			question.comment="...";
			return false;
		}else{
			return true;
		}
	}

}
