// *************** JS에 주입돼서 들어가는 영역 ***************

export default class TagManager {

  constructor() {
    // *************** JS에 주입돼서 들어가는 영역 ***************
    this.injection = {
      bootstrap: 'https://dummy-bootstrap.com',
      serviceToken: 'dummy-serviceToken',
      events: {
        click: {dom: null, param: [], path: []},
        mouseenter: {dom: null, param: [], path: []},
        mouseleave: {dom: null, param: [], path: []},
        scroll: {dom: null, param: [], path: []},
        login: {
          dom: 'click',
          param: [],
          path: [
            {name: "userId", index: 2}
          ]
        }, // param: 쿼리스트링으로 전달되는 데이터, path: path로 전달되는 데이터의 인덱스
        purchase: {
          dom: 'click',
          param: [
            {name: "productName", key: "product"}
          ],
          path: [
            {name: "productId", index: 3}
          ]
        },
        click_mata: {
          dom: 'click',
          param: [
            {name: "productId", key: 'productId'}
          ],
          path: []
        },
        click_main: {
          dom: 'click',
          param: [
            {name: "productId", key: 'productId'},
            {name: "userId", key: 'userId'}
          ],
          path: []
        },
        click_header: {
          dom: 'click',
          param: [
            {name: "productId", key: 'productId'},
            {name: "userId", key: 'userId'}
          ],
          path: []
        },
      },
      tags: {
        button1: {id: 'button', class: '', events: ['click', 'login']},
        button2: {id: 'button2', class: 'primary', events: ['purchase']},
        mata: {id: 'MATA', class: '', events: ['click', 'click_mata']},
        main: {id: 'main', class: null, events: ['click_main']},
        header: {id: null, class: 'flex items-center', events: ['click_header']},
      }
    }
    // *************** JS에 주입돼서 들어가는 영역 ***************

    // TODO: meta에 referrer 전달 로직 추가

    // 주입 데이터 unstructured
    if (!sessionStorage.getItem('TAGMANAGER_SESSION')) {
      let randomValue = Math.floor(Math.random() * (Math.pow(2, 52) - 1));
      sessionStorage.setItem('TAGMANAGER_SESSION', randomValue)
    }
    this.sessionId = sessionStorage.getItem('TAGMANAGER_SESSION');
    this.bootstrap = this.injection.bootstrap;
    this.serviceToken = this.injection.serviceToken;
    this.events = this.injection.events;
    this.tags = this.injection.tags;
    this.location = document.location;
    this.referrer = document.referrer;
    this.pageDuration = 0;
    this.data = {};

    // 추가적으로 필요한 데이터
    this.logStash = [];
    this.enterTimer = Date.now();
    this.baseEvents = ['click', 'mouseenter', 'mouseleave', 'scroll', 'load', 'unload'];

    // 이벤트 딕셔너리 초기화
    this.eventDict = {};
    const urlStr = document.location;
    const url = new URL(urlStr);
    const urlParams = url.searchParams;
    const pathArray = document.location.pathname.split('/');
    for (name of Object.keys(this.events)) {
      let detail = {};
      for (let d = 0; d < this.events[name].param.length; d++) {
        detail[this.events[name].param[d].name] = urlParams.get(this.events[name].param[d].key);
      }
      for (let p = 0; p < this.events[name].path.length; p++) {
        detail[this.events[name].path[p].name] = pathArray[this.events[name].path[p].index];
      }
      this.eventDict[name] = new CustomEvent(name, {
        detail: detail,
        bubbles: true,
        cancelable: true
      });
    }




    // 이벤트 핸들러 딕셔너리 초기화
    this.handlerDict = {};
    this.handlerDict['pageenter'] = function (e) {
      this.stackLog(e, 'pageenter');
      this.flushLog();
    }.bind(this);
    this.handlerDict['pageleave'] = function (e) {
      this.stackLog(e, 'pageenter');
      this.flushLog();
    }.bind(this);
    for (name of Object.keys(this.events)) {
      this.handlerDict[name] = function (e) {
        console.log(e)
        this.stackLog(e, e.type);
      }.bind(this)
    }

    // 로그 적재, 전송 로직
    this.flushLog = function () {
      fetch(this.bootstrap, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.logStash)
      })
      this.logStash = [];
    }
    this.stackLog = function (e, eventType = '') {
      let body = {
        serviceToken: this.serviceToken,
        sessionId: this.sessionId,
        event: eventType,
        targetId: (e && e.target && e.target.id) ? e.target.id : 'none',
        positionX: e && e.pageX ? e.pageX : null,
        positionY: e && e.pageY ? e.pageY : null,
        location: this.location.href,
        referrer: this.referrer,
        timestamp: Date.now(),
        pageDuration: Date.now() - this.enterTimer,
        data: e.detail ? e.detail : null
      }
      this.logStash.push(body)

      console.log(this.logStash);
    }

    // Tagmanager 부착/제거 로직
    this.attach = function () {
      let keys = Object.keys(this.tags);
      for (let i=0; i<keys.length; i++) { // 모든 태그 중
        if (this.tags[keys[i]].id) { // ID로 태그 찾기
          let tagById = document.querySelector('#' + this.tags[keys[i]].id);
          if (!tagById) continue;
          for (let e = 0; e < this.tags[keys[i]].events.length; e++) {
            if (!this.baseEvents.includes(this.tags[keys[i]].events[e])) { // 사용자 커스텀 이벤트라면
              tagById.addEventListener(this.events[this.tags[keys[i]].events[e]].dom, function () { // base DOM 이벤트에 dispatcher 붙이기
                tagById.dispatchEvent(this.eventDict[this.tags[keys[i]].events[e]]);
              }.bind(this));
            }
            tagById.addEventListener(this.tags[keys[i]].events[e], this.handlerDict[this.tags[keys[i]].events[e]]); // 해당 eventHandler 붙이기
          }
        } else if (this.tags[keys[i]].class) { // ID가 없으면 class로 태그 찾기
          let tagsByClass = document.querySelectorAll('.' + this.tags[keys[i]].class.replace(" ", "."));
          if (!tagsByClass) continue;
          tagsByClass.forEach((tagByClass) => {
            for (let e = 0; e < this.tags[keys[i]].events.length; e++) {
              if (!this.baseEvents.includes(this.tags[keys[i]].events[e])) { // 사용자 커스텀 이벤트라면
                tagByClass.addEventListener(this.events[this.tags[keys[i]].events[e]].dom, function () { // base DOM 이벤트에 dispatcher 붙이기
                  tagByClass.dispatchEvent(this.eventDict[this.tags[keys[i]].events[e]]);
                }.bind(this));
              }
              tagByClass.addEventListener(this.tags[keys[i]].events[e], this.handlerDict[this.tags[keys[i]].events[e]]); // 해당 eventHandler 붙이기
            }
          });
        } else { // 모든 element

        }
      }
      // 태그에 종속되지 않는 이벤트 발생시키기
      this.handlerDict['pageenter'](window);
    }
    this.detach = function () {
      for (name of Object.keys(this.tags)) { // 모든 태그 중
        if (this.tags[name].id) { // ID로 태그 찾기
          let tagById = document.querySelector('#' + this.tags[name].id);
          if (!tagById) continue;
          for (let e = 0; e < this.tags[name].events.length; e++) {
            if (!this.baseEvents.includes(this.tags[name].events[e])) { // 사용자 커스텀 이벤트라면
              tagById.removeEventListener(this.events[this.tags[name].events[e]].dom, function () { // base DOM 이벤트에 dispatcher 붙이기
                tagById.dispatchEvent(this.eventDict[this.tags[name].events[e]]);
              }.bind(this));
            }
            tagById.removeEventListener(this.tags[name].events[e], this.handlerDict[this.tags[name].events[e]]); // 해당 eventHandler 붙이기
          }
        } else if (this.tags[name].class) { // ID가 없으면 class로 태그 찾기
          let tagsByClass = document.querySelectorAll('.' + this.tags[name].class.replace(" ", "."));
          if (!tagsByClass) continue;
          tagsByClass.forEach((tagByClass) => {
            for (let e = 0; e < this.tags[name].events.length; e++) {
              if (!this.baseEvents.includes(this.tags[name].events[e])) { // 사용자 커스텀 이벤트라면
                tagByClass.removeEventListener(this.events[this.tags[name].events[e]].dom, function () { // base DOM 이벤트에 dispatcher 붙이기
                  tagByClass.dispatchEvent(this.eventDict[this.tags[name].events[e]]);
                }.bind(this));
              }
              tagByClass.removeEventListener(this.tags[name].events[e], this.handlerDict[this.tags[name].events[e]]); // 해당 eventHandler 붙이기
            }
          });
        } else { // 모든 element

        }
      }
      // 태그에 종속되지 않는 이벤트 발생시키기
      this.handlerDict['pageleave'](window);
    }

    window.addEventListener("load", function (e) {
      this.attach();
      console.log("loaded")
    }.bind(this));
    window.addEventListener("unload", function (e) {
      this.detach();
      console.log("unloaded")
    }.bind(this));


  };

}

let mata = new TagManager();









