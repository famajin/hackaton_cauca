package com.example.focusgroupheroican.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.focusgroupheroican.R;
import com.example.focusgroupheroican.clases.ClsQuestion;
import com.example.focusgroupheroican.clases.ClsRango;
import com.example.focusgroupheroican.views.ViewAdminRang;

public class AdpAdminQuestions extends ArrayAdapter{
	
	private Context context;
	private ArrayList<ClsQuestion> questions;

    public AdpAdminQuestions(Context context, ArrayList<ClsQuestion> questions) {
    	super(context, R.layout.item_question_admin, questions);
    	this.questions=questions;
        this.context=context;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    
    private ViewHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null){
        	holder=new ViewHolder();
        	convertView=View.inflate(context, R.layout.item_question_admin, null);
        	holder.txt=(TextView) convertView.findViewById(R.id.viewQuesAdmin_txt);
        	holder.lin=(ViewGroup) convertView.findViewById(R.id.viewQuesAdmin_lin);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }
        ClsQuestion q=questions.get(position);
        holder.txt.setText(q.ques_description);
        
        holder.lin.removeAllViews();
    	if(q.ques_tipe==ClsQuestion.BOOLEAN){
        	holder.lin.addView(new ViewAdminRang(context, context.getResources().getString(R.string.yes), 20));
        	holder.lin.addView(new ViewAdminRang(context, context.getResources().getString(R.string.not), 20));
        }else{
        	for (ClsRango r : q.rangs) {
    			holder.lin.addView(new ViewAdminRang(context, r.rang_description, 10));
    		}
        }
        return convertView;
    }
    
    public class ViewHolder{
        TextView txt;
        ViewGroup lin;
    }
}
