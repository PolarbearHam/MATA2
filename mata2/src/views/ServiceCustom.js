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
    <div className='flex d-flex justify-content-center align-items-center'>
      <div>
        <p>토큰 샘플: aaaa-bbbb-cccc</p>
        <button>토큰 발급</button>
      </div>
      

      <Form>
        <p>서비스 설정</p>
        <div>
          <label>
            서비스 주소:
            <input placeholder='서비스 주소'/> 
          </label>
          <label>
            <input type='checkbox' name='spa' value='yes'/>
            SPA로 구성되었다면 체크해주세요.
          </label>
        </div>
        
          
          <label>
            서비스 화면 이미지 캡쳐 파일
            <input type='file'/>
          </label>

        <p>이벤트 설정</p>
        
      
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
      <div>
        <p>스크립트 주소: https://cdn.jsdelivr.net/npm/mata2</p>
        <button>스크립트 발급</button>
      </div>
    </div>
  );
};

export default ServiceCustom;