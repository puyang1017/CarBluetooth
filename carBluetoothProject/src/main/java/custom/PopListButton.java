/**
 * 
 */
package custom;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.puy.carbluetooth.R;

public class PopListButton extends Button implements OnClickListener {

	// 声明上下文对象
	private Context mContext;
	// 声明PopupWindow对象，用来显示弹出框内容
	private PopupWindow mPopWindow;
	// 弹出ListView的默认选中下标
	private int mSelectedIndex = 0;
	// 声明CharSequence数组，用来保存自定义数组中的内容，需要将其中的内容添加到listDataSource中
	private CharSequence[] popListViewEntries;
	// 声明并创建List集合，用来保存弹出ListView的数据源
	List<String> listDataSource = new ArrayList<>();
	// 声明ListView视图对象
	private ListView mListView;
	
	// 设置弹出框的内边距大小
	private final int XOFFSET = 20;

	/*
	 * 声明一个PopItem选中的回调接口，
	 * 当某一条被选中时，需要通知相应的Activity做相应处理
	 */
	private OnPopItemSelectedListener mOnPopItemSelectedListener;

	public void setOnPopItemSelectedListener(OnPopItemSelectedListener mOnItemSelectedListener) {
		this.mOnPopItemSelectedListener = mOnItemSelectedListener;
	}

	public interface OnPopItemSelectedListener {

		public void onPopItemSelected(int position);
	}

	public PopListButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		// 获取在布局文件中指定的自定义属性值(jiang:entries="@array/XXX")
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PopListView);
		popListViewEntries = array.getTextArray(R.styleable.PopListView_entries);
		// 遍历获取的TypeArray， 并赋值到ListView的数据源moreList中
		for (int i = 0; i < popListViewEntries.length; i++) {
			listDataSource.add(popListViewEntries[i].toString());
		}
		// 设置Button的点击事件
		setOnClickListener(this);
		// 设置Button默认显示的文本内容
		setText(listDataSource.get(mSelectedIndex));
		// 设置Button的背景样式
		setBackgroundResource(R.drawable.btn_rec);
	}

	private void showPopupListView() {
		//如果PopupWindow为空，则重新创建并显示
		if (mPopWindow == null) {
			mPopWindow = new PopupWindow();
			mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

				@Override
				public void onDismiss() {
					setSelected(false);
				}
			});
		}
		// 初始化PopupWindow所需要显示的视图View
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupLayout = inflater.inflate(R.layout.pop_list, null);
		// 通过popupLayout找到ListView
		mListView = (ListView) popupLayout.findViewById(R.id.lv_popup_list);
		// 填充ListView并动态计算它的高度
		inflateAndMeasure(mListView);
		
		// 将ListView设置为PopupWindow的视图
		mPopWindow.setContentView(popupLayout);
		// 调整PopupWindow的宽度比Button窄一点(XOFFSET具体值可调)
		mPopWindow.setWidth(getMeasuredWidth() - (XOFFSET * 2));
		// 设置PopupWindow的高度为ListView的实际高度
		mPopWindow.setHeight((mListView.getMeasuredHeight()) * listDataSource.size());

		// 设置背景图片，不能在布局中设置，要通过代码来设置
		mPopWindow.setBackgroundDrawable(this.getResources().getDrawable(R.color.transparent));
		/*
		 * 控制popupwindow点击屏幕其他地方消失
		 * 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
		 */
		mPopWindow.setOutsideTouchable(true);
	}

	private void inflateAndMeasure(ListView listView) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.pop_list_item, listDataSource);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mSelectedIndex = position;
				mPopWindow.dismiss();
				mPopWindow = null;
				setText(listDataSource.get(position));
				if (mOnPopItemSelectedListener != null) {
					mOnPopItemSelectedListener.onPopItemSelected(position);
				}
			}
		});

		/*
		 * 调用ListView的measure方法，计算它的高度
		 * 下面一行一定要加上，否则ListView不显示
		 */
		listView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
	}

	@Override
	public void onClick(View v) {
		if (mPopWindow != null && mPopWindow.isShowing()) {
			mPopWindow.dismiss();
			mPopWindow = null;
		} else {
			showPopupListView();
			setSelected(true);
			mPopWindow.showAsDropDown(this, XOFFSET, 0);
		}
	}

	public  int dp2px(float dp) {
		final float scale = this.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

}
