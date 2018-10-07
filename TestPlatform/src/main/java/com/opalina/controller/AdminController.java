package com.opalina.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opalina.model.QuizQuestion;
import com.opalina.model.QuizQuestionType;
import com.opalina.model.Response;
import com.opalina.model.User;
import com.opalina.service.AdminService;

@Component
@Path("/admin")
public class AdminController {
		
	@Autowired
	AdminService adminService;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create-new-user/examiner")
	public Response createExaminer(@FormParam("firstname") String firstname, @FormParam("lastname") String lastname,
	@FormParam("email") String email,@FormParam("password") String pass,@FormParam("contact") String contact) {
		System.out.println(firstname+" "+lastname+" "+email+" "+pass+" "+contact+": "+"Inside adminController");
		return adminService.examinerCreation(firstname,lastname,email,pass,contact);		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create-new-user/examinee")
	public Response createExaminee(@FormParam("firstname") String firstname, @FormParam("lastname") String lastname,
	@FormParam("email") String email,@FormParam("password") String pass,@FormParam("contact") String contact) {
		System.out.println(firstname+" "+lastname+" "+email+" "+pass+" "+contact+": "+"Inside adminController");
		return adminService.examineeCreation(firstname,lastname,email,pass,contact);		
	}
	
	@GET
	@Path("/view/table-examiner")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> viewExaminers() {		
		return adminService.listOfExaminers();
	}
	
	@GET
	@Path("/view/table-examinee")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> viewExaminees() {		
		return adminService.listOfExaminees();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/examiners/{id}")
	public Response deleteExaminers(@PathParam("id") Long id) {
		return adminService.removeExaminer(id);
	}
	
	@DELETE
	@Path("/examinees/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteExaminees(@PathParam("id") Long id) {
		return adminService.removeExaminee(id);
	}
	
	@POST
	@Path("/upload-file")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(@FormDataParam("file") File file) throws IOException{		
		return adminService.uploadFile(file);
	}
	
	@POST
	@Path("/add-question-type")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addType(@FormParam("type")String type) {
		return adminService.addType(type);
	}
	
	@GET
	@Path("/get-question-type")
	@Produces(MediaType.APPLICATION_JSON)
	public List<QuizQuestionType> getType(){
		return adminService.getType();
	}
	
	@POST
	@Path("/add-question-to-questionbank")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addQuestions(@FormDataParam("type")String type,@FormDataParam("groupedOrIndividual")Integer groupedOrIndividual,
	@FormDataParam("imageOrPara")Integer imageOrPara,@FormDataParam("file")InputStream fileInputStream,@FormDataParam("file")FormDataContentDisposition cdh,
	@FormDataParam("paragraph")String paragraph,@FormDataParam("format")Integer format,@FormDataParam("difficulty")String difficulty,
	@FormDataParam("imageUploadOrNot")Integer imageUploadOrNot,@FormDataParam("questions")String questions,@FormDataParam("options")String options,
	@FormDataParam("optionsCheck")String optionsCheck)throws IOException{
		
		/*
		System.out.println("type: "+type);
		System.out.println("groupedOrIndividual: "+groupedOrIndividual);
		System.out.println("imageOrPara: "+imageOrPara);
		System.out.println("paragraph: "+paragraph);
		System.out.println("format: "+format);
		System.out.println("difficulty: "+difficulty);
		System.out.println("imageUploadOrNot: "+imageUploadOrNot);
		System.out.println("questions: "+questions);
		System.out.println("options check list: "+optionsCheck);
		System.out.println("options: "+options);
		*/
		
		JsonElement jelementDifficulty=new JsonParser().parse(difficulty);
		List<String> tempDifficultyList=parseJsonelement(jelementDifficulty);
		List<Integer> difficultyList=new ArrayList<Integer>();
		for(String s : tempDifficultyList) difficultyList.add(Integer.valueOf(s));
		
		JsonElement jelementOptions=new JsonParser().parse(options);
		List<String> optionsList=parseJsonelement(jelementOptions);
		
		JsonElement jelementQuestions=new JsonParser().parse(questions);
		List<String> questionsList=parseJsonelement(jelementQuestions);
		
		JsonElement jelementOptionsCheck=new JsonParser().parse(optionsCheck);
		List<String> optionsCheckList=parseJsonelement(jelementOptionsCheck);

		return adminService.addQuestions(type,groupedOrIndividual,imageOrPara,fileInputStream,cdh,paragraph,format,difficultyList,imageUploadOrNot,questionsList,optionsList,optionsCheckList);
	}
	
	@POST
	@Path("/view-question")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<QuizQuestion> viewQuestions(@FormParam("type")String type,@FormParam("groupOrNot")Integer groupOrNot,@FormParam("format")Integer format,@FormParam("difficulty")Integer difficulty) {
		return null;
		//return adminService.viewQuestions(type,groupOrNot,format,difficulty);
	}
		
	@InitBinder
	public void initBinder(WebDataBinder dataBinder){
		StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
	}
	
	private List<String> parseJsonelement(JsonElement jsonelement){
		JsonObject jobject=jsonelement.getAsJsonObject();
		Iterator<String> it=jobject.keySet().iterator();
		List<String> resultList=new ArrayList<String>();
		while(it.hasNext()) {
			String key=it.next();
			resultList.add(jobject.get(key).getAsString());
		}
		return resultList;
	}
}
