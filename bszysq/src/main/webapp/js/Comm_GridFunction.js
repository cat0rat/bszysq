function doSearch(){
	jQuery("#page").val(1);
	document.forms['search_form'].submit();
}

function checkAll(cObj){
	var elements = document.forms["search_form"].elements;
	for(var i = 0; i < elements.length; i++){
		if(elements[i].type == "checkbox"){
			elements[i].checked = cObj.checked;
		}				
	}
	
	if(cObj.checked){
		jQuery('#tbContent').children("tr").each(function(){
			jQuery(this).addClass('checked');
		});
	}else{
		jQuery('#tbContent').children("tr").each(function(){
			jQuery(this).removeClass('checked');
			jQuery(this).removeClass("on")
		});
	}
}

function getChecked(){
	var checked = new Array();
	var elements = document.forms["search_form"].elements;
	for(var i = 0; i < elements.length; i++){
		if(elements[i].type == "checkbox" && elements[i].value != "" && elements[i].checked){
			checked[checked.length] = elements[i];
		}				
	}
	return checked;
}

function selectRow(index){
	var cObj = document.getElementById("ck_" + index);
	if(cObj.checked){
		cObj.checked = false;
		jQuery("#row_" + index).removeClass("checked");
	}else{
		cObj.checked = true;
		jQuery("#row_" + index).addClass("checked");
	}
}

function onRow(index){
	var cObj = document.getElementById("ck_" + index);
	if(cObj != null){
		if(!cObj.checked){
			jQuery("#row_" + index).addClass("on");
		}
	}else{
		jQuery("#row_" + index).addClass("on");
	}
}

function outRow(index){
	var cObj = document.getElementById("ck_" + index);
	if(cObj != null){
		if(!cObj.checked){
			jQuery("#row_" + index).removeClass("on");
		}
	}else{
		jQuery("#row_" + index).removeClass("on");
	}
}