<template>
    <div>
        <Header/>
        <main id="main">
            <!--==========================
                团队模块
                ============================-->
            <section id="team" style="min-height: 500px">
                <div class="container">
                <div class="section-header wow fadeInUp">
                    <h3>团队展示</h3>
                    <p>这里是<span v-text="unitName"></span>的<span v-text="teamTitle"></span>小伙伴们</p>
                </div>
                <!-- 遍历所有年级 -->
                <div :class="gradeMembers.grade === '指导老师' ? 'grade teacher' : 'grade'" v-for="(gradeMembers, index) in gradesMembers" v-loading="loading">
                    <div class="flag wow fadeInUp">
                        <span class="flag_name" v-text="gradeMembers.grade" ></span>
                    </div>
                    <!-- 年级学生容器 -->
                    <div class="row">
                        <!-- 遍历某个年级的所有学生 -->
                        <div class="col-lg-3 col-md-6 wow fadeInUp" v-for="member in gradeMembers.members">
                        <!-- 老师单个展示栏 -->
                        <div v-if="gradeMembers.grade === '指导老师'" class="memberbox">
                            <div class="imginfobox">
                            <div class="imgbox">
                                <img :src="member.perImg" alt="">
                            </div>
                            <div class="infobox">
                                <p v-text="member.realName === '' ? '': member.realName"></p>
                                <p>学院：<span v-text="member.academyName"></span></p>
                                <p>身份：<span v-text="member.roleName"></span></p>
                                <p>专业：<span v-text="member.majorName"></span></p>
                                <p><a target="_blank" :href="member.teacherMainHref">导师主页链接</a></p>
                            </div>
                            </div>
                            <div class="descbox">
                            <div class="spanbox">
                                <b>人生格言：</b> <span v-text="member.description"></span>
                            </div>
                            </div>
                        </div>
                        <!-- 学生单个展示栏 -->
                        <div v-else class="memberbox">
                            <div class="imginfobox">
                            <div class="imgbox">
                                <img :src="member.perImg" alt="">
                            </div>
                            <div class="infobox">
                                <p v-text="member.realName"></p>
                                <el-tooltip placement="top">
                                    <div slot="content" v-text="member.academyName"></div>
                                    <p style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">学院：<span v-text="member.academyName"></span></p>
                                </el-tooltip>
                                <p>身份：<span v-text="member.roleName"></span></p>
                                <el-tooltip placement="top">
                                    <div slot="content" v-text="member.majorName"></div>
                                    <p style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">专业：<span v-text="member.majorName"></span></p>
                                </el-tooltip>
                                <p>学业状态：
                                <span v-text="member.directionTypeName"></span>
                                </p>
                            </div>
                            </div>
                            <div class="descbox">
                            <div class="spanbox">
                                <b>人生格言：</b> <span v-text="member.description"></span>
                            </div>
                            </div>
                            <!-- 非指导老师显示该区域 -->
                            <div v-show="gradeMembers.grade !== '指导老师'" class="directionbox">
                                <div v-if="member.directionTypeName === '在校'">
                                    <div class="descbox">
                                        <b>当前所在：
                                            <span v-if="unitName" v-text="unitName"></span>
                                            <span v-else>暂未知状态</span>
                                        </b>
                                    </div>
                                    <div class="logobox">
                                        <img v-if="unitLogo" :src="unitLogo" alt="">
                                        <img v-else src="https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/image-20240714220851751.png" alt="">
                                    </div>
                                </div>
                                <div v-else>
                                    <div class="descbox">
                                        <b>毕业去向：
                                            <span v-if="member.directionName != ''" v-text="member.directionName"></span>
                                            <span v-else>暂未知状态</span>
                                        </b>
                                    </div>
                                    <div class="logobox">
                                        <img v-if="member.logoImg != ''" :src="member.logoImg" alt="">
                                        <img v-else src="https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/image-20240714220851751.png" alt="">
                                    </div>
                                </div>
                            </div>
                        </div>
                        </div>
                    </div>
                </div>
                </div>
            </section><!-- #team -->
        </main>
        <Footer/>
    </div>
</template>

<script>
    import Header from '../components/Header.vue';
    import Footer from '../components/Footer.vue';

    import { queryTeamMembers } from '@/api/openUserApi.js'

    export default {
        name: 'Team',
        components: {
            Header, Footer
        },
        data() {
            return {
                // 全局配置获取
                unitLogo: this.$store.state.site.unitLogo,
                unitName: this.$store.state.site.unitName,
                teamTitle: this.$store.state.site.teamTitle,
                // 当前vue属性配置
                gradesMembers: [],
                loading: true
            }
        },
        created() {
            this.getMembers()
        },
        methods: {
            getMembers() {
                queryTeamMembers().then((data) => {
                    // console.log("queryTeamMembers=>", data)
                    this.gradesMembers = data.data;
                    this.loading = false;
                }).catch(err=>console.log(err))
            }
        }
    }
</script>

<style scoped>

</style>