<template>
<div id="app" class="layout">
<Header class="fixed-header">
    <Menu mode="horizontal" theme="dark" active-name="1">
        <MenuItem name="0" class="layout-logo" >
            <Logo/>
        </MenuItem>
        <MenuItem name="1">
            <router-link to="/">
                <Icon type="ios-navigate"></Icon>
                首页
            </router-link>
        </MenuItem>
        <MenuItem name="2">
            <router-link to="/category">
                <Icon type="ios-keypad"></Icon>
                分类
            </router-link>
        </MenuItem>
        <MenuItem name="3">
            <router-link to="/hot">
                <Icon type="ios-analytics"></Icon>
                热门
            </router-link>
        </MenuItem>
        <MenuItem name="4">
            <router-link to="/cart">
                <Icon type="ios-cart" />
                购物车
            </router-link>
        </MenuItem>
        <MenuItem name="5" style="float: right">
            <router-link to="/login">
                <Avatar icon="ios-person" size="medium" />
                <span style="padding-left: 0.5em">登录</span>
            </router-link>
        </MenuItem>
    </Menu>
</Header>
<router-view style="margin: 70px auto 15px;"></router-view>
</div>
</template>

<style scoped>
.fixed-header {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 1000;
    transition: box-shadow 0.15s linear;
    border-bottom-left-radius: 5px;
    border-bottom-right-radius: 5px;
}

.fixed-header:hover {
    box-shadow: 0px 2px 5px #444444;
}

.layout-logo {
    max-width: 15vw;
    height: 100%;
    padding: 5px;
}

.fixed-header a {
    color: white;
}

.fixed-header a:hover {
    color: red;
}

</style>


<script>
import BookData from './data/books.js';
import Logo from './components/Logo';

export default {
    components: {
        Logo
    },
    data: function() { 
        return {
            books: BookData.book_list
        }
    }
}
</script>