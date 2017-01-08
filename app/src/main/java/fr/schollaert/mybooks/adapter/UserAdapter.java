package fr.schollaert.mybooks.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.schollaert.mybooks.R;
import fr.schollaert.mybooks.model.User;

/**
 * Created by Thomas on 08/01/2017.
 */

public class UserAdapter extends BaseAdapter {
    Context context;
    List<User> userList;

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = parent.inflate(context, R.layout.listitem_user, null);
        final User user = (User) getItem(position);

        TextView tvParamName = (TextView) view.findViewById(R.id.tvUserPseudo);
        tvParamName.setText(user.getPseudo());

        TextView tvParamValue = (TextView) view.findViewById(R.id.tvUserMail);
        tvParamValue.setText(user.getMail());

        return view;
    }

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    public UserAdapter() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAdapter that = (UserAdapter) o;

        if (!context.equals(that.context)) return false;
        return userList.equals(that.userList);

    }

    @Override
    public int hashCode() {
        int result = context.hashCode();
        result = 31 * result + userList.hashCode();
        return result;
    }
}
