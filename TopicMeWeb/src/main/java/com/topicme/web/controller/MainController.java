package com.topicme.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.topicme.html.parser.HtmlParserService;
import com.topicme.mongodb.collection.model.PageSearchResultDomain;
import com.topicme.search.service.HtmlContentSearchService;
import com.topicme.solr.common.HtmlDocumentFields.HTML_PARSED_OPTIONS;
import com.topicme.solr.model.WebPageModel;
import com.topicme.web.model.LinkAndNotes;
import com.topicme.web.model.SearchInputs;

@Controller
public class MainController {

	private Logger LOGGER = LoggerFactory.getLogger(MainController.class);
	
	
	@Autowired
	private HtmlParserService htmlParserService;
	
	@Autowired
	private HtmlContentSearchService htmlContentSearchService;
	
	@PostConstruct
	public void init(){
		System.out.println("MainController");
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String searchPage(ModelMap model) {

		model.addAttribute(new SearchInputs());
		return "search";

	}
	
	@ResponseBody
	@RequestMapping(value = "/q={query}", method = RequestMethod.GET)
	public List<PageSearchResultDomain>
				searchPage(@PathVariable("query") String query,HttpServletRequest request,ModelMap model) {
		
		List<PageSearchResultDomain> results = htmlContentSearchService.searchPage(query);
		return results;
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
	public String addLink(
			@ModelAttribute("linkAndNotes") LinkAndNotes linkAndNotes,
			SessionStatus status) {
		
		String link = linkAndNotes.getLink();
		String notes = linkAndNotes.getNotes();
		
		String topic = linkAndNotes.getTopic();
		status.setComplete();
		
		try {
		
			Map<HTML_PARSED_OPTIONS,String> parsedContentMap = htmlParserService.parseAndIndexLink(link,notes);
		
		} catch (IOException | SAXException | TikaException | SolrServerException e) {
			LOGGER.error("",e);
		}
		
        return "redirect:/index/"+topic;
	}

	
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchString(@ModelAttribute("searchInputs") SearchInputs searchInputs ,@RequestParam("query") String searchString,ModelAndView model) {
		
		model.setViewName("results");
		model.addObject(new SearchInputs());
		List<WebPageModel> pageList = new ArrayList<>();
		WebPageModel webPageModel = new WebPageModel();
		
		webPageModel.setTitle("Khoa Tran");
		
		WebPageModel webPageModel2 = new WebPageModel();
		
		webPageModel2.setTitle("Vicky Hoang");
		
		pageList.add(webPageModel);
		pageList.add(webPageModel2);
		
		model.addObject("pages",pageList);
		
		return model;
	}

}
