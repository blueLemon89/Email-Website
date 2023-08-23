import Vue from 'vue'
import VueRouter from 'vue-router'

import Schedule from '@/components/Schedule.vue'

Vue.use(VueRouter)

export default[
    {
        path: "/",
        name: 'home',
        component: Schedule
    },
    {
        path: "/scheduleError",
        name: 'scheduleError',
        component:() => import('../views/ScheduleErr.vue')
    },
    {
        path: "/file",
        name: 'file',
        component:() => import('../views/File.vue')
    },
    {
        path:"/errorInFile/:id",
        name:'errorInFile',
        component:()=> import('../views/ErrorInFile.vue')
    },
    {
        path:"/requestFail",
        name: 'requestFail',
        component: () => import('../views/RequestFailed.vue')
    }
]
