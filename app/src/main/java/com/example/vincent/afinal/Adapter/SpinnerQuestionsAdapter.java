package com.example.vincent.afinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vincent.afinal.Objects.Question;
import com.example.vincent.afinal.R;

import java.util.List;

/**
 * Created by Vincent on 05.11.2016.
 */

//Custom adapteur créé pour pour le spinner afin de pouvoir récupérer les questions dans la base de donnée et les inflates dans le spinnner
public class SpinnerQuestionsAdapter extends BaseAdapter{

    private List<Question> items;
    private LayoutInflater inflater;

    public SpinnerQuestionsAdapter(Context context, List<Question> listQuestions){
        this.setItems(listQuestions);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Question getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getId() : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if(view == null){
            view = inflater.inflate(R.layout.spinner_item_question, parent, false);
            holder = new ViewHolder();
            holder.tv_QuestionTitle = (TextView) view.findViewById(R.id.tv_question_title);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        Question currentItem = getItem(position);
        if(currentItem != null){
            holder.tv_QuestionTitle.setText(currentItem.getTitle());
        }

        return view;
    }

    public List<Question> getItems() {
        return items;
    }

    public void setItems(List<Question> items) {
        this.items = items;
    }

    class ViewHolder {
        TextView tv_QuestionTitle;
    }
}
