package it.ldlife.service;

import it.ldlife.common.ServiceResponse;
import it.ldlife.pojo.User;

/**
 * Created by xubinhui on 17-5-16.
 */
public interface IUserService {
	
	int checkUserName(String userName);
	
	int checkEmail(String email);

    ServiceResponse<User> login(String username, String password);

    ServiceResponse<String> register(User user);

	ServiceResponse<String> checkValid(String str, String type);
	
	ServiceResponse<String> selectQuestion(String userName);
	
	ServiceResponse<String> checkAnswer(String username,String question,String answer);

	ServiceResponse<String> forgetResetPassword(String username, String forgetToken, String passwordNew);

	ServiceResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

	ServiceResponse<User> updateInformation(User user);

	ServiceResponse<User> getInformation(String id);

	ServiceResponse checkAdminRole(User user);

}
