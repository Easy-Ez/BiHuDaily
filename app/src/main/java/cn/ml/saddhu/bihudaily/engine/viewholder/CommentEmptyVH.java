package cn.ml.saddhu.bihudaily.engine.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.util.UIUtils;

/**
 * Created by sadhu on 2017/4/2.
 * Email static.sadhu@gmail.com
 * Describe: 长评为空 vh
 */
public class CommentEmptyVH extends RecyclerView.ViewHolder {

    public CommentEmptyVH(View itemView) {
        super(itemView);
        RelativeLayout comment_item_empty = (RelativeLayout) itemView.findViewById(R.id.comment_item_empty);
        ViewGroup.LayoutParams layoutParams = comment_item_empty.getLayoutParams();
        layoutParams.height = UIUtils.getContentHeight(itemView.getContext()) - (itemView.getContext().getResources().getDimensionPixelSize(R.dimen.list_header_height) * 2) ;
        comment_item_empty.setLayoutParams(layoutParams);
    }
}
