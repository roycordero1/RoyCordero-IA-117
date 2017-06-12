/*-------------------------------------------
Progra 2 - IA, Simulación de taxis Karma
Roy Cordero Durán
-------------------------------------------*/

/*
* Class Application
* Manage app general functions
*/
class Application {
	constructor() {
		this.map = new CityMap();
		this._updateIntervalId = 0;
	}

  loadMap(fileMap) {
  	this.map.loadMap(fileMap);
  }

  printMap() {
  	this.map.printMap();
  }

  checkInstruction() {
  	var inputInstruction = document.getElementById("input").value;
  	var instruction = inputInstruction.split(" ");
    console.log(instruction);
  	switch (this._validateInstruction(instruction)) {
  		case "Animar":
  			document.getElementById("message").innerHTML = "Animar " + instruction[1];
  			this._animarInstruction(instruction[1]);
  			break;
      case "Pasear":
        if (instruction[1])
          document.getElementById("message").innerHTML = "Pasear a " + instruction[1];
        else
          document.getElementById("message").innerHTML = "Pasear a todos";
        this._pasearInstruction(instruction);
        break;
      case "Buscar":
        if (instruction[1])
          document.getElementById("message").innerHTML = "Buscar a " + instruction[1];
        else
          document.getElementById("message").innerHTML = "Buscar a todos";
        this._buscarInstruction(instruction);
        break;
      case "Parquear":
        if (instruction[2])
          document.getElementById("message").innerHTML = "Parquear a " + instruction[2];
        else
          document.getElementById("message").innerHTML = "Parquear a todos";
        this._parquearInstruction(instruction);
        break;
      case "Mostrar":
        if (instruction[2])
          document.getElementById("message").innerHTML = "Mostrar a " + instruction[2];
        else
          document.getElementById("message").innerHTML = "Mostrar a todos";
        this._mostrarInstruction(instruction);
        break;
      case "Ruta":
        if (instruction[2])
          document.getElementById("message").innerHTML = "Ruta a " + instruction[2];
        else
          document.getElementById("message").innerHTML = "Ruta a todos";
        this._rutaInstruction(instruction);
        break;
  		default:
  			document.getElementById("message").innerHTML = "Comando no válido!"
  	}
  }

  _validateInstruction(instr) {
  	if (instr[0] == "animar" && instr.length == 2 && typeof parseInt(instr[1]) == "number")
  		return "Animar";
    else if (instr[0] == "pasear")
      return "Pasear";
    else if (instr[0] == "buscar")
      return "Buscar"
    else if (instr[0] == "parquear" && (instr.length==2 || instr.length==3))
      return "Parquear"
    else if (instr[0] == "mostrar" && (instr.length==2 || instr.length==3) && (instr[1]=="on" || instr[1]=="off"))
      return "Mostrar"
    else if (instr[0] == "ruta" && (instr.length==2 || instr.length==3) && (instr[1]=="on" || instr[1]=="off"))
      return "Ruta"
  	return "Invalid";
  }

  _animarInstruction(time) {
  	if (time == 0) {
  		this._killInterval(this._updateIntervalId);
      eventEmiter.send({msg: "Detener"});
    }
  	else
  		this._createUpdateInterval(time);
  }

  _createUpdateInterval(time) {
  	if (this._updateIntervalId != 0)
  		this._killInterval(this._updateIntervalId);
  	this._updateIntervalId = setInterval(() => {
		  eventEmiter.update();
		  eventEmiter.send({msg: "update"});
		  app.printMap();
		}, time);
  }

  _killInterval(id) {
  	clearInterval(id);
  }

  _pasearInstruction(instruction) {
    if (!instruction[1])
      eventEmiter.send({msg: "Pasear"});
    else
      eventEmiter.send({msg: "Pasear", id: instruction[1]});
  }

  _buscarInstruction(instruction) {
    if (!instruction[1])
      eventEmiter.send({msg: "Buscar"});
    else
      eventEmiter.send({msg: "Buscar", id: instruction[1]});
  }

  _parquearInstruction(instruction) {
    if (!instruction[2])
      eventEmiter.send({msg: "Parquear", param1: instruction[1]});
    else
      eventEmiter.send({msg: "Parquear", id: instruction[2], param1: instruction[1]});
  }

  _mostrarInstruction(instruction) {
    if (!instruction[2] && instruction[1] == "on")
      eventEmiter.send({msg: "MostrarOn"});
    else if (!instruction[2] && instruction[1] == "off")
      eventEmiter.send({msg: "MostrarOff"});
    else if (instruction[2] && instruction[1] == "on")
      eventEmiter.send({msg: "MostrarOn", id: instruction[2]});
    else if (instruction[2] && instruction[1] == "off")
      eventEmiter.send({msg: "MostrarOff", id: instruction[2]});
  }

  _rutaInstruction(instruction) {
    if (!instruction[2] && instruction[1] == "on")
      eventEmiter.send({msg: "RutaOn"});
    else if (!instruction[2] && instruction[1] == "off")
      eventEmiter.send({msg: "RutaOff"});
    else if (instruction[2] && instruction[1] == "on")
      eventEmiter.send({msg: "RutaOn", id: instruction[2]});
    else if (instruction[2] && instruction[1] == "off")
      eventEmiter.send({msg: "RutaOff", id: instruction[2]});
  }
}