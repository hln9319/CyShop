function popUpAlert(message) {
    alert(message);
}

function showDIVs(){
	document.getElementById("alert").style.display = "block";
	document.getElementById("overlay").style.visibility = "visible";
}

function hideDIVs(){
	document.getElementById("alert").style.display = "none";
	document.getElementById("overlay").style.visibility = "hidden";
}