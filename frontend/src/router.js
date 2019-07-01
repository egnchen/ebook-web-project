import Login from './views/Login'
import Register from './views/Register'
import Detail from  './views/Detail'
import Order from './views/Order'
import Stats from './views/Stats'
import BookFlow from './components/BookFlow'
import AdminContainer from './views/AdminContainer'
import BookList from './components/BookList'
import Cart from './views/Cart'
import ManageUsers from './views/ManageUsers'
import EditStock from './views/EditStock'
import AllOrders from './views/AllOrders'

const routes = [
    {
        path: '/login',
        name: 'login',
        component: Login,
        meta: {
            title: "登录"
        }
    },
    {
        path: '/register',
        name: 'register',
        component: Register,
        meta: {
            title: "注册"
        }
    },
    {
        path: '/',
        name: 'index',
        component: BookList,
        meta: {
            title: "首页"
        }
    },
    {
        path: '/cart',
        name: 'cart',
        component: Cart,
        meta: {
            title: "购物车"
        }
    },
    {
        path: '/detail',
        name: 'detail',
        component: Detail,
        meta: {
            title: "图书详情"
        }
    },
    {
        path: '/buy',
        name: 'buy',
        meta: {
            title: "购买页面"
        }
    },
    {
        path: '/order',
        name: 'order',
        component: Order,
        meta: {
            title: "订单管理"
        }
    },
    {
        path: '/flow',
        name: 'bookflow',
        component: BookFlow,
        meta: {
            title: "瀑布流视图"
        }
    },
    {
        path: '/admin',
        name: 'admin',
        component: AdminContainer,
        children: [
            {
                path: 'edit-stock',
                name: 'admin-edit-stock',
                component: EditStock,
                meta: {
                    title: "编辑库存"
                }
            },
            {
                path: 'all-orders',
                name: 'admin-all-orders',
                component: AllOrders,
                meta: {
                    title: "所有订单"
                }
            },
            {
                path: 'stats',
                name: 'admin-view-stats',
                component: Stats,
                meta: {
                    title: "统计信息"
                }
            },
            {
                path: 'users',
                name: 'admin-manage-users',
                component: ManageUsers,
                meta: {
                    title: "用户管理"
                }
            }
        ]
    }
]

export default routes;
