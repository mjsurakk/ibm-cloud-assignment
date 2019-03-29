const backend = process.env.REACT_APP_BACKEND_BASEURL || '';

export const searchByEquipmentNumber = async (term) => {
  if(term) {
    const url = `${backend}/equipment/${term}`;
    const response = await fetch(url);
    const json = response && response.ok ? await response.json() : [];
    return json;
  } else {
    return [];
  }

};

export const fetchEquipment = async (number) => {
  if(number) {
    const url = `${backend}/equipment/search?limit=${number}`;
    const response = await fetch(url);
    const json = response && response.ok ? await response.json() : [];
    return json;
  } else {
    return [];
  }
};

export const addEquipment = async (equipment) => {
  const url = `${backend}/equipment`;
  const response = await fetch(url, {
    method: 'POST',
    body: JSON.stringify(equipment),
    headers:{
      'Content-Type': 'application/json'
    }
  });
  const json = response && await response.json();
  return json;
};
