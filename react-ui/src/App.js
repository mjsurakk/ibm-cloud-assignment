import React, {Component} from 'react';
import './App.css';
import EquipmentTableContainer from './components/EquipmentTable';

class App extends Component {

  render() {

    return (
      <div className="App">
        <EquipmentTableContainer/>
      </div>
    );
  }
}

export default App;
