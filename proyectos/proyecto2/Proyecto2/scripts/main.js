/*-------------------------------------------
Progra 2 - IA, Simulación de taxis Karma
Roy Cordero Durán
-------------------------------------------*/
var app = new Application();

/*-------------------------------------------
// Wait for file to read the map
-------------------------------------------*/
document.getElementById('file').onchange = (evt) => {
  var file = evt.target.files[0];
  var reader = new FileReader();
  reader.onload = function(progressEvent) {
    app.loadMap(this.result);
    app.printMap();
  };
  reader.readAsText(file);
};