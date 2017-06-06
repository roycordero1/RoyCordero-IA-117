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
  	switch (this._validateInstruction(instruction)) {
  		case 1:
  			document.getElementById("message").innerHTML = "Animar"
  			this._animarInstruction(instruction[1])
  			break;
  		default:
  			document.getElementById("message").innerHTML = "Comando no válido!"
  	}
  }

  _validateInstruction(instr) {
  	if (instr[0] == "animar" && instr.length == 2 && typeof parseInt(instr[1]) == "number") {
  			return 1;
  	}
  	return 0;
  }

  _animarInstruction(time) {
  	if (time == 0)
  		this._updateIntervalId()
  	else
  		this._createUpdateInterval(time)
  }

  _createUpdateInterval(time) {
  	if (this._updateIntervalId != 0)
  		this._killInterval(this._updateIntervalId);
  	this._updateIntervalId = setInterval(() => {
		  //eventEmiter.update();
		  //eventEmiter.send("update");
		  app.printMap();
		}, time);
  }

  _killInterval(id) {
  	clearInterval(id);
  }
}