package com.guang.wang.openapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wangguang.
 * Date:2016/12/1
 * Description:
 */

public class MainExListAdapter extends BaseExpandableListAdapter {

    List<String> groupNames;
    List<List<String>> childNames;

    Context mContext;
    LayoutInflater mLayoutInflater;

    public MainExListAdapter( Context context,List<String> groupNames,List<List<String>> childNames){
        mContext=context;
        this.groupNames=groupNames;
        this.childNames=childNames;
        mLayoutInflater=LayoutInflater.from(mContext);
    }
    @Override
    public int getGroupCount() {
        return groupNames.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childNames.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupNames.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childNames.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition*100+childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=mLayoutInflater.inflate(android.R.layout.simple_expandable_list_item_1,null);
            AbsListView.LayoutParams params=new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,80);
            convertView.setLayoutParams(params);
        }
        ((TextView)convertView).setText(groupNames.get(groupPosition));
        if(childNames.get(groupPosition).size()==1){
//            convertView.set
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=mLayoutInflater.inflate(android.R.layout.simple_expandable_list_item_1,null);
            AbsListView.LayoutParams params=new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100);
            convertView.setLayoutParams(params);
            convertView.setBackgroundColor(Color.YELLOW);
        }
        ((TextView)convertView).setText(childNames.get(groupPosition).get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
