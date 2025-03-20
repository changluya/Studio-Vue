<template>
    <div>
        <header id="header" :class="isScrolled ? 'header-scrolled' : ''">
            <div class="container-fluid">
                <div id="logo" class="pull-left">
                <h1><a href="#/index" class="scrollto" v-html="siteTitle"></a></h1>
                </div>

                <nav id="nav-menu-container">
                <ul class="nav-menu">
                    <li :class="curRoutePath === '/index' ? 'menu-active': ''"><a href="/#/index">主页</a></li>
                    <li><a class="tab" v-scroll-to="'#about'">关于我们</a></li>
                    <li><a class="tab" v-scroll-to="'#portfolio'">成果</a></li>
                    <li><a class="tab" v-scroll-to="'#honer'">荣誉资质</a></li>
                    <li><a :class="curRoutePath === '/time' ? 'menu-active': ''" href="/#/time" target="_blank">时光轴</a></li>
                    <li><a :class="curRoutePath === '/team' ? 'menu-active': ''" href="/#/team" target="_blank">团队</a></li>
                    <!-- <li class="menu-has-children"><a href="">Drop Down</a>
                    <ul>
                        <li><a href="#">Drop Down 1</a></li>
                        <li><a href="#">Drop Down 3</a></li>
                        <li><a href="#">Drop Down 4</a></li>
                        <li><a href="#">Drop Down 5</a></li>
                    </ul>
                </li> -->
                    <li><a href="#contact">联系我们</a></li>
                    <li><a class="loginbtn" :href="studioAdminUIHref" target="_blank">登陆/注册</a></li>
                </ul>
                </nav>
            </div>
        </header>
    </div>
</template>

<script>

    export default {
        name: 'Header',
        props: {
            isScrolled: {
                type: [Boolean, String],
                default: true
            }
        },
        watch: {
            isScrolled: {
                immediate: true, //立即执行观察者函数
                handler(isScrolled) {
                    // 检查 isScrolled 是否为字符串，并尝试转换为布尔值
                    if (typeof isScrolled === 'string') {
                        try {
                            // 尝试将字符串转换为布尔值
                            const boolValue = Boolean(isScrolled);
                            this.localIsScrolled = boolValue
                        } catch (error) {
                            console.log('Error converting isScrolled to boolean:', error)
                        }
                    }
                }
            }
        },
        data() {
            return {
                siteConfigParms: {
                    configKey: this.$MY_CONSTANT.SITE_CONFIG.SITE_BASIC_CONFIG.configKey
                },
                // 父组件传递值
                localIsScrolled: this.isScrolled,
                // 当前路径
                curRoutePath: '',
                // 后台管理系统配置
                studioAdminUIHref: import.meta.env.VITE_STUDIO_ADMIN
            }
        },
        computed: {
            siteTitle() {
                return this.$store.state.site.siteTitle
            }
        },
        created() {
            // console.log('当前路径：', this.$route.path);
            this.curRoutePath = this.$route.path
            console.log(this.studioAdminUIHref)
            console.log(import.meta.env.VITE_API_URL)
            console.log(import.meta.env.VITE_API_URL)
        },
        methods: {
        },
    }
</script>

<style scoped>

</style>