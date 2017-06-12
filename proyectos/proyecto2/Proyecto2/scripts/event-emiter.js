/*-------------------------------------------
Progra 2 - IA, Simulación de taxis Karma
Roy Cordero Durán
-------------------------------------------*/
var eventEmiterSingleton = (() => {

  var instance;
  function createInstance() {
      var object = new EventEmitter();
      return object;
  }

  return {
    getInstance: function () {
        if (!instance) {
            instance = createInstance();
        }
        return instance;
    }
  };
})();

/**
* Singleton object
* FSM subscribe to receive messages
* Se encarga de recibir y despachar los eventos del sistema
* Los mensajes generados dentro del ciclo se despachan hasta el siguiente ciclo
**/
class EventEmitter {
    
  constructor() {
    this._listeners = [];
    this._listenersById = new Map();
    this._queue = [];
  }
  /**
   * Despachar los mensajes que esten en la cola
   */
  update() {
    console.log(`[Update] EventEmitter ${this._queue.length}`);
    for(;this._queue.length > 0;) {
      this._queue.shift()();
    }
  }
  /**
   * Registrar objetos con capcidad de recibir mensajes. listener.onMessage(event)
   */
  register(listener) {
    this._listenersById.set(listener.id(), listener);
    this._listeners.push(listener);
  }
  /**
   * Enviar mensaje, 
   * Si el id es no se especifica se envia a todos los objetos 
   */
  send(params) {
    this._send({
      id: params.id,
      msg: params.msg,
      param1: params.param1,
      param2: params.param2
    });
  }
  /**
   * processo interno para enviar el mensaje.
   */
  _send(event) {      
    if (event.id) {
      this._sendPrivateMessage(event);
    } else {
      this._sendToAll(event);
    }
  }
  /**
   * Enviar a un objeto especifico
   */
  _sendPrivateMessage(event) {
    if (this._listenersById.has(event.id)) {
      const self = this;
      this._addToQueue(() => {
        const fsm = self._listenersById.get(event.id);
        fsm.onMessage(this, event);
      });
    } else {
      console.log("Agente desconocido " + event.id + "Mensaje " + JSON.stringify(event));
    }
  }
  /**
   * Enviar a todos los objetos
   */
  _sendToAll(event) {
    const self = this;
    this._addToQueue(() => {
      self._listeners.forEach((l) => l.onMessage(this, event))
    });
  }

  _addToQueue(action) {
    this._queue.push(action);
  }    
}

var eventEmiter = eventEmiterSingleton.getInstance();