package com.topicme.web.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.topicme.web.model.LinkAndNotes;

@Controller
public class MainController {

	@PostConstruct
	public void init(){
		System.out.println("MainController");
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {

		model.addAttribute("message", "Spring 3 MVC Hello World");
		return "hello";

	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexPage(@ModelAttribute("linkAndNotes") LinkAndNotes linkAndNotes,ModelMap model) {
		return "index";

	}
	
	@RequestMapping(value = "/index/{topic:.+}", method = RequestMethod.GET)
	public ModelAndView gotoTopicPage(@ModelAttribute("linkAndNotes") LinkAndNotes linkAndNotes,
			@PathVariable("topic") String topic) {
		
		ModelAndView model = new ModelAndView();
		
		model.setViewName("index");
		model.addObject("topic", topic);

		return model;
	

	}
	
	
	@RequestMapping(value = "/index/addTopic", method = RequestMethod.POST)
	public String addLink(@ModelAttribute("linkAndNotes") LinkAndNotes linkAndNotes,
//			BindingResult result, 
//			Model model,
//			final RedirectAttributes redirectAttributes,
			HttpServletRequest req,
			SessionStatus status) {
		
		String userName = req.getUserPrincipal().getName();
		String link = linkAndNotes.getLink();
		String notes = linkAndNotes.getNotes();
		
		String topic = linkAndNotes.getTopic();
		status.setComplete();
		
        return "redirect:/index/"+topic;
	}

	@RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
	public ModelAndView hello(@PathVariable("name") String name) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    User principal = (User) auth.getPrincipal();
	      
		ModelAndView model = new ModelAndView();
		model.setViewName("hello");
		model.addObject("name", principal.getUsername());

		return model;

	}

}
