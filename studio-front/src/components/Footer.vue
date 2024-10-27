<template>
    <footer id="footer">
        <div class="footer-top">
            <div class="container">
            <div class="row">

                <div class="col-lg-4 col-md-6 footer-info" data-wow-delay="0.3s">
                    <h3>简介</h3>
                    <p style="white-space: pre-wrap;" v-html="siteConfig.briefDescription"></p>
                </div>

                <div class="col-lg-4 col-md-6 footer-links" data-wow-delay="0.3s">
                <h4>网站地图</h4>
                <ul>
                    <li><i class="ion-ios-arrow-right"></i> <a href="#about">关于我们</a></li>
                    <li><i class="ion-ios-arrow-right"></i> <a href="#portfolio">作品</a></li>
                    <li><i class="ion-ios-arrow-right"></i> <a href="./time.html">时光轴</a></li>
                    <li><i class="ion-ios-arrow-right"></i> <a href="./team.html">团队</a></li>
                    <li><i class="ion-ios-arrow-right"></i> <a href="#honer">荣誉证书</a></li>
                </ul>
                </div>

                <div class="col-lg-4 col-md-6 footer-contact" data-wow-delay="0.3s">
                <h4>联系我们</h4>
                <p>
                    <strong>联系人：</strong><span v-text="siteConfig.contactPerson"></span><br>
                    <strong>地址：</strong><span v-text="siteConfig.contactAddress"></span><br>
                    <strong>电子邮件：</strong><span v-text="siteConfig.contactEmail"></span><br>
                </p>
                </div>
            </div>
            </div>
        </div>

        <div class="container">
            <div class="copyright">
            Copyright  <span v-html="time"/> Studio All rights reserved.
            </div>
            <div class="copyright" style="padding-top: 10px !important;">
            <a style="color: white!important;" href="https://beian.miit.gov.cn/" rel="noreferrer" target="_blank" v-html="ISPN"></a>
            </div>
        </div>
    </footer><!-- #footer -->
</template>

<script>

    export default {
        name: 'Footer',
        data() {
            return {
                siteFooterConfigParms: {
                    configKey: this.$MY_CONSTANT.SITE_CONFIG.SITE_PAGE_FOOTER_CONFIG.configKey
                },
                siteBasicConfigParms: {
                    configKey: this.$MY_CONSTANT.SITE_CONFIG.SITE_BASIC_CONFIG.configKey
                },
                siteConfig: {
                    briefDescription: "去年在工作室偶然听到其他工作室要做一个官网，接着趁着一时兴起然后也找了个模板来做了一个自己的官网以及一个后台管理系统，当时的后台系统只有一个登录、注册还有一个提交个人信息的页面，主要为了方便展示动态汇总团队成员。该工作室系统主要面向校园工作室，可供个人及工作室团队学习使用。",
                    contactPerson: "长路老师",
                    contactAddress: "xx大学xx大楼xxx层",
                    contactEmail: "939974883@qq.com"
                }
            }
        },
        computed: {
            time() {
                // 获取网站创建时间
                let createYear = this.$store.state.site.siteCreateTime.substring(0, 4)
                let curYear = new Date().getFullYear()
                // 合并为最终网站的历史时间
                return createYear + '-' + curYear
            },
            ISPN() {
                return this.$store.state.site.ISPN
            }
        },
        created() {
            this.getSiteConfig();
        },
        methods: {
            getSiteConfig() {
                // console.log("this.siteFooterConfigParms=>", this.siteFooterConfigParms)
                // 获取网站底部栏目配置
                this.$getSiteConfig(this.siteFooterConfigParms).then(configValue=>{
                    this.siteConfig.briefDescription = configValue.briefDescription.replace(/\n/g,'<br />')
                    this.siteConfig.contactPerson = configValue.contactPerson
                    this.siteConfig.contactAddress = configValue.contactAddress
                    this.siteConfig.contactEmail = configValue.contactEmail
                });
            }
        }
    }
</script>

<style scoped>

</style>