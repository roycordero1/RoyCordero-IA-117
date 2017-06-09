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
  	return "Invalid";
  }

  _animarInstruction(time) {
  	if (time == 0) {
  		this._killInterval(this._updateIntervalId);
      eventEmiter.send("Detener");
    }
  	else
  		this._createUpdateInterval(time);
  }

  _createUpdateInterval(time) {
  	if (this._updateIntervalId != 0)
  		this._killInterval(this._updateIntervalId);
  	this._updateIntervalId = setInterval(() => {
		  eventEmiter.update();
		  eventEmiter.send("update");
		  app.printMap();
		}, time);
  }

  _killInterval(id) {
  	clearInterval(id);
  }

  _pasearInstruction(instruction) {
    if (!instruction[1])
      eventEmiter.send("Pasear");
    else
      eventEmiter.send("Pasear", instruction[1]);
  }

  _buscarInstruction(instruction) {
    if (!instruction[1])
      eventEmiter.send("Buscar");
    else
      eventEmiter.send("Buscar", instruction[1]);
  }
}