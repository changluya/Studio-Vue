// 定义一个函数，用于根据路径和版本信息获取当前路径对应的版本路径
function getVersionPath(path, versions, defaultVersion) {
    /**
     * 如果路径是 /、/README.md 或 /index.html，我们希望重定向到默认版本。
     * 否则，我们希望重定向到版本化的路径。
     */
    const parts = path.split('/'); // 将路径按 '/' 分割成数组
    const version = versions.find((v) => v.folder === parts[1]); // 查找与路径第一部分匹配的版本
    return version ? version.folder : defaultVersion; // 返回匹配的版本文件夹名称，否则返回默认版本
}

// 定义一个 Docsify 插件函数
function versionedDocsPlugin(hook, vm) {
    var versions = vm.config.versions || []; // 获取配置中的版本信息，如果没有则默认为空数组
    var defaultVersion = vm.config.versions.find((v) => v.default).folder; // 查找默认版本并获取其文件夹名称

    // 定义一个函数，用于更新当前显示的版本
    function updateVersion(version) {
        // console.log("vm.route.path=>", vm.route.path)
        // alert(vm.route.path)
        var newPath = vm.route.path.split('/').slice(2).join('/'); // 获取当前路径中版本之后的部分
        if (newPath === '' || newPath === 'README.md') {
            newPath = '/'; // 如果路径为空或为 README.md，则设置为根路径
        }
        window.location.href = '/' + version + newPath; // 重定向到新版本的路径
        vm.basePath = '/' + version + newPath; // 更新 Docsify 的基础路径
    }

    // 定义一个函数，用于初始化版本选择器
    function initVersionSelector() {
        // 创建一个 div 元素作为版本选择器容器
        var selector = document.createElement('div');
        selector.className = 'version-selector'; // 设置容器的类名
        selector.innerHTML = `
        <select>
            ${versions
            .map(
                (v) =>
                    `<option value="${v.folder}" ${
                        v.default ? 'selected' : '' // 如果版本是默认版本，则设置为选中状态
                    }>${v.label}</option>` // 生成版本选项
            )
            .join('')}
        </select>
        `;

        // 添加事件监听器
        var versionPath = (window.location.pathname && window.location.pathname.split('/')[1]) || defaultVersion; // 获取当前路径中的版本部分
        selector.querySelector('select').value = versionPath; // 设置选择器的值为当前版本
        selector.querySelector('select').addEventListener('change', function () {
            updateVersion(this.value); // 当选择器值变化时，调用 updateVersion 函数
        });

        // 添加标签
        var labelText = vm.config.versionSelectorLabel || 'Version:'; // 获取配置中的标签文本，如果没有则默认为 'Version:'
        var label = document.createElement('span');
        label.className = 'version-selector-label'; // 设置标签的类名
        label.textContent = labelText; // 设置标签的文本内容
        selector.insertBefore(label, selector.querySelector('select')); // 将标签插入到选择器前面

        // 将版本选择器插入到页面中
        var nameEl = document.querySelector('.app-name'); // 查找页面中的应用名称元素
        if (nameEl) {
            var versionLabel = versions.find((v) => v.folder === versionPath).label; // 获取当前版本的标签
            nameEl.innerHTML += ` <small>${versionLabel}</small>`; // 在应用名称后添加版本标签
            nameEl.parentNode.insertBefore(selector, nameEl.nextElementSibling); // 将版本选择器插入到应用名称后面
        }
        return selector; // 返回版本选择器元素
    }

    // 在 Docsify 的 ready 钩子中执行代码
    hook.ready(function () {
        // 如果当前路径不是封面页路径，则禁用封面页
        if (vm.route.path !== '/_coverpage.md') {
            vm.config.coverpage = false;
        }

        // 初始化版本选择器
        versionSelector = initVersionSelector();
    });

    // 在 Docsify 的 beforeEach 钩子中执行代码
    hook.beforeEach(function (html, next) {
        // 替换所有 Markdown 文件中的 {{versionLabel}} 为当前版本的标签
        var versionPath = getVersionPath(vm.compiler.contentBase, versions, defaultVersion); // 获取当前路径对应的版本
        var version = versions.find((v) => v.folder === versionPath); // 查找当前版本

        if (version) {
            var versionLabel = version.label; // 获取当前版本的标签
            var updatedHtml = html.replace(/{{versionLabel}}/g, versionLabel); // 替换 HTML 中的 {{versionLabel}}
            next(updatedHtml); // 将更新后的 HTML 传递给下一个钩子
        } else {
            next(html); // 如果没有找到版本，则直接传递原始 HTML
        }
    });

    // 在 Docsify 的 doneEach 钩子中执行代码
    hook.doneEach(function () {
        // 替换封面页中的 {{versionLabel}} 为当前版本的标签
        var versionPath = getVersionPath(vm.compiler.contentBase, versions, defaultVersion); // 获取当前路径对应的版本
        var version = versions.find((v) => v.folder === versionPath); // 查找当前版本

        if (version) {
            var versionLabel = version.label; // 获取当前版本的标签
            var cover = document.querySelector('.cover'); // 查找封面页元素
            if (cover) {
                cover.innerHTML = cover.innerHTML.replace(/{{versionLabel}}/g, versionLabel); // 替换封面页中的 {{versionLabel}}
            }
        }
    });
}

// 将 versionedDocsPlugin 插件添加到 Docsify 的插件列表中
window.$docsify.plugins = [].concat(versionedDocsPlugin, window.$docsify.plugins);

// 在页面加载时执行的自执行函数
(function () {
    // 如果当前路径是根路径或 index.html，则重定向到默认版本的路径
    if (window.location.pathname === '/' || window.location.pathname === '/index.html') {
        // 选择当前参数中配置default=true的版本，然后进行替换访问
        var defaultVersion = window.$docsify.versions.find((v) => v.default).folder; // 获取默认版本的文件夹名称
        window.location.replace('/' + defaultVersion + '/'); // 重定向到默认版本的路径
    }
})();