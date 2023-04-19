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
        header: {id: null, class: 'flex justify-between items-center flex-wrap', events: ['click_header']},
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
    this.attachedListeners = [];
    this.logStash = [];
    this.enterTimer = Date.now();
    this.baseEvents = ['click', 'mouseenter', 'mouseleave', 'scroll', 'load', 'unload'];

    this.getCustomEvent = function (name, targetName) {
      const urlStr = document.location;
      const url = new URL(urlStr);
      const urlParams = url.searchParams;
      const pathArray = document.location.pathname.split('/');
      let detail = {};
      detail['targetName'] = targetName;
      for (let d = 0; d < this.events[name].param.length; d++) {
        detail[this.events[name].param[d].name] = urlParams.get(this.events[name].param[d].key);
      }
      for (let p = 0; p < this.events[name].path.length; p++) {
        detail[this.events[name].path[p].name] = pathArray[this.events[name].path[p].index];
      }
      return new CustomEvent(name, {
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
      this.stackLog(e, 'pageleave');
      this.flushLog();
    }.bind(this);
    let keys = Object.keys(this.events);
    for (let i=0; i<keys.length; i++) {
      this.handlerDict[keys[i]] = function (e) {
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
        targetId: (e && e.target && e.target.id) ? e.target.id : null,
        targetName: (e && e.detail && e.detail['targetName']) ? e.detail['targetName'] : null,
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
              let dispatcher = function () { // base DOM 이벤트에 dispatcher 붙이기
                tagById.dispatchEvent(this.getCustomEvent(this.tags[keys[i]].events[e], keys[i]));
              }.bind(this)
              tagById.addEventListener(this.events[this.tags[keys[i]].events[e]].dom, dispatcher);
              this.attachedListeners.push({target: tagById, type:this.events[this.tags[keys[i]].events[e]].dom, listener: dispatcher}) // detach를 위해 붙인 이벤트 모으기
            }
            tagById.addEventListener(this.tags[keys[i]].events[e], this.handlerDict[this.tags[keys[i]].events[e]]); // 해당 eventHandler 붙이기
            this.attachedListeners.push({target: tagById, type:this.tags[keys[i]].events[e], listener: this.handlerDict[this.tags[keys[i]].events[e]]}) // detach를 위해 붙인 이벤트 모으기
          }
        } else if (this.tags[keys[i]].class) { // ID가 없으면 class로 태그 찾기
          let classes = this.tags[keys[i]].class.split(" ");
          let tagsByClass = [...document.querySelectorAll('*')];
          for (let c=0; c<classes.length; c++) {
            tagsByClass = tagsByClass.filter(tag => tag.classList.contains(classes[c]))
          }
          if (!tagsByClass) continue;
          tagsByClass.forEach((tagByClass) => {
            for (let e = 0; e < this.tags[keys[i]].events.length; e++) {
              if (!this.baseEvents.includes(this.tags[keys[i]].events[e])) { // 사용자 커스텀 이벤트라면
                let dispatcher = function () { // base DOM 이벤트에 dispatcher 붙이기
                  tagByClass.dispatchEvent(this.getCustomEvent(this.tags[keys[i]].events[e], keys[i]));
                }.bind(this)
                tagByClass.addEventListener(this.events[this.tags[keys[i]].events[e]].dom, dispatcher);
                this.attachedListeners.push({target: tagByClass, type:this.events[this.tags[keys[i]].events[e]].dom, listener: dispatcher}) // detach를 위해 붙인 이벤트 모으기
              }
              tagByClass.addEventListener(this.tags[keys[i]].events[e], this.handlerDict[this.tags[keys[i]].events[e]]); // 해당 eventHandler 붙이기
              this.attachedListeners.push({target: tagByClass, type:this.tags[keys[i]].events[e], listener: this.handlerDict[this.tags[keys[i]].events[e]]}) // detach를 위해 붙인 이벤트 모으기
            }
          });
        } else { // 모든 element

        }
      }
      // 태그에 종속되지 않는 이벤트 발생시키기
      this.handlerDict['pageenter']({target: window});
    }
    this.detach = function () {
      for(let i=0; i<this.attachedListeners.length; i++) {
        this.attachedListeners[i].target.removeEventListener(this.attachedListeners[i].type, this.attachedListeners[i].listener);
      }
      // 태그에 종속되지 않는 이벤트 발생시키기
      this.handlerDict['pageleave']({target: window});
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

