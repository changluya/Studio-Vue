<template>
    <div>
        <Header/>
        <main id="main">
            <!-- 时光轴-->
            <section class="page-section" id="time">
            <div class="container">
                <div class="text-center">
                <h2 class="section-heading text-uppercase m-color-warmred">时光轴</h2>
                <h3 class="section-subheading text-muted">这里记录着工作室的点滴成长</h3>
                </div>
                <ul class="timeline">
                <li>
                    <div class="timeline-image wow slideInUp"><img class="rounded-circle img-fluid img-pos-adj"
                        src="@/assets/image/time/05weightlifting.png" alt="..." />
                    </div>
                    <div class="timeline-panel wow slideInLeft">
                    <div class="timeline-heading">
                        <h4>2022年</h4>
                        <h4 class="subheading">再下一城</h4>
                    </div>
                    <div class="timeline-body">
                        <p class="text-muted">
                        疫情打击，组织线上学习。参与了xxx省级技能竞赛，大学生创新创业大赛，江苏省大学生创新创业训练计划项目，互联网+等。
                        </p>
                        <p class="text-muted">
                        获取荣誉有：全国大学生数学建模竞赛江苏省一等奖，后台web、app软著，多个研究性课题结题证书，江苏省大学生创新创业训练计划项目优秀结题等。
                        </p>
                    </div>
                    </div>
                </li>
                <li class="timeline-inverted">
                    <div class="timeline-image wow slideInUp"><img class="rounded-circle img-fluid img-pos-adj"
                        src="@/assets/image/time/04fencer.png" alt="..." />
                    </div>
                    <div class="timeline-panel wow slideInRight">
                    <div class="timeline-heading">
                        <h4>2021年</h4>
                        <h4 class="subheading">初露锋芒</h4>
                    </div>
                    <div class="timeline-body">
                        <p class="text-muted">参与了物联网省级技能竞赛，大学生创新创业大赛，人工智能技术服务技能大赛，互联网+等多项比赛。获取NB-IOT信号检测仪软著，江苏省大学生创新创业训练计划项目优秀奖等
                        </p>
                    </div>
                    </div>
                </li>
                <li>
                    <div class="timeline-image wow slideInUp"><img class="rounded-circle img-fluid img-pos-adj"
                        src="@/assets/image/time/03vol.png" alt="..." />
                    </div>
                    <div class="timeline-panel wow slideInLeft">
                    <div class="timeline-heading">
                        <h4>2020年</h4>
                        <h4 class="subheading">再进一步</h4>
                    </div>
                    <div class="timeline-body">
                        <p class="text-muted">学校评审取得工位，获省物联网大赛“新大陆杯”二等奖、省级计算机设计大赛三等奖等</p>
                    </div>
                    </div>
                </li>
                <li class="timeline-inverted">
                    <div class="timeline-image wow slideInUp"><img class="rounded-circle img-fluid img-pos-adj"
                        src="@/assets/image/time/02bike.png" alt="..." />
                    </div>
                    <div class="timeline-panel wow slideInRight">
                    <div class="timeline-heading">
                        <h4>2019年</h4>
                        <h4 class="subheading">小有成就</h4>
                    </div>
                    <div class="timeline-body">
                        <p class="text-muted">获得了物联网大赛三等奖、计算机设计大赛一等奖一等奖等许多奖项</p>
                    </div>
                    </div>
                </li>
                <li>
                    <div class="timeline-image wow slideInUp"><img class="rounded-circle img-fluid img-pos-adj"
                        src="@/assets/image/time/01run.png" alt="..." />
                    </div>
                    <div class="timeline-panel wow slideInLeft">
                    <div class="timeline-heading">
                        <h4>2017-2018年</h4>
                        <h4 class="subheading">初出茅庐</h4>
                    </div>
                    <div class="timeline-body">
                        <p class="text-muted">在xx竞赛、大学生创新创业大赛等比赛中开始崭露头角</p>
                    </div>
                    </div>
                </li>
                <li class="timeline-inverted">
                    <div class="timeline-image">
                    <h4>
                        2016年
                        <br />
                        仿生实验室成立
                    </h4>
                    </div>
                </li>
                </ul>
            </div>
            </section>
        </main>
        <Footer/>
    </div>
</template>

<script>
    import Header from '../components/Header.vue';
    import Footer from '../components/Footer.vue';
    export default {
        name: 'Time',
        components: {
            Header, Footer
        },
    }
</script>

<style scoped>

</style>