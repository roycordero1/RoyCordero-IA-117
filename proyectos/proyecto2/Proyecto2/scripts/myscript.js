function myFunction() {

    var flip = 0;
	setInterval(() => {
	  console.log(`flip: ${flip}`);
	  flip++;
	  document.getElementById("demo").innerHTML = "Hello World " + flip;
	}, 1000);
}

function loadXMLDoc() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("demo2").innerHTML =
      this.responseText;
    }
  };
  xhttp.open("GET", "prueba.txt", true);
  xhttp.send();
}