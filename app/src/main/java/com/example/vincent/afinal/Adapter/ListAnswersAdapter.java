package com.example.vincent.afinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vincent.afinal.Objects.Answer;
import com.example.vincent.afinal.R;

import java.util.List;

/**
 * Created by Vincent on 05.11.2016.
 */

//custom adapter créé pour afficher les réponses sous une simple forme de textView
public class ListAnswersAdapter extends BaseAdapter{

    private List<Answer> items;
    private LayoutInflater inflater;

    public ListAnswersAdapter(Context context, List<Answer> listQuestions){
        this.setItems(listQuestions);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Answer getItem(int position) {
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
            view = inflater.inflate(R.layout.list_item_answer, parent, false);
            holder = new ViewHolder();
            holder.tvAnswerContent = (TextView) view.findViewById(R.id.tv_answer_content);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Answer currentItem = getItem(position);
        if(currentItem != null){
            holder.tvAnswerContent.setText(currentItem.getContent());
        }

        return view;
    }

    public List<Answer> getItems(){
        return items;
    }

    public void setItems(List<Answer> items){
        this.items = items;
    }

    class ViewHolder{
        TextView tvAnswerContent;
    }
}
