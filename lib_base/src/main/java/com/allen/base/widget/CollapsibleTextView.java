package com.allen.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.android.arouter.utils.TextUtils;
import com.allen.base.R;

import java.util.ArrayList;
import java.util.List;

public class CollapsibleTextView extends AppCompatTextView {

    private int collapsedLines;//最大显示行数，其余折叠
    private String collapsedText;//折叠时后缀文字
    private String expandedText;//展开时，后缀名字
    private int suffixColor;//后缀字体颜色

    private boolean isOnDraw = false;

    final int defaultLines = 5;//默认显示5行
    final int defaultColor = Color.parseColor("#3683FF");//默认后缀颜色
    String defaultExpandedText = "收起";//默认展开时的后缀文字
    String defaultCollapsedText = "全文";//默认收起时的后缀文字
    final String tag = "...";

    String mText;//文本内容
    boolean mIsneedExpanded;//是否展开。默认收起

    private int tagsBackgroundStyle = R.drawable.base_comend_tag_shape_hot;
    private int tagTextSize = 10;//  标签的字体大小
    private String tagTextColor = "#E15756"; //   标签的字体颜色

    List<String> tags = new ArrayList<>();
    private StringBuffer content_buffer;
    OnTextClickListener onTextClickListener;//extView自身点击事件，代替onClickListener

    private CustomLinkMovementMethod customLinkMovementMethod;

    private boolean isNeedEllipsis = true;//是否需要默认的省略点“...”
    private Context mContext;

    public CollapsibleTextView(Context context) {
        super(context);
        init(context, null);
    }

    public CollapsibleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CollapsibleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化操作 ，比如获取某些属性值
     */
    private void init(final Context context, AttributeSet attrs) {
        mContext = context;

        if (attrs == null)
            return;

        //获取属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CollapsibleTextView);
        collapsedLines = typedArray.getInt(R.styleable.CollapsibleTextView_collapsedLines, defaultLines);
        collapsedText = typedArray.getString(R.styleable.CollapsibleTextView_collapsedText);
        expandedText = typedArray.getString(R.styleable.CollapsibleTextView_expandedText);
        suffixColor = typedArray.getInt(R.styleable.CollapsibleTextView_suffixColor, defaultColor);

        typedArray.recycle();

        if (TextUtils.isEmpty(collapsedText))
            collapsedText = defaultCollapsedText;
        if (TextUtils.isEmpty(expandedText))
            expandedText = defaultExpandedText;

        //获取xml中设置的文字内容
        this.mText = getText() == null ? null : getText().toString();
//        //使TextView支持局部点击--ClickSpan。【如果不需要对Textview本身以及上层bujurong布局容器进行点击事件，直接添加该句即可。】
//       setMovementMethod(LinkMovementMethod.getInstance());

        //若还需要对TextView的父容器进行点击事件设置，需要判断点击区域有没有clickspan，有自身消费，无则交由上层viewGroup处理。
        customLinkMovementMethod = CustomLinkMovementMethod.getInstance();
        setMovementMethod(customLinkMovementMethod);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //由于 getLineCount() 等函数只有在 layout 过程之后值才有意义,所以要合理的选择 updateUI 的时机

        if (isOnDraw)
            return;

        if (getLineCount() > collapsedLines) {
            isOnDraw = true;
            updateUI(mIsneedExpanded);
        } else if (tags.size() > 0) {
            isOnDraw = true;
            if (getLineCount() <= collapsedLines) {
                addClickListnear();
            } else {
                updateUI(mIsneedExpanded);
            }
        } else if (getLineCount() <= collapsedLines) {
            isOnDraw = true;
            addClickListnear();
        }
    }

    private CenterImageSpan createTag(String item) {
        //  设置标签的布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.base_item_tag_commend, null);
        TextView tv_tag = view.findViewById(R.id.tvTag);
        tv_tag.setText(item);
        tv_tag.setTextSize(tagTextSize);
        tv_tag.setTextColor(Color.parseColor(tagTextColor));

        //  设置背景样式
        tv_tag.setBackgroundResource(tagsBackgroundStyle);

        Bitmap bitmap = convertViewToBitmap(view);
        Drawable drawable = new BitmapDrawable(bitmap);
        drawable.setBounds(0, 0, tv_tag.getWidth(), tv_tag.getHeight());

        return new CenterImageSpan(drawable);
    }

    public void clearTags() {
        if (null == tags || tags.isEmpty())
            return;
        tags.clear();
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void addTag() {
        content_buffer = new StringBuffer();
        int size = 0;
        if (tags.size() > 0) {
            size = tags.get(0).length();
            content_buffer.append(tags.get(0));
        }

        content_buffer.append(mText);
        SpannableString str = new SpannableString(content_buffer);
        for (int i = 0; i < tags.size(); i++) {
            String item = tags.get(i);
            str.setSpan(createTag(item), 0, size, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

        //设置自身点击事件【避免与自身点击事件冲突，采用剩余部位点击事件实现】
        if (onTextClickListener != null) {//一定要判断。【一般自身点击与父容器点击只存其一。如果不判断，相当于整个TextView均含clickspan，不会再响应父容器点击事件】
            str.setSpan(selfClickSpan,
                    0,
                    mText.length(),
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        post(new Runnable() {
            @Override
            public void run() {
                setText(str);
            }
        });
    }

    private void addClickListnear() {
        content_buffer = new StringBuffer();
        int size = 0;
        if (tags.size() > 0) {
            size = tags.get(0).length();
            content_buffer.append(tags.get(0));
        }

        content_buffer.append(mText);
        SpannableString str = new SpannableString(content_buffer);
        for (int i = 0; i < tags.size(); i++) {
            String item = tags.get(i);
            str.setSpan(createTag(item), 0, size, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

        if (onTextClickListener != null) {//一定要判断。【一般自身点击与父容器点击只存其一。如果不判断，相当于整个TextView均含clickspan，不会再响应父容器点击事件】
            str.setSpan(selfClickSpan,
                    0,
                    mText.length() + size,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        post(new Runnable() {
            @Override
            public void run() {
                setText(str);
            }
        });
    }

    /**
     * 更新文本显示方式：展开 or 收起
     *
     * @param mIsneedExpanded 是否需要展开
     */
    private void updateUI(boolean mIsneedExpanded) {
        if (TextUtils.isEmpty(mText))
            return;

        int endIndex = 0;
        int startIndex = 1;
        String suffix, temp = mText;
        content_buffer = new StringBuffer();

        int tagSize = 0;
        for (String item : tags) {
            tagSize += item.length();
            content_buffer.append(item);
        }

        if (mIsneedExpanded) {//展开
            suffix = expandedText;
        } else { //收起
            suffix = collapsedText;

            if (collapsedLines < 1) {
                throw new RuntimeException("CollapsedLines must larger than 0");
            }

            int lineEnd = getLayout().getLineVisibleEnd(collapsedLines - 1);//第 mCollapsedLines 行打上省略点+后缀【padding不计算在内】
            /**
             UTF8编码：中文占3个字节，英文占用1个字节。
             所以，“...”占用3个字节，也就是只占一个中文的宽度，因而计算字数时不能- 3（即tag.length()），而是-1，也就是下面-1的由来
             */
            if (isNeedEllipsis) {//需要默认省略点
                int newEnd = lineEnd - 1 - suffix.length() - tagSize;//不能- tagName.length()，实际占空间并不是三个字的大小，而是一个中文大小
                int end = newEnd > 0 ? newEnd : lineEnd;
                temp = temp.substring(0, end) + tag;
            } else {
                int newEnd = lineEnd - suffix.length();//不需要减去省略点大小
                int end = newEnd > 0 ? newEnd : lineEnd;
                temp = temp.substring(0, end);
            }
        }

        content_buffer.append(temp + suffix);
        final SpannableString str = new SpannableString(content_buffer);
        for (int i = 0; i < tags.size(); i++) {
            String item = tags.get(i);
            endIndex += item.length();
            str.setSpan(createTag(item), startIndex - 1, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            startIndex += item.length();
        }

        int end = temp.length() + suffix.length() + tagSize;
        //设置后缀点击事件
        str.setSpan(mClickSpanListener,
                temp.length() + tagSize,
                end,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置后缀文字颜色
        str.setSpan(new ForegroundColorSpan(suffixColor),
                temp.length() + tagSize,
                end,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);


        //设置自身点击事件【避免与自身点击事件冲突，采用剩余部位点击事件实现】
        if (onTextClickListener != null) {//一定要判断。【一般自身点击与父容器点击只存其一。如果不判断，相当于整个TextView均含clickspan，不会再响应父容器点击事件】
            str.setSpan(selfClickSpan,
                    0,
                    temp.length(),
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        post(new Runnable() {
            @Override
            public void run() {
                setText(str);
            }
        });
    }


    /**
     * 使用setFullString代替setText
     *
     * @param str
     */
    public void setFullString(String str) {
        this.mText = str;
        isOnDraw = false;
        setText(mText);
    }

    public String getFullString() {
        return mText;
    }

    /**
     * 设置后缀文字颜色
     *
     * @param color
     */
    public void setSuffixColor(@ColorInt int color) {
        suffixColor = color;
    }

    /**
     * 设置收起时的最大显示行数
     *
     * @param collapsedLines
     */
    public void setCollapsedLines(int collapsedLines) {
        this.collapsedLines = collapsedLines;
    }

    /**
     * 设置收起时的后缀文字，eg.“展开”
     *
     * @param collapsedText
     */
    public void setCollapsedText(String collapsedText) {
        this.collapsedText = collapsedText;
    }

    /**
     * 设置展开时的后缀文字，eg.“收起”
     *
     * @param expandedText
     */
    public void setExpandedText(String expandedText) {
        this.expandedText = expandedText;
    }

    /**
     * 是否需要省略点“...”
     *
     * @param isNeedEllipsis
     */
    public void isNeedEllipsis(boolean isNeedEllipsis) {
        this.isNeedEllipsis = isNeedEllipsis;
    }

    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }

    public void setDefaultExpandedText(String defaultExpandedText) {
        this.defaultExpandedText = defaultExpandedText;
    }

    public void setDefaultCollapsedText(String defaultCollapsedText) {
        this.defaultCollapsedText = defaultCollapsedText;
    }

    /**
     * TextView自身点击事件(除后缀外的部分)
     */
    ClickableSpan selfClickSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            if (onTextClickListener != null) {
                onTextClickListener.onTextClick(null);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }
    };

    /**
     * 局部点击事件: 展开 or 收起
     */
    ClickableSpan mClickSpanListener = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            mIsneedExpanded = !mIsneedExpanded;
            updateUI(mIsneedExpanded);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
//            super.updateDrawState(ds);
            ds.setUnderlineText(false);//点击区域下方是否有下划线。默认true，会像超链接一样显示下划线
            setHighlightColor(Color.TRANSPARENT);//取消选中文字背景色高亮显示
        }
    };

    public interface OnTextClickListener {
        void onTextClick(String content);
    }

    private static Bitmap convertViewToBitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
