<%@ page import="java.util.*, java.io.*, javax.servlet.*" %>
<html>

<!--
	History:
		Updated 6/7/2006 by SKW with the following requests from Susan Starr:
		
		Status:
			Change "select affiliation" to "select status"
			Change popup wording to "status" for the same
			Make the Status identical to paper
			Change "Status" to Classification
		
		Proxy:
			Are you using this resource for yourself OR on behalf of another UCSD faculty/staff?
			"For myself" default
			"On behalf of another UCSD faculty/staff member"
			
		General:
			"assess" in the description at top
			"during a two hour time period today" at top 
			Move "After you complete ..." BEFORE "Your responses are important"
			Change "usage of Libraries electronic ..." to "usage of online resources and services at UCSD"
			Catherine 2nded the notion of
				"Are you using the requested resource or service ..."
			Keep it "resource or service" in the descriptive paragraph
			Contact information goes underneath submit buttons
			Cookie statement will also be below submit button
			Ditched "User Survey" sub heading
			
		Purpose:
			"Departmental" or "Departmental Research?"  Check with other surveys
			Try bullets for the description and text input instead of textarea for brevity
			Change to "reason for" ("purpose for" should be "purpose of")'
			Change "Reason for using this online resource" to "Reason for using this online resource or library service"
			Make the bullets "or"
			On choosing the sponsored research, have the cursor control go right to the text box.
			Instead of "proof" on the "no proof popup," say something like:
				"sponsor fund source name, etc. in the text box"
			Make the last reason for usage "Other Institutional Activities"
			Sponsorship proof:
				· We need department + one of the following ...
			Changed background colour of <div> for proof and gave it a border.
			
		Form:
			Submit button:  Change to "Submit and connect to requested resource or service"
			Put submit flush left, reset flush right
			
			
	     Updated 6/12/2006 by Jasman Mann:
		 
		 1. Added "Definition" rollover functionality. 
		 2. Updated header to include "UCSD" and to fit the new table width. 
		 3. Updated help desk contact info.
		 4. Updated cookie warning message. 

		 Updated 6/15/2006 by Jasman Mann:
		 
		 1. Fixed CSS Positioning to align correctly in IE and Firefox
		 2. Fixed some other Javascript errors including:
		    -Moving an onclick event that calls displaySponsorQuestion()
			from the form tag to the sponsor radio input tag. 
			-Moving an onclick event that triggers a focus() on the 
			academic_department object. 
			
		Updated 7/11/2006 by Steve Wieda:
			1. 	Removed paragraph regarding contacting the ITD Help Desk in case of
				technical issues.
-->

<!--
	TO DO:
			6/7/2006: We have to work out the contact
				We should have two contacts, one for content and one for technical assistance (help desk)
			6/8/2006:  Have the definitions be Javascript rollovers on the "Definition" text
			6/8/2006:  Header:  Make it UCSD Libraries
			6/8/2006:  Usability:
				· Can use desk staff
				· Can also use some student workers
-->




<head>
<title>
UCSD Geisel Library Online Resource Usage Survey
</title>

<style type="text/css">
<!--


table {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	border-color: #FFFFFF;
}

.formtable {

 border-color: #000000;
 border-width: thin;

}

.style1 {
	color: #CC6600;
	font-weight: bold;
	font-size: 14px;
}
.style2 {font-size: 16px}

.textarea_style {
 width: 400px;
 margin-top: 5px;
 margin-bottom: 5px;
}

a.def:link {color: #000000; text-decoration:underline;}     
a.def:visited {color: #000000} 
a.def:hover {color: #000000; text-decoration:none;}  
a.def:active {color: #000000}   

#sponsorPopup {position:absolute; top: 280px; left: 506px; height: 220px; width: 300px;  
               background-color:#FFFFDD; padding:6px; border:2px ridge; border-color:gray;
			   font-family: Verdana, Arial, Helvetica, sans-serif; font-size:10px; visibility:hidden; }
			  			   
#instructPopup {height: 250px; width: 250px;  
               background-color:#FFFFDD; padding:6px; border:2px ridge; border-color:gray;
			   font-family: Verdana, Arial, Helvetica, sans-serif; font-size:10px; visibility:hidden; }		   
			   
#patientPopup {height: 100px; width: 250px; 
               background-color:#FFFFDD; padding:6px; border:2px ridge; border-color:gray;
			   font-family: Verdana, Arial, Helvetica, sans-serif; font-size:10px; visibility:hidden; }
			   
#otherSponsorPopup {height: 100px; width: 280px; 
               background-color:#FFFFDD; padding:6px; border:2px ridge; border-color:gray;
			   font-family: Verdana, Arial, Helvetica, sans-serif; font-size:10px; visibility:hidden; }
			   
#otherInstitutionalPopup {height: 150px; width: 280px; 
               background-color:#FFFFDD; padding:6px; border:2px ridge; border-color:gray;
			   font-family: Verdana, Arial, Helvetica, sans-serif; font-size:10px; visibility:hidden; }

-->
</style>

<script language="javascript" type="text/javascript">

<!--

/* Function to show/hide definitions */
function showDef(id,flagit) {

     //get positioning units
     var noPx = document.childNodes ? 'px' : 0;
	 
	 if (document.layers) 
	     var div_obj = document.layers[''+id+''];
     else if (document.all) 
	     var div_obj = document.all[''+id+''];
     else if (document.getElementById) 
	     var div_obj = document.getElementById(''+id+'');
     
	 //show definition
     if (flagit=="1"){
	     if(document.forms[0].research_type[0].checked && id != 'sponsorPopup') {
		    //move div down
			if(div_obj.style)
		       div_obj.style.top = parseInt(div_obj.style.top) + 200 + noPx; 
		 }
         if (document.layers) div_obj.visibility = "show";
         else if (document.all) div_obj.style.visibility = "visible";
         else if (document.getElementById) div_obj.style.visibility = "visible";
     }
	 
	 //hide definition
     else if (flagit=="0"){
	     if(document.forms[0].research_type[0].checked && id != 'sponsorPopup') {
		     //move div back up
			 if(div_obj.style)
			   div_obj.style.top = parseInt(div_obj.style.top) - 200 + noPx; 
		 }
         if (document.layers) div_obj.visibility = "hide";
         else if (document.all) div_obj.style.visibility = "hidden";
         else if (document.getElementById) div_obj.style.visibility = "hidden";
     }
}

/* Function to validate form inputs */
function validate_form() {

     var radio_checked=false;
	 var selected = false;
	 
     for (i=0;i<document.forms[0].research_type.length;i++) {
	     if (document.forms[0].research_type[i].checked) {
		    user_input = document.forms[0].research_type[i].value;
			if(user_input == 0) {
				if(document.forms[0].academic_department.value == ''){
					alert("Please enter Academic Department.");
					return false;
				}
			}
			if(user_input == 0) {
			   if(document.forms[0].sponsor_proof.value == '') {
			     alert("Please enter sponsor fund source name, etc. in the text box.");
				 return false;
			   }
			}
			radio_checked= true;
	     }
     }
	  
	 if (document.forms[0].patron_status.value == 0) {
	 	alert ("Please select a valid Classification.");
		return false;
	 }
	 
     if (radio_checked==false) {
	  alert ("Please select an option for \"Reason for using the requested online resource or library service.\""); 
	  return false;
	 }
	 
	 return true;

}

/* Function to display the sponsor question */
function displaySponsorQuestion(obj) {

	 if(document.forms[0].research_type[0].checked) {
	    obj1 = document.getElementById(obj); 
        obj1.style.display = 'block';
	 }
	 else {
	    obj1 = document.getElementById(obj);
		document.forms[0].sponsor_proof.value=''; 
        document.forms[0].academic_department.value='';
	    obj1.style.display = 'none';
	 }
}

/* Function to hide the sponsor question */
function hideSponsorQuestion(obj, resetval) {

   if(!document.forms[0].research_type[0].checked || resetval==1) {
      obj1 = document.getElementById(obj);
      //reset values      
      document.forms[0].academic_department.value='';
      document.forms[0].sponsor_proof.value=''; 
      obj1.style.display = 'none';
   }

}

function openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}


-->
</script>

</head>
<body>

<table width="824" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><p><img src="images/ucsd_header.gif" width="824" height="58"></p>
      THIS SURVEY is being conducted during a two hour time period today by the University of California, 
 	  San Diego to assess the usage of selected online resources and of library services at UCSD. 
	  After completing the survey you will be connected to the resource you selected.
	  Your response to this short survey is important. ALL RESPONSES ARE CONFIDENTIAL.  
        
      Thank you for your help.</p></td>
  </tr>
</table>

<p>&nbsp;</p>
<form action="MINES_survey" method="post" onclick="hideSponsorQuestion('sponsor_question',0)" onReset="hideSponsorQuestion('sponsor_question',1);" onSubmit="return validate_form()" name="mines_survey" id="mines_survey">
  <table width="824" border="1" cellpadding="4" cellspacing="0">
    <tr>
      <td width="200px"><strong>Classification:</strong></td>
      <td width="598"><select name="patron_status" id="mines_survey" class="form_table">
        <option value="0" selected>Select your classification/status</option>
        <option value="1">UCSD Undergraduate Student</option>
        <option value="2">UCSD Graduate Student (including Medical / Pharmacy and other Graduate / Professional Schools)</option>
        <option value="3">UCSD Personnel (including Faculty / Staff / Fellows / Residents / Post-Docs / Clinical Faculty)</option>
        <option value="4">Non-UCSD</option>
      </select></td>
    </tr>
    <tr>
      <td width="200px"><strong>Are you using the requested resource or library service for yourself or on behalf of a UCSD faculty or staff member?</strong></td>
      <td><select name="patron_behalf_of" id="patron_behalf_of" class="form_table">
	  <option value="0"> For myself</option>
	  <option value="1"> On behalf of a UCSD faculty/staff member</option>
      </select>      
	  </td>
    </tr>
    <tr>
      <td width="200px" valign="top"><strong>Reason for using <br>
      the requested online resource or library service: </strong></td>
      <td valign="top">

        <input id="research_type" name="research_type" type="radio" value="0" onClick="displaySponsorQuestion('sponsor_question'); document.mines_survey.academic_department.focus(); "/>
          Sponsored (Funded) Research [<a class="def" href="javascript:openBrWindow('definition_sponsor.html','sponsor','width=300,height=270,left=700,top=350,screenX=700,screenY=350')" onMouseOver="showDef('sponsorPopup',1)" onMouseOut="showDef('sponsorPopup',0)">Definition</a>]<br>
          
        <div id="sponsor_question" style="padding: 5px; display: none; background-color: #ffeeee; border-color: #ff0000; border-style: solid; border-width: 1px">
          You indicated Sponsored (Funded) Research:<br />
          Please provide the following information:
		 <table>
		 	<tr><td>
				Your Department, Division, or School: <i>(Please specify)</i>
			</td>
			<td>
				<input type="text" name="academic_department" id="academic_department" />
			</td></tr>
		</table>			
			<b>AND AT LEAST ONE</b> of the following:
			  	<ul>
				  <li>Sponsor or fund source name (for example, NIH, NSF, DOE, DOD, State of California, American Cancer Society, Genentech, Mellon Foundation, NEH, etc.): <i>(Please specify)</i> or</li>
					<li>Principal investigator/researcher: or</li>
					<li>Name of the grant or fund number: </li>
				</ul>
	        <input type="text"  name="sponsor_proof" id="sponsor_proof" />
		</div>
	  
        <input type="radio" name="research_type" value="1"> Instruction/Education/Departmental (Non-Funded) Research [<a class="def" href="javascript:openBrWindow('definition_instruction.html','instruction','width=250,height=305,left=700,top=350,screenX=700,screenY=350')" onMouseOver="showDef('instructPopup',1)" onMouseOut="showDef('instructPopup',0)">Definition</a>]
		<br />
        <input type="radio" name="research_type" value="2"> Patient Care [<a class="def" href="javascript:openBrWindow('definition_patient.html','patient','width=270,height=130,left=700,top=350,screenX=700,screenY=350')" onMouseOver="showDef('patientPopup',1)" onMouseOut="showDef('patientPopup',0)">Definition</a>]
        <br />
        <input type="radio" name="research_type" value="3"> Other Sponsored Activities (Public Service) [<a class="def" href="javascript:openBrWindow('definition_other_sponsor.html','other_sponsor','width=285,height=130,left=700,top=350,screenX=700,screenY=350')" onMouseOver="showDef('otherSponsorPopup',1)" onMouseOut="showDef('otherSponsorPopup',0)">Definition</a>]
        <br />
        <input type="radio" name="research_type" value="4"> Other Institutional Activities [<a class="def" href="javascript:openBrWindow('definition_other_instruct.html','other_instruct','width=260,height=200,left=700,top=350,screenX=700,screenY=350')" onMouseOver="showDef('otherInstitutionalPopup',1)" onMouseOut="showDef('otherInstitutionalPopup',0)">Definition</a>]
	  </td>
    </tr>
  </table>

  <p>&nbsp;</p>
  <p>
    <input type="hidden" name="destination_url" id="destination_url" value="<%= request.getAttribute("requestURL")%>">
    <input type="hidden" name="new_destination_url" id="new_destination_url" value="<%= request.getAttribute("requestURL")%>">
	<input type="hidden" name="patron_location" id="patron_location" value="<%= request.getAttribute("clientIp")%>">
	<input type="hidden" name="client_ip_param" value="<%= request.getAttribute("clientIpParam")%>">	

  </p>
    <table width="715">
		<tr><td width="377">
			<input type="submit" name="Submit" value="Submit and Connect to the Requested Resource or Service"></td>
		<td width="326"><input type="reset" name="Reset" value="Reset"></td>
		</tr>
	</table>
	
	<p>&nbsp;</p>
	<table width="824" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><p>Direct any questions about this survey to: <a href="mailto:survey@libraries.ucsd.edu">survey@libraries.ucsd.edu</a>. </p>
          <p> In order to avoid seeing this survey more than once every twenty minutes, please <a href="https://www.cogsci.ucsd.edu/Plone/classes/WI06/cogs205/enabling_cookies" target="_blank" >enable cookies</a>.</p></td>
      </tr>
    </table>
	<p>&nbsp;</p>
	<p>&nbsp;</p>

</form>

<!-- BEGIN DEFINITION DIV TAGS -->
<div id="sponsorPopup">
  <p><b>Sponsored (Funded) Research</b></p>
                       <p>This includes:<br />
					      <ul>
						  <li>Research funded by grants or contracts from federal, state, or local governments</li>
						  <li>Research funded for grants or contracts from a foundation or other outside party</li>
						  <li>Separate budgeted research projects funded by University money</li>
						  <li>Research training</li>
						  </ul>
					   </p>
					   <p><b>Note:</b><i> This category includes only specially funded research projects, which are separately 
					                     budgeted and accounted for as organized research projects by the institution.</i>
					   </p>



</div>
<div id="instructPopup" style="position:absolute; top: 296px; left: 695px;" >
  <p><b>Instruction/Education/Departmental (Non-Funded) Research</b></p>
                        <p>This includes:<br />
						   <ul>
						   <li>All teaching and training activities</li>
						   <li>All student coursework, including term papers</li>
						   <li>Preparation for classes</li>
						   <li>Activities funded by University or an outside award made primarily for supporting graduate student teaching and training activities</li>
						   <li>Theses, dissertations, etc.</li>
						   <li>Sponsored training</li>
						   <li>Independent faculty research (departmental research), writing, and other scholarly activities</li>
						   </ul>
						</p>

</div>

<div id="patientPopup" style="position:absolute; top: 316px; left: 396px;">
  <p><b>Patient Care</b></p>
					   <p>This includes:<br />                       
					       <ul>
						   <li>Activities related to treating patients</li>
						   <li>Activities related to hospital duties</li>
						   </ul>		  				 
					   </p>
</div>

<div id="otherSponsorPopup" style="position:absolute; top: 341px; left: 582px;">
  <p><b>Other Sponsored Activities (Public Service)</b></p>
					   <p>This includes:<br />                       
					       <ul>
						   <li>Community action and service programs</li>
						   <li>Sponsored public service projects</li>
						   </ul>		  				 
					   </p>
</div>

<div id="otherInstitutionalPopup" style="position:absolute; top: 355px; left: 491px;">
  <p><b>Other Institutional Activities</b></p>
					   <p>This includes:<br />                       
					       <ul>
						   <li>Recreational usages (not for class)</li>
						   <li>Administrative work, including grant preparation</li>
						   <li>Routine clinical trials and testing/service agreements</li>
						   <li>All other purposes not identified in other categories</li>
						   </ul>		  				 
					   </p>
</div>
<!-- END DEFINITION DIV TAGS -->

</body>
</html>