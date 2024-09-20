<template>
    <div>
            <Header is-scrolled="false"/>
            <!--==========================
            Intro Section
            ============================-->
            <section id="intro">
                <div class="intro-container">
                    <el-carousel height="100vh" :interval="5000" arrow="always">
                        <el-carousel-item v-for="(banner, index) in siteConfig.banners" :key="index">
                            <el-image :src="banner.bannerImg" style="filter: brightness(0.5);width: 100%;height: 120%;" fit="cover">
                            </el-image>
                            <!-- style="height: 500px; width: 600px; position: relative; align-items: center;" -->
                            <div class="carousel-container">
                                <div class="carousel-content">
                                    <h2 v-html="banner.mainTitle"></h2>
                                    <p v-html="banner.subTitle"></p>
                                    <a href="./index.html" class="btn-get-started scrollto">Get Started</a>
                                </div>
                            </div>
                        </el-carousel-item>
                    </el-carousel>
                </div>
            </section>


            <main id="main">

            <!--==========================
                About Us Section
            ============================-->
            <section id="about">
                <div class="container">

                <header class="section-header">
                    <h3>关于我们</h3>
                    <p style="white-space: pre-wrap;" v-html="siteConfig.teamDescription"></p>
                </header>

                <div class="row about-cols">

                    <div class="col-md-4 wow fadeInUp">
                    <div class="about-col">
                        <div class="img">
                        <img src="@/assets/image/about-mission.jpg" alt="" class="img-fluid">
                        <div class="icon"><i class="ion-ios-speedometer-outline"></i></div>
                        </div>
                        <h2 class="title"><a href="#">我们的使命</a></h2>
                        <p v-html="siteConfig.teamMission">
                        </p>
                    </div>
                    </div>

                    <div class="col-md-4 wow fadeInUp" data-wow-delay="0.1s">
                    <div class="about-col">
                        <div class="img">
                        <img src="@/assets/image/about-plan.jpg" alt="" class="img-fluid">
                        <div class="icon"><i class="ion-ios-list-outline"></i></div>
                        </div>
                        <h2 class="title"><a href="#">我们的计划</a></h2>
                        <p v-html="siteConfig.teamPlan">
                        </p>
                    </div>
                    </div>

                    <div class="col-md-4 wow fadeInUp" data-wow-delay="0.2s">
                    <div class="about-col">
                        <div class="img">
                        <img src="@/assets/image/about-vision.jpg" alt="" class="img-fluid">
                        <div class="icon"><i class="ion-ios-eye-outline"></i></div>
                        </div>
                        <h2 class="title"><a href="#">我们的愿景</a></h2>
                        <p v-html="siteConfig.teamVision">
                        </p>
                    </div>
                    </div>

                </div>

                </div>
            </section><!-- #about -->

            <!--==========================
                技术栈模块
            ============================-->
            <section id="skills">
                <div class="container">

                <header class="section-header">
                    <h3>技术栈</h3>
                    <p>团队成员掌握的技术能力</p>
                </header>

                <div class="skills-content">
                    <div class="progressBox" v-for="(skill, index) in skillConfig" :key="index">
                        <el-progress  :text-inside="true" :stroke-width="40" :percentage="skill.skillPercentage" :color="customColors"/>
                        <span v-html="skill.skillName">物联网</span>
                    </div>
                    <!-- <div class="progressBox">
                        <el-progress  :text-inside="true" :stroke-width="40" :percentage="80" status="warning"/>
                        <span >后端</span>
                    </div>
                    <div class="progressBox">
                        <el-progress  :text-inside="true" :stroke-width="40" :percentage="70" status="exception"/>
                        <span >Web前端</span>
                    </div>
                    <div class="progressBox">
                        <el-progress  :text-inside="true" :stroke-width="40" :percentage="50" status="exception"/>
                        <span >平面设计</span>
                    </div> -->
                </div>

                </div>
            </section>

            <!--==========================
                数据模块
            ============================-->
            <section id="facts" class="wow fadeIn">
                <div class="container">
                    <header class="section-header">
                        <h3>数据</h3>
                        <!-- <p>我们的队伍在不断壮大，期待你在工作室放光放彩</p> -->
                    </header>

                    <div class="row counters" style="margin-bottom: 120px;">
                        <div class="col-lg-3 col-6 text-center">
                            <span data-toggle="counter-up" v-html="statisticsData.establishmentDaysNum">1,364</span>
                            <p>成立天数</p>
                        </div>

                        <div class="col-lg-2 col-6 text-center">
                            <span data-toggle="counter-up" v-html="statisticsData.teamMembersNum">18</span>
                            <p>团队成员</p>
                        </div>

                        <div class="col-lg-2 col-6 text-center">
                            <span data-toggle="counter-up" v-html="statisticsData.racesNum">4</span>
                            <p>参与竞赛</p>
                        </div>

                        <div class="col-lg-2 col-6 text-center">
                            <span data-toggle="counter-up" v-html="statisticsData.achievementsNum">10</span>
                            <p>获取成果</p>
                        </div>

                        <div class="col-lg-3 col-6 text-center">
                            <span data-toggle="counter-up" v-html="statisticsData.cciesNum">4</span>
                            <p>奖项证书</p>
                        </div>
                    </div>
                </div>
            </section>

            <!--==========================
                作品模块 Section
            ============================-->
            <section id="portfolio" class="section-bg">
                <div class="container">

                <header class="section-header">
                    <h3 class="section-title">成果展示</h3>
                </header>

                <div class="row">
                    <div class="col-lg-12">
                    <ul id="portfolio-flters">
                        <li v-for="(pocs, index) in pocsArr" v-on:click="clickPocs(pocs.id)" :class="curClickPocs == pocs.id ? 'filter-active' : ''" v-html="pocs.pocsName" >All</li>
                        <!-- <li >Web</li>
                        <li >App</li>
                        <li >硬件</li> -->
                    </ul>
                    </div>
                    <div class="achievementSelBox">
                        <el-select v-model="searchAchievementYear" placeholder="请选择年份" size="mini"
                        @change="changeAchievementSearchYear"
                        :popper-append-to-body="false" 
                        style="width: 60%;">
                        <el-option 
                            v-for="item in achievementYearOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                            </el-option>
                        </el-select>
                    </div>
                </div>

                <div class="row portfolio-container" style="display: flex;flex-wrap: wrap;">
                    <div v-for="(achievement, index) in showAchievementArr" :key="index" class="col-lg-4 col-md-6 portfolio-item filter-web wow fadeInUp" data-wow-delay="0.2s">
                        <div class="portfolio-wrap" style="position: relative;">
                            <figure>
                                <!-- @/assets/image/portfolio/production.png -->
                                <img :src="achievement.previewImg" class="img-fluid" alt="">
                                <a :href="achievement.previewImg" class="link-preview" data-lightbox="portfolio"
                                    :data-title="achievement.title" title="Preview"><i class="ion ion-eye"></i></a>
                                <a href="#" class="link-details" title="More Details"><i class="ion ion-android-open"></i></a>
                            </figure>
                            <div :class="achievement.partUserNames.includes(',') ? 'teamTagBox' : 'ownTagBox'" v-html="achievement.partUserNames.includes(',') ? '团队' : '个人'">个人</div>
                            <div class="portfolio-info">
                                <h4><a href="#" v-html="achievement.title">xx信号检测仪web</a></h4>
                                <p style="font-size: 12px;font-weight: lighter;color: black;" v-html="achievement.procedureDate">2024年5月～2024年8月</p>
                                <span style="font-size: 12px;">参与成员：<span v-html="achievement.partUserNames"></span></span>
                        </div>
                        </div>
                    </div>
                </div>

                </div>
            </section><!-- #portfolio -->

            <!-- 荣誉 -->
            <section id="honer" class="section-bg">
                <div class="container" style="max-width: 1250px !important;">
                    <div class="section-header wow fadeInUp">
                        <h3>团队荣誉资质</h3>
                    </div>

                    <div style="width: 600px; position: relative;left: 25%;">
                        <el-tabs v-model="activeName" :stretch="stretch" @tab-click="handleClickHonerTabs">
                            <el-tab-pane label="竞赛证书" name="first"></el-tab-pane>
                            <el-tab-pane label="专利" name="second"></el-tab-pane>
                            <el-tab-pane label="论文" name="three"></el-tab-pane>
                            <el-tab-pane label="软著" name="four"></el-tab-pane>
                        </el-tabs>
                        <div class="honerSelBox">
                            <el-select v-model="searchHonerYear" placeholder="请选择年份" size="mini"
                            @change="changeHonerSearchYear"
                            :popper-append-to-body="false" 
                            style="width: 20%;">
                            <el-option 
                                v-for="item in honerYearOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                                </el-option>
                            </el-select>
                        </div>
                    </div>


                    <div v-if="activeName === 'first'" class="row honer-imgs" style="margin-top: 30px;display: flex;flex-wrap: wrap; position: relative; left: 2%;">
                        <div class="imgbox wow fadeInUp" v-for="(showRace, index) in showRaceArr" :key="index">
                            <div class="honerDate" v-html="showRace.raceEndTime">2024年7月</div>
                            <div :class="showRace.raceFlag == '1' ? 'honerOwnTag' : 'honerTeamTag'" v-html="showRace.raceFlag == '1' ? '个人' : '团队'">个人</div>
                            <img class="honerImg" v-on:click="clickPreviewImg(showRace.raceCcie)" :src="showRace.raceCcie" alt="">
                            <div class="honerType" v-html="showRace.raceName">xxxxx个人竞赛</div>
                            <div class="honerDesc" v-html="showRace.teamMemberRealNames">获得者：xxxxxx</div>
                        </div>

                        <!-- <div class="imgbox wow fadeInUp" v-for="index in [1,2,3,4,5]">
                            <div class="honerDate">2024年7月</div>
                            <div class="honerTeamTag">团队</div>
                            <img class="honerImg" src="@/assets/image/patents/patents.png" alt="">
                            <div class="honerType" >xxxxxx团队竞赛</div>
                            <div class="honerDesc" >获得者：xxxxx</div>
                        </div> -->
                    </div>
                    <div v-if="activeName === 'second' || activeName === 'three' || activeName === 'four'" class="row honer-imgs" style="margin-top: 30px;display: flex;flex-wrap: wrap; position: relative; left: 2%;">
                        <div class="imgbox wow fadeInUp" v-for="(showCcie, index) in showCcieArr" :key="index">
                            <div class="honerDate" v-html="showCcie.ccieGetTime">2024年7月</div>
                            <div :class="showCcie.tagClass" v-html="showCcie.tagName">个人</div>
                            <img class="honerImg" v-on:click="clickPreviewImg(showCcie.ccieImg)" :src="showCcie.ccieImg" alt=""></img>
                            <div class="honerType" v-html="showCcie.ccieName">个人竞赛</div>
                            <div class="honerDesc" v-html="'发表人：' + showCcie.userName">xxxxxxxxxxx个人竞赛</div>
                        </div>
                    </div>
                </div>
                <el-image ref="elImage" style="width: 0; height: 0;" :src="bigImageUrl" :preview-src-list="logicImageList"></el-image>
            </section><!-- #contact -->

            <!--==========================
                Contact Section
            ============================-->
            <section id="contact" class="section-bg wow fadeInUp">
                <div class="container">

                <div class="section-header">
                    <h3>联系我们</h3>
                    <p>如果你有追求技术的一颗心，快来加入我们吧</p>
                </div>

                <div class="form">
                    <div id="sendmessage">Your message has been sent. Thank you!</div>
                    <div id="errormessage"></div>
                    <form action="" method="post" role="form" class="contactForm">
                    <div class="form-row">
                        <div class="form-group col-md-6">
                        <input type="text" name="name" class="form-control" id="name" placeholder="姓名" data-rule="minlen:4"
                                data-msg="Please enter at least 4 chars" />
                        <div class="validation"></div>
                        </div>
                        <div class="form-group col-md-6">
                        <input type="email" class="form-control" name="email" id="email" placeholder="邮箱" data-rule="email"
                                data-msg="Please enter a valid email" />
                        <div class="validation"></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="subject" id="subject" placeholder="项目" data-rule="minlen:4"
                            data-msg="Please enter at least 8 chars of subject" />
                        <div class="validation"></div>
                    </div>
                    <div class="form-group">
                    <textarea class="form-control" name="message" rows="5" data-rule="required"
                                data-msg="Please write something for us" placeholder="描述"></textarea>
                        <div class="validation"></div>
                    </div>
                    <div class="text-center"><button type="button" disabled>暂未开放</button></div>
                    </form>
                </div>

                </div>
            </section><!-- #contact -->
            </main>
            <Footer/>
            <a href="#" class="back-to-top"><i class="fa fa-chevron-up"></i></a>
    </div>
</template>

<script>
import Header from '../components/Header.vue';
import Footer from '../components/Footer.vue';
import { showAchievements } from '@/api/openAchievementApi.js'
import { pocsList } from '@/api/openPocsApi.js'
import { ccieList } from '@/api/openCcieApi.js'
import { raceList } from '@/api/openRaceApi.js'
import { getSiteStatistics } from '@/api/openSiteApi.js'

export default {
    name: 'Index',
    components: {
      Header, Footer
    },
    data() {
        return {
            // 全局动态配置
            // 创建团队时间
            createTeamYear: this.$store.state.site.siteCreateTime !== '' ? this.$store.state.site.siteCreateTime.substring(0, 4) : 2001,
            // 动态配置
            activeName: 'first',
            stretch: true,
            curClickPocs: 0,  // 当前选中的栏目
            // 网站主页配置查询条件
            siteConfigParms: {
                configKey: this.$MY_CONSTANT.SITE_CONFIG.SITE_PAGE_MAIN_CONFIG.configKey
            },
            // 网站主页技术栈查询条件查询参数
            siteSkillConfigParams: {
                configKey: this.$MY_CONSTANT.SITE_CONFIG.SITE_PAGE_SKILL_CONFIG.configKey
            },
            // 网站主页基础配置
            siteConfig: {
                banners: [
                    {
                    "bannerImg": "https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/071.png",
                    "mainTitle": "欢迎来到仿生实验室",
                    "subTitle": "一群志同道合的人，一起奔跑在理想的路上..."
                    },
                    {
                    "bannerImg": "https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/072.png",
                    "mainTitle": "关于我们",
                    "subTitle": "最好的团队，最好的我们，不负韶华，努力奋斗。"
                    },
                    {
                    "bannerImg": "https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/073.png",
                    "mainTitle": "时光轴",
                    "subTitle": "时间是温柔的羽毛，把过往的灰尘轻轻弹去。"
                    },
                    {
                    "bannerImg": "https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/074.png",
                    "mainTitle": "团队",
                    "subTitle": "拍照只需要三秒，可锁住的是我们三年青春，感谢遇见！"
                    }
                ],
                teamDescription: '这里是物联网工作室，在这里不仅有学习硬件，还有学习软件的小伙伴们，我们都在前进的路上，未来值得期待！\n工作室有着丰富的学习资源，有着可以帮助你解决问题的学长学姐们以及专业指导老师，让你不断在专业领域进行探索和挖掘知识宝藏。\n',
                teamMission: '把我们的努力和知识，化身成有价值的产品，在未来物联网领域开拓出一片新的天地.',
                teamPlan: '多方位的扩展技术方面的人才，将想法进行落实.',
                teamVision: '期望所有的成员能够学有所成，在各自的领域发光发亮，取得优秀的成绩.'
            },
            // 技术栈配置
            skillConfig: [
                { skillName: 'Java', skillPercentage: 10 },
                { skillName: '网站开发', skillPercentage: 10 },
                { skillName: '上位机', skillPercentage: 10 },
                { skillName: 'openCV', skillPercentage: 10 },
            ],
            // 自定义进度条颜色
            customColors: [
                {color: '#f56c6c', percentage: 20},
                {color: '#e6a23c', percentage: 40},
                {color: '#5cb87a', percentage: 60},
                {color: '#1989fa', percentage: 80},
                {color: '#6f7ad3', percentage: 100}
            ],
            // 展示成果数组
            showAchievementArr: [],
            // 成果分类列表
            pocsArr: [
                {
                    "id": 0,
                    "pocsName": "ALL"
                },
                {
                    "id": 2,
                    "pocsName": "无人机"
                },
                {
                    "id": 3,
                    "pocsName": "web系统"
                },
                {
                    "id": 4,
                    "pocsName": "无人车"
                }
            ],
            // 展示证书列表
            showCcieArr: [],
            // 展示竞赛列表
            showRaceArr: [],
            // 网站统计数据
            statisticsData: {},
            // 预览图片
            bigImageUrl: '',
            logicImageList: [],
            // 成果年份下拉选项
            achievementYearOptions: [],
            // 成果年份查询
            searchAchievementYear: '',
            // 荣誉资质年份下拉选项
            honerYearOptions: [],
            // 荣誉资质年份查询
            searchHonerYear: ''
        }
    },
    created() {
        this.init()
        // console.log("index")
    },
    methods: {
        init() {
            window.curPage = 'index'
            // 获取网站主页配置信息
            this.$getSiteConfig(this.siteConfigParms).then(configValue=>{
                // console.log(configValue)
                this.siteConfig.banners = configValue.bannerTableData
                this.siteConfig.teamDescription = configValue.teamDescription.replace(/\n/g,'<br />')
                this.siteConfig.teamMission = configValue.teamMission.replace(/\n/g,'<br />')
                this.siteConfig.teamPlan = configValue.teamPlan.replace(/\n/g,'<br />')
                this.siteConfig.teamVision = configValue.teamVision.replace(/\n/g,'<br />')
            });
            // console.log("this.$MY_CONSTANT => ", this.$MY_CONSTANT.SITE_CONFIG.SITE_PAGE_SKILL_CONFIG.configKey)
            // 获取网站技术栈信息
            this.$getSiteConfig(this.siteSkillConfigParams).then(configValue=>{
                // console.log("this.siteSkillConfigParams => ", configValue)
                this.skillConfig = configValue
            });
            // 获取成果分类信息
            pocsList().then((data) => {
                // console.log("pocsList=>", data.data)
                this.pocsArr = data.data
                this.pocsArr.unshift({
                    id: 0,
                    pocsName: 'ALL'
                })
            }).catch(err=>console.log(err))
            // 获取成果信息
            this.getShowAchievements()
            // 获取竞赛信息
            this.getShowRaces()
            // 获取竞赛证书列表信息
            // this.getShowCcies();
            this.querySiteStatistics();
            // 构造下拉年份选择器
            let createYear = this.createTeamYear
            // console.log("createYear=>", createYear)
            // console.log(this.$store.state.site.siteCreateTime)
            if (createYear) {
                let currentYear = new Date().getFullYear();
                let initAllOption =  { 
                    label: "全部年份",
                    value: ""
                }
                this.honerYearOptions.push(initAllOption)
                this.achievementYearOptions.push(initAllOption)
                for (let year = currentYear; year >= parseInt(createYear); year--) {
                    let option = { 
                        label: year + "年",
                        value: year 
                    }
                    this.honerYearOptions.push(option)
                    this.achievementYearOptions.push(option)
                }
            }
            // console.log('createYear=>', createYear)
            // console.log("STUDIO_ADMIN=>", import.meta.env)
        },
        // 获取成果信息
        getShowAchievements() {
            // 构建请求对象
            let achievementParams = {}
            if (this.curClickPocs != 0) {
                achievementParams = { 
                    pocsId: this.curClickPocs
                }
            }
            // 添加年份搜索
            achievementParams.searchYear = this.searchAchievementYear
            // console.log("achievementParams=>", achievementParams)
            showAchievements(achievementParams).then((data) => {
                // console.log("showAchievements=>", data)
                this.showAchievementArr = data.data
                // console.log("showAchievementArr=>", this.showAchievementArr)
            }).catch(err=>console.log(err))
        },
        // 获取竞赛证书列表
        // type=1  => 专利
        // type=2  => 论文
        // type=3  => 软著
        getShowCcies(type, tagClass) {
            // 默认为1
            let typeParam = 1;
            if (type) {
                typeParam = type.val;
            }
            let ccieParams = {
                type: typeParam,
                searchYear: this.searchHonerYear
            }
            // 调用查询证书接口
            ccieList(ccieParams).then((data) => {
                // console.log("showCcieArr=>", data)
                this.showCcieArr = data.data
                // 遍历所有的数组，填充指定的样式
                if (this.showCcieArr) {
                    for (let i = 0; i < this.showCcieArr.length; i++) {
                        this.showCcieArr[i].tagClass = type.tagClass
                        this.showCcieArr[i].tagName = type.name
                    }
                }

            }).catch(err=>console.log(err))
        },
        getShowRaces() {
            let queryParams =  {
                searchYear: this.searchHonerYear
            }
            raceList(queryParams).then((data) => {
                // console.log("getShowRaces=>", data)
                this.showRaceArr = data.data
            }).catch(err=>console.log(err))
        },
        querySiteStatistics() {
            getSiteStatistics().then((data) => {
                // console.log("getShowRaces=>", data)
                this.statisticsData = data.data
            }).catch(err=>console.log(err))
        },
        handleClickHonerTabs() {
            // console.log("activeName=>", this.activeName)
            let activeName = this.activeName
            let type = {}
            let tagClass = 'honerZhuanLiTag'
            switch(activeName) {
                case 'first':
                    break;
                case 'second':
                    type = {
                        val: 1,
                        name: '专利',
                        tagClass: 'honerZhuanLiTag'
                    }
                    break;
                case 'three':
                    type = {
                        val: 2,
                        name: '论文',
                        tagClass: 'honerLunWenTag'
                    }
                    break;
                case 'four':
                    type = {
                        val: 3,
                        name: '软著',
                        tagClass: 'honerRuanZhuTag'
                    }
            }
            // 根据类型区分情况
            if (activeName == 'first') {
                this.getShowRaces()
            }else if (activeName == 'second' || activeName == 'three' || activeName == 'four') {
                this.getShowCcies(type, tagClass)
            }
        },
        // 点击分类标签
        clickPocs(curClickPocs) {
            this.curClickPocs = curClickPocs
            // 获取成果信息
            this.getShowAchievements()
        },
        clickPreviewImg(image) {
            this.$nextTick(() => {
                let img = image
                this.logicImageList = [ img ]
                this.bigImageUrl = img
                this.$refs.elImage.clickHandler()
            })
        },
        changeHonerSearchYear() {
            // console.log(this.queryParams.searchYear)
            // this.getShowRaces()
            this.handleClickHonerTabs()
        },
        changeAchievementSearchYear() {
            this.getShowAchievements()
        }
    }
}
</script>

<style scoped lang="scss">
  %honerTagBase {
    position: absolute;
        right: 15px;
        top: 15px;
        font-size: 9px;
        padding: 2px 5px;
        border-radius: 3px;
        font-weight: 330;
  }

    ::v-deep {
        // 自定义下拉选项 【搭配select标签设置:popper-append-to-body="false"】
        .el-select-dropdown__item {
            height: 28px;
            font-size: 10px;
        }
    }

    .achievementSelBox {
        position: relative;
        left: 5%;
        bottom: 35px;
    }

    .honerSelBox {
        position: relative;
        left: -37%;
        bottom: 6px
    }

  .imgbox {
    width: 210px;
    height: 280px;
    position: relative;
    background: white;
    margin-right: 40px;
    box-shadow: 0 0 1px 1px #cccccc91;
    border-radius: 3px;

    .honerDate {
        position: absolute;
        left: 15px;
        top: 15px;
        font-size: 13px;
    }

    .honerOwnTag {
        @extend %honerTagBase;
        border: 1px solid #C0A060;
        color: #000000;
        background-color: #FFD700;
    }

    .honerTeamTag {
        @extend %honerTagBase;
        border: 1px solid #CC8C00;
        color: #FFFFFF;
        background-color: #FFA500;
    }

    .honerZhuanLiTag {
        @extend %honerTagBase;
        border: 1px solid #0077CC;
        color: #FFFFFF;
        background-color: #00BFFF;
    }

    .honerLunWenTag {
        @extend %honerTagBase;
        border: 1px solid #808080;
        color: #000000;
        background-color: #A9A9A9;
    }

    .honerRuanZhuTag {
        @extend %honerTagBase;
        border: 1px solid #388E3C;
        color: #FFFFFF;
        background-color: #4CAF50;
    }

    // 原始
    .honerImg {
        width: auto;
        max-width: 80%;
        height: auto;
        max-height: 60%;
        position: absolute;
        left: 50%;  
        transform: translate(-50%, -42%);top: 43%;
        border: 1px solid rgba(0, 0, 0, 0.126);
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(207, 243, 4, 0.1);
        transition: border-color 0.3s ease;
    }

    .honerImg:hover {
        // transition: all 0.3s;
        cursor: pointer;
        // transform: scale(1.2);
        border-color: #f13207; /* 鼠标悬停时改变边框颜色 */
    }

    .honerType {
        width: 200px;
        text-align: center;
        position: absolute;
        left: 5px;
        bottom: 32px;
        font-size: 13px;
        left: 50%;
        transform: translate(-50%, 0%);
        font-weight: bold;
    }

    .honerDesc {
        width: 200px; 
        position: absolute;
        left: 5px;
        bottom: 10px;
        font-size: 12px;
        left: 50%;
        transform: translate(-50%, 0%);
        text-align: center;
    }
  }

  // 定义一个基础类，包含共有的样式
  %tagBoxBase {
    font-size: 12px;
    position: absolute;
    font-weight: 330;
    color: black;
    left: 4%;
    bottom: 21%;
    padding: 0px 5px;
    border-radius: 3px;
    width: 37px;
    height: 20px;
  } 

  .teamTagBox {
    @extend %tagBoxBase;
    // background-color: rgba(129, 179, 55, 0.9);
    border: 1px solid #CC8C00;
    color: #FFFFFF;
    background-color: #FFA500;
  }

  .ownTagBox {
    @extend %tagBoxBase;
    border: 1px solid #C0A060;
    color: #000000;
    background-color: #FFD700;
    // background-color: rgba(127, 131, 247, 0.9);
    // color: white;
  }

  .progressBox span{
    position: relative;
    top: -32px;
    left: 20px;
    color: white;
  }
  .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>