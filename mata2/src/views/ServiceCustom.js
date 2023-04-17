import React, {useState} from 'react';
import {Button, Form, FormGroup, Label, Input} from 'reactstrap';

const ServiceCustom = () => {
  const [fields, setFields] = useState([{value: null}]);

  const handleAddField = () => {
    const values = [...fields];
    values.push({value: null});
    setFields(values);
  };

  const handleRemoveField = (index) => {
    const values = [...fields];
    values.splice(index, 1);
    setFields(values);
  };

  const handleChange = (index, event) => {
    const values = [...fields];
    values[index].value = event.target.value;
    setFields(values);
  };

  return (
    <div>
      <Form>
        {fields.map((field, index) => (
          <FormGroup key={index}>
            <Label for={`field-${index}`}>필드 #{index + 1}</Label>
            <Input
              type="text"
              name={`field-${index}`}
              value={field.value || ''}
              onChange={(e) => handleChange(index, e)}
            />
            <Button
              color="danger"
              onClick={() => handleRemoveField(index)}
              className="mt-2"
            >
              삭제
            </Button>
          </FormGroup>
        ))}
        <Button color="primary" onClick={() => handleAddField()}>
          추가
        </Button>
      </Form>
    </div>
  );
};

export default ServiceCustom;