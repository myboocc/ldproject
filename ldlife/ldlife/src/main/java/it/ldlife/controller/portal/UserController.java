package it.ldlife.controller.portal;

import it.ldlife.common.Const;
import it.ldlife.common.ResponseCode;
import it.ldlife.common.ServiceResponse;
import it.ldlife.pojo.User;
import it.ldlife.service.IUserService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by xubinhui on 17-5-16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService iUserService;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> login(String username, String password, HttpSession session){
        ServiceResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServiceResponse.createBySuccess();
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> register(User user){
        ServiceResponse<String> response = iUserService.register(user);
        return response;
    }
    
    @RequestMapping(value = "/checkValid",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> checkValid(String str, String type){
    	return iUserService.checkValid(str,type);
    }

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> getUserInfo(HttpSession session){
    	User user = (User) session.getAttribute(Const.CURRENT_USER);
    	if(user != null){
    		return ServiceResponse.createBySuccess(user);
    	}
    	return ServiceResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }
    
    @RequestMapping(value = "/forgetGetQuestion",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> forgetGetQuestion(String username){
    	return iUserService.selectQuestion(username);
    }
    
    @RequestMapping(value = "/forgetCheckAnswer",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> forgetCheckAnswer(String username,String question,String answer){
    	return iUserService.checkAnswer(username, question, answer);
    }
    
    @RequestMapping(value = "/forgetResetPassword",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> forgetResetPassword(String username, String forgetToken,String passwordNew){
    	return iUserService.forgetResetPassword(username,forgetToken,passwordNew);
    }
    
    @RequestMapping(value = "/resetPassword",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
    	User user = (User) session.getAttribute(Const.CURRENT_USER);
    	if(user == null){
    		return ServiceResponse.createByErrorMessage("用户未登录");
    	}
    	return iUserService.resetPassword(passwordOld,passwordNew,user);
    }
    
    @RequestMapping(value = "/updateInformation",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> updateInformation(HttpSession session, User user){
    	User loginUser = (User) session.getAttribute(Const.CURRENT_USER);
    	if(loginUser == null){
    		return ServiceResponse.createByErrorMessage("用户未登录");
    	}
    	user.setId(loginUser.getId());
    	//TODO
    	user.setUsername(loginUser.getUsername());
    	ServiceResponse<User> response = iUserService.updateInformation(user);
    	if(response.isSuccess()){
    		session.setAttribute(Const.CURRENT_USER, response.getData());
    	}
    	return response;
    }
    
    @RequestMapping(value = "getInformation",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> getInformation(HttpSession session){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录,需要强制登录status=10");
        }
        return iUserService.getInformation(currentUser.getId());
    } 
    
    

}
