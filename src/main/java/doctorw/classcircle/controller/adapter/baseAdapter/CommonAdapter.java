package doctorw.classcircle.controller.adapter.baseAdapter;



import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
public abstract class CommonAdapter<T> extends BaseAdapter{
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int layoutId;

    public CommonAdapter(Context context, List<T> datas,int layoutId) {
        this.layoutId = layoutId;
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 抽象类，每一个实际的adapter中都不相同
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
                layoutId, position);

        // 注意这里的使用顺序
        convert(holder, getItem(position));

        return holder.getmConvertView();
    }

    public abstract void convert(ViewHolder holder, T t);

}
