import React, { Component } from 'react';
import { Button, Checkbox, Form, Modal, Icon } from 'semantic-ui-react';
import { DateInput } from 'semantic-ui-calendar-react';
import {addEquipment} from "./equipmentActions";
import * as moment from 'moment';

class AddEquipmentModal extends Component {

  constructor(props) {
    super(props);
    this.state = this.initialState();
  }
  dateFormat = 'YYYY-MM-DD';

  initialState = () => {
    return {
      showModal: false,
      inProgress: false,
      errors: {},
      message: null,
      equipment: {
        equipmentNumber: '',
        address: '',
        contractStartDate: '',
        contractEndDate: '',
        status: 'Stopped'
      }
    }
  };

  handleInput = (event) => {
    const { value, name } = event.target;
    const err = this.state.errors;
    const eq = this.state.equipment;
    eq[name] = value;
    err[name] = null;
    this.setState({
      equipment: eq,
      errors: err
    });
  };

  handleDateInput = (event, {name, value}) => {
    const eq = this.state.equipment;
    const date = moment(value);
    if(date.isValid()) {
      eq[name] = value;
    }
    this.setState({
      equipment: eq
    });
  };

  handleStatusChange = () => {
    const newStatus = this.state.equipment.status === 'Running' ? 'Stopped': 'Running';
    this.setState({
      equipment: {
        ...this.state.equipment,
        status: newStatus
      }
    });
  };

  equipmentHasRequiredData = () => {
    return Object.keys(this.state.equipment).every((key) => {
      return this.state.equipment[key];
    })
  };

  submit = async () => {
    this.setState({
      inProgress: true
    });
    const resp = await addEquipment(this.state.equipment);

    if(resp.error === 'duplicateEquipmentNumber') {
      this.setState({
        inProgress: false,
        errors: {
          equipmentNumber: 'Duplicate equipment number'
        }
      });
    } else if(resp.error){
      // todo: other possible error conditions should be handled here
      this.setState({
        inProgress: false
      });
    } else {
      this.setState({
        message: 'Equipment was added'
      });
      setTimeout(() => {
        this.setState(this.initialState());
      }, 1500);
    }
  };

  render() {
    return (
        <Modal open={this.state.showModal} trigger={<Button onClick={() => this.setState({ showModal: true })}>Add equipment</Button>}>
          <Modal.Header>Add equipment</Modal.Header>
          <Modal.Content>
            <Form>
              {this.state.message && (
              <div className="ui positive message">
                <div className="header">{this.state.message}</div>
              </div>)}
              <Form.Field className={this.state.errors['equipmentNumber'] ? 'error' : ''}>
                <label>Equipment number</label>
                <input placeholder='Equipment number' name='equipmentNumber' value={this.state.equipment.equipmentNumber} onChange={this.handleInput}/>
                {this.state.errors['equipmentNumber'] && <label>{this.state.errors['equipmentNumber']}</label>}
              </Form.Field>
              <Form.Field>
                <label>Address</label>
                <input placeholder='Address' name='address' value={this.state.equipment.address} onChange={this.handleInput}/>
              </Form.Field>
              <Form.Field>
                <label>Contract start date</label>
                <DateInput
                    name="contractStartDate"
                    placeholder="Contract start date"
                    value={this.state.equipment.contractStartDate}
                    dateFormat={this.dateFormat}
                    icon=""
                    onChange={this.handleDateInput}
                />
              </Form.Field>
              <Form.Field>
                <label>Contract end date</label>
                <DateInput
                    name="contractEndDate"
                    placeholder="Contract end date"
                    value={this.state.equipment.contractEndDate}
                    dateFormat={this.dateFormat}
                    icon=""
                    onChange={this.handleDateInput}
                />
              </Form.Field>
              <Form.Field>
                <Checkbox label='Running' name='status' checked={this.state.equipment.status === 'Running'} onChange={this.handleStatusChange}/>
              </Form.Field>
            </Form>
          </Modal.Content>
          <Modal.Actions>
            <Button basic color='red' onClick={() => this.setState(this.initialState())}>
              Cancel
            </Button>
            <Button className={this.state.inProgress ? 'ui loading button' : 'ui button'} type='submit' color='green' disabled={!this.equipmentHasRequiredData()} onClick={this.submit}>
              Add
            </Button>
          </Modal.Actions>
        </Modal>
    );
  }
};

export default AddEquipmentModal;