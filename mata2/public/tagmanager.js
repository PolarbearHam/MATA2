// *************** JS에 주입돼서 들어가는 영역 ***************
let injection = {
  bootstrap: 'dummy-bootstrap',
  serviceToken: 'dummy-serviceToken',
  events: [
    {name: 'click', dom: null, param: null, path: null},
    {name: 'mouseenter', dom: null, param: null, path: null},
    {name: 'mouseleave', dom: null, param: null, path: null},
    {name: 'scroll', dom: null, param: null, path: null},
    {name: 'pageenter', dom: null, param: null, path: null},
    {name: 'pageleave', dom: null, param: null, path: null},
    {name: 'login', dom: 'click',
      param: null,
      path: [
        {name: "userId", index: 2}
      ]
    }, // param: 쿼리스트링으로 전달되는 데이터, path: path로 전달되는 데이터의 인덱스
    {name: 'purchase', dom: 'click',
      param: [
        {name: "productName", key: "product"}
      ],
      path: [
        {name: "productId", index: 3}
      ]},
  ],
  tags: [
    {id: 'button', class: '', events: ['click', 'login']},
    {id: 'button2', class: 'primary', events: ['purchase']}
  ]
}
// *************** JS에 주입돼서 들어가는 영역 ***************

let enterTimer = Date.now();
if (!sessionStorage.getItem('TAGMANAGER_SESSION')) {
  let randomValue = Math.floor(Math.random() * (Math.pow(2, 52) - 1));
  sessionStorage.setItem('TAGMANAGER_SESSION', randomValue)
}
const sessionId = sessionStorage.getItem('TAGMANAGER_SESSION')
const bootstrap = injection.bootstrap;
const serviceToken = injection.serviceToken;
const events = injection.events;
const tags = injection.tags;
const location = document.location;
const referrer = document.referrer;
const pageDuration = 0;
const data = {};

let logStash = [];

const customEvents = {}; // 사용자 커스텀 이벤트

const urlStr = document.location;
const url = new URL(urlStr);
const urlParams = url.searchParams;
const pathArray = document.location.pathname.split('/');

for(let i=0; i<events.length; i++) {
  let detail = {};
  for(let d=0; d<events[i].param.length; d++) {
    detail[events[i].param[d].name] = urlParams.get(events[i].param[d].key);
  }
  for(let p=0; p<events[i].path.length; p++) {
    detail[events[i].path[p].name] = pathArray[events[i].path[p].index];
  }
  if(!domEvents.includes(events[i].name)) { // 커스텀 이벤트일 때
    customEvents[events[i].name] = new CustomEvent(events[i], {
      detail: detail,
      bubbles: true,
      cancelable: true
    });
  }
}

const handleEvents = function (e) {
  this.stackLog(e, e.type);
}
const handlePageenter = function (e) {
  this.stackLog(e, 'pageenter');
  this.flushLog();
}
const handlePageleave = function (e) {
  this.stackLog(e, 'pageleave');
  this.flushLog();
}

const flushLog = function () {
  fetch(bootstrap, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(logStash)
  })
  logStash = [];
}

const stackLog = function (e, eventType = '') {
  let body = {
    serviceToken: serviceToken,
    sessionId: sessionId,
    event: eventType,
    targetId: (e && e.target && e.target.id) ? e.target.id : 'none',
    positionX: e && e.pageX ? e.pageX : null,
    positionY: e && e.pageY ? e.pageY : null,
    location: location,
    referrer: referrer,
    timestamp: Date.now(),
    pageDuration: Date.now() - enterTimer,
    data: e.detail
  }
  logStash.push(body)
}

// TODO: ID랑 class로 element 찾고 event 갖다 붙이기
const attach = function () {
  for(let i=0; i<tags.length; i++) {

  }
  let elementById = document.querySelectorAll(this.selector);
  elements.forEach((elem) => {
    for (let i in this.events) {
      elem.addEventListener(this.events[i], this.eventDictionary[this.events[i]])
    }
  })
  window.dispatchEvent(customEvents['pageenter'])
}

const detach = function() {
  let elements = document.querySelectorAll(this.selector);
  elements.forEach((elem) => {
    for (let i in this.events) {
      elem.removeEventListener(this.events[i], this.eventDictionary[this.events[i]])
    }
  })
  this.prevLocation = this.location;
  this.stackLog(null, "pageleave");
  this.flushLog();
}

attach();
window.addEventListener("beforeunload", (e) => {
  detach();
  console.log("unload")
});