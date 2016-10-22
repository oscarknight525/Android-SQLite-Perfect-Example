package com.mobile.lance.iconnect.iConnect.myPost;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.iConnect.friends.friendsviewposts.CustomCommentsAdapter;
import com.mobile.lance.iconnect.utils.AppConstants;
import com.mobile.lance.iconnect.utils.DBCommentManager;
import com.mobile.lance.iconnect.utils.TableCommentsItem;
import com.mobile.lance.iconnect.utils.TablePostsItem;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by administrator on 7/18/16.
 */
public class CustomMyViewPostAdapter extends BaseAdapter {

    private ArrayList<TablePostsItem> friendsviewpost;
    private ArrayList<TableCommentsItem> commentsList;
    private ArrayList<ArrayList<TableCommentsItem>> arrayLists;
    private ArrayList<CommentData> templist;
    private List<Long> commentidList;
    LayoutInflater inflater;
    Activity act;

    private DBCommentManager comment_db;
    private TableCommentsItem comments_item;

    long my_user_id;
    long comment_id;
    long post_id;
    private CustomCommentsAdapter commentAdapter;
    private boolean commented;
    private long update_comment_id;
    private int update_comment_index;

    String picturePath;
    Bitmap thumbnail;
    Bitmap profileImage;
    public CustomMyViewPostAdapter(Activity act, ArrayList<TablePostsItem> friendsviewpost) {
        this.friendsviewpost = friendsviewpost;
        inflater = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.act = act;
        commented = false;
        comment_db = new DBCommentManager(act);
        arrayLists = new ArrayList<>();
        templist = new ArrayList<>();
        commentidList = new ArrayList<>();
        for(int i=0; i<friendsviewpost.size(); i++) {
            CommentData data = new CommentData();
            templist.add(data);
            post_id = friendsviewpost.get(i).getId();
            commentsList = new ArrayList<>();
            try {
                comment_db.open();
                commentidList = comment_db.getCommentIDs(post_id);
                for (int j = 0; j < commentidList.size(); j++) {
                    comment_id = commentidList.get(j);
                    TableCommentsItem item = comment_db.getCommentInfo(comment_id);
                    commentsList.add(item);
                }
                arrayLists.add(commentsList);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        SharedPreferences prefs = act.getSharedPreferences("UserData", act.MODE_PRIVATE);
        String userid_loggedin = prefs.getString("userid", null);
        my_user_id = Long.parseLong(userid_loggedin);
    }

    @Override
    public int getCount() {
        return friendsviewpost.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        ViewHolder holder;
        holder = new ViewHolder();
        TablePostsItem friendsviewpostcell = friendsviewpost.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        convertView = null;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.activity_friends_view_post_cell,null);
            holder.tvDate = (TextView) convertView.findViewById(R.id.txtDate);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.txtDescription);
            holder.imgPost = (ImageView) convertView.findViewById(R.id.img_friend_postphoto);
            holder.btn_makecomment = (Button) convertView.findViewById(R.id.btn_makecomment);
            holder.edt_comment = (EditText) convertView.findViewById(R.id.edt_comment);
            holder.btn_sendcomment = (Button) convertView.findViewById(R.id.btn_sendcomment);
            holder.list_comments = (ListView) convertView.findViewById(R.id.list_comments);
            holder.layout_comment = (LinearLayout) convertView.findViewById(R.id.layout_make_comment);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
//        final LinearLayout layout_comment = (LinearLayout) convertView.findViewById(R.id.layout_make_comment);

        holder.btn_makecomment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                templist.get(position).visible = true;
                notifyDataSetChanged();
            }
        });

        holder.btn_sendcomment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               if(templist.get(position).comment.isEmpty()){
                   AppConstants.alertShow(act, "Alert", "Please input comments");
               }
               else {
                   try {
                       comment_db.open();
                       for(int i=0; i < arrayLists.get(position).size(); i++){
                           if(my_user_id == arrayLists.get(position).get(i).getComment_user_id()){
//                               commented = true;
                               update_comment_id = arrayLists.get(position).get(i).getId();
                               update_comment_index = i;
                               break;
                           }
                       }
//                       if(commented){
//                           comments_item = new TableCommentsItem();
//                           comments_item.setComment(templist.get(position).comment);
//                           comments_item.setComment_updated_at(getCurrentDate());
//
//                           arrayLists.get(position).get(update_comment_index).setComment(templist.get(position).comment);
//                           comment_db.updateCommentInfo(update_comment_id, comments_item);
//                       }
//                       else {
                           comments_item = new TableCommentsItem();
                           comments_item.setComment_post_id(friendsviewpost.get(position).getId());
                           comments_item.setComment(templist.get(position).comment);
                           comments_item.setComment_user_id(my_user_id);
                           comments_item.setComment_created_at(getCurrentDate());

                           arrayLists.get(position).add(comments_item);
                           comment_db.insertComment(comments_item);
//                       }

//                       commentAdapter.notifyDataSetChanged();
                       act.finish();
                       act.startActivity(act.getIntent());
                       comment_db.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }
            }
        });

        holder.edt_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                templist.get(position).comment = s.toString();
            }
        });

        holder.tvDate.setText(friendsviewpostcell.getPost_created_at());
        holder.tvDescription.setText(friendsviewpostcell.getPost());
        holder.edt_comment.setText(templist.get(position).comment);

        if(!friendsviewpostcell.getPostImagePath().equals("")){
            picturePath = friendsviewpostcell.getPostImagePath();
            File file = new File(picturePath);
            if(file.exists()) {
                try {
                    thumbnail = (BitmapFactory.decodeFile(picturePath));
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (OutOfMemoryError e) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 10;
                    thumbnail = (BitmapFactory.decodeFile(picturePath, options));
                }
                profileImage = Bitmap.createScaledBitmap(thumbnail, 200, 200, true);
                holder.imgPost.setImageBitmap(profileImage);
            }
            else{
                holder.imgPost.setVisibility(View.GONE);
            }
        }
        else{
            holder.imgPost.setVisibility(View.GONE);
        }

        if(templist.get(position).visible){
            holder.layout_comment.setVisibility(View.VISIBLE);
        }
        else {
            holder.layout_comment.setVisibility(View.GONE);
        }

        commentAdapter = new CustomCommentsAdapter(act, arrayLists.get(position));
        holder.list_comments.setAdapter(commentAdapter);
        float height = AppConstants.Screen_Density * 50 * arrayLists.get(position).size();
        holder.list_comments.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)height));

        return convertView;
    }

    private  static String  getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

    class ViewHolder {
        TextView tvDate;
        TextView tvDescription;
        ImageView imgPost;
        Button btn_makecomment;
        LinearLayout layout_comment;
        EditText edt_comment;
        Button btn_sendcomment;
        ListView list_comments;
    }

    class CommentData{
        public String comment;
        public boolean visible;
        public CommentData(){
            comment = "";
            visible = false;
        }
    }


}
