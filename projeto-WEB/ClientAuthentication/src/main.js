/*import Router from './router'
import App from './App.vue'
import { createApp } from 'vue'

createApp(App).use(Router).mount('#app')*/
import * as Vue from 'vue';
import * as VueRouter from 'vue-router';
import * as Vuex from 'vuex';
import './axios'
import App from "./App.vue";
import Home from "@/components/Home";
import Login from "@/components/Login";
import Register from "@/components/Register"

const store = Vuex.createStore({
    state: {
        user: null
    },
    getters:{
        user: (state) => {
            return state.user
        }
    },
    actions: {
        user(context, user){
            context.commit('user', user)
        }
    },
    mutations:{
        user(state, user){
            state.user = user
        }
    }
})

const routes = [
    {path: '/', component: Home},
    {path: '/login', component: Login},
    {path: '/register', component: Register}
];

const router = VueRouter.createRouter({
    history: VueRouter.createWebHistory(),
    routes,
});

Vue.createApp(App).use(router).use(store).mount('#app');
