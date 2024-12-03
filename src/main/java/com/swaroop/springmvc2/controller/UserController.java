package com.swaroop.springmvc2.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.swaroop.springmvc2.dao.UserDAO;
import com.swaroop.springmvc2.dto.UserDTO;
import com.swaroop.springmvc2.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserDAO userDAO;
	 
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	protected String getSignUpPage() {
		return "signup";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	protected String getLogInPage() {
		return "login";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	protected String getHomePage(HttpSession httpSession, ModelMap modelMap) {
		UserDTO user = (UserDTO) httpSession.getAttribute("user");
		if (user != null) {
			modelMap.addAttribute("user", user);
			return "home";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/add-user", method = RequestMethod.POST)
	protected String addUser(@RequestParam(name = "username") String userName,
			@RequestParam(name = "email") String email, @RequestParam(name = "mobile") long mobile,
			@RequestParam(name = "password") String password, @RequestParam(name = "role") String role,
			ModelMap modelMap) {
		UserDTO addedUser = userService.addUser(userName, email, mobile, password, role);
		if (addedUser != null) {
			modelMap.addAttribute("message", "User added..");
			return "login";
		} else {
			modelMap.addAttribute("message", "Something went wrong..");
			return "signup";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	protected String login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password,
			ModelMap modelMap, HttpSession httpSession) {
		UserDTO user = userService.login(email, password);
		if (user != null) {
			httpSession.setAttribute("user", user);
			httpSession.setMaxInactiveInterval(30 * 24 * 60 * 60);
			modelMap.addAttribute("user", user);
			if(user.isBlocked()) {
				modelMap.addAttribute("message","user is blocked contact admin");
						return "login";
			}else {
			return "home";
			}
		} else {
			modelMap.addAttribute("message", "Invalid email or password..");
			return "login";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	protected String logout(HttpSession httpSession) {
		httpSession.invalidate();
		return "login";
	}

	@RequestMapping(value = "/edit-user", method = RequestMethod.GET)
	protected String getEditPage(HttpSession httpSession, ModelMap modelMap) {
		UserDTO user = (UserDTO) httpSession.getAttribute("user");
		if (user != null) {
			modelMap.addAttribute("user", user);
			return "edit";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/update-user", method = RequestMethod.POST)
	protected String updateUser(@RequestParam(name = "id") int id, @RequestParam(name = "username") String userName,
			@RequestParam(name = "email") String email, @RequestParam(name = "mobile") long mobile,
			@RequestParam(name = "password") String password, ModelMap modelMap) {
		UserDTO updatedUser = userService.updateUser(id, userName, email, mobile, password);
		if (updatedUser != null) {
			modelMap.addAttribute("message", "User updated..");
		} else {
			modelMap.addAttribute("message", "Something went wrong..");
		}
		return "home";
	}

	@RequestMapping(value = "/delete-user", method = RequestMethod.GET)
	protected String deleteUser(HttpSession httpSession, ModelMap modelMap) {
		UserDTO user = (UserDTO) httpSession.getAttribute("user");
		if (user != null) {
			userService.deleteUser(user.getId());
			modelMap.addAttribute("message", "User deleted..");
		}
		httpSession.invalidate();
		return "login";
	}
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	protected String findAllUsers(HttpSession httpSession, ModelMap modelMap) {
		UserDTO user = (UserDTO) httpSession.getAttribute("user");
		if (user != null) {
			List<UserDTO> users = userDAO.findAllUsers();
			modelMap.addAttribute("users", users);
			return "users";
		} else {
			return "login";
		}
	}
	@RequestMapping(value = "/block-user", method = RequestMethod.GET)
	protected String blockUser(@RequestParam(name = "id") int id, ModelMap modelMap, HttpSession httpSession) {
		UserDTO user = (UserDTO) httpSession.getAttribute("user");
		if (user != null) {
			UserDTO blockedUser = userService.blockUser(id);
			if (blockedUser != null) {
				List<UserDTO> users = userDAO.findAllUsers();
				modelMap.addAttribute("users", users);
			}
			return "users";
		} else {
			return "login";
		}
	}
	@RequestMapping(value = "/unblock-user", method = RequestMethod.GET)
	protected String unBlockUser(@RequestParam(name = "id") int id, ModelMap modelMap, HttpSession httpSession) {
		UserDTO user = (UserDTO) httpSession.getAttribute("user");
		if (user != null) {
			UserDTO blockedUser = userDAO.unBlockUser(id);
			if (blockedUser != null) {
				List<UserDTO> users = userDAO.findAllUsers();
				modelMap.addAttribute("users", users);
			}
			return "users";
		} else {
			return "login";
		}
	}

}
