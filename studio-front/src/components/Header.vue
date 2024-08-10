<template>
    <div>
        <header id="header" :class="isScrolled ? 'header-scrolled' : ''">
            <div class="container-fluid">
                <div id="logo" class="pull-left">
                <h1><a href="./index.html" class="scrollto">仿生实验室</a></h1>
                <!-- Uncomment below if you prefer to use an image logo -->
                <!-- <a href="#intro"><img src="@/assets/image/logo.png" alt="" title="" /></a>-->
                </div>

                <nav id="nav-menu-container">
                <ul class="nav-menu">
                    <li class="menu-active"><a href="#intro">主页</a></li>
                    <li><a href="#about">关于我们</a></li>
                    <!-- <li><a href="#services">服务</a></li> -->
                    <li><a href="#portfolio">作品</a></li>
                    <li><a href="#time" target="_blank">时光轴</a></li>
                    <li><a href="#team" target="_blank">团队</a></li>
                    <!-- <li class="menu-has-children"><a href="">Drop Down</a>
                    <ul>
                        <li><a href="#">Drop Down 1</a></li>
                        <li><a href="#">Drop Down 3</a></li>
                        <li><a href="#">Drop Down 4</a></li>
                        <li><a href="#">Drop Down 5</a></li>
                    </ul>
                </li> -->
                    <li><a href="#contact">联系我们</a></li>
                    <li><a class="loginbtn" href="http://localhost:8089" target="_blank">登陆/注册</a></li>
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
                type: Boolean,
                default: true
            }
        }
    }
</script>

<style scoped>

</style>