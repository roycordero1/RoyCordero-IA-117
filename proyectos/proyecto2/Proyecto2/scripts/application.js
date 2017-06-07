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
  		case 1:
  			document.getElementById("message").innerHTML = "Animar"
  			this._animarInstruction(instruction[1])
  			break;
      case 2:
        document.getElementById("message").innerHTML = "Pasear"
        this._pasearInstruction(instruction)
        break;
  		default:
  			document.getElementById("message").innerHTML = "Comando no válido!"
  	}
  }

  _validateInstruction(instr) {
  	if (instr[0] == "animar" && instr.length == 2 && typeof parseInt(instr[1]) == "number") {
  		return 1;
  	}
    else if (instr[0] == "pasear") {
       return 2;
    }
  	return 0;
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
    if (!instruction[1]) {
      eventEmiter.send("Pasear");
      console.log("Pasear todos")
    }      
    else
      eventEmiter.send("Pasear", instruction[1]);
  }
}