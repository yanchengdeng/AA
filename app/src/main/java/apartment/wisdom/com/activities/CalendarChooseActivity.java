package apartment.wisdom.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tubb.calendarselector.library.CalendarSelector;
import com.tubb.calendarselector.library.FullDay;
import com.tubb.calendarselector.library.IntervalSelectListener;
import com.tubb.calendarselector.library.MonthView;
import com.tubb.calendarselector.library.SCDateUtils;
import com.tubb.calendarselector.library.SCMonth;
import com.tubb.calendarselector.library.SegmentSelectListener;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import apartment.wisdom.com.R;
import apartment.wisdom.com.commons.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 邓言诚  Create at : 17/8/8  15:28
 * Email: yanchengdeng@gmail.com
 * Describle:日历选择界面
 */
public class CalendarChooseActivity extends BaseActivity {

    CalendarSelector selector;
    List<SCMonth> data;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_common_bar)
    RelativeLayout rlCommonBar;
    @BindView(R.id.rvCalendar)
    RecyclerView rvCalendar;
    private FullDay stant_in, stand_out;

    public static int SUCCESS_SELECT_TIME = 0x110;
    private boolean isSelectSingle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_choose);
        ButterKnife.bind(this);
        tvTittle.setText(getString(R.string.select_date));
        stant_in = getIntent().getParcelableExtra(Constants.PASS_STAND_IN);
        stand_out = getIntent().getParcelableExtra(Constants.PASS_STAND_OUT);
        isSelectSingle = getIntent().getBooleanExtra(Constants.PASS_SELECT_HOTLE_TYPE, false);
        rvCalendar.setLayoutManager(new LinearLayoutManager(this));
//        ((SimpleItemAnimator) rvCalendar.getItemAnimator()).setSupportsChangeAnimations(false);
        data = getData();
        if (isSelectSingle) {
            intervalMode();
        } else {
            segmentMode();
        }
    }

    /**
     * interval mode
     */
    private void intervalMode() {
        selector = new CalendarSelector(data, CalendarSelector.INTERVAL);
        selector.addSelectedInterval(stant_in);
        selector.setIntervalSelectListener(new IntervalSelectListener() {
            @Override
            public void onIntervalSelect(List<FullDay> selectedDays) {

            }

            @Override
            public boolean onInterceptSelect(List<FullDay> selectedDays, FullDay selectingDay) {

                Calendar selectCalend = Calendar.getInstance(Locale.CHINA);
                selectCalend.set(Calendar.YEAR, selectingDay.getYear());
                selectCalend.set(Calendar.MONTH, selectingDay.getMonth());
                selectCalend.set(Calendar.DAY_OF_MONTH, selectingDay.getDay());
                LogUtils.w("dyc", selectCalend.getTime().getTime() + "----" + System.currentTimeMillis());
                Calendar todayCalend = Calendar.getInstance(Locale.CHINA);
                todayCalend.set(Calendar.MONTH, todayCalend.get(Calendar.MONTH) + 1);
                if (!SCDateUtils.isToday(selectCalend.get(Calendar.YEAR), selectCalend.get(Calendar.MONTH), selectCalend.get(Calendar.DAY_OF_MONTH))) {
                    if (selectCalend.compareTo(todayCalend) < 0) {
                        ToastUtils.showShort(R.string.can_not_select_before_today);
                        return true;
                    }
                }

                selectCalend.add(Calendar.DAY_OF_MONTH,1);
                FullDay nextDay = new FullDay(selectCalend.get(Calendar.YEAR),selectCalend.get(Calendar.MONTH),selectCalend.get(Calendar.DAY_OF_MONTH));

                Intent intent = new Intent();
                intent.putExtra(Constants.PASS_STAND_IN, selectingDay);
                intent.putExtra(Constants.PASS_STAND_OUT, nextDay);
                intent.putExtra(Constants.STAND_IN_OUT_DISTANCE, 1);
                setResult(SUCCESS_SELECT_TIME, intent);
                finish();
                return super.onInterceptSelect(selectedDays, selectingDay);
            }
        });
        rvCalendar.setAdapter(new CalendarAdpater(data));
    }

    /**
     * segment mode
     */
    private void segmentMode() {
        selector = new CalendarSelector(data, CalendarSelector.SEGMENT);
//        if (isSelectSingle) {
//            selector.addSelectedSegment(stant_in);
//        } else {
        selector.addSelectedSegment(stant_in, stand_out);
//        }
        selector.setSegmentSelectListener(new SegmentSelectListener() {
            @Override
            public void onSegmentSelect(FullDay startDay, FullDay endDay) {


            }

            @Override
            public boolean onInterceptSelect(FullDay selectingDay) { // one day intercept
                Calendar selectCalend = Calendar.getInstance(Locale.CHINA);
                selectCalend.set(Calendar.YEAR, selectingDay.getYear());
                selectCalend.set(Calendar.MONTH, selectingDay.getMonth());
                selectCalend.set(Calendar.DAY_OF_MONTH, selectingDay.getDay());
                LogUtils.w("dyc", selectCalend.getTime().getTime() + "----" + System.currentTimeMillis());
                Calendar todayCalend = Calendar.getInstance(Locale.CHINA);
                todayCalend.set(Calendar.MONTH, todayCalend.get(Calendar.MONTH) + 1);
                if (!SCDateUtils.isToday(selectCalend.get(Calendar.YEAR), selectCalend.get(Calendar.MONTH), selectCalend.get(Calendar.DAY_OF_MONTH))) {
                    if (selectCalend.compareTo(todayCalend) < 0) {
                        ToastUtils.showShort(R.string.can_not_select_before_today);
                        return true;
                    }
                }
                return super.onInterceptSelect(selectingDay);
            }

            @Override
            public boolean onInterceptSelect(FullDay startDay, FullDay endDay) { // segment days intercept
                int differDays = SCDateUtils.countDays(startDay.getYear(), startDay.getMonth(), startDay.getDay(),
                        endDay.getYear(), endDay.getMonth(), endDay.getDay());
                Log.d("dyc", "differDays " + differDays);
                if (differDays > 5) {
                    ToastUtils.showShort(R.string.no_more_than_five);
                    return true;
                }
                Intent intent = new Intent();
                intent.putExtra(Constants.PASS_STAND_IN, startDay);
                intent.putExtra(Constants.PASS_STAND_OUT, endDay);
                intent.putExtra(Constants.STAND_IN_OUT_DISTANCE, differDays-1);
                setResult(SUCCESS_SELECT_TIME, intent);
                finish();
                return super.onInterceptSelect(startDay, endDay);
            }

            @Override
            public void selectedSameDay(FullDay sameDay) { // selected the same day
                super.selectedSameDay(sameDay);
            }
        });
        rvCalendar.setAdapter(new CalendarAdpater(data));
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    class CalendarAdpater extends RecyclerView.Adapter<CalendarViewHolder> {

        List<SCMonth> months;

        public CalendarAdpater(List<SCMonth> months) {
            this.months = months;
        }

        @Override
        public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CalendarViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar, parent, false));
        }

        @Override
        public void onBindViewHolder(CalendarViewHolder holder, int position) {
            SCMonth scMonth = months.get(position);
            holder.tvMonthTitle.setText(String.format("%d年%d月", scMonth.getYear(), scMonth.getMonth()));
            holder.monthView.setSCMonth(scMonth);
            selector.bind(rvCalendar, holder.monthView, position);
        }

        @Override
        public int getItemCount() {
            return months.size();
        }
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder {

        TextView tvMonthTitle;
        MonthView monthView;

        public CalendarViewHolder(View itemView) {
            super(itemView);
            tvMonthTitle = (TextView) itemView.findViewById(R.id.tvMonthTitle);
            monthView = (MonthView) itemView.findViewById(R.id.ssMv);
        }
    }


    public List<SCMonth> getData() {
        return SCDateUtils.generateMonths(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 3);
    }
}
