<template>
    <div class="auth-wrapper">
        <div class="auth-inner">
            <form @submit.prevent="handleSubmit">
                <error v-if="error" :error="error"/>
                <h3>Login</h3>
                <div class="form-group">
                    <label>Username</label>
                    <input type="text" class="form-control" v-model="username" placeholder="Username"/>
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="password" class="form-control" v-model="password" placeholder="Password"/>
                </div>

                <button class="btn btn-primary btn-block">Login</button>
            </form>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'
    import Error from './Error.vue'

    export default {
        // eslint-disable-next-line vue/multi-word-component-names
        name: 'Login',
        components:{
            Error
        },
        data(){
            return{
                username:'',
                password:'',
                error: ''
            }
        },
        methods:{
            async handleSubmit(){
                try {
                    const response = await axios.post('auth', {
                        username: this.username,
                        password: this.password
                    })
                    console.log(response.data);
                    localStorage.setItem('token', response.data.token)
                    this.$store.dispatch('user', response.data.user)
                    await this.$router.push('/')
                }catch (e) {
                    this.error = 'Invalid username or password'
                }
            }
        }
    }
</script>