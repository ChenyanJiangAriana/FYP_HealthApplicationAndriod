package com.jk.dayu.jkapp.Interrogation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jk.dayu.jkapp.UserModule.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作类
 * 
 * @author admin
 * 
 */
public class DBManager {

	private Context context;
	private SQLiteDatabase database;

	public DBManager(Context context) {
		this.context = context;
	}

	/**
	 * 打开数据库，如果不存在则创建一个数据库
	 */
	public void openDB() {
		DBHelper dbHelper = new DBHelper(context);
		if (database == null || !database.isOpen()) {
			database = dbHelper.getWritableDatabase();
		}
	}

	/**
	 * 关闭数据库
	 */
	public void closeDB() {
		if (database != null && database.isOpen()) {
			database.close();
		}
	}


	/**
	 * 用户注册
	 */
	public UserBean addUser(UserBean user){
        ContentValues newValues = new ContentValues();

        newValues.put("username",user.getUsername());
        newValues.put("password",user.getPassword());
		newValues.put("age",user.getAge());
		newValues.put("sex",user.getSex());
		newValues.put("role",user.getRole());
		long res = database.insert(Constant.DB_USERS,null,newValues);

		if (res<=0){
			return null;
		}else{
			return userLogin(user);
		}

	}


    /**
     * 用户登录
     */
    public UserBean userLogin(UserBean user){

        UserBean loginUser = null;
        Cursor result = database.query(Constant.DB_USERS,null,"username=? and password=? and role=?",
                new String[]{user.getUsername(),user.getPassword(),user.getRole()+""},null,null,null);
        if(result.moveToFirst()){
            if(result.getColumnCount()>0){
                loginUser = new UserBean();
                loginUser.setId("555");
                //loginUser.setId(result.getInt(result.getColumnIndex("id")));
                loginUser.setUsername(result.getString(result.getColumnIndex("username")));
                loginUser.setPassword(result.getString(result.getColumnIndex("password")));
				loginUser.setRole(result.getString(result.getColumnIndex("role")));
				loginUser.setAge(result.getString(result.getColumnIndex("age")));
				loginUser.setSex(result.getString(result.getColumnIndex("sex")));
            }
        }
        return loginUser;
    }


	/**
	 * 根据ID查找用户信息
	 * @return
	 */
	public UserBean getUserById(int id){
		UserBean u = null;
		Cursor result = database.query(Constant.DB_USERS,null,"id=? and role=0",
				new String[]{id+""},null,null,null);
		if(result.moveToFirst()){
			if(result.getColumnCount()>0){
				u = new UserBean();
				//u.setId(result.getInt(result.getColumnIndex("id")));
				u.setId("dd");
				u.setUsername(result.getString(result.getColumnIndex("username")));
				u.setPassword(result.getString(result.getColumnIndex("password")));
				u.setRole(result.getString(result.getColumnIndex("role")));
				u.setAge(result.getString(result.getColumnIndex("age")));
				u.setSex(result.getString(result.getColumnIndex("sex")));
			}
		}
		return u;
		
		
	}

	/**
	 * 症状信息插入
	 */
	public long addConditio(Condition c){

		openDB();
		ContentValues newValues = new ContentValues();

		newValues.put("symptoms",c.getSymptoms());
		newValues.put("time",c.getTime());
		newValues.put("detial",c.getDetails());
		newValues.put("userid",c.getUid());


		return database.insert(Constant.DB_CONDITION,null,newValues);

	}


	/**
	 * 根据ID查找病症信息
	 */
	public Condition getConditionById(int id){
		Condition c = null;
		String[] args = {id+""};
		Cursor result = database.query(Constant.DB_CONDITION,null,"id="+id,null,null,null,null);
		if(result.moveToFirst()) {
			if (result.getColumnCount() > 0) {
				c = new Condition();
				c.setCid(result.getInt(result.getColumnIndex("id")));
				c.setSymptoms(result.getString(result.getColumnIndex("symptoms")));
				c.setTime(result.getString(result.getColumnIndex("time")));
				c.setUid(result.getLong(result.getColumnIndex("userid")));
				c.setDetails(result.getString(result.getColumnIndex("detial")));
			}
		}
		return c;
	}

    /**
	 * 根据用户ID病症信息查看
	 */
	public List<Condition> getConditionsByUserId(int id){
		List<Condition> dataList = null;
		String[] args = {id+""};
		Cursor result = database.query(Constant.DB_CONDITION,null,"userid="+id,null,null,null,null);
		if(result!=null){
			dataList = new ArrayList<Condition>();
			while(result.moveToNext()){
				Condition c = new Condition();
				c.setCid(result.getInt(result.getColumnIndex("id")));
				c.setSymptoms(result.getString(result.getColumnIndex("symptoms")));
				c.setTime(result.getString(result.getColumnIndex("time")));
				c.setUid(result.getLong(result.getColumnIndex("userid")));
				c.setDetails(result.getString(result.getColumnIndex("detial")));
				dataList.add(c);
			}
		}

		return dataList;
	}

	/**
	 * 所有病症信息查看
	 */
	public List<Condition> getConditions(){
		List<Condition> dataList = null;
		Cursor result = database.query(Constant.DB_CONDITION,null,null,null,null,null,null,null);
		if(result!=null){
			dataList = new ArrayList<Condition>();
			while(result.moveToNext()){
				Condition c = new Condition();
				c.setCid(result.getInt(result.getColumnIndex("id")));
				c.setSymptoms(result.getString(result.getColumnIndex("symptoms")));
				c.setTime(result.getString(result.getColumnIndex("time")));
				c.setUid(result.getLong(result.getColumnIndex("userid")));
				c.setDetails(result.getString(result.getColumnIndex("detial")));
				dataList.add(c);
			}
		}

		return dataList;
	}




	/**
	 * 评论添加
	 */
	public long addReply(Reply reply){
		ContentValues newValues = new ContentValues();

		newValues.put("content",reply.getContent());
		newValues.put("uid",reply.getUid());
		newValues.put("cid",reply.getCid());

		return database.insert(Constant.DB_REPLY,null,newValues);
	}


	/**
	 * 查找所有回复信息
	 */
	public List<Reply> getReplys(){
		List<Reply> dataList = null;
		Cursor result = database.query(Constant.DB_REPLY,null,null,null,null,null,null,null);
		if(result!=null){
			dataList = new ArrayList<Reply>();
			while(result.moveToNext()){
				Reply r = new Reply();
				r.setRid(result.getInt(result.getColumnIndex("id")));
				r.setCid(result.getInt(result.getColumnIndex("cid")));
				r.setUid(result.getInt(result.getColumnIndex("uid")));
				r.setContent(result.getString(result.getColumnIndex("content")));
				dataList.add(r);
			}
		}

		return dataList;
	}


	/**
	 * 根据用户ID查找回复信息
	 */
	public List<Reply> getReplysByUid(int uid){
		List<Reply> dataList = null;
		Cursor result = database.query(Constant.DB_REPLY,null,"uid="+uid,null,null,null,null);
		if(result!=null){
			dataList = new ArrayList<Reply>();
			while(result.moveToNext()){
				Reply r = new Reply();
				r.setRid(result.getInt(result.getColumnIndex("id")));
				r.setCid(result.getInt(result.getColumnIndex("cid")));
				r.setUid(result.getInt(result.getColumnIndex("uid")));
				r.setContent(result.getString(result.getColumnIndex("content")));
				dataList.add(r);
			}
		}

		return dataList;
	}

}
