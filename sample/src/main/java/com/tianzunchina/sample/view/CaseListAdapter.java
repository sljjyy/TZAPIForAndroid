package com.tianzunchina.sample.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.tianzunchina.sample.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 事件列表适配器
 * Created by sg on 2015/5/20.
 */
public class CaseListAdapter extends BaseAdapter {
    ArrayList<CaseParent> lists;
    Context context;
    float dish = 0;
    float disW = 0;
    int status_show;
    public static final String DEF_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public CaseListAdapter(Context context, ArrayList<CaseParent> lists, int status_show) {
        this.lists = lists;
        this.context = context;
        dish = Dp2Px(context, 50);
        disW = Dp2Px(context, 60);
        this.status_show = status_show;

    }

    private int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.item_case_list, null);
            holder = new Holder();
            holder.tvTitle = (TextView) view.findViewById(R.id.id_tv_case_list_item_title);
            holder.tvDate = (TextView) view.findViewById(R.id.id_tv_case_list_item_date);
            holder.iv = (ImageView) view.findViewById(R.id.id_iv_case_list_item_pic);
            holder.ivHot = (ImageView) view.findViewById(R.id.id_iv_case_list_item_new);
            holder.tvStatus = (TextView) view.findViewById(R.id.id_tv_case_list_item_address);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        if (lists != null && !lists.isEmpty()) {
            CaseParent caseParent = lists.get(position);
            holder.tvTitle.setText(caseParent.getTitle());
            holder.tvDate.setText((caseParent.getUpdateTime()));
            holder.tvStatus.setText(caseParent.getAddress());
            Date updateTime = getDateOfSrc(caseParent.getUpdateTime());
            Date currentTime = new Date();
            if (position <= 3 && getNumIncludeTwoHours(currentTime, updateTime) < 5) {
                holder.ivHot.setVisibility(View.VISIBLE);
            } else {
                holder.ivHot.setVisibility(View.GONE);
            }
           /* if(status_show != -1){
                if(caseParent.getStatus() == 0){
                    holder.tvStatus.setText("未上报");
                    holder.tvStatus.setTextColor(activity.getResources().getColor(R.color.red));
                }else{
                    holder.tvStatus.setText("已上报");
                    holder.tvStatus.setTextColor(activity.getResources().getColor(R.color.green));
                }
            }else*/
            {
                //holder.tvStatus.setVisibility(View.GONE);
            }
            if (caseParent.getPicture1() != null) {
                String url = caseParent.getPicture1();
                Glide.with(context).load(url).override(100, 100).into(holder.iv);
                //imageLoader.displayImage(url,holder.iv,SysApplication.getInstance().getOptions());

                /*
                 if(url.startsWith("http")){
                    final Holder holder1 = holder;
                    ImageRequest imgRequest = new ImageRequest(url,
                            new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap bitmap) {
                                    holder1.iv.setImageBitmap(WindowsUtil.toRoundCorner(bitmap,40));
                                }
                            }, 0, 0, Bitmap.Config.RGB_565, new ErrResponse());
                    queue.add(imgRequest);
                    queue.start();
                    imageLoader.displayImage(url,holder.iv,SysApplication.getInstance().getOptions());
                }
                 */

            } else {
                holder.iv.setImageResource(R.drawable.img_holder);
            }

            // Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(),lists.get(i).getImgId());
            //holder.iv.setImageBitmap(bitmap);
        }
        //if(imageLoader != null)
        // imageLoader.destroy();
        return view;
    }

    class Holder {
        TextView tvTitle;
        TextView tvDate;
        ImageView iv;
        ImageView ivHot;
        TextView tvStatus;
    }

    public  Date getDateOfSrc(String src) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEF_DATE_FORMAT);
        Date date = null;
        try {
            date = formatter.parse(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    public  int getNumIncludeTwoHours(Date date1, Date date2) {
        try {
            GregorianCalendar g1 = new GregorianCalendar();
            GregorianCalendar g2 = new GregorianCalendar();
            g1.setTime(date1);
            g2.setTime(date2);
            return getHourInclude(g1, g2);
        } catch (Exception e) {
            return 0;
        }
    }
    private  int getHourInclude(GregorianCalendar g1, GregorianCalendar g2) {
        return getHours(g1, g2) + 1;
    }

    private  int getHours(GregorianCalendar g1, GregorianCalendar g2) {
        int elapsed = 0;
        GregorianCalendar gc1, gc2;

        if (g2.after(g1)) {
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
        } else {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }
        //gc1.clear(Calendar.DAY_OF_MONTH);
        gc1.clear(Calendar.MILLISECOND);
        gc1.clear(Calendar.SECOND);
        gc1.clear(Calendar.MINUTE);
        //gc1.clear(Calendar.HOUR_OF_DAY);

        // gc2.clear(Calendar.DAY_OF_MONTH);
        gc2.clear(Calendar.MILLISECOND);
        gc2.clear(Calendar.SECOND);
        gc2.clear(Calendar.MINUTE);
        // gc2.clear(Calendar.HOUR_OF_DAY);

        while (gc1.before(gc2)) {
            gc1.add(Calendar.HOUR_OF_DAY, 1);
            elapsed++;
        }
        return elapsed;
    }
}
