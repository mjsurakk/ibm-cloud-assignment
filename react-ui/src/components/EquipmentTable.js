import React, { Component } from 'react';
import AddEquipmentModal from './AddEquipmentModal';
import * as ea from "./equipmentActions";

class EquipmentTable extends Component {

  renderEquipmentRow = (equipment) => {
    return (
        <tr key={equipment.equipmentNumber}>
          <td>{equipment.equipmentNumber}</td>
          <td>{equipment.address}</td>
          <td>{equipment.contractStartDate}</td>
          <td>{equipment.contractEndDate}</td>
          <td>{equipment.status}</td>
        </tr>
    );
  };

  render() {
    return (
      <table className="ui celled table">
        <thead>
          <tr>
            <th>Equipment number</th>
            <th>Address</th>
            <th>Contract start</th>
            <th>Contract end</th>
            <th>Status</th>
            </tr>
        </thead>
        <tbody>
          {this.props.equipment.map(this.renderEquipmentRow)}
          {this.props.fetching && (
          <tr className='ui placeholder'>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>)}
        </tbody>
      </table>

    );
  }
}

class EquipmentActionBar extends Component {

  limitChanged = (event) => {
    this.props.fetchEquipment(event.target.value);
  };

  searchChanged = (event) => {
    this.props.search(event.target.value);
  };

  render() {
    return (
      <div className="ui three column grid container">
        <div className="ui column input">
          <input type="number" min="0" placeholder="Limit..." onChange={this.limitChanged} />
        </div>
        <div className="ui column input">
          <input type="text" placeholder="Search..." onChange={this.searchChanged} />
        </div>
        <div className="ui column">
          <AddEquipmentModal/>
        </div>
      </div>
    );
  }
}

class EquipmentTableContainer extends Component {

  constructor(props) {
    super(props);
    this.state = {
      equipment: [],
      fetching: false
    };
  }

  searchByEquipmentNumber = async (term) => {
    this.setState({
      fetching: true
    });
    const json = await ea.searchByEquipmentNumber(term);
    this.setState({
      equipment: json,
      fetching: false
    });
  };

  fetchEquipment = async (number) => {
    this.setState({
      fetching: true
    });
    const json = await ea.fetchEquipment(number);
    this.setState({
      equipment: json,
      fetching: false
    });
  };

  render() {
    return (
      <div className="ui column container">
        <EquipmentActionBar search={this.searchByEquipmentNumber} fetchEquipment={this.fetchEquipment}/>
        <EquipmentTable {...this.state}/>
      </div>
    );
  }
}

export default EquipmentTableContainer;