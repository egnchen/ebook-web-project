import Login from './views/Login'
import Register from './views/Register'
import Detail from  './views/Detail'
import Order from './views/Order'
import Stats from './views/Stats'
import BookFlow from './components/BookFlow'
import AdminContainer from './views/AdminContainer'
import BookList from './components/BookList'

const routes = [
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path: '/register',
        name: 'register',
        component: Register
    },
    {
        path: '/',
        name: 'index',
        component: BookList
    },
    {
        path: '/cart',
        name: 'cart',
        component: BookList
    },
    {
        path: '/detail',
        name: 'detail',
        component: Detail
    },
    {
        path: '/buy',
        name: 'buy'
    },
    {
        path: '/order',
        name: 'order',
        component: Order
    },
    {
        path: '/flow',
        name: 'bookflow',
        component: BookFlow
    },
    {
        path: '/admin',
        name: 'admin',
        component: AdminContainer,
        children: [
            {
                path: 'edit-stock',
                name: 'admin-edit-stock',
                component: BookList
            },
            {
                path: 'stats',
                name: 'admin-view-stats',
                component: Stats
            }
        ]
    }
]

export default routes;
