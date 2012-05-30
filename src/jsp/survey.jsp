<%@ page import="java.util.*, java.io.*, javax.servlet.*" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width">
	<title>Online Resource Usage Survey &ndash; UC San Diego Libraries</title>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script src="javascript/mines.js"></script>
	<link rel="stylesheet" href="css/mines.css">
	<!--[if lt IE 9]>
	<link rel="stylesheet" href="http://libraries.ucsd.edu/assets/mines/mines-ie.css">
	<![endif]-->
</head>
<body>
	
	<div id="header">
		<img width="168" height="33" alt="UC San Diego" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAKgAAAAhCAYAAABN9OCkAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYxIDY0LjE0MDk0OSwgMjAxMC8xMi8wNy0xMDo1NzowMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNS4xIFdpbmRvd3MiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6MTMyRTY5QkQ4OEQwMTFFMUJDNTA4QkQ2NjU1OUFBRkIiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6MTMyRTY5QkU4OEQwMTFFMUJDNTA4QkQ2NjU1OUFBRkIiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDoxMzJFNjlCQjg4RDAxMUUxQkM1MDhCRDY2NTU5QUFGQiIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDoxMzJFNjlCQzg4RDAxMUUxQkM1MDhCRDY2NTU5QUFGQiIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PlEYwbgAAA0cSURBVHja7FxrUhNN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">
	</div>
	
	<div id="section">
	
		<h1>Complete this survey to proceed to your requested resource.</h1>
		<p>UC San Diego is conducting an anonymous, 2-hour cost analysis of library use which may benefit the library's budget.  Thanks for your help.</p>
	
	<form name="mines_survey" id="mines_survey" method="post" action="MINES_survey">
		    <input type="hidden" name="destination_url" id="destination_url" value="<%= request.getAttribute("requestURL")%>">
		    <input type="hidden" name="new_destination_url" id="new_destination_url" value="<%= request.getAttribute("requestURL")%>">
			<input type="hidden" name="patron_location" id="patron_location" value="<%= request.getAttribute("clientIp")%>">
			<input type="hidden" name="client_ip_param" value="<%= request.getAttribute("clientIpParam")%>">	
	
			<fieldset id="set-a">
				<legend>
					What's your classification?
					<div class="required">Required &ndash; Select one</div>
				</legend>
				<label for="a1">
					<input id="a1" type="radio" name="patron_status" value="1">
					UCSD Undergraduate Student
				</label>
				<label for="a2">
					<input id="a2" type="radio" name="patron_status" value="2">
					UCSD Graduate Student
					<div class="note">Medical, Pharmacy, &amp; other Graduate/Professional Schools</div>
				</label>
				<label for="a3">
					<input id="a3" type="radio" name="patron_status" value="3">
					UCSD Personnel
					<div class="note">Faculty, Staff, Fellow, Resident, Post-Doc, &amp; Clinical Faculty</div>
				</label>
				<label for="a4">
					<input id="a4" type="radio" name="patron_status" value="4">
					Non-UCSD
				</label>
			</fieldset>

			<fieldset id="set-c">
				<legend>
					Why are you using the requested resource?
					<div class="required">Required &ndash; Select one</div>
				</legend>
				<label for="c1">
					<input id="c1" type="radio" name="research_type" value="0">
					Sponsored (Funded) Research
					<div class="note">
						<ul>
							<li>Research funded by grants or contracts from federal, state, or local governments</li>
							<li>Research funded for grants or contracts from a foundation or other outside party</li>
							<li>Separately budgeted research projects funded by University money</li>
							<li>Research training</li>
						</ul>
						<p><em>Category includes only specially funded research projects, which are separately budgeted and accounted for as organized research projects by the institution.</em></p>
					</div>
				</label>
				<label for="c2">
					<input id="c2" type="radio" name="research_type" value="1">
					Instruction/&#8203;Education/&#8203;Departmental (Non-Funded) Research
					<div class="note">
						<ul>
							<li>All teaching and training activities</li>
							<li>All student coursework, including term papers</li>
							<li>Preparation for classes</li>
							<li>Activities funded by University or an outside award made primarily for supporting graduate student teaching and training activities</li>
							<li>Theses, dissertations, etc.</li>
							<li>Sponsored training</li>
							<li>Independent faculty research (departmental research), writing, and other scholarly activities</li>
						</ul>
					</div>
				</label>
				<label for="c3">
					<input id="c3" type="radio" name="research_type" value="2">
					Patient Care
					<div class="note">
						<ul>
							<li>Activities related to treating patients & hospital duties</li>
						</ul>
					</div>
				</label>
				<label for="c4">
					<input id="c4" type="radio" name="research_type" value="3">
					Other Sponsored Activities (Public Service)
					<div class="note">
						<ul>
							<li>Community action and service programs</li>
							<li>Sponsored public service projects</li>
						</ul>
					</div>
				</label>
				<label for="c5">
					<input id="c5" type="radio" name="research_type" value="4">
					Other Institutional Activities
					<div class="note">
						<ul>
							<li>Recreational usages (not for class)</li>
							<li>Administrative work, including grant preparation</li>
							<li>Routine clinical trials and testing/service agreements</li>
							<li>All other purposes not identified in other categories</li>
						</ul>
					</div>
				</label>
			</fieldset>
	
			<div id="sponsored-research-extra">
				<fieldset>
					<legend>
						Which department, division, or school do you belong to?
						<div class="optional">Optional &ndash; Enter text</div>
					</legend>
					<div class="text-control"><input type="text" name="academic_department" id="academic_department"></div>
				</fieldset>
				
				<fieldset class="detail-list">
					<legend>
						What's the name of your sponsor or fund source?
						<div class="optional">Optional &ndash; Enter text</div>
					</legend>
					<div class="text-control"><input type="text"  name="sponsor_proof" id="sponsor_proof"></div>
					<p><em>Choose one of the following options:</em></p>
					<ul>
						<li>Name of Sponsor or Fund Source<br/>
							<span>E.g., NIH, NSF, DOE, DOD, State of California, American Cancer Society, Genentech, Mellon Foundation, NEH, etc.</span>
						<li>Name of Principal Investigator/Researcher</li>
						<li>Name of Grant or Fund Number</li>
					</ul>
				</fieldset>
			</div>
			
			<fieldset id="ye-olde-submit-button">
				<input type="submit" name="submit" id="submit" value="Submit Survey">
			</fieldset>
	
		</form>
	
	</div>
	
	<div id="footer">
		<p>Questions about this survey? <a href="mailto:librarysurvey@ucsd.edu">Let us know</a>!</p>
	</div>

</body>
</html>