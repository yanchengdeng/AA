package apartment.wisdom.com.adapters.city;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.ImagePreviewActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.CustomeType;
import apartment.wisdom.com.enums.DIYType;
import apartment.wisdom.com.utils.LoginUtils;
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
        final ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_custome_room_item_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final CustomeType.CustomTypeItem diyItem = customTypeItems.get(i);

        viewHolder.tvName.setText(customTypeItems.get(i).getName());
        viewHolder.tvMoney.setText(customTypeItems.get(i).getPrice());
        Glide.with(context).load(customTypeItems.get(i).getImg()).into(viewHolder.ivImage);
        viewHolder.ckSelected.setChecked(customTypeItems.get(i).isSelect());
        if (customTypeItems.get(i).isSupportMultSelect()) {
            viewHolder.llSupportMult.setVisibility(View.VISIBLE);
        } else {
            viewHolder.llSupportMult.setVisibility(View.GONE);
        }

        viewHolder.tvNumBreakfast.setText(String.valueOf(diyItem.getNum()));

        //增加
        viewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int diyCount = Integer.parseInt(viewHolder.tvNumBreakfast.getText().toString());
                diyItem.setNum(diyCount + 1);
                notifyDataSetChanged();
            }
        });

        //减少
        viewHolder.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int diyCount = Integer.parseInt(viewHolder.tvNumBreakfast.getText().toString());
                if (diyCount == 1) {
                    return;
                } else {
                    diyItem.setNum(diyCount - 1);
                    notifyDataSetChanged();
                }
            }
        });


        viewHolder.ckSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (diyItem.getDiy_type().equals(DIYType.DIY_TYPE_BRAKFAST.getType())||diyItem.getDiy_type().equals(DIYType.DIY_TYPE_WINE.getType())) {
                    diyItem.setSelect(b);
                }else{
                    LoginUtils.setDIYByTypeResetSelected(customTypeItems,diyItem.getDiy_type());
                    diyItem.setSelect(b);
                }
                notifyDataSetChanged();
            }
        });

        //点击图片预览
        viewHolder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setBigImageUrl(diyItem.getImg());
                imageInfo.setThumbnailUrl(diyItem.getImg());
                List<ImageInfo> imageInfos = new ArrayList<>();
                imageInfos.add(imageInfo);
                Intent intent = new Intent(context, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfos);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, 0);
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(0, 0);
            }
        });


        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.iv_minus)
        ImageView ivMinus;
        @BindView(R.id.tv_num_breakfast)
        TextView tvNumBreakfast;
        @BindView(R.id.iv_add)
        ImageView ivAdd;
        @BindView(R.id.ll_support_mult)
        LinearLayout llSupportMult;
        @BindView(R.id.iv_selected)
        CheckBox ckSelected;
        @BindView(R.id.rl_ui)
        LinearLayout rlUi;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
