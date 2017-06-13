/*-------------------------------------------
Progra 2 - IA, Simulación de taxis Karma
Roy Cordero Durán
-------------------------------------------*/

/*-------------------------------------------
// Class Street
// Manage street general functions
-------------------------------------------*/
class Street {

  constructor(id, i, j, ownerMap) {
    this._id = id;
    this._ownerMap = ownerMap;
    this.pos = [i, j];
    this.taxisQuant = 0;
    this.congestion = 0;
  }

  id() {
    return this._id;
  }

  getPos() {
    return this.pos;
  }

  getTaxisQuant() {
    return this.taxisQuant;
  }

  withTaxi() {
    this._state = "contaxi";
  }

  empty() {
    this._state = "vacio";
  }

  addTaxi() {
    this.taxisQuant = this.taxisQuant+1;
    if (this.taxisQuant > 1) {
      this.congestion = this.taxisQuant*0.25
      return this.congestion;
    }
    else
      return 0;
  }

  substractTaxi() {
    this.taxisQuant = this.taxisQuant-1;
  }

  congestionDown() {
    if (this.taxisQuant == 1 && this.congestion>0)
      this.congestion = (this.congestion-0.1).toFixed(2);
  }

}