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
                    <!-- <p style="margin: 0;">这里是物联网工作室，在这里不仅有学习硬件，还有学习软件的小伙伴们，我们都在前进的路上，未来值得期待！</p>
                    <p style="margin: 0;">工作室有着丰富的学习资源，有着可以帮助你解决问题的学长学姐们以及专业指导老师，让你不断在专业领域进行探索和挖掘知识宝藏。</p> -->
                </header>

                <div class="row about-cols">

                    <div class="col-md-4 wow fadeInUp">
                    <div class="about-col">
                        <div class="img">
                        <img src="@/assets/image/about-mission.jpg" alt="" class="img-fluid">
                        <div class="icon"><i class="ion-ios-speedometer-outline"></i></div>
                        </div>
                        <h2 class="title"><a href="#">我们的使命</a></h2>
                        <p>
                        把我们的努力和知识，化身成有价值的产品，在未来物联网领域开拓出一片新的天地.
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
                        <p>
                        多方位的扩展技术方面的人才，将想法进行落实.
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
                        <p>
                        期望所有的成员能够学有所成，在各自的领域发光发亮，取得优秀的成绩.
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
                    <p>工作室小组成员掌握的技术能力</p>
                </header>

                <div class="skills-content">
                    <div class="progressBox">
                        <el-progress  :text-inside="true" :stroke-width="40" :percentage="100" status="success"/>
                        <span >物联网</span>
                    </div>
                    <div class="progressBox">
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
                    </div>
                </div>

                </div>
            </section>

            <!--==========================
                目标模块
            ============================-->
            <section id="facts" class="wow fadeIn">
                <div class="container">

                <header class="section-header">
                    <h3>数据</h3>
                    <!-- <p>我们的队伍在不断壮大，期待你在工作室放光放彩</p> -->
                </header>

                <div class="row counters">

                    <div class="col-lg-3 col-6 text-center">
                    <span data-toggle="counter-up">1,364</span>
                    <p>成立天数</p>
                    </div>

                    <div class="col-lg-3 col-6 text-center">
                    <span data-toggle="counter-up">18</span>
                    <p>成员</p>
                    </div>

                    <div class="col-lg-3 col-6 text-center">
                    <span data-toggle="counter-up">10</span>
                    <p>奖项</p>
                    </div>

                    <div class="col-lg-3 col-6 text-center">
                    <span data-toggle="counter-up">4</span>
                    <p>项目</p>
                    </div>

                </div>

                <div class="facts-img">
                    <img src="@/assets/image/bg.png" alt="" class="img-fluid">
                </div>

                </div>
            </section><!-- #facts -->

            <!--==========================
                作品模块 Section
            ============================-->
            <section id="portfolio" class="section-bg">
                <div class="container">

                <header class="section-header">
                    <h3 class="section-title">我们的作品</h3>
                </header>

                <div class="row">
                    <div class="col-lg-12">
                    <ul id="portfolio-flters">
                        <li class="filter-active">All</li>
                        <li >Web</li>
                        <li >App</li>
                        <li >硬件</li>
                    </ul>
                    </div>
                </div>

                <div class="row portfolio-container">

                    <!-- 属于web：filter-web -->
                    <div class="col-lg-4 col-md-6 portfolio-item filter-web wow fadeInUp" data-wow-delay="0.2s">
                    <div class="portfolio-wrap">
                        <figure>
                        <img src="@/assets/image/portfolio/production.png" class="img-fluid" alt="">
                        <a href="@/assets/image/portfolio/production.png" class="link-preview" data-lightbox="portfolio"
                            data-title="xx信号检测仪web" title="Preview"><i class="ion ion-eye"></i></a>
                        <a href="#" class="link-details" title="More Details"><i class="ion ion-android-open"></i></a>
                        </figure>
                        <div class="portfolio-info">
                        <h4><a href="#">xx信号检测仪web</a></h4>
                        <p>Web</p>
                        </div>
                    </div>
                    </div>



                    <div class="col-lg-4 col-md-6 portfolio-item filter-web wow fadeInUp" data-wow-delay="0.2s">
                    <div class="portfolio-wrap">
                        <figure>
                        <img src="@/assets/image/portfolio/production.png" class="img-fluid" alt="">
                        <a href="@/assets/image/portfolio/production.png" class="link-preview" data-lightbox="portfolio"
                            data-title="xx管理系统web" title="Preview"><i class="ion ion-eye"></i></a>
                        <a href="#" class="link-details" title="More Details"><i class="ion ion-android-open"></i></a>
                        </figure>
                        <div class="portfolio-info">
                            <h4><a href="#">xx管理系统web</a></h4>
                            <p>web</p>
                        </div>
                    </div>
                    </div>



                    <div class="col-lg-4 col-md-6 portfolio-item filter-web wow fadeInUp" data-wow-delay="0.2s">
                    <div class="portfolio-wrap">
                        <figure>
                        <img src="@/assets/image/portfolio/production.png" class=" img-fluid" alt="">
                        <a href="@/assets/image/portfolio/production.png" class="link-preview" data-lightbox="portfolio"
                            data-title="xx停车管理系统web" title="Preview"><i class="ion ion-eye"></i></a>
                        <a href="#" class="link-details" title="More Details"><i class="ion ion-android-open"></i></a>
                        </figure>

                        <div class="portfolio-info">
                            <h4><a href="#">xx停车管理系统</a></h4>
                            <p>Web</p>
                        </div>
                    </div>
                    </div>

                    <!-- 属于app：filter-app -->
                    <div class="col-lg-4 col-md-6 portfolio-item filter-app wow fadeInUp" data-wow-delay="0.2s">
                    <div class="portfolio-wrap">
                        <figure>
                        <img src="@/assets/image/portfolio/production.png" class="img-fluid" alt="">
                        <a href="@/assets/image/portfolio/production.png" class="link-preview" data-lightbox="portfolio"
                            data-title="xx信号检测仪app" title="Preview"><i class="ion ion-eye"></i></a>
                        <a href="#" class="link-details" title="More Details"><i class="ion ion-android-open"></i></a>
                        </figure>

                        <div class="portfolio-info">
                            <h4><a href="#">xx信号检测仪APP</a></h4>
                            <p>App</p>
                        </div>
                    </div>
                    </div>

                    <div class="col-lg-4 col-md-6 portfolio-item filter-app wow fadeInUp" data-wow-delay="0.2s">
                    <div class="portfolio-wrap">
                        <figure>
                        <img src="@/assets/image/portfolio/production.png" class="img-fluid" alt="">
                        <a href="@/assets/image/portfolio/production.png" class="link-preview" data-lightbox="portfolio"
                            data-title="xx微信小程序" title="xx微信小程序"><i class="ion ion-eye"></i></a>
                        <a href="#" class="link-details" title="More Details"><i class="ion ion-android-open"></i></a>
                        </figure>

                        <div class="portfolio-info">
                            <h4><a href="#">xx微信小程序</a></h4>
                            <p>App</p>
                        </div>
                    </div>
                    </div>
                    <!-- 属于硬件：filter-硬件 -->
                    <div class="col-lg-4 col-md-6 portfolio-item filter-硬件 wow fadeInUp" data-wow-delay="0.2s">
                    <div class="portfolio-wrap">
                        <figure>
                        <img src="@/assets/image/portfolio/production.png" class="img-fluid" alt="">
                        <a href="@/assets/image/portfolio/production.png" class="link-preview" data-lightbox="portfolio"
                            data-title="xx检测仪硬件" title="xx检测仪硬件"><i class="ion ion-eye"></i></a>
                        <a href="#" class="link-details" title="More Details"><i class="ion ion-android-open"></i></a>
                        </figure>

                        <div class="portfolio-info">
                        <h4><a href="#">xx检测仪硬件</a></h4>
                        <p>硬件</p>
                        </div>
                    </div>
                    </div>
                </div>

                </div>
            </section><!-- #portfolio -->

            <!-- 荣誉 -->
            <section id="honer" class="section-bg">
                <div class="container">

                <div class="section-header wow fadeInUp">
                    <h3>荣誉证书</h3>
                </div>

                <div class="row honer-imgs">

                    <div class="col-lg-4 col-md-6 wow fadeInUp">
                    <div class="imgbox">
                        <img src="@/assets/image/honers/honer.png" alt="">
                    </div>
                    </div>

                    <div class="col-lg-4 col-md-6 wow fadeInUp">
                    <div class="imgbox">
                        <img src="@/assets/image/honers/honer.png" alt="">
                    </div>
                    </div>

                    <div class="col-lg-4 col-md-6 wow fadeInUp">
                    <div class="imgbox">
                        <img src="@/assets/image/honers/honer.png" alt="">
                    </div>
                    </div>

                </div>
                </div>
            </section><!-- #contact -->

            <!-- 软著：patents -->
            <section id="honer" class="section-bg">
                <div class="container">

                <div class="section-header wow fadeInUp">
                    <h3>软著</h3>
                </div>

                <div class="row honer-imgs">

                    <div class="col-lg-3 col-md-6 wow fadeInUp">
                    <div class="imgbox">
                        <img src="@/assets/image/patents/patents.png" alt="">
                    </div>
                    </div>

                    <div class="col-lg-3 col-md-6 wow fadeInUp">
                    <div class="imgbox">
                        <img src="@/assets/image/patents/patents.png" alt="">
                    </div>
                    </div>

                    <div class="col-lg-3 col-md-6 wow fadeInUp">
                    <div class="imgbox">
                        <img src="@/assets/image/patents/patents.png" alt="">
                    </div>
                    </div>

                    <div class="col-lg-3 col-md-6 wow fadeInUp">
                    <div class="imgbox">
                        <img src="@/assets/image/patents/patents.png" alt="">
                    </div>
                    </div>

                </div>

                </div>
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

export default {
    name: 'Index',
    components: {
      Header, Footer
    },
    data() {
        return {
            siteConfigParms: {
                configKey: this.$MY_CONSTANT.SITE_CONFIG.SITE_PAGE_MAINCONFIG.configKey
            },
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
                teamDescription: "这里是物联网工作室，在这里不仅有学习硬件，还有学习软件的小伙伴们，我们都在前进的路上，未来值得期待！\n工作室有着丰富的学习资源，有着可以帮助你解决问题的学长学姐们以及专业指导老师，让你不断在专业领域进行探索和挖掘知识宝藏。\n"
            }

        }
    },
    created() {
        console.log("index")
        window.curPage = 'index'
        this.$getSiteConfig(this.siteConfigParms).then(configValue=>{
            // console.log(configValue)
            this.siteConfig.banners = configValue.bannerTableData
            this.siteConfig.teamDescription = configValue.teamDescription.replace(/\n/g,'<br />')
        });
    },
}
</script>

<style scoped lang="scss">
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