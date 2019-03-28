import React, { Component } from 'react';

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
      <table>
        <thead>
          <tr>
            <th>Equipment number</th>
            <th>Address</th>
            <th>Contract start</th>
            <th>Contract end</th>
            <th>Status</th>
            </tr>
        </thead>
        <tbody>{this.props.equipment.map(this.renderEquipmentRow)}</tbody>
      </table>
    );
  }
}

class SearchBar extends Component {

  constructor(props) {
    super(props);
    this.state = {
      search: ''
    };
  }

  limitChanged = (event) => {
    this.props.fetch(event.target.value);
  };

  searchChanged = (event) => {
    this.props.search(event.target.value);
  };

  render() {
    return (
      <div>
        <input type="text" placeholder="Limit..." onChange={this.limitChanged} />
        <input type="text" placeholder="Search..." onChange={this.searchChanged} />
      </div>
    );
  }
}

const backend = process.env.REACT_APP_BACKEND_BASEURL || '';

class EquipmentTableContainer extends Component {

  constructor(props) {
    super(props);
    this.state = {
      equipment: []
    };
  }

  searchByEquipmentNumber = async (term) => {
    const url = `${backend}/equipment/${term}`;

    const response = await fetch(url);
    const json = response && await response.json();
    this.setState({
      equipment: json
    });
  };

  fetchEquipment = async (number) => {
    const url = `${backend}/equipment/search?limit=${number}`;

    const response = await fetch(url);
    const json = response && await response.json();
    this.setState({
      equipment: json
    });
  };

  render() {
    return (
      <div>
        <SearchBar search={this.searchByEquipmentNumber} fetch={this.fetchEquipment}/>
        <EquipmentTable {...this.state}/>
      </div>
    );
  }
}

export default EquipmentTableContainer;