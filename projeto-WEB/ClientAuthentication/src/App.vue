<template>
    <div id = "app">
        <Nav />
        <router-view />
    </div>
</template>

<script>
    // import axios from 'axios'
    import Nav from './components/Nav.vue'

    export default{
        name: 'App',
        async created() {
            // const response = await axios.get('clients/' + this.$store.state.user.id) // REVISAR
            // console.log(response)
            // this.$store.dispatch('user',response.data)
        },
        components: {
            Nav
        }
    }
</script>

<style>
    @import url('http://fonts.googleapis.com/css?family=Fira+Sans:400,500,600,700,800');

    *{
        box-sizing: border-box;
    }

    body{
        background: #1C8EF9 !important;
        min-height: 100vh;
        display: flex;
        font-weight: 400;
        font-family: 'Fira Sans', sans-serif;
    }

    h1, h2, h3, h4, h5, h6, label, span {
        font-weight: 500;
        font-family: 'Fira Sans', sans-serif;
    }

    body, html, #app, #root, .auth-wrapper{
        width: 100%;
        height: 100%;
    }

    #app {
        text-align: center;
    }

    .navbar-light {
        background-color: #ffffff;
        box-shadow: 0px 14px 80px rgba(34, 35, 58, 0.2);
    }

    .auth-wrapper{
        display: flex;
        justify-content: center;
        flex-direction: column;
        text-align: left;
    }

    .auth-inner{
        width: 450px;
        margin: auto;
        background: #ffffff;
        box-shadow: 0px 14px 80px rgba(34, 35, 58, 0.2);
        padding: 40px 55px 45px 55px;
        border-radius: 15px;
        transition: all .3s;
    }

    .auth-wrapper .form-control:focus{
        border-color: #167bff;
        box-shadow: none;
    }

    .auth-wrapper h3{
        text-align: center;
        margin: 0;
        line-height: 1;
        padding-bottom: 20px;
    }

    .custom-control-label{
        font-weight: 400;
    }

    .forgot-password,
    .forgot-password a {
        text-align: right;
        font-size: 13px;
        padding-top: 10px;
        color: #7f7d7d
    }

    .forgot-password a {
        color: #167bff
    }

    .form-group {
        margin-top: 10px;
    }

    .btn-block {
        margin-top: 10px;
    }

    #home {
        text-align: left;
        margin: 80px 30px 10px 30px;
        background: #ffffff;
        box-shadow: 0px 14px 80px rgba(34, 35, 58, 0.2);
        padding: 40px 55px 45px 55px;
        border-radius: 15px;
        transition: all .3s;        
    }

    #home h5 {
        text-align: center;
    }

    #home table{
        width: 100%;
    }
</style>
