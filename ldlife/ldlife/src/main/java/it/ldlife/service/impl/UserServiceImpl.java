package it.ldlife.service.impl;

import it.ldlife.common.Const;
import it.ldlife.common.ServiceResponse;
import it.ldlife.common.TokenCache;
import it.ldlife.mongo.dao.UserDao;
import it.ldlife.pojo.User;
import it.ldlife.service.IUserService;
import it.ldlife.util.MD5Util;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.UUID;

import javax.annotation.Resource;

/**
 * Created by xubinhui on 17-5-16.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Resource
    private UserDao userDao;

    @Override
    public ServiceResponse<User> login(String username, String password) {
        //判断用户名是否存在
        int resultCount = userDao.checkUserName(username);
        if(resultCount == 0){
            return ServiceResponse.createByErrorMessage("用户名不存在");
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userDao.selectLogin(username,md5Password);
        if (user == null){
            return ServiceResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServiceResponse.createBySuccess("登陆成功",user);
    }

    @Override
    public ServiceResponse<String> register(User user) {
    	ServiceResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
    	if(!validResponse.isSuccess()){
    		return validResponse;
    	}
    	validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
    	if(!validResponse.isSuccess()){
    		return validResponse;
    	}
    	user.setRole(Const.Role.ROLE_CUSTOMER);
    	//md5加密
    	user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
    	User saveUser = userDao.save(user);
    	if(saveUser.getId() == null){
    		return ServiceResponse.createByErrorMessage("注册失败");
    	}
    	return ServiceResponse.createBySuccessMessage("注册成功");
    }

	@Override
	public int checkUserName(String userName) {
		return userDao.checkUserName(userName);
	}

	@Override
	public int checkEmail(String email) {
		return userDao.checkEmail(email);
	}

	@Override
	public ServiceResponse<String> checkValid(String str, String type) {
		if(StringUtils.isNotBlank(type)){
    		if(Const.USERNAME.equals(type)){
    			int resultCount = userDao.checkUserName(str);
    			if(resultCount > 0){
    				return ServiceResponse.createByErrorMessage("用户名已存在");
    			}
    		}
    		if(Const.EMAIL.equals(type)){
    			int resultCount = userDao.checkEmail(str);
    			if(resultCount > 0){
    				return ServiceResponse.createByErrorMessage("Email已存在");
    			}
    		}
    	}else{
    		return ServiceResponse.createByErrorMessage("参数错误");
    	}
		return ServiceResponse.createBySuccessMessage("校验成功");
	}

	@Override
	public ServiceResponse<String> selectQuestion(String userName) {
		ServiceResponse validResponse = this.checkValid(userName, Const.USERNAME);
		if(validResponse.isSuccess()){
			//用户名不存在
			return ServiceResponse.createByErrorMessage("用户名不存在");
		}
		//查询问题
		User user = userDao.selectQuestionByUserName(userName);
		if(StringUtils.isNotBlank(user.getQuestion())){
			return ServiceResponse.createBySuccessMessage(user.getQuestion());
		}
		return ServiceResponse.createByErrorMessage("找回密码的问题是空的");
	}

	@Override
	public ServiceResponse<String> checkAnswer(String username, String question, String answer) {
		boolean result = userDao.checkAnswer(username,question,answer);
		if(result){
			// 正确
			String forgetToken = UUID.randomUUID().toString();
			TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
			return ServiceResponse.createBySuccess(forgetToken);
		}
		return ServiceResponse.createByErrorMessage("问题的答案错误");
	}

	@Override
	public ServiceResponse<String> forgetResetPassword(String username, String forgetToken, String passwordNew) {
		if(StringUtils.isBlank(forgetToken)){
			return ServiceResponse.createByErrorMessage("参数错误，token为空");
		}
		ServiceResponse validResponse = this.checkValid(username, Const.USERNAME);
		if(validResponse.isSuccess()){
			//用户名不存在
			return ServiceResponse.createByErrorMessage("用户名不存在");
		}
		String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
		if(StringUtils.isBlank(token)){
			return ServiceResponse.createByErrorMessage("token无效或者过期");
		}
		if(StringUtils.equals(token, forgetToken)){
			String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
			int rowCount = userDao.updatePasswordByUsername(username,md5Password);
			if(rowCount > 0){
				return ServiceResponse.createBySuccessMessage("修改密码成功");
			}
		}else{
			return ServiceResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
		}
		return ServiceResponse.createByErrorMessage("修改密码失败");
	}

	@Override
	public ServiceResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
		int resultCount = userDao.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
		if(resultCount == 0){
			return ServiceResponse.createByErrorMessage("旧密码错误");
		}
		user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
		int updateCount = userDao.update(user).getN();
		if(updateCount > 0){
			return ServiceResponse.createBySuccessMessage("密码更新成功");
		}
		return ServiceResponse.createByErrorMessage("密码更新失败");
	}

	@Override
	public ServiceResponse<User> updateInformation(User user) {
		//用户名不能被修改
		//校验email不能相同
		int resultCount = userDao.checkEmailByUserId(user.getEmail(),user.getId());
		if(resultCount > 0){
			return ServiceResponse.createByErrorMessage("Email已经存在，请更换Email");
		}
		User updateUser = new User();
		updateUser.setId(user.getId());
		updateUser.setEmail(user.getEmail());
		updateUser.setPhone(user.getPhone());
		updateUser.setQuestion(user.getQuestion());
		updateUser.setAnswer(user.getAnswer());
		int updateCount = userDao.updateUserInfo(updateUser);
		if(updateCount > 0){
			return ServiceResponse.createBySuccess("更新个人信息成功",updateUser);
		}
		return ServiceResponse.createByErrorMessage("更新个人信息失败");
	}

	@Override
	public ServiceResponse<User> getInformation(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		User user = userDao.findOne(query);
		if(user == null){
            return ServiceResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServiceResponse.createBySuccess(user);
	}

	@Override
	public ServiceResponse checkAdminRole(User user) {
		if(user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServiceResponse.createBySuccess();
        }
        return ServiceResponse.createByError();
	}


}
