package Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Dao.SimilarityDAO;
import Structure.AList;

@Path("/service")
public class First {
	@GET
	@Path("/getFiles")
	@Produces(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response getAuth() throws Exception {

		AList a = new AList();
		List<Object> al = new ArrayList<Object>();

		File folder = new File("C://Users//sujeet.suresh.dixit//Desktop//AI-JUD//JudData//Cases");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				al.add(listOfFiles[i].getName());
			}
		}
		a.setaList(al);

		return javax.ws.rs.core.Response.status(200).entity(a.getaList())
				.type("application/json").build();

	}
	
	@GET
	@Path("/getNewCase")
	@Produces(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response getNewCase() throws Exception {

		AList a = new AList();
		List<Object> al = new ArrayList<Object>();

		File folder = new File("C://Users//sujeet.suresh.dixit//Desktop//AI-JUD//JudData//NewCases");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				al.add(listOfFiles[i].getName());
			}
		}
		a.setaList(al);

		return javax.ws.rs.core.Response.status(200).entity(a.getaList())
				.type("application/json").build();

	}
	
	
	
	@POST
	@Path("/getCaseDetails")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response getCaseDetails(Object ob)
			throws Exception {

		AList a = new AList();
		List<Object> al = new ArrayList<Object>();
		String Case = ob.toString();
		String op=(String) new SimilarityDAO().getCaseDetails(Case);
		al.add(op);
		a.setaList(al);

		return javax.ws.rs.core.Response.status(200).entity(a.getaList())
				.type("application/json").build();

	}

	
	@POST
	@Path("/getTop3")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response getTop3(Object ob)
			throws Exception {

		AList a = new AList();
		List<Object> al = new ArrayList<Object>();
		String Case = ob.toString();
		al=new SimilarityDAO().getTop3(Case);
		a.setaList(al);

		return javax.ws.rs.core.Response.status(200).entity(a.getaList())
				.type("application/json").build();

	}
	
	
	@POST
	@Path("/getBasic")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response getBasic(Object ob)
			throws Exception {

		AList a = new AList();
		List<Object> al = new ArrayList<Object>();
		String Case = ob.toString();
		al=new SimilarityDAO().getBasic(Case);
		a.setaList(al);

		return javax.ws.rs.core.Response.status(200).entity(a.getaList())
				.type("application/json").build();

	}
	
	
	@POST
	@Path("/getSimilarResult")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response getSimilarResult(Object ob)
			throws Exception {

		AList a = new AList();
		List<Object> al = new ArrayList<Object>();
		String Case = ob.toString();
		al=new SimilarityDAO().similarResult(Case);
		a.setaList(al);

		return javax.ws.rs.core.Response.status(200).entity(a.getaList())
				.type("application/json").build();

	}

	@POST
	@Path("/getPendingChecklistDetails")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response getPendingChecklistDetails(Object ob)
			throws Exception {

		AList a = new AList();
		List<Object> al = new ArrayList<Object>();
		String Case = ob.toString();
		al=new SimilarityDAO().generate(Case);
		a.setaList(al);

		return javax.ws.rs.core.Response.status(200).entity(a.getaList())
				.type("application/json").build();

	}
}