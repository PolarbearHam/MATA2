import React, {useState} from 'react';
import {Button, Form, FormGroup, Label, Input} from 'reactstrap';
import './ServiceCustom.css'

const ServiceCustom = () => {
  const [fields, setFields] = useState([[{value: null},{value: null}]]);

  const handleAddField = () => {
    const values = [...fields];
    values.push([{value: null},{value: null}]);
    setFields(values);
  };

  const handleRemoveField = (index) => {
    const values = [...fields];
    values.splice(index, 1);
    setFields(values);
  };

  const handleChangeName = (index, event) => {
    const values = [...fields];
    values[index][0].value = event.target.value;
    setFields(values);
  };
  const handleChangeCondition = (index, event) => {
    const values = [...fields];
    values[index][1].value = event.target.value;
    setFields(values);
  };

  return (
    <div id='form-background' className='flex w-100 justify-content-center'>
      <div className='lg:w-1/2 w-full m-8'>
        <div className=''>
          <p>스크립트</p>
          <button>토큰 발급</button>
        </div>

        <Form>
          <p>서비스 설정</p>
          <FormGroup id='service' className='d-flex flex-column justify-content-center align-items-center gap-3 bg-white rounded Service'>

          <label>

            <input type='text' placeholder='서비스 주소'/>
          </label>
          <label>
            <input type='checkbox' name='spa' value='yes'/>
            SPA로 구성되었다면 체크해주세요.
          </label>
          <label>
            서비스 화면 이미지 캡쳐 파일
            <input type='file'/>
          </label>
          <label>

            <input type='text'   placeholder='타겟 페이지'/>
          </label>
          </FormGroup>
          <br/>

          <p>이벤트 설정</p>
          <div className='d-flex flex-column justify-content-center align-items-center gap-3 bg-white rounded Service'>
            {fields.map((field, index) => (

              <FormGroup key={index}>
                <Label for={`field-${index}`}>이벤트 #{index + 1}</Label>
                <Button
                  color="light"
                  onClick={({index}) => handleRemoveField(index)}
                  className="mt-2"
                >
                  삭제
                </Button>
                <Input
                  type="text"
                  name={`eventName-${index}`}
                  value={field[0].value || ''}
                  onChange={(e) => handleChangeName(index, e)}
                  placeholder='이벤트 명'
                />
                 <Input
                  type="text"
                  name={`eventCondition-${index}`}
                  value={field[1].value || ''}
                  onChange={(e) => handleChangeCondition(index, e)}
                  placeholder='이벤트 조건'
                />

              </FormGroup>
            ))}
            <Button color="dark" onClick={() => handleAddField()}>
              추가
            </Button>
          </div>
          <br/>
          <p>태그 설정</p>
          <div className='d-flex flex-column justify-content-center align-items-center gap-3 bg-white rounded Service'>


            {fields.map((field, index) => (

              <FormGroup key={index}>
                <Label for={`field-${index}`}>태그 #{index + 1}</Label>
                <Button
                  color="light"
                  onClick={({index}) => handleRemoveField(index)}
                  className="mt-2"
                >
                  삭제
                </Button>
                <div className='inputSet'>
                <Input
                  type="text"
                  name={`eventName-${index}`}
                  value={field[0].value || ''}
                  onChange={(e) => handleChangeName(index, e)}
                  placeholder='태그명'
                />
                 <Input
                  type="text"
                  name={`eventCondition-${index}`}
                  value={field[1].value || ''}
                  onChange={(e) => handleChangeCondition(index, e)}
                  placeholder='이벤트 명'
                />
                </div>

              </FormGroup>
            ))}
            <Button color="dark" onClick={() => handleAddField()}>
              추가
            </Button>
          </div>

        </Form>
        <div className=' d-flex flex-column justify-content-center align-items-center'>
          <div>스크립트 주소: https://cdn.jsdelivr.net/npm/mata2</div>
          <Button color="primary">설정 저장 및 스크립트 발급</Button>
        </div>
      </div>
    </div>
  );
};

export default ServiceCustom;