/*-------------------------------------------
Progra 2 - IA, Simulación de taxis Karma
Roy Cordero Durán
-------------------------------------------*/

/*-------------------------------------------
// Class State
// For implement in the specific states
-------------------------------------------*/
class State {

  constructor() {}

  //Validate the message to accept, true if accepts the message
  accepts(event, current) {}

  //Take a decision based on message
  onMessage(event) {}

  //Action to take when state is activated
  onEnter(eventEmitter, fsm) {}
  
  //Each time that cicle is the current
  onUpdate(eventEmitter, fsm) {}

  //Action to take when state is deactivated
  onExit(eventEmitter, fsm) {}
  
}