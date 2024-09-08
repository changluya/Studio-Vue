<template>
  <div>
    <div @click.prevent.stop>
      <div :id="mdId" :style="{height:height+'px'}" />
    </div>
  </div>
</template>
<script>
import Cherry from 'cherry-markdown'
import 'cherry-markdown/dist/cherry-markdown.css'

export default {
  name: 'Cherry',
  props: {
    height: {
      type: Number | String,
      default: null
    },
    mdId: {
      type: String,
      default: 'markdown-container'
    }
  },
  data() {
    return {
      content: null,
      cherrInstance: null
    }
  },
  created() {
  },
  mounted() {
    this.initCherryMD()
  },
  methods: {
    // 初始化编辑器
    initCherryMD(value, config) {
      var {
        afterChange,
        afterInit,
        beforeImageMounted,
        fileUpload,
        mdId
      } = this
      var defaultValue = value || this.value
      /**
       * 自定义一个自定义菜单
       * 点第一次时，把选中的文字变成同时加粗和斜体
       * 保持光标选区不变，点第二次时，把加粗斜体的文字变成普通文本
       */
      var customMenuA = Cherry.createMenuHook('加粗斜体', {
        iconName: 'font',
        onClick: function(selection) {
          // 获取用户选中的文字，调用getSelection方法后，如果用户没有选中任何文字，会尝试获取光标所在位置的单词或句子
          let $selection = this.getSelection(selection) || '同时加粗斜体';
          // 如果是单选，并且选中内容的开始结束内没有加粗语法，则扩大选中范围
          if (!this.isSelections && !/^\s*(\*\*\*)[\s\S]+(\1)/.test($selection)) {
            this.getMoreSelection('***', '***', () => {
              const newSelection = this.editor.editor.getSelection();
              const isBoldItalic = /^\s*(\*\*\*)[\s\S]+(\1)/.test(newSelection);
              if (isBoldItalic) {
                $selection = newSelection;
              }
              return isBoldItalic;
            });
          }
          // 如果选中的文本中已经有加粗语法了，则去掉加粗语法
          if (/^\s*(\*\*\*)[\s\S]+(\1)/.test($selection)) {
            return $selection.replace(/(^)(\s*)(\*\*\*)([^\n]+)(\3)(\s*)($)/gm, '$1$4$7');
          }
          /**
           * 注册缩小选区的规则
           *    注册后，插入“***TEXT***”，选中状态会变成“***【TEXT】***”
           *    如果不注册，插入后效果为：“【***TEXT***】”
           */
          this.registerAfterClickCb(() => {
            this.setLessSelection('***', '***');
          });
          return $selection.replace(/(^)([^\n]+)($)/gm, '$1***$2***$3');
        }
      });
      /**
       * 定义一个空壳，用于自行规划cherry已有工具栏的层级结构
       */
      var customMenuB = Cherry.createMenuHook('实验室', {
        iconName: '',
      });
      /**
       * 定义一个自带二级菜单的工具栏
       */
      var customMenuC = Cherry.createMenuHook('帮助中心', {
        iconName: 'question',
        onClick: (selection, type) => {
          switch (type) {
            case 'markdown':
              return `${selection}markdown教程在这里：https://markdown.com.cn/`;
            case 'Emoji':
              return `${selection}Emoji表情在这里：https://emojipedia.org/zh/`;
            case 'formula':
              return `${selection}LaTeX公式编辑器在这里：https://www.latexlive.com/`;
            case 'Example':
              return `${selection}完整示例看这里：https://tencent.github.io/cherry-markdown/examples/index.html`;
            default:
              return selection;
          }
        },
        subMenuConfig: [{
          noIcon: true,
          name: 'markdown教程',
          onclick: (event) => {
            this.cherrInstance.toolbar.menus.hooks.customMenuCName.fire(null, 'markdown')
          }
        },
          {
            noIcon: true,
            name: 'Emoji 表情',
            onclick: (event) => {
              this.cherrInstance.toolbar.menus.hooks.customMenuCName.fire(null, 'Emoji')
            }
          },
          {
            noIcon: true,
            name: '公式编辑器',
            onclick: (event) => {
              this.cherrInstance.toolbar.menus.hooks.customMenuCName.fire(null, 'formula')
            }
          },
          {
            noIcon: true,
            name: '完整示例',
            onclick: (event) => {
              this.cherrInstance.toolbar.menus.hooks.customMenuCName.fire(null, 'Example')
            }
          },
        ]
      });
      this.cherrInstance = new Cherry({
        id: mdId,
        value: defaultValue,
        fileUpload: fileUpload,
        // 第三方包
        externals: { // externals
        },
        // 解析引擎配置
        engine: {
          // 全局配置
          global: {
            // 是否启用经典换行逻辑
            // true：一个换行会被忽略，两个以上连续换行会分割成段落，
            // false： 一个换行会转成<br>，两个连续换行会分割成段落，三个以上连续换行会转成<br>并分割段落
            classicBr: false,

            /**
             * 全局的URL处理器
             * @param {string} url 来源url
             * @param {'image'|'audio'|'video'|'autolink'|'link'} srcType 来源类型
             * @returns
             */
            urlProcessor: this.urlProcessor,

            /**
             * 额外允许渲染的html标签
             * 标签以英文竖线分隔，如：htmlWhiteList: 'iframe|script|style'
             * 默认为空，默认允许渲染的html见src/utils/sanitize.js whiteList 属性
             * 需要注意：
             *    - 启用iframe、script等标签后，会产生xss注入，请根据实际场景判断是否需要启用
             *    - 一般编辑权限可控的场景（如api文档系统）可以允许iframe、script等标签
             */
            htmlWhiteList: ''
          },
          // 内置语法配置
          syntax: {
            // 语法开关
            // 'hookName': false,
            // 语法配置
            // 'hookName': {
            //
            // }
            autoLink: {
              /** 是否开启短链接 */
              enableShortLink: true,

              /** 短链接长度 */
              shortLinkLength: 20
            },
            list: {
              listNested: false,
              // 同级列表类型转换后变为子级
              indentSpace: 2 // 默认2个空格缩进

            },
            table: {
              enableChart: false // chartRenderEngine: EChartsTableEngine,
              // externals: ['echarts'],

            },
            inlineCode: {
              theme: 'red'
            },
            codeBlock: {
              theme: 'dark',
              // 默认为深色主题
              wrap: true,
              // 超出长度是否换行，false则显示滚动条
              lineNumber: true,
              // 默认显示行号
              copyCode: true,
              // 是否显示“复制”按钮
              customRenderer: { // 自定义语法渲染器
              },
              mermaid: {
                svg2img: false, // 是否将mermaid生成的画图变成img格式
              },

              /**
               * indentedCodeBlock是缩进代码块是否启用的开关
               *
               *    在6.X之前的版本中默认不支持该语法。
               *    因为cherry的开发团队认为该语法太丑了（容易误触）
               *    开发团队希望用```代码块语法来彻底取代该语法
               *    但在后续的沟通中，开发团队发现在某些场景下该语法有更好的显示效果
               *    因此开发团队在6.X版本中才引入了该语法
               *    已经引用6.x以下版本的业务如果想做到用户无感知升级，可以去掉该语法：
               *        indentedCodeBlock：false
               */
              indentedCodeBlock: true
            },
            emoji: {
              useUnicode: false, // 是否使用unicode进行渲染
              customResourceURL: 'https://github.githubassets.com/images/icons/emoji/unicode/${code}.png?v8',
              upperCase: true,

            },
            fontEmphasis: {
              /**
               * 是否允许首尾空格
               * 首尾、前后的定义： 语法前**语法首+内容+语法尾**语法后
               * 例：
               *    true:
               *           __ hello __  ====>   <strong> hello </strong>
               *           __hello__    ====>   <strong>hello</strong>
               *    false:
               *           __ hello __  ====>   <em>_ hello _</em>
               *           __hello__    ====>   <strong>hello</strong>
               */
              allowWhitespace: false
            },
            strikethrough: {
              /**
               * 是否必须有前后空格
               * 首尾、前后的定义： 语法前**语法首+内容+语法尾**语法后
               * 例：
               *    true:
               *            hello wor~~l~~d     ====>   hello wor~~l~~d
               *            hello wor ~~l~~ d   ====>   hello wor <del>l</del> d
               *    false:
               *            hello wor~~l~~d     ====>   hello wor<del>l</del>d
               *            hello wor ~~l~~ d     ====>   hello wor <del>l</del> d
               */
              needWhitespace: false
            },
            mathBlock: {
              engine: 'MathJax',
              // katex或MathJax
              src: 'https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-svg.js', // 如果使用MathJax plugins，则需要使用该url通过script标签引入
              plugins: true // 默认加载插件

            },
            inlineMath: {
              engine: 'MathJax',
              // katex或MathJax
              src: 'https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-svg.js' // 如果使用MathJax plugins，则需要使用该url通过script标签引入
            },
            toc: {
              /** 默认只渲染一个目录 */
              allowMultiToc: false
            },
            header: {
              /**
               * 标题的样式：
               *  - default       默认样式，标题前面有锚点
               *  - autonumber    标题前面有自增序号锚点
               *  - none          标题没有锚点
               */
              anchorStyle: 'default'
            }
          }
        },
        editor: {
          id: 'code',
          // textarea 的id属性值
          name: 'code',
          // textarea 的name属性值
          autoSave2Textarea: false,
          // 是否自动将编辑区的内容回写到textarea里
          theme: 'default',
          // depend on codemirror theme name: https://codemirror.net/demo/theme.htm
          // 编辑器的高度，默认100%，如果挂载点存在内联设置的height则以内联样式为主
          height: '100%',
          // defaultModel 编辑器初始化后的默认模式，一共有三种模式：1、双栏编辑预览模式；2、纯编辑模式；3、预览模式
          // edit&preview: 双栏编辑预览模式
          // editOnly: 纯编辑模式（没有预览，可通过toolbar切换成双栏或预览模式）
          // previewOnly: 预览模式（没有编辑框，toolbar只显示“返回编辑”按钮，可通过toolbar切换成编辑模式）
          defaultModel: this.defaultModel,
          // 粘贴时是否自动将html转成markdown
          convertWhenPaste: true,
          codemirror: {
            // 是否自动focus 默认为true
            autofocus: true
          }
        },
        toolbars: {
          theme: 'dark',
          // light or dark
          showToolbar: this.defaultModel=="previewOnly"?false:true,
          // false：不展示顶部工具栏； true：展示工具栏; toolbars.showToolbar=false 与 toolbars.toolbar=false 等效
          toolbar: ['bold', 'italic',
            {
              strikethrough: ['strikethrough', 'underline', 'sub', 'sup', 'ruby', 'customMenuAName'],
            },
            'size','|', 'color', 'header', '|','drawIo', '|', 'list',
            'panel', 'justify', // 对齐方式，默认不推荐这么“复杂”的样式要求
            'detail', '|', {
              insert: ['image', 'audio', 'video', 'link', 'hr', 'br', 'code', 'formula', 'toc', 'table',
                'line-table', 'bar-table', 'pdf', 'word'
              ]
            }, 'graph', 'export', 'codeTheme', 'switchModel', 'togglePreview',
            // {
            //   customMenuBName: ['ruby', 'audio', 'video', 'customMenuAName'], //实验室
            // },
            'settings', 'customMenuCName', 'theme'
          ],
          toolbarRight: ['fullScreen', '|'],
          bubble: ['bold', 'italic', 'underline', 'strikethrough', 'sub', 'sup', 'quote', 'ruby', '|', 'size',
            'color'
          ],
          // array or false
          "float": ['h1', 'h2', 'h3', '|', 'checklist', 'quote', 'quickTable', 'code'], // array or false
          sidebar: ['mobilePreview', 'theme'], // 'copy',
          customMenu: {
            customMenuAName: customMenuA,
            customMenuBName: customMenuB,
            customMenuCName: customMenuC,
          },
        },
        // 打开draw.io编辑页的url，如果为空则drawio按钮失效
        drawioIframeUrl: window.location.origin + '/CherryMarkdown/drawio_demo.html',
        /**
         * 上传文件的时候用来指定文件类型
         */
        fileTypeLimitMap: {
          video: 'video/*',
          audio: 'audio/*',
          image: 'image/*',
          word: '.doc,.docx',
          pdf: '.pdf',
          file: '*',
        },
        callback: {
          afterChange: this.afterChange,
          afterInit: this.afterInit,
          beforeImageMounted: this.beforeImageMounted,
          // 预览区域点击事件，previewer.enablePreviewerBubble = true 时生效
          onClickPreview: this.onClickPreview,
          // 复制代码块代码时的回调
          onCopyCode: this.onCopyCode,
          // 把中文变成拼音的回调，当然也可以把中文变成英文、英文变成中文
          changeString2Pinyin: this.changeString2Pinyin
        },
        previewer: {
          // 自定义markdown预览区域class
          className: 'cherry-markdown-previewer'
        },
        // 预览页面不需要绑定事件
        isPreviewOnly: false,
        // 预览区域跟随编辑器光标自动滚动
        autoScrollByCursor: true,
        // 外层容器不存在时，是否强制输出到body上
        forceAppend: true,
        // The locale Cherry is going to use. Locales live in /src/locales/
        locale: 'zh_CN'
      })
    },
  }
}
</script>

<style scoped>

</style>
