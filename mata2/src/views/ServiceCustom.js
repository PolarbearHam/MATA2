import React, {useEffect, useState} from 'react';
import {Button, Form, FormGroup, Label, Input} from 'reactstrap';
import './ServiceCustom.css'
import { CodeBlock, dracula } from "react-code-blocks";
import { useParams } from 'react-router-dom';
const ServiceCustom = (props) => {
  const saveEvent= (e)=>{
    e.preventDefault();
    const payload=[]
    const event={}
    events.forEach(element => {
      event[element.eventName]={
        base:element.base,
        param:element.params,
        path:element.paths
      }
    });
    console.log(event)
  }

  const test=()=>{
    console.log(fields,events,tags)
  }
  const [events,setEvents] = useState([])
  const [fields, setFields] = useState({service:{},events:events,tags:[]});
  const [tags,setTags]=useState([])
  const [spaChkecked,setSpaChecked]=useState(true)


  const handleAddPath = (index1) => {
    console.log('index는', index1)
    const values = [...events]
    values[index1].paths.push({name:'',index:''})
    setEvents(values)
  };
  const handleRemovePath = (index1,index2) => {
    const values = [...events];
    values[index1].paths.splice(index2, 1);
    setEvents(values);
  };
  const handleAddEvent = () => {
    const values = [...events]
    values.push({eventName:'',base:'',params:[],paths:[]});
    setEvents(values);
  };

  const handleRemoveEvent = (index1) => {
    const values = [...events];
    values.splice(index1, 1);
    setEvents(values);
  };

  const handleChangeName = (index1, event) => {
    const values = [...events];
    values[index1].eventName = event.target.value;
    setEvents(values);
  };
  const handleChangeCondition = (index1, event) => {
    const values = [...events];
    values[index1].base = event.target.value;
    setEvents(values);
  };
  const handleCheckboxChange=(event)=>{
    setSpaChecked(!spaChkecked)
    console.log(spaChkecked)
  }
  const handleAddParam = (index1) => {
    console.log('index는', index1)
    const values = [...events]
    values[index1].params.push({name:'',key:''})
    setEvents(values)
  };
  const handleRemoveParam = (index1,index2) => {
    const values = [...events];
    values[index1].params.splice(index2, 1);
    console.log('잘라낸후',values[index1].params)
    setEvents(values);
  };
  const handleChangeParamName=(index1,index2,e)=>{
    console.log(index1,index2,e.target.value)
    const values=[...events]
    values[index1].params[index2].name=e.target.value
    setEvents(values)
  }
  const handleChangeParamKey=(index1,index2,e)=>{
    console.log(index1,index2,e.target.value)
    const values=[...events]
    values[index1].params[index2].key=e.target.value
    setEvents(values)
  }
  const handleChangePathName=(index1,index2,e)=>{
    const values=[...events]
    values[index1].paths[index2].name=e.target.value
    setEvents(values)
  }
  const handleChangePathIndex=(index1,index2,e)=>{
    const values=[...events]
    values[index1].paths[index2].index=e.target.value
    setEvents(values)
  }



  const handleAddTag = () => {
    const values = [...tags]
    values.push({tagName:'',id:'',class:'',events:[]});
    setTags(values);
  };  
  const handleRemoveTag= (index)=>{
    const values=[...tags]
    values.splice(index,1)
    setTags(values)
  }
  const handleAddTagEvent = (index) => {
    
    const values = [...tags]
    console.log('index는', values[index].events)
    values[index].events.push(null)
    console.log('index는', values[index].events)
    setTags(values)
  };
  const handleRemoveTagEvent = (index,index2) => {
    const values = [...tags];
    values[index].events.splice(index2, 1);
    setTags(values);
  };
  const handleChangeTagName = (index, e) => {
    const values = [...tags];
    values[index].tagName = e.target.value;
    setTags(values);
  };
  const handleChangeTagId = (index, e) => {
    const values = [...tags];
    values[index].id = e.target.value;
    setTags(values);
  };
  const handleChangeTagClass = (index, e) => {
    const values = [...tags];
    values[index].class = e.target.value;
    setTags(values);
  };
  const handleChangeTagEvent = (index,index2, e) => {
    const values = [...tags];
    values[index].events[index2] = e.target.value;
    setTags(values);
  };

  let currentService={}
  const [origin,setOrigin]=useState('')
  const  serviceId  = useParams();
  useEffect(()=>{
    console.log('useeffect시작',serviceId.id ,props.state.serviceList)
    props.state.serviceList.map( (service) => {
      if(serviceId.id==service.id){
        currentService=service
        console.log(currentService)
        setOrigin(currentService.url)
      }else{}
    });
    console.log('useeffect 끝')
  })

  
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
        <button onClick={test}>test</button>
        <Form>
          <div className='bg-white mt-3 p-3 rounded-3xl'>
            <p>서비스 설정</p>
            <FormGroup id='service' className='d-flex flex-column justify-content-center align-items-center gap-3 bg-white rounded Service'>
              <label>
                <Input type='text' placeholder='서비스 주소' value={origin}/>
              </label>
              <label>
                <Input type='checkbox' name='spa'  onChange={handleCheckboxChange}/>
                <span className='ms-2'>SPA로 구성되었다면 체크해주세요.</span>
              </label>
              <Button>스크립트 재발급</Button>
            </FormGroup>
          </div>

          <div className='bg-white mt-3 p-3 rounded-3xl'>
          <p>이벤트 설정</p>
          <button onClick={saveEvent}> 이벤트 저장</button>
          
            <div className='d-flex flex-column justify-content-center align-items-center gap-3 bg-white rounded Service'>
              {events.map((event, index1) => (
                <FormGroup key={index1}>
                  <div>
                    <Button
                      color="danger"
                      onClick={() => handleRemoveEvent(index1)}
                      className="mt-2"
                      key={index1}
                    >
                      삭제
                    </Button>
                    <Button key={index1} color="dark" onClick={() => handleAddParam(index1)}>
                      event{index1}params 추가
                    </Button>
                    <Button color="dark" onClick={() => handleAddPath(index1)}>
                      event{index1}paths 추가
                    </Button>
                  </div>
                  <Input
                    className='mt-1'
                    type="text"
                    name={`eventName-${index1}`}
                    // value={event[index].eventName || ''}
                    onChange={(e) => handleChangeName(index1, e)}
                    placeholder='이벤트 명'
                    value={events[index1].eventName}
                  />
                  
                  <Input
                    className='mt-1'
                    type="text"
                    name={`eventCondition-${index1}`}
                    // value={event[index].base || ''}
                    onChange={(e) => handleChangeCondition(index1, e)}
                    placeholder='이벤트 조건'
                    value={events[index1].base}
                  />
                  <div>
                    event {index1} params 
                  </div>
                  {events[index1].params.map((param,index2)=>(
                    
                    <FormGroup>
                      <Button onClick={()=>handleRemoveParam(index1,index2)}>event{index1} param{index2}삭제</Button>
                      <Input
                       className='mt-1'
                        type="text"
                        name={`paramName-${index2}`}
                        // value={param.name || ''}
                        placeholder='param name'
                        onChange={(e)=>handleChangeParamName(index1,index2,e)}
                        value={events[index1].params[index2].name}
                      />
                      <Input
                       className='mt-1'
                        type="text"
                        name={`paramKey-${index2}`}
                        // value={param.key || ''}
                        placeholder='param key'
                        onChange={(e)=>{handleChangeParamKey(index1,index2,e)}}
                        value={events[index1].params[index2].key}
                      />
                    </FormGroup>
                  ))}
              <div>
                event {index1} paths
              </div>
              {events[index1].paths.map((path,index2)=>(
                    
                    <FormGroup>
                      <Button onClick={()=>{handleRemovePath(index1,index2)}}>event{index1} path {index2}삭제</Button>
                      <Input
                       className='mt-1'
                        type="text"
                        name={`pathName-${index2}`}
                        // value={path.name || ''}
                        placeholder='path name'
                        onChange={(e)=>{handleChangePathName(index1,index2,e)}}
                        value={events[index1].paths[index2].name}
                      />
                      <Input
                       className='mt-1'
                        type="text"
                        name={`pathKey-${index2}`}
                        // value={path.index || ''}
                        placeholder='path index'
                        onChange={(e)=>{handleChangePathIndex(index1,index2,e)}}
                        value={events[index1].paths[index2].index}
                      />
                    </FormGroup>
                  ))}
                </FormGroup>
            ))}
              <Button color="dark" onClick={() => handleAddEvent()}>
                이벤트 추가
              </Button>
            </div>
          </div>

          <div className='bg-white mt-3 p-3 rounded-3xl'>
            <p>태그 설정</p>
            <button>태그 저장</button>
            <div className='d-flex flex-column justify-content-center align-items-center gap-3 bg-white rounded Service'>


              {tags.map((tag, index) => (

                <FormGroup key={index}>
                  <Button
                    color="danger"
                    className="mt-2"
                    onClick={()=>{handleRemoveTag(index)}}
                  >
                    태그 삭제
                  </Button>
                  <Button color="dark" onClick={() => handleAddTagEvent(index)}>
                    tags {index} 이벤트 추가
                  </Button>
                  <div className='inputSet'>
                  <Input
                    type="text"
                    name={`eventName-${index}`}
                    onChange={(e) => handleChangeTagName(index, e)}
                    placeholder='태그명'
                    value={tags[index].tagName}
                  />
                   <Input
                    type="text"
                    name={`eventCondition-${index}`}
                    onChange={(e) => {handleChangeTagId(index,e)} }
                    placeholder='id'
                    value={tags[index].id}
                  />
                   <Input
                    type="text"
                    name={`eventCondition-${index}`}
                    onChange={(e) =>{handleChangeTagClass(index,e)} }
                    placeholder='클래스'
                    value={tags[index].class}
                  />
                  {tags[index].events.map((event,index2)=>(
                    <FormGroup>
                    <Button onClick={()=>{handleRemoveTagEvent(index,index2)}}>tags{index} event {index2}삭제</Button>
                    <Input
                      type="text"
                      name={`eventCondition-${index}`}
                      onChange={(e) => {handleChangeTagEvent(index,index2,e)}}
                      placeholder='이벤트'
                      value={tags[index].events[index2]}
                    />
                    </FormGroup>
                  ))}

                  </div>

                </FormGroup>
              ))}
              <Button color="dark" onClick={() => handleAddTag()}>
                태그 추가
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