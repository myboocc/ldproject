package it.ldlife.base;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import it.ldlife.util.Page;

public interface MongoBaseDao<T> {
	//添加  
//    public void insert(T entity);    
//    //根据条件查找  
//    public T findOne(Map<String,Object> params,String collectionName);    
//    //查找所有  
//    public List<T> findAll(Map<String,Object> params,String collectionName);    
//    //修改  
//    public void update(Map<String,Object> params,String collectionName);   
//    //创建集合  
//    public void createCollection(String collectionName);  
//    //根据条件删除  
//    public void remove(Map<String,Object> params,String collectionName);  
    
    /** 
     * 插入 
     */  
    public T save(T entity);  
  
    /** 
     * 根据ID查询 
     */  
    public T findById(String id);  
  
    /** 
     * 通过ID获取记录,并且指定了集合名(表的意思) 
     */  
    public T findById(String id, String collectionName);  
  
    /** 
     * 获得所有该类型记录 
     */  
    public List<T> findAll();  
  
    /** 
     * 获得所有该类型记录,并且指定了集合名(表的意思) 
     */  
    public List<T> findAll(String collectionName);  
  
    /** 
     * 根据条件查询 
     */  
    public List<T> find(Query query);  
  
    /** 
     * 根据条件查询一个 
     */  
    public T findOne(Query query);  
  
    /** 
     * 分页查询 
     */  
    public Page<T> findPage(Page<T> page, Query query);  
  
    /** 
     * 根据条件 获得总数 
     */  
    public long count(Query query);  
  
    /** 
     * 根据条件 更新 
     */  
    public WriteResult update(Query query, Update update);  
  
    /** 
     * 更新符合条件并sort之后的第一个文档 并返回更新后的文档 
     */  
    public T updateOne(Query query, Update update);  
  
    /** 
     * 根据传入实体ID更新 
     */  
    public WriteResult update(T entity);  
  
    /** 
     * 根据条件 删除 
     *  
     * @param query 
     */  
    public void remove(Query query);
}
