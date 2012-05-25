$(document).ready(function() {

	//---
	// Prepare "mines_survey" form
	//---
	document.mines_survey.reset();
	$("#submit").attr("disabled","disabled");
	
	//---
	// iDevice clickable label fix
	//---
	$("label").attr("onclick","");

	//---
	// Add fancy checkmark to labels onclick	
	//---
	$("#set-a input[type=radio]").click(function(){ 
		$("#set-a label").removeClass("checked");
		$(this).parent().addClass("checked");
	});
	
	$("#set-b input[type=radio]").click(function(){ 
		$("#set-b label").removeClass("checked");
		$(this).parent().addClass("checked");
	});
	
	$("#set-c input[type=radio]").click(function(){ 
		$("#set-c label").removeClass("checked");
		$(this).parent().addClass("checked");
	});
	
	//---
	// Toggle "sponsored-research-extra" questions
	//---
	$("#c1").click(function(){ 
		$("#sponsored-research-extra").show();
		$("#set-c .note").hide();
		$("#c1 + div").show();
	});
	
	$("#c2").click(function(){ 
		$("#sponsored-research-extra").hide();
		$("#set-c .note").hide();
		$("#c2 + div").show();
	});

	$("#c3").click(function(){ 
		$("#sponsored-research-extra").hide();
		$("#set-c .note").hide();
		$("#c3 + div").show();
	});

	$("#c4").click(function(){ 
		$("#sponsored-research-extra").hide();
		$("#set-c .note").hide();
		$("#c4 + div").show();
	});

	$("#c5").click(function(){ 
		$("#sponsored-research-extra").hide();
		$("#set-c .note").hide();
		$("#c5 + div").show();
	});
	
	//---
	// Activate submit button on form completion
	//---
	$("input").live("change", function(){activeVerify();});
	$("input[type=text]").keyup(function(event){activeVerify();});

});

function activeVerify(){
	
	$("#submit").attr("disabled","disabled");

	var
		in1 = $("input[name=patron_status]:checked").length, 
		in2 = $("input[name=patron_behalf_of]:checked").length, 
		in3 = $("input[name=research_type]:checked").length,
		in4 = ($("input[name=academic_department]").val() !== "")?1:0,
		in5 = ($("input[name=sponsor_proof]").val() !== "")?1:0,
		research_type = $("input[name=research_type]:checked").val();

	if(research_type === "0")
	{
		switch(in1+in2+in3+in4+in5)
		{
			case 1:	$("#ye-olde-submit-button legend").html("One down...");break;
			case 2:	$("#ye-olde-submit-button legend").html("Almost there...");break;
			case 3:	$("#ye-olde-submit-button legend").html("Couple more...");break;
			case 4:	$("#ye-olde-submit-button legend").html("One left...");break;
			case 5:	$("#ye-olde-submit-button legend").html("Done! :)");break;
		}

		if(in1+in2+in3+in4+in5 === 5){
			$("#submit").removeAttr("disabled");
		}
	}
	else
	{
		switch(in1+in2+in3)
		{
			case 1:	$("#ye-olde-submit-button legend").html("One down...");break;
			case 2:	$("#ye-olde-submit-button legend").html("Almost there...");break;
			case 3:	$("#ye-olde-submit-button legend").html("Done! :)");break;
		}

		if(in1+in2+in3 === 3){
			$("#submit").removeAttr("disabled");
		}
	}	

}