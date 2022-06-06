<template>
    <div class="auth-wrapper">
        <div class="auth-inner">
            <form @submit.prevent="handleSubmit">
                <error v-if="error" :error="error"/>
                <h3>Sign up</h3>

                <div class="form-group">
                    <label>Username</label>
                    <input type="text" class="form-control" v-model="username" placeholder="Username"/>
                </div>

                <div class="form-group">
                    <label>Personal name</label>
                    <input type="text" class="form-control" v-model="personalname" placeholder="Personal Name"/>
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="password" class="form-control" v-model="password" placeholder="Password"/>
                </div>

                <div class="form-group">
                    <label>Confirm Password</label>
                    <input type="password" class="form-control" v-model="confirm_password" placeholder="Confirm Password"/>
                </div>

                <button class="btn btn-primary btn-block">Sign up</button>
            </form>
        </div>
    </div>
    
</template>

<script>
    import axios from 'axios'
    import Error from './Error.vue'
    export default{
        // eslint-disable-next-line vue/multi-word-component-names
        name: 'Register',
        components:{
            Error
        },
        data(){
            return {
                username: '',
                personalname: '',
                password: '',
                confirm_password: '',
                permission: '',
                error: ''
            }
        },
        methods: {
            async handleSubmit(){
                try {
                    if (this.username.length != 8) {
                        this.error = 'Username must contain exactly 8 characters'
                    }
                    else if (this.password != this.confirm_password) {
                        this.error = 'Password doesn`t match'
                    }
                    else {
                        const response = await axios.post('clients', {
                            username: this.username,
                            personalname: this.personalname,
                            password: this.password,
                            permission: 0
                        })
                        console.log(response);
                        await this.$router.push('/login')
                    }
                }catch (e) {
                    this.error = 'Already exists user with username ' + this.username // show the error instead
                }

            }
        }
    }
</script>