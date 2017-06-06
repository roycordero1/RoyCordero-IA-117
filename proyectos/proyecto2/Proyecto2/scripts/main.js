/*-------------------------------------------
Progra 2 - IA, Simulación de taxis Karma
Roy Cordero Durán
-------------------------------------------*/

var app = new Application();
var eventEmiter = eventEmiterSingleton.getInstance();

/*
* Wait for file to read the map
*/
document.getElementById('file').onchange = (evt) => {
  var file = evt.target.files[0];
  var reader = new FileReader();
  reader.onload = function(progressEvent) {
    app.loadMap(this.result);
  };
  reader.readAsText(file);
};

setInterval(() => {
  //eventEmiter.update();
  //eventEmiter.send("update");
  app.printMap();
}, 500);


/**
const b1 = new Bombillo("b1");
//eventEmiter.send("apagar");

var flip = 0;
setInterval(() => {
  console.log(`flip: ${flip}`);
  if (flip === 0) {
    eventEmiter.send("encender");
    flip = 1;
  } else {
    eventEmiter.send("apagar");
    flip = 0;
  }
}, 2000);


const npc1 = new NPC("npc1");
//eventEmiter.send("apagar");


var flip = 0;
setInterval(() => {
  console.log(`flip: ${flip}`);
  if (flip === 0) {
    eventEmiter.send("EDA");
    flip = 1;
  }
  else if (flip === 1) {
    eventEmiter.send("Sanando");
    flip = 2;
  }
  else if (flip === 2) {
    eventEmiter.send("Herido");
    flip = 3;
  }
  else if (flip === 3) {
    eventEmiter.send("Herido");
    flip = 4;
  }
  else {
    eventEmiter.send("EFA");
    flip = 5;
  }
}, 2000);
*/

