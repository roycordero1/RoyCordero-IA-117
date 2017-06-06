(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
/**
 * Class Application
 * Manage app general functions
 */
module.exports = 
class Application {
  constructor(urlMapFile) {
    this.map = new CityMap(urlMapFile);
  }
}
},{}],2:[function(require,module,exports){
/*
const eventEmiter = require('event-emiter');
const Bombillo = require ('./bombillo');
const NPC = require ('./npc');
*/
const Application = require('./application');
const CityMap = require('./map');
var app = new Application("public/prueba.txt");
var map = new CityMap("public/prueba.txt");

/**
 * Ciclo principal


setInterval(() => {
  eventEmiter.update();  
  eventEmiter.send("update");
}, 500);
*/

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


},{"./application":1,"./map":3}],3:[function(require,module,exports){
/**
 * Class CityMap
 * Manage map general functions
 */
module.exports = 
class CityMap {
  constructor(urlMapFile) {
    console.log("Entro");
	  this.charMap = this.loadFile(urlMapFile);
  }

  loadFile(urlMapFile) {
  	var xhttp = new XMLHttpRequest();
  	xhttp.onreadystatechange = function() {
  		if (this.readyState == 4 && this.status == 200) {
      	document.getElementById("demo2").innerHTML = this.responseText;
    	}
  	};
  	xhttp.open("GET", urlMapFile, true);
  	xhttp.send();
  }
}
},{}]},{},[2]);
