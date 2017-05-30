package it.ldlife.mongo.dao.impl;

import it.ldlife.base.MongoBaseDaoImpl;
import it.ldlife.mongo.dao.UserDao;
import it.ldlife.pojo.User;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by xubinhui on 17-5-17.
 */
@Repository("userDaoImpl")
public class UserDaoImpl extends MongoBaseDaoImpl<User> implements UserDao {

	@Override
	public int checkUserName(String username) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("username").is(username));
    	User user = this.mongoTemplate.findOne(query, this.getEntityClass());
    	if(user != null){
    		return 1;
    	}
		return 0;
	}
	
	@Override
	public User selectLogin(String username, String md5Password) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("username").is(username).and("password").is(md5Password));
    	User user = this.mongoTemplate.findOne(query, this.getEntityClass());
		return user;
	}

	@Override
	public int checkEmail(String email) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("email").is(email));
    	User user = this.mongoTemplate.findOne(query, this.getEntityClass());
    	if(user != null){
    		return 1;
    	}
		return 0;
	}

	@Override
	public User selectQuestionByUserName(String userName) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("username").is(userName));
    	User user = this.mongoTemplate.findOne(query, this.getEntityClass());
		return user;
	}

	@Override
	public boolean checkAnswer(String username, String question, String answer) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username).and("question").is(question).and("answer").is(answer));
		return this.mongoTemplate.exists(query, this.getEntityClass());
	}

	@Override
	public int updatePasswordByUsername(String username, String md5Password) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		Update update = new Update();
		update.set("password", md5Password);
		return this.mongoTemplate.updateFirst(query, update, this.getEntityClass()).getN();
	}

	@Override
	public int checkPassword(String passwordOld, String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id).and("password").is(passwordOld));
		User user = this.mongoTemplate.findOne(query, this.getEntityClass());
		if(user != null){
			return 1;
		}
		return 0;
	}

	@Override
	public int checkEmailByUserId(String email, String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email).and("_id").ne(id));
		User user = this.mongoTemplate.findOne(query, this.getEntityClass());
		if(user != null){
			return 1;
		}
		return 0;
	}

	@Override
	public int updateUserInfo(User updateUser) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(updateUser.getId()));
		Update update = new Update();
		update.set("email", updateUser.getEmail());
		update.set("phone", updateUser.getPhone());
		update.set("question", updateUser.getQuestion());
		update.set("answer", updateUser.getAnswer());
		return this.mongoTemplate.updateFirst(query, update, this.getEntityClass()).getN();
	}

	
}
