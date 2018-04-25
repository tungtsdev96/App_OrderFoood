package com.ptpmcn.orderfood.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.model.restaurant.Comment;

import java.util.List;

/**
 * Created by tungts on 12/7/2017.
 */

public class RcvCommentAdapter extends RecyclerView.Adapter<RcvCommentAdapter.CommnetViewHolder> {

    private Context context;
    private List<Comment> comments;
    private LayoutInflater layoutInflater;

    public RcvCommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public CommnetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommnetViewHolder(layoutInflater.inflate(R.layout.item_comment,null));
    }

    @Override
    public void onBindViewHolder(CommnetViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.tv_name_cus.setText(comment.getCustomer_name());
        holder.tv_content.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    protected class CommnetViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name_cus,tv_content;

        public CommnetViewHolder(View itemView) {
            super(itemView);
            tv_name_cus = itemView.findViewById(R.id.tv_name_cus);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

}
