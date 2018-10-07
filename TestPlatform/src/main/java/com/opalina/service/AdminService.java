package com.opalina.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opalina.model.QuizQuestion;
import com.opalina.model.QuizQuestionCommonElement;
import com.opalina.model.QuizQuestionOption;
import com.opalina.model.QuizQuestionType;
import com.opalina.model.Response;
import com.opalina.model.Role;
import com.opalina.model.User;
import com.opalina.repository.QuizQuestionCommonElementRepository;
import com.opalina.repository.QuizQuestionOptionRepository;
import com.opalina.repository.QuizQuestionRepository;
import com.opalina.repository.QuizQuestionTypeRepository;
import com.opalina.repository.RoleRepository;
import com.opalina.repository.UserRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private QuizQuestionCommonElementRepository qqceRepository;
	@Autowired
	private QuizQuestionRepository quizQuestionRepository;
	@Autowired
	private QuizQuestionOptionRepository quizQuestionOptionRepository;
	@Autowired
	private QuizQuestionTypeRepository quizQuestionTypeRepository;

	public Response examinerCreation(String firstname,String lastname,String email,String pass,String contact){
		Response r=new Response();
		try {
		User user=new User(firstname,lastname,email,pass,contact);
		user.setRole(roleRepository.findByRole("examiner"));
		userRepository.save(user);
		r.setMessage("Succesfully created examiner");
		r.setStatus("1");
		}
		catch(DataIntegrityViolationException ex){
			Throwable t=ex.getCause();
			while((t!=null) && !(t instanceof org.hibernate.exception.ConstraintViolationException)){
				t=t.getCause();
			}
			if(t instanceof org.hibernate.exception.ConstraintViolationException) {
				r.setMessage("email or mobile number is not unique");
				r.setStatus("0");
			}
			else {
				r.setMessage("Undefined exception was encountered");
				r.setStatus("0");
			}
		}
		catch(TransactionSystemException ex) {
			Throwable t=ex.getCause();
			while((t!=null) && !(t instanceof ConstraintViolationException)){
				t=t.getCause();
			}
			if(t instanceof ConstraintViolationException) {
				r.setMessage("some field does not fullfill validation");
				r.setStatus("0");
			}
			else {
				r.setMessage("Undefined exception was encountered");
				r.setStatus("0");
			}
		}
		finally {
			return r;
		}
	}
	
	public Response examineeCreation(String firstname,String lastname,String email,String pass,String contact){
		Response r=new Response();
		try {
		User user=new User(firstname,lastname,email,pass,contact);
		user.setRole(roleRepository.findByRole("examinee"));
		userRepository.save(user);
		r.setMessage("Succesfully created examinee");
		r.setStatus("1");
		}
		catch(DataIntegrityViolationException ex){
			Throwable t=ex.getCause();
			while((t!=null) && !(t instanceof org.hibernate.exception.ConstraintViolationException)){
				t=t.getCause();
			}
			if(t instanceof org.hibernate.exception.ConstraintViolationException) {
				r.setMessage("email or mobile number is not unique");
				r.setStatus("0");
			}
			else {
				r.setMessage("Undefined exception was encountered");
				r.setStatus("0");
			}
		}
		catch(TransactionSystemException ex) {
			Throwable t=ex.getCause();
			while((t!=null) && !(t instanceof ConstraintViolationException)){
				t=t.getCause();
			}
			if(t instanceof ConstraintViolationException) {
				r.setMessage("some field does not fullfill validation");
				r.setStatus("0");
			}
			else {
				r.setMessage("Undefined exception was encountered");
				r.setStatus("0");
			}
		}
		finally {
			return r;
		}
	}
	
	public List<User> listOfExaminers(){
		Role role=roleRepository.findByRole("examiner");
		return userRepository.findByRole(role);
	}
		
	public List<User> listOfExaminees(){
		Role role=roleRepository.findByRole("examinee");
		return userRepository.findByRole(role);
	}
	
	@Transactional
	public Response removeExaminer(Long id){
		if(!userRepository.existsById(id)) {
			return new Response("Examiner with user id: "+id+" does not exist","0");
		}
		userRepository.deleteById(id);
		return new Response("Succesfully deleted examiner with user id: "+id,"1");
	}
	
	@Transactional
	public Response removeExaminee(Long id){
		if(!userRepository.existsById(id)) {
			return new Response("Examinee with user id: "+id+" does not exist","0");
		}
		userRepository.deleteById(id);
		return new Response("Succesfully deleted examinee with user id: "+id,"1");
	}
	
	public Response uploadFile(File infile)throws IOException {
		if(infile==null || infile.length()==0) {
			return new Response("Empty File","0");
		}
		else {
			int userSucc=0;
			int userUnsucc=0;
			try {
				CSVReader csvReader=new CSVReaderBuilder(new FileReader(infile)).withSkipLines(1).build();
				String[] nextRecord;
				while((nextRecord=csvReader.readNext())!=null) {
					Response r1=examineeCreation(nextRecord[0],nextRecord[1],nextRecord[2],nextRecord[3],nextRecord[4]);
					if(r1.getStatus().equals("1")) {
						userSucc++;
					}
					else {
						userUnsucc++;
					}
				}
				if(userUnsucc>0){
					return new Response("Successfully created "+userSucc+" users. "+userUnsucc+" users were not created due to missing parameters or repetition of key values","1");
				}
				else {
					return new Response("Successfully created "+userSucc+" users","1");
				}
			}
			catch(IOException ex) {
				ex.printStackTrace();
				return new Response("IOException has occurred","0");
			}
		}
	}
	
	@Transactional
	public Response addQuestions(String type,Integer groupedOrIndividual,Integer imageOrPara,InputStream fileInputStream,
	FormDataContentDisposition cdh,String paragraph,Integer format,List<Integer> difficulty,Integer imageUploadOrNot,
	List<String> questions,List<String> options,List<String> optionsCheck)throws IOException {		
		Long connectId=null;
		if(groupedOrIndividual==1) {
			if(imageOrPara==1) {
				connectId=uploadImage(fileInputStream,cdh,1);
			}
			else {
				QuizQuestionCommonElement qqiop=new QuizQuestionCommonElement(0,paragraph,1);
				qqceRepository.save(qqiop);
				connectId=qqiop.getId();
			}
		}
		
		if(imageUploadOrNot==1) {
			connectId=uploadImage(fileInputStream,cdh,0);
		}
		
		int len=questions.size();
		//Iterator<String> optionCheckList=optionsCheck.iterator();
		//Iterator<String> optionList=options.iterator();
		for(int i=0;i<len;i++) {
			QuizQuestion q=new QuizQuestion(difficulty.get(i),format,questions.get(i));
			q.setQuizQuestionType(quizQuestionTypeRepository.findByType(type));
			if(connectId!=null) {
				q.setQuizQuestionCommonElement(qqceRepository.findById(connectId).get());
			}
			q.setSingleOrMulti(0);
			quizQuestionRepository.save(q);
			if(format==1) {
				addOptions(options.get(i),optionsCheck.get(i),q.getId());
				/*
				if(optionList.hasNext() && optionCheckList.hasNext()) {
					addOptions(optionList.next(),optionCheckList.next(),q.getId());
				}
				*/
			}
		}
		return new Response("Question succesfully added to the question bank","1");
	}
	
	
	@Transactional
	public Long uploadImage(InputStream fileInputStream,FormDataContentDisposition cdh,Integer groupOrNot) throws IOException {
		final String url_path="..//..//..//..//resources//static//images";
		try {
			int read=0;
			byte[] bytes=new byte[1024];
			final String path=url_path+cdh.getFileName();
			OutputStream out=new FileOutputStream(new File(path));
			while((read=fileInputStream.read(bytes))!=-1) {
				out.write(bytes,0,read);
			}
			out.flush();
			out.close();
			
			QuizQuestionCommonElement qqiop=new QuizQuestionCommonElement(1,path,groupOrNot);
			qqceRepository.save(qqiop);
			return qqiop.getId();	
		}
		catch(IOException ex){
			ex.printStackTrace();
			return null;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
			
	@Transactional
	public void addOptions(String options,String optionsCheck,Long quizQuestionId) {
		QuizQuestion quizQuestion=quizQuestionRepository.findById(quizQuestionId).get();
		int check=0;
		Pattern pattern=Pattern.compile("\\$\\#");
		String[] optionsArray=pattern.split(options);
		String[] optionsCheckArray=pattern.split(optionsCheck);
		int len=optionsArray.length;
		for(int i=0;i<len;i++) {
			if(optionsCheckArray[i].equals("true")) {
				check++;
			}
			QuizQuestionOption newOption=new QuizQuestionOption(optionsArray[i],optionsCheckArray[i]);
			newOption.setQuizQuestion(quizQuestion);
			quizQuestionOptionRepository.save(newOption);
		}
		if(check>1) {
			quizQuestion.setSingleOrMulti(1);
		}
		else {
			quizQuestion.setSingleOrMulti(0);
		}
		quizQuestionRepository.save(quizQuestion);
	}
	
	public Response addType(String type) {
		QuizQuestionType t=new QuizQuestionType(type);
		quizQuestionTypeRepository.save(t);
		return new Response("Succesfully added a new type","1");
	}
	
	public List<QuizQuestionType> getType(){
		return quizQuestionTypeRepository.findAll();	
	}
	
	public JsonArray viewQuestions(String type,Integer groupOrNot,Integer format,Integer difficulty){
		//List<QuizQuestion> resultList=new ArrayList<>();
		JsonArray resultList=new JsonArray();
		if(groupOrNot==1) {
			List<QuizQuestionCommonElement> tempList=qqceRepository.findByGrouped(groupOrNot);
			for(QuizQuestionCommonElement qqce:tempList) {
				QuizQuestion temp1=qqce.getQuizQuestion().get(0);
				if(temp1.getQuestionFormat()==format && temp1.getQuizQuestionType().getType()==type) {
					Iterator<QuizQuestion> quizQuestionIterator=qqce.getQuizQuestion().iterator();
					while(quizQuestionIterator.hasNext()) {
						QuizQuestion temp2=quizQuestionIterator.next();
						if(temp2.getQuestionDifficulty()==difficulty) {
							//resultList.add(temp2);
						}
					}
				}
			}
		}		
		else {
			List<QuizQuestion> tempList=quizQuestionRepository.findAll();
			for(QuizQuestion qq:tempList) {
				JsonObject questionDetails=new JsonObject();
				QuizQuestionCommonElement temp1=qq.getQuizQuestionCommonElement();
				if(temp1==null) {
					if(qq.getQuestionFormat()==format && qq.getQuizQuestionType().getType().equals(type) && qq.getQuestionDifficulty()==difficulty) {
						questionDetails.addProperty("questionId",qq.getId());
						questionDetails.addProperty("question",qq.getQuestion());
						//questionDetails.addProperty("")
					}
				}
				else if(temp1.getGrouped()==0){
					if(qq.getQuestionFormat()==format && qq.getQuizQuestionType().getType().equals(type) && qq.getQuestionDifficulty()==difficulty) {
						questionDetails.addProperty("questionId",qq.getId());
						questionDetails.addProperty("question",qq.getQuestion());
						//questionDetails.addProperty("", qq.);
					}
				}
			}
		}
		return resultList;
	}
	
	/*
	private String encodeFileToBase64Binary(File file) throws IOException {
		String encodedfile=null;
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fileInputStreamReader.read(bytes);
        encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
		return null;
	}
	*/
}
