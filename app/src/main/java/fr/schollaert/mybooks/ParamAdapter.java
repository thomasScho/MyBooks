package fr.schollaert.mybooks;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Thomas on 04/01/2017.
 */

public class ParamAdapter extends BaseAdapter {
    Context context;
    List<Param> paramList;

    @Override
    public int getCount() {
        return paramList.size();
    }

    @Override
    public Object getItem(int position) {
        return paramList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = parent.inflate(context, R.layout.listitem_param, null);
        final Param paramName = (Param) getItem(position);

        TextView tvParamName = (TextView) view.findViewById(R.id.tvParamName);
        tvParamName.setText(paramName.getName());

        TextView tvParamValue = (TextView) view.findViewById(R.id.tvParamValue);
        tvParamValue.setText(paramName.getDescription());

        return view;
    }

    public ParamAdapter(Context context, List<Param> paramList) {
        this.context = context;
        this.paramList = paramList;
    }
}
