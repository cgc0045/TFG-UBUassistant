package es.ubu.cgc0045.ubuassistant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageListHolder>{

    private Context mContext;
    private List<Message> messages;

    public MessageListAdapter(Context mContext, List<Message> messages) {
        this.mContext = mContext;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(viewType==2 ? R.layout.user_message : R.layout.response_message, null);
        return new MessageListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListHolder holder, int position) {
        Message mes = messages.get(position);
        holder.mes.setText(mes.getMessage());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position % 2 * 2;
    }

    class MessageListHolder extends RecyclerView.ViewHolder{

        TextView mes;

        public MessageListHolder(View itemView) {
            super(itemView);

            mes = itemView.findViewById(R.id.text_message_body);
        }
    }

}
