marked.setOptions({sanitize: true});

// Androidからの呼び出しがある
function rendering(title, content) {
    const labelTitle = document.getElementById("js-daily-report-title");
    const labelContent = document.getElementById("js-daily-report-content");
                        
    labelTitle.textContent = title;
    labelContent.innerHTML = marked(content);
}
