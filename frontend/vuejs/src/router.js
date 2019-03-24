import Login from './Login'
import BookFlow from './components/BookFlow';
import Cart from './Cart';

const routes = [
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path: '/',
        name: 'index',
        component: BookFlow
    },
    {
        path: '/cart',
        name: 'cart',
        component: Cart
    }
]

export default routes;
