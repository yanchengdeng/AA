package apartment.wisdom.com.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import apartment.wisdom.com.R;
import apartment.wisdom.com.beans.SelfRoomListInfo;

public class SelectRoomAdapter extends BaseQuickAdapter<SelfRoomListInfo.SelfRoomItem,BaseViewHolder> {
    public SelectRoomAdapter(@LayoutRes int layoutResId, @Nullable List<SelfRoomListInfo.SelfRoomItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelfRoomListInfo.SelfRoomItem item) {
        ((TextView) helper.getView(R.id.tv_room_id)).setText(item.roomNo);
        ((TextView) helper.getView(R.id.tv_room_level)).setText(item.roomDomain);
        if (item.isSelect){
            ((ImageView)helper.getView(R.id.iv_select)).setImageResource(R.mipmap.room_sele_s_ic);
        }else{
            ((ImageView)helper.getView(R.id.iv_select)).setImageResource(R.mipmap.room_sele_ic);
        }
    }
}
