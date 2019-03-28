import Login from './views/Login'
import Register from './views/Register'
import Cart from './views/Cart'
import Detail from  './views/Detail'
import AdminContainer from './views/AdminContainer'
// import BookFlow from './components/BookFlow'
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
        path: '/admin',
        name: 'admin',
        component: AdminContainer,
        children: [
            {
                path: 'edit-stock',
                name: 'admin-edit-stock',
                component: BookList
            }
        ]
    }
]

export default routes;
