package apartment.wisdom.com.adapters.city;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.CustomeType;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomeNeedListItemAdapter extends BaseAdapter {
    private Context context;
    List<CustomeType.CustomTypeItem> customTypeItems;

    public CustomeNeedListItemAdapter(Context context, CustomeType item) {
        this.context = context;
        this.customTypeItems = item.getCustomTypeItems();
    }

    @Override
    public int getCount() {
        return customTypeItems.size();
    }

    @Override
    public Object getItem(int i) {
        return customTypeItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_custome_room_item_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvName.setText(customTypeItems.get(i).getName());
        viewHolder.tvMoney.setText(customTypeItems.get(i).getPrice());
        Glide.with(context).load(customTypeItems.get(i).getImg()).into(viewHolder.ivImage);
        if (customTypeItems.get(i).isSelect()){
            viewHolder.iv_selected.setVisibility(View.VISIBLE);
        }else{
            viewHolder.iv_selected.setVisibility(View.GONE);
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.rl_ui)
        RelativeLayout rlUi;
        @BindView(R.id.iv_selected)
        ImageView iv_selected;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
