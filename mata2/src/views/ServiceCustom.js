import React, {useState} from 'react';
import {Button, Form, FormGroup, Label, Input} from 'reactstrap';
import './ServiceCustom.css'
import { CodeBlock, dracula } from "react-code-blocks";

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
        <div className='bg-white mt-3 p-3 rounded-3xl'>
          <p>스크립트</p>
          <div>
            { !fields.spa ? (
              <>
                <span>BrowserRouter를 최상단(보통 index.js)에 정의합니다.</span>
                <CodeBlock
                  text={
`const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <BrowserRouter>
            <App/>
        </BrowserRouter>
  </React.StrictMode>
);`}
                  language={"jsx"}
                />
                <hr/>
                <span>최상단 컴포넌트(일반적으로 App.js)에 다음과 같이 추가합니다.</span>
                <CodeBlock
                  text={
`import TagManager from "./assets/tagmanager";

const mata = new TagManager();

function App() {
const location = useLocation();
  useEffect(() => {
    mata.attach();
    return () => {
      mata.detach();
    }
  }, [location])

//...코드`}
                  language={"jsx"}
                />
              </> ) : (
              <>
                <span>head 태그 아래에 다름과 같이 스크립트를 추가합니다.</span>
                <CodeBlock
                  text={
`<head>
    <!-- Your head contents -->
</head>

<!-- MATA Tag Manager -->
<script src="tagmanager.js" type="module"></script>`
              }
                  language={"html"}
                />
              </>
            ) }
          </div>
        </div>

        <Form>
          <div className='bg-white mt-3 p-3 rounded-3xl'>
            <p>서비스 설정</p>
            <FormGroup id='service' className='d-flex flex-column justify-content-center align-items-center gap-3 bg-white rounded Service'>
              <label>
                <Input type='text' placeholder='서비스 주소'/>
              </label>
              <label>
                <Input type='checkbox' name='spa'/>
                <span className='ms-2'>SPA로 구성되었다면 체크해주세요.</span>
              </label>
              <Button>스크립트 재발급</Button>
            </FormGroup>
          </div>

          <div className='bg-white mt-3 p-3 rounded-3xl'>
          <p>이벤트 설정</p>
            <div className='d-flex flex-column justify-content-center align-items-center gap-3 bg-white rounded Service'>
              {fields.map((field, index) => (
                <FormGroup key={index}>
                  <Button
                    color="danger"
                    onClick={({index}) => handleRemoveField(index)}
                    className="mt-2"
                  >
                    삭제
                  </Button>
                  <Input
                    className='mt-1'
                    type="text"
                    name={`eventName-${index}`}
                    value={field[0].value || ''}
                    onChange={(e) => handleChangeName(index, e)}
                    placeholder='이벤트 명'
                  />
                   <Input
                     className='mt-1'
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
          </div>

          <div className='bg-white mt-3 p-3 rounded-3xl'>
            <p>태그 설정</p>
            <div className='d-flex flex-column justify-content-center align-items-center gap-3 bg-white rounded Service'>


              {fields.map((field, index) => (

                <FormGroup key={index}>
                  <Button
                    color="danger"
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
          </div>

        </Form>
        <div className=' d-flex flex-column justify-content-center align-items-center'>
          <Button color="primary">설정 저장</Button>
        </div>
      </div>
    </div>
  );
};

export default ServiceCustom;