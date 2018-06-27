package es.ubu.cgc0045.ubuassistant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author Carlos González Calatrava
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageListHolder>{

    private Context mContext;
    private List<Message> messages;

    MessageListAdapter(Context mContext, List<Message> messages) {
        this.mContext = mContext;
        this.messages = messages;
    }

    /**
     * Method used to obtain the style of the message holder depending on the view type.
     * @param parent Parent of the view
     * @param viewType Type of the view
     * @return Style of the message holder
     */
    @NonNull
    @Override
    public MessageListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(viewType==2 ? R.layout.user_message : R.layout.response_message, null);
        return new MessageListHolder(view);
    }

    /**
     * Method used to set the params of the message holder
     * @param holder Message holder
     * @param position Position of the holder
     */
    @Override
    public void onBindViewHolder(@NonNull MessageListHolder holder, int position) {
        Message mes = messages.get(position);

        holder.mes.setMovementMethod(LinkMovementMethod.getInstance());
        holder.mes.setClickable(true);
        holder.mes.setText(Html.fromHtml(mes.getMessage()));
    }

    /**
     * Method used to obtain the size of the message.
     * @return Message size
     */
    @Override
    public int getItemCount() {
        return messages.size();
    }

    /**
     * Method used to select the type of the view
     * @param position Position of the holder
     * @return final position
     */
    @Override
    public int getItemViewType(int position) {

        return position % 2 * 2;
    }

    class MessageListHolder extends RecyclerView.ViewHolder{

        TextView mes;

        MessageListHolder(View itemView) {
            super(itemView);

            mes = itemView.findViewById(R.id.text_message_body);
        }
    }

}
