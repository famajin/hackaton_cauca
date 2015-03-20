package com.example.focusgroupheroican.views;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.focusgroupheroican.R;
import com.example.focusgroupheroican.R.id;
import com.example.focusgroupheroican.R.layout;
import com.example.focusgroupheroican.R.string;
import com.example.focusgroupheroican.clases.ClsQuestion;

public class ViewQuestionRang extends LinearLayout implements OnSeekBarChangeListener{

	private TextView txtSeek;
	private EditText edt;
	private ClsQuestion question;
	
	public ViewQuestionRang(Context context, ClsQuestion question) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context, question);
	}
	
	private void init(Context context, ClsQuestion question) {
		inflate(context, R.layout.view_answer_admin, this);
		this.question=question;
		TextView txtDescription=(TextView)findViewById(R.id.viewRang_description);
		txtDescription.setText(question.ques_description);
		
		edt=(EditText) findViewById(R.id.viewBool_edt);
		edt.setHint(question.ques_hint);
		
		txtSeek=(TextView)findViewById(R.id.viewBool_txtSeek);
		txtSeek.setHint(getResources().getString(R.string.hint_deslizate));
		((SeekBar)findViewById(R.id.question_seekbar)).setMax(question.rangs.size()-1);
		((SeekBar)findViewById(R.id.question_seekbar)).setOnSeekBarChangeListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		question.answer_id=question.rangs.get(progress).ans_id;
		txtSeek.setText(question.rangs.get(progress).rang_description);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
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
