package com.example.recyclerviewloadmore;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by WEI JUNHAO on 2017/1/10.
 */

public class RecyclerLayoutManager extends RecyclerView.LayoutManager {


    private int totalHeight = 0;
    private int verticalScrollOffset = 0;

    private SparseArray<Rect> allItemFrames = new SparseArray<>();
    private SparseBooleanArray hasAttachedItems = new SparseBooleanArray();
    private int horizontalSpace;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        if (getItemCount() <= 0) return;
        if (state.isPreLayout()) {
            return;
        }

        detachAndScrapAttachedViews(recycler);

        //定义竖直方向的偏移量
        int offsetY = 0;
        totalHeight = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);

            //对子view进行测量
            measureChildWithMargins(view, 0, 0);
            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);

            Rect frame = allItemFrames.get(i);
            if (frame == null) {
                frame = new Rect();
            }
            frame.set(0, offsetY, width, offsetY + height);
            allItemFrames.put(i, frame);
            hasAttachedItems.put(i, false);
            //layoutDecorated(view, 0, offsetY, width, offsetY + height);

            offsetY += height;
            totalHeight += height;
        }
        totalHeight = Math.max(totalHeight, getVerticalSpace());
        recyclerAndFillItems(recycler, state);
    }

    private void recyclerAndFillItems(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            return;
        }

        Rect displayFrame = new Rect(0, verticalScrollOffset, getHorizontalSpace(),
                verticalScrollOffset + getVerticalSpace());

        Rect childFrame = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childFrame.left = getDecoratedLeft(child);
            childFrame.top = getDecoratedTop(child);
            childFrame.right = getDecoratedRight(child);
            childFrame.bottom = getDecoratedBottom(child);
            if (!Rect.intersects(displayFrame, childFrame)) {
                removeAndRecycleView(child, recycler);
            }
        }

        for (int i = 0; i < getItemCount(); i++) {
            if (Rect.intersects(displayFrame, allItemFrames.get(i))) {
                View scrap = recycler.getViewForPosition(i);
                measureChildWithMargins(scrap, 0, 0);
                addView(scrap);

                Rect frame = allItemFrames.get(i);
                layoutDecorated(scrap, frame.left, frame.top - verticalScrollOffset,
                        frame.right, frame.bottom - verticalScrollOffset);
            }
        }

    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int travel = dy;

        if (verticalScrollOffset + dy < 0) {
            travel = -verticalScrollOffset;
        } else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) {
            travel = totalHeight - getVerticalSpace() - verticalScrollOffset;
        }

        verticalScrollOffset += travel;
        offsetChildrenVertical(-travel);
        recyclerAndFillItems(recycler, state);
        Log.i("bonc","childCount:" + getChildCount());
        return travel;
    }

    public int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
}
