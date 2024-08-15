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
                        <li v-for="(time, index) in siteCon.times" :key="index" :class="index % 2 === 1 ? 'timeline-inverted' : ''">
                            <div class="timeline-image wow slideInUp"><img class="rounded-circle img-fluid img-pos-adj"
                                :src="`/src/assets/image/time/${siteCon.times.length - index}.png`" alt="..." />
                            </div>
                            <div :class="index % 2 === 1 ? 'timeline-panel wow slideInLeft' : 'timeline-panel wow slideInRight'">
                            <div class="timeline-heading">
                                <h4 v-html="time.year"></h4>
                                <h4 class="subheading" v-html="time.title"></h4>
                            </div>  
                            <div class="timeline-body">
                                <p class="text-muted" style="white-space: pre-wrap;" v-html="time.description"></p>
                            </div>
                            </div>
                        </li>

                    <li class="timeline-inverted">
                        <div class="timeline-image">
                        <h4>
                            <span v-html="siteCon.siteCreateTime"/>
                            <br />
                            <span v-html="siteCon.teamTitle"/>成立
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
        data() {
            return {
                siteConfigParms: {
                    configKey: this.$MY_CONSTANT.SITE_CONFIG.SITE_PAGE_TIMECONFIG.configKey
                },
                siteBasicConfigParms: {
                    configKey: this.$MY_CONSTANT.SITE_CONFIG.SITE_BASICCONFIG.configKey
                },
                siteCon: {
                    times: [
                        { year: '2017-2018年', title:'初出茅庐', description: '在xx竞赛、大学生创新创业大赛等比赛中开始崭露头角' },
                        { year: '2019年', title:'小有成就', description: '获得了物联网大赛三等奖、计算机设计大赛一等奖一等奖等许多奖项' },
                        { year: '2020年', title:'再进一步', description: '学校评审取得工位，获省物联网大赛“新大陆杯”二等奖、省级计算机设计大赛三等奖等'},
                        { year: '2021年', title:'初露锋芒', description: '参与了物联网省级技能竞赛，大学生创新创业大赛，人工智能技术服务技能大赛，互联网+等多项比赛。获取NB-IOT信号检测仪软著，江苏省大学生创新创业训练计划项目优秀奖等' },
                        { year: '2022年', title:'再下一城', description: '疫情打击，组织线上学习。参与了xxx省级技能竞赛，大学生创新创业大赛，江苏省大学生创新创业训练计划项目，互联网+等。获取荣誉有：全国大学生数学建模竞赛江苏省一等奖，后台web、app软著，多个研究性课题结题证书，江苏省大学生创新创业训练计划项目优秀结题等。' },
                        { year: '2023年', title:'冲劲十足', description: '一路高歌' }
                    ],
                    teamTitle: 'xx实验室',
                    siteCreateTime: '2016年'
                }
            }
        },
        computed: {
            teamTitle() {
                return this.$store.state.site.teamTitle
            },
            siteCreateTime() {
                return this.$store.state.site.siteCreateTime + '年'
            }
        },
        created() {
            // console.log(this.siteCon.times)
            this.getSiteConfig()
        },
        methods: {
            getSiteConfig() {
                this.$getSiteConfig(this.siteConfigParms).then(configValue=>{
                    // this.siteCon.times = configValue
                    // 将所有的描述信息\n来进行替换
                    this.siteCon.times = configValue.map(item => {
                        // 克隆对象以避免直接修改原始对象
                        let updatedItem = { ...item };
                        // 执行字符串替换操作
                        updatedItem.description = updatedItem.description.replace(/\n/g, '<br />');
                        return updatedItem;
                    });
                });
            }
        }
    }
</script>

<style scoped>

</style>