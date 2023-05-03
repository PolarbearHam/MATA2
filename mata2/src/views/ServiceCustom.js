import React, {useEffect, useState} from 'react';
import {Button, Form, FormGroup, Label, Input} from 'reactstrap';
import './ServiceCustom.css'
import { CodeBlock, dracula } from "react-code-blocks";
import { useParams } from 'react-router-dom';
import axios from 'axios';
const ServiceCustom = (props) => {
  const saveEvent= (e)=>{
    e.preventDefault();
    const payload=[]
    const event={events:[]}
    events.forEach((element,index1) => {
      event.events[index1]={
        eventName:element.eventName,
        eventBase:element.eventBase,
        eventParam:element.eventParams,
        eventPath:element.eventPaths
      }
    }
    )
    
    const url='//localhost:8080/api/v1/project/'+serviceId.id+'/events';
    const headers = {
      "Authorization": `Bearer ${sessionStorage.getItem('accessToken')}`,
      'Content-type': 'application/json',
    }
    console.log("이벤트 설정 저장 시작 보내는 건:", url,event,{headers} )
    axios.post(url,event,{headers})

    .then(response => {
      console.log(response);
      // if (response.status==200) {
      //   sessionStorage.setItem('accessToken',response.data.accessToken)  
      //   navigate('/')
      // }else alert('틀림')
    })
    .catch(error => {
      console.error(error);
      alert(error.data)
    });
    
  }

  const test=()=>{
    console.log(fields,events,tags)
  }
  const [events,setEvents] = useState([])
  const [fields, setFields] = useState({service:{},events:events,tags:[]});
  const [tags,setTags]=useState([])
  const [spaChkecked,setSpaChecked]=useState(true)


  const handleAddPath = (index1) => {
    const values = [...events]
    values[index1].eventPaths.push({pahtName:'',pathIndex:''})
    setEvents(values)
  };
  const handleRemovePath = (index1,index2) => {
    const values = [...events];
    values[index1].eventPaths.splice(index2, 1);
    setEvents(values);
  };
  const handleAddEvent = () => {
    const values = [...events]
    values.push({eventName:'',eventBase:'',eventParams:[],eventPaths:[]});
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
    values[index1].eventBase = event.target.value;
    setEvents(values);
  };
  const handleCheckboxChange=(event)=>{
    setSpaChecked(!spaChkecked)
  }
  const handleAddParam = (index1) => {
    const values = [...events]
    values[index1].eventParams.push({paramName:'',paramKey:''})
    setEvents(values)
  };
  const handleRemoveParam = (index1,index2) => {
    const values = [...events];
    values[index1].eventParams.splice(index2, 1);
    setEvents(values);
  };
  const handleChangeParamName=(index1,index2,e)=>{
    const values=[...events]
    values[index1].eventParams[index2].paramName=e.target.value
    setEvents(values)
  }
  const handleChangeParamKey=(index1,index2,e)=>{
    const values=[...events]
    values[index1].eventParams[index2].paramKey=e.target.value
    setEvents(values)
  }
  const handleChangePathName=(index1,index2,e)=>{
    const values=[...events]
    values[index1].eventPaths[index2].pathName=e.target.value
    setEvents(values)
  }
  const handleChangePathIndex=(index1,index2,e)=>{
    const values=[...events]
    values[index1].eventPaths[index2].pathIndex=e.target.value
    setEvents(values)
  }



  const handleAddTag = () => {
    const values = [...tags]
    values.push({tagName:'',tagOd:'',tagClass:'',tagEvents:[]});
    setTags(values);
  };  
  const handleRemoveTag= (index)=>{
    const values=[...tags]
    values.splice(index,1)
    setTags(values)
  }
  const handleAddTagEvent = (index) => {
    
    const values = [...tags]
    values[index].tagEvents.push(null)
    setTags(values)
  };
  const handleRemoveTagEvent = (index,index2) => {
    const values = [...tags];
    values[index].tagEvents.splice(index2, 1);
    setTags(values);
  };
  const handleChangeTagName = (index, e) => {
    const values = [...tags];
    values[index].tagName = e.target.value;
    setTags(values);
  };
  const handleChangeTagId = (index, e) => {
    const values = [...tags];
    values[index].tagId = e.target.value;
    setTags(values);
  };
  const handleChangeTagClass = (index, e) => {
    const values = [...tags];
    values[index].tagClass = e.target.value;
    setTags(values);
  };
  const handleChangeTagEvent = (index,index2, e) => {
    const values = [...tags];
    values[index].tagEvents[index2] = e.target.value;
    setTags(values);
  };
  const saveTag= (e)=>{
    e.preventDefault();
    const payload=[]
    const tag={tags:[]}
    tags.forEach((element,index) => {
      tag.tags[index]={
        tagName:element.tagName,
        tagId:element.tagId,
        tagClass:element.tagClass,
        tagEvents:element.tagEvents
      }

    }
    );
    console.log(tag)
  }

  let currentService={}
  const [origin,setOrigin]=useState('')
  const  serviceId  = useParams();

  useEffect(()=>{

    props.state.serviceList.map( (service) => {
      if(serviceId.id==service.id){
        currentService=service
        setOrigin(currentService.url)
        console.log('주소 찾음')
        
      }else{console.log('주소 못 찾음')
    }
    });
    const url='//localhost:8080/api/v1/project/'+serviceId.id+'/settings'
    const headers = {
      "Authorization": `Bearer ${sessionStorage.getItem('accessToken')}`,
    }
    axios.get(url,headers)
    .then(res=>{
      console.log('현재 세팅은',res.data)
      setOrigin(res.data.projectDto.url)
      const currentEvents=res.data.eventDtoList
      const newData = currentEvents.map(obj => {
        const { eventParamDtoList,eventPathDtoList, ...rest } = obj;

        return  {eventParams:eventParamDtoList,eventPaths:eventPathDtoList,...rest };
      });
      
      console.log(newData);
      
      console.log('events 저장전',newData);
      setEvents(newData)
      
    })
    .catch(err=>{
      console.log('잘못됨',err)
    })

  // console.log('받은 세팅은',currentSetting)
  // const currentEvents=currentSetting.events
  // setEvents(currentEvents)
  },[])
  

  
  return (
    <div id='form-background' className='flex w-100 justify-content-center'>
      <div className='lg:w-3/5 w-full m-8'>
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
                <Input type='text' placeholder='서비스 주소' defaultValue={origin}/>
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
                  <div className='d-flex flex-auto gap-3'>
                    <Button
                      color="danger"
                      onClick={() => handleRemoveEvent(index1)}
                    
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
                    value={events[index1].eventBase}
                  />
                  <div>
                    event {index1} params 
                  </div>
                  {events[index1].eventParams.map((param,index2)=>(
                    
                    <FormGroup>
                      <div className='d-flex flex-row gap-3 '>
                        <div className='flex-grow'>
                          <Input
                           className='mt-1'
                            type="text"
                            name={`paramName-${index2}`}
                            // value={param.name || ''}
                            placeholder='param name'
                            onChange={(e)=>handleChangeParamName(index1,index2,e)}
                            value={events[index1].eventParams[index2].paramName}
                          />
                          <Input
                           className='mt-1'
                            type="text"
                            name={`paramKey-${index2}`}
                            // value={param.key || ''}
                            placeholder='param key'
                            onChange={(e)=>{handleChangeParamKey(index1,index2,e)}}
                            value={events[index1].eventParams[index2].paramKey}
                          />
                        </div>
                        <Button className='w-1/6' onClick={()=>handleRemoveParam(index1,index2)}>event{index1} param{index2}삭제</Button>
                      </div>
                    </FormGroup>
                  ))}
              <div>
                event {index1} paths
              </div>
              {events[index1].eventPaths.map((path,index2)=>(
                    
                    <FormGroup>
                      <div className='d-flex flex-row gap-3'>
                        <div className='flex-grow '> 
                          <Input
                           className='mt-1'
                            type="text"
                            name={`pathName-${index2}`}
                            // value={path.name || ''}
                            placeholder='path name'
                            onChange={(e)=>{handleChangePathName(index1,index2,e)}}
                            value={events[index1].eventPaths[index2].pathName}
                          />
                          <Input
                           className='mt-1'
                            type="text"
                            name={`pathKey-${index2}`}
                            // value={path.index || ''}
                            placeholder='path index'
                            onChange={(e)=>{handleChangePathIndex(index1,index2,e)}}
                            value={events[index1].eventPaths[index2].pathIndex}
                          />
                        </div>
                        <Button className='w-1/6' onClick={()=>{handleRemovePath(index1,index2)}}>event{index1} path {index2}삭제</Button>
                      </div>

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
            <button onClick={saveTag}>태그 저장</button>
            <div className='d-flex flex-column justify-content-center align-items-center gap-3 flex-auto bg-white rounded Service'>


              {tags.map((tag, index) => (

                <FormGroup key={index}>
                  <div className='d-flex flex-auto gap-3'>
                    <Button
                      color="danger"
                      onClick={()=>{handleRemoveTag(index)}}
                    >
                      태그 삭제
                    </Button>
                    <Button color="dark" onClick={() => handleAddTagEvent(index)}>
                      tags {index} 이벤트 추가
                    </Button>
                  </div>
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
                    value={tags[index].tagId}
                  />
                   <Input
                    type="text"
                    name={`eventCondition-${index}`}
                    onChange={(e) =>{handleChangeTagClass(index,e)} }
                    placeholder='클래스'
                    value={tags[index].tagClass}
                  />
                  {tags[index].tagEvents.map((event,index2)=>(
                    <FormGroup className='d-flex flex-row gap-3'> 
                    
                    <Input
                      type="text"
                      name={`eventCondition-${index}`}
                      onChange={(e) => {handleChangeTagEvent(index,index2,e)}}
                      placeholder='이벤트'
                      value={tags[index].tagEvents[index2]}
                    />
                    <Button className='w-1/6 h-1/1' onClick={()=>{handleRemoveTagEvent(index,index2)}}> 삭제</Button>
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