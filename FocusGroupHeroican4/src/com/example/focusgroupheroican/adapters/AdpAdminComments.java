package com.example.focusgroupheroican.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.focusgroupheroican.R;
import com.example.focusgroupheroican.clases.ClsComment;

public class AdpAdminComments extends ArrayAdapter{
	
	private Context context;
	private ArrayList<ClsComment> comments;

    public AdpAdminComments(Context context, ArrayList<ClsComment> comments) {
    	super(context, R.layout.item_question_admin, comments);
    	this.comments=comments;
        this.context=context;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    
    private ViewHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null){
        	holder=new ViewHolder();
        	convertView=View.inflate(context, R.layout.item_comment_admin, null);
        	
        	holder.txtAnswer=(TextView) convertView.findViewById(R.id.itemCom_txtAnswer);
        	holder.txtComment=(TextView) convertView.findViewById(R.id.itemCom_txtComment);
        	holder.txtDescription=(TextView) convertView.findViewById(R.id.itemCom_txtDescription);
        	
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }
        
        holder.txtDescription.setText(comments.get(position).usu_nombre);
        holder.txtAnswer.setText(comments.get(position).answer);
        holder.txtComment.setText(comments.get(position).comment);
        
        return convertView;
    }
    
    public class ViewHolder{
        TextView txtAnswer, txtComment, txtDescription;
    }
}
