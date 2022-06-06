<template>
    <div id="home">
        <h3 v-if="!user">
            Hello, you are not logged in!
        </h3>

        <div v-if="user">
            <h3>
                Hi, {{user.personalname}}
            </h3>

            <div v-if="user.permission == 0">
                You don't have permission neither to list nor to insert goods
            </div>
            <div v-if="user.permission == 2">
            <hr>

            <form v-if="user.permission == 2" @submit.prevent="insertGood">
                <error v-if="error" :error="error"/>
                <h5>Goods insertion</h5>
                <div class="form-group">
                    <label>Description</label>
                    <input type="text" class="form-control" v-model="description" placeholder="Description"/>
                </div>

                <button class="btn btn-primary btn-block">Insert</button>
            </form>

            </div>
            <div v-if="user.permission == 2 || user.permission == 1">
                <hr>

                <h5>
                    List of goods inserted
                </h5>
                <table>

                    <thead>

                    <tr>
                        <th>Description</th>
                        <th>Owner</th>
                    </tr>

                    </thead>

                    <tbody>

                    <tr v-for="good of goods" :key="good.id">

                        <td>{{ good.description }}</td>
                        <td>{{ good.client.personalname }}</td>

                    </tr>

                    </tbody>
            
                </table>
            </div>
            
        </div>

        
    </div>
</template>

<script>
    import {mapGetters} from "vuex";
    import Good from '../goods'
    import axios from 'axios'
    import Error from './Error.vue'

    export default{
        // eslint-disable-next-line vue/multi-word-component-names
        name: 'Home',
        components:{
            Error
        },
        computed:{
            ...mapGetters(['user'])
        },
        data () {
            return {
            good:{
                id: '',
                description: '',
                client: ''
            },
            goods: [],
            errors: []
            }
        },

        mounted(){
            this.listar()
        },

        methods:{
            listar(){
                Good.listar().then(resposta => {
                    this.goods = resposta.data._embedded.goodList,
                    console.log(this.goods)
                })
            }
            , async insertGood(){
                const client_get = await axios.get('clients/username/' + this.$store.state.user.username)
                console.log(client_get.data.id)
                try {
                    await axios.post('goods', {
                        description: this.description
                        , client: this.$store.state.user
                    })
                    this.listar()
                    this.description = "";
                    await this.$router.push('/')
                }catch (e) {
                    this.error = 'Error'
                }
            }
        }
    }

</script>