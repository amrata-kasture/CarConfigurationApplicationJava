package carConfigServlet;

/*import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;*/
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import defaultSocket.DefaultSocketClient;

/**
 * Servlet implementation class CarConfigMenu
 */
@WebServlet("/CarConfigMenu")
public class CarConfigMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//public String[] modelArr = new String[5];
	DefaultSocketClient cSocket = null;
    /**
     * Default constructor. 
     */
    public CarConfigMenu() {
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException
    {
    	
    	cSocket = new DefaultSocketClient("192.168.0.4", 4444);
    	//cSocket.start();
    	/*modelArr = cSocket.list;
    	*/
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 // reading the user input
        //String menu = request.getParameter("menu");
      //  String password = request.getParameter("password");
	
	  
		/*request.setAttribute("makeVal", modelArr);
        // request.getRequestDispatcher("/CarModels.jsp").forward(request, response);
		  RequestDispatcher view=request.getRequestDispatcher("CarModels.jsp");
		    view.forward(request,response); 
		System.out.println("################"+modelArr.length);
         System.out.println("****************"+Arrays.toString(modelArr));
         String model = request.getParameter("makeVal");
 	    // compare selected value 
 	    if (true) {
 	    	System.out.println("@@@@@@@@@@@@@@@@"+model);
 	    }*/
		
		if(request.getParameter("configCar") != null){
		LinkedHashMap<String,String[]> propMap = new LinkedHashMap<String,String[]>();	
		String modelChosen = request.getParameter("makeVal");
		System.out.println("@@@@@@@@@@@@@@@@"+modelChosen);
		/*String[] colArr = new String[10];
		String[] transArr = new String[2];
		String[] brkArr = new String[3];
		String[] airBArr = new String[2];
		String[] pmrArr = new String[2];*/
		request.setAttribute("choice",modelChosen);	
		
		//String abc = cSocket.getAuto(modelChosen);
		 if (cSocket.openConnection()){
	/*	colArr = cSocket.getColor(modelChosen);
		for(String s:colArr){
			System.out.println("# "+s);
		}
		
		transArr=cSocket.getTransmission(modelChosen);
		brkArr=cSocket.getBrakes(modelChosen);
		airBArr=cSocket.getAirBags(modelChosen);
		pmrArr=cSocket.getPMoonRoof(modelChosen);*/
		propMap = cSocket.getProps(modelChosen);	 
		for(String s:propMap.keySet()){
			System.out.println("# "+s+"-->"+Arrays.toString(propMap.get(s)));
		}
		 }
		 cSocket.closeSession(); 
		/*request.setAttribute("colorVal",colArr);	
		request.setAttribute("transmissionVal",transArr);
		request.setAttribute("brakeVal",brkArr);
		request.setAttribute("airbagsVal",airBArr);
		request.setAttribute("pmroofVal",pmrArr);*/
		 request.setAttribute("colorVal",propMap.get("Color"));	
		request.setAttribute("transmissionVal",propMap.get("Transmission"));
		request.setAttribute("brakeVal",propMap.get("Brakes"));
		request.setAttribute("airbagsVal",propMap.get("AirBags"));
		request.setAttribute("pmroofVal",propMap.get("PowerMonnroof"));
 	    request.getRequestDispatcher("/CarConfig.jsp").forward(request, response);
		}
        //PrintWriter out = response.getWriter();
        //out.println (modelArr);   
       // response.getWriter().append("Served at: ").append(request.getContextPath());
		if(request.getParameter("showPrice") != null){
			String modelChosen = request.getParameter("ch");
			String color = request.getParameter("colorVal");
			String transmission = request.getParameter("transmissionVal");
			String brakes = request.getParameter("brakeVal");
			String airbags = request.getParameter("airbagsVal");
			String pmroof = request.getParameter("pmroofVal");
			 if (cSocket.openConnection()){
			float cPrice = cSocket.getOpPrice(modelChosen,color);
			float tPrice = cSocket.getOpPrice(modelChosen,transmission);
			float bPrice = cSocket.getOpPrice(modelChosen,brakes);
			float abPrice = cSocket.getOpPrice(modelChosen,airbags);
			float pmPrice = cSocket.getOpPrice(modelChosen,pmroof);
			 
			System.out.println("@@"+cPrice);
			System.out.println("@@"+tPrice);
			System.out.println("@@"+bPrice);
			System.out.println("@@"+abPrice);
			System.out.println("@@"+pmPrice);
			
			request.setAttribute("ch",modelChosen);
			request.setAttribute("colorVal",color);	
			request.setAttribute("transmissionVal",transmission);
			request.setAttribute("brakeVal",brakes);
			request.setAttribute("airbagsVal",airbags);
			request.setAttribute("pmroofVal",pmroof);
			request.setAttribute("colorPri",cPrice);	
			request.setAttribute("transmissionPri",tPrice);
			request.setAttribute("brakePri",bPrice);
			request.setAttribute("airbagsPri",abPrice);
			request.setAttribute("pmroofPri",pmPrice);
			 }
			 cSocket.closeSession();
			float price = 0;
			price = cSocket.getTotal(modelChosen,color,transmission,brakes,airbags,pmroof);
			request.setAttribute("price",price);
			request.getRequestDispatcher("/CarTotalPrice.jsp").forward(request, response);
		}
		
		
        }
	
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("I  I AM FIRST");
		doGet(request, response);
		
	}

}
