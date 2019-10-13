// Работа с Vue.js
Vue.component('message-row',{
    props: ['message'],
    template: '<div><i>({{ message.id }})</i>{{ message.text }}</div>'
})

// Определяем новый компонент под именем messages-list
Vue.component('messages-list', {
    props: ['messages'],
    template: '<div><message-row v-for="message in messages" :key="message.id" :message="message"/></div>'
})

//js синтаксис
var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages"/>',
    data: {
        messages: [
            {id: '1', text: 'first'},
            {id: '2', text: 'second'},
            {id: '3', text: 'third'}
        ]
    }
});