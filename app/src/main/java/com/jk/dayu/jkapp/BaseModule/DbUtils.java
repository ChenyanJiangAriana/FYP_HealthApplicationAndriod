package com.jk.dayu.jkapp.BaseModule;

import android.content.Context;
import com.jk.dayu.jkapp.untils.StringUtil;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;
import java.util.List;

public class DbUtils {

    public static LiteOrm liteOrm;

    public static void createDb(Context _activity) {
        if (liteOrm == null) {
            liteOrm = LiteOrm.newCascadeInstance(_activity, "health.db");
            liteOrm.setDebugged(true);
        }
    }
    //万能查询 inquiry
    public static <T> List<T> getQueryByWhere(Class<T> tClass, List<String> queryParamList,List<String> valueList) {
        if (queryParamList.size()!=valueList.size()){
            System.out.println(queryParamList);
            System.out.println(valueList);
            throw new RuntimeException("Request parameter key-value pairs do not match");
        }
        for (int i = 0; i <queryParamList.size() ; i++) {
            if (StringUtil.isEmpty(queryParamList.get(i)) || StringUtil.isEmpty(valueList.get(i))){
                throw new RuntimeException("Missing key or value for the corresponding request parameter");
            }
        }
        String querySql ="1=1";
        for (String queryParam:queryParamList){
            if (!StringUtil.isEmpty(queryParam)){
                querySql+=" and "+queryParam+" = ? ";
            }
        }
        return liteOrm.query(new QueryBuilder<T>(tClass).where(querySql, valueList.toArray(new String[valueList.size()])));
    }
    /**
     * 仅在以存在时更新
     * @param t
     */
    public static <T> void update(T t) {
        liteOrm.update(t, ConflictAlgorithm.Replace);
    }

    /**
     * 插入一条记录
     *
     * @param t
     */
    public static <T> void insert(T t) {
        liteOrm.save(t);
    }

    /**
     * 插入所有记录
     *
     * @param list
     */
    public static <T> void insertAll(List<T> list) {
        liteOrm.save(list);
    }

    /**
     * 查询所有
     *
     * @param cla
     * @return
     */
    public static <T> List<T> getQueryAll(Class<T> cla) {
        return liteOrm.query(cla);
    }

    /**
     * 查询  某字段 等于 Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public static <T> List<T> getQueryByWhere(Class<T> cla, String field, String[] value) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", value));
    }

    public static <T> List<T> getQueryByWhere2(Class<T> cla, String field1, String field2, String[] value) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field1 + "=? and "+ field2 +"=?", value));
    }

    /**
     * 查询  某字段 等于 Value的值  可以指定从1-20，就是分页
     *
     * @param cla
     * @param field
     * @param value
     * @param start
     * @param length
     * @return
     */
    public static <T> List<T> getQueryByWhereLength(Class<T> cla, String field, String[] value, int start, int length) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", value).limit(start, length));
    }

    /**
     * 删除所有 某字段等于 Vlaue的值
     * @param cla
     * @param field
     * @param value
     */
//        public static <T> void deleteWhere(Class<T> cla,String field,String [] value){
//            liteOrm.delete(cla, WhereBuilder.create().where(field + "=?", value));
//        }

    /**
     * delete all
     *
     * @param cla
     */
    public static <T> void deleteAll(Class<T> cla) {
        liteOrm.deleteAll(cla);
    }

    /**
     * 删除所有 某字段等于 Vlaue的值
     *
     * @param cla
     * @param field
     * @param value
     */
    public static <T> int deleteWhere(Class<T> cla, String field, String[] value) {
        return liteOrm.delete(cla, new WhereBuilder(cla).where(field + "=?", value));
    }
    public static <T> void updateALL(List<T> list) {
        liteOrm.update(list);
    }

    public static void closeDb() {
        liteOrm.close();
    }

}
